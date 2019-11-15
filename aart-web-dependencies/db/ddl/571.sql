-- 571.sql
-- Increasing the testsession name length to 300.
DROP VIEW node_report;
DROP VIEW total_item_summary_by_node;
ALTER TABLE testsession ALTER COLUMN name TYPE character varying(300);

CREATE OR REPLACE VIEW total_item_summary_by_node AS 
 SELECT st.id AS students_tests_id, st.studentid AS student_id, 
    st.testid AS test_id, st.testcollectionid AS test_collection_id, 
    tvln.nodecode AS node_key, cfd.contentcode AS content_framework_detail_code, 
    cfd.description AS content_framework_detail_description, 
    count(1) AS total_items_presented, tss.name AS test_session_name
   FROM studentstests st, category ststat, categorytype metatype, 
    testsection ts, testsectionstaskvariants tstv, 
    taskvariantcontentframeworkdetail tvcfd, contentframeworkdetail cfd, 
    taskvariantlearningmapnode tvln, testsession tss
  WHERE st.testsessionid = tss.id AND tvcfd.contentframeworkdetailid = cfd.id AND tvcfd.isprimary = true AND tvcfd.taskvariantid = tstv.taskvariantid AND st.testid = ts.testid AND tstv.testsectionid = ts.id AND st.status = ststat.id AND ststat.categorycode::text <> 'unused'::text AND metatype.id = ststat.categorytypeid AND metatype.typecode::text = 'STUDENT_TEST_STATUS'::text AND tstv.taskvariantid = tvln.taskvariantid
  GROUP BY st.id, st.studentid, st.testid, tvln.nodecode, cfd.contentcode, cfd.description, tss.name;

CREATE OR REPLACE VIEW node_report AS 
 SELECT s.id AS student_id, 
    s.statestudentidentifier AS state_student_identifier, 
    s.legalfirstname AS legal_first_name, 
    s.legalmiddlename AS legal_middle_name, s.legallastname AS legal_last_name, 
    s.comprehensiverace AS comprehensive_race, s.gender, 
    s.primarydisabilitycode AS primary_disability_code, ti_summary.node_key, 
    ti_summary.students_tests_id, ti_summary.test_id, t.testname AS test_name, 
    t.status AS test_status_id, tc.id AS test_collection_id, 
    tc.name AS test_collection_name, 
    nvl(cr_summary.no_of_correct_responses) AS no_of_correct_responses, 
    nvl(ir_summary.no_of_in_correct_responses) AS no_of_in_correct_responses, 
    nvl(cr_summary.no_of_correct_responses) + nvl(ir_summary.no_of_in_correct_responses) AS no_of_responses, 
    nvl(ai_summary.no_of_answered_items) AS no_of_answered_items, 
    nvl(cr_summary.total_raw_score::bigint) AS total_raw_score, 
    ti_summary.content_framework_detail_code, 
    ti_summary.content_framework_detail_description, 
    ti_summary.total_items_presented, ti_summary.test_session_name
   FROM student s, test t, testcollectionstests tct, testcollection tc, 
    total_item_summary_by_node ti_summary
   LEFT JOIN correct_response_summary_by_node cr_summary ON ti_summary.students_tests_id = cr_summary.students_tests_id AND ti_summary.node_key::text = cr_summary.node_key::text AND ti_summary.content_framework_detail_code::text = cr_summary.content_framework_detail_code::text
   LEFT JOIN in_correct_response_summary_by_node ir_summary ON ti_summary.students_tests_id = ir_summary.students_tests_id AND ti_summary.node_key::text = ir_summary.node_key::text AND ti_summary.content_framework_detail_code::text = ir_summary.content_framework_detail_code::text
   LEFT JOIN answered_item_summary_by_node ai_summary ON ti_summary.students_tests_id = ai_summary.students_tests_id AND ti_summary.node_key::text = ai_summary.node_key::text AND ti_summary.content_framework_detail_code::text = ai_summary.content_framework_detail_code::text
  WHERE tct.testid = t.id AND tct.testcollectionid = tc.id AND ti_summary.student_id = s.id AND ti_summary.test_id = t.id;
  
-- Add a new column to students tests table  
DO
$BODY$
BEGIN
	IF EXISTS (
		SELECT column_name
		FROM information_schema.columns
		WHERE table_name = 'studentstests' AND column_name = 'totalrawscore'
	) THEN
		RAISE NOTICE 'studentstests.totalrawscore already found, skipping add';
	ELSE
		ALTER TABLE IF EXISTS studentstests ADD COLUMN totalrawscore numeric(6,3);
	END IF;
	
	DROP INDEX IF EXISTS idx_studentstests_totalrawscore;
	CREATE INDEX idx_studentstests_totalrawscore ON studentstests
		USING btree (totalrawscore);
