-- 358.sql
-- US15776, updates to PNP settings for KAP and AMP

DO 
$BODY$
DECLARE
	rec RECORD;
	cetesysadminid BIGINT;
	kapid BIGINT;
	ampid BIGINT;
BEGIN
	SELECT INTO cetesysadminid (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
	SELECT INTO kapid (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'KAP');
	SELECT INTO ampid (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'AMP');
	RAISE NOTICE 'Setting new view options for Magnification...';
	-- disable Magnification
	FOR rec IN (SELECT id FROM profileitemattributenameattributecontainer WHERE attributecontainerid IN
		(SELECT id FROM profileitemattributecontainer WHERE attributecontainer = 'Magnification'))
	LOOP
		-- for KAP
		INSERT INTO profileitemattrnameattrcontainerviewoptions (id, pianacid, assessmentprogramid, viewoption,
			createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (nextval('profileitemattrnameattrcontainerviewoptions_id_seq'), rec.id, kapid, 'disable',
			now(), cetesysadminid, TRUE, now(), cetesysadminid);

		-- for AMP
		INSERT INTO profileitemattrnameattrcontainerviewoptions (id, pianacid, assessmentprogramid, viewoption,
			createddate, createduser, activeflag, modifieddate, modifieduser)
		VALUES (nextval('profileitemattrnameattrcontainerviewoptions_id_seq'), rec.id, ampid, 'disable',
			now(), cetesysadminid, TRUE, now(), cetesysadminid);
	END LOOP;
	
	-- update any existing PNP records that have magnification for non-DLM students
	RAISE NOTICE 'Removing Magnification support/activeByDefault from non-DLM students...';
	UPDATE studentprofileitemattributevalue
	SET selectedvalue = 'false',
		modifieddate = NOW(),
		modifieduser = cetesysadminid
	WHERE id IN
		(SELECT spiav.id
		FROM studentprofileitemattributevalue spiav
			INNER JOIN profileitemattributenameattributecontainer pianac
			ON spiav.profileitemattributenameattributecontainerid = pianac.id
			INNER JOIN profileitemattributecontainer piac
			ON pianac.attributecontainerid = piac.id
			INNER JOIN profileitemattribute pia
			ON pianac.attributenameid = pia.id
			INNER JOIN student s
			ON spiav.studentid = s.id
		WHERE s.profilestatus = 'CUSTOM'
			AND (s.assessmentprogramid IS NULL
				OR s.assessmentprogramid != (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM'))
			AND piac.attributecontainer ILIKE 'Magnification'
			AND (pia.attributename ILIKE 'assignedSupport' OR pia.attributename ILIKE 'activateByDefault')
			AND spiav.selectedvalue ILIKE 'true'
		);
	
	RAISE NOTICE 'Setting Magnification value back to default for non-DLM students...';
	UPDATE studentprofileitemattributevalue
	SET selectedvalue = '2x', -- default is 2x
		modifieddate = NOW(),
		modifieduser = cetesysadminid
	WHERE id IN
		(SELECT spiav.id
		FROM studentprofileitemattributevalue spiav
			INNER JOIN profileitemattributenameattributecontainer pianac
			ON spiav.profileitemattributenameattributecontainerid = pianac.id
			INNER JOIN profileitemattributecontainer piac
			ON pianac.attributecontainerid = piac.id
			INNER JOIN profileitemattribute pia
			ON pianac.attributenameid = pia.id
			INNER JOIN student s
			ON spiav.studentid = s.id
		WHERE s.profilestatus = 'CUSTOM'
			AND (s.assessmentprogramid IS NULL
				OR s.assessmentprogramid != (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM'))
			AND piac.attributecontainer ILIKE 'Magnification'
			AND pia.attributename ILIKE 'magnification'
			AND spiav.selectedvalue != '2x'
		);
END;
$BODY$;

update assessmentprogram set abbreviatedname='ARMM' where programname='ARMM';
update assessmentprogram set abbreviatedname='UAA2' where programname='Utah Alternate Assessment2';
update assessmentprogram set abbreviatedname='KAP_OLD' where programname='Kansas Assessment Program';
update assessmentprogram set abbreviatedname='CPASS2' where programname='CPASS2';
update assessmentprogram set abbreviatedname='EXMPL2' where programname='Example2';
update assessmentprogram set abbreviatedname='CPASS_COL' where programname='CPASS - Colorado';
update assessmentprogram set abbreviatedname='CPASS_KS' where programname='CPASS - Kansas';
update assessmentprogram set abbreviatedname='CPASS_MS' where programname='CPASS - Mississippi';
update assessmentprogram set abbreviatedname='DLM2' where programname='DLM 2';
update assessmentprogram set abbreviatedname='SBAC' where programname='Smarter Balanced';
update assessmentprogram set abbreviatedname='PLYGRND' where programname='Playground';
update assessmentprogram set abbreviatedname='ATEA' where programname='Accessibility for Technology-Enhanced Assessments';
update assessmentprogram set abbreviatedname='EMBRTSN' where programname='Embretson';
update assessmentprogram set abbreviatedname='CPASS_PLY' where programname='Career Pathways Playground';
update assessmentprogram set abbreviatedname='PRODDEMO' where programname='Production Demonstration';
update assessmentprogram set abbreviatedname='AAS' where programname='Alternative Assessment System';
update assessmentprogram set abbreviatedname='GAS' where programname='General Assessment System';
update assessmentprogram set abbreviatedname='DLM_PD' where programname='Dynamic Learning Maps - Professional Development';
update assessmentprogram set abbreviatedname='CETE' where programname='CETE';
update assessmentprogram set abbreviatedname='CPPR' where programname='Center for Public Partnerships and Research';
update assessmentprogram set abbreviatedname=programname where abbreviatedname is null;
