-- Student: 1002141804

UPDATE studentstests 
	SET enrollmentid = 2392922, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE enrollmentid = 2380258;
     
UPDATE testsession 
	SET rosterid = (select id from roster where  aypschoolid = 33868 and statesubjectareaid = 440), 
		modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	WHERE id in (2919407,2815340,2758914,2643224,2596630,2465798)  and rosterid =940394;

