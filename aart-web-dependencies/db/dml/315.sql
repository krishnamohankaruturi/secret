
-- 315.sql
delete from fieldspecificationsrecordtypes 
where fieldspecificationid=(select id from fieldspecification where fieldname='comprehensiveRace' and mappedname is null)
and recordtypeid=(select id from category where categorycode='TEST_RECORD_TYPE');

delete from fieldspecificationsrecordtypes 
where fieldspecificationid=(select id from fieldspecification where fieldname='comprehensiveRace' and mappedname is null)
and recordtypeid=(select id from category where categorycode='EXIT_RECORD_TYPE');

update fieldspecification set allowablevalues= '{'''',1,2,4,5,6,7,8}', minimum=null, maximum=null
where id = (select id from fieldspecification where fieldname='comprehensiveRace' and mappedname is null);

update student set comprehensiverace=NULL where comprehensiverace='3';

update category set categoryname='5' where categorycode='00001' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='2' where categorycode='00100' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='8' where categorycode='01000' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='4' where categorycode='00010' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='1' where categorycode='10000' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);

delete from fieldspecificationsrecordtypes 
where fieldspecificationid=(select id from fieldspecification where fieldname='comprehensiveRace' and mappedname is null)
and recordtypeid=(select id from category where categorycode='TEST_RECORD_TYPE');

delete from fieldspecificationsrecordtypes 
where fieldspecificationid=(select id from fieldspecification where fieldname='comprehensiveRace' and mappedname is null)
and recordtypeid=(select id from category where categorycode='EXIT_RECORD_TYPE');

update fieldspecification set allowablevalues= '{'''',1,2,4,5,6,7,8}', minimum=null, maximum=null
where id = (select id from fieldspecification where fieldname='comprehensiveRace' and mappedname is null);

update student set comprehensiverace=NULL where comprehensiverace='3';
