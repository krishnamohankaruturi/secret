-- dml for 596
--select populatestudentpnpassessmentprogram();
select repopulateactnoexpdate();

-- Getting rid-off comma-separated values (hide_accomodation,....)
--Response
DO
$BODY$ 
DECLARE TTARECORD RECORD;
BEGIN
	
update profileitemattrnameattrcontainerviewoptions set viewoption = 'hide' 
where 
pianacid = (SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='response' and pia.attributename='dictated') 
and assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM');

IF NOT EXISTS (
		SELECT 1
		FROM profileitemattrnameattrcontainerviewoptions
		WHERE pianacid = (SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='response' and pia.attributename='usedCommunicationDevice')
		AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM')
	) THEN
		insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
			values (
			(SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='response' and pia.attributename='usedCommunicationDevice'),
			(select id from assessmentprogram where abbreviatedname='DLM'),
			'hide',
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
			where piac.attributecontainer='response' and pia.attributename='signedResponses')
		AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM')
	) THEN
		insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
		values (
		(SELECT pianc.id	
		FROM profileitemattribute pia 
			JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
			JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
		where piac.attributecontainer='response' and pia.attributename='signedResponses'),
		(select id from assessmentprogram where abbreviatedname='DLM'),
		'hide',
		now(),
		(select id from aartuser where username='cetesysadmin'),
		true,
		now(),
		(select id from aartuser where username='cetesysadmin')
		);
	ELSE
		RAISE NOTICE 'skipping INSERT';
	END IF;



--Presentation

update profileitemattrnameattrcontainerviewoptions set viewoption = 'hide' 
where 
pianacid = (SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='someotheraccommodation') 
and assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM');

IF NOT EXISTS (
		SELECT 1
		FROM profileitemattrnameattrcontainerviewoptions
		WHERE pianacid = (SELECT pianc.id	
			FROM profileitemattribute pia 
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer='presentation' and pia.attributename='readsAssessmentOutLoud')
		AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM')
	) THEN
		insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
		values (
		(SELECT pianc.id	
		FROM profileitemattribute pia 
			JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
			JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
		where piac.attributecontainer='presentation' and pia.attributename='readsAssessmentOutLoud'),
		(select id from assessmentprogram where abbreviatedname='DLM'),
		'hide',
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
			where piac.attributecontainer='presentation' and pia.attributename='useTranslationsDictionary')
		AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM')
	) THEN
		insert into profileitemattrnameattrcontainerviewoptions (pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser) 
		values (
		(SELECT pianc.id	
		FROM profileitemattribute pia 
			JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
			JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
		where piac.attributecontainer='presentation' and pia.attributename='useTranslationsDictionary'),
		(select id from assessmentprogram where abbreviatedname='DLM'),
		'hide',
		now(),
		(select id from aartuser where username='cetesysadmin'),
		true,
		now(),
		(select id from aartuser where username='cetesysadmin')
		);
	ELSE
		RAISE NOTICE 'skipping INSERT';
	END IF;

update profileitemattrnameattrcontainerviewoptions set viewoption = 'hide' 
where 
pianacid = (SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='someotheraccommodation') 
and assessmentprogramid = (select id from assessmentprogram where abbreviatedname='KAP');

update profileitemattrnameattrcontainerviewoptions set viewoption = 'hide' 
where 
pianacid = (SELECT pianc.id	
FROM profileitemattribute pia 
	JOIN profileItemAttributenameAttributeContainer pianc ON pia.id =  pianc.attributenameid
	JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
where piac.attributecontainer='presentation' and pia.attributename='someotheraccommodation')
and assessmentprogramid = (select id from assessmentprogram where abbreviatedname='AMP');
END;
$BODY$;
