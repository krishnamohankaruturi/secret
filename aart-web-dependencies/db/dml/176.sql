
--FC changes 



UPDATE surveyresponse SET responsevalue = '0 switches' WHERE labelid in (select id from surveylabel where labelnumber='Q34_1') and responseorder = 4;

UPDATE surveyresponse SET responsevalue = '0 switches' WHERE labelid in (select id from surveylabel where labelnumber='Q34_2') and responseorder = 4;

UPDATE surveyresponse SET responsevalue = '0 switches' WHERE labelid in (select id from surveylabel where labelnumber='Q34_3') and responseorder = 4;


UPDATE surveylabel SET activeflag = false WHERE labelnumber in ('Q65_TEXT');



--US14347 Name:  Test Management: Test Coordination: Student System Independent Accommodation



INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('setting',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('separateQuiteSetting',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='separateQuiteSetting'), 
		(select id from profileitemattributecontainer where attributecontainer = 'setting'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));



INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('presentation',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('someotheraccommodation',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='someotheraccommodation'), 
		(select id from profileitemattributecontainer where attributecontainer = 'presentation'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('readsAssessmentOutLoud',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='readsAssessmentOutLoud'), 
		(select id from profileitemattributecontainer where attributecontainer = 'presentation'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('useTranslationsDictionary',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='useTranslationsDictionary'), 
		(select id from profileitemattributecontainer where attributecontainer = 'presentation'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));




INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('response',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('dictated',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='dictated'), 
		(select id from profileitemattributecontainer where attributecontainer = 'response'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('usedCommunicationDevice',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='usedCommunicationDevice'), 
		(select id from profileitemattributecontainer where attributecontainer = 'response'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('signedResponses',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='signedResponses'), 
		(select id from profileitemattributecontainer where attributecontainer = 'response'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


		