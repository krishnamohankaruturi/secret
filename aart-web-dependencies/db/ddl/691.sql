--ddl/691.sql
--Deactivating the students in Colorado-cPass State
update student set stateid=(select id from organization where organizationname='Colorado' 
and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true)),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where stateid=(select id from organization where organizationname='Colorado-cPass' 
and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true));


update reportassessmentprogram set stateid=(select id from organization where organizationname='Colorado' 
and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true)),
modifieddate=now()
where stateid=(select id from organization where organizationname='Colorado-cPass' 
and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true));

update externalstudentreports set stateid=(select id from organization where organizationname='Colorado' 
and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true)),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where stateid=(select id from organization where organizationname='Colorado-cPass' 
and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true));


--insering entry in orgassessmentprogram for adding CPASS to Colorado
INSERT INTO orgassessmentprogram
    (organizationid, assessmentprogramid, createddate, createduser, activeflag, modifieddate, modifieduser)
SELECT (SELECT id FROM organization WHERE organizationname ='Colorado' 
		and organizationtypeid=(select id from organizationtype where typecode ='ST')),
    	(SELECT id FROM assessmentprogram WHERE abbreviatedname ='CPASS'), now(),
    	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), true, now(), 
    	(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
WHERE NOT EXISTS (
SELECT 1 FROM orgassessmentprogram WHERE organizationid=(SELECT id FROM organization WHERE organizationname ='Colorado' 
    and organizationtypeid=(SELECT id FROM organizationtype WHERE typecode ='ST'))
	and assessmentprogramid =(SELECT id FROM assessmentprogram WHERE abbreviatedname ='CPASS')
);


---Users script for SSA&SAA Roles 

CREATE OR REPLACE FUNCTION public.retirecoloradocpassuserorganizations()
RETURNS void AS
$BODY$
declare
    user_rec record;
    olduserorganizationsgroupsid record;
    newuserorganizationsgroupsid record;
    neworganizationid record;
begin
   RAISE NOTICE 'begining';
   for user_rec in select distinct au.email,uog.groupid from usersorganizations uo
    inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
    inner join userassessmentprogram uap on uap.userorganizationsgroupsid=uog.id
    inner join aartuser au on au.id=uo.aartuserid
    inner join organization o on o.id = uo.organizationid
    inner join groups g on g.id=uog.groupid and g.activeflag is true
    where uo.organizationid in(
    (select id from organization where organizationname ='Colorado-cPass' and organizationtypeid=
    (select id from organizationtype where typecode ='ST'))) order by email
    loop 
       SELECT INTO olduserorganizationsgroupsid (select uog.id from usersorganizations uo
        inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
        inner join userassessmentprogram uap on uap.userorganizationsgroupsid=uog.id
        where uap.aartuserid in(select id from aartuser where email =user_rec.email)
        and uo.organizationid=(select id from organization  where organizationname='Colorado-cPass' 
        and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true))
        and uog.groupid=user_rec.groupid);
        
        SELECT INTO newuserorganizationsgroupsid (select distinct uog.id from usersorganizations uo
        inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
        where uo.aartuserid in(select id from aartuser where email =user_rec.email)
        and uo.organizationid=(select id from organization  where organizationname='Colorado' 
        and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true))
        and uog.groupid=user_rec.groupid);

        SELECT INTO neworganizationid (select distinct uo.organizationid from usersorganizations uo
        inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
        where uo.aartuserid in(select id from aartuser where email =user_rec.email)
        and uo.organizationid=(select id from organization  where organizationname='Colorado' 
        and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true)));
        
         RAISE NOTICE 'olduserorganizationsgroupsid : % newuserorganizationsgroupsid : % email: %', 
      olduserorganizationsgroupsid.id,newuserorganizationsgroupsid.id, user_rec.email;
      
       IF newuserorganizationsgroupsid IS NOT NULL THEN
       
       update userassessmentprogram set userorganizationsgroupsid =(select uog.id from usersorganizations uo
        inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
        inner join userassessmentprogram uap on uap.userorganizationsgroupsid=uog.id
        where uap.aartuserid in(select id from aartuser where email =user_rec.email)
        and uo.organizationid=(select id from organization  where organizationname='Colorado' 
        and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true))
        and uog.groupid=user_rec.groupid)
        where userorganizationsgroupsid in
        (select uog.id from usersorganizations uo
        inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
        inner join userassessmentprogram uap on uap.userorganizationsgroupsid=uog.id
        where uap.aartuserid in(select id from aartuser where email =user_rec.email)
        and uo.organizationid=(select id from organization  where organizationname='Colorado-cPass' 
        and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true))
        and uog.groupid=user_rec.groupid);

    ELSIF newuserorganizationsgroupsid IS NULL AND neworganizationid IS NOT null THEN

    update userorganizationsgroups set userorganizationid=(select uo.id from usersorganizations uo
    inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
    where uo.aartuserid in(select id from aartuser where email =user_rec.email)
    and uo.organizationid=(select id from organization  where organizationname='Colorado' 
    and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true))),
    modifieddate=now(),
    modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
    where userorganizationid=(select uo.id from usersorganizations uo
    inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
    where uo.aartuserid in(select id from aartuser where email =user_rec.email)
    and uo.organizationid=(select id from organization  where organizationname='Colorado-cPass' 
    and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true))
    and uog.groupid=user_rec.groupid) and groupid=user_rec.groupid;

    ELSIF neworganizationid IS null THEN
    
    RAISE NOTICE 'neworganizationid %', neworganizationid.organizationid;
      
    update usersorganizations set organizationid=(select id from organization where organizationname='Colorado' 
    and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true)),
    modifieddate=now(),
    modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
    where organizationid=(select id from organization where organizationname='Colorado-cPass' 
    and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true))
    and aartuserid in(select id from aartuser where email =user_rec.email);

    END IF;
    end loop;
    END;
    $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  select * from public.retirecoloradocpassuserorganizations();

