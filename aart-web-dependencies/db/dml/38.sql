
--R8 - Iter 2 

--US12393 Name: First Contact Survey - Submit Survey Status

ALTER TABLE survey add COLUMN status bigint ;

INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Survey Status', 'SURVEY_STATUS', 'Survey Status', 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), 
 now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Complete', 'COMPLETE', 'Complete status for first contact', (select id from categorytype where typecode='SURVEY_STATUS'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('In Progress', 'IN_PROGRESS', 'In Progress status for first contact', (select id from categorytype where typecode='SURVEY_STATUS'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
 VALUES ('Not Started', 'NOT_STARTED', 'Not Started status for first contact', (select id from categorytype where typecode='SURVEY_STATUS'), 
 'AART_ORIG_CODE', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
