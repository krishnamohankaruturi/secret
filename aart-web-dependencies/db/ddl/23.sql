
--US10458 Name: Technical Debt - CSV Upload (Remove Array)
ALTER TABLE fieldspecification ALTER COLUMN allowablevalues TYPE text;

--US12186:Edit Test Session - Functional Refactoring
CREATE OR REPLACE FUNCTION nvltest(testSessionId BIGINT)
  RETURNS text AS
$BODY$
        BEGIN
  			if(testSessionId is not null) THEN
   				return 'Enrolled Students';
  			else    
   				RETURN 'Other Students';
  			end if;
        END;
	$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION nvl(BIGINT)
  OWNER TO aart;
  
--commented because of build failure.publishing for CB
-- this is checked in 23.sql because it looks like this was manually executed in QA.
--ALTER TABLE contentgroup ADD COLUMN stimulusvariantid bigint;  