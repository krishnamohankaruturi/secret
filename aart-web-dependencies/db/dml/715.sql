--dml/715.sql

UPDATE rawscoresectionweights SET schoolyear = 2016 where schoolyear is null;

--script to upload section weights data from file into table

/*
BEGIN;

DELETE FROM rawscoresectionweights where schoolyear = 2018;

\COPY rawscoresectionweights(schoolyear, assessmentprogram, subject, testid,section_1_weight,section_2_weight)  
FROM '2018 Weight scores by test section KAP SS.csv' DELIMITER ',' CSV HEADER ;

select updaterawscoresectionweights('2018 Weight scores by test section KAP SS.csv', 2018);

SELECT * from rawscoresectionweights;

COMMIT;

*/