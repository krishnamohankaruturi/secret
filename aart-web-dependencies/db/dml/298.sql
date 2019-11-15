
--DE7775
update fieldspecification
set fieldlength=254 where id = (
	select fs.id from fieldspecificationsrecordtypes fsrt 
	inner join fieldspecification fs on fsrt.fieldspecificationid = fs.id 
	where fs.fieldname='email' and recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE')
);

update fieldspecification
set fieldlength=254 where id = (
	select fs.id from fieldspecificationsrecordtypes fsrt 
	inner join fieldspecification fs on fsrt.fieldspecificationid = fs.id 
	where fs.fieldname='educatorIdentifier' and recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE')
);
