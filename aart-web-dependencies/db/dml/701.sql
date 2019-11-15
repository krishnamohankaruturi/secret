--701.sql dml
-- DE16404: EXIT template not allowing valid exit codes
update fieldspecification set allowablevalues = '{'''',1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,30,98,99}',
	modifieddate = now(), modifieduser = 12
where fieldname = 'exitReason' 
and id in (select fieldspecificationid from fieldspecificationsrecordtypes 
			where recordtypeid = (select id from category where categorycode = 'TEC_RECORD_TYPE' 
			and categorytypeid = (select id from categorytype where typecode ='CSV_RECORD_TYPE')));


	