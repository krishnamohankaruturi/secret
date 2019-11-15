update enrollment set currentgradelevel = null
where currentgradelevel is not null and currentgradelevel
not in (select id from gradecourse);

delete from category where categorytypeid in
(select id from categorytype where typecode ='GRADE_TYPE_CODE');

delete from categorytype where typecode ='GRADE_TYPE_CODE';

--remove test types

delete from category where categorytypeid in
(Select id from categorytype where typecode = 'TEST_TYPE_CODE');

delete from categorytype where typecode = 'TEST_TYPE_CODE';

--remove the test subjects

delete from category where categorytypeid in
(Select id from categorytype where typecode = 'SUBJECT_CODE');

delete from categorytype where typecode='SUBJECT_CODE';


--Original 5.sql
--TODO execute this in DEV.
--this is for restricting enrollments.
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid) VALUES
    ('Enrollment Resource','ENROLLMENT_RESOURCE','This is the roster that gets created',
    (select id from categorytype where typecode = 'RESTRICTED_RESOURCE_TYPE'))
    except (select categoryname, categorycode, categorydescription, categorytypeid
    from category);

-- This is common to all organizations.IF one organization wants to be different then a seperate entry has to be made.
INSERT INTO restriction (restrictionname, restrictioncode, restrictiondescription, restrictedresourcetypeid)
VALUES ('Top Down For Enrollments', 'TOP_DOWN_ENROLLMENTS',
'This is the restriction that applies for all organizations in this system',
(select id from category where categorycode = 'ENROLLMENT_RESOURCE') );

--'PERM_ENROLLMENTRECORD_VIEW','PERM_ENROLLMENTRECORD_SEARCH' to parent.
insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,true as isparent,false as ischild,false as isdifferential
 from restriction,authorities where authorities.authority in ('PERM_ENRL_UPLOAD'));

--'PERM_ENROLLMENTRECORD_VIEW','PERM_ENROLLMENTRECORD_MODIFY','PERM_ENROLLMENTRECORD_CREATE',
--'PERM_ENROLLMENTRECORD_DELETE','PERM_ENROLLMENTRECORD_SEARCH','PERM_ENROLLMENTRECORD_ARCHIVE',
--'PERM_ENROLLMENTRECORD_UPLOAD' to current org/ownership org.

insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,false as isparent,false as ischild,false as isdifferential
 from restriction,authorities where authorities.authority in ('PERM_ENRL_UPLOAD'));

 --none for child.

 --no differential permission.

-- insert this restriction to all organizations.

insert into restrictionsorganizations(restrictionid,organizationid,isenforced)
(Select restriction.id as restrictionid,organization.id as organizationid,true from organization,restriction
where (restriction.id,organization.id) not in (select restrictionid,organizationid from restrictionsorganizations));
 
 --now update all the rosters to the newly created restriction.
update
enrollment
set
restrictionid = (select id from restriction where restrictioncode = 'TOP_DOWN_ENROLLMENTS');

--add the not null constraint.This can only be added after the data and hence it is added here.

ALTER TABLE enrollment
   ALTER COLUMN restrictionid SET NOT NULL;
   
--in qa this not been inserted yet.

INSERT INTO testingprogram(
            programname, assessmentprogramid, originationcode)
    (
    Select  'Un Known Testing Program', (select id from assessmentprogram limit 1), 'AART_ORIG_CODE'
    where not exists (select 1 from testingprogram where programname = 'Un Known Testing Program') 
    );

    
    