--Deactivating the Colorado-cPass  in usersorganizations table
update usersorganizations set activeflag = false,
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
where organizationid=(select id from organization where organizationname='Colorado-cPass'
and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true));

--Deactivating the userorganizationsgroups relatated to Colorado-cPass 
update userorganizationsgroups set activeflag =false,
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
where id in(
select uog.id from usersorganizations uo
inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
where uo.organizationid=(select id from organization where organizationname='Colorado-cPass'
and organizationtypeid=(select id from organizationtype where typecode ='ST' and activeflag is true)));


--users script for DTC
update usersorganizations  set activeflag=false,
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid=(select distinct districtid from organizationtreedetail  where statename='Colorado-cPass' and districtname='KIT CARSON R 1') and
aartuserid=(select id from aartuser where username ='agekeler@rebeltec.net');

update userorganizationsgroups set activeflag=false,
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where userorganizationid =(select uo.id from usersorganizations uo
inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
where uo.aartuserid=(select id from aartuser where username ='agekeler@rebeltec.net') 
and uo.organizationid=(select distinct districtid from organizationtreedetail  where statename='Colorado-cPass' and districtname='KIT CARSON R 1')
);

update userassessmentprogram set userorganizationsgroupsid =(select uog.id from usersorganizations uo
inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
inner join userassessmentprogram uap on uap.userorganizationsgroupsid=uog.id
where uap.aartuserid =(select id from aartuser where username ='agekeler@rebeltec.net')
and uo.organizationid=(select distinct districtid from organizationtreedetail  where statename='Colorado' and districtname='KIT CARSON R 1')),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where userorganizationsgroupsid in
(select uog.id from usersorganizations uo
inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
inner join userassessmentprogram uap on uap.userorganizationsgroupsid=uog.id
where uap.aartuserid =(select id from aartuser where username ='agekeler@rebeltec.net')
and uo.organizationid=(select distinct districtid from organizationtreedetail  where statename='Colorado-cPass' and districtname='KIT CARSON R 1'));


update usersorganizations  set activeflag=false,
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid=(select distinct districtid from organizationtreedetail  where statename='Colorado-cPass' and districtname='LAS ANIMAS RE 1') and
aartuserid=(select id from aartuser where username ='addie.wallace@la-schools.net');

update userorganizationsgroups set activeflag=false,
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where userorganizationid =(select uo.id from usersorganizations uo
inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
where uo.aartuserid=(select id from aartuser where username ='addie.wallace@la-schools.net') 
and uo.organizationid=(select distinct districtid from organizationtreedetail  where statename='Colorado-cPass' and districtname='LAS ANIMAS RE 1')
);

update userassessmentprogram set userorganizationsgroupsid =(select uog.id from usersorganizations uo
inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
inner join userassessmentprogram uap on uap.userorganizationsgroupsid=uog.id
where uap.aartuserid =(select id from aartuser where username ='addie.wallace@la-schools.net')
and uo.organizationid=(select distinct districtid from organizationtreedetail  where statename='Colorado' and districtname='LAS ANIMAS RE 1')),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where userorganizationsgroupsid in
(select uog.id from usersorganizations uo
inner join userorganizationsgroups uog on uog.userorganizationid = uo.id
inner join userassessmentprogram uap on uap.userorganizationsgroupsid=uog.id
where uap.aartuserid =(select id from aartuser where username ='addie.wallace@la-schools.net')
and uo.organizationid=(select distinct districtid from organizationtreedetail  where statename='Colorado-cPass' and districtname='LAS ANIMAS RE 1'));




---Users script for BTC,TEA

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='ARAPAHOE RIDGE HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='ARAPAHOE RIDGE HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='heather.ridge@bvsd.org');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='FORT MORGAN HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='FORT MORGAN HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='danica.farnik@morgan.k12.co.us');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='OTIS JUNIOR-SENIOR HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='OTIS JUNIOR-SENIOR HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='sinkerman_99@yahoo.com');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='SARGENT SENIOR HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='SARGENT SENIOR HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='cmondragon@sargent.k12.co.us');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='HAXTUN HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='HAXTUN HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='jeffplumb@yahoo.com');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='CEDAREDGE HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='CEDAREDGE HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='katie.greenwood@deltaschools.com');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='PLATEAU VALLEY HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='PLATEAU VALLEY HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='rshepardson@pvsd50.org');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='VALLEY HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='VALLEY HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='adamsr@weld-re1.k12.co.us');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='LAS ANIMAS HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='LAS ANIMAS HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='ryan.siefkas@la-schools.net');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='VALLEY HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='VALLEY HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='waterse@weld-re1.k12.co.us');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='ALAMOSA HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='ALAMOSA HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='krice@alamosa.k12.co.us');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='WINDSOR HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='WINDSOR HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='jarrod.bessire@weldre4.k12.co.us');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='FOWLER HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='FOWLER HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='brenten.ormiston@fowler.k12.co.us');



