--F457 		
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES
(
	'Student Reports Import',
	'STUDENT_REPORTS_IMPORT',
	'Student Reports Import',
	(SELECT id FROM categorytype WHERE typecode = 'CSV_RECORD_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
),
(
	'Student Summary Reports Import',
	'STUDENT_SUMMARY_REPORTS_IMPORT',
	'Student Summary Reports Import',
	(SELECT id FROM categorytype WHERE typecode = 'CSV_RECORD_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
),
(
	'School Reports Import',
	'SCHOOL_REPORTS_IMPORT',
	'School Reports Import',
	(SELECT id FROM categorytype WHERE typecode = 'CSV_RECORD_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
),
(
	'Classroom Reports Import',
	'CLASSROOM_REPORTS_IMPORT',
	'Classroom Reports Import',
	(SELECT id FROM categorytype WHERE typecode = 'CSV_RECORD_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
),
(
	'School CSV Reports Import',
	'SCHOOL_CSV_REPORTS_IMPORT',
	'School CSV Reports Import',
	(SELECT id FROM categorytype WHERE typecode = 'CSV_RECORD_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
),
(
	'Classroom CSV Reports Import',
	'CLASSROOM_CSV_REPORTS_IMPORT',
	'Classroom CSV Reports Import',
	(SELECT id FROM categorytype WHERE typecode = 'CSV_RECORD_TYPE'),
	'AART_ORIG_CODE',
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	NOW(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
);
	
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('filename', null, null, null, null, true,
	 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'); 

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('teacherID', null, null, null, null, false,
	 false, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'Number'); 

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('school', null, null, null, null, true,
	 false, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'); 
	 
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('district', null, null, null, null, true,
	 false, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'); 	 
	 
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('reportType', '{Student,StudentSummary,School,Classroom,School_csv,Classroom_csv}', null, null, null, true,
	 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'); 
	 
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('level1Text', null, null, null, null, false,
	 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'); 
	 
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('level2Text', null, null, null, null, false,
	 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'); 	 
	 
--student individual import
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'reportType'), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Report_Type'
);	
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'filename'), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Filename'
);	 
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'assessmentProgram'), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Assessment program'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'state' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'State'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'district' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'District'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'school' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'subject' and rejectifempty is false), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Subject'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'grade' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Grade'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'studentId' and rejectifempty is true and mappedname = 'Student_Id'), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'EP_Student_ID'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'level1Text'), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Level_1_text_beneath_student'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'level2Text'), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Level_2_text_beneath_student'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'schoolYear'), 
(select id from category where categorycode='STUDENT_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School_Year'
);

--student summary import
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'reportType'), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Report_Type'
);	
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'filename'), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Filename'
);	 
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'assessmentProgram'), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Assessment program'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'state' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'State'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'district' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'District'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'school' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'subject' and rejectifempty is false), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Subject'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'grade' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Grade'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'studentId' and rejectifempty is true and mappedname = 'Student_Id'), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'EP_Student_ID'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'level1Text'), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Level_1_text_beneath_student'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'level2Text'), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Level_2_text_beneath_student'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'schoolYear'), 
(select id from category where categorycode='STUDENT_SUMMARY_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School_Year'
);

--school import
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'reportType'), 
(select id from category where categorycode='SCHOOL_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Report_Type'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'filename'), 
(select id from category where categorycode='SCHOOL_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Filename'
);	 
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'assessmentProgram'), 
(select id from category where categorycode='SCHOOL_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Assessment program'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'state' and rejectifempty is true), 
(select id from category where categorycode='SCHOOL_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'State'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'district' and rejectifempty is true), 
(select id from category where categorycode='SCHOOL_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'District'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'school' and rejectifempty is true), 
(select id from category where categorycode='SCHOOL_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'teacherID' and rejectifempty is false), 
(select id from category where categorycode='SCHOOL_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Teacher_ID'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'subject' and rejectifempty is false), 
(select id from category where categorycode='SCHOOL_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Subject'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'grade' and rejectifempty is false), 
(select id from category where categorycode='SCHOOL_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Grade'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'schoolYear'), 
(select id from category where categorycode='SCHOOL_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School_Year'
);

--Classroom
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'reportType'), 
(select id from category where categorycode='CLASSROOM_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Report_Type'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'filename'), 
(select id from category where categorycode='CLASSROOM_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Filename'
);	 
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'assessmentProgram'), 
(select id from category where categorycode='CLASSROOM_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Assessment program'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'state' and rejectifempty is true), 
(select id from category where categorycode='CLASSROOM_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'State'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'district' and rejectifempty is true), 
(select id from category where categorycode='CLASSROOM_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'District'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'school' and rejectifempty is true), 
(select id from category where categorycode='CLASSROOM_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'teacherID' and rejectifempty is false), 
(select id from category where categorycode='CLASSROOM_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Teacher_ID'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'subject' and rejectifempty is false), 
(select id from category where categorycode='CLASSROOM_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Subject'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'grade' and rejectifempty is false), 
(select id from category where categorycode='CLASSROOM_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Grade'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'schoolYear'), 
(select id from category where categorycode='CLASSROOM_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School_Year'
);

--School CSV
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'reportType'), 
(select id from category where categorycode='SCHOOL_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Report_Type'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'filename'), 
(select id from category where categorycode='SCHOOL_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Filename'
);	 
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'assessmentProgram'), 
(select id from category where categorycode='SCHOOL_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Assessment program'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'state' and rejectifempty is true), 
(select id from category where categorycode='SCHOOL_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'State'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'district' and rejectifempty is true), 
(select id from category where categorycode='SCHOOL_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'District'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'school' and rejectifempty is true), 
(select id from category where categorycode='SCHOOL_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'teacherID' and rejectifempty is false), 
(select id from category where categorycode='SCHOOL_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Teacher_ID'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'subject' and rejectifempty is false), 
(select id from category where categorycode='SCHOOL_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Subject'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'grade' and rejectifempty is false), 
(select id from category where categorycode='SCHOOL_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Grade'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'schoolYear'), 
(select id from category where categorycode='SCHOOL_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School_Year'
);

--Classroom CSV
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'reportType'), 
(select id from category where categorycode='CLASSROOM_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Report_Type'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'filename'), 
(select id from category where categorycode='CLASSROOM_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Filename'
);	 
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'assessmentProgram'), 
(select id from category where categorycode='CLASSROOM_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Assessment program'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'state' and rejectifempty is true), 
(select id from category where categorycode='CLASSROOM_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'State'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'district' and rejectifempty is true), 
(select id from category where categorycode='CLASSROOM_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'District'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'school' and rejectifempty is true), 
(select id from category where categorycode='CLASSROOM_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'teacherID' and rejectifempty is false), 
(select id from category where categorycode='CLASSROOM_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Teacher_ID'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'subject' and rejectifempty is false), 
(select id from category where categorycode='CLASSROOM_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Subject'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'grade' and rejectifempty is false), 
(select id from category where categorycode='CLASSROOM_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Grade'
);
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'schoolYear'), 
(select id from category where categorycode='CLASSROOM_CSV_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School_Year'
);
