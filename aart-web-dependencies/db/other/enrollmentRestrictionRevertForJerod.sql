begin;

--Colorado – COLO
--West Colorado District - WCOLODIST
--West Colorado Highschool – WCHS

--note that no authority is added to the restriction.

--Select * from organization where displayidentifier in ('COLO','WCOLODIST','WCHS');

--'PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_SEARCH' to parent.
insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,true as isparent,false as ischild,false as isdifferential
 from restriction,authorities where authorities.authority in ('PERM_ENRL_UPLOAD')
 and restriction.id=(Select id from restriction where restrictioncode = 'TOP_DOWN_ENROLLMENTS'));

update restrictionsorganizations
 set restrictionid = (select id from restriction where
 restrictioncode='TOP_DOWN_ENROLLMENTS')
  where
   organizationid in (
	SELECT id from organization
	where
	organizationtypeid in (select id from organizationtype where typecode in ('SCH','RG')) and
	id in (Select id from organization_children(
	(Select id from organization where displayidentifier='COLO')
	)) 
   ) and
   restrictionid
   = (Select id from restriction where restrictioncode = 'TOP_DOWN_ENROLLMENT_JEROD');

commit;