update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='VALLEY HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='VALLEY HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='waterse@weld-re1.k12.co.us');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='HI-PLAINS SCHOOL DISTRICT R-23'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='HI-PLAINS SCHOOL DISTRICT R-23') 
and aartuserid=(select id from aartuser where email='angied@hp-patriots.com');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='KIT CARSON JUNIOR-SENIOR HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='KIT CARSON JUNIOR-SENIOR HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='rgekeler@rebeltec.net');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='BURLINGTON HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='BURLINGTON HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='spoet@burlingtonk12.org');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='YUMA HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='YUMA HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='lebsockk@yumaschools.org');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='FRUITA MONUMENT HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='FRUITA MONUMENT HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='ryan.hudson@d51schools.org');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='WALSH HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='WALSH HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='d.carlson@walsheagles.com');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='ARICKAREE UNDIVIDED HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='ARICKAREE UNDIVIDED HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='bethr@arickaree.org');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='LEGEND HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='LEGEND HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='datawzer@dcsdk12.org');

update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='SOROCO HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='SOROCO HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='jwhaley@southrouttk12.org');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='PEETZ JUNIOR-SENIOR HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='PEETZ JUNIOR-SENIOR HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='forsterm@peetzschool.org');


update usersorganizations set organizationid=(
select distinct schoolid from organizationtreedetail where statename='Colorado' and schoolname='NEW VISTA HIGH SCHOOL'),
modifieddate=now(),
modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
where organizationid =(select distinct schoolid from organizationtreedetail where statename='Colorado-cPass' and schoolname='NEW VISTA HIGH SCHOOL') 
and aartuserid=(select id from aartuser where email='heather.riffel@bvsd.org');



--script for organizations

CREATE OR REPLACE FUNCTION public.retirecoloradocpass()
RETURNS void AS
$BODY$
declare
    organization_rec record;
    state_name record;
    neworgId record;
begin
   RAISE NOTICE 'begining';
   for organization_rec in select distinct districtid,districtname,districtdisplayidentifier,schoolname,schooldisplayidentifier from organizationtreedetail otd1 
   where otd1.statename='Colorado-cPass' and otd1.schoolname not in
   (select distinct otd2.schoolname from organizationtreedetail otd2 
    where otd2.statename='Colorado' and otd1.districtname=otd2.districtname) order by districtname asc
    loop 
       SELECT INTO neworgId (select distinct districtid from organizationtreedetail where districtname=organization_rec.districtname
        and districtdisplayidentifier=organization_rec.districtdisplayidentifier
        and statename='Colorado');
         RAISE NOTICE 'olddistrictid : % districtname : % newdistrictid: %', 
   organization_rec.districtid,organization_rec.districtname, neworgId.districtid;

  IF neworgId is not null 
    THEN
     update organizationrelation set parentorganizationid =
    (select distinct districtid from organizationtreedetail where statename='Colorado' 
    and districtname=organization_rec.districtname and districtdisplayidentifier=organization_rec.districtdisplayidentifier),
    modifieddate=now(),
    modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
    where parentorganizationid in(select distinct districtid from organizationtreedetail where statename='Colorado-cPass' 
    and districtname=organization_rec.districtname and districtdisplayidentifier=organization_rec.districtdisplayidentifier)
    and organizationid in(select id from organization where organizationname =organization_rec.schoolname 
    and displayidentifier=organization_rec.schooldisplayidentifier);
  else 
    update organizationrelation set parentorganizationid =
    (select distinct stateid from organizationtreedetail where statename='Colorado'),
    modifieddate=now(),
    modifieduser=(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
    where parentorganizationid in(select distinct stateid from organizationtreedetail where statename='Colorado-cPass')
    and organizationid in(select distinct districtid from organizationtreedetail where districtname=organization_rec.districtname
    and districtdisplayidentifier=organization_rec.districtdisplayidentifier
    and statename='Colorado-cPass');
        END IF;
    end loop;
	
    END;
    $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  select * from public.retirecoloradocpass();


--Deactivating the all the districts and schools with in the Colorado-cPass State
update organization set activeflag = false,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where id in (
select id from organization_children(
(select id from organization where organizationname ='Colorado-cPass' and organizationtypeid=
(select id from organizationtype where typecode ='ST')))
);


--Deactivating the Colorado-cPass State
update organization set activeflag = false,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where id in (
(select id from organization where organizationname ='Colorado-cPass' and organizationtypeid=
(select id from organizationtype where typecode ='ST'))
);


--Refresh organizationtreedetail
select * from refresh_organization_detail(); 

