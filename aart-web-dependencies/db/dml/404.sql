-- DE9812: Not reporting all errors, just the first one.
-- Updated fieldtype of 'scale score' to number to validate as integer. Otherwise decimal values are throwing exceptions because of which rest of the errors are not shown
select * from fieldspecification
where id = (select fieldspecificationid from fieldspecificationsrecordtypes 
where recordtypeid = (select id from category where categorytypeid=(select id from categorytype where typecode = 'CSV_RECORD_TYPE' and categorycode='RAW_TO_SCALE_SCORE'))
and mappedname='Scale_Score');
