-- Changed level to Level_number for level description uploads

update fieldspecificationsrecordtypes set mappedname = 'Level_Number' where 
fieldspecificationid = (select id from fieldspecification where fieldname = 'level') 
and recordtypeid = (select id from category where categorycode = 'LEVEL_DESCRIPTIONS' and categorytypeid = (select id from categorytype where typecode = 'CSV_RECORD_TYPE'))