-- delete various orgs
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where displayidentifier in (
  '21965',
  '21030',
  '27635',
  '20020',
  '28995',
  '23035',
  '28105',
  '22775',
  '29045',
  '21120',
  '22270'
)
and id in (
  select id from organization_children((select id from organization where displayidentifier = 'NH'))
);

-- change name of Concord Area Special Education (Case Collaborative
update organization
set organizationname = 'Concord Area Special Education CASE Collaborative',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69023;

-- change name of Greater Lawrence Ed. Collab-Alternative High schoo
update organization
set organizationname = 'Greater Lawrence Ed. Collab-Alternative High school',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69020;

-- change name of Greater Lawrence Ed. Collab-Alternative High schoo
update organization
set organizationname = 'Greater Lawrence Ed. Collab-Alternative High school',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69020;

-- change name of John Perkins Elementary School
update organization
set organizationname = 'John D. Perkins Elementary School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 59698;

-- change name of May Institute, Inc.- Chatham
update organization
set organizationname = 'May Institute',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69001;

update organization
set organizationname = 'NFI North - DavenportSchool',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 63779;

update organization
set organizationname = 'NFI North - Midway Shelter',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 63817;

update organization
set organizationname = 'North Country Independent Living',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69012;

update organization
set organizationname = 'Springfield School District',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69006;

update organization
set organizationname = 'Tenny School - Greater Lawrence Educational Collaborative',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 68991;

update organization
set organizationname = 'The Birches Academy of Academics - Art A Public Charter Sch',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 60012;

update organization
set organizationname = 'The Community School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 68854;

update organization
set organizationname = 'Windsor High School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 69011;

update organization
set organizationname = 'World Academy and Child Dev Ctr',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 68851;
