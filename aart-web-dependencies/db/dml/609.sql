--F152 US19195 - add new statuses for ITI plans, testsessions, studentstests and studentstestsections to allow return to previous status related to exit, transfer and unroster
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Exit/Transfer Previously In Progress',  'exitclearunenrolled-inprogress', 'Exit/Transfer Previously In Progress', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Exit/Transfer Previously Pending',  'exitclearunenrolled-pending', 'Exit/Transfer Previously Pending', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Exit/Transfer Previously Unused',  'exitclearunenrolled-unused', 'Exit/Transfer Previously Unused', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));                        

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Exit/Transfer Previously In Progress',  'exitclearunenrolled-inprogress', 'Exit/Transfer Previously In Progress', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Exit/Transfer Previously Unused',  'exitclearunenrolled-unused', 'Exit/Transfer Previously Unused', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));                                    

            
            
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Unrostered Previously In Progress',  'rosterunenrolled-inprogress', 'Unrostered Previously In Progress', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Unrostered Previously Pending',  'rosterunenrolled-pending', 'Unrostered Previously Pending', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Unrostered Previously Unused',  'rosterunenrolled-unused', 'Unrostered Previously Unused', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));                        



INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Unrostered Previously In Progress',  'rosterunenrolled-inprogress', 'Unrostered Previously In Progress', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Unrostered Previously Unused',  'rosterunenrolled-unused', 'Unrostered Previously Unused', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));             