--dml/710.sql

update fieldspecification set fieldlength  = null, modifieddate = now()
where id in(
select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid in(
select id from category where categorycode  = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' 
and categorytypeid = (select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE'))
and mappedname = 'Sub-score_Report_Description');