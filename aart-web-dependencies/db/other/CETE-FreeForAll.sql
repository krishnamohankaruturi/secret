--insert the mother of all organizations.
INSERT INTO organization(
            organizationname, displayidentifier, organizationtypeid)
    VALUES ('CETE Organization', 'CETE', 1);

--these are the organizations that do not have a parent
--Select * from organization where id not in (select organizationid from organizationrelation);

--insert CETE to be the mother of all organizations.
insert into organizationrelation(
Select id as organizationid,
(select id from organization where displayidentifier='CETE') as parentorganizationid
from organization where id not in (select organizationid from organizationrelation) 
);

delete from organizationrelation where organizationid=parentorganizationid;
---delete from restrictions organizations

delete from restrictionsorganizations;

insert into restrictionsorganizations(restrictionid,organizationid,isenforced)
(Select restriction.id as restrictionid,organization.id as organizationid,true from organization,restriction
where displayidentifier='CETE' and (restriction.id,organization.id) not in (select restrictionid,organizationid from restrictionsorganizations));

--Select * from organizationrelation where organizationid=52

--select * from organization_parent(52)

--select * from organization_parent(3080)
