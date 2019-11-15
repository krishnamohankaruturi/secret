--dml/717.sql

--Place the files at some location and provide that path to the function call before executing these
---F559-DML--
/*

BEGIN;

DELETE FROM studentmetametricsmeasuresscore where schoolyear = 2018;
\COPY studentmetametricsmeasuresscore (gradecode,scalescore,researchmeasure,reportedmeasure,lowerrange,upperrange)  FROM 'Kansas Lexile Tables with Ranges.csv' DELIMITER ',' CSV HEADER;
select import_metametrics_measure('ELA', 'Kansas Lexile Tables with Ranges.csv');

SELECT count(*) from studentmetametricsmeasuresscore;
Rollback;
COMMIT;

BEGIN;

\COPY studentmetametricsmeasuresscore (gradecode,scalescore,researchmeasure,reportedmeasure,lowerrange,upperrange)  FROM 'Kansas Quantile Tables with Ranges.csv' DELIMITER ',' CSV HEADER;
select import_metametrics_measure('M', 'Kansas Quantile Tables with Ranges.csv');

SELECT count(*) from studentmetametricsmeasuresscore;
Rollback;
COMMIT;

*/