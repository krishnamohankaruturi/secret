---- Roster Tec File upload
---update Query To Remove Fields for validation 

update fieldspecificationsrecordtypes set activeflag=false 
where recordtypeid=(select id from category where categorycode='SCRS_RECORD_TYPE')
and mappedname in ('Assessment Program 1','Assessment Program 2','Assessment Program 3','Assessment Program 4');

update fieldspecificationsrecordtypes set activeflag=false 
where recordtypeid =(select id from category where categorycode='TEC_RECORD_TYPE') and mappedname in ('Grade');
