
-- 395.sql

update fieldspecificationsrecordtypes set mappedname = 'Level_Number' where recordtypeid = (select id from category where categorycode = 'LEVEL_DESCRIPTIONS' and categorytypeid = (select id from categorytype where typecode = 'CSV_RECORD_TYPE'))
and fieldspecificationid = (select id from fieldspecification where fieldname = 'level');