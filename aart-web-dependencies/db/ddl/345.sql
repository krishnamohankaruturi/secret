--345.sql
DROP TABLE proportionmetrics;

CREATE TABLE proportionmetrics
(
  id bigint NOT NULL DEFAULT nextval('proportionmetrics_id_seq'::regclass),
  contentareaid bigint,
  gradecourseid bigint,
  gradebandid bigint,
  linkagelevelid bigint,
  essentialelementid bigint,
  essentialelement character varying(40),
  linkagelevelabbr character varying(75),
  proportionlow numeric(10,9),
  proportionhigh numeric(10,9),
  createduser integer,
  createddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer,
  modifieddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp without time zone,
  activeflag boolean DEFAULT true);
  
ALTER TABLE proportionmetrics ADD CONSTRAINT proportionmetrics_pkey PRIMARY KEY(id);
ALTER TABLE proportionmetrics ADD CONSTRAINT proportionmetrics_contentareaid_fkey FOREIGN KEY (contentareaid) REFERENCES contentarea (id);
ALTER TABLE proportionmetrics ADD CONSTRAINT proportionmetrics_gradecourseid_fkey FOREIGN KEY (gradecourseid) REFERENCES gradecourse (id);
ALTER TABLE proportionmetrics ADD CONSTRAINT proportionmetrics_gradebandid_fkey FOREIGN KEY (gradebandid) REFERENCES gradeband (id);
ALTER TABLE proportionmetrics ADD CONSTRAINT proportionmetrics_linkagelevelid_fkey FOREIGN KEY (linkagelevelid) REFERENCES category (id);
ALTER TABLE proportionmetrics ADD CONSTRAINT proportionmetrics_essentialelementid_fkey FOREIGN KEY (essentialelementid) REFERENCES contentframeworkdetail (id);

CREATE INDEX idx_proportionmetrics_contentareaid ON proportionmetrics USING btree (contentareaid);
CREATE INDEX idx_proportionmetrics_gradecourseid ON proportionmetrics USING btree (gradecourseid);
CREATE INDEX idx_proportionmetrics_gradebandid ON proportionmetrics USING btree (gradebandid);
CREATE INDEX idx_proportionmetrics_linkagelevelid ON proportionmetrics USING btree (linkagelevelid);
CREATE INDEX idx_proportionmetrics_essentialelementid ON proportionmetrics USING btree (essentialelementid);

--st
CREATE SEQUENCE complexityband_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 232
  CACHE 1;

CREATE INDEX idx_essentialelementlinkagetranslationvalues_tvalue ON essentialelementlinkagetranslationvalues USING btree (translationvalue);
CREATE INDEX idx_essentialelementlinkagetranslationvalues_category ON essentialelementlinkagetranslationvalues USING btree (categoryid);

CREATE INDEX idx_studentsurveyresponse_survey_response ON studentsurveyresponse USING btree (surveyid, surveyresponseid);

CREATE INDEX idx_usersecurityagreement_aartuser ON usersecurityagreement USING btree (aartuserid);
CREATE INDEX idx_usersecurityagreement_assessmentprogram ON usersecurityagreement USING btree (assessmentprogramid);

CREATE INDEX idx_batchregistrationreason_brid ON batchregistrationreason USING btree (batchregistrationid);
CREATE INDEX idx_batchregistrationreason_studentid ON batchregistrationreason USING btree (studentid);

ALTER TABLE batchregistration ADD COLUMN batchtypecode character varying(20);
ALTER TABLE batchregistration ALTER COLUMN batchtypecode SET DEFAULT 'BATCHAUTO'::character varying;
update batchregistration set batchtypecode='BATCHAUTO';
ALTER TABLE batchregistration ALTER COLUMN batchtypecode SET NOT NULL;

ALTER TABLE batchregistrationreason DROP COLUMN firstname;
ALTER TABLE batchregistrationreason DROP COLUMN lastname;
ALTER TABLE batchregistrationreason ALTER COLUMN batchregistrationid DROP DEFAULT;
ALTER TABLE batchregistrationreason ALTER COLUMN batchregistrationid TYPE bigint;
ALTER TABLE batchregisteredtestsessions ALTER COLUMN batchregistrationid DROP DEFAULT;
ALTER TABLE batchregisteredtestsessions ALTER COLUMN batchregistrationid TYPE bigint;

DROP SEQUENCE batchregistrationreason_batchregistrationid_seq;
DROP SEQUENCE batchregisteredtestsessions_batchregistrationid_seq;

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
                and (test.status is null or test.status = deployed_status_id)
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
                FROM studentstests st INNER JOIN test t ON st.testid = t.id
                                INNER JOIN student s ON st.studentid = s.id
                                INNER JOIN operationaltestwindow otw ON otw.testcollectionid = st.testcollectionid
                WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
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
	WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
			AND st.activeflag = true 
			AND ((otw.effectivedate <= CURRENT_TIMESTAMP 
				AND otw.expirydate >= CURRENT_TIMESTAMP
				AND otw.suspendwindow IS false ) 
				OR (t.qccomplete IS false and otw.effectivedate IS NULL
					AND otw.expirydate IS NULL))
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
	WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
			AND st.activeflag = true AND tp.programname != 'Practice'
			AND ((otw.effectivedate <= CURRENT_TIMESTAMP 
				AND otw.expirydate >= CURRENT_TIMESTAMP
				AND otw.suspendwindow IS false ) 
				OR (t.qccomplete IS false and otw.effectivedate IS NULL
					AND otw.expirydate IS NULL))
			AND st.studentid = student_id order by ap.programname, st.createddate, t.testname;
    END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
  