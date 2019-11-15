--222.sql;

update category set categoryname='American Indian or Alaska Native' where categorycode='00001' and categorytypeid = (select id from categorytype where typecode = 'COMPREHENSIVE_RACE');
update category set categoryname='Asian' where categorycode='00010' and categorytypeid = (select id from categorytype where typecode = 'COMPREHENSIVE_RACE');
update category set categoryname='Black or African American' where categorycode='00100' and categorytypeid = (select id from categorytype where typecode = 'COMPREHENSIVE_RACE');
update category set categoryname='Native Hawaiian or Other Pacific Islander' where categorycode='01000' and categorytypeid = (select id from categorytype where typecode = 'COMPREHENSIVE_RACE');
update category set categoryname='White' where categorycode='10000' and categorytypeid = (select id from categorytype where typecode = 'COMPREHENSIVE_RACE');