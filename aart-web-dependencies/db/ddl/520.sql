 --DROP FUNCTION enrollments_count(text, bigint, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION enrollments_count(IN _assessmentprogram text, IN _stataeid bigint, IN _contentareaid bigint, IN _gradelevel bigint, IN _currentschoolyear bigint, OUT "State" text, OUT "GradeCourse" text, OUT "Subject" text, OUT "EnrollCount" bigint)
  RETURNS SETOF record AS
$BODY$
DECLARE
	selectstr text;
	wherestr text;
	result record;
BEGIN
	IF (_assessmentprogram = NULL OR _currentschoolyear = NULL) THEN
		RAISE EXCEPTION 'Please provide assessment program name and current scheool year';
	END IF;
		
	selectstr := 'SELECT (SELECT displayidentifier AS State FROM organization WHERE id =o.stateid),
				(SELECT abbreviatedname AS gradecourse FROM gradecourse WHERE id = en.currentGradeLevel),
				(SELECT abbreviatedname AS subject FROM contentarea WHERE id = cttsa.contentareaid),
				COUNT(DISTINCT en.id)  AS enrollmentcount 
			FROM enrollment en INNER JOIN student st ON st.id=en.studentid
				INNER JOIN enrollmenttesttypesubjectarea ets ON ets.enrollmentid=en.id
				INNER JOIN testtypesubjectarea ttsa ON ttsa.testtypeid=ets.testtypeid AND ttsa.subjectareaid=ets.subjectareaid 
				INNER JOIN contentareatesttypesubjectarea cttsa ON cttsa.testtypesubjectareaid=ttsa.id
				INNER JOIN organizationtreedetail o ON o.schoolid=en.attendanceschoolid ';
	wherestr := ' WHERE en.activeflag is true AND ets.activeflag is true
			AND (en.exitwithdrawaldate is null OR (en.exitwithdrawaldate < en.schoolentrydate)) and '''|| _assessmentprogram ||''' in (
				SELECT ap.abbreviatedname
				FROM assessmentprogram ap
					INNER JOIN studentassessmentprogram sap ON (sap.studentid = st.id and ap.id = sap.assessmentprogramid)
				WHERE sap.activeflag = true
	      	) and en.currentschoolyear = ' || _currentschoolyear;

	IF (_stataeid is not NULL) THEN
		wherestr := wherestr || ' AND o.stateid = ' ||_stataeid;
	END IF;
	IF (_contentareaid is not NULL) THEN
		wherestr := wherestr || ' AND cttsa.contentareaid = ' || _contentareaid;
	END IF;
	IF (_gradelevel is not NULL) THEN
		wherestr := wherestr || ' AND en.currentGradeLevel = ' || _gradelevel;
	END IF;
	
	selectstr := selectstr || wherestr || ' group by  o.stateid, cttsa.contentareaid, en.currentGradeLevel'
		|| ' order by state, gradecourse, subject';
	FOR result IN EXECUTE  selectstr LOOP
		RETURN QUERY SELECT result.State::text, result.gradecourse::text, result.subject::text, result.enrollmentcount::bigint;
	END LOOP;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
  

CREATE OR REPLACE FUNCTION studentstests_count(IN _assessmentprogram text, IN _stataeid bigint, IN _contentareaid bigint, IN _gradelevel bigint, IN _currentschoolyear bigint, OUT "State" text, OUT "GradeCourse" text, OUT "Subject" text, OUT "StudentCount" bigint)
  RETURNS SETOF record AS
$BODY$
DECLARE
	selectstr text;
	wherestr text;
	result record;
BEGIN
	IF (_assessmentprogram = NULL OR _currentschoolyear = NULL) THEN
		RAISE EXCEPTION 'Please provide assessment program name and current scheool year';
	END IF;
		
	selectstr := 'SELECT (SELECT displayidentifier AS State FROM organization WHERE id =o.stateid),
				(SELECT abbreviatedname AS gradecourse FROM gradecourse WHERE id = tc.gradecourseid),
				(SELECT abbreviatedname AS subject FROM contentarea WHERE id = tc.contentareaid),
				COUNT(distinct st.enrollmentid)  AS studentcount 
				FROM testsession ts 
					JOIN studentstests st on st.testsessionid = ts.id
					JOIN testcollection tc on tc.id = st.testcollectionid
					JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid
					JOIN enrollment en on en.id = st.enrollmentid
					JOIN organizationtreedetail o on o.schoolid=en.attendanceschoolid ';
	wherestr := ' WHERE ts.source = ''BATCHAUTO'' and otw.effectivedate <= now() and otw.expirydate > now()
				and '''|| _assessmentprogram ||''' in (
					SELECT ap.abbreviatedname
					FROM assessmentprogram ap
						INNER JOIN studentassessmentprogram sap ON (sap.studentid = st.studentid and ap.id = sap.assessmentprogramid)
					WHERE sap.activeflag = true
	      		) and en.currentschoolyear = ' || _currentschoolyear || 'and en.activeflag is true and st.activeflag is true and ts.activeflag is true';

	IF (_stataeid is not NULL) THEN
		wherestr := wherestr || ' AND o.stateid = ' ||_stataeid;
	END IF;
	IF (_contentareaid is not NULL) THEN
		wherestr := wherestr || ' AND tc.contentareaid = ' || _contentareaid;
	END IF;
	IF (_gradelevel is not NULL) THEN
		wherestr := wherestr || ' AND tc.gradecourseid = ' || _gradelevel;
	END IF;
	
	selectstr := selectstr || wherestr || ' group by o.stateid,tc.gradecourseid ,tc.contentareaid'
		|| ' order by state, gradecourse, subject';
	FOR result IN EXECUTE  selectstr LOOP
		RETURN QUERY SELECT result.State::text, result.gradecourse::text, result.subject::text, result.studentcount::bigint;
	END LOOP;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;




