-- Function: updatesectionstatusandgetteststatus(bigint, bigint, bigint, text, text, text, double precision, text, boolean)

-- DROP FUNCTION updatesectionstatusandgetteststatus(bigint, bigint, bigint, text, text, text, double precision, text, boolean);

CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatus(
    in_studenttestid bigint,
    in_testsectionid bigint,
    in_studentsteststimesid bigint,
    in_sectionscore text,
    in_testscore text,
    in_categorycode text,
    in_interimthetavalue double precision,
    in_action text,
    in_fromlcs boolean)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
    trackerid bigint;
    testunusedstatusid bigint;
    studenttestsection_status varchar;
    studenttest_status varchar;
	testteacherid bigint; -- F767:to store the teacher id for the studentstest
	testrosterid bigint; -- F767:to store the roster id of the student
	
  BEGIN
	iscomplete := false;
	select into studenttestsection_status category.categorycode from public.category 
	  	JOIN public.studentstestsections sts ON sts.statusid = category.id where sts.studentstestid = in_studenttestid and sts.testsectionid = in_testsectionid;
	select into studenttest_status category.categorycode from public.category 
	  	JOIN public.studentstests st ON st.status = category.id where st.id=in_studenttestid;
	-- F767 : retreiving rosterid for a student to update in studentstests table		
	testrosterid := (select ts.rosterid from studentstests st
					join testsession ts on st.testsessionid = ts.id 
					where st.id=in_studenttestid);
	
	IF studenttestsection_status != 'complete' THEN
		IF in_categorycode = 'inprogress' THEN
			update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
					ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
					and category.categorycode = in_categorycode), 
				scores = in_sectionscore, modifieddate=now(), startdatetime = now(), enddatetime=null where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
		ELSE
			update public.studentsteststimes set studentstestsections_endtime=now(), action=in_action where id = in_studentsteststimesid;
			
			update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
					ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
						and category.categorycode = in_categorycode), 
				scores = in_sectionscore, modifieddate=now(), enddatetime = now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
		END IF;

		SELECT count(1) into completecount FROM studentstestsections AS sts
			JOIN category On category.id = sts.statusid 
			JOIN public.categorytype ON category.categorytypeid = categorytype.id 
			where categorytype.typecode ='STUDENT_TESTSECTION_STATUS'
				and category.categorycode != 'complete'
				and  sts.studentstestid = in_studenttestid;

		IF completecount = 0 THEN
			iscomplete := true;
			
			IF in_fromlcs = true THEN
			
				 update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
									ON category.categorytypeid = categorytype.id 
									where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'PROCESS_LCS_RESPONSES'),
									scores = in_testscore, interimtheta = in_interimthetavalue, modifieddate=now(),
									enddatetime = now() where id = in_studenttestid;
			
			ELSE

				trackerid := (select studenttrackerid from studenttrackerband stb inner join studentstests sts 
							on stb.testsessionid=sts.testsessionid where sts.id = in_studenttestid);
				testunusedstatusid := (select category.id from public.category JOIN public.categorytype 
						ON category.categorytypeid = categorytype.id 
						where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'unused');
						
										   
				IF testrosterid is not null THEN
				testteacherid := (select r.teacherid from roster r where r.id = testrosterid);	
				-- F767: update query modified to insert teacherid and rosterid info into studentstests table
					update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
						ON category.categorytypeid = categorytype.id 
						where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'),
						rosteridtested=testrosterid,teacheridtested=testteacherid,
						scores = in_testscore, interimtheta = in_interimthetavalue, 
						modifieddate=now(), enddatetime = now() where id = in_studenttestid;
				ELSE
					update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
						ON category.categorytypeid = categorytype.id 
						where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'),
						scores = in_testscore, interimtheta = in_interimthetavalue, 
						modifieddate=now(), enddatetime = now() where id = in_studenttestid;	
				END IF;
				-- F767 end of codechanges
				
				--F1021: Update start time and end time on studentstests and studentsteststimes
				
				update public.studentsteststimes set studentstests_endtime=now() where id=in_studentsteststimesid;
				
				update public.studentstests st set startdatetime=stt.studentstests_starttime, enddatetime=stt.studentstests_endtime
				from public.studentsteststimes stt where stt.id = in_studentsteststimesid and st.id=stt.studentstestsid;
				
				update public.studentstestsections sts set startdatetime=stt.studentstestsections_starttime, enddatetime=stt.studentstestsections_endtime
				from public.studentsteststimes stt where stt.id = in_studentsteststimesid and sts.id=stt.studentstestsectionid;
				
				--F1021: End
				
				
				--DLM	 
				IF trackerid is not null THEN
					update public.studenttracker set status='UNTRACKED',modifieddate=now() where status='TRACKED' and id = trackerid;
				END IF;
				--KAP
				update studentstests set status=testunusedstatusid,modifieddate=now() where id in (select distinct nextst.id from studentstests st
								inner join testsession ts on st.testsessionid=ts.id
								inner join testcollection tc on tc.id=ts.testcollectionid
								inner join stage s on s.predecessorid=tc.stageid
								inner join testcollection nexttc on nexttc.stageid=s.id and tc.contentareaid=nexttc.contentareaid
								inner join testsession nextts on nexttc.id=nextts.testcollectionid
								inner join studentstests nextst on nextst.testsessionid=nextts.id
									and st.studentid=nextst.studentid
								inner join category nextstatus on nextst.status = nextstatus.id
							where nextstatus.categorycode = 'pending' and ts.source='BATCHAUTO' 
								and nextts.source='BATCHAUTO' and st.id = in_studenttestid);
			
			END IF;
		ELSIF in_testscore IS NOT NULL THEN
			update public.studentstests set scores = in_testscore, modifieddate=now() where id = in_studenttestid;
		END IF;
	ELSE 
		IF studenttest_status = 'complete' THEN
			iscomplete := true;
		END IF;
	END IF;
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;