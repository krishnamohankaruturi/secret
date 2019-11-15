
--DE3460: Organization identifier not fully alphanumeric 

UPDATE fieldspecification SET formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' 
	WHERE fieldname = 'displayIdentifier' and mappedname is null;

-- US11322 Enhancement: Student PNP profile upload - Reverse Contrast, auditory calm and test time activate by default

-- AdditionalTestingTimeActivatebydefault
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('AdditionalTestingTimeActivatebydefault', '{True, False}', NULL, NULL, 30, false, false, NULL, 'AdditionalTestingTimeActivatebydefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	now(), (Select id from aartuser where username = 'cetesysadmin'));


INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('AdditionalTestingTimeActivatebydefault', 'AdditionalTestingTimeActivatebydefault'));


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('AdditionalTestingTimeActivatebydefault', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='AdditionalTestingTimeActivatebydefault'), (select id from profileitemattributecontainer where attributecontainer = 'ControlAdditionalTestingTime'), 
	(select id from profileitemattributenickname where attributenickname ='AssessmentNeedActivateByDefault'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


-- Assigned Support.
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('AssignedSupport', '{True, False}', NULL, NULL, 30, false, false, NULL, 'AssignedSupport', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));


INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('AssignedSupport', 'AssignedSupport'));


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('AssignedSupport', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('DisplayAuditoryBackground',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('ScreenEnhancementInvertColourChoice',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='AssignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'DisplayAuditoryBackground'), 
	NULL, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='AssignedSupport'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementInvertColourChoice'), 
	NULL, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


-- ActivatebyDefault.
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('ActivatebyDefault', '{True, False}', NULL, NULL, 30, false, false, NULL, 'ActivatebyDefault', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));


INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('ActivatebyDefault', 'ActivatebyDefault'));


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('ActivatebyDefault', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));

INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='ActivatebyDefault'), (select id from profileitemattributecontainer where attributecontainer = 'DisplayAuditoryBackground'), 
	NULL, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='ActivatebyDefault'), (select id from profileitemattributecontainer where attributecontainer = 'ScreenEnhancementInvertColourChoice'), 
	NULL, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));


-- Band
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ('Band', '{True, False}', NULL, NULL, 30, false, false, NULL, 'Band', true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'));


INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification WHERE (fieldname,mappedname) in (('Band', 'Band'));


INSERT INTO profileitemattribute (attributename,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ('Band', now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattributecontainer (attributecontainer,createddate,createduser,activeflag,modifieddate,modifieduser) VALUES 
	('DLM',now(), (select id from aartuser where username='cetesysadmin'), true, 
	now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO profileitemattributenicknamecontainer (attributenameid,attributecontainerid,attributenicknameid,createddate,createduser,activeflag,modifieddate,modifieduser)
	VALUES ((select id from profileitemattribute where attributename ='Band'), (select id from profileitemattributecontainer where attributecontainer = 'DLM'), 
	NULL, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));