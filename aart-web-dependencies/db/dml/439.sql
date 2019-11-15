--/439.sql 

-- districtEntryDate
update fieldspecification  set formatregex = '(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9]|^$'  where fieldname = 'districtEntryDate' and 
id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE')
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'districtEntryDate'));
-- StateEntryDate
update fieldspecification  set formatregex = '(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9]|^$'  where fieldname = 'stateEntryDate' and 
id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE')
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'stateEntryDate'));

-- giftedStudent
update fieldspecification  set allowablevalues ='{yes,YES,Yes,No,'''',NO,no}' where fieldname = 'giftedStudent' and 
id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='ENRL_RECORD_TYPE')
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='ENRL_RECORD_TYPE' ) and fieldname = 'giftedStudent'));

	
            