-- F506 Error Log Replaces Emails for KIDS 2018 
DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='VIEW_DASHBOARD_MENU' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with VIEW_DASHBOARD_MENU not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'VIEW_DASHBOARD_MENU','View Dashboard Menu',
				'Administrative-Dash Board', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with VIEW_DASHBOARD_MENU exists. Skipping';
	END IF;
END;
$BODY$;


DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities where authority='VIEW_KIDS_ERROR_MESSAGES' and activeflag=true) = 0) THEN
		RAISE NOTICE  '%', 'Permission with VIEW_KIDS_ERROR_MESSAGES not exists. Inserting';
		INSERT INTO authorities(
			    id, authority, displayname, objecttype, createddate, createduser, 
			    activeflag, modifieddate, modifieduser)
		    VALUES (nextval('authorities_id_seq'),'VIEW_KIDS_ERROR_MESSAGES','View KIDS Error Messages ',
				'Administrative-Dash Board', now(),(Select id from aartuser where username='cetesysadmin'), 
				TRUE,now(),(Select id from aartuser where username='cetesysadmin'));
	ELSE 
		RAISE NOTICE  '%', 'Permission with VIEW_KIDS_ERROR_MESSAGES exists. Skipping';
	END IF;
END;
$BODY$;



-- parameters are assessment program abbreviation, State display identifier, school year and Braille file type (EBAE or UEB or BOTH)
--select populatebraillefiletype( 'DLM','AK', 2017, 'EBAE');
CREATE OR REPLACE FUNCTION populatebraillefiletype(
    IN _assessmentprogram text,
    IN _stataeidentifier text,
    IN _currentschoolyear bigint,
    IN _braillefiletype text
)
returns void as $BODY$
declare
	student_rec record;
	student_recs record;
	state_id bigint;
	assessment_program_id bigint;
