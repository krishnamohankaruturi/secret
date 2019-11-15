--515.sql

-- these columns were added to production via another script
DO
$BODY$
BEGIN
  IF EXISTS (
    SELECT column_name
    FROM information_schema.columns 
    WHERE table_name='enrollment' AND column_name='notes'
  ) THEN
    RAISE NOTICE 'notes column found in enrollment already, skipping add';
  ELSE
    RAISE NOTICE 'notes column not found in enrollment, adding';
    ALTER TABLE enrollment ADD COLUMN notes TEXT;
  END IF;
  
  IF EXISTS (
    SELECT column_name
    FROM information_schema.columns 
    WHERE table_name='roster' AND column_name='notes'
  ) THEN
    RAISE NOTICE 'notes column found in roster already, skipping add';
  ELSE
    RAISE NOTICE 'notes column not found in roster, adding';
    ALTER TABLE roster ADD COLUMN notes TEXT;
  END IF;
END
$BODY$;
