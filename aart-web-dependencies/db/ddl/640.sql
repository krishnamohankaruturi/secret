--ddl/640.sql - F396 changes

alter table batchupload add column assessmentprogramname character varying(100);
alter table batchupload add column contentareaname character varying(100);
alter table batchupload add column uploadtype character varying(100);
alter table batchupload add column createduserdisplayname character varying(161);

CREATE INDEX idx_batchupload_apname
  ON batchupload
  USING btree
  (assessmentprogramname COLLATE pg_catalog."default");
  
CREATE INDEX idx_batchupload_contentareaname
  ON batchupload
  USING btree
  (contentareaname COLLATE pg_catalog."default");
  
CREATE INDEX idx_batchupload_uploadtype
  ON batchupload
  USING btree
  (uploadtype COLLATE pg_catalog."default");

ALTER TABLE testcutscores DROP CONSTRAINT testcutscores_batchuploadid_fk;
ALTER TABLE subscoresdescription DROP CONSTRAINT subscoresdescription_batchuploadid_fk;
ALTER TABLE subscoreframework DROP CONSTRAINT subscoreframework_batchuploadid_fk;
ALTER TABLE subscoresrawtoscale DROP CONSTRAINT subscores_rawtoscale_batchuploadid_fk;
ALTER TABLE rawtoscalescores DROP CONSTRAINT rawtoscalescores_batchuploadid_fk;
ALTER TABLE leveldescription DROP CONSTRAINT leveldescription_batchuploadid_fk;
ALTER TABLE excludeditems DROP CONSTRAINT excludeditems_batchuploadid_fk;
ALTER TABLE combinedlevelmap DROP CONSTRAINT combinedlevelmap_batchuploadid_fk;

--Create table studentreportreprocess for holding specific student details to reprocess reports
DROP TABLE IF EXISTS StudentReportReprocess;

CREATE TABLE studentreportreprocess(
	id bigserial NOT NULL,
	studentid bigint ,
	statestudentidentifier character varying(50) NOT NULL,
	grade character varying(10) NOT NULL,
	gradeid bigint,
	contentarea character varying(10) NOT NULL,
	contentareaid bigint,
	assessmentprogram character varying(10) DEFAULT 'KAP',
	assessmentprogramid bigint DEFAULT 12,
	schoolyear bigint NOT NULL,
	batchreportprocessid bigint,
	schoolid bigint,
	districtid bigint,
	calculationscomplete boolean DEFAULT false,
	isrcomplete boolean DEFAULT false,
	summaryreportcomplete boolean DEFAULT false,
	bundledreportcomplete boolean DEFAULT false,
	generatespecificstudentreport boolean DEFAULT false,
	generatereportsindistrict boolean DEFAULT false,
	createduser integer NOT NULL DEFAULT 12,
	createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	modifieduser integer NOT NULL DEFAULT 12,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	processeddate timestamp with time zone,
	CONSTRAINT studentreportreprocess_id_pk PRIMARY KEY (id));

DROP INDEX IF EXISTS idx_studentreportreprocess_studentid;
CREATE INDEX idx_studentreportreprocess_studentid
ON StudentReportReprocess
USING btree
  (studentid);

DROP INDEX IF EXISTS idx_studentreportreprocess_ssid;  
CREATE INDEX idx_studentreportreprocess_ssid
ON StudentReportReprocess
USING btree
  (Statestudentidentifier);

DROP INDEX IF EXISTS idx_studentreportreprocess_gradeid;  
CREATE INDEX idx_studentreportreprocess_gradeid
ON StudentReportReprocess
USING btree
  (gradeid);

DROP INDEX IF EXISTS idx_studentreportreprocess_contentareaid;  
CREATE INDEX idx_studentreportreprocess_contentareaid
ON StudentReportReprocess
USING btree
  (contentareaid);

DROP INDEX IF EXISTS idx_studentreportreprocess_apid;  
CREATE INDEX idx_studentreportreprocess_apid
ON StudentReportReprocess
USING btree
  (assessmentprogramid);

DROP INDEX IF EXISTS idx_studentreportreprocess_schyr;  
CREATE INDEX idx_studentreportreprocess_schyr
ON StudentReportReprocess
USING btree
  (schoolyear);

DROP INDEX IF EXISTS idx_studentreportreprocess_calccomplete;  
CREATE INDEX idx_studentreportreprocess_calccomplete
ON StudentReportReprocess
USING btree (calculationscomplete);

