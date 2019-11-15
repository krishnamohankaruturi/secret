begin;

--Colorado – COLO
--West Colorado District - WCOLODIST
--West Colorado Highschool – WCHS

--note that no authority is added to the restriction.

--Select * from organization where displayidentifier in ('COLO','WCOLODIST','WCHS');

--'PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_SEARCH' to parent.
insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,true as isparent,false as ischild,false as isdifferential
 from restriction,authorities where authorities.authority in ('PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_SEARCH') and restriction.id=1);

update restrictionsorganizations
 set restrictionid = (select id from restriction where
 restrictioncode='TOP_DOWN_ROSTERS')
  where
   organizationid in (
   Select id from organization where displayidentifier in ('COLO','WCOLODIST','WCHS')
   );

commit;