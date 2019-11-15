-- F546 Scoring dml updates

--POPULATE LAST AUDIT COLUMN VALUES IN BELOW TABLES
UPDATE scoringassignment set modifieduser = createduser, modifieddate = createddate;
UPDATE scoringassignmentstudent set modifieduser = createduser, modifieddate = createddate;
UPDATE scoringassignmentscorer set modifieduser = createduser, modifieddate = createddate;

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser,tabname,groupingname,level,sortorder)
VALUES ('PERM_EDIT_SCORES', 'Edit Scores', 'Test Management-Scoring',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'),'Scoring','My Scoring',1,9050);

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser,tabname,groupingname,level,sortorder)
VALUES ('PERM_MONITOR_HARM_TO_SELF', 'Monitor Scores Harm To Self', 'Test Management-Scoring',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'),'Scoring','Manage Scoring',1,9001);

--Removing columns from scoring upload file
UPDATE fieldspecificationsrecordtypes set activeflag =false,modifieddate=now() where recordtypeid in 
(select id from category where categorycode ='SCORING_RECORD_TYPE') 
and mappedname in ('Educator Identifier','Educator First Name','Educator Last Name');
