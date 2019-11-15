--all sqls in this script must be idempotent.

--free for all setting on roster.
--all for parents
 insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(
Select restriction.id,authorities.id,true as isparent,false as ischild,false as isdifferential
 from restriction,authorities where restriction.restrictioncode = 'TOP_DOWN_ROSTERS'
  and authorities.authority in ('PERM_ROSTERRECORD_VIEW',
 'PERM_ROSTERRECORD_MODIFY','PERM_ROSTERRECORD_CREATE','PERM_ROSTERRECORD_DELETE',
 'PERM_ROSTERRECORD_SEARCH','PERM_ROSTERRECORD_ARCHIVE','PERM_ROSTERRECORD_UPLOAD')
 except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities)
 );
--all for children
 insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,false as isparent,true as ischild,false as isdifferential
 from restriction,authorities where restriction.restrictioncode = 'TOP_DOWN_ROSTERS'
 and authorities.authority in ('PERM_ROSTERRECORD_VIEW',
 'PERM_ROSTERRECORD_MODIFY','PERM_ROSTERRECORD_CREATE','PERM_ROSTERRECORD_DELETE',
 'PERM_ROSTERRECORD_SEARCH','PERM_ROSTERRECORD_ARCHIVE','PERM_ROSTERRECORD_UPLOAD')
  except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities)
 );
--all for current
 insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,false as isparent,false as ischild,false as isdifferential
 from restriction,authorities where restriction.restrictioncode = 'TOP_DOWN_ROSTERS'
 and authorities.authority in ('PERM_ROSTERRECORD_VIEW',
 'PERM_ROSTERRECORD_MODIFY','PERM_ROSTERRECORD_CREATE','PERM_ROSTERRECORD_DELETE',
 'PERM_ROSTERRECORD_SEARCH','PERM_ROSTERRECORD_ARCHIVE','PERM_ROSTERRECORD_UPLOAD')
  except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities)
 );
 
 ---the differential permission
 
insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,false as isparent,false as ischild,true as isdifferential
 from restriction,authorities where  restriction.restrictioncode = 'TOP_DOWN_ROSTERS'
 and authorities.authority in ('PERM_ROSTERRECORD_VIEWALL')
except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities) 
 );

--free for all setting on enrollment

--parent.
insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,true as isparent,false as ischild,false as isdifferential
 from restriction,authorities where restriction.restrictioncode = 'TOP_DOWN_ENROLLMENTS'
  and authorities.authority in ('PERM_ENRL_UPLOAD')
 except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities)
  );

--child.
insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,false as isparent,true as ischild,false as isdifferential
 from restriction,authorities where restriction.restrictioncode = 'TOP_DOWN_ENROLLMENTS'
  and authorities.authority in ('PERM_ENRL_UPLOAD')
 except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities) 
  );

--current
insert into restrictionsauthorities(restrictionid,authorityid,isparent,ischild,isdifferential)
(Select restriction.id,authorities.id,false as isparent,false as ischild,false as isdifferential
 from restriction,authorities where restriction.restrictioncode = 'TOP_DOWN_ENROLLMENTS'
  and authorities.authority in ('PERM_ENRL_UPLOAD')
 except (select restrictionid,authorityid,isparent,ischild,isdifferential from restrictionsauthorities) 
  );  
  
  --insert the mother of all organizations.
INSERT INTO organization(
            organizationname, displayidentifier, organizationtypeid)
    (Select 'CETE Organization', 'CETE', 1 where not exists
    	(Select 1 from organization where displayidentifier='CETE')
    );

--these are the organizations that do not have a parent
--Select * from organization where id not in (select organizationid from organizationrelation);

--insert CETE to be the mother of all organizations.
insert into organizationrelation(
Select id as organizationid,
(select id from organization where displayidentifier='CETE') as parentorganizationid
from organization where id not in (select organizationid from organizationrelation)
and displayidentifier <> 'CETE'
);

--insert this restriction for CETE alone.
insert into restrictionsorganizations(restrictionid,organizationid,isenforced)
(Select restriction.id as restrictionid,organization.id as organizationid,true from organization,restriction
where displayidentifier='CETE' and (restriction.id,organization.id)
not in (select restrictionid,organizationid from restrictionsorganizations));

--create the CETESysAdmin
INSERT INTO aartuser (username, firstname, middlename, surname, password, email,
uniquecommonidentifier, defaultusergroupsid, ukey)  
(Select 'CETESysAdmin' as username, 'CETE' as firstname, null as middlename,
'SysAdmin' as surname, password, 'cete@ku.edu' as email,
'12345678' as uniquecommonidentifier, defaultusergroupsid,
ukey from aartuser where username='ColoradoSysAdmin' and
 not exists (select 1 from aartuser where username='CETESysAdmin') );

--create the role
 
 INSERT INTO groups (organizationid, groupname, defaultrole)
 (
 select id,'System Administrator' as groupname, false as defaulrole
 from organization where displayidentifier = 'CETE' and
 not exists (Select 1 from groups g, organization o where g.organizationid=o.id and o.displayidentifier='CETE')
 );

 --give him all the permissions.
INSERT INTO groupauthorities(
            groupid, authorityid)
(Select g.id as groupid,a.id as authorityid
from authorities a,groups g, organization o
where g.organizationid=o.id and o.displayidentifier='CETE'
except (Select groupid, authorityid from groupauthorities)
); 

INSERT INTO usergroups(
            aartuserid, groupid)
    (Select id as aartuserid,
    (Select g.id from groups g, organization o where g.organizationid=o.id and o.displayidentifier='CETE') groupid
    from aartuser where username='CETESysAdmin'
    except (Select aartuserid, groupid from usergroups)
    );
    
delete from orgassessmentprogram where organizationid in (Select id from organization where displayidentifier='CETE');

update fieldspecification set formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' where fieldname = 'organizationName';
update fieldspecification set formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' where fieldname = 'firstName';
update fieldspecification set formatregex = E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$' where fieldname = 'lastName';

update fieldspecification set fieldlength = 10 where fieldname in ( 'attendanceSchoolProgramIdentifier','aypSchoolIdentifier',
'fundingSchool','schoolIdentifier') and fieldlength <> 10;

--fix earlier data.
update aartuser set email = 'cete@ku.edu' where username = 'CETESysAdmin';

--fix the data on creating teachers during roster upload.

delete from enrollmentsrosters
 where rosterid in (Select r.id from roster r,aartuser au
  where r.teacherid = au.id and au.username=au.email
   and au.username = au.uniquecommonidentifier
    and au.defaultusergroupsid = 0); 

delete from roster
 where teacherid in (Select id from aartuser
  where username=email and username = uniquecommonidentifier
   and defaultusergroupsid = 0); 

delete from usergroups
 where aartuserid in (Select id
  from aartuser where username=email and
   username = uniquecommonidentifier and defaultusergroupsid = 0);

delete from aartuser where username=email and
 username = uniquecommonidentifier and defaultusergroupsid = 0;