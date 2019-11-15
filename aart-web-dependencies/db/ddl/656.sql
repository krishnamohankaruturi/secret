--ddl/656.sql ==> For dml/656.sql
--F475: K-ELPA calculations and return file

ALTER TABLE studentreport DROP COLUMN IF EXISTS comprehension_rawscore,
						  DROP COLUMN IF EXISTS comprehension_standarderror;

ALTER TABLE studentreport ADD COLUMN comprehension_rawscore numeric(6,3),
						  ADD COLUMN comprehension_standarderror numeric(6,3);
						  
ALTER TABLE studentreport DROP COLUMN IF EXISTS overall_scalescore;						  