DROP INDEX IF EXISTS idx_studentreportreprocess_isrcomplete;
CREATE INDEX idx_studentreportreprocess_isrcomplete
ON StudentReportReprocess
USING btree (isrcomplete);

DROP INDEX IF EXISTS idx_studentreportreprocess_batchreportprocessid;  
CREATE INDEX idx_studentreportreprocess_batchreportprocessid
ON StudentReportReprocess
USING btree
  (batchreportprocessid);

-- Function to load studentreportreprocess table from csv file
DROP FUNCTION IF EXISTS updatestudentreportreprocess(text);
CREATE OR REPLACE FUNCTION updatestudentreportreprocess(indexfilename text)
  RETURNS void AS
$BODY$
DECLARE
	var_subjectid BIGINT;
	trim_subject CHARACTER VARYING;
	var_gradeid BIGINT;
	trim_grade CHARACTER VARYING;
	trim_ssid CHARACTER VARYING;
	var_studentid BIGINT;		
	student_record RECORD;
	row_number INTEGER;
	final_record_count INTEGER;
BEGIN
    row_number := 1;
    
    RAISE NOTICE 'Started processing %', indexFileName;

    FOR student_record IN (SELECT * from studentreportreprocess where calculationscomplete is false) 
    LOOP
	
	SELECT TRIM(student_record.grade) INTO trim_grade;
	SELECT TRIM(student_record.contentarea) INTO trim_subject;
	IF(student_record.statestudentidentifier IS NOT NULL) THEN
		select st.id from student st 
		join enrollment en ON en.studentid = st.id 
		join studentassessmentprogram sap on sap.studentid = st.id
		join organizationtreedetail otd on otd.stateid = st.stateid and otd.statedisplayidentifier = 'KS'
		join assessmentprogram ap on ap.id = sap.assessmentprogramid and ap.abbreviatedname = 'KAP'
		where en.activeflag is true and en.currentschoolyear = student_record.schoolyear 
		and st.statestudentidentifier = student_record.statestudentidentifier limit 1 into var_studentid;
	END IF;
	
	
	RAISE NOTICE 'Processing Row: %, Student: %, Subject: %, Grade: %', row_number, student_record.statestudentidentifier, trim_subject, trim_grade;
	

	IF( student_record.statestudentidentifier is not null and var_studentid is null ) THEN
		RAISE NOTICE 'Error in row %, Student % not found in EP. Delete %', row_number, student_record.statestudentidentifier, subscoresmissingstages_record.id;
		DELETE FROM studentreportreprocess WHERE id = student_record.id;
	ELSE
		IF(trim_subject is not null  AND (LOWER(trim_subject) = LOWER('Sci') OR LOWER(trim_subject) = LOWER('M') OR LOWER(trim_subject) = LOWER('ELA'))) THEN
			SELECT id FROM contentarea where lower(abbreviatedname) = lower(trim(trim_subject)) INTO var_subjectid;
			IF(var_subjectid is NOT null) THEN
				
				IF(trim_grade is not null OR trim_grade != '') THEN
					SELECT id FROM gradecourse where lower(abbreviatedname) = lower(trim(trim_grade)) and contentareaid = var_subjectid INTO var_gradeid;
					IF(var_gradeid is null) THEN
						RAISE NOTICE 'Error in row %, Grade: % for Subject: % not found in EP. Delete %', row_number, trim_grade, trim_subject, student_record.id;
						DELETE FROM studentreportreprocess WHERE id = student_record.id;
					
					END IF;
				END IF;
				
			ELSE 
				RAISE NOTICE 'Error in row: %, Subject not found in EP for subject code: %', row_number, trim_subject;
				DELETE FROM studentreportreprocess WHERE id = student_record.id;
			END IF;
		ELSE
			IF(trim_subject is not null) THEN
				RAISE NOTICE 'Error in row %, Expected Subjects: ELA/M/Sci ==> FOUND: %', row_number, trim_subject;
				DELETE FROM studentreportreprocess WHERE id = student_record.id;
			END IF;
		END IF;			
		
		
	END IF;
		
	IF(var_studentid is not null) THEN
		update studentreportreprocess set studentid = var_studentid, gradeid = var_gradeid, contentareaid = var_subjectid,
		modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
		where id = student_record.id;
	END IF;		 
				
	row_number := row_number + 1;
	
    END LOOP;     
    select count(id) from studentreportreprocess INTO final_record_count;
    RAISE NOTICE 'Final record count % out of total %', final_record_count, row_number-1; 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
