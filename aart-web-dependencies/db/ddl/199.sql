CREATE OR REPLACE FUNCTION organization_school_year(orgid bigint)
  RETURNS integer AS
$BODY$
DECLARE
    school_year integer;
BEGIN
	school_year:= null;
	select cast(extract(year from schoolenddate) as integer) into school_year from organization where id=orgid and contractingorganization=true;
	IF (school_year is null) THEN
		select cast(extract(year from schoolenddate) as integer) into school_year  
			from organization where id in (select id from organization_parent_tree(orgid, 7))
			and contractingorganization=true;
	END IF;
	return school_year;	
	END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
