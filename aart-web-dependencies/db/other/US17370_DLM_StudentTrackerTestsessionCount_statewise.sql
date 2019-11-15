-- Function: getstudenttrackertestsessioncount(bigint, bigint, bigint, bigint, bigint);

-- DROP FUNCTION getstudenttrackertestsessioncount(bigint, bigint, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION getstudenttrackertestsessioncount(_otwid bigint, _assessmentprogramid bigint, _schoolyear bigint, _stateid bigint, _contentareaid bigint)
  RETURNS bigint AS
$BODY$
DECLARE
	count BIGINT;
BEGIN
		
	count = (select count(*) from (SELECT distinct stb.testsessionid from studenttrackerband stb
			INNER JOIN testsession ts ON ts.id = stb.testsessionid
			INNER JOIN studenttracker st ON st.id = stb.studenttrackerid
			INNER JOIN studentassessmentprogram sap ON sap.studentid = st.studentid
			INNER JOIN enrollment e ON e.studentid = st.studentid
			INNER JOIN organizationtreedetail otd ON otd.schoolid = e.attendanceschoolid

			WHERE stb.operationalwindowid = _otwid
				AND sap.assessmentprogramid = _assessmentprogramid
				AND otd.stateid = _stateid
				AND st.contentareaid = _contentareaid 
				AND st.schoolyear = _schoolyear
				AND st.activeflag is true) as count);			

	RETURN count;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;



