--INFO: AART related tables. original 30.sql

--names are now required.
update fieldspecification
set
rejectifempty=true,rejectifinvalid=true
where lower(fieldname) like '%legal%st%name%';


-- Start -- UPDATE statements for fieldspecification table -- US10197

UPDATE fieldspecification SET allowablevalues = '{TEST,STCO}' WHERE fieldname = 'recordType' and mappedname ='Record_Type';

UPDATE fieldspecification SET fieldname = 'rosterAYPSchoolIdentifier', fieldlength=4, rejectifempty = true, rejectifinvalid = true, mappedname = 'AYP_QPA_Bldg_No' WHERE fieldname = 'aypSchoolIdentifier' and mappedname isnull;

UPDATE fieldspecification SET fieldlength=30, mappedname = 'section' WHERE fieldname = 'courseSection' and mappedname = 'SCRS_section';

UPDATE fieldspecification SET allowablevalues = '{'',00,01,02,03,04,05,99}', fieldlength=2, rejectifempty = true, rejectifinvalid = true, mappedname = 'Course_Status' WHERE fieldname = 'enrollmentStatusCode' and mappedname = 'SCRS_enrollment_status';

UPDATE fieldspecification SET fieldlength=10, mappedname = 'teacher_identifier' WHERE fieldname = 'educatorIdentifier' and mappedname = 'SCRS_teacher_identifier';

-- End -- UPDATE statements for fieldspecification table -- US10197


