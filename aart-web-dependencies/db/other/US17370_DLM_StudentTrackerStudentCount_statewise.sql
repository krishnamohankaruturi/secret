-- Function: getstudenttrackercount(bigint, bigint, bigint, bigint, bigint);

-- DROP FUNCTION getstudenttrackercount(bigint, bigint, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION getstudenttrackercount(_otwid bigint, _assessmentprogramid bigint, _schoolyear bigint, _stateid bigint, _contentareaid bigint)
  RETURNS bigint AS
$BODY$
DECLARE
	count BIGINT;
BEGIN
		
	count = (SELECT count(*) from(SELECT DISTINCT e.studentid		
			FROM student st INNER JOIN enrollment e ON st.id=e.studentid 
				INNER JOIN enrollmentsrosters er ON er.enrollmentid=e.id
				INNER JOIN roster r ON er.rosterid=r.id
				INNER JOIN gradecourse gc ON gc.id=e.currentgradelevel
				INNER JOIN studentassessmentprogram sap ON st.id = sap.studentid
				INNER JOIN organizationtreedetail otd ON e.attendanceschoolid=otd.schoolid		
				LEFT JOIN  operationaltestwindowstudent otws ON (otws.studentid = st.id 
									AND  r.statesubjectareaid = otws.contentareaid 
									AND ( r.statecoursesid = otws.courseid  or otws.courseid is null)
									AND otws.operationaltestwindowid = _otwid)                                                                                                                                                                                                                                        
			WHERE er.activeflag is true AND st.activeflag is true 
			AND e.activeflag is true  
			AND r.activeflag is true 
			AND sap.activeflag = true
			AND sap.assessmentprogramid = _assessmentprogramid
			AND e.currentschoolyear = _schoolyear 
			AND (e.exitwithdrawaldate is null or (e.exitwithdrawaldate < e.schoolentrydate))			
			AND otd.stateid = _stateid
			AND (r.statesubjectareaid = _contentareaid
			AND r.currentschoolyear = _schoolyear)) AS count);			
		

	RETURN count;
	END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