--Original 6.sql
--insert the web service parameters.
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode) 
 VALUES 
 ('08/01/2012 01:00:00 AM', 'KANSAS_IMMEDIATE_WEB_SERVICE_START_TIME',
 'This is the start time from which the records are pulled',
 (Select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE' ),
 'AART_ORIG_CODE');
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode) 
 VALUES 
 ('08/01/2012 02:00:00 AM', 'KANSAS_SCHEDULED_WEB_SERVICE_START_TIME',
 'This is the start time from which the records are pulled',
 (Select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE' ), 'AART_ORIG_CODE');
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode) 
 VALUES 
 ('08/01/2012 04:00:00 AM', 'KANSAS_IMMEDIATE_WEB_SERVICE_END_TIME',
 'This is the end time untill which the records are pulled',
 (Select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE' ), 'AART_ORIG_CODE');
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode) 
 VALUES 
 ('08/01/2012 05:00:00 AM', 'KANSAS_SCHEDULED_WEB_SERVICE_END_TIME',
 'This is the end time from which the records are pulled',
 (Select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE' ), 'AART_ORIG_CODE');
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode) 
 VALUES 
 ('2012', 'KANSAS_IMMEDIATE_WEB_SERVICE_SCHOOL_YEAR',
 'This is the school year for which the records are pulled',
 (Select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE' ), 'AART_ORIG_CODE');
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode) 
 VALUES 
 ('2012 ', 'KANSAS_SCHEDULED_WEB_SERVICE_SCHOOL_YEAR',
 'This is the school year for which the records will be pulled',
 (Select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE' ), 'AART_ORIG_CODE');
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode) 
 VALUES 
 ('Kids By Date Input Parameter For Immediate Upload', 'KIDS_BY_DATE_INPUT_PARAMETER_IMMEDIATE_UPLOAD',
 'This is the record of kids web service input parameter.',
 (Select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE' ), 'AART_ORIG_CODE');
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode) 
 VALUES 
 ('Kids By Date Input Parameter For Scheduled Upload', 'KIDS_BY_DATE_INPUT_PARAMETER_SCHEDULED_UPLOAD',
 'This is the record of kids web service input parameter.',
 (Select id from categorytype where typecode = 'WEB_SERVICE_RECORD_TYPE' ), 'AART_ORIG_CODE');

 --insert for webservice configuration
 
 INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode) 
 VALUES 
 ('999999', 'TOMCAT_START_TIME_CODE',
 'This is the tomcat start time',
 (Select id from categorytype where typecode = 'KANSAS_WEB_SERVICE_CONFIG_TYPE_CODE' ), 'AART_ORIG_CODE');
 
 INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, originationcode) 
 VALUES 
 ('9999999', 'RETRY_TIME_CODE',
 'This is the retry time up on failure',
 (Select id from categorytype where typecode = 'KANSAS_WEB_SERVICE_CONFIG_TYPE_CODE' ), 'AART_ORIG_CODE');
 
 
 --insert field specification for parsing input parameters.
 
 INSERT INTO fieldspecification
(fieldname, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, mappedname, showerror)
VALUES
('currentSchoolYear', 1900, 2099, 4, true, true, 'KANSAS_IMMEDIATE_WEB_SERVICE_SCHOOL_YEAR', false);
INSERT INTO fieldspecification
(fieldname, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, mappedname, showerror)
VALUES
('currentSchoolYear', 1900, 2099, 4, true, true, 'KANSAS_SCHEDULED_WEB_SERVICE_SCHOOL_YEAR', false);
INSERT INTO fieldspecification
(fieldname, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, mappedname, showerror)
VALUES
('strFromDate', NULL, NULL, NULL, true, true, 'KANSAS_SCHEDULED_WEB_SERVICE_START_TIME', false);
INSERT INTO fieldspecification
(fieldname, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, mappedname, showerror)
VALUES
('strToDate', NULL, NULL, NULL, true, true, 'KANSAS_SCHEDULED_WEB_SERVICE_END_TIME', false);
INSERT INTO fieldspecification
(fieldname, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, mappedname, showerror)
VALUES
('strFromDate', NULL, NULL, NULL, true, true, 'KANSAS_IMMEDIATE_WEB_SERVICE_START_TIME', false);
INSERT INTO fieldspecification
(fieldname, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, mappedname, showerror)
VALUES
('strToDate', NULL, NULL, NULL, true, true, 'KANSAS_IMMEDIATE_WEB_SERVICE_END_TIME', false);

--insert the field specification for immediate upload.

insert into fieldspecificationsrecordtypes(fieldspecificationid,recordtypeid)
(Select fspec.id,cat.id from category cat,fieldspecification fspec
 where
  cat.categorycode = 'KIDS_BY_DATE_INPUT_PARAMETER_IMMEDIATE_UPLOAD' and
  fspec.mappedname like '%IMMEDIATE%');
  
--insert the field specifications for scheduled upload.

insert into fieldspecificationsrecordtypes(fieldspecificationid,recordtypeid)
(Select fspec.id,cat.id from category cat,fieldspecification fspec
 where
  cat.categorycode = 'KIDS_BY_DATE_INPUT_PARAMETER_SCHEDULED_UPLOAD' and
  fspec.mappedname like '%SCHEDULED%');
  
--for recordType for KidRecord.

insert into fieldspecification(fieldname,allowablevalues,fieldlength,rejectifempty,rejectifinvalid,mappedname)
values ('recordType','{TEST}',4,true,true,'RECORD_TYPE');


insert into fieldspecificationsrecordtypes values
(
(Select id from fieldspecification where fieldname = 'recordType'),
(Select id from category where categorycode='KID_RECORD_TYPE')
);

update fieldspecification set showerror = (rejectifempty or rejectifInvalid)
where id in (select fieldspecificationid from fieldspecificationsrecordtypes,category recordType
where recordType.id = recordTypeId and recordType.categoryCode = 'KID_RECORD_TYPE');     