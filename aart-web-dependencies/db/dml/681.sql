--dml/680.sql 

--F632
delete from groupauthorities where authorityid = (select id from authorities where authority='REACTIVATE_HS_STDNT_TESTSESSION');
delete from authorities where authority='REACTIVATE_HS_STDNT_TESTSESSION';
delete from groupauthorities where authorityid = (select id from authorities where authority='END_HS_STDNT_TESTSESSION');
delete from authorities where authority='END_HS_STDNT_TESTSESSION';

-- F570 - PredictiveReports
--Reporting cycle
INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag,
             modifieddate, modifieduser)
    VALUES ( 'Reporting cycle', 'REPORT_CYCLE', 'Reporting cycle values(like Fall, Winter, Spring)', null, null,
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
     now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Fall', 'CURRENT_INTERIM_REPORT_CYCLE', 'Current interim reporting cycle',
    (select id from  categorytype where typecode ='REPORT_CYCLE' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

--set up operational test window ids, moved otw id to testingcycle table
/*
INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag,
             modifieddate, modifieduser)
    VALUES ( 'Operational test window group', 'INTERIM_PREDICTIVE_REPORT_OTW', 'Predictive testing window type used to identify specific OTW ID', null, null,
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
     now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( '11111', 'Fall_INTERIM_PREDICTIVE_OTW_ID', 'Operational test window id for Fall predictive reports',
    (select id from  categorytype where typecode ='INTERIM_PREDICTIVE_REPORT_OTW' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( '22222', 'Winter_INTERIM_PREDICTIVE_OTW_ID', 'Operational test window id for Winter predictive reports',
    (select id from  categorytype where typecode ='INTERIM_PREDICTIVE_REPORT_OTW' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( '33333', 'Spring_INTERIM_PREDICTIVE_OTW_ID', 'Operational test window id for Spring predictive reports',
    (select id from  categorytype where typecode ='INTERIM_PREDICTIVE_REPORT_OTW' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));
*/
    
--Score range display reasons on graph    
INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag,
             modifieddate, modifieduser)
    VALUES ( 'No score range reason', 'NO_SCORE_RANGE_REASON', 'Reason for score range not displayed', null, null,
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
     now(), (select id from aartuser where username = 'cetesysadmin'));


INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Student not tested', 'STUDENT_NOT_TESTED', 'Student did not take interim predictive test',
    (select id from  categorytype where typecode ='NO_SCORE_RANGE_REASON' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Test not completed', 'TEST_NOT_COMPLETED', 'All questions were not answered',
    (select id from  categorytype where typecode ='NO_SCORE_RANGE_REASON' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

--Earned credit
INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag,
             modifieddate, modifieduser)
    VALUES ( 'Earned credit rating', 'CREDIT_EARNED', 'Credit received for a question based on response score', null, null,
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
     now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'No Credit', 'NO_CREDIT', 'Student did not receive credit on a question',
    (select id from  categorytype where typecode ='CREDIT_EARNED' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Partial Credit', 'PARTIAL_CREDIT', 'Student received partial credit on a question',
    (select id from  categorytype where typecode ='CREDIT_EARNED' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Full Credit', 'FULL_CREDIT', 'Student received full credit on a question',
    (select id from  categorytype where typecode ='CREDIT_EARNED' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Question Unanswered', 'QUESTION_UNANSWERED', 'Student did not answer to the question',
    (select id from  categorytype where typecode ='CREDIT_EARNED' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));


INSERT INTO batchjobschedule( 
            jobname, jobrefname, initmethod, cronexpression, scheduled,  
            allowedserver) 
VALUES ('Interim PredictiveReport Calculations and ISRs', 'predictiveRptCalcsScheduler', 'startPredictiveRptCalcProcess', '0 0/30 * * * ?', false, 'localhost');

--New upload file for Question information
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Question Information', 'QUESTION_INFORMATION', 'Question information upload file',
    (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE'),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

--Fieldspecifications
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser, 
iskeyvaluepairfield, fieldtype)values('testingProgramName',null,'{Summative,Interim}',null,null,true,true,true,(Select id from aartuser where username='cetesysadmin'), 
(Select id from aartuser where username='cetesysadmin'),false, 'String');

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser, 
iskeyvaluepairfield, fieldtype)values('reportCycle',null,'{Fall,Winter,Spring}',null,null,false,false,false,(Select id from aartuser where username='cetesysadmin'), 
(Select id from aartuser where username='cetesysadmin'),false, 'String'); 

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='RAW_TO_SCALE_SCORE') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Testing_Program' as mappedname from fieldspecification where fieldname = 'testingProgramName'); 

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='RAW_TO_SCALE_SCORE') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Report_Cycle' as mappedname from fieldspecification where fieldname = 'reportCycle');

--TestCutScores
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='TEST_CUT_SCORES') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Testing_Program' as mappedname from fieldspecification where fieldname = 'testingProgramName'); 

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='TEST_CUT_SCORES') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Report_Cycle' as mappedname from fieldspecification where fieldname = 'reportCycle'); 

