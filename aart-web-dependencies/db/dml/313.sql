
-- comprehensive data correction
update category set categoryname='1' where categorycode='00001' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='2' where categorycode='00100' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='4' where categorycode='01000' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='8' where categorycode='00010' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);
update category set categoryname='5' where categorycode='10000' and categorytypeid=(select id from categorytype where typecode = 'KSDE_COMPREHENSIVE_RACE' and activeflag is true);


update student set comprehensiverace=null where comprehensiverace in ('     ','00002', '00004', '00007', '3', '4/9/1', '5/18/', '8/17/', '9/26/', '0    ','DLM', 'NA', 'Other', 'H    ', 'His  ','hisp ','Hisp ','HISP ','Hispa', 'I    ', 'C    ');
update student set comprehensiverace='1' where comprehensiverace in ('01', 'w    ', 'W    ', 'white', 'White', 'WHITE');
update student set comprehensiverace='2' where comprehensiverace in ('02', 'B    ', 'black', 'Black', 'BLACK', 'A    ', 'Afric');
update student set comprehensiverace='5' where comprehensiverace in ('05', 'amind', 'AmInd', '10   ', '100  ', '1000 ');
update student set comprehensiverace='4' where comprehensiverace in ('04', 'asian', 'Asian');
update student set comprehensiverace='8' where comprehensiverace='08';
update student set comprehensiverace='6' where comprehensiverace='06';
update student set comprehensiverace='7' where comprehensiverace='07';