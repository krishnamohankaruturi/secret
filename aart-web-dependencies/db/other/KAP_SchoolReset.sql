
--4286807533
update enrollment set attendanceschoolid = 3186,
modifieddate = now(),
modifieduser=12,
notes = 'update ATT school' 
where id = 2858621; 

update studentstests 
set activeflag = false,
modifieddate = now(),
modifieduser=12
where enrollmentid = 2858621 and activeflag is true;

--2428233688
update enrollment set attendanceschoolid = 3186,
modifieddate = now(),
modifieduser=12,
notes = 'update ATT school' 
where id = 2849075; 

update studentstests 
set activeflag = false,
modifieddate = now(),
modifieduser=12
where enrollmentid = 2849075 and activeflag is true;

--9127417409
update enrollment set attendanceschoolid = 3186,
modifieddate = now(),
modifieduser=12,
notes = 'update ATT school' 
where id = 2861928; 

update studentstests 
set activeflag = false,
modifieddate = now(),
modifieduser=12
where enrollmentid = 2861928 and activeflag is true;

--1184898898
update enrollment set attendanceschoolid = 3186,
modifieddate = now(),
modifieduser=12,
notes = 'update ATT school' 
where id = 2855260; 

update studentstests 
set activeflag = false,
modifieddate = now(),
modifieduser=12
where enrollmentid = 2855260 and activeflag is true;

--2258589592
update enrollment set attendanceschoolid = 3179,
modifieddate = now(),
modifieduser=12,
notes = 'update ATT school' 
where id = 2863848; 

update studentstests 
set activeflag = false,
modifieddate = now(),
modifieduser=12
where enrollmentid = 2863848 and activeflag is true;

update enrollment set activeflag = true,
modifieduser = 12,
modifieddate = now(),
notes = 'Activated manually since KIDS not processing TEST records due to entry/exit dates validation, kid is a shared student so school entry and exit dates cannot be modified'
where id = 2465011 and activeflag is false;

update enrollmenttesttypesubjectarea
set activeflag = true,
modifieddate = now(),
modifieduser = 12 
where enrollmentid = 2465011
and activeflag is false;

