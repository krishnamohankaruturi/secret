
-- 249.sql

update fieldspecification set fieldlength = null, minimum = null, maximum = null where fieldname = 'achievementLevelDescription';
update fieldspecification set fieldlength = null, minimum = null, maximum = null where fieldname = 'achievementLevelName';
update fieldspecification set minimum = null, maximum = null where fieldname = 'achievementLevelLabel';



DO
$BODY$
BEGIN
	IF ((select count(*) from fieldspecificationsrecordtypes where recordtypeid = (select id from category where categorycode = 'TEST_CUT_SCORES_RECORD_TYPE') and fieldspecificationid = (select id from fieldspecification where fieldname = 'testId')
) = 0) THEN
		RAISE NOTICE  '%', 'Rule for Test ID and TEST cut score not exists. Inserting';
		insert into fieldspecificationsrecordtypes values ((select id from fieldspecification where fieldname = 'testId'
), (select id from category where categorycode = 'TEST_CUT_SCORES_RECORD_TYPE'
), current_timestamp, (select id from aartuser where username='cetesysadmin'), true, current_timestamp, (select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Rule for Test ID and TEST cut score is already exists';
	END IF;
END;
$BODY$;
	 