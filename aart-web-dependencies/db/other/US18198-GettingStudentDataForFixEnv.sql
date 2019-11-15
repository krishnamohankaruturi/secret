DROP TABLE IF EXISTS student_fix_details;
CREATE TEMP TABLE student_fix_details(statestudentidentifier CHARACTER VARYING, aypschooldisplayidentifier CHARACTER VARYING, attendanceschooldisplayidentifier CHARACTER VARYING, studentid BIGINT);
DO
$BODY$
DECLARE 
temp_stuenrlrecord RECORD;
current_ayp_school_id BIGINT;
current_attendance_school_id BIGINT;
student_id BIGINT;
ks_state_id BIGINT;
BEGIN

     FOR temp_stuenrlrecord IN (SELECT * FROM temp_students_enrollments) LOOP
       SELECT id FROM organization WHERE displayidentifier = 'KS' INTO ks_state_id;
       SELECT schoolid FROM organizationtreedetail WHERE schooldisplayidentifier = temp_stuenrlrecord.aypschooldisplayidentifier AND stateid = ks_state_id INTO current_ayp_school_id;
       SELECT schoolid FROM organizationtreedetail WHERE schooldisplayidentifier = temp_stuenrlrecord.attendanceschooldisplayidentifier AND stateid = ks_state_id INTO current_attendance_school_id;
       SELECT id FROM student WHERE statestudentidentifier = temp_stuenrlrecord.statestudentidentifier AND stateid = ks_state_id AND activeflag IS true INTO student_id;
	   
       IF((SELECT count(*) from enrollment where studentid = student_id AND aypschoolid = current_ayp_school_id AND attendanceschoolid = current_attendance_school_id
             AND currentschoolyear = 2016 AND activeflag is true) > 0) THEN

              INSERT INTO student_fix_details(statestudentidentifier, aypschooldisplayidentifier, attendanceschooldisplayidentifier, studentid) VALUES (temp_stuenrlrecord.statestudentidentifier, temp_stuenrlrecord.aypschooldisplayidentifier,
                temp_stuenrlrecord.attendanceschooldisplayidentifier, student_id);

        ELSE

          RAISE NOTICE 'Student % not found in ayp school % and attendance school %', temp_stuenrlrecord.statestudentidentifier, temp_stuenrlrecord.aypschooldisplayidentifier,
                temp_stuenrlrecord.attendanceschooldisplayidentifier;

        END IF;

     END LOOP;

END;
$BODY$;
\COPY (select * from  student_fix_details) to 'student_fix_env__details.csv' DELIMITER ',' CSV HEADER ;
DROP TABLE IF EXISTS student_fix_details;