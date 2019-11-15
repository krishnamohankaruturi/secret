--dml/41.sql 
-- DE16298
update batchupload set assessmentprogramname  ='KELPA2',
--modifieduser=(select id from aartuser where username = 'cetesysadmin'),//aartuser table does not exist in aartaudit database
modifieddate=now() 
where assessmentprogramname ='K-ELPA';

