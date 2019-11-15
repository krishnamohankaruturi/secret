
--US14176 Name:  Summative Reports - cPass Report Upload CSV and Report Upload UI Changes



insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
	(Select 'Entity Scale Scores' as categoryname,'ENTITY_SCALE_SCORES_RECORD_TYPE' as categorycode,'Entity Scale Scores' as categorydescription,
		(select id from categorytype where typecode='CSV_RECORD_TYPE') as categorytypeid, 'AART' as originationcode,now() as createddate,id as createduser,
		true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');


INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('entityType', NULL, NULL, NULL, 100, false, false, NULL, 'EntityType', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('stateStudentId', NULL, NULL, NULL, 100, false, false, NULL, 'StateStudentID', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('organizationId', NULL, NULL, NULL, 100, false, false, NULL, 'OrganizationID', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('scaleScore', NULL, NULL, NULL, 100, false, false, NULL, 'ScaleScore', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('sem', NULL, NULL, NULL, 100, false, false, NULL, 'SEM', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('scaleAndSEM', NULL, NULL, NULL, 100, false, false, NULL, 'ScaleandSEM', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

		

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SCALE_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('testId', 'TestID'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SCALE_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('entityType', 'EntityType'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SCALE_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('stateStudentId', 'StateStudentID'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SCALE_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('organizationId', 'OrganizationID'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SCALE_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('scaleScore', 'ScaleScore'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SCALE_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('achievementLevelLabel', 'AchievementLevelLabel'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SCALE_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('sem', 'SEM'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SCALE_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('scaleAndSEM', 'ScaleandSEM'));


	