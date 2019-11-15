--US18704_MoveTestSessionsToActiveRoster.sql

--Student: Austin Johnson
--State Student ID: 1301817
--StudentID: 928228
--EnrollmentID: 1797077


UPDATE testsession 
SET rosterid = 1061135, modifieduser=12, modifieddate=now()
WHERE id = 3153365 and rosterid =922599;

UPDATE testsession 
SET rosterid = 1061136 ,modifieduser=12, modifieddate=now()
WHERE id = 3154027 and rosterid =912990;


--Student: Baleria Fernandez
--State Student ID: 1997767
--StudentID: 1103492
--EnrollmentID: 1797075

UPDATE testsession 
SET rosterid = 1061135 ,modifieduser=12, modifieddate=now()
WHERE id = 2361158 and rosterid =885291;

UPDATE testsession 
SET rosterid = 1061136 ,modifieduser=12, modifieddate=now()
WHERE id = 2361263 and rosterid =912990;