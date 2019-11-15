
--empty for ddl/167.sql

INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('ITI Configuration', 'ITI_CONFIGURATION', 'ITI Configuration', 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), 
 now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('ITI Recommended Level', 'ITI_RECOMMENDED_LEVEL', 'ITI Recommended Level', (select id from categorytype where typecode='ITI_CONFIGURATION'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('ITI Access', 'ITI_ACCESS', 'ITI Access', (select id from categorytype where typecode='ITI_CONFIGURATION'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
