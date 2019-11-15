----ddl/653.sql ==> For dml/653.sql
--F475: K-ELPA calculations and return file
ALTER TABLE studentreport DROP COLUMN IF EXISTS reading_rawscore,
                          DROP COLUMN IF EXISTS listening_rawscore,
                          DROP COLUMN IF EXISTS speaking_rawscore,
                          DROP COLUMN IF EXISTS writing_rawscore;

ALTER TABLE studentreport ADD COLUMN reading_rawscore numeric(6,3),
                          ADD COLUMN listening_rawscore numeric(6,3),
                          ADD COLUMN speaking_rawscore numeric(6,3),
                          ADD COLUMN writing_rawscore numeric(6,3);

-- Add new columns to studentreport table
ALTER TABLE studentreport DROP COLUMN IF EXISTS reading_scalescore,
                          DROP COLUMN IF EXISTS listening_scalescore,
                          DROP COLUMN IF EXISTS speaking_scalescore,
                          DROP COLUMN IF EXISTS writing_scalescore;
                                                  
ALTER TABLE studentreport ADD COLUMN reading_scalescore bigint,
                          ADD COLUMN listening_scalescore bigint,
                          ADD COLUMN speaking_scalescore bigint,
                          ADD COLUMN writing_scalescore bigint;                                               

-- Add new columns to studentreport table
ALTER TABLE studentreport DROP COLUMN IF EXISTS reading_standarderror,
                          DROP COLUMN IF EXISTS listening_standarderror,
                          DROP COLUMN IF EXISTS speaking_standarderror,
                          DROP COLUMN IF EXISTS writing_standarderror;

ALTER TABLE studentreport ADD COLUMN reading_standarderror numeric(6,3),
                          ADD COLUMN listening_standarderror numeric(6,3),
                          ADD COLUMN speaking_standarderror numeric(6,3),
                          ADD COLUMN writing_standarderror numeric(6,3);
                                                  
-- Add new columns to studentreport table
ALTER TABLE studentreport DROP COLUMN IF EXISTS reading_sccode,
                          DROP COLUMN IF EXISTS listening_sccode,
                          DROP COLUMN IF EXISTS speaking_sccode,
                          DROP COLUMN IF EXISTS writing_sccode;
                                                  
ALTER TABLE studentreport ADD COLUMN reading_sccode boolean,
                          ADD COLUMN listening_sccode boolean,
                          ADD COLUMN speaking_sccode boolean,
                          ADD COLUMN writing_sccode boolean;


-- Add new columns to studentreport table
ALTER TABLE studentreport DROP COLUMN IF EXISTS reading_level,
                          DROP COLUMN IF EXISTS listening_level,
                          DROP COLUMN IF EXISTS speaking_level,
                          DROP COLUMN IF EXISTS writing_level;

ALTER TABLE studentreport ADD COLUMN reading_level bigint,
                          ADD COLUMN listening_level bigint,
                          ADD COLUMN speaking_level bigint,
                          ADD COLUMN writing_level bigint; 

-- Add new columns to studentreport table
ALTER TABLE studentreport DROP COLUMN IF EXISTS overall_scalescore,
                          DROP COLUMN IF EXISTS overall_level;
                                                  
ALTER TABLE studentreport ADD COLUMN overall_scalescore bigint,
                          ADD COLUMN overall_level bigint;

-- Add new columns to studentreport table
ALTER TABLE studentreport DROP COLUMN IF EXISTS comprehension_scalescore;
ALTER TABLE studentreport ADD COLUMN comprehension_scalescore bigint;


