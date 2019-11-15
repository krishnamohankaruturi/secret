--free for all setting on roster.
 insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(
Select restriction.id,authorities.id,true as isparent,false as ischild,false as isdifferential
 from restriction,authorities where restriction.restrictioncode = 'TOP_DOWN_ROSTERS'
  and authorities.authority in ('PERM_ROSTERRECORD_VIEW',
 'PERM_ROSTERRECORD_MODIFY','PERM_ROSTERRECORD_CREATE','PERM_ROSTERRECORD_DELETE',
 'PERM_ROSTERRECORD_SEARCH','PERM_ROSTERRECORD_ARCHIVE','PERM_ROSTERRECORD_UPLOAD')
 except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities)
 );

 insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,false as isparent,true as ischild,false as isdifferential
 from restriction,authorities where restriction.restrictioncode = 'TOP_DOWN_ROSTERS'
 and authorities.authority in ('PERM_ROSTERRECORD_VIEW',
 'PERM_ROSTERRECORD_MODIFY','PERM_ROSTERRECORD_CREATE','PERM_ROSTERRECORD_DELETE',
 'PERM_ROSTERRECORD_SEARCH','PERM_ROSTERRECORD_ARCHIVE','PERM_ROSTERRECORD_UPLOAD')
  except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities)
 );

--free for all setting on enrollment

--'PERM_ENROLLMENTRECORD_VIEW','PERM_ENROLLMENTRECORD_SEARCH' to parent.
insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,true as isparent,false as ischild,false as isdifferential
 from restriction,authorities where restriction.restrictioncode = 'TOP_DOWN_ENROLLMENTS'
  and authorities.authority in ('PERM_ENRL_UPLOAD')
 except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities)
  );

--'PERM_ENROLLMENTRECORD_VIEW','PERM_ENROLLMENTRECORD_MODIFY','PERM_ENROLLMENTRECORD_CREATE',
--'PERM_ENROLLMENTRECORD_DELETE','PERM_ENROLLMENTRECORD_SEARCH','PERM_ENROLLMENTRECORD_ARCHIVE',
--'PERM_ENROLLMENTRECORD_UPLOAD' to current org/ownership org.

insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,false as isparent,true as ischild,false as isdifferential
 from restriction,authorities where restriction.restrictioncode = 'TOP_DOWN_ENROLLMENTS'
  and authorities.authority in ('PERM_ENRL_UPLOAD')
 except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities) 
  );