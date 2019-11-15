
-- US15088
ALTER TABLE testtype ADD COLUMN activeflag boolean;
ALTER TABLE testtype ALTER COLUMN activeflag SET DEFAULT true;

DO
$BODY$
BEGIN
	IF ((SELECT count(*) FROM information_schema.constraint_table_usage WHERE table_name = 'testtype' and constraint_name = 'uk_testtype_testtypecode'
) = 0) THEN
		RAISE NOTICE  '%', 'Constraint uk_testtype_testtypecode not found.';
	ELSE 
		ALTER TABLE testtype DROP CONSTRAINT uk_testtype_testtypecode;
		RAISE NOTICE  '%', 'Constraint uk_testtype_testtypecode found. Droping the constraint.';
		ALTER TABLE testtype
  			ADD CONSTRAINT uk_testtype_testtypecode UNIQUE(testtypecode, assessmentid, activeflag);
	END IF;
END;
$BODY$;

ALTER TABLE testtypesubjectarea ADD COLUMN activeflag boolean;
ALTER TABLE testtypesubjectarea ALTER COLUMN activeflag SET DEFAULT true;

DO
$BODY$
BEGIN
	IF ((SELECT count(*) FROM information_schema.constraint_table_usage WHERE table_name = 'testtypesubjectarea' and constraint_name = 'uk_testtypesubjectarea_testtypeid_subjectareaid'
) = 0) THEN
		RAISE NOTICE  '%', 'Constraint uk_testtypesubjectarea_testtypeid_subjectareaid not found.';
	ELSE 
		ALTER TABLE testtypesubjectarea DROP CONSTRAINT uk_testtypesubjectarea_testtypeid_subjectareaid;
		RAISE NOTICE  '%', 'Constraint uk_testtypesubjectarea_testtypeid_subjectareaid found. Droping the constraint.';
		ALTER TABLE testtypesubjectarea
  			ADD CONSTRAINT uk_testtypesubjectarea_testtypeid_subjectareaid UNIQUE(testtypeid, subjectareaid, activeflag);
	END IF;
END;
$BODY$;
  
ALTER TABLE contentareatesttypesubjectarea ADD COLUMN activeflag boolean;
ALTER TABLE contentareatesttypesubjectarea ALTER COLUMN activeflag SET DEFAULT true;

DO
$BODY$
BEGIN
	IF ((SELECT count(*) FROM information_schema.constraint_table_usage WHERE table_name = 'contentareatesttypesubjectarea' and constraint_name = 'uk_contentareatesttypesubjectarea_contentareaid_testtypesubject'
) = 0) THEN
		RAISE NOTICE  '%', 'Constraint uk_contentareatesttypesubjectarea_contentareaid_testtypesubject not found.';
	ELSE 
		ALTER TABLE contentareatesttypesubjectarea DROP CONSTRAINT uk_contentareatesttypesubjectarea_contentareaid_testtypesubject;
		RAISE NOTICE  '%', 'Constraint uk_contentareatesttypesubjectarea_contentareaid_testtypesubject found. Droping the constraint.';
		ALTER TABLE contentareatesttypesubjectarea
  			ADD CONSTRAINT uk_contentareatesttypesubjectarea_contentareaid_testtypesubject UNIQUE(contentareaid, testtypesubjectareaid, activeflag);
	END IF;
END;
$BODY$;

