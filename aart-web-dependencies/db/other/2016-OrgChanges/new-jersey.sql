-- delete Central Jersey Arts Charter School
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 55923;

-- delete Galloway Community Charter School
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 55926;

-- delete Woodcliff Academy
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 53447;

-- change name and identifier of Windsor Academy district
update organization
set organizationname = 'WINDSOR BERGEN ACADEMY',
displayidentifier = '048319',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 53135;

-- change name of Washington Township (155500)
update organization
set organizationname = 'WASHINGTON TOWNSHIP - Gloucester County',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 52877;

-- change name of Washington Township (275520)
update organization
set organizationname = 'WASHINGTON TOWNSHIP - Morris County',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 53068;

-- change name of Washington Township (415530)
update organization
set organizationname = 'WASHINGTON TOWNSHIP - Warren County',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 53264;

-- change name of Washington Township (055490)
update organization
set organizationname = 'WASHINGTON TOWNSHIP - Burlington County',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 52737;

-- change name and identifier of Windsor Academy school
update organization
set organizationname = 'WINDSOR BERGEN ACADEMY',
displayidentifier = '048319001',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 53471;

-- change name of Children's Home - Mary A. Dobbins School
update organization
set organizationname = 'LEGACY TREATMENT SERVICES - MARY A. DOBBINS SCHOOL',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 53378;

update organization
set organizationname = 'HOWELL TWP MEMORIAL ELEMENTARY SCH',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 54046;

update organization
set organizationname = 'LAND O''PINES ELEMENTARY SCHOOL',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 53828;

update organization
set organizationname = 'Newbury Elementary School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 31215;

update organization
set organizationname = 'Taunton Elementary School',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 31837;
