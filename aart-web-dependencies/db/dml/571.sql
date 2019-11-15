-- 571.sql dml

-- US18971: DLM import student reports - handle 1 file with all subjects
/**-- To load data

        begin; 
DROP TABLE IF EXISTS temp_externalStudentReports;
          
CREATE TEMPORARY TABLE temp_externalStudentReports (reporttype character varying, filename character varying,
                                                        assessmentprogramabbrname character varying,
                                                        statedisplayidentifier character varying,
                                                        districtdisplayidentifier character varying,
                                                        schooldisplayidentifier character varying,
                                                        subjectabbrname character varying,
                                                        gradename character varying,
                                                        studentid bigint,
                                                        level1_text character varying,
                                                        level2_text character varying);

COPY temp_externalStudentReports FROM '//home/v673j085/Downloads/US17061/US18971.csv' WITH (FORMAT CSV, HEADER);

select uploadExternalStudentFilesToEP('/home/v673j085/Downloads/US17061/US18971.csv');

DROP TABLE IF EXISTS temp_externalStudentReports;
commit;

**/