
-- 259 SQL
-- US14740

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Pending', 'pending', 'Pending', (select id from categorytype where typecode='STUDENT_TEST_STATUS'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('ITI', 'ITI', 'Instructional Tool Interface', (select id from categorytype where typecode='SOURCE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 