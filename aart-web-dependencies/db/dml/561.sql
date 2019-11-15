----dml/*.sql ==> For ddl/*.sql

--US18812: Reports: Weight item raw score by test ID and section
--Please manually delete the "Comments" column in the CSV file provided by the Psychometricians before proceeding to this section.
/*
BEGIN;

DELETE FROM rawscoresectionweights;

\COPY rawscoresectionweights(assessmentprogram, subject, testid,section_1_weight,section_2_weight)  
FROM 'C://work//reports_data//US18812//US18812_SectionWeightsForSocialStudies.csv' DELIMITER ',' CSV HEADER ;

select updaterawscoresectionweights('US18812_SectionWeightsForSocialStudies.csv');

SELECT * from rawscoresectionweights;

COMMIT;
*/