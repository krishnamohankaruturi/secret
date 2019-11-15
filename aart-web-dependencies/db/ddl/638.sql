--empty for dml/638.sql

CREATE OR REPLACE FUNCTION dlm_testlet_reset(student_id bigint, testsession_id bigint, content_area_id bigint)
  RETURNS void AS
$BODY$

  DECLARE temptable RECORD;

  BEGIN
  CREATE TEMPORARY TABLE tmp_dlm_data (studentid bigint, studentstestsid bigint, testsessionid bigint, studenttrackerid bigint, studenttrackerbandid bigint);
  
  FOR temptable IN ( select stu.id as studentid, st.id as studentstestsid, st.testsessionid, str.id as studenttrackerid,
      strb.id as studenttrackerbandid
      from student stu
      join studentstests st on st.studentid = stu.id
      join studenttrackerband strb on strb.testsessionid = st.testsessionid
      join studenttracker str on strb.studenttrackerid = str.id
      where stu.id = student_id  
      and  st.testsessionid >= testsession_id
      and st.activeflag is true
      and str.contentareaid = content_area_id      
      and strb.activeflag is true
      )
    LOOP
        INSERT INTO tmp_dlm_data(studentid , studentstestsid , testsessionid , studenttrackerid , studenttrackerbandid ) 
                values( temptable.studentid,temptable.studentstestsid,temptable.testsessionid,temptable.studenttrackerid,
                temptable.studenttrackerbandid);
    END LOOP;
    UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now() AT TIME ZONE 'GMT'
    WHERE studentstestsid IN (SELECT id FROM studentstests WHERE testsessionid IN (select testsessionid from tmp_dlm_data) and studentid=student_id);
RAISE NOTICE 'Updated student responses';

UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now() AT TIME ZONE 'GMT'
     WHERE studentstestid IN (SELECT id FROM studentstests WHERE testsessionid IN (select testsessionid from tmp_dlm_data) and studentid=student_id);

UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now() AT TIME ZONE 'GMT'
    WHERE testsessionid IN (select testsessionid from tmp_dlm_data) and studentid=student_id;
  
UPDATE studenttrackerband SET activeflag = false, modifieddate = now() AT TIME ZONE 'GMT', modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
    WHERE studenttrackerid in (select studenttrackerid from tmp_dlm_data);
 
update studenttracker set status = 'UNTRACKED', modifieddate = now() AT TIME ZONE 'GMT', modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') where id in (select studenttrackerid from tmp_dlm_data);

              
DROP TABLE  tmp_dlm_data;       
 
END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatusforlcs(in_studenttestid bigint, in_testsectionid bigint, in_sectionscore text, in_testscore text, in_categorycode text, in_interimthetavalue double precision, in_stdteststartdatetime timestamp with time zone, in_stdtestenddatetime timestamp with time zone, in_stdtestsecstartdatetime timestamp with time zone, in_stdtestsecenddatetime timestamp with time zone)
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
  
CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatus(in_studenttestid bigint, in_testsectionid bigint, in_sectionscore text, in_testscore text, in_categorycode text, in_interimthetavalue double precision, in_fromlcs boolean)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
    trackerid bigint;
    testunusedstatusid bigint;
    studenttestsection_status varchar;
    studenttest_status varchar;
  BEGIN
	iscomplete := false;
	select into studenttestsection_status category.categorycode from public.category 
	  	JOIN public.studentstestsections sts ON sts.statusid = category.id where sts.studentstestid = in_studenttestid and sts.testsectionid = in_testsectionid;
	select into studenttest_status category.categorycode from public.category 
	  	JOIN public.studentstests st ON st.status = category.id where st.id=in_studenttestid;
	
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
				 scores = in_testscore, interimtheta = in_interimthetavalue, modifieddate=now(), enddatetime = now() where id = in_studenttestid;
			
			ELSE

				trackerid := (select studenttrackerid from studenttrackerband stb inner join studentstests sts 
							on stb.testsessionid=sts.testsessionid where sts.id = in_studenttestid);
				testunusedstatusid := (select category.id from public.category JOIN public.categorytype 
						ON category.categorytypeid = categorytype.id 
						where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'unused');

				update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
						ON category.categorytypeid = categorytype.id 
						where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'),
					 scores = in_testscore, interimtheta = in_interimthetavalue, modifieddate=now(), enddatetime = now() where id = in_studenttestid;
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

