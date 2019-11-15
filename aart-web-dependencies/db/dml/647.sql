-- DML for DE15547 & DE15557
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Exit/Transfer Previously Complete',  'exitclearunenrolled-complete', 'Exit/Transfer Previously Complete', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));      
            
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Exit/Transfer Previously Complete',  'exitclearunenrolled-complete', 'Exit/Transfer Previously Complete', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));     
            
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Unrostered Previously Complete',  'rosterunenrolled-complete', 'Unrostered Previously Complete', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));            
            
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Unrostered Previously Complete',  'rosterunenrolled-complete', 'Unrostered Previously Complete', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));             