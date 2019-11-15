 -- 458.sql
 
 -- Updating fieldspecification for tec record to accept the / in exit withdrawal date in case of exit record.
 update fieldspecification  set formatregex = '(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9]|^$', minimum = 2, maximum=4 where fieldname = 'exitDateStr' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'exitDateStr')
);

update fieldspecification  set rejectifempty = true where fieldname = 'comprehensiveRace' and 
id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE')
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'comprehensiveRace'));

-- populate role codes for all the remaining roles.
UPDATE groups SET groupcode='PDDAD' WHERE groupname='PD District Admin';
UPDATE groups SET groupcode='GSAD' WHERE groupname='Global System Administrator';
UPDATE groups SET groupcode='PDSAD' WHERE groupname='PD State Admin';
UPDATE groups SET groupcode='PDU' WHERE groupname='PD User';
UPDATE groups SET groupcode='PDAD' WHERE groupname='PD Admin';
UPDATE groups SET groupcode='HS' WHERE groupname='HS';
UPDATE groups SET groupcode='TAQCP' WHERE groupname='Test Administrator (QC Person)';
UPDATE groups SET groupcode='CAPAD' WHERE groupname='Consortium Assessment Program Administrator';
UPDATE groups SET groupcode='SAAD' WHERE groupname='State Assessment Administrator';
UPDATE groups SET groupcode='SSAD' WHERE groupname='State System Administrator';


 