END
$BODY$;
  
--US18971: DLM import student reports - handle 1 file with all subjects
DROP FUNCTION IF EXISTS uploadexternalstudentfilestoep(text);
CREATE OR REPLACE FUNCTION uploadexternalstudentfilestoep(indexfilename text)
  RETURNS void AS
$BODY$
DECLARE
    ceteSysadmin_id BIGINT;
    batchProcess_id BIGINT;
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
    reporttype CHARACTER VARYING;
    subject CHARACTER VARYING;
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
    SELECT TRIM(externalStudentReportRecord.reporttype) INTO reporttype;

        IF(upper(reporttype) = 'STUDENT' OR upper(reporttype) = 'SCHOOL') THEN
        
     
        IF((trim_file_name = '' OR trim_file_name is null) 
            OR (trim_assessmentprog_abbr_name = '' OR trim_assessmentprog_abbr_name is null) 
            OR (trim_state_displayidentifier = '' OR trim_state_displayidentifier is null)
            OR (trim_district_displayidentifer = '' OR trim_district_displayidentifer is null) 
            OR (trim_school_displayidentifier = '' OR trim_school_displayidentifier is null)
            OR (trim_subject_abbrname = '' OR trim_subject_abbrname is null)
            OR (trim_grade_name = '' OR trim_grade_name is null)
            OR (externalStudentReportRecord.studentid is null)
            AND upper(reporttype) = 'STUDENT') THEN
            
            RAISE NOTICE 'In row %, one or more required fields are empty or null FileName: %, AssessmentProgram: %, State: %, District: %, School: %, Subject: %, Grade: %, EPStudentId: %',  
                   row_number, externalStudentReportRecord.filename, externalStudentReportRecord.assessmentprogramabbrname, externalStudentReportRecord.statedisplayidentifier,
                   externalStudentReportRecord.districtdisplayidentifier, externalStudentReportRecord.schooldisplayidentifier,
                   externalStudentReportRecord.subjectabbrname, externalStudentReportRecord.gradename, externalStudentReportRecord.studentid;

            ELSIF((trim_file_name = '' OR trim_file_name is null) 
                OR (trim_assessmentprog_abbr_name = '' OR trim_assessmentprog_abbr_name is null) 
                OR (trim_state_displayidentifier = '' OR trim_state_displayidentifier is null)
                OR (trim_district_displayidentifer = '' OR trim_district_displayidentifer is null) 
                OR (trim_school_displayidentifier = '' OR trim_school_displayidentifier is null)
                OR (trim_subject_abbrname = '' OR trim_subject_abbrname is null)
                OR (trim_grade_name = '' OR trim_grade_name is null)
                AND upper(reporttype) = 'SCHOOL') THEN

                RAISE NOTICE 'In row %, one or more required fields are empty or null FileName: %, AssessmentProgram: %, State: %, District: %, School: %, Subject: %, Grade: %',  
                       row_number, externalStudentReportRecord.filename, externalStudentReportRecord.assessmentprogramabbrname, externalStudentReportRecord.statedisplayidentifier,
                       externalStudentReportRecord.districtdisplayidentifier, externalStudentReportRecord.schooldisplayidentifier,
                       externalStudentReportRecord.subjectabbrname, externalStudentReportRecord.gradename;
            
        ELSIF(trim_file_name not ilike '%.pdf') THEN
            RAISE NOTICE 'In row %, file name is not ending wth pdf', row_number; 
        ELSE

          SELECT stateid FROM organizationtreedetail WHERE statedisplayidentifier = trim_state_displayidentifier LIMIT 1 INTO state_id;
          SELECT id FROM assessmentprogram WHERE UPPER(abbreviatedname) = UPPER(trim_assessmentprog_abbr_name) LIMIT 1 INTO assessmentProgram_id;

          IF(assessmentProgram_id is null) THEN 
             RAISE NOTICE 'Error in row %, Assessment program % value is not present in EP', row_number, trim_assessmentprog_abbr_name;
         ELSIF(state_id is null) THEN
            RAISE NOTICE 'Error in row %, state % is not present in EP. Expecting the displayidentifieres(example: KS, OK, MO,AK)', row_number, trim_state_displayidentifier;
         ELSIF((SELECT count(*) FROM student WHERE id = externalStudentReportRecord.studentid) <= 0) THEN
             RAISE NOTICE 'Error in row %, studentid % is not present in EP', row_number, externalStudentReportRecord.studentid;
         ELSE         
            SELECT distinct districtid FROM organizationtreedetail WHERE stateid = state_id AND districtdisplayidentifier = trim_district_displayidentifer LIMIT 1 INTO district_id;         
            IF(district_id is null) THEN
            RAISE NOTICE 'Error in row %, district % is not present in EP', row_number, trim_district_displayidentifer;
             ELSE
            
            SELECT distinct schoolid FROM organizationtreedetail WHERE stateid = state_id AND districtid = district_id AND schooldisplayidentifier = trim_school_displayidentifier LIMIT 1 INTO school_id;

            IF(school_id is null) THEN
               RAISE NOTICE 'Error in row %, school % is not present in EP', row_number, trim_school_displayidentifier;
            ELSE
              IF(lower(trim_assessmentprog_abbr_name) = lower('CPASS')) THEN
                SELECT id FROM gradecourse WHERE lower(abbreviatedname) = lower(trim_grade_name) LIMIT 1 INTO grade_id;
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
                   ELSE                               
                      SELECT regexp_split_to_array(trim_subject_abbrname, ',') INTO subjects_array;
                     FOR i IN array_lower(subjects_array, 1) .. array_upper(subjects_array, 1) LOOP
                     SELECT id FROM contentarea where lower(abbreviatedname) = lower(trim(subjects_array[i])) INTO subject_id;

                     IF(subject_id is null) THEN 
                         RAISE NOTICE 'Error in row %, Subject: % is not found in EP', row_number, trim(subjects_array[i]);
                     ELSE
                     
                       IF(upper(reporttype) = 'SCHOOL') THEN
                          SELECT ('/reports/external/' || UPPER(trim_assessmentprog_abbr_name) || '/' 
                            || school_year || '/'||'SD'||'/'    
                            || regexp_replace(trim_state_displayidentifier, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
                            || regexp_replace(trim_district_displayidentifer, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
                            || regexp_replace(trim_school_displayidentifier, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
                            || regexp_replace(trim(subjects_array[i]), '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
                            || regexp_replace(trim_grade_name, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
                            || trim_file_name) INTO ep_srv_file_path;
                        
                         SELECT id FROM organizationreportdetails WHERE lower(detailedreportpath) = lower(ep_srv_file_path)
                         and contentareaid = subject_id and organizationid = school_id and assessmentprogramid = assessmentProgram_id
                         and gradeid = grade_id and schoolyear = school_year INTO existing_exteranlFile_id;

                         IF(existing_exteranlFile_id is not null) THEN        

                        UPDATE organizationreportdetails SET gradeid = grade_id, contentareaid = subject_id, 
                             assessmentprogramid = assessmentProgram_id, organizationid = school_id,  
                             detailedreportpath = ep_srv_file_path, schoolyear = school_year, createddate = now()                             
                             WHERE id = existing_exteranlFile_id;                                                                                                     

                          ELSE                         
                        INSERT INTO reportprocess(id,assessmentprogramid, subjectid, gradeid, process, status, 
                                successcount, failedcount, resultjson, submissiondate, modifieddate, 
                                createduser, modifieduser, activeflag)
                            VALUES ((SELECT nextval('reportprocess_id_seq')),assessmentProgram_id, subject_id, grade_id, 'Importing External School Details:', 'COMPLETED', 
                                1, 0, null, now(), now(), ceteSysadmin_id,      
                                ceteSysadmin_id, TRUE) RETURNING id INTO batchProcess_id;
                            
                        INSERT INTO organizationreportdetails(gradeid, contentareaid, assessmentprogramid, organizationid, detailedreportpath, schoolyear,batchreportprocessid)
                             VALUES(grade_id, subject_id, assessmentProgram_id, school_id, ep_srv_file_path, school_year,batchProcess_id);

                          END IF;
                                

                       ELSIF(upper(reporttype) = 'STUDENT') THEN                           
                          IF ((upper(trim_subject_abbrname) LIKE '%ELA%') OR (upper(trim_file_name) LIKE '%ELA%')) THEN 
                             subject := 'ELA';
                          ELSE 
                                                 subject := regexp_replace(trim(subjects_array[i]), '[^-a-zA-Z0-9.\,()&'']', '_', 'g');
                          END IF;                      
                          SELECT ('/reports/external/' || UPPER(trim_assessmentprog_abbr_name) || '/' 
                            || school_year || '/'||'ISR'||'/'
                            || regexp_replace(trim_state_displayidentifier, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
                            || regexp_replace(trim_district_displayidentifer, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
                            || regexp_replace(trim_school_displayidentifier, '[^-a-zA-Z0-9.\,()&'']', '_', 'g') || '/'
                            || subject || '/'
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
                          ELSE
                        RAISE NOTICE 'Error in row %, reporttype % is not valid ', reporttype;
                         END IF;
                      END IF;
                     END LOOP;                                               
                  END IF;             
                END IF;  
            END IF;
           END IF; 
        END IF;
         END IF;
         ELSE                
        RAISE NOTICE 'Error in row %, reporttype % is not valid ', row_number, reporttype;
       END IF;    
         
    END LOOP;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
