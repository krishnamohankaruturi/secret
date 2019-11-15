-- DML statements
-- two braille file type attributes.
insert into profileitemattribute (attributename, createduser, activeflag, modifieduser, nonselectedvalue)
values('ebaeFileType', (select id from aartuser where username='cetesysadmin'), true, 
(select id from aartuser where username='cetesysadmin'), '');

insert into profileitemattribute (attributename, createduser, activeflag, modifieduser, nonselectedvalue)
values('uebFileType', (select id from aartuser where username='cetesysadmin'), true, 
(select id from aartuser where username='cetesysadmin'), '');

INSERT INTO profileitemattributenameattributecontainer 
(attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser,
parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
VALUES ((select id from profileitemattribute where attributename ='ebaeFileType'), 
(select id from profileitemattributecontainer where attributecontainer = 'Braille'),now(),
(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'),
(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Display'), (SELECT id FROM profileitemattributecontainer 
WHERE attributecontainer = '') );

INSERT INTO profileitemattributenameattributecontainer 
(attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser,
parentcontainerleveloneid,parentcontainerleveltwoid,parentcontainerlevelthreeid) 
VALUES ((select id from profileitemattribute where attributename ='uebFileType'), 
(select id from profileitemattributecontainer where attributecontainer = 'Braille'),now(),
(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'),
(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Access_For_All_User'),
(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Display'), (SELECT id FROM profileitemattributecontainer 
WHERE attributecontainer = '') );

-- two braille file type accomodations to get them as pnp settings.
INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser, sortorder, parentid) 
values ('EBAE',(select id from category where categorycode = 'LANGUAGE_AND_BRAILLE'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='ebaeFileType'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'), 81, (select id from pnpaccomodations where accomodation='Braille'));

INSERT INTO pnpaccomodations (accomodation, categoryid, pianacid, createddate, createduser, modifieddate, modifieduser, sortorder, parentid) 
values ('UEB',(select id from category where categorycode = 'LANGUAGE_AND_BRAILLE'),
(SELECT pianc.id    
FROM profileitemattribute pia 
    JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
    JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Braille' and pia.attributename='uebFileType'),now(),(select id from aartuser where username='cetesysadmin'),
now(),(select id from aartuser where username='cetesysadmin'), 82, (select id from pnpaccomodations where accomodation='Braille'));
