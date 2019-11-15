--dml/714.sql

--F678
--Insert Category table for csv file
			INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
			VALUES ( 'Student DCPS Report Import', 'STUDENT_DCPS_REPORTS_IMPORT', 'Student DCPS Report Import', (select id from categorytype  where typecode ='CSV_RECORD_TYPE'), null,
            null,now(), (select id from aartuser where username  ='cetesysadmin'), true,now(), 
            (select id from aartuser where username  ='cetesysadmin'));
			
			
--Insert Category for report type			
			INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
			VALUES ( 'Student (DCPS)', 'ALT_ST_DCPS', 'DLM DCPS REPORT', (select id from categorytype  where typecode ='REPORT_TYPES_UI'), null,
            null,now(), (select id from aartuser where username  ='cetesysadmin'), true,now(), 
            (select id from aartuser where username  ='cetesysadmin'));

--Insert authorities
						
			insert into authorities 
			(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,labelname,level,sortorder)
			select 'VIEW_DLM_STUDENT_DCPS','View DLM Student (DCPS)','Reports-Performance Reports',NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),'Reports','Alternate Assessments',null,1,
           ((select sortorder from authorities where authority='VIEW_ALT_YEAREND_STATE_REPORT')+
           (select sortorder from authorities where authority='VIEW_ALT_YEAREND_STD_IND_REP'))/2
			where not exists(
			select 1 from authorities where authority='VIEW_DLM_STUDENT_DCPS'
			);   
        

---Insert script for fieldspecificationsrecordtypes
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'reportType'), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Report_Type'
);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'filename'), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Filename'
);
	 
insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'assessmentProgram'), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Assessment program'
);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'state' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'State'
);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'district' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'District'
);


insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'school' and rejectifempty is true), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School'
);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'subject' and rejectifempty is false and mappedname is null), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Subject'
);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'grade' and rejectifempty is true and mappedname is null), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Grade'
);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'studentId' and rejectifempty is true and mappedname = 'Student_Id'), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'EP_Student_ID'
);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'level1Text'), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Level_1_text_beneath_student'
);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'level2Text'), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'Level_2_text_beneath_student'
);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createduser, modifieduser, mappedname)
	values
(
(select id from fieldspecification where fieldname = 'schoolYear'), 
(select id from category where categorycode='STUDENT_DCPS_REPORTS_IMPORT' and categorytypeid=(select id from	categorytype where typecode = 'CSV_RECORD_TYPE')), 
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'),
'School_Year'
);

--Updata script for fieldspecification
update fieldspecification set allowablevalues ='{Student,StudentSummary,School,Classroom,School_csv,Classroom_csv,StudentDCPS}' where id=(select id from fieldspecification where fieldname='reportType' and rejectifinvalid is true and activeflag=true);
			--select * from fieldspecification where fieldname='reportType' 
			--Insert reportassessmentprogram
			INSERT INTO reportassessmentprogram(
             reporttypeid, assessmentprogramid, createdate, modifieddate, 
            readytoview, activeflag, stateid, subjectid, authorityid)
    VALUES ((select id from category where categorycode = 'ALT_ST_DCPS' and categorytypeid = (select id from categorytype where typecode = 'REPORT_TYPES_UI')), (select id from assessmentprogram where abbreviatedname='DLM' and activeflag is true), now(), now(), 
            true, true, ( select id from organization where displayidentifier = 'DE' and organizationtypeid = (select id from organizationtype where typecode = 'ST') ), null, (select id from authorities where authority = 'VIEW_DLM_STUDENT_DCPS'));

	
--Insert reportassessmentprogramgroup			
			INSERT INTO reportassessmentprogramgroup(
            reportassessmentprogramid, groupid, activeflag)
			VALUES ((select id from reportassessmentprogram where assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM') and reporttypeid = (select id from category where categorycode ='ALT_ST_DCPS')),(select id from groups where groupcode = 'SSAD'), true);

			
--Insert groupauthoritiesexclusion
			INSERT INTO public.groupauthoritiesexclusion(
             groupid, authorityid, assessmentprogramid, stateid, activeflag, 
            createddate, modifieddate)
    VALUES ((select id from groups where groupcode = 'TEA'), (select id from authorities where authority = 'VIEW_DLM_STUDENT_DCPS'), (select id from assessmentprogram where abbreviatedname='DLM' and activeflag is true),( select id from organization where displayidentifier = 'DE' and organizationtypeid = (select id from organizationtype where typecode = 'ST')),true, 
            now(), now());
