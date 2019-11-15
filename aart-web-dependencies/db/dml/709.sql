--dml/709.sql
UPDATE fieldspecification SET allowablevalues = '{'''',BIO}' WHERE fieldname = 'stateCourseCode' AND mappedname is null and activeflag is true;