--dml/558.sql 
update fieldspecification set fieldlength=300 where id=(select id from fieldspecification where fieldname='subscoreReportDescription');