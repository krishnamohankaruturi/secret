--ddl/556.sql
-- Function: uploadexternalstudentfilestoep(text)

DROP FUNCTION IF EXISTS uploadexternalstudentfilestoep(text);

CREATE OR REPLACE FUNCTION uploadexternalstudentfilestoep(indexfilename text)
  RETURNS void AS
$BODY$
DECLARE
	ceteSysadmin_id BIGINT;
	school_year BIGINT;
	assessmentProgram_id BIGINT;
	state_id BIGINT;
	district_id BIGINT;
	school_id BIGINT;
	subject_id BIGINT;
	grade_id BIGINT;
	externalStudentReportRecord RECORD;
	row_number INTEGER;
	trim_file_name CHARACTER VARYING;
	trim_assessmentprog_abbr_name CHARACTER VARYING;	
	trim_state_displayidentifier CHARACTER VARYING;
	trim_district_displayidentifer CHARACTER VARYING;
	trim_school_displayidentifier CHARACTER VARYING;
	trim_subject_abbrname CHARACTER VARYING;
	trim_grade_name CHARACTER VARYING;
	trim_level1_text CHARACTER VARYING;
	trim_level2_text CHARACTER VARYING;
	subjects_array text[];
	existing_exteranlFile_id BIGINT;
	ep_srv_file_path CHARACTER VARYING;
