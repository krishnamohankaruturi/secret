
--US14643
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('School', 'SCHOOL', 'School', (select id from categorytype where typecode='REPORT_CATEGORY'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));