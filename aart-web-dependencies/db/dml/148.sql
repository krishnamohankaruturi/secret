
--US13712 Name:  Reports - Report UI - Report Data Management UI/Summative Reports Data Upload

INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('SUMMATIVE_REPORTS_UPLOAD','Summative Reports Upload','Report', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
	
INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Report Upload File Type', 'REPORT_UPLOAD_FILE_TYPE', 'Report Upload File Type', 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), 
 now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Test Cut Scores', 'TEST_CUT_SCORES', 'Test Cut Scores', (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Entity Scale Scores', 'ENTITY_SCALE_SCORES', 'Entity Scale Scores', (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Scale Score Deviation', 'SCALE_SCORE_DEVIATION', 'Scale Score Deviation', (select id from categorytype where typecode='REPORT_UPLOAD_FILE_TYPE'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

 
 
 