--DE18510 fix ISMART Auto Enrollment - add complexity bands by gradeband

--alter table to add gradeband id
ALTER TABLE complexityband
ADD COLUMN IF NOT EXISTS gradebandid bigint;

DO 
$BODY$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'complexityband_gradebandid_fkey') THEN
		RAISE NOTICE 'complexityband_gradebandid_fkey does not exist - adding';
        ALTER TABLE complexityband
            ADD CONSTRAINT complexityband_gradebandid_fkey
            FOREIGN KEY (gradebandid) REFERENCES gradeband(id);
	ELSE
		RAISE NOTICE 'complexityband_gradebandid_fkey already exists';
    END IF;
END;
$BODY$;


--originally planned in ddl/781.sql, but necessity of patch made it go faster.

CREATE OR REPLACE FUNCTION string_to_interval(s text) RETURNS interval AS $fn$
BEGIN
    -- returns s as an interval, null otherwise
    RETURN s::INTERVAL;
EXCEPTION WHEN OTHERS THEN RETURN NULL;
END;
$fn$ LANGUAGE plpgsql STRICT;

