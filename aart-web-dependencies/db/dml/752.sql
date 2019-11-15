--dml/752.sql

update category set categoryname = 'Student Summary (Bundled)' where categorytypeid = (select id from categorytype 
where typecode = 'REPORT_TYPES_UI') and activeflag is true and categorycode = 'ALT_ST_SUM_ALL';

update category set categoryname = 'School Aggregate (Bundled)' where categorytypeid = (select id from categorytype 
where typecode = 'REPORT_TYPES_UI') and activeflag is true and categorycode = 'ALT_SCH_SUM_ALL';

update fieldspecificationsrecordtypes set mappedname ='Kite_Educator_Identifier' , modifieddate =now() 
where mappedname='KITE_Educator_Identifier' ;
