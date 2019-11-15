--US12733: PNP - Language & Braille

INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,
modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
VALUES ((select id from profileitemattribute where attributename ='assignedSupport'), 
(select id from profileitemattributecontainer where attributecontainer = 'keywordTranslationDisplay'),now(), 
(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), 
(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Display'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = '') );

INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,
modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
VALUES ((select id from profileitemattribute where attributename ='activateByDefault'), 
(select id from profileitemattributecontainer where attributecontainer = 'keywordTranslationDisplay'),now(), 
(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), 
(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Display'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = '') );

INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,
modifieduser,parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
VALUES ((select id from profileitemattribute where attributename ='Language'), 
(select id from profileitemattributecontainer where attributecontainer = 'keywordTranslationDisplay'),now(), 
(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'), 
(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Display'), (SELECT id FROM profileitemattributecontainer WHERE attributecontainer = '') );

