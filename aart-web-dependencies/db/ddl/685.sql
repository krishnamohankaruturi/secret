--ddl -- KELPA2 name changes from TDE
-- Function: autoreactivatestudentstests(text)

-- DROP FUNCTION autoreactivatestudentstests(text);

CREATE OR REPLACE FUNCTION autoreactivatestudentstests(in_username text)
  RETURNS void AS
$BODY$

DECLARE

STUDENTTEST_ROW RECORD;
test_inprogress_status_id bigint;
reactivate_status_id bigint;

BEGIN

SELECT c.id into test_inprogress_status_id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
  AND c.categorycode = 'inprogress' AND ct.typecode='STUDENT_TEST_STATUS';
select into reactivate_status_id category.id from public.category 
  JOIN public.categorytype ON category.categorytypeid = categorytype.id
  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
  and category.categorycode = 'reactivated';

                                  
FOR STUDENTTEST_ROW IN
(select distinct st.id as studentstestsid, sts.id as studentstestsectionid,ts.operationaltestwindowid, ap.abbreviatedname as assessmentprogramname
 from studentstests st
 inner join studentstestsections sts on sts.studentstestid = st.id
 inner join student std on std.id = st.studentid
inner join testsession ts on ts.id = st.testsessionid
left join operationaltestwindow otw on otw.id = ts.operationaltestwindowid 
AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false) 
OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP))
INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
INNER JOIN assessment a ON atc.assessmentid = a.id
INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
inner join category cat on cat.id = st.status
where std.username=in_username and ap.abbreviatedname in ('KAP','KELPA2') and st.activeflag=true and cat.categorycode='inprogresstimedout')

LOOP

update public.studentstestsections set statusId = reactivate_status_id, modifieduser = 13, modifieddate = now(), startdatetime = null, enddatetime = null where id=STUDENTTEST_ROW.studentstestsectionid;
update public.studentstests set status = test_inprogress_status_id, modifieduser = 13, modifieddate = now(), enddatetime = null where id=STUDENTTEST_ROW.studentstestsid;
insert into studentstestshistory (studentstestsid, studentstestsstatusid, action, acteduser, acteddate)
    values (STUDENTTEST_ROW.studentstestsid, STUDENTTEST_ROW.studentstestsectionid, 'SYSTEM_REACTIVATION', 13, now());

END LOOP;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;




-- Function: randomizestudenttest(text, text)

-- DROP FUNCTION randomizestudenttest(text, text);

CREATE OR REPLACE FUNCTION randomizestudenttest(
    IN in_username text,
    IN in_testtypename text)
  RETURNS TABLE(assessmentprogramid bigint, assessmentprogramname character varying, testingprogramid bigint, testingprogramname character varying, contentarea character varying, gradecourse character varying, testname character varying, testformatcode character varying, studentstestsid bigint, studentid bigint, testid bigint, testcollectionid bigint, status character varying, testsessionid bigint, activeflag boolean, ticketno character varying, windowopen boolean, createduser integer, modifieduser integer, modifieddate timestamp with time zone, createddate timestamp with time zone, schooldisplayidentifier character varying) AS
$BODY$

DECLARE

STUDENTTEST_ROW RECORD;
studenttestid bigint;
randomized_testid bigint;
randomized_testname text;
test_testsections RECORD;
timedout_status_id bigint;
test_timedout_status_id bigint;
test_inprogress_status_id bigint;
test_unused_status_id bigint;
status_id bigint;
deployed_status_id bigint;
grace_period bigint;
current_millis decimal;
current_millis_tocheck decimal;
lastmodifieddatemillis decimal;
lastactivemillis decimal;
inprogress_status_id bigint;
STUDENTTESTSECTION_ROW RECORD;
reactivate_status_id bigint;

BEGIN
select into timedout_status_id category.id from public.category 
	JOIN public.categorytype ON category.categorytypeid = categorytype.id
	where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
	and category.categorycode = 'inprogresstimedout';
select into test_timedout_status_id category.id from public.category 
	JOIN public.categorytype ON category.categorytypeid = categorytype.id
	where categorytype.typecode = 'STUDENT_TEST_STATUS'
	and category.categorycode = 'inprogresstimedout';
select into inprogress_status_id category.id from public.category 
	JOIN public.categorytype ON category.categorytypeid = categorytype.id
	where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
	and category.categorycode = 'inprogress';
select into status_id category.id from public.category 
	JOIN public.categorytype ON category.categorytypeid = categorytype.id
	where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
	and category.categorycode = 'unused';
