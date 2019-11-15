-- DE11027- Uploading Roster with 'Remove from Roster' option chose causes NotReadablePropertyException

update fieldspecification set fieldname = 'removefromroster'  
     where id = (select fieldspecificationid from fieldspecificationsrecordtypes where mappedname = 'Remove from roster' 
                      and recordtypeid =( select id from category where categorycode='SCRS_RECORD_TYPE' ));
