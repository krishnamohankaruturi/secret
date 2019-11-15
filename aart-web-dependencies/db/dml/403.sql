-- DE9813: Accepted non-numeric Low cut score 
-- Updated fieldtype of low cut score and high cut score to number to validate as integer
update fieldspecification 
set fieldtype = 'number'
where id = (select fieldspecificationid from fieldspecificationsrecordtypes 
where recordtypeid = (select id from category where categorytypeid=(select id from categorytype where typecode = 'CSV_RECORD_TYPE' and categorycode='TEST_CUT_SCORES'))
and mappedname='Level_Low_Cut_Score');

update fieldspecification 
set fieldtype = 'number'
where id = (select fieldspecificationid from fieldspecificationsrecordtypes 
where recordtypeid = (select id from category where categorytypeid=(select id from categorytype where typecode = 'CSV_RECORD_TYPE' and categorycode='TEST_CUT_SCORES'))
and mappedname='Level_High_Cut_Score');

