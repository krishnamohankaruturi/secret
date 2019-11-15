Select * from organization where organizationname like '%SCHOOL_BLDG_%';

--update organization set organizationname='SCHOOL_BLDG_'||displayIdentifier where organizationname like '%ORG_%';

Select * from organization where organizationname like '%SCHOOL_BLDG_%' and id between 2001 and 5000;

Select * from organization where organizationname like '%Colorado';

Select * from organization where organizationname not like '%SCHOOL_BLDG_%' 

Select * from organizationrelation;

insert into organizationrelation (Select id,12 from organization where organizationname like '%SCHOOL_BLDG_%' and id between 51 and 1000
);

insert into organizationrelation (Select id,13 from organization where organizationname like '%SCHOOL_BLDG_%' and id between 1001 and 2000
);


insert into organizationrelation (Select id,14 from organization where organizationname like '%SCHOOL_BLDG_%' and id between 2001 and 3078
);