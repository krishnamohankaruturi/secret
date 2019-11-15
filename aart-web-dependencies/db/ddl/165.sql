

--Org filter common code component sql to populate OrganizationHierarchy

CREATE OR REPLACE FUNCTION Populate_OrganizationHierarchy()
RETURNS character varying AS
$BODY$
DECLARE organization_row record;
	org_type_ids bigint[];
	record_counter int;
	id_val bigint;
	returnmessage character varying(20) = 'Unsuccessful';
	BEGIN
		FOR organization_row IN select * from organization where organizationtypeid = 2
		LOOP
			returnmessage = 'Successful';
			RAISE INFO 'organization_row.id - %', organization_row.id;

			select count(*) INTO record_counter from organizationhierarchy WHERE organizationid = organization_row.id;

			RAISE INFO '	record_counter - %', record_counter;
			IF (record_counter = 0) THEN
				org_type_ids = array(select distinct organizationtypeid from organization_children(organization_row.id)
					UNION select distinct organizationtypeid from organization where id = organization_row.id
					UNION select distinct organizationtypeid from organization_parent(organization_row.id));
				RAISE INFO '		In IF - %', org_type_ids;
				FOREACH id_val IN ARRAY org_type_ids
				LOOP
					INSERT INTO organizationhierarchy VALUES(organization_row.id, id_val);
					RAISE INFO '			INSERT - %, %', organization_row.id, id_val;

				END LOOP;
			END IF;
		END LOOP;
		
	RETURN returnmessage;
	END;	
$BODY$
LANGUAGE 'plpgsql';


/*NO LOAD BALANCE*/SELECT * from Populate_OrganizationHierarchy();


DROP FUNCTION IF EXISTS Populate_OrganizationHierarchy();


