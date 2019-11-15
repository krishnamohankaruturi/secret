--F152 US19204 Add the ability to change grade level in UI and apply new functionality of grade change
--add new statuses for studentstests and studentstestsections
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Grade Change Inactivated',  'gradechangeinactivated-complete', 'Grade Change Inactivated Previously Complete', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Grade Change Inactivated',  'gradechangeinactivated-inprogress', 'Grade Change Inactivated Previously In Progress', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Grade Change Inactivated',  'gradechangeinactivated-pending', 'Grade Change Inactivated Previously Pending', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Grade Change Inactivated',  'gradechangeinactivated-unused', 'Grade Change Inactivated Previously Unused', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));                        



INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Grade Change Inactivated',  'gradechangeinactivated-complete', 'Grade Change Inactivated Previously Complete', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Grade Change Inactivated',  'gradechangeinactivated-inprogress', 'Grade Change Inactivated Previously In Progress', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Grade Change Inactivated',  'gradechangeinactivated-pending', 'Grade Change Inactivated Previously Pending', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Grade Change Inactivated',  'gradechangeinactivated-unused', 'Grade Change Inactivated Previously Unused', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));                                    
