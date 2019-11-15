-- 616.dml script from changepond
INSERT INTO authorities(
            authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_SCORE_ALL_TEST', 'Score All Tests', 'Test Management-Scoring', CURRENT_TIMESTAMP, (select id from aartuser  where email = 'cete@ku.edu'), 
            true, CURRENT_TIMESTAMP, (select id from aartuser  where email = 'cete@ku.edu'));


INSERT INTO authorities(
            authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_SCORE_UPLOADSCORER', 'Upload Scores', 'Test Management-Scoring', CURRENT_TIMESTAMP, (select id from aartuser  where email = 'cete@ku.edu'), 
            true, CURRENT_TIMESTAMP, (select id from aartuser  where email = 'cete@ku.edu'));			
			

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Scoring Upload', 'SCORING_RECORD_TYPE', 'This indicates that the uploaded record is Scoring record', (select id from categorytype where typecode = 'CSV_RECORD_TYPE'), 
            null, null, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'));

			



INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('documentId', null, null, null, null, 
            true, true, null, null, TRUE, 
            CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            false, 'number', null, null);



 INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('assessmentName', null, null, null, 100, 
            true, true, null, null, TRUE, 
            CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            false, null, null, null);


 INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('stage', null, null, null, 100, 
            true, true, null, null, TRUE, 
            CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            false, null, null, null);



			
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'assessmentName' and rejectifempty is true and rejectifinvalid is true and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Assessment Name');
			
INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'documentId' and rejectifempty is true and rejectifinvalid is true and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Document ID');


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'testId' and rejectifempty is true and rejectifinvalid is true and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Test ID');

            

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'state' and rejectifempty is true and rejectifinvalid is true and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'State');



INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'districtID' and rejectifempty is true and rejectifinvalid is true and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'District ID');


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'schoolID' and rejectifempty is true and rejectifinvalid is true and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'School ID');


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'educatorIdentifier' and rejectifempty is true and rejectifinvalid is true and activeflag is true limit 1), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Educator Identifier');




INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'educatorFirstName' and rejectifempty is false and rejectifinvalid is false and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Educator First Name');


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'educatorLastName' and rejectifempty is false and rejectifinvalid is false and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Educator Last Name');


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'firstName' and rejectifempty is false and rejectifinvalid is false and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Student First Name');



INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'lastName' and rejectifempty is false and rejectifinvalid is false and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Student Last Name');


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname ilike 'stateStudentIdentifier' and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'State Student Identifier');



INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'grade' and rejectifempty is true and rejectifinvalid is true and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Grade');



INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'subject' and rejectifempty is true and rejectifinvalid is true and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Subject');

            

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'stage' and rejectifempty is true and rejectifinvalid is true and activeflag is true), (select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Stage');