SELECT c.id into test_unused_status_id FROM category c, categorytype ct 
	WHERE c.categorytypeid = ct.id AND c.categorycode = 'unused' AND ct.typecode='STUDENT_TEST_STATUS';
SELECT c.id into test_inprogress_status_id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
        AND c.categorycode = 'inprogress' AND ct.typecode='STUDENT_TEST_STATUS';
select category.id into deployed_status_id from category, categorytype where category.categorytypeid = categorytype.id 
	and categorytype.typecode = 'TESTSTATUS' and category.categorycode = 'DEPLOYED';
select into reactivate_status_id category.id from public.category 
	JOIN public.categorytype ON category.categorytypeid = categorytype.id
	where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
  	and category.categorycode = 'reactivated';


FOR studenttestid IN (select sts.id from studentstests sts,student std where sts.testid is null
and sts.studentid = std.id and std.username=in_username and sts.activeflag is true
and sts.status IN (test_unused_status_id, test_inprogress_status_id))

LOOP
    select into randomized_testid, randomized_testname  test.id, test.testname
                from (select * from studentstests stt join testcollection tc on tc.id = stt.testcollectionid where stt.id = studenttestid ) studenttesttestcollection
                join testcollectionstests tct on tct.testcollectionid = studenttesttestcollection.testcollectionid
                join test on test.id = tct.testid
                where not exists (select distinct st.testid from studentstests st where st.studentid = studenttesttestcollection.studentid 
          and st.testcollectionid = studenttesttestcollection.testcollectionid and st.testid = test.id and st.activeflag=true
          and st.status IN (test_unused_status_id, test_inprogress_status_id))
                and test.status = deployed_status_id
                and test.qccomplete = true ORDER BY random() LIMIT 1;
  IF randomized_testid is not null THEN
      update public.studentstests set testid = randomized_testid, modifieddate=now() where id = studenttestid;
 
      FOR test_testsections IN (select testsection.id from public.testsection where testsection.testid = randomized_testid)
      LOOP
        insert into public.studentstestsections(studentstestid, testsectionid, statusid) values(studenttestid, test_testsections.id, status_id);
      END LOOP;
  END IF;
END LOOP;

                                  
FOR STUDENTTEST_ROW IN
(select st.id,ts.operationaltestwindowid, ap.abbreviatedname as assessmentprogramname,
(select count(id) from studentstestsections where studentstestid = st.id and statusid = timedout_status_id) as gracestatuscount
 from studentstests st
inner join student std on std.id = st.studentid
inner join testsession ts on ts.id = st.testsessionid
inner join operationaltestwindow otw on otw.id = ts.operationaltestwindowid
inner join assessmentprogram ap on ap.id = otw.assessmentprogramid
where st.status IN (test_unused_status_id, test_inprogress_status_id) and st.activeflag=true 
AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false) 
OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP)) AND std.username = in_username)


LOOP

