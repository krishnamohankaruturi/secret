--dml/211.sql

UPDATE fieldspecification set allowablevalues='{'''',00,01,02,03,04,05,99}',rejectifinvalid=true 
	where fieldname='enrollmentStatusCode'  and mappedname='Course_Status';
	