--515.sql

--US17446: SR - Production - Low - Generation Code field in EP not using same validations as the upload template.
UPDATE student 
SET generationcode = 'Jr'
where generationcode in ('Jr.','JR','JR.','jr','jr.','junior','JUNIOR');

UPDATE student 
SET generationcode = 'Sr'
where generationcode in ('Sr.','SR','SR.','sr','sr.','senior','SENIOR');

UPDATE student SET generationcode = 'II' WHERE generationcode ='ii';
UPDATE student SET generationcode = 'III' WHERE generationcode ='iii';
UPDATE student SET generationcode = 'IV' WHERE generationcode ='iv';
UPDATE student SET generationcode = 'V' WHERE generationcode ='v';
UPDATE student SET generationcode = 'VI' WHERE generationcode ='vi';
UPDATE student SET generationcode = 'VII' WHERE generationcode ='vii';
UPDATE student SET generationcode = 'VIII' WHERE generationcode ='viii';
UPDATE student SET generationcode = 'IX' WHERE generationcode ='ix';

UPDATE student SET generationcode = null
WHERE generationcode!='Jr' and generationcode!='Jr' and generationcode!='Sr' and generationcode!='II' and generationcode!='III' and 
	generationcode!='IV' and generationcode!='V' and generationcode!='VI' and generationcode!='VII' and generationcode!='VIII' and generationcode!='IX' and generationcode!=''; 


