 
DO
$BODY$
DECLARE
	tsrecord RECORD;
BEGIN
	--Student : 1397969121
	--SCI
	FOR tsrecord IN (select s.statestudentidentifier,s.id as studentid, e.id as enrollmentid, ts.id as testsessionid, ts.name as TSname, r.id as rosterid, er.activeflag, er.modifieddate, er.modifieduser
					from testsession ts
					join enrollmentsrosters er on er.rosterid = ts.rosterid
					join roster r on r.id = er.rosterid
					join enrollment e on e.id = er.enrollmentid
					join student s ON s.id = e.studentid and s.statestudentidentifier in('1397969121')
					where e.activeflag is true and ts.activeflag is true and r.id in (894111) and ts.source = 'BATCHAUTO')
					
	LOOP
	
		UPDATE testsession SET rosterid = 1024446, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  = tsrecord.testsessionid and rosterid in (894111);
	    
	END LOOP;
	
	--Math
	
	FOR tsrecord IN (select s.statestudentidentifier,s.id as studentid, e.id as enrollmentid, ts.id as testsessionid, ts.name as TSname, r.id as rosterid, er.activeflag, er.modifieddate, er.modifieduser
					from testsession ts
					join enrollmentsrosters er on er.rosterid = ts.rosterid
					join roster r on r.id = er.rosterid
					join enrollment e on e.id = er.enrollmentid
					join student s ON s.id = e.studentid and s.statestudentidentifier in('1397969121')
					where e.activeflag is true and ts.activeflag is true and r.id in (894110) and ts.source = 'BATCHAUTO')
					
	LOOP
	
		UPDATE testsession SET rosterid = 1024444, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  = tsrecord.testsessionid and rosterid in (894110);
	    
	END LOOP;
	
END;
$BODY$;