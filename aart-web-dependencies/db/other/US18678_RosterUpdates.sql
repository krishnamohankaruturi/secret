--Student : 4397435676
UPDATE testsession 
	SET rosterid = 1048133, 
		modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	WHERE id in (2274962,2310016,2310843,2310974,2380404,2380860)  and rosterid =927690;
	
--Student : 2234573833
UPDATE testsession 
	SET rosterid = 1048133, 
		modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	WHERE id in (2275000,2307502,2379874,2381118,2382220,2397910)  and rosterid =927690;
	
--Student : 7076501064
UPDATE testsession 
	SET rosterid = 1048133, 
		modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	WHERE id in (2298934,2310089,2310867,2311011,2311138)  and rosterid =927690;
	
--Student : 6859713291
UPDATE testsession 
	SET rosterid = 1048133, 
		modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	WHERE id in (2383606,2274972,2310153,2310958,2311074,2380500,2381361)  and rosterid =927690;
	
--Student : 9502954422
UPDATE testsession 
	SET rosterid = 1048133, 
		modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	WHERE id in (2274976,2381879,2383168,2383569)  and rosterid =927690;

