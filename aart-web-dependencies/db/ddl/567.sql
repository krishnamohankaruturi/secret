--For dml/*.sql

--US18811: Reports: Subscores - handle missing stages

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
	performance_testid = NULL;stage3_testid = NULL;stage2_testid=NULL;
	SELECT TRIM(subscoresmissingstages_record.grade) INTO trim_grade;
	SELECT TRIM(subscoresmissingstages_record.subject) INTO trim_subject;
	IF(subscoresmissingstages_record.default_stage2_externaltestid IS NOT NULL) THEN
		select st.testid from studentstests st 
		join testsession ts ON st.testsessionid = ts.id 
		JOIN test t on t.id = st.testid 
		where t.externalid in (subscoresmissingstages_record.default_stage2_externaltestid) 
		and t.activeflag is true and t.status in (64, 65) and ts.operationaltestwindowid in (10133, 10131, 10132) limit 1 into stage2_testid;
	END IF;
	IF(subscoresmissingstages_record.default_stage3_externaltestid IS NOT NULL) THEN
		select st.testid from studentstests st 
		join testsession ts ON st.testsessionid = ts.id 
		JOIN test t on t.id = st.testid 
		where t.externalid in (subscoresmissingstages_record.default_stage3_externaltestid) 
		and t.activeflag is true and t.status in (64, 65) and ts.operationaltestwindowid in (10133, 10131, 10132) limit 1 into stage3_testid;
	END IF;
	IF(subscoresmissingstages_record.default_performance_externaltestid IS NOT NULL) THEN
		select st.testid from studentstests st 
		join testsession ts ON st.testsessionid = ts.id 
		JOIN test t on t.id = st.testid 
		where t.externalid in (subscoresmissingstages_record.default_performance_externaltestid) 
		and t.activeflag is true and t.status in (64, 65) and ts.operationaltestwindowid in (10133, 10131, 10132) limit 1 into performance_testid;
	END IF;
	
	RAISE NOTICE 'Row: %, Subject: %, Grade: %, stage2_testid(%): %,stage3_testid(%): %, performance_testid(%): %', row_number, trim_subject, trim_grade, 
	subscoresmissingstages_record.default_stage2_externaltestid, stage2_testid, 
	subscoresmissingstages_record.default_stage3_externaltestid, stage3_testid,
	subscoresmissingstages_record.default_performance_externaltestid, performance_testid;

	IF( subscoresmissingstages_record.default_stage2_externaltestid is not null and stage2_testid is null ) THEN
		RAISE NOTICE 'Error in row %, Stage2 TestID % not found in EP. Delete %', row_number, subscoresmissingstages_record.default_stage2_externaltestid, subscoresmissingstages_record.id;
		DELETE FROM subscoresmissingstages WHERE id = subscoresmissingstages_record.id;
	ELSIF( subscoresmissingstages_record.default_stage3_externaltestid is not null and stage3_testid is null ) THEN
		RAISE NOTICE 'Error in row %, Stage3 TestID % not found in EP. Delete %', row_number, subscoresmissingstages_record.default_stage3_externaltestid, subscoresmissingstages_record.id;
		DELETE FROM subscoresmissingstages WHERE id = subscoresmissingstages_record.id;
	ELSIF( subscoresmissingstages_record.default_performance_externaltestid is not null and performance_testid is null ) THEN
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



-- Script from TDE team
-- Function: addorupdateresponse(bigint, bigint, bigint, bigint, bigint, bigint, bigint, text, numeric, boolean)

-- DROP FUNCTION addorupdateresponse(bigint, bigint, bigint, bigint, bigint, bigint, bigint, text, numeric, boolean);

CREATE OR REPLACE FUNCTION addorupdateresponse(
    in_studentid bigint,
    in_testid bigint,
    in_testsectionid bigint,
    in_studenttestid bigint,
    in_studenttestsectionid bigint,
    in_taskid bigint,
    in_foilid bigint,
    in_response text,
    in_score numeric,
    in_attempts int,
    in_islcs boolean)
  RETURNS integer AS
$BODY$
DECLARE
studenttestsection_status varchar;
  BEGIN
  select into studenttestsection_status category.categorycode from public.category 
  	JOIN public.studentstestsections sts ON sts.statusid = category.id
	where sts.id=in_studenttestsectionid;
  IF studenttestsection_status != 'complete' OR in_islcs = true THEN
INSERT INTO studentsresponses(studentid, testid, testsectionid, studentstestsid, 
studentstestsectionsid, taskvariantid, foilid, response, score, attempts) 
VALUES (in_studentid, in_testid, in_testSectionId, in_studentTestId, in_studentTestSectionId,
in_taskId, in_foilid, in_response, in_score, in_attempts);
RETURN 1;
ELSE 
	RETURN 0;
END IF;
  EXCEPTION 
    WHEN unique_violation THEN
--RAISE NOTICE unique violation;
  IF studenttestsection_status != 'complete' OR in_islcs = true THEN
UPDATE studentsresponses SET foilid = in_foilid, response = in_response, score = in_score, attempts =  in_attempts, modifieddate=now()
WHERE studentstestsectionsid = in_studentTestSectionId and taskvariantid = in_taskId;
RETURN 1;
ELSE 
	RETURN 0;
END IF;
    WHEN OTHERS THEN
RETURN 0;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


DO
$BODY$
BEGIN
	IF EXISTS (
		SELECT column_name
		FROM information_schema.columns
		WHERE table_name = 'studentsresponses' AND column_name = 'attempts'
	) THEN
		RAISE NOTICE 'studentsresponses.attempts already found, skipping add';
	ELSE
		alter table IF EXISTS studentsresponses add column attempts int;
	END IF;
END
$BODY$;
