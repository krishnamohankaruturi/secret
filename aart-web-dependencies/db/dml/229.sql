--229.sql;

UPDATE fieldspecification set allowablevalues='{'''',00,01,02,03,04,05,99,0,1,2,3,4,5}',rejectifinvalid=true 
	where fieldname='enrollmentStatusCode'  and mappedname='Course_Status';
	