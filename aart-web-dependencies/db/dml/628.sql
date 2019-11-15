--Dml of 628.sql
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Exit/Transfer Previously In Progress-Timed Out',  'exitclearunenrolled-inprogresstimedout', 'Exit/Transfer Previously In Progress-Timed Out', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
            
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Exit/Transfer Previously In Progress-Timed Out',  'exitclearunenrolled-inprogresstimedout', 'Exit/Transfer Previously In Progress-Timed Out', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));   
            
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Unrostered Previously In Progress-Timed Out',  'rosterunenrolled-inprogresstimedout', 'Unrostered Previously In Progress-Timed Out', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
            
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Unrostered Previously In Progress-Timed Out',  'rosterunenrolled-inprogresstimedout', 'Unrostered Previously In Progress-Timed Out', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));          
            
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Grade Change Inactivated',  'gradechangeinactivated-inprogresstimedout', 'Grade Change Inactivated Previously In Progress-Timed Out', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));
            
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Grade Change Inactivated',  'gradechangeinactivated-inprogresstimedout', 'Grade Change Inactivated Previously In Progress-Timed Out', (select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS'), 
             'AART', current_timestamp, (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));            
            
            
-- handle more internal users
update aartuser set internaluserindicator= true where id in(
select id from aartuser where email in('agileservicedesk@gmail.com','aletrajohnson@gmail.com','alistair.overeem@email.com','anniejatala@gmail.com','anniejatala+qc@gmail.com',
'cameron.clyne@gmail.com','herynk@gmail.com','jamesherynk@gmail.com','jlkenney8@outlook.com','kdillon@ksde.org','lakshmi.yarram@scriptbees.com',
'ramki.r@changepond.com','traineramp@gmail.com','trainercpass@gmail.com','trainerdlm@gmail.com','trainerkap@gmail.com'));

update authorities set authority ='DATA_EXTRACTS_SPL_CIRCM_CODE_REP',modifieddate=now() where authority = 'DATA_EXTRACT_SPL_CIRCUM_CODE_REP';