--LevelDescription
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='LEVEL_DESCRIPTIONS') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Testing_Program' as mappedname from fieldspecification where fieldname = 'testingProgramName'); 

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='LEVEL_DESCRIPTIONS') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Report_Cycle' as mappedname from fieldspecification where fieldname = 'reportCycle'); 

--Question Information
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'School_Year' as mappedname from fieldspecification where fieldname = 'schoolYear');

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Assessment_Program' as mappedname from fieldspecification where fieldname = 'assessmentProgram');

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Testing_Program' as mappedname from fieldspecification where fieldname = 'testingProgramName'); 

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Grade' as mappedname from fieldspecification where fieldname = 'grade' and mappedname is null);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Subject' as mappedname from fieldspecification where fieldname = 'subject' and mappedname is null and rejectifempty is true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Report_Cycle' as mappedname from fieldspecification where fieldname = 'reportCycle'); 

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser, 
iskeyvaluepairfield, fieldtype)values('externalTestId',null,null,null,null,true,true,true,(Select id from aartuser where username='cetesysadmin'), 
(Select id from aartuser where username='cetesysadmin'),false, 'Number');

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'TestID' as mappedname from fieldspecification where fieldname = 'externalTestId'); 

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser, 
iskeyvaluepairfield, fieldtype)values('taskVariantExternalId',null,null,null,null,true,true,true,(Select id from aartuser where username='cetesysadmin'), 
(Select id from aartuser where username='cetesysadmin'),false, 'Number');

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Task_Variant_ID' as mappedname from fieldspecification where fieldname = 'taskVariantExternalId');

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser, 
iskeyvaluepairfield, fieldtype)values('questionDescription',null,null,null,null,true,true,true,(Select id from aartuser where username='cetesysadmin'), 
(Select id from aartuser where username='cetesysadmin'),false, 'String');

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Question_Description' as mappedname from fieldspecification where fieldname = 'questionDescription');

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser, 
iskeyvaluepairfield, fieldtype)values('creditPercent',null,null,null,null,true,true,true,(Select id from aartuser where username='cetesysadmin'), 
(Select id from aartuser where username='cetesysadmin'),false, 'Number');

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Credit_Percent' as mappedname from fieldspecification where fieldname = 'creditPercent');

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname) 
(Select id as fieldspecificationid, (select id from category where categorytypeid  in (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE') and categorycode='QUESTION_INFORMATION') recordtypeid,createduser, now() as createddate, 
modifieduser,now() as modifieddate, 'Comment' as mappedname from fieldspecification where fieldname = 'comment');

--Update testingprogram value to Summative on existing data in rawtoscalescore, testcutscore and leveldescription tables

select updateTestingProgramInReportUploads('testcutscores');
select updateTestingProgramInReportUploads('rawtoscalescores');
select updateTestingProgramInReportUploads('leveldescription');
select updateTestingProgramInReportUploads('reportprocess');


INSERT INTO public.testingcycle(id, schoolyear, assessmentprogramid, testingprogramid, testingcyclename, sortorder, 
    activeflag, createduser, modifieduser, createddate, modifieddate, operationaltestwindowid)
	VALUES (1, 2018, 12, 17, 'Fall',  1, true, 12, 12, now(), now(), null);
            
INSERT INTO public.testingcycle(id, schoolyear, assessmentprogramid, testingprogramid, testingcyclename,  sortorder, 
   activeflag, createduser, modifieduser, createddate, modifieddate, operationaltestwindowid)
	VALUES (2, 2018, 12, 17, 'Winter', 2, true, 12, 12, now(), now(), null);
            
INSERT INTO public.testingcycle(id, schoolyear, assessmentprogramid, testingprogramid, testingcyclename,  sortorder, 
   activeflag, createduser, modifieduser, createddate, modifieddate, operationaltestwindowid)
	VALUES (3, 2018, 12, 17, 'Spring', 3, true, 12, 12, now(), now(), null);


