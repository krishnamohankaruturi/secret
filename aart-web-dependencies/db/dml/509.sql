--dml/509.sql
update statespecialcircumstance set requireconfirmation=true 
where stateid=(select id from organization where displayidentifier = 'KS') 
and specialcircumstanceid=(select id from specialcircumstance where specialcircumstancetype = 'Cheating');