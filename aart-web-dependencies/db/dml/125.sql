                                
--DE5496: Email Apostrophe

UPDATE fieldspecification SET 
formatregex = '^[\w~`!#$%^&*_+={};:/?|-]+(\.{0,1}[\w~`!#$%^''&*_+={};:/?|-]+)*@{1}[A-Za-z0-9]+(\.{0,1}[A-Za-z0-9]+-{0,1})*\.{1}[A-Za-z0-9]{2,}$' where fieldname='email';





--US13611: Report Elements

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('primaryExceptionalityCode', NULL, NULL, NULL, 30, false, false, NULL, 'Primary_Exceptionality_Code', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('secondaryExceptionalityCode', NULL, NULL, NULL, 30, false, false, NULL, 'Secondary_Exceptionality_Code', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingMath1', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_Math_1', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingMath2', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_Math_2', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingReading1', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_Reading_1', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingReading2', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_Reading_2', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingScience1', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_Science_1', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingScience2', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_Science_2', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingHistory1', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_History_1', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingHistory2', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_History_2', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingWriting1', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_Writing_1', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingWriting2', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_Writing_2', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingKelpa1', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_KELPA_1', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('groupingKelpa2', NULL, NULL, NULL, 30, false, false, NULL, 'Grouping_KELPA_2', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('userField1', NULL, NULL, NULL, 30, false, false, NULL, 'User_Field_1', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('userField1', NULL, NULL, NULL, 30, false, false, NULL, 'User_Field_2', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('userField3', NULL, NULL, NULL, 30, false, false, NULL, 'User_Field_3', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('esolParticipationCode', NULL, NULL, NULL, 30, false, false, NULL, 'ESOL_Participation_Code', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('esolProgramEndingDate', NULL, NULL, NULL, 10, false, false, NULL, 'ESOL_Program_Ending_Date', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('spedProgramEndDate', NULL, NULL, NULL, 10, false, false, NULL, 'SPED_Program_End_Date', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('esolProgramEntryDate', NULL, NULL, NULL, 10, false, false, NULL, 'ESOL_Program_Entry_Date', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('kelpa', NULL, NULL, NULL, 30, false, false, NULL, 'KELPA', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('usaEntryDate', NULL, NULL, NULL, 10, false, false, NULL, 'USA_Entry_Date', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('stateWritingAssess', NULL, NULL, NULL, 30, false, false, NULL, 'State_Writing_Assess', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

--Fieldspecificationrecordtypes
					
INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('primaryExceptionalityCode', 'Primary_Exceptionality_Code'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('secondaryExceptionalityCode', 'Secondary_Exceptionality_Code'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingMath1', 'Grouping_Math_1'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingMath2', 'Grouping_Math_2'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingReading1', 'Grouping_Reading_1'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingReading2', 'Grouping_Reading_2'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingScience1', 'Grouping_Science_1'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingScience2', 'Grouping_Science_2'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingHistory1', 'Grouping_History_1'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingHistory2', 'Grouping_History_2'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingWriting1', 'Grouping_Writing_1'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingWriting2', 'Grouping_Writing_2'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingKelpa1', 'Grouping_KELPA_1'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('groupingKelpa2', 'Grouping_KELPA_2'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('userField1', 'User_Field_1'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('userField2', 'User_Field_2'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('userField3', 'User_Field_3'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('esolParticipationCode', 'ESOL_Participation_Code'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('esolProgramEndingDate', 'ESOL_Program_Ending_Date'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('spedProgramEndDate', 'SPED_Program_End_Date'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('esolProgramEntryDate', 'ESOL_Program_Entry_Date'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('kelpa', 'KELPA'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('usaEntryDate', 'USA_Entry_Date'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('stateWritingAssess', 'State_Writing_Assess'));
	

--US12925: Professional Development: Enroll in a module

INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
                VALUES ('User Module Status', 'USER_MODULE_STATUS', 'User Module Status', 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), 
                                now(), (select id from aartuser where username='cetesysadmin'));
                                
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
                VALUES ('In Progress', 'INPROGRESS', 'User enrolled to a module is taking the test.', (select id from categorytype where typecode='USER_MODULE_STATUS'), 
                                'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
                            

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
                VALUES ('Enrolled', 'ENROLLED', 'User is enrolled to a module.', (select id from categorytype where typecode='USER_MODULE_STATUS'), 
                                'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
                VALUES ('Unenrolled', 'UNENROLLED', 'User is unenrolled to a module.', (select id from categorytype where typecode='USER_MODULE_STATUS'), 
                                'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

 INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
                VALUES ('Completed', 'COMPLETED', 'User COMPLETED a module.', (select id from categorytype where typecode='USER_MODULE_STATUS'), 
                                'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));                                
