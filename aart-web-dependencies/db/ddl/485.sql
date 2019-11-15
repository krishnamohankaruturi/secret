--ddl/485.sql
-- Function: updatesectionstatusandgetteststatus(bigint, bigint, text, text, text, double precision)

-- DROP FUNCTION updatesectionstatusandgetteststatus(bigint, bigint, text, text, text, double precision);

CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatus(in_studenttestid bigint, in_testsectionid bigint, in_sectionscore text, in_testscore text, in_categorycode text, in_interimthetavalue double precision)
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
			scores = in_sectionscore, modifieddate=now(), startdatetime = now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
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
			update public.studenttracker set status='UNTRACKED' where status='TRACKED' and id = trackerid;
		END IF;
		--KAP
		update studentstests set status=testunusedstatusid where id in (select distinct nextst.id from studentstests st
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
	ELSIF in_testscore IS NOT NULL THEN
		update public.studentstests set scores = in_testscore, modifieddate=now() where id = in_studenttestid;
	END IF;
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
-- Function: randomizestudenttest(text, text)

-- DROP FUNCTION randomizestudenttest(text, text);

CREATE OR REPLACE FUNCTION randomizestudenttest(IN in_username text, IN in_testtypename text)
  RETURNS TABLE(assessmentprogramid bigint, assessmentprogramname character varying, testingprogramid bigint, testingprogramname character varying, contentarea character varying, gradecourse character varying, testname character varying, testformatcode character varying, studentstestsid bigint, studentid bigint, testid bigint, testcollectionid bigint, status character varying, testsessionid bigint, activeflag boolean, ticketno character varying, windowopen boolean, createduser integer, modifieduser integer, modifieddate timestamp without time zone, createddate timestamp without time zone) AS
$BODY$
  DECLARE
    studenttestid bigint;
    randomized_testid bigint;
    randomized_testname text;
    status_id bigint;
    timedout_status_id bigint;
    reactivated_status_id bigint;
    inprogress_status_id bigint;
    test_unused_status_id bigint;
    test_inprogress_status_id bigint;
    deployed_status_id bigint;
    test_testsections RECORD;
    studenttestsection RECORD;
    studenttest RECORD;
    grace_period bigint;
    current_millis decimal;
    student_id bigint;
  BEGIN
                select id into student_id from student where username=in_username;
                select into status_id category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id
                                  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
                                  and category.categorycode = 'unused'; 
                select into timedout_status_id category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id
                                  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
                                  and category.categorycode = 'inprogresstimedout';
                select into reactivated_status_id category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id
                                  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
                                  and category.categorycode = 'reactivated';
                select into inprogress_status_id category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id
                                  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
                                  and category.categorycode = 'inprogress';
                SELECT c.id into test_unused_status_id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
        AND c.categorycode = 'unused'
        AND ct.typecode='STUDENT_TEST_STATUS';
    SELECT c.id into test_inprogress_status_id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
        AND c.categorycode = 'inprogress'
        AND ct.typecode='STUDENT_TEST_STATUS';
    select category.id into deployed_status_id from category, categorytype
                where category.categorytypeid = categorytype.id and categorytype.typecode = 'TESTSTATUS' and category.categorycode = 'DEPLOYED';
                                                                  
  FOR studenttestid IN (select sts.id from studentstests sts where sts.testid is null
                                                                and sts.studentid = student_id and sts.activeflag is true
                                                                and sts.status IN (test_unused_status_id, test_inprogress_status_id))
  LOOP
    select into randomized_testid, randomized_testname  test.id, test.testname
                from (select * from studentstests stt join testcollection tc on tc.id = stt.testcollectionid where stt.id = studenttestid ) studenttesttestcollection
                join testcollectionstests tct on tct.testcollectionid = studenttesttestcollection.testcollectionid
                join test on test.id = tct.testid
                where not exists (select distinct st.testid from studentstests st where st.studentid = studenttesttestcollection.studentid 
          and st.testcollectionid = studenttesttestcollection.testcollectionid and st.testid = test.id
          and st.status IN (test_unused_status_id, test_inprogress_status_id))
                and test.status = deployed_status_id
                and test.qccomplete = true ORDER BY random() LIMIT 1;
                IF randomized_testid is not null THEN
      update public.studentstests set testid = randomized_testid where id = studenttestid;
 
      FOR test_testsections IN (select testsection.id from public.testsection where testsection.testid = randomized_testid)
                        LOOP
        insert into public.studentstestsections(studentstestid, testsectionid, statusid) values(studenttestid, test_testsections.id, status_id);
      END LOOP;
                END IF;
    END LOOP;
    --get all tests, check grace period timeout and update status
    FOR studenttest IN (SELECT st.id, st.studentid, st.testid,st.testcollectionid,ts.operationaltestwindowid
                FROM studentstests st 
      INNER JOIN test t ON st.testid = t.id
      INNER JOIN student s ON st.studentid = s.id
      INNER JOIN testsession ts on ts.id=st.testsessionid
      INNER JOIN operationaltestwindow otw ON otw.id = ts.operationaltestwindowid
                WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
                                AND st.activeflag = true
                                AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false) 
          OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP))
                                AND s.id = student_id order by st.id)
    LOOP
                SELECT graceperiod into grace_period FROM operationaltestwindowsessionrule otwsr, category sessionrule,categorytype catType
                                WHERE otwsr.sessionruleid = sessionrule.id AND sessionrule.categorytypeid=cattype.id
                                AND sessionrule.categorycode='GRACE_PERIOD' AND cattype.typecode='SESSION_RULES'
                                AND otwsr.operationaltestwindowid = studenttest.operationaltestwindowid and otwsr.activeflag=true;
                IF grace_period IS NOT NULL THEN
      FOR studenttestsection IN (select sts.id, sts.statusid, (EXTRACT(EPOCH FROM sts.modifieddate)+grace_period) as sectionmodifiedmillis from studentstestsections sts where sts.studentstestid=studenttest.id
                                                                                                AND sts.statusid=inprogress_status_id)
      LOOP
        SELECT EXTRACT(EPOCH FROM current_timestamp) INTO current_millis;
        IF (studenttestsection.sectionmodifiedmillis < current_millis) THEN
          update public.studentstestsections set statusid = timedout_status_id, modifieddate=now() where id = studenttestsection.id;
        END IF;
      END LOOP;
                END IF;
    END LOOP;     
 
    --return all tests
    IF in_testtypename ilike 'Practice'  THEN
  RETURN QUERY SELECT ap.id AS assessmentprogramId,
  cast('Practice' as character varying) AS assessmentprogramname,
  tp.id AS testingprogramid, tp.programname AS testingprogramname,
  ca.name as contentarea,
  case when tc.gradebandid is not null then (select gb.name from gradeband gb where gb.id=tc.gradebandid) else (select gc.name from gradecourse gc where gc.id=tc.gradecourseid) end as gradecourse,
  t.testname, t.testformatcode,
  st.id as studentstestsid, st.studentid,
  st.testid, st.testcollectionid,
  category.categorycode, st.testsessionid,
  st.activeflag, st.ticketno,true as windowOpen,
  st.createduser, st.modifieduser,
  st.modifieddate, st.createddate
  FROM studentstests st INNER JOIN test t ON st.testid = t.id
      INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
      INNER JOIN assessment a ON atc.assessmentid = a.id
      INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
      INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
      INNER JOIN testsession ts on ts.id=st.testsessionid
      INNER JOIN testcollection tc on tc.id = st.testcollectionid
      INNER JOIN contentarea ca on ca.id = tc.contentareaid
      INNER JOIN category ON category.id = st.status
                       INNER JOIN operationaltestwindow otw ON otw.id = ts.operationaltestwindowid
  WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
      AND st.activeflag = true 
      AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false ) 
        OR (t.qccomplete IS false and otw.effectivedate IS NULL AND otw.expirydate IS NULL)
        OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP))
      AND st.studentid = student_id AND tp.programabbr = 'P'
      order by ap.programname, st.createddate, t.testname;
    ELSE
  RETURN QUERY SELECT ap.id AS assessmentprogramId,
  ap.programname AS assessmentprogramname,
  tp.id AS testingprogramid, tp.programname AS testingprogramname,
  ca.name as contentarea,
  case when tc.gradebandid is not null then (select gb.name from gradeband gb where gb.id=tc.gradebandid) else (select gc.name from gradecourse gc where gc.id=tc.gradecourseid) end as gradecourse,
  t.testname, t.testformatcode,
  st.id as studentstestsid, st.studentid,
  st.testid, st.testcollectionid, 
  category.categorycode,
  st.testsessionid,
  st.activeflag, st.ticketno,true as windowOpen,
  st.createduser, st.modifieduser,
  st.modifieddate, st.createddate
  FROM studentstests st INNER JOIN test t ON st.testid = t.id
      INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
      INNER JOIN assessment a ON atc.assessmentid = a.id
      INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
      INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
      INNER JOIN testsession ts on ts.id=st.testsessionid
      INNER JOIN testcollection tc on tc.id = st.testcollectionid
      INNER JOIN contentarea ca on ca.id = tc.contentareaid
      INNER JOIN category ON category.id = st.status
      LEFT OUTER JOIN operationaltestwindow otw ON otw.id = ts.operationaltestwindowid
  WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
      AND st.activeflag = true AND tp.programabbr != 'P'
      AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false) 
        OR (t.qccomplete IS false and otw.effectivedate IS NULL AND otw.expirydate IS NULL)
        OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP))
      AND st.studentid = student_id order by ap.programname, st.createddate, t.testname;
    END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
