--317.sql

-- US15567 - add subject preference to spoken, and update more hidden/disabled settings for assessment programs

INSERT INTO profileitemattribute (id, attributename, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES (nextval('profileitemattribute_id_seq'), 'preferenceSubject', now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE, now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO profileitemattributenameattributecontainer (id, attributenameid, attributecontainerid, parentcontainerleveloneid,
	parentcontainerleveltwoid, parentcontainerlevelthreeid, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES (nextval('profileitemattributenameattributecontainer_id_seq'), (SELECT id FROM profileitemattribute WHERE attributename = 'preferenceSubject'),
	(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Spoken'), NULL, NULL, NULL, now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE, now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

-- hide new subjects for DLM
INSERT INTO profileitemattrnameattrcontainerviewoptions
	(id, pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES (nextval('profileitemattrnameattrcontainerviewoptions_id_seq'),
	(SELECT id
		FROM profileitemattributenameattributecontainer pianac
		WHERE attributenameid =
			(SELECT id
			FROM profileitemattribute pia
			WHERE attributename LIKE 'preferenceSubject' LIMIT 1)
		AND attributecontainerid = 
			(SELECT id
			FROM profileitemattributecontainer piac
			WHERE attributecontainer LIKE 'Spoken' LIMIT 1)),
	(SELECT id FROM assessmentprogram WHERE programname LIKE 'Dynamic Learning Maps'),
	'hide',
	now(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	now(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
);

-- hide new subjects for KAP
INSERT INTO profileitemattrnameattrcontainerviewoptions
	(id, pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES (nextval('profileitemattrnameattrcontainerviewoptions_id_seq'),
	(SELECT id
		FROM profileitemattributenameattributecontainer pianac
		WHERE attributenameid =
			(SELECT id
			FROM profileitemattribute pia
			WHERE attributename LIKE 'preferenceSubject' LIMIT 1)
		AND attributecontainerid = 
			(SELECT id
			FROM profileitemattributecontainer piac
			WHERE attributecontainer LIKE 'Spoken' LIMIT 1)),
	(SELECT id FROM assessmentprogram WHERE programname LIKE 'KAP'),
	'hide',
	now(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	now(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
);

-- hide directionsOnly from KAP
INSERT INTO profileitemattrnameattrcontainerviewoptions
	(id, pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES (nextval('profileitemattrnameattrcontainerviewoptions_id_seq'),
	(SELECT id
		FROM profileitemattributenameattributecontainer pianac
		WHERE attributenameid =
			(SELECT id
			FROM profileitemattribute pia
			WHERE attributename LIKE 'directionsOnly' LIMIT 1)
		AND attributecontainerid = 
			(SELECT id
			FROM profileitemattributecontainer piac
			WHERE attributecontainer LIKE 'Spoken' LIMIT 1)),
	(SELECT id FROM assessmentprogram WHERE programname LIKE 'KAP'),
	'hide',
	now(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	now(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
);

-- hide directionsOnly from AMP
INSERT INTO profileitemattrnameattrcontainerviewoptions
	(id, pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser)
VALUES (nextval('profileitemattrnameattrcontainerviewoptions_id_seq'),
	(SELECT id
		FROM profileitemattributenameattributecontainer pianac
		WHERE attributenameid =
			(SELECT id
			FROM profileitemattribute pia
			WHERE attributename LIKE 'directionsOnly' LIMIT 1)
		AND attributecontainerid = 
			(SELECT id
			FROM profileitemattributecontainer piac
			WHERE attributecontainer LIKE 'Spoken' LIMIT 1)),
	(SELECT id FROM assessmentprogram WHERE programname LIKE 'Alaska'),
	'hide',
	now(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	TRUE,
	now(),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
);

-- update to disable the Non-Visual setting in AMP
UPDATE profileitemattrnameattrcontainerviewoptions
SET viewoption = 'disable_textonly,disable_graphicsonly,disable_nonvisual'
WHERE pianacid =
	(SELECT id
	FROM profileitemattributenameattributecontainer
	WHERE attributecontainerid = 
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Spoken')
	AND attributenameid = 
		(SELECT id FROM profileitemattribute WHERE attributename = 'UserSpokenPreference'))
AND assessmentprogramid =
	(SELECT id FROM assessmentprogram WHERE programname = 'Alaska' AND abbreviatedname = 'AMP' LIMIT 1);
	
update category set categoryname='5', categorydescription='American Indian or Alaska Native' where categorycode='00001' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='8', categorydescription='Native Hawaiian or Other Pacific Islander' where categorycode='01000' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='4', categorydescription='Asian' where categorycode='00010' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='1', categorydescription='White' where categorycode='10000' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);

INSERT INTO groups(organizationid, groupname, defaultrole, createddate, activeflag, 
            createduser, modifieduser, modifieddate, organizationtypeid)
    VALUES ((select id from organization where displayidentifier='CETE'), 'PD District Admin', false, now(), true,
            (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), 
            now(), (select id from organizationtype where typecode='DT'));
	