SELECT graceperiod into grace_period FROM operationaltestwindowsessionrule otwsr, category sessionrule,categorytype catType
WHERE otwsr.sessionruleid = sessionrule.id AND sessionrule.categorytypeid=cattype.id
AND sessionrule.categorycode='GRACE_PERIOD' AND cattype.typecode='SESSION_RULES'
AND otwsr.operationaltestwindowid = STUDENTTEST_ROW.operationaltestwindowid and otwsr.activeflag=true;

	IF grace_period IS NOT NULL and STUDENTTEST_ROW.gracestatuscount = 0 THEN
	
	      FOR STUDENTTESTSECTION_ROW IN (select sts.id, sts.statusid, sts.studentstestid, 
	      (EXTRACT(EPOCH FROM sts.modifieddate)+(grace_period*60)) as sectionmodifiedmillis 
	      from studentstestsections sts 
	      where sts.studentstestid=STUDENTTEST_ROW.id AND sts.statusid=inprogress_status_id)
	      
	      LOOP
	      
		SELECT EXTRACT(EPOCH FROM current_timestamp) INTO current_millis;
		IF (STUDENTTESTSECTION_ROW.sectionmodifiedmillis < current_millis) THEN
			IF (STUDENTTEST_ROW.assessmentprogramname = 'KAP' OR STUDENTTEST_ROW.assessmentprogramname = 'KELPA2') THEN
				update public.studentstestsections set statusId = reactivate_status_id, modifieduser = 13, modifieddate = now(), startdatetime = null, enddatetime = null where id=STUDENTTESTSECTION_ROW.id;
				update public.studentstests set status = test_inprogress_status_id, modifieduser = 13, modifieddate = now(), enddatetime = null where id=STUDENTTESTSECTION_ROW.studentstestid;
				insert into studentstestshistory (studentstestsid, studentstestsstatusid, action, acteduser, acteddate)
				    values (STUDENTTESTSECTION_ROW.studentstestid, STUDENTTESTSECTION_ROW.id, 'SYSTEM_REACTIVATION', 13, now());
			ELSE 
				update public.studentstestsections set statusid = timedout_status_id, modifieddate=now() where id = STUDENTTESTSECTION_ROW.id;
				update public.studentstests set status = test_timedout_status_id, modifieddate=now() where id = STUDENTTESTSECTION_ROW.studentstestid;
			END IF;
		END IF;
	      
	      END LOOP;

	END IF;
	
	IF (STUDENTTEST_ROW.assessmentprogramname = 'DLM') THEN  
		FOR STUDENTTESTSECTION_ROW IN 
		(SELECT sts.id, sts.statusid, (EXTRACT(EPOCH FROM sts.lastactive)+(90*60)) AS lastactivemillis 
		FROM studentstestsections sts where sts.studentstestid = STUDENTTEST_ROW.id AND sts.statusid=inprogress_status_id)
	        LOOP
			SELECT EXTRACT(EPOCH FROM current_timestamp) INTO current_millis_tocheck;
			SELECT (EXTRACT(EPOCH FROM MAX(sr.modifieddate))+(90*60)) INTO lastmodifieddatemillis 
			FROM studentsresponses AS sr WHERE sr.studentstestsectionsid = STUDENTTESTSECTION_ROW.id;
			IF ((lastmodifieddatemillis IS NULL OR current_millis_tocheck > lastmodifieddatemillis) 
			    AND current_millis_tocheck > STUDENTTESTSECTION_ROW.lastactivemillis) THEN
				update public.studentsresponses set activeflag = false, modifieddate=now() where studentstestsectionsid = STUDENTTESTSECTION_ROW.id;
				update public.studentstestsections set statusid = status_id, modifieddate=now() where id = STUDENTTESTSECTION_ROW.id;
				update public.studentstests set status = test_unused_status_id, modifieddate=now() where id = STUDENTTEST_ROW.id;
			END IF;
		END LOOP;
	END IF;

END LOOP;

PERFORM (select * from autoreactivatestudentstests(in_username));

  --return all tests
    IF in_testtypename ilike 'Practice'  THEN
  RETURN QUERY SELECT ap.id AS assessmentprogramId,
  ap.programname AS assessmentprogramname,
 -- cast('Practice' as character varying) AS assessmentprogramname,
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
  attsch.displayidentifier as schooldisplayidentifier
  FROM studentstests st INNER JOIN test t ON st.testid = t.id
      INNER JOIN student std on std.id = st.studentid
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
      AND (
      		(otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false AND ts.suspend IS false) 
              OR (t.qccomplete IS false and otw.effectivedate IS NULL AND otw.expirydate IS NULL AND t.is_interim_test IS false)
        	  OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP AND t.is_interim_test IS false)
         )
      AND std.username = in_username AND tp.programabbr = 'P'
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
  attsch.displayidentifier as schooldisplayidentifier
  FROM studentstests st INNER JOIN test t ON st.testid = t.id
      INNER JOIN student std on std.id = st.studentid
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
      AND (
      		(otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false AND ts.suspend IS false) 
              OR (t.qccomplete IS false and otw.effectivedate IS NULL AND otw.expirydate IS NULL AND t.is_interim_test IS false)
        	  OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP AND t.is_interim_test IS false)
         )
      AND std.username = in_username
     order by ap.programname, st.createddate, t.testname;
    END IF;
    
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

-- script from DBA team
drop table if exists taskvariantpossiblescoresjson;
Create table taskvariantpossiblescoresjson(
id bigserial not null,
externaltaskvariantid bigint,
numberofpossiblescores_count integer,
numberofpossiblescores text,
max_score integer,
scoring_type character varying(75),
createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
createduser integer NOT NULL,
modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
modifieduser integer NOT NULL,
CONSTRAINT taskvariantpossiblescoresjson_pkey PRIMARY KEY (id))
WITH (
  OIDS=FALSE
);

COMMENT ON Table taskvariantpossiblescoresjson IS 'KAP SQLite using this table for parsing JSON scoring data values.';

CREATE INDEX idx_taskvariantpossiblescoresjson_id
  ON taskvariantpossiblescoresjson USING btree (externaltaskvariantid);