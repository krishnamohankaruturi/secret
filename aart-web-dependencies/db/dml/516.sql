--516.sql 
-- include/exclude csv record type 
insert into category (categoryname, categorycode, categorydescription, originationcode, activeflag, categorytypeid, createduser, modifieduser)
values('Include/Exclude Students', 'INCLUDE_EXCLUDE_STUDENTS', 'Include/Exclude students to/from Operational Test Window.','AART', true,
(select id from categorytype where typecode='CSV_RECORD_TYPE'), 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'));

-- include/exclude student from test window
insert into fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid,
showerror, createduser, activeflag, modifieduser, iskeyvaluepairfield, fieldtype)
values('exclude','{I,E}',null, null, 1, true, true, true, 
(select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), false, null);

-- organization field for include/exclude record type
insert into fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid,
showerror, createduser, activeflag, modifieduser, iskeyvaluepairfield, fieldtype)
values('organization', null, null,null, 30, true, true, true, 
(select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), false, null);

-- organization field for include/exclude record type
insert into fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid,
showerror, createduser, activeflag, modifieduser, iskeyvaluepairfield, fieldtype)
values('course', null, null,null, 30, true, true, true, 
(select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), false, null);

--  include/exclude field 
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'exclude'), (select id from category where categorycode='INCLUDE_EXCLUDE_STUDENTS'), 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Include/Exclude');

-- subject field (Reusing existing field)
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select min(id) from fieldspecification where fieldname  = 'subject'), 
(select id from category where categorycode='INCLUDE_EXCLUDE_STUDENTS'), 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Subject');

-- Course field (Reusing existing field)
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select min(id) from fieldspecification where fieldname  = 'course'), 
(select id from category where categorycode='INCLUDE_EXCLUDE_STUDENTS'), 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Course');

-- State student identifier (Reusing existing field)
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select min(id) from fieldspecification where fieldname  = 'stateStudentIdentifier'), (select id from category where categorycode='INCLUDE_EXCLUDE_STUDENTS'), 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'State Student Identifier');

-- Attendance School Program Identifier (Reusing existing field)
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select min(id) from fieldspecification where fieldname  = 'attendanceSchoolProgramIdentifier'), (select id from category where categorycode='INCLUDE_EXCLUDE_STUDENTS'), 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Attendance School Program Identifier');

-- Organization field
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
values((select id from fieldspecification where fieldname  = 'organization'), 
(select id from category where categorycode='INCLUDE_EXCLUDE_STUDENTS'), 
(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'),
'Organization');

-- course is a non mandatory field.
update fieldspecification set rejectifempty = false where fieldname = 'course';

-- To fix issue with Braille DE12775
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='assignedSupport'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);