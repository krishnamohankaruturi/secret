
ALTER TABLE modulereport ADD COLUMN reporttypeid SMALLINT;

UPDATE modulereport SET reporttypeid = 1;

-- Function: randomizestudenttest(text, text)

-- DROP FUNCTION randomizestudenttest(text, text);

CREATE OR REPLACE FUNCTION randomizestudenttest(IN in_username text, IN in_testtypename text)
  RETURNS TABLE(assessmentprogramid bigint, assessmentprogramname character varying, testingprogramid bigint, testingprogramname character varying, testname character varying, testformatcode character varying, studentstestsid bigint, studentid bigint, testid bigint, testcollectionid bigint, status bigint, testsessionid bigint, activeflag boolean, ticketno character varying, windowopen boolean, createduser integer, modifieduser integer, modifieddate timestamp without time zone, createddate timestamp without time zone) AS
$BODY$
  DECLARE
    studenttestid bigint;
    randomized_testid bigint;
    randomized_testname text;
    status_id bigint;
    timedout_status_id bigint;
    reactivated_status_id bigint;
    inprogress_status_id bigint;
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
                                                                  
  FOR studenttestid IN (select sts.id from studentstests sts where sts.testid is null
                                                                and sts.studentid = student_id and sts.activeflag is true
                                                                and sts.status IN (SELECT c.id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
                                                                                AND c.categorycode not in ('complete', 'fcunenrolled', 'fcmidunenrolled')
                                                                                AND ct.typecode='STUDENT_TEST_STATUS'))
  LOOP
    select into randomized_testid, randomized_testname  test.id, test.testname
                from (select * from studentstests stt join testcollection tc on tc.id = stt.testcollectionid where stt.id = studenttestid ) studenttesttestcollection
                join testcollectionstests tct on tct.testcollectionid = studenttesttestcollection.testcollectionid
                join test on test.id = tct.testid
                where not exists (select st.testid from studentstests st where st.studentid = studenttesttestcollection.studentid and st.testcollectionid = studenttesttestcollection.testcollectionid and st.testid = test.id
                and st.status NOT IN (select category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode != 'complete'))
                and (test.status is null or test.status = (select category.id  from category, categorytype
                where category.categorytypeid = categorytype.id and categorytype.typecode = 'TESTSTATUS' and category.categorycode = 'DEPLOYED' ))
                and test.qccomplete = true ORDER BY random() LIMIT 1;
                IF randomized_testid is not null THEN
                                update public.studentstests set testid = randomized_testid where id = studenttestid;
 
                                FOR test_testsections IN (select testsection.id from public.testsection
                                                                where testsection.testid = randomized_testid)
                                LOOP
                                  insert into public.studentstestsections(studentstestid, testsectionid, statusid) values(studenttestid, test_testsections.id, status_id);
                                END LOOP;
                END IF;
    END LOOP;
    --get all tests, check grace period timeout and update status
    FOR studenttest IN (SELECT st.id, st.studentid, st.testid,st.testcollectionid
                FROM studentstests st INNER JOIN test t ON st.testid = t.id
                                INNER JOIN student s ON st.studentid = s.id
                                INNER JOIN operationaltestwindow otw ON otw.testcollectionid = st.testcollectionid
                WHERE st.status IN (SELECT c.id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
                                AND c.categorycode not in ('complete', 'fcunenrolled', 'fcmidunenrolled')
                                AND ct.typecode='STUDENT_TEST_STATUS')
                                AND st.activeflag = true
                                AND otw.suspendwindow = false
                                AND otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP
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
                t.testname, t.testformatcode,
                st.id as studentstestsid, st.studentid,
                st.testid, st.testcollectionid,
                st.status, st.testsessionid,
                st.activeflag, st.ticketno,true as windowOpen,
                st.createduser, st.modifieduser,
                st.modifieddate, st.createddate
                FROM studentstests st INNER JOIN test t ON st.testid = t.id
                                INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
                                INNER JOIN assessment a ON atc.assessmentid = a.id
                                INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
                                INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
                                INNER JOIN operationaltestwindow otw ON otw.testcollectionid = st.testcollectionid
                WHERE st.status IN (SELECT c.id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
                                AND c.categorycode not in ('complete', 'fcunenrolled', 'fcmidunenrolled')
                                AND ct.typecode='STUDENT_TEST_STATUS')
                                AND st.activeflag = true AND otw.suspendwindow = false
                                AND otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP
                                AND st.studentid = student_id AND tp.programname = 'Practice'
                                order by ap.programname, st.createddate, t.testname;
    ELSE
                RETURN QUERY SELECT ap.id AS assessmentprogramId,
                ap.programname AS assessmentprogramname,
                tp.id AS testingprogramid, tp.programname AS testingprogramname,
                t.testname, t.testformatcode,
                st.id as studentstestsid, st.studentid,
                st.testid, st.testcollectionid,
                st.status, st.testsessionid,
                st.activeflag, st.ticketno,true as windowOpen,
                st.createduser, st.modifieduser,
                st.modifieddate, st.createddate
                FROM studentstests st INNER JOIN test t ON st.testid = t.id
                                INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
                                INNER JOIN assessment a ON atc.assessmentid = a.id
                                INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
                                INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
                                LEFT OUTER JOIN operationaltestwindow otw ON otw.testcollectionid = st.testcollectionid
                WHERE st.status IN (SELECT c.id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
                                AND c.categorycode not in ('complete', 'fcunenrolled', 'fcmidunenrolled')
                                AND ct.typecode='STUDENT_TEST_STATUS')
                                AND st.activeflag = true AND tp.programname != 'Practice'
                                AND ((otw.effectivedate <= CURRENT_TIMESTAMP 
					AND otw.expirydate >= CURRENT_TIMESTAMP
					AND otw.suspendwindow = false ) OR t.qccomplete = false)
                                AND st.studentid = student_id order by ap.programname, st.createddate, t.testname;
    END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE  
  COST 100
  ROWS 1000;

DROP INDEX IF EXISTS idx_studentstests_test;

CREATE INDEX idx_studentstests_test ON studentstests USING btree (testid);

--301.sql - CB fortscott i1 changes
ALTER TABLE gradecourse ADD COLUMN course boolean DEFAULT false;

ALTER TABLE contentframework ALTER COLUMN gradecourseid DROP NOT NULL;
ALTER TABLE contentframework ADD COLUMN gradebandid bigint;
ALTER TABLE contentframework ADD CONSTRAINT contentframework_gradebandid_fkey FOREIGN KEY (gradebandid)
      REFERENCES gradeband(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE testcollection ADD COLUMN courseid bigint;
ALTER TABLE testcollection ADD CONSTRAINT testcollection_courseid_fkey FOREIGN KEY (courseid)
      REFERENCES gradecourse(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

CREATE TABLE testleitemusage (
  testletid bigint NOT NULL,
  itemusageid bigint NOT NULL,
  CONSTRAINT testleitemusage_pkey PRIMARY KEY (testletid, itemusageid),
  CONSTRAINT testleitemusage_itemusageid_fkey FOREIGN KEY (itemusageid)
      REFERENCES category (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT taskvariantitemusage_testletid_fkey FOREIGN KEY (testletid)
      REFERENCES testlet (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION); 
      
--add column that will contain the state course id and is linked to gradecourse with course column true
alter table roster add column statecoursesid bigint;      
alter table roster add constraint course_gradecourse_fk FOREIGN KEY (statecoursesid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;