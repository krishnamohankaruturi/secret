

--US13779 Name:  Reports Functional Selection Filters 


INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Report Category', 'REPORT_CATEGORY', 'Report Category', 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), 
 now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Student', 'STUDENT', 'Student', (select id from categorytype where typecode='REPORT_CATEGORY'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

 INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Roster', 'ROSTER', 'Roster', (select id from categorytype where typecode='REPORT_CATEGORY'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

  INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('State', 'STATE', 'State', (select id from categorytype where typecode='REPORT_CATEGORY'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

  INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('District', 'DISTRICT', 'District', (select id from categorytype where typecode='REPORT_CATEGORY'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

 
 