--ddl/428.sql

drop table if exists studentreportsubscores;
drop sequence if exists studentreportsubscores_id_seq;

DROP FUNCTION IF EXISTS randomizestudenttest(text, text);

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
    FOR studenttest IN (SELECT st.id, st.studentid, st.testid,st.testcollectionid
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
                SELECT graceperiod into grace_period FROM TestCollectionsSessionRules tcsr, category sessionrule,categorytype catType
                                WHERE tcsr.sessionruleid = sessionrule.id AND sessionrule.categorytypeid=cattype.id
                                AND sessionrule.categorycode='GRACE_PERIOD' AND cattype.typecode='SESSION_RULES'
                                                                AND tcsr.testcollectionid = studenttest.testcollectionid;
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
  gc.name as gradecourse,
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
      INNER JOIN gradecourse gc on gc.id = tc.gradecourseid
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
  gc.name as gradecourse,
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
      INNER JOIN gradecourse gc on gc.id = tc.gradecourseid
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
  
 -- chnage pond changes
ALTER TABLE IF EXISTS userassessmentprograms RENAME TO userassessmentprogram;
ALTER TABLE IF EXISTS userassessmentprograms_id_seq RENAME TO userassessmentprogram_id_seq;
 
ALTER TABLE IF EXISTS studentassessmentprograms RENAME TO studentassessmentprogram;
ALTER TABLE IF EXISTS studentassessmentprograms_id_seq RENAME TO studentassessmentprogram_id_seq;

create table studentassessmentprogram
(
	id 				bigserial NOT NULL,
	studentid			bigint NOT NULL,	
	assessmentprogramid		bigint NOT NULL,	
	activeflag 			boolean DEFAULT true,
	
  CONSTRAINT pk_studentassessmentprogram PRIMARY KEY (id),
  CONSTRAINT studentassmentprgm_studentid_fk FOREIGN KEY (studentid)
      REFERENCES student (id), 
  CONSTRAINT studentassmentprgm_assessmentprogramid_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id)
);

------------ drop column funding school      US16289
--ALTER TABLE enrollment DROP COLUMN fundingschool;


 