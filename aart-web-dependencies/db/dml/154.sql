
--US14176 Name:  Summative Reports - cPass Report Upload CSV and Report Upload UI Changes


DELETE FROM userreportupload WHERE filetypeid = (SELECT id FROM CATEGORY WHERE categorycode = 'SCALE_SCORE_DEVIATION' AND categorytypeid = (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE'));

DELETE FROM CATEGORY WHERE categorycode = 'SCALE_SCORE_DEVIATION' AND categorytypeid = (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE');


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Scale and SEM', 'SCALE_AND_SEM', 'SCALE_AND_SEM', (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Entity SubScores', 'ENTITY_SUBSCORES', 'Entity SubScores', (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Entity Topic Scores', 'ENTITY_TOPIC_SCORES', 'Entity Topic Scores', (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('SubScores and SubScore SEM', 'SUBSCORES_AND_SUBSCORE_SEM', 'SubScores and SubScore SEM', (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Topic Scores and Topic Score SEM', 'TOPIC_SCORES_AND_TOPIC_SCORE_SEM', 'Topic Scores and Topic Score SEM', (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
	(Select 'Test Cut Scores' as categoryname,'TEST_CUT_SCORES_RECORD_TYPE' as categorycode,'Test Cut Scores' as categorydescription,
		(select id from categorytype where typecode='CSV_RECORD_TYPE') as categorytypeid, 'AART' as originationcode,now() as createddate,id as createduser,
		true as activeflag, now() as modifieddate,id as modifieduser from aartuser where username='cetesysadmin');


INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('testId', NULL, NULL, NULL, 100, false, false, NULL, 'TestID', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('achievementLevelLabel', NULL, NULL, NULL, 100, false, false, NULL, 'AchievementLevelLabel', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('achievementLevelDescription', NULL, NULL, NULL, 100, false, false, NULL, 'AchievementLevelDescription', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('lowerLevelCutScore', NULL, NULL, NULL, 100, false, false, NULL, 'LowerLevelCutScore', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, 
	formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES 
	('upperLevelCutScore', NULL, NULL, NULL, 100, false, false, NULL, 'UpperLevelCutScore', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
		(Select id from aartuser where username = 'cetesysadmin'), false);


INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='TEST_CUT_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('testId', 'TestID'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='TEST_CUT_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('achievementLevelLabel', 'AchievementLevelLabel'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='TEST_CUT_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('achievementLevelDescription', 'AchievementLevelDescription'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='TEST_CUT_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('lowerLevelCutScore', 'LowerLevelCutScore'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='TEST_CUT_SCORES_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('upperLevelCutScore', 'UpperLevelCutScore'));

	
	