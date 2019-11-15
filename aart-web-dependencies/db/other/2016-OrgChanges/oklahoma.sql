-- "Silo Jhs" -> "Silo Ms", school number 610 -> 515
update organization
set organizationname = 'Silo MS',
displayidentifier = '07I001515',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 34142;

-- "Lincoln ES" -> "Lincoln Learning Ctr" - name change
update organization
set organizationname = 'Lincoln Learning Ctr',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 33627;

-- "Leslie F Roblyer Ms" -> "Leslie F Roblyer Learning Ctr" - name change
update organization
set organizationname = 'Leslie F Roblyer Learning Ctr',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 33606;

-- closed schools:
-- Webster Es
-- Brockland Es
-- Geronimo Road Es
-- Park Lane Es
-- Sheridan Road Es
-- Swinney Es
-- Harrah Jhs
-- Nichols Es
-- Harrison Es
-- Sunnyside Es
-- Harmony Es
-- Deep Rock Es
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id in (34388, 32895, 33310, 33915, 34136, 34231, 33377, 33813, 33380, 34223, 33374, 33131);

-- "Wilson ES" -> "Collinsville 6th Grade Center", school number 215 -> 135
update organization
set organizationname = 'Collinsville 6th Grade Center',
displayidentifier = '72I006135',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 34456;

-- "Ninnekah Jhs" -> "Ninnekah Ms", school number 610 -> 510
update organization
set organizationname = 'Ninnekah MS',
displayidentifier = '26I051510',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 33819;

-- "Haworth Jhs" -> "Haworth Ms", school number 610 -> 510
update organization
set organizationname = 'Haworth MS',
displayidentifier = '48I006510',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 33390;

-- close Harris Jobe Es
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 33379;

-- "Parkview Oklahoma School for the Blind ES" -> "Oklahoma School for the Blind ES" - name change
update organization
set organizationname = 'Oklahoma School for the Blind ES',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 63751;

-- "Parkview Oklahoma School for the Blind HS" -> "Oklahoma School for the Blind HS" - name change
update organization
set organizationname = 'Oklahoma School for the Blind HS',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 63752;

-- "ABLE Charter Middle" -> "ABLE Charter School 6th - 8th" - name change
update organization
set organizationname = 'ABLE Charter School 6th - 8th',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 68466;

-- "ABLE Charter High" -> "ABLE Charter School 9th - 12th" - name change
update organization
set organizationname = 'ABLE Charter School 9th - 12th',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 68467;

-- delete "Alexis Rainbow (Charter)" and "Alexis Rainbow Cs"
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id in (52487, 52506);

-- "Wilson School" -> "Cushing Pre-Kindergarten Schl"
update organization
set organizationname = 'Cushing Pre-Kindergarten Schl',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 34463;

-- "Yale Jhs" -> "Yale MS", school number 615 -> 505
update organization
set organizationname = 'Yale MS',
displayidentifier = '60I103505',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 34494;

-- "Hartshorne Jhs" -> "Hartshorne MS", school number 610 -> 510
update organization
set organizationname = 'Hartshorne MS',
displayidentifier = '61I001510',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 33383;

-- close Juvenile Ctr
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 33532;

-- update Herald Es school number 120 -> 125
update organization
set displayidentifier = '72I006125',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 33412;

-- close Bartlesville Mhs
update organization
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 32811;

-- update Singleton ES identifier
update organization
set displayidentifier = '40I091205',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 34143;

-- update Astec Charter MS identifier
update organization
set displayidentifier = '55G004971',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 52510;

-- update Astec Charter HS identifier
update organization
set displayidentifier = '55G004972',
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where id = 52509;

-- move the above 2 Astec schools to the Astec Charter district
update organizationrelation
set parentorganizationid = 79259,
modifieddate = now(),
modifieduser = (select id from aartuser where username = 'cetesysadmin')
where organizationid in (52509, 52510);
