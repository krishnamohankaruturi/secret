--For dml/*.sql

--US18811: Reports: Subscores - handle missing stages

DROP TABLE IF EXISTS subscoresmissingstages;

CREATE TABLE subscoresmissingstages
(
  id bigserial NOT NULL,
  assessmentprogramid bigint default 12, 
  subjectid bigint,
  subject text NOT NULL,
  gradeid bigint,
  grade text NOT NULL,
  default_stage2_testid bigint,
  default_stage2_externaltestid bigint,
  default_stage3_testid bigint,
  default_stage3_externaltestid bigint,
  default_performance_testid bigint,
  default_performance_externaltestid bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  CONSTRAINT subscoresmissingstages_id_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);


DROP FUNCTION IF EXISTS updatesubscoresmissingstages(text);
CREATE OR REPLACE FUNCTION updatesubscoresmissingstages(indexfilename text)
  RETURNS VOID AS
$BODY$
DECLARE
	var_subjectid BIGINT;
	trim_subject CHARACTER VARYING;
	var_gradeid BIGINT;
	trim_grade CHARACTER VARYING;	
	subscoresmissingstages_record RECORD;
	row_number INTEGER;
	final_record_count INTEGER;
	stage2_testid integer;
	stage3_testid integer;
	performance_testid integer;
BEGIN
    row_number := 1;
    
    RAISE NOTICE 'Started processing %', indexFileName;

    FOR subscoresmissingstages_record IN (SELECT * from subscoresmissingstages) 
    LOOP
	SELECT TRIM(subscoresmissingstages_record.grade) INTO trim_grade;
	SELECT TRIM(subscoresmissingstages_record.subject) INTO trim_subject;
	IF(subscoresmissingstages_record.default_stage2_externaltestid IS NOT NULL) THEN
		SELECT id FROM TEST WHERE externalid = subscoresmissingstages_record.default_stage2_externaltestid and activeflag is true and status = 64 limit 1into stage2_testid;
	END IF;
	IF(subscoresmissingstages_record.default_stage3_externaltestid IS NOT NULL) THEN
		SELECT id FROM TEST WHERE externalid = subscoresmissingstages_record.default_stage3_externaltestid and activeflag is true and status = 64 limit 1 into stage3_testid;
	END IF;
	IF(subscoresmissingstages_record.default_performance_externaltestid IS NOT NULL) THEN
		SELECT id FROM TEST WHERE externalid = subscoresmissingstages_record.default_performance_externaltestid and activeflag is true and status = 64 limit 1 into performance_testid;
	END IF;
	
	RAISE NOTICE 'Row: %, Subject: %, Grade: %, stage2_testid(%): %,stage3_testid(%): %, performance_testid(%): %', row_number, trim_subject, trim_grade, 
	subscoresmissingstages_record.default_stage2_externaltestid, stage2_testid, 
	subscoresmissingstages_record.default_stage3_externaltestid, stage3_testid,
	subscoresmissingstages_record.default_performance_externaltestid, performance_testid;

	IF( stage2_testid is null ) THEN
		RAISE NOTICE 'Error in row %, Stage2 TestID % not found in EP. Delete %', row_number, subscoresmissingstages_record.default_stage2_externaltestid, subscoresmissingstages_record.id;
		DELETE FROM subscoresmissingstages WHERE id = subscoresmissingstages_record.id;
	ELSIF( stage3_testid is null ) THEN
		RAISE NOTICE 'Error in row %, Stage3 TestID % not found in EP. Delete %', row_number, subscoresmissingstages_record.default_stage3_externaltestid, subscoresmissingstages_record.id;
		DELETE FROM subscoresmissingstages WHERE id = subscoresmissingstages_record.id;
	ELSIF( performance_testid is null ) THEN
		RAISE NOTICE 'Error in row %, Performance TestID % not found in EP. Delete %', row_number, subscoresmissingstages_record.default_performance_externaltestid, subscoresmissingstages_record.id;
		DELETE FROM subscoresmissingstages WHERE id = subscoresmissingstages_record.id;
	END IF;
	
	IF((trim_grade = '' OR trim_grade is null) OR (trim_subject = '' OR trim_subject is null)) THEN
		RAISE NOTICE 'Error in row %, Either Subject: % or Grade: % are not provided. Delete %', row_number, subscoresmissingstages_record.subject, subscoresmissingstages_record.grade, subscoresmissingstages_record.id;
		DELETE FROM subscoresmissingstages WHERE id = subscoresmissingstages_record.id;
	ELSE
		SELECT id FROM contentarea where lower(abbreviatedname) = lower(trim(trim_subject)) INTO var_subjectid;
		IF(var_subjectid is null) THEN 
			RAISE NOTICE 'Error in row %, Subject: % is not found in EP. Delete %', row_number, trim(trim_subject), subscoresmissingstages_record.id;
			DELETE FROM subscoresmissingstages WHERE id = subscoresmissingstages_record.id;
		ELSIF (LOWER(trim_subject) != LOWER('SS') AND LOWER(trim_subject) != LOWER('M') AND LOWER(trim_subject) != LOWER('ELA')) THEN
			RAISE NOTICE 'Error in row %, Expected Subjects: ELA/M/SS ==> FOUND: %', row_number, trim_subject;
			DELETE FROM subscoresmissingstages WHERE id = subscoresmissingstages_record.id;
		ELSE
			SELECT id FROM gradecourse where lower(abbreviatedname) = lower(trim(trim_grade)) and contentareaid = var_subjectid INTO var_gradeid;
			IF(var_gradeid is null) THEN 
				RAISE NOTICE 'Error in row %, Grade: % for Subject: % not found in EP. Delete %', row_number, trim_grade, trim_subject, subscoresmissingstages_record.id;
				DELETE FROM subscoresmissingstages WHERE id = subscoresmissingstages_record.id;
			ELSE
				UPDATE subscoresmissingstages 
				SET subjectid = var_subjectid, gradeid = var_gradeid, default_stage2_testid = stage2_testid, default_stage3_testid = stage3_testid, default_performance_testid = performance_testid, modifieddate = now()                              
				WHERE id = subscoresmissingstages_record.id;  
			END IF;
		END IF;
		
	END IF;
	row_number := row_number + 1;
	
    END LOOP;     
    select count(id) from subscoresmissingstages INTO final_record_count;
    RAISE NOTICE 'Final record count % out of total %', final_record_count, row_number-1; 
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

