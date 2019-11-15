
--fix for validation of state subject area code
update fieldspecification set fieldlength=40 
where fieldname='stateSubjectAreaCode' and id 
in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid = (select id from category where categorycode='SCRS_RECORD_TYPE'));