/*
 begin;
	delete from studentreportreprocess;
	COPY studentreportreprocess(statestudentidentifier, contentarea, grade, schoolyear)
	FROM 'C://dev/KAP_REPORTS/processbystudent.csv' DELIMITER ',' CSV HEADER ;
	select updatestudentreportreprocess('processbystudent.csv');
	select * from studentreportreprocess;
 commit; 
 */

-- Add new columns to studentreport table
ALTER TABLE studentreport ADD COLUMN stage1hassccode boolean,
			  ADD COLUMN stage2hassccode boolean;
			  
---Add new column to statespecialcircumstance table; for few sc codes we should not generate ISR for 2017 year
ALTER TABLE statespecialcircumstance ADD COLUMN reportscore boolean;	


-- Add new column Domain to upload files rawtoscalescore and testcutscore to support K-ELPA testing
ALTER TABLE rawtoscalescores ADD COLUMN domain character varying(100);
ALTER TABLE testcutscores ADD COLUMN domain character varying(100);

--- ADD new columns to subscoremissingstages table
ALTER TABLE subscoresmissingstages ADD COLUMN schoolyear BIGINT,
ADD COLUMN createduser BIGINT,
ADD COLUMN modifieduser BIGINT,
ADD COLUMN comment character varying(1000);



-- Function to upload default stage ids

DROP FUNCTION IF EXISTS updatesubscoredefaultstageids(in_subject character varying, in_otwid bigint, in_schoolyear bigint);
CREATE OR REPLACE FUNCTION updatesubscoredefaultstageids(in_subject character varying, in_otwid bigint, in_schoolyear bigint)
  RETURNS void AS
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
    
    RAISE NOTICE 'Started processing for Subject: % SchoolYear: % OperationalTestWindowId: %', in_subject, in_schoolyear, in_otwid;

    FOR subscoresmissingstages_record IN (SELECT * from subscoresmissingstages where schoolyear = in_schoolyear and LOWER(subject) = LOWER(in_subject)) 
    LOOP
	performance_testid = NULL;stage3_testid = NULL;stage2_testid=NULL;
	SELECT TRIM(subscoresmissingstages_record.grade) INTO trim_grade;
	SELECT TRIM(subscoresmissingstages_record.subject) INTO trim_subject;
	IF(subscoresmissingstages_record.default_stage2_externaltestid IS NOT NULL) THEN
		select st.testid from studentstests st 
		join testsession ts ON st.testsessionid = ts.id 
		JOIN test t on t.id = st.testid 
		where t.externalid in (subscoresmissingstages_record.default_stage2_externaltestid) 
		and t.activeflag is true and t.status in (64, 65) and ts.operationaltestwindowid in (in_otwid) limit 1 into stage2_testid;
	END IF;
	IF(subscoresmissingstages_record.default_stage3_externaltestid IS NOT NULL) THEN
		select st.testid from studentstests st 
		join testsession ts ON st.testsessionid = ts.id 
		JOIN test t on t.id = st.testid 
		where t.externalid in (subscoresmissingstages_record.default_stage3_externaltestid) 
		and t.activeflag is true and t.status in (64, 65) and ts.operationaltestwindowid in (in_otwid) limit 1 into stage3_testid;
	END IF;
	IF(subscoresmissingstages_record.default_performance_externaltestid IS NOT NULL) THEN
		select st.testid from studentstests st 
		join testsession ts ON st.testsessionid = ts.id 
		JOIN test t on t.id = st.testid 
		where t.externalid in (subscoresmissingstages_record.default_performance_externaltestid) 
		and t.activeflag is true and t.status in (64, 65) and ts.operationaltestwindowid in (in_otwid) limit 1 into performance_testid;
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
		ELSIF (LOWER(trim_subject) != LOWER('Sci') AND LOWER(trim_subject) != LOWER('M') AND LOWER(trim_subject) != LOWER('ELA')) THEN
			RAISE NOTICE 'Error in row %, Expected Subjects: ELA/M/Sci ==> FOUND: %', row_number, trim_subject;
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
  
/*
	begin;
		delete from subscoresmissingstages where schoolyear = 2017 and subject = 'ELA';
		COPY subscoresmissingstages(schoolyear,subject, grade, default_stage2_externaltestid,default_stage3_externaltestid,default_performance_externaltestid,comment)  
		FROM 'C://dev//2017_Subscore_Default_State_ID_KAP_ELA_Mock.csv' DELIMITER ',' CSV HEADER ;
		select updatesubscoredefaultstageids('ELA', 10172, 2017); 
 	rollback;
 */



  