DROP FUNCTION IF EXISTS resetassessmentprogrampnp();

CREATE OR REPLACE FUNCTION resetassessmentprogrampnp(in assessment_Name text, in attribute_Container text, in pnp_Attribute text, in view_option text)
  RETURNS integer AS
$BODY$
DECLARE
	student RECORD;
	studentPNPJson RECORD;
	jsonText text;
	pnpAttribute text;
BEGIN
	
	assessment_Name := trim( both ']' from (trim(both '[' from assessment_Name) ) );
	attribute_Container := trim( both ']' from (trim(both '[' from attribute_Container) ) );
	pnp_Attribute := trim( both ']' from (trim(both '[' from pnp_Attribute) ) );
	view_option := trim( both ']' from (trim(both '[' from view_option) ) );
	if (assessment_Name is null) OR (assessment_Name = '') then
		return 0;
	end if;
	if (attribute_Container is null) OR (attribute_Container = '') then
		return 0;
	end if;
	if (pnp_Attribute is null) OR (pnp_Attribute = '') then
		return 0;
	end if;
	if (view_option is null) OR (view_option = '') then
		return 0;
	end if;
	
	RAISE NOTICE 'Started % % attribute', view_option, pnp_Attribute;
	-- Find if an entry exists in profileitemattrnameattrcontainerviewoptions for 'Spoken', 'directionsOnly' and 'DLM' combo
	IF NOT EXISTS (
		SELECT 1
			FROM profileitemattrnameattrcontainerviewoptions
			WHERE pianacid = (SELECT pianc.id
				FROM profileitemattribute pia
				JOIN profileItemAttributenameAttributeContainer pianc ON pia.id = pianc.attributenameid
				JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
				where piac.attributecontainer=attribute_Container and pia.attributename=pnp_Attribute)
			AND assessmentprogramid = (select id from assessmentprogram where abbreviatedname=assessment_Name)
	) THEN
		-- insert an entry in profileitemattrnameattrcontainerviewoptions for 'Spoken', 'directionsOnly', 'DLM' and 'disable' combo
		RAISE NOTICE 'Inserting entry in profileitemattrnameattrcontainerviewoptions';
		insert into profileitemattrnameattrcontainerviewoptions 
		(pianacid, assessmentprogramid, viewoption, createddate, createduser, activeflag, modifieddate, modifieduser)
		values (
		(SELECT pianc.id
			FROM profileitemattribute pia
			JOIN profileItemAttributenameAttributeContainer pianc ON pia.id = pianc.attributenameid
			JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer=attribute_Container and pia.attributename=pnp_Attribute),
		(select id from assessmentprogram where abbreviatedname=assessment_Name),
		view_option,
		now(),
		(select id from aartuser where displayname='CETE SysAdmin'),
		true,
		now(),
		(select id from aartuser where displayname='CETE SysAdmin')
		);
	ELSE
		-- update entry in profileitemattrnameattrcontainerviewoptions for 'Spoken', 'directionsOnly', 'DLM' combo
		RAISE NOTICE 'Updating entry in profileitemattrnameattrcontainerviewoptions';
		update profileitemattrnameattrcontainerviewoptions 
		set viewoption = view_option, activeflag = true,
		modifieddate = now(),
		modifieduser = (select id from aartuser where displayname='CETE SysAdmin')
		where pianacid = (SELECT pianc.id
			FROM profileitemattribute pia
			JOIN profileItemAttributenameAttributeContainer pianc ON pia.id = pianc.attributenameid
			JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id
			where piac.attributecontainer=attribute_Container and pia.attributename=pnp_Attribute)
		and assessmentprogramid = (select id from assessmentprogram where abbreviatedname=assessment_Name);
	END IF;
	
	RAISE NOTICE 'Completed % % only attribute',view_option,  pnp_Attribute;

	RAISE NOTICE 'Started % student''s % attribute',view_option,  pnp_Attribute;

	FOR student IN select s.id as studentid
		from student s
		inner join studentassessmentprogram sap on sap.studentid = s.id
		and sap.assessmentprogramid= (select id from assessmentprogram where abbreviatedname=assessment_Name)
		inner join studentprofileitemattributevalue spiav on spiav.studentid = s.id and
		profileitemattributenameattributecontainerid =
		(select id from profileitemattributenameattributecontainer
			WHERE attributecontainerid = (SELECT id FROM profileitemattributecontainer
				WHERE attributecontainer = attribute_Container)
			AND attributenameid = (SELECT id FROM profileitemattribute WHERE attributename = pnp_Attribute))
		order by s.id
	LOOP
		update studentprofileitemattributevalue set activeflag = false
			where profileitemattributenameattributecontainerid =
			(select id from profileitemattributenameattributecontainer
				WHERE attributecontainerid = (SELECT id FROM profileitemattributecontainer
					WHERE attributecontainer = attribute_Container)
				AND attributenameid = (SELECT id FROM profileitemattribute WHERE attributename = pnp_Attribute))
			and studentid = student.studentid;
		
		delete from studentpnpjson where studentid = student.studentid;
		
		RAISE NOTICE ' Populating student pnp for % ...', student.studentid;
		
		jsonText = '[';
		pnpAttribute = '';
		
        for studentPNPJson in SELECT lower(pia.attributename) as attrname, piac.id as attrcontainerid, 
			lower(piac.attributecontainer) as attrcontainer, lower(spiav.selectedvalue) as attrvalue
			FROM profileitemattribute pia 
			INNER JOIN profileItemAttributenameAttributeContainer pianc ON pia.id=pianc.attributenameid and pia.activeflag is true and pianc.activeflag is true
			INNER JOIN profileitemattributecontainer piac ON pianc.attributecontainerid = piac.id and piac.activeflag is true
			INNER JOIN studentprofileitemattributevalue spiav ON pianc.id = spiav.profileitemattributenameattributecontainerid and spiav.activeflag is true
			where spiav.studentid = student.studentid
        loop
			pnpAttribute = pnpAttribute || '{';
			pnpAttribute = pnpAttribute || '"attrName"' || ':"' || studentPNPJson.attrname || '",';
			pnpAttribute = pnpAttribute || '"attrContainerId"' || ':' || studentPNPJson.attrcontainerid || ',';
			pnpAttribute = pnpAttribute || '"attrContainer"' || ':"' || studentPNPJson.attrcontainer || '",';
			pnpAttribute = pnpAttribute || '"attrValue"' || ':"' || studentPNPJson.attrvalue || '"';
			pnpAttribute = pnpAttribute || '},';
        end loop;
        
        jsonText = jsonText || trim( trailing ',' from pnpAttribute);
        jsonText = jsonText || ']';
        -- RAISE NOTICE '%', jsonText;
        if(jsonText != '[]') then
        	EXECUTE 'INSERT INTO studentpnpjson (studentid, jsontext) values($1, $2)' using  student.studentid, jsonText;
        end if;
	END LOOP;
	RAISE NOTICE 'Completed % student''s % attribute', view_option,  pnp_Attribute;
	RETURN 1;	
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;
