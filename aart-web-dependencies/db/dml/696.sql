--696.dml

--2 cPass_Field_Specification
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('testType', null, null, null, null, 
            'true', 'true', null, 'Test_Type', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'String', null, null);
            
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('districtIdentifier', null, null, null, null, 
            'true', 'true', null, 'District_Identifier', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'String', null, null);

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('districtIdentifier', null, null, null, null, 
            'false', 'false', null, 'District_Identifier_Null_Allowed', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'String', null, null);

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('schoolIdentifier', null, null, null, null, 
            'false', 'false', null, 'School_Identifier_Null_Allowed', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'String', null, null);
            

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('scaleScore', null, null, null, null, 
            'true', 'true', null, 'Scale_Score', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'Number', null, null);
            
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('standardError', null, null, null, null, 
            'true', 'true', null, 'Standard_Error', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'Decimal', null, null);
            
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('achievementLevel', null, null, null, null, 
            'true', 'true', null, 'Achievement_Level', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'String', null, null);
            
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('completedFlag', null, null, null, null, 
            'true', 'true', null, 'Completed_Flag', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', null, null, null);
            
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('reportCycle', '{Dec,May}', null, null, null, 
            'true', 'true', null, 'Report_Cycle', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'String', null, null);
            
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('topicName', null, null, null, null, 
            'true', 'true', null, 'Topic_Name', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'String', null, null);
            
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('topicCode', null, null, null, null, 
            'true', 'true', null, 'Topic_Code', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'String', null, null);
            
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('totalNoOfItems', null, null, null, null, 
            'true', 'true', null, 'Total_No_Of_Items', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'Number', null, null);

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('percentCorrect', null, null, null, null, 
            'true', 'true', null, 'Percent_Correct', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'Decimal', null, null);

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('testingProgram', null, null, null, null, 
            'true', 'true', null, 'Testing_Program', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'String', null, null);

--3-category DML script
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Assessment Topics', 'ASSESSMENT_TOPICS', 'Assessment Topics', 
	   (select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE'), 
            null, null,now(), (select id  from aartuser  where email ='cete@ku.edu'), true, 
            now(), (select id  from aartuser  where email ='cete@ku.edu'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Test Cut Scores', 'TEST_CUT_SCORES_CPASS', 'Test Cut Scores for cpass', 
	   (select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE'), 
            null, null,now(), (select id  from aartuser  where email ='cete@ku.edu'), true, 
            now(), (select id  from aartuser  where email ='cete@ku.edu'));
            
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Level Descriptions', 'LEVEL_DESCRIPTIONS_CPASS', 'Level Descriptions for cpass', 
	   (select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE'), 
            null, null,now(), (select id  from aartuser  where email ='cete@ku.edu'), true, 
            now(), (select id  from aartuser  where email ='cete@ku.edu'));



INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Upload student score caluculations', 'UPLOAD_STUDENT_SCORE_CALC', 'Students score calculations for report generation',(select id from categorytype where typecode ='CSV_RECORD_TYPE'), 
            null, null,now(), (select id  from aartuser  where email ='cete@ku.edu'), true, 
            now(), (select id  from aartuser  where email ='cete@ku.edu'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Upload students percent correct for each assessment topics', 'UPLOAD_STUDENT_PCT_ASMNT_TOPIC', 'Students percent correct for each assessment topics for  report generation',(select id from categorytype where typecode ='CSV_RECORD_TYPE'), 
            null, null,now(), (select id  from aartuser  where email ='cete@ku.edu'), true, 
            now(), (select id  from aartuser  where email ='cete@ku.edu'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Upload organization percent correct for each assessment topics', 'UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC', 'Organization percent correct for each assessment topics for  report generation',(select id from categorytype where typecode ='CSV_RECORD_TYPE'),null, null,now(), (select id  from aartuser  where email ='cete@ku.edu'), true, 
            now(), (select id  from aartuser  where email ='cete@ku.edu'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Upload Organization score caluculations', 'UPLOAD_ORGANIZATION_SCORE_CALC', 'Organization score calculations for  report generation',(select id from categorytype where typecode ='CSV_RECORD_TYPE'), 
            null, null,now(), (select id  from aartuser  where email ='cete@ku.edu'), true, 
            now(), (select id  from aartuser  where email ='cete@ku.edu'));
--testingcycle DML script
INSERT INTO testingcycle(
            id, schoolyear, assessmentprogramid, testingprogramid, testingcyclename, 
            operationaltestwindowid, sortorder, activeflag, createduser, 
            modifieduser, createddate, modifieddate)
    VALUES ((SELECT nextval('testingcycle_id_seq')),2018, (select id from assessmentprogram where abbreviatedname ='CPASS' and activeflag is true), (select id from testingprogram where programname ='Summative' and assessmentprogramid in (select id from assessmentprogram where abbreviatedname ='CPASS' and activeflag is true) and activeflag is true), 'Dec', null, 
            1, true, (select id  from aartuser  where email ='cete@ku.edu'), (select id  from aartuser  where email ='cete@ku.edu'),
            now(), now());

INSERT INTO testingcycle(
            id, schoolyear, assessmentprogramid, testingprogramid, testingcyclename, 
            operationaltestwindowid, sortorder, activeflag, createduser, 
            modifieduser, createddate, modifieddate)
    VALUES ((SELECT nextval('testingcycle_id_seq')),2018, (select id from assessmentprogram where abbreviatedname ='CPASS' and activeflag is true), (select id from testingprogram where programname ='Summative' and assessmentprogramid in (select id from assessmentprogram where abbreviatedname ='CPASS' and activeflag is true) and activeflag is true), 'May', null, 
            2, true, (select id  from aartuser  where email ='cete@ku.edu'), (select id  from aartuser  where email ='cete@ku.edu'),
            now(), now());
            
          
--fieldspecification DML script
            
--fieldspecificationsrecordtypes DML script
-- LEVEL_DESCRIPTIONS
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='assessmentProgram'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Assessment_Program');
			

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='subject'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Subject');
			

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='grade'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Grade');
			

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='levelName'and rejectifempty is false and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Level_Name');
			
            
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='levelDescription'and rejectifempty is false and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Level_Description');
			
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolYear'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Year');
			
            
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='level'and rejectifempty is true and rejectifinvalid is true and mappedname='Level' and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Level_Number');
			

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='descriptionType'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Description_Type');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='descriptionParagraphPageBottom'and rejectifempty is false and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Level_Description_Paragraph_Page_Bottom');


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testingProgramName'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Testing_Program');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='reportCycle'and rejectifempty is true and rejectifinvalid is true and mappedname='Report_Cycle' and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Report_Cycle');            

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testType'and rejectifempty is true and rejectifinvalid is true and mappedname='Test_Type' and activeflag is true), (SELECT id from category where    categorycode ='LEVEL_DESCRIPTIONS_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Test_Type');

-- Test_Cut_Score
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testType'and rejectifempty is true and rejectifinvalid is true and mappedname='Test_Type' and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Test_Type');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='assessmentProgram'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Assessment_Program');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='subject'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Subject');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='grade'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Grade');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='level'and rejectifempty is true and rejectifinvalid is true and mappedname='Level' and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Level_Number');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='levelLowCutScore'and rejectifempty is true and rejectifinvalid is true and mappedname='LowerLevelCutScore' and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Level_Low_Cut_Score');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='levelHighCutScore'and rejectifempty is true and rejectifinvalid is true and mappedname='UpperLevelCutScore' and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Level_High_Cut_Score');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='domain'and rejectifempty is false and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Domain');


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testingProgramName'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Testing_Program');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='reportCycle'and rejectifempty is true and rejectifinvalid is true and mappedname='Report_Cycle' and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Report_Cycle');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolYear'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='TEST_CUT_SCORES_CPASS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Year');


