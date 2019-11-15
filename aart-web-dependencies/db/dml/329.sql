-- Creating new status for data extract failure
INSERT INTO category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser) 
VALUES ('Failed', 'FAILED', 'Data extract creation is failed.', (select id from categorytype where  typecode = 'PD_REPORT_STATUS'), NULL, 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
