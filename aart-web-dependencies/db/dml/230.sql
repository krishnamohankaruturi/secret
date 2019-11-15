
--DE6604
update fieldspecification set rejectifempty = true, rejectifinvalid = true 
where fieldname='currentGradeLevel' and id in (select fieldspecificationid from fieldspecificationsrecordtypes 
	where recordtypeid = (select id from category where categorycode='ENRL_RECORD_TYPE'));