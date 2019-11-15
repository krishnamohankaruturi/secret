--For dml/*.sql

--US18812: Reports: Weight item raw score by test ID and section

DROP TABLE IF EXISTS rawscoresectionweights;

CREATE TABLE rawscoresectionweights
(
  id bigserial NOT NULL,
  assessmentprogramid bigint,
  assessmentprogram text not null,
  subjectid bigint,
  subject text not null,
  testid bigint not null,
  section_1_weight numeric,
  section_2_weight numeric,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  CONSTRAINT rawscoresectionweights_id_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

DROP FUNCTION IF EXISTS updaterawscoresectionweights(text);

CREATE OR REPLACE FUNCTION updaterawscoresectionweights(indexfilename text)
  RETURNS TEXT AS
$BODY$
DECLARE
	var_assessmentprogramid BIGINT;
	var_subjectid BIGINT;
	rawscoresectionweights_record RECORD;
	row_number INTEGER;
	trim_assessmentprogram CHARACTER VARYING;	
	trim_subject CHARACTER VARYING;
	final_record_count INTEGER;
	testid_count integer;
BEGIN
    row_number := 1;
    final_record_count:=1;
    testid_count := 0;
    
    RAISE NOTICE 'Started processing %', indexFileName;

    FOR rawscoresectionweights_record IN (SELECT * from rawscoresectionweights) 
    LOOP
	SELECT TRIM(rawscoresectionweights_record.assessmentprogram) INTO trim_assessmentprogram;
	SELECT TRIM(rawscoresectionweights_record.subject) INTO trim_subject;
	 
        IF((trim_assessmentprogram = '' OR trim_assessmentprogram is null) 
		OR (trim_subject = '' OR trim_subject is null) 
		OR (rawscoresectionweights_record.testid is null)
		OR (rawscoresectionweights_record.section_1_weight is null)
		OR (rawscoresectionweights_record.section_2_weight is null)
	   ) 
	THEN

		RAISE NOTICE 'In row %, one or more required fields are empty or null: AssessmentProgram: %, Subject: %, TestID: %, section_1_weight: %, section_1_weight: %',  
                       row_number, rawscoresectionweights_record.assessmentprogram, rawscoresectionweights_record.subject, rawscoresectionweights_record.testid,
                       rawscoresectionweights_record.section_1_weight, rawscoresectionweights_record.section_2_weight;
                DELETE FROM rawscoresectionweights WHERE id = rawscoresectionweights_record.id;
                

        ELSE

		
		SELECT id FROM assessmentprogram WHERE abbreviatedname = trim_assessmentprogram LIMIT 1 INTO var_assessmentprogramid;
		
		IF(var_assessmentprogramid is null) THEN 
		     RAISE NOTICE 'Error in row %, Assessment program % value is not found in EP', row_number, trim_assessmentprogram;
		     DELETE FROM rawscoresectionweights WHERE id = rawscoresectionweights_record.id;
		     final_record_count:= final_record_count -1;
		ELSE 
			IF(LOWER(trim_assessmentprogram) != LOWER('KAP')) THEN
				RAISE NOTICE 'Error in row %, Expected AssessmentProgram: KAP ==> FOUND: %', row_number, trim_assessmentprogram;
				DELETE FROM rawscoresectionweights WHERE id = rawscoresectionweights_record.id;
				final_record_count:= final_record_count -1;
			ELSE 
				SELECT id FROM contentarea where lower(abbreviatedname) = lower(trim(trim_subject)) INTO var_subjectid;
				IF(var_subjectid is null) THEN 
					RAISE NOTICE 'Error in row %, Subject: % is not found in EP', row_number, trim(trim_subject);
					DELETE FROM rawscoresectionweights WHERE id = rawscoresectionweights_record.id;
					final_record_count:=final_record_count-1;
				ELSE
					IF(LOWER(trim_subject) != LOWER('SS')) THEN
						RAISE NOTICE 'Error in row %, Expected Subject: SS ==> FOUND: %', row_number, trim_subject;
						DELETE FROM rawscoresectionweights WHERE id = rawscoresectionweights_record.id;
						final_record_count:= final_record_count -1;
					ELSE
						SELECT count(id) FROM TEST WHERE externalid IN (rawscoresectionweights_record.testid) into testid_count;
						IF( testid_count > 0 )
						THEN
							UPDATE rawscoresectionweights 
							SET subjectid = var_subjectid, assessmentprogramid = var_assessmentprogramid, modifieddate = now()                              
							WHERE id = rawscoresectionweights_record.id;  
						ELSE
							RAISE NOTICE 'Error in row %, TestID % not found in EP', row_number, rawscoresectionweights_record.testid;
							DELETE FROM rawscoresectionweights WHERE id = rawscoresectionweights_record.id;
							final_record_count:= final_record_count -1;
						END IF;
					END IF;
				END IF;
			END IF;
		END IF;
		
	END IF;
	
	row_number := row_number + 1;
	final_record_count := final_record_count+1;
    END LOOP;     
   -- RAISE 'Final record count % out of total %', final_record_count, row_number; 
    RETURN '';
    
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
