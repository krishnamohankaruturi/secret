-- ddl/583.sql

--ddl/579.sql

ALTER TABLE ONLY fieldspecification
    DROP CONSTRAINT uk_field_spec; 
    
ALTER TABLE ONLY fieldspecification
    ADD CONSTRAINT uk_field_spec UNIQUE (fieldname, mappedname, activeflag);

--ddl/581.sql

ALTER TABLE test ADD COLUMN is_interim_test boolean DEFAULT false;
ALTER TABLE testcollection ADD COLUMN activeflag boolean DEFAULT true;
alter table testsession add column windoweffectivedate timestamp with time zone;
alter table testsession add column windowstartdate timestamp with time zone;
ALTER TABLE testsession ADD COLUMN windowstarttime time with time zone;
ALTER TABLE testsession ADD COLUMN windowendtime time with time zone;


CREATE SEQUENCE interimtest_id_seq;


CREATE TABLE interimtest
(
  id bigint NOT NULL DEFAULT nextval('interimtest_id_seq'::regclass),
  name character varying(75),
  description character varying(300),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  gradecourseid bigint,
  contentareaid bigint,
  createduser integer,
  activeflag boolean DEFAULT true,
  modifieduser integer,
  testtestid bigint,
  testcollectionid bigint,
  istestassigned boolean default false,
  CONSTRAINT interimtest_pkey PRIMARY KEY (id),
  CONSTRAINT interimtest_contentareaid_fkey FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT interimtest_gradecourseid_fkey FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE SEQUENCE interimtesttest_id_seq;

CREATE TABLE interimtesttest
(
  id bigint NOT NULL DEFAULT nextval('interimtesttest_id_seq'::regclass),
  interimtestid bigint,
  testid bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  testtype character varying(75),
  createduser integer,
  activeflag boolean DEFAULT true,
  modifieduser integer,
  CONSTRAINT interimtesttest_pkey PRIMARY KEY (id),
  CONSTRAINT interimtesttest_interimtestid_fkey FOREIGN KEY (interimtestid)
      REFERENCES interimtest (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT interimtesttest_testid_fkey FOREIGN KEY (testid)
      REFERENCES test (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


create table interimgroup(
  id  SERIAL NOT NULL,
  groupname character varying(50) NOT NULL, 
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser integer,
  activeflag boolean,
  modifieduser integer,
  CONSTRAINT interimgroup_pkey PRIMARY KEY (id)
);

create table interimgroupstudent(
id SERIAL NOT NULL,
studentid bigint NOT NULL,
interimgroupid integer,
createduser integer, 
createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
activeflag boolean,
CONSTRAINT interimgroupstudent_pkey PRIMARY KEY (id),
 CONSTRAINT fk_interimgroupstudent_interimgroupid FOREIGN KEY (interimgroupid)
      REFERENCES interimgroup (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DO
$BODY$
BEGIN
	IF EXISTS (
		SELECT column_name 
		FROM information_schema.columns 
		WHERE table_name='profileitemattribute' and column_name='nonselectedvalue'
	) THEN
		RAISE NOTICE 'nonselectedvalue found in profileitemattribute';
	ELSE
		ALTER TABLE profileitemattribute ADD COLUMN nonselectedvalue TEXT;
END IF;
END
$BODY$;

-- ddl/584.sql

DROP FUNCTION IF EXISTS updatesectionstatusandgetteststatus(bigint, bigint, text, text, text, double precision);

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

DROP FUNCTION IF EXISTS updatesectionstatusandgetteststatusforlcs(bigint, bigint, text, text, text, double precision, timestamp with time zone, timestamp with time zone, timestamp with time zone, timestamp with time zone);

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
			update public.studentstestsections set startdatetime = in_stdtestsecstartdatetime,enddatetime=null where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
		ELSE 
			update public.studentstestsections set startdatetime = in_stdtestsecstartdatetime where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
		END IF;
	END IF;
	IF in_stdtestsecenddatetime IS NOT NULL AND in_categorycode = 'complete' THEN
		update public.studentstestsections set enddatetime = in_stdtestsecenddatetime where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
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
			update public.studentstests set startdatetime = in_stdteststartdatetime,enddatetime=null where id = in_studenttestid; 
		ELSE 
			update public.studentstests set startdatetime = in_stdteststartdatetime where id = in_studenttestid; 
		END IF;
	END IF;
	IF in_stdtestenddatetime IS NOT NULL AND iscomplete = true THEN
		update public.studentstests set enddatetime = in_stdtestenddatetime where id = in_studenttestid; 
	END IF;	
	
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

DROP FUNCTION IF EXISTS updatestudentteststatuscompleteforlcs(bigint);

CREATE OR REPLACE FUNCTION updatestudentteststatuscompleteforlcs(in_studenttestid bigint)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
    trackerid bigint;
    testunusedstatusid bigint;
    studenttest_status varchar;
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

		update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'), 
				modifieddate=now() where id = in_studenttestid;
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

	END IF;

	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- Function: randomizestudenttest(text, text)
DROP FUNCTION randomizestudenttest(text, text);

CREATE OR REPLACE FUNCTION randomizestudenttest(IN in_username text, IN in_testtypename text)
  RETURNS TABLE(assessmentprogramid bigint, assessmentprogramname character varying, testingprogramid bigint, testingprogramname character varying, contentarea character varying, gradecourse character varying, testname character varying, testformatcode character varying, studentstestsid bigint, studentid bigint, testid bigint, testcollectionid bigint, status character varying, testsessionid bigint, activeflag boolean, ticketno character varying, windowopen boolean, createduser integer, modifieduser integer, modifieddate timestamp without time zone, createddate timestamp without time zone, gracetimedoutsectioncount bigint, schooldisplayidentifier character varying) AS
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
    FOR studenttest IN (SELECT st.id, st.studentid, st.testid,st.testcollectionid,ts.operationaltestwindowid,
                            (select count(id) from studentstestsections where studentstestid = st.id and statusid = timedout_status_id) as gracestatuscount
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
                IF grace_period IS NOT NULL and studenttest.gracestatuscount = 0 THEN
      FOR studenttestsection IN (select sts.id, sts.statusid, (EXTRACT(EPOCH FROM sts.modifieddate)+(grace_period*60)) as sectionmodifiedmillis from studentstestsections sts where sts.studentstestid=studenttest.id
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
  st.modifieddate, st.createddate,
  (select count(id) from studentstestsections where studentstestid = st.id and statusid = timedout_status_id) as gracetimedoutsectioncount,
  attsch.displayidentifier as schooldisplayidentifier
  FROM studentstests st INNER JOIN test t ON st.testid = t.id
      INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
      INNER JOIN assessment a ON atc.assessmentid = a.id
      INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
      INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
      INNER JOIN testsession ts on ts.id=st.testsessionid
      INNER JOIN testcollection tc on tc.id = st.testcollectionid
      INNER JOIN contentarea ca on ca.id = tc.contentareaid
      INNER JOIN category ON category.id = st.status
      INNER JOIN enrollment en ON en.id = st.enrollmentid
      INNER JOIN organization attsch ON attsch.id = en.attendanceschoolid
      INNER JOIN operationaltestwindow otw ON otw.id = ts.operationaltestwindowid
  WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
      AND st.activeflag = true 
      AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false AND t.is_interim_test is false) 
        OR (t.qccomplete IS false and otw.effectivedate IS NULL AND otw.expirydate IS NULL)
        OR (t.is_interim_test IS true and ts.windowexpirydate>=CURRENT_TIMESTAMP and ts.windoweffectivedate<=CURRENT_TIMESTAMP
		and ts.windowstarttime<=CURRENT_TIME and ts.windowendtime>=CURRENT_TIME
		and otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false)
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
  st.modifieddate, st.createddate,
  (select count(id) from studentstestsections where studentstestid = st.id and statusid = timedout_status_id) as gracetimedoutsectioncount,
  attsch.displayidentifier as schooldisplayidentifier
  FROM studentstests st INNER JOIN test t ON st.testid = t.id
      INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
      INNER JOIN assessment a ON atc.assessmentid = a.id
      INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
      INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
      INNER JOIN testsession ts on ts.id=st.testsessionid
      INNER JOIN testcollection tc on tc.id = st.testcollectionid
      INNER JOIN contentarea ca on ca.id = tc.contentareaid
      INNER JOIN category ON category.id = st.status
      INNER JOIN enrollment en ON en.id = st.enrollmentid
      INNER JOIN organization attsch ON attsch.id = en.attendanceschoolid
      LEFT OUTER JOIN operationaltestwindow otw ON otw.id = ts.operationaltestwindowid
  WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
      AND st.activeflag = true AND tp.programabbr != 'P'
      AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false AND t.is_interim_test is false) 
        OR (t.qccomplete IS false and otw.effectivedate IS NULL AND otw.expirydate IS NULL)
        OR (t.is_interim_test IS true and ts.windowexpirydate>=CURRENT_TIMESTAMP and ts.windoweffectivedate<=CURRENT_TIMESTAMP
		and ts.windowstarttime<=CURRENT_TIME and ts.windowendtime>=CURRENT_TIME
		and otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false)
        OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP))
      AND st.studentid = student_id
     order by ap.programname, st.createddate, t.testname;
    END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;


ALTER TABLE rubriccategory ADD COLUMN rubricscoreweight bigint;
ALTER TABLE interimtest add column schoolname varchar;

-- ddl/585.sql
CREATE TABLE userfeedback
(
  id bigserial NOT NULL,
  aartuserid bigint,
  username character varying(160) NOT NULL,
  email character varying(254) NOT NULL,
  feedback character varying(1024) NOT NULL,
  webpage character varying(80) NOT NULL,
  activeflag boolean DEFAULT true,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser bigint,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser bigint,
  CONSTRAINT pk_userfeedbacks PRIMARY KEY (id),
  CONSTRAINT userfeedbacks_aartuserid_fk FOREIGN KEY (aartuserid)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

alter table interimtest add column istestcopied boolean default false;


DROP FUNCTION randomizestudenttest(text, text);

CREATE OR REPLACE FUNCTION randomizestudenttest(IN in_username text, IN in_testtypename text)
  RETURNS TABLE(assessmentprogramid bigint, assessmentprogramname character varying, testingprogramid bigint, testingprogramname character varying, contentarea character varying, gradecourse character varying, testname character varying, testformatcode character varying, studentstestsid bigint, studentid bigint, testid bigint, testcollectionid bigint, status character varying, testsessionid bigint, activeflag boolean, ticketno character varying, windowopen boolean, createduser integer, modifieduser integer, modifieddate timestamp without time zone, createddate timestamp without time zone, gracetimedoutsectioncount bigint, schooldisplayidentifier character varying) AS
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
    FOR studenttest IN (SELECT st.id, st.studentid, st.testid,st.testcollectionid,ts.operationaltestwindowid,
                            (select count(id) from studentstestsections where studentstestid = st.id and statusid = timedout_status_id) as gracestatuscount
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
                IF grace_period IS NOT NULL and studenttest.gracestatuscount = 0 THEN
      FOR studenttestsection IN (select sts.id, sts.statusid, (EXTRACT(EPOCH FROM sts.modifieddate)+(grace_period*60)) as sectionmodifiedmillis from studentstestsections sts where sts.studentstestid=studenttest.id
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
  st.modifieddate, st.createddate,
  (select count(id) from studentstestsections where studentstestid = st.id and statusid = timedout_status_id) as gracetimedoutsectioncount,
  attsch.displayidentifier as schooldisplayidentifier
  FROM studentstests st INNER JOIN test t ON st.testid = t.id
      INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
      INNER JOIN assessment a ON atc.assessmentid = a.id
      INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
      INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
      INNER JOIN testsession ts on ts.id=st.testsessionid
      INNER JOIN testcollection tc on tc.id = st.testcollectionid
      INNER JOIN contentarea ca on ca.id = tc.contentareaid
      INNER JOIN category ON category.id = st.status
      INNER JOIN enrollment en ON en.id = st.enrollmentid
      INNER JOIN organization attsch ON attsch.id = en.attendanceschoolid
      INNER JOIN operationaltestwindow otw ON otw.id = ts.operationaltestwindowid
  WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
      AND st.activeflag = true 
      AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false AND t.is_interim_test is false) 
        OR (t.qccomplete IS false and otw.effectivedate IS NULL AND otw.expirydate IS NULL AND t.is_interim_test is false)
        OR (t.is_interim_test IS true and date(ts.windowexpirydate)>=CURRENT_DATE and date(ts.windoweffectivedate)<=CURRENT_DATE
	      	and (ts.windowstarttime::TIME WITHOUT TIME ZONE)<=(CURRENT_TIME AT TIME ZONE 'US/Central')::TIME WITHOUT TIME ZONE 
	      	and (ts.windowendtime::TIME WITHOUT TIME ZONE)>=(CURRENT_TIME AT TIME ZONE 'US/Central')::TIME WITHOUT TIME ZONE
      		and otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false)
        OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP AND t.is_interim_test is false))
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
  st.modifieddate, st.createddate,
  (select count(id) from studentstestsections where studentstestid = st.id and statusid = timedout_status_id) as gracetimedoutsectioncount,
  attsch.displayidentifier as schooldisplayidentifier
  FROM studentstests st INNER JOIN test t ON st.testid = t.id
      INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
      INNER JOIN assessment a ON atc.assessmentid = a.id
      INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
      INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
      INNER JOIN testsession ts on ts.id=st.testsessionid
      INNER JOIN testcollection tc on tc.id = st.testcollectionid
      INNER JOIN contentarea ca on ca.id = tc.contentareaid
      INNER JOIN category ON category.id = st.status
      INNER JOIN enrollment en ON en.id = st.enrollmentid
      INNER JOIN organization attsch ON attsch.id = en.attendanceschoolid
      LEFT OUTER JOIN operationaltestwindow otw ON otw.id = ts.operationaltestwindowid
  WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
      AND st.activeflag = true AND tp.programabbr != 'P'
      AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false AND t.is_interim_test is false) 
              OR (t.qccomplete IS false and otw.effectivedate IS NULL AND otw.expirydate IS NULL AND t.is_interim_test is false)
              OR (t.is_interim_test IS true and date(ts.windowexpirydate)>=CURRENT_DATE and date(ts.windoweffectivedate)<=CURRENT_DATE
      		and (ts.windowstarttime::TIME WITHOUT TIME ZONE)<=(CURRENT_TIME AT TIME ZONE 'US/Central')::TIME WITHOUT TIME ZONE 
      		and (ts.windowendtime::TIME WITHOUT TIME ZONE)>=(CURRENT_TIME AT TIME ZONE 'US/Central')::TIME WITHOUT TIME ZONE
      		and otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false)
        OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP AND t.is_interim_test is false))
      AND st.studentid = student_id
     order by ap.programname, st.createddate, t.testname;
    END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
