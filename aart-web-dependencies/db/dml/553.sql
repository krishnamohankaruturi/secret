--dml/553.SQL

--SQL TO RUN BULK UPDATE SC CODES

--BEGIN;
--DROP TABLE IF EXISTS tmp_x;
--CREATE TEMP TABLE tmp_x (state text, district text, school text, educatorLastName text, educatorFirstName text, studentLastName text,
--                studentFirstName text, studentMiddleName text, stateStudentIdentifier text, assessmentProgram text, subject text, testSessionName text,
--                scCodeDescription text, cedsCodeNumber text, stateCodeNumber text, approvalStatus text, approverLastName text, approverFirstName text,
--                approvalDateTime text); 

--\COPY tmp_x FROM 'SC_upload.csv' DELIMITER ',' CSV HEADER ;
--SELECT count(*) as temp_x_count from tmp_x;
--SELECT update_special_circumstance_status();
--DROP TABLE IF EXISTS tmp_x;
--COMMIT;
