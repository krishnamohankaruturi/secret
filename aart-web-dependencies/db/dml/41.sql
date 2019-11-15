
--R8 -Iter2
--US12734 Name: PNP - Audio and Environment Support 


INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('breaks',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,
		modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
	VALUES ((select id from profileitemattribute where attributename ='assignedSupport'), 
		(select id from profileitemattributecontainer where attributecontainer = 'breaks'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), 
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Control'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'apipn:apipControl') );



INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('directionsOnly',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,
		modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
	VALUES ((select id from profileitemattribute where attributename ='directionsOnly'), 
		(select id from profileitemattributecontainer where attributecontainer = 'Spoken'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), 
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Content'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'apipn:apipContent') );




INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('onscreenKeyboard',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('scanSpeed',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('automaticScanInitialDelay',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('automaticScanRepeat',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,
		modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
	VALUES ((select id from profileitemattribute where attributename ='assignedSupport'), 
		(select id from profileitemattributecontainer where attributecontainer = 'onscreenKeyboard'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), 
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Control'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'apipn:apipControl') );


INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,
		modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
	VALUES ((select id from profileitemattribute where attributename ='activateByDefault'), 
		(select id from profileitemattributecontainer where attributecontainer = 'onscreenKeyboard'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), 
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Control'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'apipn:apipControl') );


INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,
		modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
	VALUES ((select id from profileitemattribute where attributename ='scanSpeed'), 
		(select id from profileitemattributecontainer where attributecontainer = 'onscreenKeyboard'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), 
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Control'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'apipn:apipControl') );


INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,
		modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
	VALUES ((select id from profileitemattribute where attributename ='automaticScanInitialDelay'), 
		(select id from profileitemattributecontainer where attributecontainer = 'onscreenKeyboard'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), 
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Control'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'apipn:apipControl') );


INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,
		modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
	VALUES ((select id from profileitemattribute where attributename ='automaticScanRepeat'), 
		(select id from profileitemattributecontainer where attributecontainer = 'onscreenKeyboard'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), 
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Control'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'apipn:apipControl') );
