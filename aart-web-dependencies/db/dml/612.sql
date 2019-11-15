--F365
INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('DATA_EXTRACTS_KAP_STUDENT_SCORES', 'Create KAP Student Score Data Extract','Reports-Data Extracts', current_timestamp,(Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin'));
            
--SQL Script aartuser changes for F408
--updating flag to true for all the internel users.
update aartuser set internaluserindicator = true where email ilike '%@scriptbees.com%';
update aartuser set internaluserindicator = true where email ilike '%@changepond.com%';
update aartuser set internaluserindicator = true where email ilike '%@ku.edu%';

-- DML(Adding new permission) for  US19250: ITI Blueprint Coverage summary - data extract
DO
$BODY$
BEGIN
	
	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_DLM_BP_COVERAGE' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_DLM_BP_COVERAGE does not exist. Inserting...';
		INSERT INTO authorities(
				authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES ('DATA_EXTRACTS_DLM_BP_COVERAGE', 'Create Blueprint coverage summary Data Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_DLM_BP_COVERAGE exists. Skipping...';
	END IF;
	
END;
$BODY$;


INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('DATA_EXTRACTS_MONITOR_ASSIGNMENT', 'Create Monitor Scoring Assignments Data Extract', 
    'Reports-Data Extracts',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));
