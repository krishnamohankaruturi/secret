
--US12928 Define module structure

INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Module Status', 'MODULE_STATUS', 'Module Status', 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), 
 now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Unpublished', 'UNPUBLISHED', 'The module is not ready for use.', (select id from categorytype where typecode='MODULE_STATUS'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

 INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Published', 'PUBLISHED', 'The module is ready for use.', (select id from categorytype where typecode='MODULE_STATUS'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));