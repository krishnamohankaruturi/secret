-- dml of 638.sql
update fieldspecification set allowablevalues = '{'''',2,3,A,B,D,E,F,G,GN,H,P,2Q,AQ,AM,BQ,DQ,DM,EM,FQ,GQ,HQ,HM,I}'
 where id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid = 472)
 and fieldname = 'testType';
 
 update testtype set activeflag = true where testtypecode = 'I';
 