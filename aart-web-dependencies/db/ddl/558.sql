-- Scenario 9 - addNewEnrollment
DROP FUNCTION IF EXISTS addNewEnrollment(character varying, character varying, character varying, character varying, character varying, bigint,
         date, date, date, character varying, character varying);

CREATE OR REPLACE FUNCTION addNewEnrollment(statestudent_identifier character varying, localState_StuId character varying, aypSch character varying, attSch character varying, district character varying, schoolyear bigint,
         schEntryDate date, distEntryDate date, state_EntryDate date, grade character varying, stateDisplayidentifier character varying)
        RETURNS TEXT AS
$BODY$
 DECLARE
   state_Id BIGINT;
   ayp_sch_id BIGINT;
   att_sch_id BIGINT;
   dist_id BIGINT;
   ceteSysAdminUserId BIGINT;
   new_EnrlId BIGINT;
   newSchEnrlRecord RECORD;
   grade_id BIGINT;
   student_id BIGINT;
   sch_entry_date timestamp with time zone;
   district_entry_date timestamp with time zone;
   state_entry_date timestamp with time zone;
   error_msg TEXT;

 BEGIN
   error_msg :='';
   sch_entry_date:=((schEntryDate::timestamp) AT TIME ZONE 'CDT');
   district_entry_date:=((distEntryDate::timestamp) AT TIME ZONE 'CDT');
   state_entry_date:=((state_EntryDate::timestamp) AT TIME ZONE 'CDT');
   IF (select count(*) from (select o.schooldisplayidentifier tree_schooldisplayidentifier,o.stateDisplayidentifier tree_stateDisplayidentifier from organizationtreedetail o) org 
            where lower(trim(org.tree_stateDisplayidentifier))=lower(trim(stateDisplayidentifier))
            and (lower(org.tree_schooldisplayidentifier)=lower(attSch) or lower(org.tree_schooldisplayidentifier)=lower(aypSch))  
       group by org.tree_schooldisplayidentifier,org.tree_stateDisplayidentifier order by 1 desc limit 1 ) >1
   THEN 
       error_msg := '<error> Duplicate display Identifier on school more info>> '||'Student:'||COALESCE(statestudent_identifier,'NULL')||';State:'||COALESCE(stateDisplayidentifier,'NULL')||';AND Attendance school:'||COALESCE(attSch,'NULL')||';OR AYP School:'||COALESCE(aypSch,'NULL');
       RAISE NOTICE '%',error_msg;
   ELSE
	SELECT INTO state_Id (SELECT id FROM organization WHERE lower(trim(displayidentifier)) = lower(trim(stateDisplayidentifier)) AND activeflag is true);
	SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(trim(schooldisplayidentifier)) = lower(trim(aypSch)));
	SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(trim(schooldisplayidentifier)) = lower(trim(attSch)));
	SELECT INTO dist_id (SELECT districtid FROM organizationtreedetail WHERE stateid = state_Id AND lower(trim(schooldisplayidentifier)) = lower(trim(district)));
	SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
        SELECT INTO grade_id (SELECT id FROM gradecourse gc WHERE contentareaid IS NULL AND assessmentprogramgradesid IS NOT NULL
					AND abbreviatedname = grade AND activeflag is true);
        SELECT INTO student_id (SELECT id FROM student WHERE lower(trim(statestudentidentifier)) = lower(trim(statestudent_identifier)) AND stateid = state_Id AND activeflag is true LIMIT 1);

    IF (student_id IS NULL)

     THEN
	RAISE NOTICE 'Student % is not present in state %', statestudent_identifier, stateDisplayidentifier;
	error_msg = '<error>'||'Student :'|| COALESCE(statestudent_identifier,'NULL') || ';  not present in state:' || COALESCE(stateDisplayidentifier,'NULL');

    ELSE
    IF (grade_id IS NULL)

     THEN
	RAISE NOTICE 'Invalid grade  %', grade;
	error_msg = '<error>'||'Student :'|| COALESCE(statestudent_identifier,'NULL') || '; grade Invalid:' || COALESCE(grade,'NULL');

    ELSE    
      IF((SELECT  count(*) FROM enrollment WHERE studentid = student_id AND currentschoolyear = schoolyear 
		AND aypschoolid = ayp_sch_id and attendanceschoolid = att_sch_id) <= 0)
        THEN
          INSERT INTO enrollment(aypschoolidentifier, residencedistrictidentifier, localstudentidentifier,
		currentgradelevel, currentschoolyear, attendanceschoolid,
		schoolentrydate, districtentrydate, stateentrydate, exitwithdrawaldate,
		exitwithdrawaltype, studentid, restrictionid, createddate, createduser,
		activeflag, modifieddate, modifieduser, aypSchoolId, sourcetype)
	     VALUES (aypSch, district, localState_StuId,
		     grade_id, schoolyear, att_sch_id,
		     sch_entry_date, district_entry_date, state_entry_date, null,
		     0, student_id, 2, now(), ceteSysAdminUserId,
		     true, now(), ceteSysAdminUserId, ayp_sch_id, 'LOCK_DOWN_SCRIPT') RETURNING id INTO new_EnrlId;

	    INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT', new_EnrlId, ceteSysAdminUserId, now(),
		'NEW_ENROLLMENT', ('{"studentId":'|| student_id || ',"stateId":' ||  state_Id
				|| ',"stateStudentIdentifier":"' ||  statestudent_identifier
				|| '","aypSchool":' || ayp_sch_id || ',"attendanceSchoolId":'|| att_sch_id
				|| ',"grade":' || grade_id || ',"schoolEntryDate":"' || schEntryDate ||  '"}')::json);

	RAISE NOTICE 'Student % is enrolled into School % for school year % and grade level %', statestudent_identifier, attSch, schoolyear, grade;
	error_msg = '<success>'||'Student:' || COALESCE(statestudent_identifier,'NULL') || ';  enrolled into School:' || COALESCE(attSch,'NULL') || ';for school year:' || CAST(COALESCE(schoolyear,0) AS TEXT)  || ';and grade level:' || COALESCE(grade,'NULL');

        ELSE
	      FOR newSchEnrlRecord IN (SELECT stu.statestudentidentifier, en.* FROM enrollment en JOIN student stu ON stu.id = en.studentid
	                WHERE stu.id = student_id AND aypschoolid = ayp_sch_id AND attendanceschoolid = att_sch_id
                         AND currentschoolyear = schoolyear LIMIT 1)
             LOOP
		IF(schEntryDate is null) THEN
		     sch_entry_date := newSchEnrlRecord.schoolentrydate;
		ELSE
		     sch_entry_date := sch_entry_date;
		END IF;

                IF(distEntryDate is null) THEN
                    district_entry_date := newSchEnrlRecord.districtentrydate;
                ELSE
                   district_entry_date := district_entry_date;
                END IF;

                IF(state_EntryDate is null) THEN
                   state_entry_date := newSchEnrlRecord.stateentrydate;
                ELSE
                   state_entry_date := state_entry_date;
                END IF;

		UPDATE enrollment SET currentgradelevel= grade_id,activeflag = true, schoolentrydate = sch_entry_date, districtentrydate = district_entry_date, stateentrydate = state_entry_date,exitwithdrawaldate = null,
		          exitwithdrawaltype = 0, modifieduser = ceteSysAdminUserId, modifieddate = now() WHERE id = newSchEnrlRecord.id;

		RAISE NOTICE 'Enrollment % is updated, student % in school year %', newSchEnrlRecord.id, newSchEnrlRecord.statestudentidentifier, schoolyear;

		error_msg = '<success>'|| 'Enrollment:' || CAST(COALESCE(newSchEnrlRecord.id,0) AS TEXT)  || ';  updated for student:' || COALESCE(newSchEnrlRecord.statestudentidentifier,'NULL') || ';in school year:' || CAST(COALESCE(schoolyear,0) AS TEXT) ;

             END LOOP;
        END IF;
        END IF;
      END IF;
      END IF;
RETURN error_msg;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;


DROP FUNCTION uploadexternalstudentfilestoep(text);

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
  
  
  ALTER TABLE subscoresdescription ALTER COLUMN subscorereportdescription TYPE character varying(300);