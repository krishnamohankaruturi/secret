-- PNP - INSERT/UPDATE  fieldspecification, fieldspecificationsrecordtype

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('attributeName', NULL, NULL, NULL, 80, false, false, NULL, 'Attribute_Name', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('attributeValue', NULL, NULL, NULL, 30, false, false, NULL, 'Attribute_Value', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('attributeContainer', NULL, NULL, NULL, 80, false, false, NULL, 'Attribute_Container', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid,recordTypeid,createdDate,createdUser,activeFlag,modifieddate,modifieduser)
	(select distinct f.id, (Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE') as recordTypeId,
	 now() as createddate, f.createduser, f.activeflag, now() as modifieddate, f.modifieduser from fieldspecification f
	 where f.mappedname is not null and f.fieldname in ('attributeName', 'attributeValue', 'attributeContainer') );   
	
UPDATE fieldspecification SET fieldname = 'AssessmentNeedBackgroundColor' , mappedname ='AssessmentNeedBackgroundColor', fieldlength = 30
	WHERE fieldname = 'assessmentNeedBackgroundColor' AND mappedname = 'Assessment_Need_Background_Color';

UPDATE fieldspecification SET fieldname = 'AssessmentNeedSpokenSourcePreferenceType' , mappedname ='AssessmentNeedSpokenSourcePreferenceType', fieldlength = 30
	WHERE fieldname = 'assessmentNeedSpokenSourcePreferenceType' AND mappedname = 'Assessment_Need_Spoken_Source_Preference_Type';

UPDATE fieldspecification SET fieldname = 'AssessmentNeedNumberOfBrailleDotsType' , mappedname ='AssessmentNeedNumberOfBrailleDotsType', fieldlength = 30
	WHERE fieldname = 'assessmentNeedNumberOfBrailleDotsType' AND mappedname = 'Assessment_Need_Number_Of_Braille_Dots_Type';

UPDATE fieldspecification SET fieldname = 'AssessmentNeedReadAtStartPreference', mappedname = 'AssessmentNeedReadAtStartPreference', fieldlength = 30,
	allowablevalues = '{True, False}'
	WHERE fieldname = 'assessmentNeedReadAtStartPreference' AND mappedname ='Assessment_Need_Read_At_Start_Preference';

UPDATE fieldspecification SET fieldname = 'AssessmentNeedBrailleGradeType', mappedname = 'AssessmentNeedBrailleGradeType', fieldlength = 30
	WHERE fieldname = 'assessmentNeedBrailleGradeType' AND mappedname ='Assessment_Need_Braille_Grade_Type';

UPDATE fieldspecification SET fieldname = 'AssessmentNeedNumberOfBrailleCells', mappedname = 'AssessmentNeedNumberOfBrailleCells', fieldlength = 30
	WHERE fieldname = 'assessmentNeedNumberOfBrailleCells' AND mappedname ='Assessment_Need_Number_Of_Braille_Cells';

UPDATE fieldspecification SET fieldname = 'AssessmentNeedBrailleMarkType', mappedname = 'AssessmentNeedBrailleMarkType', fieldlength = 30
	WHERE fieldname = 'assessmentNeedBrailleMarkType' AND mappedname = 'Assessment_Need_Braille_Mark_Type';

UPDATE fieldspecification SET fieldname = 'AssessmentNeedBrailleDotPressure' , mappedname ='AssessmentNeedBrailleDotPressure', fieldlength = 30
	WHERE fieldname = 'assessmentNeedBrailleDotPressure' AND mappedname = 'Assessment_Need_Braille_Dot_Pressure';

UPDATE fieldspecification SET fieldname = 'AssessmentNeedBrailleStatusCellType', mappedname = 'AssessmentNeedBrailleStatusCellType', fieldlength = 30
	WHERE fieldname = 'assessmentNeedBrailleStatusCellType' AND mappedname = 'Assessment_Need_Braille_Status_Cell_Type';
													     
UPDATE fieldspecification SET fieldname = 'AssessmentNeedMaskingType', mappedname = 'AssessmentNeedMaskingType', fieldlength = 30
	WHERE fieldname = 'assessmentNeedMaskingType' AND mappedname = 'Assessment_Need_Masking_Type';													    

UPDATE fieldspecification SET fieldname = 'AssessmentNeedMagnification', mappedname = 'AssessmentNeedMagnification', fieldlength = 30
	WHERE fieldname = 'assessmentNeedMagnification' AND mappedname = 'Assessment_Need_Magnification';

UPDATE fieldspecification SET fieldname = 'AssessmentNeedUserSpokenPreferenceType', mappedname = 'AssessmentNeedUserSpokenPreferenceType', fieldlength = 30
	WHERE fieldname = 'assessmentNeedUserSpokenPreferenceType' AND mappedname = 'Assessment_Need_User_Spoken_Preference_Type';
													     
UPDATE fieldspecification SET fieldname = 'AssessmentNeedTimeMultiplier', mappedname = 'AssessmentNeedTimeMultiplier', fieldlength = 30
	WHERE fieldname = 'assessmentNeedTimeMultiplier' AND mappedname = 'Assessment_Need_Time_Multiplier';													    
														    
UPDATE fieldspecification SET fieldname = 'TactileApplication', mappedname = 'TactileApplication', fieldlength = 30, 
	allowablevalues = '{tactileAudioFile,tactileAudioText,tactileBrailleText}'
	WHERE fieldname = 'tactileApplication' AND mappedname = 'Tactile_Application';				

UPDATE fieldspecification SET fieldname = 'usage', mappedname = 'usage', fieldlength = 30, 
	allowablevalues = '{tactileAudioFile,tactileAudioText,tactileBrailleText}'
	WHERE fieldname = 'tactileUsage' AND mappedname = 'Tactile_Usage';

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('AssessmentNeedAssignedSupport', '{True, False}', NULL, NULL, 30, false, false, NULL, 'AssessmentNeedAssignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('AssessmentNeedActivateByDefault', '{True, False}', NULL, NULL, 30, false, false, NULL, 'AssessmentNeedActivateByDefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('AssessmentNeedSigningType', '{ASL, Signed English}', NULL, NULL, 30, false, false, NULL, 'AssessmentNeedSigningType', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('AssessmentNeedOverlayColor', NULL, NULL, NULL, 30, false, false, NULL, 'AssessmentNeedOverlayColor', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('AssessmentNeedForegroundColor', NULL, NULL, NULL, 30, false, false, NULL, 'AssessmentNeedForegroundColor', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('SpokenassignedSupport', '{True, False}', NULL, NULL, 30, false, false, NULL, 'SpokenassignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('SpokenActivateByDefault', '{True, False}', NULL, NULL, 30, false, false, NULL, 'SpokenActivateByDefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('SpokenSourcePreference', '{Human, Synthetic}', NULL, NULL, 30, false, false, NULL, 'SpokenSourcePreference', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('ReadAtStartPreference', '{True, False}', NULL, NULL, 30, false, false, NULL, 'ReadAtStartPreference', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('UserSpokenPreference', '{TextOnly,TextGraphicsand graphics,GraphicsOnly,NonVisual}', NULL, NULL, 30, false, false, NULL, 'UserSpokenPreference', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('SigningAssignedSupport', '{True, False}', NULL, NULL, 30, false, false, NULL, 'SigningAssignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('SigningActivatebydefault', '{True, False}', NULL, NULL, 30, false, false, NULL, 'SigningActivatebydefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('SigningType', '{ASL,Signed English}', NULL, NULL, 30, false, false, NULL, 'SigningType', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('BrailleAssignedSupport', NULL, NULL, NULL, 30, false, false, NULL, 'BrailleAssignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('BrailleActivatebydefault', NULL, NULL, NULL, 30, false, false, NULL, 'BrailleActivatebydefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('brailleGrade', '{Contracted,Uncontracted}', NULL, NULL, 30, false, false, NULL, 'brailleGrade', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('numberOfBrailleDots', '{6,8}', NULL, NULL, 30, false, false, NULL, 'numberOfBrailleDots', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('numberOfBrailleCells', NULL, NULL, NULL, 30, false, false, NULL, 'numberOfBrailleCells', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('brailleMark', '{Highlight,Bold,Underline,Italic,Strikeout,Color}', NULL, NULL, 30, false, false, NULL, 'brailleMark', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('brailleDotPressure', NULL, NULL, NULL, 30, false, false, NULL, 'brailleDotPressure', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('brailleStatusCell', '{Off,Left,Right}', NULL, NULL, 30, false, false, NULL, 'brailleStatusCell', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('MaskingAssignedSupport', '{True,False}', NULL, NULL, 30, false, false, NULL, 'MaskingAssignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('MaskingActivatebydefault', '{True,False}', NULL, NULL, 30, false, false, NULL, 'MaskingActivatebydefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('MaskingType', '{CustomMask,AnswerMask}', NULL, NULL, 30, false, false, NULL, 'MaskingType', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('MagnificationAssignedSupport', '{True,False}', NULL, NULL, 30, false, false, NULL, 'MagnificationAssignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('MagnificationActivatebydefault', '{True,False}', NULL, NULL, 30, false, false, NULL, 'MagnificationActivatebydefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('magnificationAmount', NULL, NULL, NULL, 30, false, false, NULL, 'magnificationAmount', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('tactileFile', '{tactileAudioFile,tactileAudioText,tactileBrailleText}', NULL, NULL, 30, false, false, NULL, 'tactileFile', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('AdditionalTestingTimeAssignedSupport', '{True,False}', NULL, NULL, 30, false, false, NULL, 'AdditionalTestingTimeAssignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('TimeMultiplier', NULL, NULL, NULL, 30, false, false, NULL, 'TimeMultiplier', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('BackgroundColourAssignedSupport', '{True,False}', NULL, NULL, 30, false, false, NULL, 'BackgroundColourAssignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('BackgroundColourActivateByDefault', '{True,False}', NULL, NULL, 30, false, false, NULL, 'BackgroundColourActivateByDefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('Colour', NULL, NULL, NULL, 30, false, false, NULL, 'Colour', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('ColourOverlayAssignedSupport', '{True,False}', NULL, NULL, 30, false, false, NULL, 'ColourOverlayAssignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('ColourOverlayactivateByDefault', '{True,False}', NULL, NULL, 30, false, false, NULL, 'ColourOverlayactivateByDefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('colourTint', NULL, NULL, NULL, 30, false, false, NULL, 'colourTint', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('ForegroundColourAssignedSupport', '{True,False}', NULL, NULL, 30, false, false, NULL, 'ForegroundColourAssignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('ForegroundColourActivateByDefault', '{True,False}', NULL, NULL, 30, false, false, NULL, 'ForegroundColourActivateByDefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('AssessmentNeedUsage', '{Required, preferred, optionally use, prohibited}', NULL, NULL, 30, false, false, NULL, 'AssessmentNeedUsage', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));

	
INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('AssessmentNeedAssignedSupport',  'AssessmentNeedAssignedSupport'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('AssessmentNeedActivateByDefault', 'AssessmentNeedActivateByDefault'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('AssessmentNeedSigningType', 'AssessmentNeedSigningType'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('AssessmentNeedUsage','AssessmentNeedUsage'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('AssessmentNeedOverlayColor','AssessmentNeedOverlayColor'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('AssessmentNeedForegroundColor','AssessmentNeedForegroundColor'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('SpokenassignedSupport', 'SpokenassignedSupport'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('SpokenActivateByDefault', 'SpokenActivateByDefault'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('SpokenSourcePreference', 'SpokenSourcePreference'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('ReadAtStartPreference', 'ReadAtStartPreference'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('UserSpokenPreference', 'UserSpokenPreference'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('SigningAssignedSupport', 'SigningAssignedSupport'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('SigningActivatebydefault', 'SigningActivatebydefault'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification  WHERE (fieldname,mappedname) in (('SigningType', 'SigningType'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('BrailleAssignedSupport', 'BrailleAssignedSupport'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('BrailleActivatebydefault', 'BrailleActivatebydefault'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('brailleGrade', 'brailleGrade'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('numberOfBrailleDots','numberOfBrailleDots'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('numberOfBrailleCells', 'numberOfBrailleCells'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('brailleMark', 'brailleMark'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('brailleDotPressure', 'brailleDotPressure'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification  WHERE (fieldname,mappedname) in (('brailleStatusCell', 'brailleStatusCell'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('MaskingAssignedSupport', 'MaskingAssignedSupport'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('MaskingActivatebydefault', 'MaskingActivatebydefault'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('MaskingType', 'MaskingType'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('MagnificationAssignedSupport', 'MagnificationAssignedSupport'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('MagnificationActivatebydefault', 'MagnificationActivatebydefault'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('magnificationAmount', 'magnificationAmount'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('tactileFile', 'tactileFile'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('AdditionalTestingTimeAssignedSupport', 'AdditionalTestingTimeAssignedSupport'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification  WHERE (fieldname,mappedname) in (('TimeMultiplier', 'TimeMultiplier'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('BackgroundColourAssignedSupport', 'BackgroundColourAssignedSupport'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('BackgroundColourActivateByDefault', 'BackgroundColourActivateByDefault'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('Colour', 'Colour'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('ColourOverlayAssignedSupport', 'ColourOverlayAssignedSupport'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('ColourOverlayactivateByDefault', 'ColourOverlayactivateByDefault'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('colourTint', 'colourTint'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('ForegroundColourAssignedSupport', 'ForegroundColourAssignedSupport'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('ForegroundColourActivateByDefault', 'ForegroundColourActivateByDefault'));
    
    
-- INFO original 26.sql
-- PNP - INSERT  profileitemattributecontainer
    
INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('ContentSpoken',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('ContentSigning',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('DisplayBraille',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('Braille',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('Masking',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('ScreenEnhancementMagnification',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('Tactile',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('ControlAdditionalTestingTime',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('ScreenEnhancementBackgroundColour',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('ScreenEnhancementColouroverlay',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('ScreenEnhancementForegroundColour',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));


--PNP INSERT CATEGORYTYPE,CATEGORY,profileitemattributenickname 
	
INSERT INTO CATEGORYTYPE (typename, typecode, typedescription,originationcode,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('Attribute Nickname Standard', 'Attribute_Nickname_Standard', 'PNP Profile Attribute Nickname Standard', '',
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode,categorydescription, categorytypeid, originationcode,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('CEDS', 'CEDS', 'CEDS',(select id from categorytype where typecode='Attribute_Nickname_Standard'),'',
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedAssignedSupport', (select id from category where categorycode='CEDS'),'00128',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedActivateByDefault', (select id from category where categorycode='CEDS'),'00129',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedSpokenSourcePreferenceType', (select id from category where categorycode='CEDS'),'001042',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedReadAtStartPreference', (select id from category where categorycode='CEDS'),'001043',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedUserSpokenPreferenceType', (select id from category where categorycode='CEDS'),'001044',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedSigningType', (select id from category where categorycode='CEDS'),'001033',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedBrailleGradeType', (select id from category where categorycode='CEDS'),'001036',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedNumberOfBrailleDotsType', (select id from category where categorycode='CEDS'),'001037',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedNumberOfBrailleCells', (select id from category where categorycode='CEDS'),'001046',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedBrailleMarkType', (select id from category where categorycode='CEDS'),'001031',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedBrailleDotPressure', (select id from category where categorycode='CEDS'),'001049',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedBrailleStatusCellType', (select id from category where categorycode='CEDS'),'001053',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedUsage', (select id from category where categorycode='CEDS'),'001053',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedMaskingType', (select id from category where categorycode='CEDS'),'001053',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedMagnification', (select id from category where categorycode='CEDS'),'001053',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedTimeMultiplier', (select id from category where categorycode='CEDS'),'001053',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedBackgroundColor', (select id from category where categorycode='CEDS'),'001053',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedOverlayColor', (select id from category where categorycode='CEDS'),'001053',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('AssessmentNeedForegroundColor', (select id from category where categorycode='CEDS'),'001053',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenickname (attributenickname, attributenicknamestandardid,attributenicknamecode,
	createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('TactileApplication', (select id from category where categorycode='CEDS'),'001053',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));


--PNP INSERT profileitemattribute

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('SpokenassignedSupport', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('SpokenActivateByDefault', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('SpokenSourcePreference',now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('ReadAtStartPreference', now(), (select id from aartuser where username='cetesysadmin'), true, now(),
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('UserSpokenPreference', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('SigningAssignedSupport', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('SigningActivatebydefault', now(), (select id from aartuser where username='cetesysadmin'), true, now(),
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('SigningType', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('BrailleAssignedSupport', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('BrailleActivatebydefault', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('brailleGrade', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('numberOfBrailleDots', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('numberOfBrailleCells', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('brailleMark', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('brailleDotPressure', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('brailleStatusCell', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('usage', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('MaskingAssignedSupport', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('MaskingActivatebydefault', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('MaskingType', now(), (select id from aartuser where username='cetesysadmin'), true, now(),
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('MagnificationAssignedSupport', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('MagnificationActivatebydefault', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('magnificationAmount', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('tactileFile', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('AdditionalTestingTimeAssignedSupport', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('TimeMultiplier', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('BackgroundColourAssignedSupport', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('BackgroundColourActivateByDefault', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('Colour', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('ColourOverlayAssignedSupport', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('ColourOverlayactivateByDefault', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('colourTint', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('ForegroundColourAssignedSupport', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('ForegroundColourActivateByDefault', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

--PNP INSERT profileitemattributenicknamecontainer	

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='SpokenassignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'ContentSpoken'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedAssignedSupport'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='SpokenActivateByDefault'), (select id from profileitemattributecontainer where attributecontainer = 'ContentSpoken'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedActivateByDefault'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='SpokenSourcePreference'), (select id from profileitemattributecontainer where attributecontainer = 'ContentSpoken'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedSpokenSourcePreferenceType'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='ReadAtStartPreference'), (select id from profileitemattributecontainer where attributecontainer = 'ContentSpoken'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedReadAtStartPreference'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='UserSpokenPreference'), (select id from profileitemattributecontainer where attributecontainer = 'ContentSpoken'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedUserSpokenPreferenceType'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='SigningAssignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'ContentSigning'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedAssignedSupport'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='SigningActivatebydefault'), (select id from profileitemattributecontainer where attributecontainer = 'ContentSigning'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedActivateByDefault'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='SigningType'), (select id from profileitemattributecontainer where attributecontainer = 'ContentSigning'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedSigningType'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='BrailleAssignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'DisplayBraille'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedAssignedSupport'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='BrailleActivatebydefault'), (select id from profileitemattributecontainer where attributecontainer = 'DisplayBraille'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedActivateByDefault'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='brailleGrade'), (select id from profileitemattributecontainer where attributecontainer = 'DisplayBraille'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedBrailleGradeType'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='numberOfBrailleDots'), (select id from profileitemattributecontainer where attributecontainer = 'DisplayBraille'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedNumberOfBrailleDotsType'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select max(id) from profileitemattribute where attributename ='numberOfBrailleCells'), (select id from profileitemattributecontainer where attributecontainer = 'DisplayBraille'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedNumberOfBrailleCells'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='brailleMark'), (select id from profileitemattributecontainer where attributecontainer = 'DisplayBraille'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedBrailleMarkType'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select max(id) from profileitemattribute where attributename ='brailleDotPressure'), (select id from profileitemattributecontainer where attributecontainer = 'DisplayBraille'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedBrailleDotPressure'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='brailleStatusCell'), (select id from profileitemattributecontainer where attributecontainer = 'Braille'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedBrailleStatusCellType'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='usage'), (select id from profileitemattributecontainer where attributecontainer = 'Braille'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedUsage'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='MaskingAssignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'Masking'), 
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='MaskingActivatebydefault'), (select id from profileitemattributecontainer where attributecontainer = 'Masking'),
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='MaskingType'), (select id from profileitemattributecontainer where attributecontainer = 'Masking'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedMaskingType'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='MagnificationAssignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementMagnification'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedAssignedSupport'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='MagnificationActivatebydefault'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementMagnification'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedActivateByDefault'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='magnificationAmount'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementMagnification'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedMagnification'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='tactileFile'), (select id from profileitemattributecontainer where attributecontainer = 'Tactile'),
	(select id from profileitemattributenickname where attributenickname ='TactileApplication'), 
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='usage'), (select id from profileitemattributecontainer where attributecontainer = 'Tactile'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedUsage'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='AdditionalTestingTimeAssignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'ControlAdditionalTestingTime'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedAssignedSupport'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='TimeMultiplier'), (select id from profileitemattributecontainer where attributecontainer = 'ControlAdditionalTestingTime'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedTimeMultiplier'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='BackgroundColourAssignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementBackgroundColour'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedAssignedSupport'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='BackgroundColourActivateByDefault'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementBackgroundColour'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedActivateByDefault'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='Colour'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementBackgroundColour'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedBackgroundColor'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='ColourOverlayAssignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementColouroverlay'),
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='ColourOverlayactivateByDefault'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementColouroverlay'),
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='colourTint'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementColouroverlay'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedOverlayColor'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='ForegroundColourAssignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementForegroundColour'),
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='ForegroundColourActivateByDefault'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementForegroundColour'),
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='Colour'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementForegroundColour'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedForegroundColor'),  
	now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
	
--PNP UPDATE/ALTER/DELETE studentprofileitemattributevalue,profileitemattribute - Don't remove this from here, these
	-- need to be executed at the end i.e after all INSERTS.

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedBackgroundColor'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'backgroundColor');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedSpokenSourcePreferenceType'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'spokenSourcePreferenceType');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedMagnification'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'magnification');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedMaskingType'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'maskingType');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedTimeMultiplier'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'timeMultiplier');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedUserSpokenPreferenceType'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'userSpokenPreferenceType');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedReadAtStartPreference'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'readAtStartPreference');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedBrailleGradeType'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'brailleGradeType');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedBrailleDotPressure'),
	profileitemattributeid = 0
	where profileitemattributeid = (select min(id) from profileitemattribute where attributename = 'brailleDotPressure');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedBrailleMarkType'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'brailleMarkType');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedBrailleStatusCellType'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'brailleStatusCellType');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedNumberOfBrailleCells'),
	profileitemattributeid = 0
	where profileitemattributeid = (select min(id) from profileitemattribute where attributename = 'numberOfBrailleCells');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'TactileApplication'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'tactileApplication');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian where 
		pianc.attributenicknameid = pian.id and attributenickname = 'AssessmentNeedNumberOfBrailleDotsType'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'numberOfBrailleDotsType');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian, profileitemattributecontainer piac where 
		pianc.attributenicknameid = pian.id and pianc.attributecontainerid = piac.id and attributenickname = 'AssessmentNeedUsage' and attributecontainer = 'Braille'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'brailleUsage');

UPDATE studentprofileitemattributevalue SET profileitemattributenicknamecontainerid = 
	(select pianc.id from profileitemattributenicknamecontainer  pianc, profileitemattributenickname pian, profileitemattributecontainer piac where 
		pianc.attributenicknameid = pian.id and pianc.attributecontainerid = piac.id and attributenickname = 'AssessmentNeedUsage' and attributecontainer = 'Tactile'),
	profileitemattributeid = 0
	where profileitemattributeid = (select id from profileitemattribute where attributename = 'tactileUsage');

ALTER TABLE studentprofileitemattributevalue DROP COLUMN profileitemattributeid;

ALTER TABLE studentprofileitemattributevalue ADD CONSTRAINT uk_student_profile_item_attribute_value UNIQUE (studentid , profileitemattributenicknamecontainerid );

DELETE FROM profileitemattribute where id < 17;

/* DE3404 - AYP School Identifier from CSV Enrollment Upload is not being stored */
UPDATE fieldspecificationsrecordtypes
SET    fieldspecificationid =
       (SELECT id
       FROM    fieldspecification
       WHERE   (
                       fieldname,mappedname
               )
               IN (('aypSchoolIdentifier' ,
                    'AYP_QPA_Bldg_No'))
       )
WHERE  fieldspecificationid =
       ( SELECT id
       FROM    fieldspecification
       WHERE   (
                       fieldname,mappedname
               )
               IN (('rosterAYPSchoolIdentifier',
                    'AYP_QPA_Bldg_No'))
       )
AND    recordtypeid =
       (SELECT id
       FROM    category
       WHERE   categorycode='ENRL_RECORD_TYPE'
       );