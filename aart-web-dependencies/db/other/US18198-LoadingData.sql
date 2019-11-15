DROP TABLE IF EXISTS temp_students_enrollments;

CREATE TABLE temp_students_enrollments(statestudentidentifier CHARACTER VARYING, aypschooldisplayidentifier CHARACTER VARYING, attendanceschooldisplayidentifier CHARACTER VARYING);

\COPY temp_students_enrollments FROM 'ks_students_enrollments_to_inactive.csv' DELIMITER ',' CSV HEADER ;
