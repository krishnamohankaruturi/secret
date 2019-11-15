
--DE7053
update fieldspecification set allowablevalues='{0,1}' where fieldname='hispanicEthnicity';
update fieldspecification set mappedname = 'Primary_Exceptionality_Code' where fieldname='primaryDisabilityCode';

--new source column and type
--upload, manual, webservice
INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES ('Source', 'SOURCE','The source of the data popluated.','AART_ORIG_CODE',now(),(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Upload', 'UPLOAD', 'The data came from an upload file.', (select id from categorytype where typecode='SOURCE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Manual', 'MANUAL', 'The data came from the application creation page.', (select id from categorytype where typecode='SOURCE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Web Service', 'WEBSERVICE', 'The data came from the webservice.', (select id from categorytype where typecode='SOURCE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 