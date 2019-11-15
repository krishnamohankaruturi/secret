--US17583 - Remove completed Math Performance Tests for statestudentidentifier = '7644684786'

UPDATE studentsresponses
SET activeflag=false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
WHERE studentstestsectionsid IN(
	SELECT id FROM studentstestsections  WHERE studentstestid IN(
		SELECT st.id FROM studentstests st 
			JOIN testsession ts on st.testsessionid=ts.id
			JOIN operationaltestwindow otw on ts.operationaltestwindowid=otw.id
			WHERE st.studentid =(SELECT id FROM student WHERE statestudentidentifier='7644684786' AND stateid=51)
			AND st.status = (SELECT cat.id FROM category cat JOIN categorytype ctype on cat.categorytypeid=ctype.id WHERE cat.categorycode='complete' AND ctype.typecode='STUDENT_TEST_STATUS')
			AND otw.effectivedate <= now() AND now() <= otw.expirydate
			AND st.activeflag is true)
	);

UPDATE studentstestsections
SET activeflag=false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
WHERE studentstestid IN(
	SELECT st.id FROM studentstests st 
		JOIN testsession ts on st.testsessionid=ts.id
		JOIN operationaltestwindow otw on ts.operationaltestwindowid=otw.id
		WHERE st.studentid =(SELECT id FROM student WHERE statestudentidentifier='7644684786' AND stateid=51)
		AND st.status = (SELECT cat.id FROM category cat JOIN categorytype ctype on cat.categorytypeid=ctype.id WHERE cat.categorycode='complete' AND ctype.typecode='STUDENT_TEST_STATUS')
		AND otw.effectivedate <= now() AND now() <= otw.expirydate
		AND st.activeflag is true
	);

UPDATE studentstests 
SET activeflag=false ,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
WHERE id IN(
	SELECT st.id FROM studentstests st 
		JOIN testsession ts on st.testsessionid=ts.id
		JOIN operationaltestwindow otw on ts.operationaltestwindowid=otw.id
		WHERE st.studentid =(SELECT id FROM student WHERE statestudentidentifier='7644684786' AND stateid=51)
		AND st.status = (SELECT cat.id FROM category cat JOIN categorytype ctype on cat.categorytypeid=ctype.id WHERE cat.categorycode='complete' AND ctype.typecode='STUDENT_TEST_STATUS')
		AND otw.effectivedate <= now() AND now() <= otw.expirydate
		AND st.activeflag is true
	);



UPDATE enrollment 
SET notes ='InActivated Completed Math performance tests as requested by US17583',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
WHERE studentid=(SELECT id FROM student WHERE statestudentidentifier='7644684786' AND stateid=51);
