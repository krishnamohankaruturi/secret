
--US15034
INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('supportsProvidedByAlternateForm',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('visualImpairment',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('largePrintBooklet',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('paperAndPencil',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));		
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='visualImpairment'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsProvidedByAlternateForm'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='largePrintBooklet'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsProvidedByAlternateForm'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='paperAndPencil'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsProvidedByAlternateForm'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));					

--supportsProvidedByAlternateForm visualImpairment -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedByAlternateForm' and pia.attributename='visualImpairment'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsProvidedByAlternateForm visualImpairment -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedByAlternateForm' and pia.attributename='visualImpairment'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsProvidedByAlternateForm largePrintBooklet -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedByAlternateForm' and pia.attributename='largePrintBooklet'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsProvidedByAlternateForm paperAndPencil -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedByAlternateForm' and pia.attributename='paperAndPencil'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);