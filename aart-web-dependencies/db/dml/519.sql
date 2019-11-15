--dml/519.sql
--US-17647
DELETE FROM statespecialcircumstance where stateid=(select org.id from organization org where org.displayidentifier = 'NY')
and specialcircumstanceid = (select id from specialcircumstance 
	where cedscode='13836' and specialcircumstancetype='Teacher cheating or mis-admin' and activeflag is true); 
	