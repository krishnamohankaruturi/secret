--dml/556.sql -- empty
/*
begin;
--cretae temp table
DROP TABLE IF EXISTS temp_externalStudentReports;
          
CREATE TEMPORARY TABLE temp_externalStudentReports (filename character varying,
                                                        assessmentprogramabbrname character varying,
                                                        statedisplayidentifier character varying,
                                                        districtdisplayidentifier character varying,
                                                        schooldisplayidentifier character varying,
                                                        subjectabbrname character varying,
                                                        gradename character varying,
                                                        studentid bigint,
                                                        level1_text character varying,
                                                        level2_text character varying);

\COPY temp_externalStudentReports FROM 'KS - AG&FR - Mandatory checking.csv' WITH (FORMAT CSV, HEADER);

select uploadExternalStudentFilesToEP('KS - AG&FR - Mandatory checking.csv');

DROP TABLE IF EXISTS temp_externalStudentReports;
commit;
*/