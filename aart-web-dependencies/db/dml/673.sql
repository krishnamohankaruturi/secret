--dml/673.sql 

update modulereport set completedtime = modifieddate where statusid = (select distinct ct.id from category ct
join categorytype ctype on ctype.id=ct.categorytypeid and
ctype.activeflag is true
where ct.categoryname ilike 'COMPLETED' and ctype.typecode ilike
'PD_REPORT_STATUS');
