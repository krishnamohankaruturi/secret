-- US18831 : DLM TEST RESET

DO
$BODY$
DECLARE
	tsrecord RECORD;
BEGIN
	FOR tsrecord IN (SELECT ts.id as testsessionid FROM testsession ts
					join enrollmentsrosters er on er.rosterid = ts.rosterid and er.activeflag is false
					join roster r on r.id = er.rosterid
					join enrollment e on e.id = er.enrollmentid
					join student s ON s.id = e.studentid and s.statestudentidentifier in('1002457332')
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
	
		UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
	    WHERE studenttrackerid in (253694, 253651) AND testsessionid  = tsrecord.testsessionid AND source = 'ST';
	
		UPDATE studenttrackerband SET testsessionid = null, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
	    WHERE studenttrackerid IN (253694, 253651) AND testsessionid  = tsrecord.testsessionid AND source = 'FC';
		
		
	END LOOP;
	INSERT INTO domainaudithistory (source, objecttype, objectid, createduserid, createddate, action, objectbeforevalues, objectaftervalues)
	      values('SCRIPT', 'STUDENTSTESTS', 865996, 12, now(), 'RESET_STUDENT_TRACKER', ('{"Rosters": "1057457,936068,936067", "activeflag": true}')::JSON,  
	      ('{"Reason": "As per US18831, inactivated the students tests"}')::JSON);
END;
$BODY$;
	




















