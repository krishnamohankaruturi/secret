----dml/*.sql ==> For ddl/*.sql
--US18811: Reports: Subscores - handle missing stages
--Please delete the "Comments" column in the CSV manually before proceeding to this section.

/*
BEGIN;

DELETE FROM subscoresmissingstages;

\COPY subscoresmissingstages(subject, grade, default_stage2_externaltestid,default_stage3_externaltestid,default_performance_externaltestid) FROM 'US18811_SubscoresHandleMissingStages_ELA.csv' DELIMITER ',' CSV HEADER ;
\COPY subscoresmissingstages(subject, grade, default_stage2_externaltestid,default_stage3_externaltestid,default_performance_externaltestid) FROM 'US18811_SubscoresHandleMissingStages_M.csv' DELIMITER ',' CSV HEADER ;

select updatesubscoresmissingstages('US18811_SubscoresHandleMissingStages_M.csv');

COMMIT;

SELECT * FROM subscoresmissingstages;
*/
