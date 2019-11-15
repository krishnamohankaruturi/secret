
--US13540 Name: Auto Registration - Science Assessment: Use Read Aloud, Braille, Large Print, Paper English and Spanish additional accessibility flags for mapping

UPDATE testtype set accessibilityflagcode = 'paper' WHERE testtypecode = '1';

UPDATE testtype set accessibilityflagcode = 'spanish' WHERE testtypecode = '6';

UPDATE testtype set accessibilityflagcode = 'braille' WHERE testtypecode = '8';

UPDATE testtype set accessibilityflagcode = 'large_print' WHERE testtypecode = 'L';

UPDATE testtype set accessibilityflagcode = 'read_aloud' WHERE testtypecode = 'R';


