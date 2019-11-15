
--get all the display identifiers less than 4 characters.
Select * from organization where length(displayidentifier) < 4;

--if we remove zeros will there be duplicates ?

Select count(1) as duplicate_count ,
trim(leading '0' from o.displayidentifier) as trimmed_display_identifier
 from organization o
  where length(o.displayidentifier) < 4
   group by trim(leading '0' from o.displayidentifier) having count(1) > 1;

--get the organization and parent organization for those.

Select org.*,parentOrg.id as parent_org_id,parentOrg.organizationname as parent_organization_name,
parentOrg.displayidentifier as parent_displayidentifier,parentorg.organizationtypeid
 from organization org,organizationrelation orgrel,Organization parentOrg where 
 org.id=orgrel.organizationid and orgrel.parentorganizationid= parentorg.id and
trim(leading '0' from org.displayidentifier) in 
(
Select
trim(leading '0' from o.displayidentifier) as trimmed_display_identifier
 from organization o
  where length(o.displayidentifier) < 4
   group by trim(leading '0' from o.displayidentifier) having count(1) > 1
) order by org.displayidentifier;

--Select * from organizationrelation where organizationid=5051;

--if we add 0 es will we create duplicates ?
 Select o.*,lpad(displayidentifier,4,'0') as padded_display_identifier
 from organization o where length(o.displayidentifier) < 4;

--if we add zeroes will there be duplicates.
 Select count(1) as duplicate_count ,
lpad(o.displayidentifier,4,'0') as padded_display_identifier
 from organization o
  where length(o.displayidentifier) < 4
   group by lpad(o.displayidentifier,4,'0') having count(1) > 1;

--find the orgs and the parent orgs for those.

Select org.*,parentOrg.id as parent_org_id,parentOrg.organizationname as parent_organization_name,
parentOrg.displayidentifier as parent_displayidentifier,parentorg.organizationtypeid
 from organization org,organizationrelation orgrel,Organization parentOrg where 
 org.id=orgrel.organizationid and orgrel.parentorganizationid= parentorg.id and
lpad(org.displayidentifier,4,'0') in 
(
 Select 
lpad(o.displayidentifier,4,'0') as padded_display_identifier
 from organization o
  where length(o.displayidentifier) < 4
   group by lpad(o.displayidentifier,4,'0') having count(1) > 1
) order by org.displayidentifier;

--

--Select id from organization where displayidentifier='CETE'

   Select org.*,lpad(org.displayidentifier,4,'0') as padded_display_identifier
 from organization org where 
lpad(org.displayidentifier,4,'0') in 
(
 Select 
lpad(o.displayidentifier,4,'0') as padded_display_identifier
 from organization o
  where length(o.displayidentifier) < 4
  and o.id in (Select id from organization_children( 84) )
   group by lpad(o.displayidentifier,4,'0') having count(1) > 1
) order by lpad(org.displayidentifier,4,'0');

begin;

update organization set displayidentifier = lpad(displayidentifier,4,'0')
where id in (Select id from organization_children( <<Id of the right kansas>>) )
and length(displayidentifier) < 4;

end;

update organization set displayidentifier = lpad(displayidentifier,4,'0')
where id in (Select id from organization_children( 84) )
and length(displayidentifier) < 4;

Select * from organization;
