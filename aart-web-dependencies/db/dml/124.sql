

--US13518 Name: Auto Registration Building Level users: View Test Sessions and Print tickets

 INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('HIGH_STAKES_TICKETING','High Stakes Ticketing','High Stakes', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
	
	
