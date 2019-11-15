--Changepond
CREATE OR REPLACE FUNCTION identify_breached_testforms(_statestudentidentifiers text[], _breachedtestsession text, _grade text, _stage text, _currentschoolyear bigint, OUT "StudentIdentifier" text, OUT "BreachedFormId" bigint, OUT "BreachedFormName" text, OUT "TestcollectionId" bigint, OUT "TestCollectionName" text) RETURNS SETOF record AS
$BODY$
DECLARE
	stdid bigint;
	stcount integer := array_length(_statestudentidentifiers, 1);
	ind integer := 1;
	enrollid bigint;
	testsession_id bigint;
	strecord RECORD;
BEGIN
	-- get testsessionid
	SELECT id INTO testsession_id FROM testsession WHERE name ilike _breachedtestsession and activeflag is true and stageid = (SELECT id FROM stage WHERE code LIKE _stage) and schoolyear = _currentschoolyear;
	
	IF (_statestudentidentifiers IS NOT NULL AND stcount > 0) THEN
		WHILE ind <= stcount LOOP
			 SELECT id INTO stdid FROM student WHERE statestudentidentifier iLIKE _statestudentidentifiers[ind] and activeflag is true;
			 SELECT id INTO enrollid FROM enrollment WHERE studentid = stdid and currentgradelevel in (select id from gradecourse where abbreviatedname ilike _grade) and activeflag is true;
			 FOR strecord IN (SELECT t.id as testid, t.testname as testname, tc.id as testcollectionid, tc.name as testcollectionname FROM studentstests st
				JOIN testcollection tc ON tc.id = st.testcollectionid
				JOIN test t ON t.id = st.testid WHERE st.studentid = stdid and st.activeflag is true 
				and st.status IN ( SELECT category.id FROM category JOIN public.categorytype ON category.categorytypeid = categorytype.id 
							WHERE categorytype.typecode = 'STUDENT_TEST_STATUS'
							and category.categorycode in('unused','inprogress','complete')
				and testsessionid = testsession_id and enrollmentid = enrollid))
			LOOP
				 RAISE INFO 'StudentIdentifier: % , BreachedFormId: % , BreachedFormName:% ', _statestudentidentifiers[ind], strecord.testid, strecord.testname;
				 RETURN QUERY SELECT _statestudentidentifiers[ind], strecord.testid, strecord.testname::text, strecord.testcollectionid, strecord.testcollectionname::text;
			END LOOP;
			ind = ind + 1;
		END LOOP;
	END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE STRICT
COST 100;

CREATE OR REPLACE FUNCTION update_breached_test(_statestudentidentifiers text[], _grade text, _contentarea text, _assessmentprogram text, _stage text, _state text, _breachedtestsession text, _breachedtest bigint, _clearscope text, _orgname text, _currentschoolyear bigint)
  RETURNS void AS
$BODY$
DECLARE
	stdtestid bigint;
	stdtestids bigint[];
	stcount integer := array_length(_statestudentidentifiers, 1);
	breachtestsession_id bigint;
	clear_completed_test boolean := false;
	enrollmethod text;
	nextstagetestsession bigint;
	nextstdtestid bigint;
	
