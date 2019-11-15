

--US14176 Name:  Summative Reports - cPass Report Upload CSV and Report Upload UI Changes



insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
	(Select 'Scale And SEM' as categoryname,'SCALE_AND_SEM_RECORD_TYPE' as categorycode,'Scale And SEM' as categorydescription,
		(select id from categorytype where typecode='CSV_RECORD_TYPE') as categorytypeid, 'AART' as originationcode,now() as createddate,id as createduser,
		true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');


INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('entitySubScores', NULL, NULL, NULL, 100, false, false, NULL, 'EntitySubScores', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);
		

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='SCALE_AND_SEM_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('testId', 'TestID'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='SCALE_AND_SEM_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('scaleScore', 'ScaleScore'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='SCALE_AND_SEM_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('achievementLevelLabel', 'AchievementLevelLabel'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='SCALE_AND_SEM_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('sem', 'SEM'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='SCALE_AND_SEM_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('entitySubScores', 'EntitySubScores'));



	
	

insert into category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
	(Select 'Entity SubScores' as categoryname,'ENTITY_SUBSCORES_RECORD_TYPE' as categorycode,'Entity SubScores' as categorydescription,
		(select id from categorytype where typecode='CSV_RECORD_TYPE') as categorytypeid, 'AART' as originationcode,now() as createddate,id as createduser,
		true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');


INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('claimOrCategory', NULL, NULL, NULL, 100, false, false, NULL, 'ClaimorCategory', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('subScore', NULL, NULL, NULL, 100, false, false, NULL, 'SubScore', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);
				
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('subScoreSEM', NULL, NULL, NULL, 100, false, false, NULL, 'SubScoreSEM', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);
		
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('entityTopicScores', NULL, NULL, NULL, 100, false, false, NULL, 'EntityTopicScores', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);


INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SUBSCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('testId', 'TestID'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SUBSCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('claimOrCategory', 'ClaimorCategory'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SUBSCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('entityType', 'EntityType'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SUBSCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('stateStudentId', 'StateStudentID'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SUBSCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('organizationId', 'OrganizationID'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SUBSCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('subScore', 'SubScore'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SUBSCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('subScoreSEM', 'SubScoreSEM'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ENTITY_SUBSCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('entityTopicScores', 'EntityTopicScores'));


	
	
	
	
	