--dml/653.sql ==> For ddl/653.sql
--F475: K-ELPA calculations and return file

update fieldspecification
set allowablevalues = '{'''',Listening,Reading,Writing,Speaking,Overall Proficiency,Overall Scale Score,Comprehension}',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id in (
	select distinct fs.id
	from fieldspecification fs
	join fieldspecificationsrecordtypes fsrt on fs.id = fsrt.fieldspecificationid
	join category c on fsrt.recordtypeid = c.id
	join categorytype ct on c.categorytypeid = ct.id
	where ct.typecode = 'REPORT_UPLOAD_FILE_TYPE'
	and c.categorycode in ('TEST_CUT_SCORES', 'RAW_TO_SCALE_SCORE')
	and fs.fieldname = 'domain'
);