BEGIN
	IF(_breachedtestsession is NULL OR _breachedtest is NULL) THEN
		RAISE NOTICE 'Please provide breached testsession and test form';
		RETURN;
	END IF;
	IF (_grade is NULL OR _contentarea is NULL OR _assessmentprogram is NULL OR _stage is NULL OR _state is NULL OR _currentschoolyear is NULL ) THEN
		RAISE NOTICE 'Please provide all the required input variables - ContenArea, AssessmentProgram, Stage, State and Current School Year';
		RETURN;
	END IF;
	-- get testsessionid, enrollmentmethod
	SELECT tm.methodcode, ts.id INTO enrollmethod, breachtestsession_id FROM testenrollmentmethod tm
		JOIN operationaltestwindow otw ON otw.testenrollmentmethodid = tm.id and otw.activeflag is true and otw.testenrollmentflag is true
		JOIN operationaltestwindowstestcollections otwtc ON otwtc.operationaltestwindowid = otw.id and otwtc.activeflag is true
		JOIN testcollection tc ON tc.id = otwtc.testcollectionid 
		JOIN testsession ts ON ts.testcollectionid = otwtc.testcollectionid and ts.activeflag is true 
		JOIN testcollectionstests tct ON tct.testcollectionid = tc.id and tct.testid = _breachedtest
	WHERE ts.name ilike _breachedtestsession and ts.stageid = (SELECT id FROM stage WHERE code LIKE _stage) and ts.schoolyear = _currentschoolyear and tc.contentareaid = (SELECT ID FROM contentarea WHERE abbreviatedname LIKE _contentarea);
	IF (breachtestsession_id IS NULL) THEN
		RAISE NOTICE 'No testsession found with the given information';
		RETURN;
	END IF;
	IF (_statestudentidentifiers IS NOT NULL AND stcount > 0) THEN
		--if specific student list is provided, completed tests also cleared
		clear_completed_test := true;
		SELECT array_agg(st.id) INTO stdtestids FROM studentstests st
			JOIN student s ON s.id = st.studentid AND s.activeflag = true
			JOIN ENROLLMENT en ON en.studentid = s.id AND en.id = st.enrollmentid AND en.activeflag is true AND en.currentschoolyear = _currentschoolyear
			JOIN organizationtreedetail OT ON OT.schoolid = En.attendanceschoolid 
			--AND OT.statedisplayidentifier = _orgname 
			AND statedisplayidentifier = _state
			WHERE en.currentgradelevel IN (select id from gradecourse where abbreviatedname ilike _grade)
				AND st.activeflag is true 
				AND st.status IN ( SELECT category.id FROM category JOIN public.categorytype ON category.categorytypeid = categorytype.id 
							WHERE categorytype.typecode = 'STUDENT_TEST_STATUS'
							and category.categorycode = ANY(CASE
								WHEN clear_completed_test THEN ARRAY['unused','inprogress','complete']
								ELSE ARRAY['unused','inprogress']
								END))
				AND st.testid = _breachedtest AND testsessionid = breachtestsession_id
				AND s.statestudentidentifier in (SELECT * FROM unnest(_statestudentidentifiers));
		--RAISE INFO 'Affected Students : %', _statestudentidentifiers;
	ELSE
		IF (_clearscope is NULL OR _orgname is NULL) THEN
			RAISE NOTICE 'Please provide the scope or Studentidentifiers to clear breached tests ';
			RETURN;
		END IF;
		
		IF (_clearscope = 'ST') THEN
			SELECT array_agg(st.id), array_agg(s.statestudentidentifier) INTO stdtestids,_statestudentidentifiers  FROM studentstests st
			JOIN student s ON s.id = st.studentid AND s.activeflag = true
			JOIN ENROLLMENT en ON en.studentid = s.id AND en.id = st.enrollmentid AND en.activeflag is true AND en.currentschoolyear = _currentschoolyear
			JOIN organizationtreedetail OT ON OT.schoolid = En.attendanceschoolid AND OT.statedisplayidentifier = _orgname AND statedisplayidentifier = _state
			WHERE en.currentgradelevel IN (select id from gradecourse where abbreviatedname ilike _grade)
				AND st.activeflag is true 
				AND st.status IN ( SELECT category.id FROM category JOIN public.categorytype ON category.categorytypeid = categorytype.id 
							WHERE categorytype.typecode = 'STUDENT_TEST_STATUS'
							and category.categorycode = ANY(CASE
								WHEN clear_completed_test THEN ARRAY['unused','inprogress','complete']
								ELSE ARRAY['unused','inprogress']
								END))
				AND st.testid = _breachedtest AND testsessionid = breachtestsession_id;
		ELSEIF (_clearscope = 'DT') THEN
			SELECT array_agg(st.id), array_agg(s.statestudentidentifier) INTO stdtestids,_statestudentidentifiers FROM studentstests st
			JOIN student s ON s.id = st.studentid AND s.activeflag = true
			JOIN ENROLLMENT en ON en.studentid = s.id AND en.id = st.enrollmentid AND en.activeflag is true AND en.currentschoolyear = _currentschoolyear
			JOIN organizationtreedetail OT ON OT.schoolid = En.attendanceschoolid AND OT.districtdisplayidentifier = _orgname AND statedisplayidentifier = _state
			WHERE en.currentgradelevel IN (select id from gradecourse where abbreviatedname ilike _grade)
				AND st.activeflag is true 
				AND st.status IN ( SELECT category.id FROM category JOIN public.categorytype ON category.categorytypeid = categorytype.id 
							WHERE categorytype.typecode = 'STUDENT_TEST_STATUS'
							and category.categorycode = ANY(CASE
								WHEN clear_completed_test THEN ARRAY['unused','inprogress','complete']
								ELSE ARRAY['unused','inprogress']
								END))
				AND st.testid = _breachedtest AND testsessionid = breachtestsession_id;
			
		ELSEIF (_clearscope = 'SCH') THEN
			SELECT array_agg(st.id), array_agg(s.statestudentidentifier) INTO stdtestids,_statestudentidentifiers FROM studentstests st
			JOIN student s ON s.id = st.studentid AND s.activeflag = true
			JOIN ENROLLMENT en ON en.studentid = s.id AND en.id = st.enrollmentid AND en.activeflag is true AND en.currentschoolyear = _currentschoolyear
			JOIN organizationtreedetail OT ON OT.schoolid = En.attendanceschoolid AND OT.schooldisplayidentifier = _orgname AND statedisplayidentifier = _state
			WHERE en.currentgradelevel IN (SELECT id FROM gradecourse where abbreviatedname ilike _grade)
				AND st.activeflag is true 
				AND st.status IN ( SELECT category.id FROM category JOIN public.categorytype ON category.categorytypeid = categorytype.id 
							WHERE categorytype.typecode = 'STUDENT_TEST_STATUS'
							and category.categorycode = ANY(CASE
								WHEN clear_completed_test THEN ARRAY['unused','inprogress','complete']
								ELSE ARRAY['unused','inprogress']
								END))
				AND st.testid = _breachedtest AND testsessionid = breachtestsession_id;
		END IF;
	END IF;
	RAISE INFO 'Affected Students : %', _statestudentidentifiers;
	IF (stdtestids IS NOT NULL AND array_length(stdtestids,1) > 0) THEN
			FOR stdtestid IN SELECT * FROM unnest(stdtestids) LOOP
				IF (enrollmethod <> 'ADP') THEN
					UPDATE studentsresponseparameters SET activeflag=false, modifieddate = now(),
						modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') WHERE studentstestsid = stdtestid;
					UPDATE studentsresponses SET activeflag=false, modifieddate = now(),
						modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') WHERE studentstestsid = stdtestid;
					UPDATE studentstestsections SET activeflag=false, modifieddate = now(),
						modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin') WHERE studentstestid = stdtestid;
					UPDATE scoringassignmentstudent SET activeflag=false WHERE studentstestsid = stdtestid;
					UPDATE studentstests SET activeflag=false, modifieddate = now(),
						modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')  WHERE id = stdtestid;
	 			ELSE -- Adaptive
					nextstdtestid := stdtestid; 
					nextstagetestsession := breachtestsession_id;
					-- This step deactivates all the next stage tests for the student
					WHILE (nextstdtestid is not null) LOOP
						--RAISE INFO 'Current Stage Inform - studentstestid : %', nextstdtestid;
						UPDATE studentsresponseparameters SET activeflag=false, modifieddate = now(),
							modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')  WHERE studentstestsid = nextstdtestid;
						UPDATE studentsresponses SET activeflag=false, modifieddate = now(),
							modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')  WHERE studentstestsid = nextstdtestid;
						UPDATE studentstestsections SET activeflag=false, modifieddate = now(),
							modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')  WHERE studentstestid = nextstdtestid;
						UPDATE scoringassignmentstudent SET activeflag=false  WHERE studentstestsid = nextstdtestid;
						UPDATE studentstests SET activeflag=false, modifieddate = now(),
							modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')  WHERE id = nextstdtestid;
						--IDENTIFY NEXT Stage test assigned or not
						SELECT nextst.id, nextst.testsessionid INTO nextstdtestid, nextstagetestsession from studentstests st
							inner join testsession ts on st.testsessionid=ts.id
							inner join testcollection tc on tc.id=ts.testcollectionid
							inner join stage s on s.predecessorid=tc.stageid
							inner join testcollection nexttc on nexttc.stageid=s.id and tc.contentareaid=nexttc.contentareaid
							inner join testsession nextts on nexttc.id=nextts.testcollectionid
							inner join studentstests nextst on nextst.testsessionid=nextts.id and st.studentid=nextst.studentid
							inner join category nextstatus on nextst.status = nextstatus.id
							WHERE nextstatus.activeflag is true
								and s.code in ('Stg2', 'Stg3')
								and nextts.activeflag is true
								and st.testsessionid =nextstagetestsession
								and st.id = nextstdtestid
								and nextst.status in (SELECT category.id FROM category JOIN public.categorytype ON category.categorytypeid = categorytype.id 
												WHERE categorytype.typecode = 'STUDENT_TEST_STATUS'
												and category.categorycode IN ('unused','inprogress','complete'));

					
												
					END LOOP;
			END IF;
		END LOOP;
	END IF;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

ALTER TABLE ccqscoreitem ADD COLUMN externalscorer bigint DEFAULT NULL;

ALTER TABLE batchupload ADD COLUMN documentid bigint DEFAULT null;

CREATE TABLE scoringuploadfile
(
  id bigserial NOT NULL,
  testid bigint NOT NULL,
  filename character varying(100),
  filepath text,
  assessmentprogramid integer,
  headercolumn json,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true
);

 CREATE INDEX studentsresponses_taskvariantid_index ON studentsresponses (taskvariantid);