begin
	IF (_stataeidentifier = NULL OR _currentschoolyear = NULL OR _braillefiletype = NULL OR _assessmentprogram = NULL) THEN
		RAISE EXCEPTION 'Please provide state display identifier and current scheool year and the braille file type';
	END IF;

	select null into state_id;
	select id from organization where displayidentifier = _stataeidentifier and organizationtypeid = 2 into state_id;
	if(state_id = NULL) then 
		RAISE EXCEPTION 'Please provide valid state identifer';
	end if;

	select null into assessment_program_id;
	select id from assessmentprogram where abbreviatedname = _assessmentprogram and _assessmentprogram in ('KAP', 'DLM', 'CPASS', 'ELPA21')
	into assessment_program_id;
	if(assessment_program_id = NULL) then 
		RAISE EXCEPTION 'Please provide valid assessment program (supported are KAP, DLM, CPASS, ELPA21)';
	end if;

	RAISE NOTICE 'Populating braille file type : % for state : % ', _braillefiletype, _stataeidentifier;

	for student_rec in SELECT DISTINCT otd.statename, otd.districtname, otd.schoolname, 
			spiav.studentid as studentid, pianc.id as "id",pia.id as "attributeNameId",pia.attributename as "attributeName", 
			piac.id as "attributeContainerId",piac.attributecontainer as "attributeContainerName", 
			spiav.selectedvalue as "selectedValue", pianacvo.viewoption as "viewOption"
			FROM profileitemattribute pia 
			JOIN profileItemAttributenameAttributeContainer pianc ON pia.id = pianc.attributenameid 
			JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id 
			LEFT OUTER JOIN studentprofileitemattributevalue spiav ON pianc.id = spiav.profileitemattributenameattributecontainerid 
			LEFT OUTER JOIN profileitemattrnameattrcontainerviewoptions pianacvo ON pianacvo.pianacid = pianc.id 
			and pianacvo.assessmentprogramid = (select id from assessmentprogram where abbreviatedname = _assessmentprogram)
			LEFT OUTER JOIN enrollment enrl on enrl.studentid = spiav.studentid and enrl.activeflag = true 
			inner join organizationtreedetail otd on otd.schoolid = enrl.attendanceschoolid
			WHERE 
			piac.attributecontainer = 'Braille' and pia.attributename='assignedSupport' and spiav.selectedvalue = 'true' 
			and spiav.activeflag = true and enrl.currentschoolyear =  _currentschoolyear
			and otd.stateid = (select id from organization where displayidentifier = _stataeidentifier and organizationtypeid = 2)
			order by spiav.studentid asc  loop 
		RAISE NOTICE 'processing student : % ', student_rec.studentid;
		
		if (_braillefiletype = 'ebae' OR _braillefiletype = 'EBAE' or _braillefiletype = 'both' OR _braillefiletype = 'BOTH' ) THEN
			
			update studentprofileitemattributevalue set activeflag = true, selectedvalue = 'true' where 
			profileitemattributenameattributecontainerid = (select id from profileItemAttributenameAttributeContainer where attributenameid=
			(select id from profileitemattribute  where attributename  = 'ebaeFileType')) 
			and studentid = student_rec.studentid;
			if not found then 
					insert into studentprofileitemattributevalue (activeflag, selectedvalue, profileitemattributenameattributecontainerid, 
					studentid, createduser, modifieduser) 
					values (true, 'true', (select id from profileItemAttributenameAttributeContainer where attributenameid=
					(select id from profileitemattribute  where attributename  = 'ebaeFileType')) , student_rec.studentid, 
					(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'));
					RAISE NOTICE 'inserting student : % with option EBAE as true', student_rec.studentid;
				else 
					RAISE NOTICE 'updating student : % with option EBAE as true', student_rec.studentid;
			end if;

			update studentprofileitemattributevalue set activeflag = true, selectedvalue = 'false' where 
			profileitemattributenameattributecontainerid = (select id from profileItemAttributenameAttributeContainer where attributenameid=
			(select id from profileitemattribute  where attributename  = 'uebFileType')) 
			and studentid = student_rec.studentid;
			if not found then 
				insert into studentprofileitemattributevalue (activeflag, selectedvalue, profileitemattributenameattributecontainerid, 
				studentid, createduser, modifieduser) 
				values (true, 'true', (select id from profileItemAttributenameAttributeContainer where attributenameid=
				(select id from profileitemattribute  where attributename  = 'uebFileType')) , student_rec.studentid, 
				(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'));
				RAISE NOTICE 'inserting student : % with option UEB as false', student_rec.studentid;
			else 
				RAISE NOTICE 'updating student : % with option UEB as false', student_rec.studentid;
			end if;
			
		end if;

		if (_braillefiletype = 'ueb' OR _braillefiletype = 'UEB') THEN
			
			update studentprofileitemattributevalue set activeflag = true, selectedvalue = 'true' where 
			profileitemattributenameattributecontainerid = (select id from profileItemAttributenameAttributeContainer where attributenameid=
			(select id from profileitemattribute  where attributename  = 'uebFileType')) 
			and studentid = student_rec.studentid;
			if not found then 
				insert into studentprofileitemattributevalue (activeflag, selectedvalue, profileitemattributenameattributecontainerid, 
				studentid, createduser, modifieduser) 
				values (true, 'true', (select id from profileItemAttributenameAttributeContainer where attributenameid=
				(select id from profileitemattribute  where attributename  = 'uebFileType')) , student_rec.studentid, 
				(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'));
				RAISE NOTICE 'inserting student : % with option UEB as true', student_rec.studentid;
			else 
				RAISE NOTICE 'updating student : % with option UEB as true', student_rec.studentid;
			end if;

			update studentprofileitemattributevalue set activeflag = true, selectedvalue = 'false' where 
			profileitemattributenameattributecontainerid = (select id from profileItemAttributenameAttributeContainer where attributenameid=
			(select id from profileitemattribute  where attributename  = 'ebaeFileType')) 
			and studentid = student_rec.studentid;
			if not found then 
				insert into studentprofileitemattributevalue (activeflag, selectedvalue, profileitemattributenameattributecontainerid, 
				studentid, createduser, modifieduser) 
				values (true, 'true', (select id from profileItemAttributenameAttributeContainer where attributenameid=
				(select id from profileitemattribute  where attributename  = 'ebaeFileType')) , student_rec.studentid, 
				(select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'));
				RAISE NOTICE 'inserting student : % with option EBAE as false', student_rec.studentid;
			else 
				RAISE NOTICE 'updating student : % with option EBAE as false', student_rec.studentid;
			end if;
		end if;
	end loop;
end;
$BODY$ language plpgsql;