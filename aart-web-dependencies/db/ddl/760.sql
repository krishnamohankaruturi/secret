-- ddl/760.sql
--F841 Script for Temporary Function to inserting group authority exclusion  

CREATE OR REPLACE FUNCTION public.insert_groupauthoritiesexclusion()
    RETURNS void
    LANGUAGE 'plpgsql'
	
    COST 100
    VOLATILE 
AS $BODY$ 

DECLARE
stateid bigint;
	
	BEGIN
		IF EXISTS(SELECT 1 FROM assessmentprogram where abbreviatedname ='PLTW' AND activeflag is true) THEN
			FOR stateid IN (SELECT id  FROM organization 
				WHERE 
				organizationtypeid =(select id from organizationtype where typecode='ST')) 
				LOOP

			INSERT INTO groupauthoritiesexclusion(groupid, authorityid, assessmentprogramid, stateid)
				VALUES(
						(select id from groups where groupname ilike 'Teacher'), 
						(select id from authorities where authority ilike 'DATA_EXTRACTS_TEST_ADMIN_PLTW'), 
						(select id from assessmentprogram where abbreviatedname ilike 'PLTW'), 
						stateid
						);
			    END LOOP;
		END IF;
		
 END;

$BODY$;
