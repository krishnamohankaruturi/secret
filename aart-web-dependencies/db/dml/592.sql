--592.sql
--- US19029 creating an default template--- 
insert into activationemailtemplate(templatename,emailsubject,emailbody,includeeplogo,isdefault,createddate,createduser,modifieddate,modifieduser) 
values('User Activation Email Template - Default','Activate your Account for KITE Assessment Administration',
'<p>Your account has been approved for access to KITE.<br />
Your username is your email address with all lowercase letters.<br />To activate your account and set up your password click on the following link.</p>
<div id=''linkText'' class=''linkText'' style=''text-decoration: underline;cursor: auto;color: #0782C1;
-webkit-touch-callout: none;-webkit-user-select: none;-khtml-user-select: none;-moz-user-select: none;
-ms-user-select: none;-o-user-select: none;user-select: none;'' contenteditable=''false'' >Activation Link will be displayed here</div>
<p>Please contact your local Assessment Coordinator or administrator if you did not request this account or are uncertain why you are receiving this email.<br />
This link to activate your account will expire in 20 days.</p>',
true,true,current_timestamp,(select id from aartuser where username='cetesysadmin'),current_timestamp, (select id from aartuser where username='cetesysadmin'));

-- creating permission for Activation emailtemplate screen 
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser) 
VALUES ('EDIT_ACTIVATION_EMAIL', 'Edit Activation Email', 'Administrative-View Tools',CURRENT_TIMESTAMP,
(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));
			
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ('VIEW_USER_MANAGEMENT', 'View User Management', 'Administrative-View Tools',CURRENT_TIMESTAMP,
(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

--- US19076 need the name to be generic. so removed the CCQ from the permission ---
update authorities set displayname = 'Score Tests' where authority = 'PERM_SCORE_CCQ_TESTS';
update authorities set displayname = 'Monitor Scores' where authority = 'PERM_SCORING_MONITORSCORES';

--- US19071 for report type change ---
update modulereport set reporttype='KAP Test Administration Monitoring' where reporttypeid=13;
update modulereport set reporttype='Accessibility Profile Counts' where reporttypeid=10;

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Projected Testing', 'PROJ_TEST_RECORD_TYPE', 'This indicates that the uploaded record is Projected Testing record.', 5, 
            null, null, current_timestamp,(Select id from aartuser where username='cetesysadmin'), true, 
            current_timestamp,(Select id from aartuser where username='cetesysadmin'));

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'assessmentProgram' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'Assessment Program');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'districtName' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'DistrictName');

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'schoolName' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'SchoolName');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('state', null, null, null, 100, 
            true, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);        


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'state' and activeflag is true and rejectifempty is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'State');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('districtID', null, null, null, 100, 
            true, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'districtID' and activeflag is true and rejectifempty is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'DistrictID');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('schoolID', null, null, null, 100, 
            true, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'schoolID' and activeflag is true and rejectifempty is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'SchoolID');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('month', '{jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec,January,February,March,April,May,June,July,August,September,October,November,December}',
            null, null, 20, 
            true, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'month' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'Month');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('one', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'one' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'1');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('two', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'two' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'2');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('three', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'three' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'3');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('four', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'four' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'4');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('five', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'five' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'5');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('six', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'six' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'6');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('seven', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'seven' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'7');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('eight', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'eight' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'8');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('nine', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'nine' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'9');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('ten', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'ten' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'10');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('eleven', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'eleven' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'11');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twelve', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twelve' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'12');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('thirteen', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'thirteen' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'13');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('fourteen', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'fourteen' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'14');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('fifteen', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'fifteen' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'15');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('sixteen', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'sixteen' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'16');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('seventeen', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'seventeen' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'17');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('eighteen', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'eighteen' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'18');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('nineteen', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'nineteen' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'19');
             
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twenty', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twenty' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'20');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twentyOne', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twentyOne' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'21');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twentyTwo', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twentyTwo' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'22');
 
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twentyThree', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twentyThree' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'23');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twentyFour', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twentyFour' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'24');

 
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twentyFive', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twentyFive' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'25');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twentySix', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twentySix' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'26');
   
INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twentySeven', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twentySeven' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'27');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twentyEight', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twentyEight' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'28');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('twentyNine', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'twentyNine' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'29');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('thirty', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'thirty' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'30');

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('thirtyOne', '{x,y,Y,X,''''}',
            null, null, 1, 
            false, true, null, null, true, 
            current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), true, current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
             false, null, null, null);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname = 'thirtyOne' and activeflag is true), 
             (SELECT id from category where categorycode = 'PROJ_TEST_RECORD_TYPE'), current_timestamp,
             (Select id from aartuser where username='cetesysadmin'), 
              true,  current_timestamp,
             (Select id from aartuser where username='cetesysadmin'),'31');
                          
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser) 
VALUES ('VIEW_SUMMARY_PROJECTED_TESTING', 'View Summary Projected Testing', 'Test Management-Projected Testing',CURRENT_TIMESTAMP,
(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser) 
VALUES ('VIEW_DETAILED_PROJECTED_TESTING', 'View Detailed Projected Testing', 'Test Management-Projected Testing',CURRENT_TIMESTAMP,
(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser) 
VALUES ('EDIT_DETAILED_PROJECTED_TESTING', 'Edit Detailed Projected Testing', 'Test Management-Projected Testing',CURRENT_TIMESTAMP,
(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));