BEGIN
    row_number := 1;

    SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO ceteSysadmin_id;
    
    RAISE NOTICE 'Started processing %', indexFileName;
    
    FOR externalStudentReportRecord IN (SELECT * from temp_externalStudentReports) LOOP
	row_number := row_number + 1;
	SELECT TRIM(externalStudentReportRecord.filename) INTO trim_file_name;
	SELECT TRIM(externalStudentReportRecord.assessmentprogramabbrname) INTO trim_assessmentprog_abbr_name;
	SELECT TRIM(externalStudentReportRecord.statedisplayidentifier) INTO trim_state_displayidentifier;	
	SELECT TRIM(externalStudentReportRecord.districtdisplayidentifier) INTO trim_district_displayidentifer;
	SELECT TRIM(externalStudentReportRecord.schooldisplayidentifier) INTO trim_school_displayidentifier;
	SELECT TRIM(externalStudentReportRecord.subjectabbrname) INTO trim_subject_abbrname;
	SELECT TRIM(externalStudentReportRecord.gradename) INTO trim_grade_name;
	SELECT TRIM(externalStudentReportRecord.level1_text) INTO trim_level1_text;
	SELECT TRIM(externalStudentReportRecord.level2_text) INTO trim_level2_text;
        
        IF((trim_file_name = '' OR trim_file_name is null) 
		OR (trim_assessmentprog_abbr_name = '' OR trim_assessmentprog_abbr_name is null) 
		OR (trim_state_displayidentifier = '' OR trim_state_displayidentifier is null)
		OR (trim_district_displayidentifer = '' OR trim_district_displayidentifer is null) 
		OR (trim_school_displayidentifier = '' OR trim_school_displayidentifier is null)
		OR (trim_subject_abbrname = '' OR trim_subject_abbrname is null)
		OR (trim_grade_name = '' OR trim_grade_name is null)
		OR (externalStudentReportRecord.studentid is null)) THEN

		RAISE NOTICE 'In row %, one or more required fields are empty or null FileName: %, AssessmentProgram: %, State: %, District: %, School: %, Subject: %, Grade: %, EPStudentId: %',  
                       row_number, externalStudentReportRecord.filename, externalStudentReportRecord.assessmentprogramabbrname, externalStudentReportRecord.statedisplayidentifier,
                       externalStudentReportRecord.districtdisplayidentifier, externalStudentReportRecord.schooldisplayidentifier,
                       externalStudentReportRecord.subjectabbrname, externalStudentReportRecord.gradename, externalStudentReportRecord.studentid;
        ELSIF(trim_file_name not ilike '%.pdf') THEN
		RAISE NOTICE 'In row %, file name is not ending wth pdf', row_number; 
        ELSE

          SELECT stateid FROM organizationtreedetail WHERE statedisplayidentifier = trim_state_displayidentifier LIMIT 1 INTO state_id;
          SELECT id FROM assessmentprogram WHERE abbreviatedname = trim_assessmentprog_abbr_name LIMIT 1 INTO assessmentProgram_id;

          IF(assessmentProgram_id is null) THEN 
	     RAISE NOTICE 'Error in row %, Assessment program % value is not present in EP', row_number, trim_assessmentprog_abbr_name;
	 ELSIF(state_id is null) THEN
	    RAISE NOTICE 'Error in row %, state % is not present in EP. Expecting the displayidentifieres(example: KS, OK, MO,AK)', row_number, trim_state_displayidentifier;
	 ELSE 
	    SELECT distinct districtid FROM organizationtreedetail WHERE stateid = state_id AND districtdisplayidentifier = trim_district_displayidentifer LIMIT 1 INTO district_id;	     
	    IF(district_id is null) THEN
                RAISE NOTICE 'Error in row %, district % is not present in EP', row_number, trim_district_displayidentifer;
             ELSE
		SELECT distinct schoolid FROM organizationtreedetail WHERE stateid = state_id AND schooldisplayidentifier = trim_school_displayidentifier LIMIT 1 INTO school_id;

		IF(school_id is null) THEN
		   RAISE NOTICE 'Error in row %, school % is not present in EP', row_number, trim_school_displayidentifier;
		ELSE
                   IF(lower(trim_assessmentprog_abbr_name) = lower('CPASS')) THEN
                         SELECT tt.id FROM testtype  tt JOIN assessment asmnt ON asmnt.id = tt.assessmentid JOIN testingprogram tp ON tp.id = asmnt.testingprogramid
                                JOIN assessmentprogram asp ON asp.id = tp.assessmentprogramid
                                WHERE asp.abbreviatedname = 'CPASS' AND lower(tt.testtypecode) = lower(trim_grade_name) LIMIT 1 INTO grade_id;
                    ELSE
                         SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(trim_grade_name) AND assessmentprogramgradesid IS NOT NULL
			     LIMIT 1 INTO grade_id;
                    END IF;

		   IF(grade_id is NULL) THEN
		      RAISE NOTICE 'Error in row %, grade % is not present in EP', row_number, trim_grade_name;
		   ELSE
		      SELECT organization_school_year(state_id) INTO school_year;
		      IF(school_year is null) THEN
		            RAISE NOTICE 'Error in row %, Organization: % school year is null', row_number, trim_state_displayidentifier; 
		      ELSIF((SELECT count(en.*) FROM enrollment en WHERE studentid = externalStudentReportRecord.studentid AND attendanceschoolid = school_id  
		           AND currentschoolyear = school_year) < 1) THEN
				RAISE NOTICE 'Error in row %, EPStudentId: % is not enrolled in school % for schoolyear: % ', row_number,
				         externalStudentReportRecord.studentid, trim_school_displayidentifier, school_year;
		       ELSE                               
                              SELECT regexp_split_to_array(trim_subject_abbrname, ',') INTO subjects_array;
			     FOR i IN array_lower(subjects_array, 1) .. array_upper(subjects_array, 1) LOOP
                                 SELECT id FROM contentarea where lower(abbreviatedname) = lower(trim(subjects_array[i])) INTO subject_id;

                                 IF(subject_id is null) THEN 
                                     RAISE NOTICE 'Error in row %, Subject: % is not found in EP', row_number, trim(subjects_array[i]);
                                 ELSE
                                     SELECT ('/reports/external/' || trim_assessmentprog_abbr_name || '/' 
						|| regexp_replace(trim_state_displayidentifier, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
						|| regexp_replace(trim_district_displayidentifer, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
						|| regexp_replace(trim_school_displayidentifier, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
						|| regexp_replace(trim(subjects_array[i]), '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
						|| regexp_replace(trim_grade_name, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
						|| trim_file_name) INTO ep_srv_file_path;
                                        
				    SELECT id FROM externalstudentreports WHERE lower(filepath) = lower(ep_srv_file_path) and studentid= externalStudentReportRecord.studentid
				        and subjectid = subject_id and schoolid = school_id and stateid = state_id  and assessmentprogramid = assessmentProgram_id
				        and districtid = district_id and gradeid = grade_id and schoolyear = school_year INTO existing_exteranlFile_id;

			            IF(existing_exteranlFile_id is not null) THEN			               
                                        UPDATE externalstudentreports SET studentid = externalStudentReportRecord.studentid, gradeid = grade_id, subjectid = subject_id, 
                                             stateid = state_id, assessmentprogramid = assessmentProgram_id, districtid = district_id, schoolid = school_id, level1_text = trim_level1_text, 
                                             level2_text = trim_level2_text, filepath = ep_srv_file_path, schoolyear = school_year, modifieddate = now(), modifieduser = ceteSysadmin_id                               
                                             WHERE id = existing_exteranlFile_id;                                                                                                     
				     ELSE				         
                                        INSERT INTO externalstudentreports(studentid, gradeid, subjectid, stateid, assessmentprogramid, districtid, schoolid, level1_text, level2_text, filepath, schoolyear, createduser, modifieduser)
                                             VALUES(externalStudentReportRecord.studentid, grade_id, subject_id, state_id, assessmentProgram_id,  district_id, school_id, trim_level1_text, trim_level2_text, ep_srv_file_path, school_year, ceteSysadmin_id, ceteSysadmin_id);
				     END IF;
			          END IF;
			     END LOOP;                                               
                          END IF; 			
                    END IF;  
		END IF;
           END IF; 
	END IF;
     END IF;
    END LOOP;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- 2 new columns on studentreport
ALTER TABLE studentreport DROP COLUMN IF EXISTS mdptlevelid;
ALTER TABLE studentreport ADD COLUMN mdptlevelid bigint;

ALTER TABLE studentreport DROP COLUMN IF EXISTS combinedlevelid;
ALTER TABLE studentreport ADD COLUMN combinedlevelid bigint;
