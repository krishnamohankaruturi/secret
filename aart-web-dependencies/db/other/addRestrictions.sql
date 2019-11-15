--this will add the generic restrictions for new organizations that are uploaded.
insert into restrictionsorganizations(restrictionid,organizationid,isenforced)
(Select restriction.id as restrictionid,organization.id as organizationid,true from organization,restriction
where restrictioncode in ('TOP_DOWN_ROSTERS','TOP_DOWN_ENROLLMENTS') and 
 (restriction.id,organization.id) not in (select restrictionid,organizationid from restrictionsorganizations));