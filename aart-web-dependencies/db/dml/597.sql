-- dml for 597
DO
$BODY$ 
DECLARE TTARECORD RECORD;
BEGIN

IF NOT EXISTS (
		SELECT 1
		FROM profileitemattrnameattrcontainerviewoptions
		WHERE pianacid = (SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='keywordTranslationDisplay' and pia.attributename='assignedSupport')
		AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname='KAP')
	) THEN
		insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
			values (
			(SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='keywordTranslationDisplay' and pia.attributename='assignedSupport'),
			(select id from assessmentprogram where abbreviatedname='KAP'),
			'enable',
			now(),
			(select id from aartuser where username='cetesysadmin'),
			true,
			now(),
			(select id from aartuser where username='cetesysadmin')
			);
	ELSE
		RAISE NOTICE 'skipping INSERT';
	END IF;


IF NOT EXISTS (
		SELECT 1
		FROM profileitemattrnameattrcontainerviewoptions
		WHERE pianacid = (SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='Signing' and pia.attributename='assignedSupport')
		AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname='KAP')
	) THEN
		insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
			values (
			(SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='Signing' and pia.attributename='assignedSupport'),
			(select id from assessmentprogram where abbreviatedname='KAP'),
			'enable',
			now(),
			(select id from aartuser where username='cetesysadmin'),
			true,
			now(),
			(select id from aartuser where username='cetesysadmin')
			);
	ELSE
		RAISE NOTICE 'skipping INSERT';
	END IF;

IF NOT EXISTS (
		SELECT 1
		FROM profileitemattrnameattrcontainerviewoptions
		WHERE pianacid = (SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='Magnification' and pia.attributename='assignedSupport')
		AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname='CPASS')
	) THEN
		insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
			values (
			(SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='Magnification' and pia.attributename='assignedSupport'),
			(select id from assessmentprogram where abbreviatedname='CPASS'),
			'disable',
			now(),
			(select id from aartuser where username='cetesysadmin'),
			true,
			now(),
			(select id from aartuser where username='cetesysadmin')
			);
	ELSE
		RAISE NOTICE 'skipping INSERT';
	END IF;	


IF NOT EXISTS (
		SELECT 1
		FROM profileitemattrnameattrcontainerviewoptions
		WHERE pianacid = (SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='Masking' and pia.attributename='assignedSupport')
		AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname='CPASS')
	) THEN
		insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
			values (
			(SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='Masking' and pia.attributename='assignedSupport'),
			(select id from assessmentprogram where abbreviatedname='CPASS'),
			'enable',
			now(),
			(select id from aartuser where username='cetesysadmin'),
			true,
			now(),
			(select id from aartuser where username='cetesysadmin')
			);
	ELSE
		RAISE NOTICE 'skipping INSERT';
	END IF;

IF NOT EXISTS (
		SELECT 1
		FROM profileitemattrnameattrcontainerviewoptions
		WHERE pianacid = (SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='InvertColourChoice' and pia.attributename='assignedSupport')
		AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname='CPASS')
	) THEN
		insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
			values (
			(SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='InvertColourChoice' and pia.attributename='assignedSupport'),
			(select id from assessmentprogram where abbreviatedname='CPASS'),
			'disable',
			now(),
			(select id from aartuser where username='cetesysadmin'),
			true,
			now(),
			(select id from aartuser where username='cetesysadmin')
			);
	ELSE
		RAISE NOTICE 'skipping INSERT';
	END IF;

IF NOT EXISTS (
		SELECT 1
		FROM profileitemattrnameattrcontainerviewoptions
		WHERE pianacid = (SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='Spoken' and pia.attributename='UserSpokenPreference')
		AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname='CPASS')
	) THEN
		insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
			values (
			(SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='Spoken' and pia.attributename='UserSpokenPreference'),
			(select id from assessmentprogram where abbreviatedname='CPASS'),
			'disable_textonly',
			now(),
			(select id from aartuser where username='cetesysadmin'),
			true,
			now(),
			(select id from aartuser where username='cetesysadmin')
			);
	ELSE
		RAISE NOTICE 'skipping INSERT';
	END IF;

END;
$BODY$;
