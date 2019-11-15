--dml/699.sql

--updates for F97 from changepond

update fieldspecification
set allowablevalues = '{TRUE,FALSE,true,false,True,False}'
where id in (
  select id
  from fieldspecification
  where fieldname='completedFlag'
  and rejectifempty is true
  and rejectifinvalid is true
  and mappedname ='Completed_Flag'
  and activeflag is true
);

update fieldspecificationsrecordtypes
set fieldspecificationid = (select id from fieldspecification where fieldname='stateId'and rejectifempty is true and rejectifinvalid is true and activeflag is true)
where fieldspecificationid = (select id from fieldspecification where fieldname='state'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true)
and recordtypeid = (
  SELECT id
  from category
  where categorycode = 'UPLOAD_ORGANIZATION_SCORE_CALC'
  and categorytypeid = (select id from categorytype where typecode ='CSV_RECORD_TYPE')
);

update fieldspecification
set rejectifinvalid = false, rejectifempty = false
where fieldname = 'standardError'
and mappedname = 'Standard_Error'
and activeflag is true;

-- Migrating reports from Colorado-cpass to Colorado
update externalstudentreports 
set schoolid=(select organizationid from organizationrelation where organizationid in(select distinct id from organization where organizationname='OTIS JUNIOR-SENIOR HIGH SCHOOL') and parentorganizationid in(select organizationid from organizationrelation where parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST')))),
districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='OTIS R 3')
and parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST'))),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='OTIS R 3')
and parentorganizationid=(select id from organization where organizationname ='Colorado-cPass' and organizationtypeid=
(select id from organizationtype where typecode ='ST')));


update externalstudentreports 
set schoolid=(select organizationid from organizationrelation where organizationid in(select distinct id from organization where organizationname='HAXTUN HIGH SCHOOL') and parentorganizationid in(select organizationid from organizationrelation where parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST')))), 
districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='HAXTUN RE 2J')
and parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST'))),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='HAXTUN RE 2J')
and parentorganizationid=(select id from organization where organizationname ='Colorado-cPass' and organizationtypeid=
(select id from organizationtype where typecode ='ST')));

update externalstudentreports 
set schoolid=(select organizationid from organizationrelation where organizationid in(select distinct id from organization where organizationname='WALSH HIGH SCHOOL') and parentorganizationid in(select organizationid from organizationrelation where parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST')))),
districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='WALSH RE 1')
and parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST'))),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='WALSH RE 1')
and parentorganizationid=(select id from organization where organizationname ='Colorado-cPass' and organizationtypeid=
(select id from organizationtype where typecode ='ST')));


update externalstudentreports 
set schoolid=(select schoolid from organizationtreedetail where schoolname='FORT MORGAN HIGH SCHOOL' and statename='Colorado'), 
districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='FORT MORGAN RE 3')
and parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST'))),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='FORT MORGAN RE 3')
and parentorganizationid=(select id from organization where organizationname ='Colorado-cPass' and organizationtypeid=
(select id from organizationtype where typecode ='ST')));

update externalstudentreports 
set schoolid=(select organizationid from organizationrelation where organizationid in(select distinct id from organization where organizationname='VALLEY HIGH SCHOOL') and parentorganizationid in(select organizationid from organizationrelation where parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST')))), 
districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='WELD COUNTY RE 1')
and parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST'))),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='WELD COUNTY RE 1')
and parentorganizationid=(select id from organization where organizationname ='Colorado-cPass' and organizationtypeid=
(select id from organizationtype where typecode ='ST')));

update externalstudentreports 
set schoolid=(select organizationid from organizationrelation where organizationid in(select distinct id from organization where organizationname='LAS ANIMAS HIGH SCHOOL') and parentorganizationid in(select organizationid from organizationrelation where parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST')))),  
districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='LAS ANIMAS RE 1')
and parentorganizationid=(select id from organization where organizationname ='Colorado' and organizationtypeid=
(select id from organizationtype where typecode ='ST'))),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where districtid =(select organizationid from organizationrelation where 
organizationid in(select distinct id from organization where organizationname='LAS ANIMAS RE 1')
and parentorganizationid=(select id from organization where organizationname ='Colorado-cPass' and organizationtypeid=
(select id from organizationtype where typecode ='ST')));

