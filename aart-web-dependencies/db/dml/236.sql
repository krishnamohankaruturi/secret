
--US14611
INSERT INTO authorities(authority,displayname,objecttype,createduser,createddate,modifieduser, modifieddate) values
	('TEST_COORD_TESTSESSION_SCORING','Test Coordination Scoring','Test Management-Test Session', (Select id from aartuser where username='cetesysadmin'),now(), (Select id from aartuser where username='cetesysadmin'), now());