--set current report cycle
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Dec', 'CURRENT_CPASS_REPORT_CYCLE', 'Current cpass reporting cycle',
    (select id from  categorytype where typecode ='REPORT_CYCLE' ),null, null, 
    now(), (select id from aartuser where username = 'cetesysadmin'), true,
    now(), (select id from aartuser where username = 'cetesysadmin'));

--update testtype name
UPDATE testtype SET testtypename ='cPass Animal Systems Assessment' WHERE testtypecode ='B' AND testtypename ='Animal Systems Assessment';

UPDATE testtype SET testtypename ='cPass Comprehensive Agriculture Assessment' WHERE testtypecode ='A' AND testtypename ='Comprehensive Agriculture Assessment';

UPDATE testtype SET testtypename ='cPass Power, Structural, and Technical Systems Module' WHERE testtypecode ='AM' AND testtypename ='Comprehensive Agriculture Assessment - Power, Structural and Technical Module';

UPDATE testtype SET testtypename ='cPass Plant Systems Assessment' WHERE testtypecode ='D' AND testtypename ='Plant Systems Assessment';

UPDATE testtype SET testtypename ='cPass Horticulture Module' WHERE testtypecode ='DM' AND testtypename ='Plant Systems Assessment - Horticulture Module';

UPDATE testtype SET testtypename ='cPass Drafting Assessment' WHERE testtypecode ='F'  AND testtypename ='Design and Pre Construction Assessment';

