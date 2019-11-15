--Restrict Course value to empty in Roster upload (Do not accept any values in that column)
UPDATE fieldspecification 
SET allowablevalues ='{''''}'
WHERE 
id in (
select fieldspecificationid from fieldspecificationsrecordtypes 
where recordtypeid in (select id from category where categorycode='SCRS_RECORD_TYPE' and categorytypeid in (select id from categorytype where typecode = 'CSV_RECORD_TYPE'))
AND fieldspecificationid in (select id from fieldspecification where fieldname ilike '%stateCourseCode%')
);