-- Start -- INSERT statements for fieldspecification table -- US10197

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('legalLastName', NULL, NULL, NULL, 60, true, true, NULL, 'Student_Legal_Last_Name', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('legalFirstName', NULL, NULL, NULL, 60, true, true, NULL, 'Student_Legal_First_Name', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('legalMiddleName', NULL, NULL, NULL, 60, false, false, NULL, 'Student_Legal_Middle_Name', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('studentGenerationCode', '{Jr.,Jr,Sr.,Sr,II,III,IV,V,'',jr.,jr,sr.,sr,ii,iii,iv,v,JR.,JR,SR.,SR}', NULL, NULL, 10, false, false, NULL, 'Student_Generation_Code', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('studentGender', NULL, NULL, NULL, 1, false, false, NULL, 'Student_Gender', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('studentBirthDate', NULL, NULL, NULL, 22, true, true, NULL, 'Student_Birth_Date', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('rosterCurrentGradeLevel', '{00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18}', NULL, NULL, 2, true, true, NULL, 'Current_Grade_Level', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('rosterLocalStudentIdentifier', NULL, NULL, NULL, 20, true, true, NULL, 'Local_Student_Identifier', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('hispanicEthnicity', NULL, NULL, NULL, 1, true, true, NULL, 'Hispanic_Ethnicity', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('educatorSchoolIdentifier', NULL, NULL, NULL, 4, true, true, NULL, 'Educator_Bldg_No', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('stateSubjectCourseIdentifier', NULL, NULL, NULL, 17, true, true, NULL, 'KCC_ID', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('localCourseId', NULL, NULL, NULL, 50, false, false, NULL, 'Local_Course_ID', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('educatorLastName', NULL, NULL, NULL, 60, true, true, NULL, 'Teacher_Last_Name', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('educatorFirstName', NULL, NULL, NULL, 60, true, true, NULL, 'Teacher_First_Name', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('educatorMiddleName', NULL, NULL, NULL, 60, false, false, NULL, 'Teacher_Middle_Name', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

-- End -- INSERT statements for fieldspecification table -- US10197

				
-- Start -- INSERT statements for fieldspecificationsrecordtypes table -- US10197

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('legalLastName' ,'Student_Legal_Last_Name'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('legalFirstName' ,'Student_Legal_First_Name'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('legalMiddleName' ,'Student_Legal_Middle_Name'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('studentGender' ,'Student_Gender'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('studentBirthDate' ,'Student_Birth_Date'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('hispanicEthnicity' ,'Hispanic_Ethnicity'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('educatorSchoolIdentifier' ,'Educator_Bldg_No'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('stateSubjectCourseIdentifier' ,'KCC_ID'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('localCourseId' ,'Local_Course_ID'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('educatorLastName' ,'Teacher_Last_Name'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('educatorFirstName' ,'Teacher_First_Name'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('educatorMiddleName' ,'Teacher_Middle_Name'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('studentGenerationCode' ,'Student_Generation_Code'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('rosterCurrentGradeLevel' ,'Current_Grade_Level'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('rosterLocalStudentIdentifier' ,'Local_Student_Identifier'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('rosterAYPSchoolIdentifier' ,'AYP_QPA_Bldg_No'));

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('comprehensiveRace' ,'Comprehensive_Race'));

-- END -- INSERT statements for fieldspecificationsrecordtypes table -- US10197

    
-- Start -- INSERT statements for category table -- US10197

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid) values
	('Enrolled','0','Enrolled', (Select id from categorytype where typecode ='COURSE_ENROLLMENT_STATUS_CODE' and typename = 'Course Enrollment Status Code'));

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid) values
	('Completed(Pass)','1','Completed(Pass)', (Select id from categorytype where typecode ='COURSE_ENROLLMENT_STATUS_CODE' and typename = 'Course Enrollment Status Code'));

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid) values
	('Completed(Fail)','2','Completed(Fail)', (Select id from categorytype where typecode ='COURSE_ENROLLMENT_STATUS_CODE' and typename = 'Course Enrollment Status Code'));

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid) values
	('Completed(Audited)','3','Completed(Audited)', (Select id from categorytype where typecode ='COURSE_ENROLLMENT_STATUS_CODE' and typename = 'Course Enrollment Status Code'));

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid) values
	('Withdrawn','4','Withdrawn', (Select id from categorytype where typecode ='COURSE_ENROLLMENT_STATUS_CODE' and typename = 'Course Enrollment Status Code'));

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid) values
	('Incomplete','5','Incomplete', (Select id from categorytype where typecode ='COURSE_ENROLLMENT_STATUS_CODE' and typename = 'Course Enrollment Status Code'));

INSERT INTO category(categoryname, categorycode, categorydescription, categorytypeid) values
	('Record Submitted in Error','99','Record Submitted in Error', (Select id from categorytype where typecode ='COURSE_ENROLLMENT_STATUS_CODE' and typename = 'Course Enrollment Status Code'));

-- End -- INSERT statements for category table -- US10197

--PNP profile upload

INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser) 
VALUES ( 'assessmentNeedBackgroundColor', '2013-01-16 14:22:46.385305',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-16 14:22:46.385305',
(Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser) 
VALUES ( 'assessmentNeedSpokenSourcePreferenceType', '2013-01-16 22:38:38.637082',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-16 22:38:38.637082', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ( 'assessmentNeedReadAtStartPreference', '2013-01-16 22:38:57.168174',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-16 22:38:57.168174', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ( 'assessmentNeedBrailleGradeType', '2013-01-16 22:39:07.729345',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-16 22:39:07.729345', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ( 'assessmentNeedNumberOfBrailleDotsType', '2013-01-17 17:42:12.428362',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:12.428362', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ( 'assessmentNeedNumberOfBrailleCells', '2013-01-17 17:42:20.155303', 
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ( 'assessmentNeedBrailleMarkType', '2013-01-17 17:42:20.155303',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ( 'assessmentNeedBrailleDotPressure', '2013-01-17 17:42:20.155303',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser) 
VALUES ( 'assessmentNeedBrailleStatusCellType', '2013-01-17 17:42:20.155303',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ( 'brailleUsage', '2013-01-17 17:42:20.155303',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ( 'assessmentNeedMaskingType', '2013-01-17 17:42:20.155303',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser) 
VALUES ( 'assessmentNeedMagnification', '2013-01-17 17:42:20.155303',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser) 
VALUES ( 'assessmentNeedUserSpokenPreferenceType', '2013-01-17 17:42:20.155303',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ( 'tactileUsage', '2013-01-17 17:42:20.155303',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser) 
VALUES ( 'tactileApplication', '2013-01-17 17:42:20.155303', 
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));
INSERT INTO profileitemattribute ( attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ( 'assessmentNeedTimeMultiplier', '2013-01-17 17:42:20.155303',
(Select id from aartuser where username='cetesysadmin'), true, '2013-01-17 17:42:20.155303', (Select id from aartuser where username='cetesysadmin'));


insert into category(categoryname, categorycode, categorydescription, categorytypeid, 
       originationcode, createddate, createduser, activeflag, 
       modifieddate, modifieduser)
(
Select 'Personal Needs Profile' as categoryname,
'PERSONAL_NEEDS_PROFILE_RECORD_TYPE' as categorycode,
'Profile For Each student' as categorydescription,
(select id from categorytype where typecode='CSV_RECORD_TYPE') as categorytypeid,
'AART' as originationcode,now() as createddate,id as createduser,true as activeflag,
now() as modifieddate,id as modifieduser
from aartuser 
where
username='cetesysadmin'
);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedBackgroundColor', NULL, NULL, NULL, 10, false, false, NULL, 'Assessment_Need_Background_Color', true, '2013-01-16 10:13:58.305702', (Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-16 10:13:58.305702', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedSpokenSourcePreferenceType', '{Human,Synthetic}', NULL, NULL, 100, false, false, NULL, 'Assessment_Need_Spoken_Source_Preference_Type', true, '2013-01-16 22:29:38.321374', (Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-16 22:29:38.321374', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedNumberOfBrailleDotsType', '{6,8}', 6, 8, 5, false, false, NULL, 'Assessment_Need_Number_Of_Braille_Dots_Type', true, '2013-01-17 17:37:50.966403', (Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-17 17:37:50.966403', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedReadAtStartPreference', '{Yes,No}', NULL, NULL, 100, false, false, NULL, 'Assessment_Need_Read_At_Start_Preference', true, '2013-01-16 22:29:57.113622', (Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-16 22:29:57.113622', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedBrailleGradeType', '{Contracted,UnContracted}', NULL, NULL, 100, false, false, NULL, 'Assessment_Need_Braille_Grade_Type', true, '2013-01-16 22:30:18.367045', (Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-16 22:30:18.367045', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedNumberOfBrailleCells', NULL, 0, 99999, 5, false, false, NULL, 'Assessment_Need_Number_Of_Braille_Cells', true,
'2013-01-17 17:38:06.990286', (Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-17 17:38:06.990286', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedBrailleMarkType', '{Highlight,Bold,Underline,Italic,Strikeout,Color}', NULL, NULL, NULL, false, false, NULL,
'Assessment_Need_Braille_Mark_Type', true, '2013-01-17 18:38:53.766696', (Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-17 18:38:53.766696', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedBrailleDotPressure', NULL, NULL, NULL, 10, false, false, NULL, 'Assessment_Need_Braille_Dot_Pressure', true,
'2013-01-17 18:39:08.893974', (Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-17 18:39:08.893974', 
(Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedBrailleStatusCellType', '{Off,Left,Right}', NULL, NULL, NULL, false, false, NULL,
'Assessment_Need_Braille_Status_Cell_Type', true, '2013-01-17 18:39:43.580552', (Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-17 18:39:43.580552', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('tactileUsage', '{Yes,yes,No,no}', NULL, NULL, NULL, false, false, NULL, 'Tactile_Usage', true, '2013-01-17 18:46:31.797419',
(Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-17 18:46:31.797419', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('responsibleSchoolIdentifier', NULL, NULL, NULL, 10, true, true, NULL, 'Responsible_School_Identifier', true,
'2012-11-20 09:13:02.867602', (Select id from aartuser where username = 'cetesysadmin'), true, '2012-11-20 09:13:02.867602',
(Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) 
VALUES ('brailleUsage', NULL, NULL, NULL, NULL, false, false, NULL, 'Braille_Usage', true, '2013-01-17 18:46:31.797419',
(Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-17 18:46:31.797419',
(Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedMaskingType', '{CustomMask,AnswerMask}', NULL, NULL, NULL, false, false, NULL,
'Assessment_Need_Masking_Type', true, '2013-01-17 18:46:31.797419', (Select id from aartuser where username = 'cetesysadmin'), 
true, '2013-01-17 18:46:31.797419', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) 
VALUES ('assessmentNeedMagnification', NULL, NULL, NULL, 10, false, false, NULL, 'Assessment_Need_Magnification', true,
'2013-01-17 18:46:31.797419', (Select id from aartuser where username = 'cetesysadmin'), true,
'2013-01-17 18:46:31.797419', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedUserSpokenPreferenceType', '{TextOnly,TextGraphics,GraphicsOnly,NonVisuall}',
NULL, NULL, NULL, false, false, NULL, 'Assessment_Need_User_Spoken_Preference_Type', true, '2013-01-17 18:46:31.797419',
(Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-17 18:46:31.797419', (Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, fieldlength, 
rejectifempty, rejectifinvalid, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('assessmentNeedTimeMultiplier', 30, false, false, 'Assessment_Need_Time_Multiplier', true, '2013-01-17 18:46:31.797419',
(Select id from aartuser where username = 'cetesysadmin'), true, '2013-01-17 18:46:31.797419', 
(Select id from aartuser where username = 'cetesysadmin'));
INSERT INTO fieldspecification (fieldname, fieldlength,allowablevalues, rejectifempty, rejectifinvalid, mappedname,
 showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
('tactileApplication' , 30, '{tactile Audio File,tactile Audio Text,tactile Braille Text}',false, false, 'Tactile_Application', true, now(), 
(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

insert into fieldspecificationsrecordtypes(
fieldspecificationid,recordTypeid,createdDate,createdUser,activeFlag,modifieddate,modifieduser)
(select
 distinct f.id,
 (Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE') as recordTypeId,
 now() as createddate, 
 f.createduser,
 f.activeflag,
 now() as modifieddate,
 f.modifieduser
 from fieldspecification f
  where
    f.mappedname is not null
   and f.fieldname in ('assessmentNeedBackgroundColor',
'assessmentNeedSpokenSourcePreferenceType',
'assessmentNeedNumberOfBrailleDotsType',
'assessmentNeedReadAtStartPreference',
'assessmentNeedBrailleGradeType',
'assessmentNeedNumberOfBrailleCells',
'assessmentNeedBrailleMarkType',
'assessmentNeedBrailleDotPressure',
'assessmentNeedBrailleStatusCellType',
'tactileUsage',
'responsibleSchoolIdentifier',
'brailleUsage',
'assessmentNeedMaskingType',
'assessmentNeedMagnification',
'assessmentNeedUserSpokenPreferenceType',
'stateStudentIdentifier',
'assessmentNeedTimeMultiplier',
'tactileApplication') );   

INSERT INTO authorities(
            authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERSONAL_NEEDS_PROFILE_UPLOAD','Personal Needs Profile Upload',
    'Personal Needs Profile','2013-01-14 22:55:50.671619',
    (Select id from aartuser where username='cetesysadmin'),TRUE,'2013-01-14 22:55:50.671619',
    (Select id from aartuser where username='cetesysadmin')	);

--INFO: AART related tables. original 31.sql

INSERT INTO authorities(
            authority, displayname, objecttype, createduser, 
            activeflag, modifieduser)
    VALUES ('PERM_VIEW_NODES', 'View Nodes', 'DLM', 
     (Select id from aartuser where username='cetesysadmin'), 
            true,
             (Select id from aartuser where username='cetesysadmin') 
             );
             
--This for a defect in qa.
             
update authorities set objecttype='Personal Needs'
where authority = 'PERSONAL_NEEDS_PROFILE_UPLOAD';

INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
('100000', 'KANSAS_WEB_SERVICE_SCHEDULE_FREQUENCY_DELTA', 'This is the record of kids web service frequency delta.', 
(select id from categorytype where  typename = 'Web Service Url Type'), NULL, 'AART_ORIG_CODE',now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin'));

update profileitemattribute
set attributename=replace( attributename, 'assessmentNeed', '');

update profileitemattribute
set attributename=lower(substr( attributename, 1,1))
|| substr( attributename, 2,100);

UPDATE gradecourse set name = 'N.A.' || id where name = 'null';


--INFO: AART related tables. original 32.sql

--insert student test session test section status.
INSERT INTO categorytype (typename, typecode, typedescription, originationcode,
createddate,createduser,activeflag,modifieddate,modifieduser)
(Select 'Student Test Section Status' as typename,
'STUDENT_TESTSECTION_STATUS' as typecode,
'Section wise status of testing session' as typedescription,
originationcode,
now() as createddate,
createduser,
activeflag,
now() as modifieddate,
modifieduser from categorytype where typecode='STUDENT_TEST_STATUS');

--insert the statuses for test session test section status.
insert into category(categoryname,categorycode,categorydescription,
categorytypeid,originationcode,createddate,createduser,activeflag,
modifieddate,modifieduser)
(
Select cat.categoryname, cat.categorycode, cat.categorydescription, 
(Select id from categorytype
where typecode='STUDENT_TESTSECTION_STATUS') as categorytypeid,
 cat.originationcode, now() as createddate,
 cat.createduser, cat.activeflag,
 now() as modifieddate, cat.modifieduser
  from category cat,categorytype cattype
   where cat.categorytypeid=cattype.id
   and cattype.typecode='STUDENT_TEST_STATUS' 
);

INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, 
 originationcode, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
('In Progress Timed Out', 'inprogresstimedout', 'The student is kicked out because grace period exceeded', 
(select id from categorytype where  typecode = 'STUDENT_TESTSECTION_STATUS'), 'AART_ORIG_CODE',now(),
 (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin'));


INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, 
 originationcode, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
('Reactivated', 'reactivated', 'The student is reactivated after kicked out because grace period exceeded', 
(select id from categorytype where  typecode = 'STUDENT_TESTSECTION_STATUS'), 'AART_ORIG_CODE',now(),
 (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin'));

--update all statusids of studentstestsections as now they have their own statuses.
update studentstestsections
set statusid = (Select id from category where categorycode= (Select categorycode from category where id=statusid) 
and categorytypeid = (Select id from categorytype where typecode='STUDENT_TESTSECTION_STATUS') ),
modifieddate = now(),
modifieduser = (Select id from aartuser where username='cetesysadmin');





-- Start -- Update statements for fieldspecification table -- US10906 : Change Request: Web Service Roster (STCO) upload required field updates

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'rosterAYPSchoolIdentifier' and mappedname ='AYP_QPA_Bldg_No';

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'recordType' and mappedname ='Record_Type';

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'enrollmentStatusCode' and mappedname ='Course_Status';

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'studentBirthDate' and mappedname ='Student_Birth_Date';

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'hispanicEthnicity' and mappedname ='Hispanic_Ethnicity';

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'educatorSchoolIdentifier' and mappedname ='Educator_Bldg_No';

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'stateSubjectCourseIdentifier' and mappedname ='KCC_ID';

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'educatorLastName' and mappedname ='Teacher_Last_Name';

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'educatorFirstName' and mappedname ='Teacher_First_Name';

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'rosterCurrentGradeLevel' and mappedname ='Current_Grade_Level';

UPDATE fieldspecification SET rejectifempty = false WHERE fieldname = 'rosterLocalStudentIdentifier' and mappedname ='Local_Student_Identifier';


UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'rosterAYPSchoolIdentifier' and mappedname ='AYP_QPA_Bldg_No';

UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'recordType' and mappedname ='Record_Type';

UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'enrollmentStatusCode' and mappedname ='Course_Status';

UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'studentBirthDate' and mappedname ='Student_Birth_Date';

UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'hispanicEthnicity' and mappedname ='Hispanic_Ethnicity';

UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'educatorSchoolIdentifier' and mappedname ='Educator_Bldg_No';

UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'stateSubjectCourseIdentifier' and mappedname ='KCC_ID';

UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'educatorLastName' and mappedname ='Teacher_Last_Name';

UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'educatorFirstName' and mappedname ='Teacher_First_Name';

UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'rosterCurrentGradeLevel' and mappedname ='Current_Grade_Level';

UPDATE fieldspecification SET rejectifinvalid = false WHERE fieldname = 'rosterLocalStudentIdentifier' and mappedname ='Local_Student_Identifier';


UPDATE fieldspecification SET allowablevalues = '{0,1}' WHERE fieldname = 'studentGenerationCode' and mappedname ='Student_Generation_Code';


UPDATE fieldspecification SET formatregex = '(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(19|20)[0-9][0-9]' WHERE fieldname = 'studentBirthDate' and mappedname ='Student_Birth_Date';

UPDATE fieldspecification SET formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' WHERE fieldname = 'stateStudentIdentifier' and mappedname ='State_Student_Identifier';

UPDATE fieldspecification SET formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' WHERE fieldname = 'legalLastName' and mappedname ='Student_Legal_Last_Name';

UPDATE fieldspecification SET formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' WHERE fieldname = 'legalFirstName' and mappedname ='Student_Legal_First_Name';

UPDATE fieldspecification SET formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' WHERE fieldname = 'attendanceSchoolProgramIdentifier' and mappedname ='Attendance_Bldg_No';

UPDATE fieldspecification SET formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' WHERE fieldname = 'courseSection' and mappedname ='section';


UPDATE fieldspecification SET allowablevalues = '{TEST}' WHERE fieldname = 'recordType' and mappedname ='Record_Type';

-- End -- Update statements for fieldspecification table


-- Start -- Insert statements for fieldspecification table -- US10906 : Change Request: Web Service Roster (STCO) upload required field updates

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
				('rosterRecordType', '{STCO}', NULL, NULL, 60, true, true, NULL, 'Record_Type', true, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

-- End -- Insert statements for fieldspecification table


-- Start -- Update statements for fieldspecificationsrecordtypes table -- US10906 : Change Request: Web Service Roster (STCO) upload required field updates

UPDATE fieldspecificationsrecordtypes SET fieldspecificationid = (SELECT id FROM fieldspecification WHERE (fieldname,mappedname) in (('rosterRecordType' ,'Record_Type')))
	 WHERE fieldspecificationid = ( SELECT id FROM fieldspecification WHERE (fieldname,mappedname) in (('recordType','Record_Type'))) AND recordtypeid = (Select id from category where categorycode='ROSTER_RECORD_TYPE');

-- End -- Update statements for fieldspecificationsrecordtypes table

--this is for Test collection flags.

INSERT INTO categorytype (typename, typecode, typedescription, originationcode,
createddate,createduser,activeflag,modifieddate,modifieduser)
(Select 'Session Rules' as typename,
'SESSION_RULES' as typecode,
'Session rules in testing lifecycle' as typedescription,
originationcode,
now() as createddate,
createduser,
activeflag,
now() as modifieddate,
modifieduser from categorytype where typecode='STUDENT_TEST_STATUS');

INSERT INTO category (categoryname, categorycode,
categorydescription, categorytypeid, 
 originationcode, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
('System Defined Enrollment to Test', 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST',
'The student is enrolled to a test with in a collection by the system.', 
(select id from categorytype where  typecode = 'SESSION_RULES'), 'AART_ORIG_CODE',now(),
 (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin'));

INSERT INTO category (categoryname, categorycode,
categorydescription, categorytypeid, 
 originationcode, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
('Manual Enrollment to Test', 'MANUAL_DEFINED_ENROLLMENT_TO_TEST',
'The student is enrolled to a test with in a collection by the user.', 
(select id from categorytype where  typecode = 'SESSION_RULES'), 'AART_ORIG_CODE',now(),
 (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin'));

INSERT INTO category (categoryname, categorycode,
categorydescription, categorytypeid, 
 originationcode, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
('Ticketed At Test', 'TICKETED_AT_TEST',
'Ticket numbers will be generated at the test level', 
(select id from categorytype where  typecode = 'SESSION_RULES'), 'AART_ORIG_CODE',now(),
 (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin'));

INSERT INTO category (categoryname, categorycode,
categorydescription, categorytypeid, 
 originationcode, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
('Ticketed At Section', 'TICKETED_AT_SECTION',
'Ticket numbers will be generated at the section level', 
(select id from categorytype where  typecode = 'SESSION_RULES'), 'AART_ORIG_CODE',now(),
 (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin'));

INSERT INTO category (categoryname, categorycode,
categorydescription, categorytypeid, 
 originationcode, createddate, createduser, activeflag, modifieddate, modifieduser) VALUES 
('Grace Period', 'GRACE_PERIOD',
'Grace Period at the test collection after which student will be kicked out of the section and not be allowed back in to test.', 
(select id from categorytype where  typecode = 'SESSION_RULES'), 'AART_ORIG_CODE',now(),
 (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin'));