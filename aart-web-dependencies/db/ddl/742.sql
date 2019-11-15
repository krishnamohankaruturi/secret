--F767 Audit History for Roster Information
--Adding two new columns into studentstests table for tracking roster & teacher info at the time of student's test
ALTER TABLE studentstests  ADD COLUMN if not exists rosteridtested bigint DEFAULT NULL;
ALTER TABLE studentstests  ADD COLUMN if not exists teacheridtested bigint DEFAULT NULL;

-- Function: updatesectionstatusandgetteststatus(bigint, bigint, text, text, text, double precision, boolean)

-- DROP FUNCTION updatesectionstatusandgetteststatus(bigint, bigint, text, text, text, double precision, boolean);

CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatus(
    in_studenttestid bigint,
    in_testsectionid bigint,
    in_sectionscore text,
    in_testscore text,
    in_categorycode text,
    in_interimthetavalue double precision,
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
  
  
 -- Function: updatesectionstatusandgetteststatusforlcs(bigint, bigint, text, text, text, double precision, timestamp with time zone, timestamp with time zone, timestamp with time zone, timestamp with time zone)

-- DROP FUNCTION updatesectionstatusandgetteststatusforlcs(bigint, bigint, text, text, text, double precision, timestamp with time zone, timestamp with time zone, timestamp with time zone, timestamp with time zone);

CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatusforlcs(
    in_studenttestid bigint,
    in_testsectionid bigint,
    in_sectionscore text,
    in_testscore text,
    in_categorycode text,
    in_interimthetavalue double precision,
    in_stdteststartdatetime timestamp with time zone,
    in_stdtestenddatetime timestamp with time zone,
    in_stdtestsecstartdatetime timestamp with time zone,
    in_stdtestsecenddatetime timestamp with time zone)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
    trackerid bigint;
    testunusedstatusid bigint;

  BEGIN
	iscomplete := false;
	IF in_categorycode = 'inprogress' THEN
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
				and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	ELSE
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
					and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	END IF;
	
	IF in_stdtestsecstartdatetime IS NOT NULL THEN
		IF in_categorycode != 'complete' THEN
			update public.studentstestsections set startdatetime = in_stdtestsecstartdatetime,enddatetime=null, modifieddate=now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
		ELSE 
			update public.studentstestsections set startdatetime = in_stdtestsecstartdatetime, modifieddate=now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
		END IF;
	END IF;
	IF in_stdtestsecenddatetime IS NOT NULL AND in_categorycode = 'complete' THEN
		update public.studentstestsections set enddatetime = in_stdtestsecenddatetime, modifieddate=now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	END IF;
	
	SELECT count(1) into completecount FROM studentstestsections AS sts
		JOIN category On category.id = sts.statusid 
		JOIN public.categorytype ON category.categorytypeid = categorytype.id 
		where categorytype.typecode ='STUDENT_TESTSECTION_STATUS'
			and category.categorycode != 'complete'
			and  sts.studentstestid = in_studenttestid;

	IF completecount = 0 THEN
		iscomplete := true;   
			
					update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
						ON category.categorytypeid = categorytype.id 
						where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'PROCESS_LCS_RESPONSES'),
						scores = in_testscore, interimtheta = in_interimthetavalue, modifieddate=now() where id = in_studenttestid;
					
	ELSIF in_testscore IS NOT NULL THEN
		update public.studentstests set scores = in_testscore, modifieddate=now() where id = in_studenttestid;
	END IF;
	
	
	IF in_stdteststartdatetime IS NOT NULL THEN
		IF iscomplete = false THEN
			update public.studentstests set startdatetime = in_stdteststartdatetime,enddatetime=null, modifieddate=now() where id = in_studenttestid; 
		ELSE 
			update public.studentstests set startdatetime = in_stdteststartdatetime, modifieddate=now() where id = in_studenttestid; 
		END IF;
	END IF;
	IF in_stdtestenddatetime IS NOT NULL AND iscomplete = true THEN
		update public.studentstests set enddatetime = in_stdtestenddatetime, modifieddate=now() where id = in_studenttestid; 
	END IF;	
	
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
-- Function: updatestudentteststatuscompleteforlcs(bigint)

-- DROP FUNCTION updatestudentteststatuscompleteforlcs(bigint);

CREATE OR REPLACE FUNCTION updatestudentteststatuscompleteforlcs(in_studenttestid bigint)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
    trackerid bigint;
    testunusedstatusid bigint;
    studenttest_status varchar;
	testteacherid bigint; -- F767:to store the teacher id for the studentstest
	testrosterid bigint; -- F767:to store the roster id of the student
  BEGIN
	iscomplete := false;

	select into studenttest_status category.categorycode from public.category 
	  	JOIN public.studentstests st ON st.status = category.id where st.id=in_studenttestid;
	
	SELECT count(1) into completecount FROM studentstestsections AS sts
		JOIN category On category.id = sts.statusid 
		JOIN public.categorytype ON category.categorytypeid = categorytype.id 
		where categorytype.typecode ='STUDENT_TESTSECTION_STATUS'
			and category.categorycode != 'complete'
			and  sts.studentstestid = in_studenttestid;

	IF completecount = 0 AND studenttest_status = 'PROCESS_LCS_RESPONSES' THEN
		iscomplete := true;

		trackerid := (select studenttrackerid from studenttrackerband stb inner join studentstests sts 
					on stb.testsessionid=sts.testsessionid where sts.id = in_studenttestid);
		testunusedstatusid := (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'unused');
		-- F767 : retreiving rosterid for a student to update in studentstests table		
		testrosterid := (select ts.rosterid from studentstests st
					join testsession ts on st.testsessionid = ts.id 
					where st.id=in_studenttestid);
		IF testrosterid is not null THEN
				testteacherid := (select r.teacherid from roster r where r.id = testrosterid);	
			-- F767: update query modified to insert teacherid and rosterid info into studentstests table for semi offline mode
			update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'),
				rosteridtested=testrosterid,teacheridtested=testteacherid,				
				modifieddate=now() where id = in_studenttestid;
		ELSE
			update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'), 
				modifieddate=now() where id = in_studenttestid;
		END IF; -- F767 end of codechanges
			
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

	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;  

