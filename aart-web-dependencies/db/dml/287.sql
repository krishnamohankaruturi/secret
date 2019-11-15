
--US15034
--remove existing hidden from viewoptions
delete from profileitemattrnameattrcontainerviewoptions where viewoption='hide';


--Setting separate quiet setting -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='setting' and pia.attributename='separateQuiteSetting'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Presentation some other accomodation -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='someotheraccommodation'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide_accommodation,hide_assessment,hide_translations',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);
--Presentation some other accomodation -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='someotheraccommodation'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide_accommodation',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Presentation some other accomodation -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='someotheraccommodation'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide_accommodation',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--Response dictated -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='response' and pia.attributename='dictated'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'hide_dictated,hide_communication,hide_responses',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--new containers
INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('supportsProvidedOutsideSystem',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('supportsRequiringAdditionalTools',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));

	
	
INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsTwoSwitch',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsTwoSwitch'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsRequiringAdditionalTools'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

--supportsRequiringAdditionalTools supportsTwoSwitch -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsTwoSwitch'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsRequiringAdditionalTools supportsTwoSwitch -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsTwoSwitch'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsAdminIpad',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsAdminIpad'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsRequiringAdditionalTools'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

--supportsRequiringAdditionalTools supportsAdminIpad -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsAdminIpad'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsRequiringAdditionalTools supportsAdminIpad -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsAdminIpad'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsAdaptiveEquip',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsAdaptiveEquip'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsRequiringAdditionalTools'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

--supportsRequiringAdditionalTools supportsAdaptiveEquip -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsAdaptiveEquip'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsRequiringAdditionalTools supportsAdaptiveEquip -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsAdaptiveEquip'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsIndividualizedManipulatives',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsIndividualizedManipulatives'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsRequiringAdditionalTools'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

--supportsRequiringAdditionalTools supportsIndividualizedManipulatives -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsIndividualizedManipulatives'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsRequiringAdditionalTools supportsIndividualizedManipulatives -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsRequiringAdditionalTools' and pia.attributename='supportsIndividualizedManipulatives'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsHumanReadAloud',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsHumanReadAloud'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsProvidedOutsideSystem'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

--supportsProvidedOutsideSystem supportsHumanReadAloud -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsHumanReadAloud'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsProvidedOutsideSystem supportsHumanReadAloud -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsHumanReadAloud'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsSignInterpretation',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsSignInterpretation'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsProvidedOutsideSystem'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

--supportsProvidedOutsideSystem supportsSignInterpretation -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsSignInterpretation'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsProvidedOutsideSystem supportsSignInterpretation -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsSignInterpretation'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);



INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsLanguageTranslation',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsLanguageTranslation'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsProvidedOutsideSystem'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

--supportsProvidedOutsideSystem supportsLanguageTranslation -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsLanguageTranslation'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsProvidedOutsideSystem supportsLanguageTranslation -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsLanguageTranslation'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsTestAdminEnteredResponses',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsTestAdminEnteredResponses'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsProvidedOutsideSystem'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

--supportsProvidedOutsideSystem supportsTestAdminEnteredResponses -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsTestAdminEnteredResponses'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsProvidedOutsideSystem supportsTestAdminEnteredResponses -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsTestAdminEnteredResponses'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);



INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('supportsPartnerAssistedScanning',now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO profileitemattributenameattributecontainer (attributenameid,attributecontainerid,createddate,createduser,activeflag,modifieddate,modifieduser) 
	VALUES ((select id from profileitemattribute where attributename ='supportsPartnerAssistedScanning'), 
		(select id from profileitemattributecontainer where attributecontainer = 'supportsProvidedOutsideSystem'),now(), 
		(select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));	

--supportsProvidedOutsideSystem supportsPartnerAssistedScanning -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsPartnerAssistedScanning'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--supportsProvidedOutsideSystem supportsPartnerAssistedScanning -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='supportsProvidedOutsideSystem' and pia.attributename='supportsPartnerAssistedScanning'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'hide',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--SpokenSourcePreference human -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='SpokenSourcePreference'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable_human',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);
--SpokenSourcePreference human -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='SpokenSourcePreference'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable_human',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--SpokenSourcePreference human -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='SpokenSourcePreference'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable_human',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--UserSpokenPreference  -DLM
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='UserSpokenPreference'),
(select id from assessmentprogram where abbreviatedname='DLM'),
'disable_graphicsonly',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);
--UserSpokenPreference  -KAP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='UserSpokenPreference'),
(select id from assessmentprogram where abbreviatedname='KAP'),
'disable_textonly,disable_graphicsonly',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);

--UserSpokenPreference  -AMP
insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
values (
(SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='Spoken' and pia.attributename='UserSpokenPreference'),
(select id from assessmentprogram where abbreviatedname='AMP'),
'disable_textonly,disable_graphicsonly',
now(),
(select id from aartuser where username='cetesysadmin'),
true,
now(),
(select id from aartuser where username='cetesysadmin')
);