--1001150391
DO
$BODY$
DECLARE
	tsrecord RECORD;
BEGIN
	FOR tsrecord IN (SELECT ts.id as testsessionid FROM testsession ts
					join enrollmentsrosters er on er.rosterid = ts.rosterid and er.activeflag is false
					join roster r on r.id = er.rosterid
					join enrollment e on e.id = er.enrollmentid
					join student s ON s.id = e.studentid and s.statestudentidentifier in('1001150391')
					where e.activeflag is true and ts.activeflag is true)
					
	LOOP
	
		UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE studentstestsid IN (SELECT id FROM studentstests WHERE testsessionid = tsrecord.testsessionid);
	
		UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE studentstestid IN (SELECT id FROM studentstests WHERE testsessionid  = tsrecord.testsessionid);
	
		UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE testsessionid  = tsrecord.testsessionid;
	                 
		UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  = tsrecord.testsessionid;
	
	END LOOP;
	
END;
$BODY$;