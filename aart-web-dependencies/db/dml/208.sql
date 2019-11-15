
--DE6314
update fieldspecification set rejectifempty = true, rejectifinvalid=true, fieldlength=30 
where fieldname='residenceDistrictIdentifier' 
		and id in (select fieldspecificationid from fieldspecificationsrecordtypes 
				where recordtypeid = (select id from category where categorycode='SCRS_RECORD_TYPE'));