--1001150391 

DO
$BODY$
DECLARE
	tsrecord RECORD;
BEGIN
	--Backout all changes done earlier
	FOR tsrecord IN (SELECT ts.id as testsessionid FROM testsession ts
					join enrollmentsrosters er on er.rosterid = ts.rosterid and er.activeflag is false
					join roster r on r.id = er.rosterid
					join enrollment e on e.id = er.enrollmentid
					join student s ON s.id = e.studentid and s.statestudentidentifier in('1001150391')
					where e.activeflag is true and ts.activeflag is false and ts.modifieduser = 12 and to_char(ts.modifieddate,'mm/dd/yyyy')='05/12/2016')
	LOOP
		UPDATE studentsresponses SET activeflag = true, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE studentstestsid IN (SELECT id FROM studentstests WHERE testsessionid = tsrecord.testsessionid);
	
		UPDATE studentstestsections SET activeflag = true,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE studentstestid IN (SELECT id FROM studentstests WHERE testsessionid  = tsrecord.testsessionid);
	
		UPDATE studentstests SET activeflag = true, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE testsessionid  = tsrecord.testsessionid;
	                 
		UPDATE testsession SET activeflag = true, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  = tsrecord.testsessionid;
	END LOOP;
	
END;
$BODY$;
---------------------Part2-----------------
DO
$BODY$
DECLARE
	tsrecord RECORD;
BEGIN
	--Student : 1001150391 -- Move all completed ELA tests to correct roster
		
	--ELA
	FOR tsrecord IN (select st.studentid as studentid, st.enrollmentid, ts.id as testsessionid, ts.name as TSname, r.id as rosterid, er.activeflag, ts.activeflag, er.modifieddate, er.modifieduser, st.status
					from studentstests st
					join testsession ts on st.testsessionid = ts.id
					join enrollmentsrosters er on er.rosterid = ts.rosterid and er.enrollmentid = st.enrollmentid and er.enrollmentid = 1800568
					join roster r on r.id = er.rosterid
					where ts.source = 'BATCHAUTO' and ts.rosterid in (927019) and st.enrollmentid = 1800568 and st.studentid = 1208300 and er.activeflag is false and ts.activeflag is true)
					
	LOOP
	
		UPDATE testsession SET rosterid = 927024, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  = tsrecord.testsessionid;
	    
	END LOOP;
	-- cleanup unused duplicate ELA test
	UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
    WHERE studentstestsid IN (SELECT id FROM studentstests WHERE testsessionid in (3684875));

	UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
    WHERE studentstestid IN (SELECT id FROM studentstests WHERE testsessionid in (3684875));

	UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
    WHERE testsessionid  in (3684875);
                 
	UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
    WHERE id in (3684875);
    
 	UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
	WHERE testsessionid in (3684875) AND source = 'ST';
	     
END;
$BODY$;

------------------Part3---------------------

-- cleanup unused duplicate ELA and SCI tests

	UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
    WHERE studentstestsid IN (SELECT id FROM studentstests WHERE testsessionid in (3685725, 3685733, 3685740, 3685746, 3685751, 3685758, 3685808, 3685651, 3685660, 3685667, 3685676, 3685682, 3684881));

	UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
    WHERE studentstestid IN (SELECT id FROM studentstests WHERE testsessionid in (3685725, 3685733, 3685740, 3685746, 3685751, 3685758, 3685808, 3685651, 3685660, 3685667, 3685676, 3685682, 3684881));

	UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
    WHERE testsessionid  in (3685725, 3685733, 3685740, 3685746, 3685751, 3685758, 3685808, 3685651, 3685660, 3685667, 3685676, 3685682, 3684881);
                 
	UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
    WHERE id in (3685725, 3685733, 3685740, 3685746, 3685751, 3685758, 3685808, 3685651, 3685660, 3685667, 3685676, 3685682, 3684881);
    
 	UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
	WHERE testsessionid in (3685725, 3685733, 3685740, 3685746, 3685751, 3685758, 3685808, 3685651, 3685660, 3685667, 3685676, 3685682, 3684881);