UPDATE testtype SET testtypename ='cpass Comprehensive Business Assessment' WHERE testtypecode ='G'  AND testtypename ='Comprehensive Business Assessment';

UPDATE testtype SET testtypename ='cPass Finance Assessment' WHERE testtypecode ='H'  AND testtypename ='Finance Assessment';

UPDATE testtype SET testtypename ='cPass Accounting Module' WHERE testtypecode ='HM' AND testtypename ='Finance Assessment - Accounting Module';

UPDATE testtype SET testtypename ='cPass General CETE Assessment' WHERE testtypecode ='2' AND testtypename ='General CETE Assessment';

UPDATE testtype SET testtypename ='cPass General CETE Assessment' WHERE testtypecode ='E' AND testtypename ='Manufacturing Production Assessment';
	
---4: Assessment Topics Info
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolYear'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='ASSESSMENT_TOPICS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Year');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testType'and rejectifempty is true and rejectifinvalid is true and mappedname = 'Test_Type' and activeflag is true), (SELECT id from category where    categorycode ='ASSESSMENT_TOPICS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Test_Type');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='subject'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='ASSESSMENT_TOPICS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Subject');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='grade'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='ASSESSMENT_TOPICS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Grade');            

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='topicName'and rejectifempty is true and rejectifinvalid is true and mappedname = 'Topic_Name' and activeflag is true), (SELECT id from category where    categorycode ='ASSESSMENT_TOPICS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Topic_Name');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='topicCode'and rejectifempty is true and rejectifinvalid is true and mappedname ='Topic_Code' and activeflag is true), (SELECT id from category where    categorycode ='ASSESSMENT_TOPICS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Topic_Code');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='totalNoOfItems'and rejectifempty is true and rejectifinvalid is true and mappedname ='Total_No_Of_Items' and activeflag is true), (SELECT id from category where    categorycode ='ASSESSMENT_TOPICS' and categorytypeid=
(select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Total_No_Of_Items');

---Student Score Info 

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='stateStudentIdentifier'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'State_Student_Identifier');  
 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testType'and rejectifempty is true and rejectifinvalid is true and mappedname = 'Test_Type' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Test_Type');
            
 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='subject'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Subject');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='grade'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Grade'); 

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='state'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'State');  
 
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='districtIdentifier'and rejectifempty is true and rejectifinvalid is true and mappedname ='District_Identifier' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'District_Identifier'); 

            
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolIdentifier'and rejectifempty is true and rejectifinvalid is true and mappedname ='School_Code_RESULTS_UPLOAD' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Identifier'); 
             

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='assessmentProgram'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Assessment_Program');
             
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolYear'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Year');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='scaleScore'and rejectifempty is true and rejectifinvalid is true and mappedname ='Scale_Score' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Scale_Score');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='standardError'and rejectifempty is true and rejectifinvalid is true and mappedname ='Standard_Error' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Standard_Error');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='achievementLevel'and rejectifempty is true and rejectifinvalid is true and mappedname ='Achievement_Level' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Level_Number');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='completedFlag'and rejectifempty is true and rejectifinvalid is true and mappedname ='Completed_Flag' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Completed_Flag');                                                 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='reportCycle'and rejectifempty is true and rejectifinvalid is true and mappedname ='Report_Cycle' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Report_Cycle'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testingProgram'and rejectifempty is true and rejectifinvalid is true and mappedname ='Testing_Program' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Testing_Program'); 

