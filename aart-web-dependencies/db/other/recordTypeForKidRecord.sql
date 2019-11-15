insert into fieldspecification(fieldname,allowablevalues,fieldlength,rejectifempty,rejectifinvalid,mappedname)
values ('recordType','{TEST}',4,true,true,'RECORD_TYPE');


insert into fieldspecificationsrecordtypes values
(
(Select id from fieldspecification where fieldname = 'recordType'),
(Select id from category where categorycode='KID_RECORD_TYPE')
);

update fieldspecification set showerror = (rejectifempty or rejectifInvalid)
where id in (select fieldspecificationid from fieldspecificationsrecordtypes,category recordType
where recordType.id = recordTypeId and recordType.categoryCode = 'KID_RECORD_TYPE'); 