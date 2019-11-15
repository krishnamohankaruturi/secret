/*
Table traversal scheme:

assessmentprogram
-> testingprogram
-> assessment
-> assessmentstestcollections
-> testcollection
-> testcollectionstests
-> test
-> studentstests
*/

ALTER TABLE studentstests ADD COLUMN completionreason text;

UPDATE studentstests
SET modifieddate = NOW(),
	modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	status =
		(SELECT id FROM category WHERE categorycode = 'complete' AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'STUDENT_TEST_STATUS')),
	completionreason = 'Forced close at end of window'
WHERE id IN (
	SELECT st.id
	FROM assessmentprogram ap
		INNER JOIN testingprogram tp
			ON ap.id = tp.assessmentprogramid
			AND ap.abbreviatedname IN ('KAP', 'AMP') AND tp.programabbr = 'S' AND tp.highstake = TRUE
		INNER JOIN assessment a
			ON tp.id = a.testingprogramid AND a.activeflag = TRUE
		INNER JOIN assessmentstestcollections atc
			ON a.id = atc.assessmentid AND atc.activeflag = TRUE
		INNER JOIN testcollection tc
			ON atc.testcollectionid = tc.id
		INNER JOIN testcollectionstests tct
			ON tc.id = tct.testcollectionid
		INNER JOIN test t
			ON tct.testid = t.id AND t.activeflag = TRUE
		INNER JOIN studentstests st
			ON t.id = st.testid AND st.activeflag = TRUE AND st.status =
				(SELECT id FROM category
					WHERE categorycode = 'inprogress' AND categorytypeid =(SELECT id FROM categorytype WHERE typecode = 'STUDENT_TEST_STATUS'))	
);