---Students Percent Correct for each Assessment Topics
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolYear'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Year');

 INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='stateStudentIdentifier'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'State_Student_Identifier');  

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testType'and rejectifempty is true and rejectifinvalid is true and mappedname = 'Test_Type' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Test_Type');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='subject'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Subject');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='grade'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Grade');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='state'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'State'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='districtIdentifier'and rejectifempty is true and rejectifinvalid is true and mappedname ='District_Identifier' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'District_Identifier'); 

            
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolIdentifier'and rejectifempty is true and rejectifinvalid is true and mappedname ='School_Code_RESULTS_UPLOAD' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Identifier');             

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='topicCode'and rejectifempty is true and rejectifinvalid is true and mappedname ='Topic_Code' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Topic_Code');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='percentCorrect'and rejectifempty is true and rejectifinvalid is true and mappedname ='Percent_Correct' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Percent_Correct'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='reportCycle'and rejectifempty is true and rejectifinvalid is true and mappedname ='Report_Cycle' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Report_Cycle'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testingProgram'and rejectifempty is true and rejectifinvalid is true and mappedname ='Testing_Program' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_STUDENT_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Testing_Program'); 
                        
---Organization Percent Correct for each Assessment Topics
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolYear'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Year');
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='state'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'State'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='districtIdentifier'and rejectifempty is false and rejectifinvalid is false and mappedname ='District_Identifier_Null_Allowed' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'District_Identifier'); 
            
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolIdentifier'and rejectifempty is false and rejectifinvalid is false and mappedname ='School_Identifier_Null_Allowed' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Identifier'); 
            

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testType'and rejectifempty is true and rejectifinvalid is true and mappedname = 'Test_Type' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Test_Type');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='subject'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Subject');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='grade'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Grade');

  INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='topicCode'and rejectifempty is true and rejectifinvalid is true and mappedname ='Topic_Code' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Topic_Code'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='percentCorrect'and rejectifempty is true and rejectifinvalid is true and mappedname ='Percent_Correct' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Percent_Correct'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='reportCycle'and rejectifempty is true and rejectifinvalid is true and mappedname ='Report_Cycle' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Report_Cycle'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testingProgram'and rejectifempty is true and rejectifinvalid is true and mappedname ='Testing_Program' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Testing_Program'); 
                       
---Organization Score Info
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolYear'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Year');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='state'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'State'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='districtIdentifier'and rejectifempty is false and rejectifinvalid is false and mappedname ='District_Identifier_Null_Allowed' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'District_Identifier'); 

            
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='schoolIdentifier'and rejectifempty is false and rejectifinvalid is false and mappedname ='School_Identifier_Null_Allowed' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'School_Identifier'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='assessmentProgram'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Assessment_Program');
            
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testType'and rejectifempty is true and rejectifinvalid is true and mappedname = 'Test_Type' and activeflag is true), (SELECT id from category where categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Test_Type');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='scaleScore'and rejectifempty is true and rejectifinvalid is true and mappedname ='Scale_Score' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Scale_Score');               

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='standardError'and rejectifempty is true and rejectifinvalid is true and mappedname ='Standard_Error' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Standard_Error');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='subject'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Subject');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='grade'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (SELECT id from category where  categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Grade'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='reportCycle'and rejectifempty is true and rejectifinvalid is true and mappedname ='Report_Cycle' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Report_Cycle'); 

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname='testingProgram'and rejectifempty is true and rejectifinvalid is true and mappedname ='Testing_Program' and activeflag is true), (SELECT id from category where    categorycode ='UPLOAD_ORGANIZATION_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE')), now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            true, now(), (select id  from aartuser  where email ='cete@ku.edu'), 'Testing_Program');   
