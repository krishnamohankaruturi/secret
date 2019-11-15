-- Empty for dml/21.sql

--DE3848 : Generic error displays when value length exceeds length of 100 characters 

ALTER TABLE studentprofileitemattributevalue ALTER COLUMN selectedvalue TYPE text;

ALTER TABLE contentframeworkdetail DROP CONSTRAINT uk_cfd_cfd_parent;

--DE3970 Record endsup getting inserted for same student, attendance school and school year.

CREATE OR REPLACE FUNCTION non_empty_id(i bigint,j bigint)
  RETURNS bigint AS
$BODY$
        BEGIN
  			if(i is not null) THEN
   				return i;
  			else    
   				RETURN j;
  			end if;
        END;
	$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;