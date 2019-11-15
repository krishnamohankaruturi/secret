
CREATE OR REPLACE FUNCTION qctest(highstake boolean , qccomplete boolean)
  RETURNS text AS
$BODY$
        BEGIN
  			if(highstake is false and qccomplete is true) THEN
   				return 'ON';
  			else    
   				RETURN 'OFF';   				
  			end if;	
        END;
	$BODY$
	LANGUAGE plpgsql VOLATILE
  COST 100;

ALTER TABLE testingprogram ADD COLUMN qccomplete boolean default false;