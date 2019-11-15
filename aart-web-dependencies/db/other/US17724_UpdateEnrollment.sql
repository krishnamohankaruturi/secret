--US17724 : PROD - Request to have student enrollments corrected that cannot be corrected with KSDE uploads

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '0854' and s.statestudentidentifier = '4660567788') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '2787' and s.statestudentidentifier = '4189262854') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3794' and s.statestudentidentifier = '7337625146') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '5413' and s.statestudentidentifier = '7580307308') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '5413' and s.statestudentidentifier = '2759720179') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '4856' and s.statestudentidentifier = '7832118946') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '5636' and s.statestudentidentifier = '8039858089') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3002' and s.statestudentidentifier = '9831537955') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3027' and s.statestudentidentifier = '5454034592') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3027' and s.statestudentidentifier = '9047723848') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3018' and s.statestudentidentifier = '3590583738') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3000' and s.statestudentidentifier = '8415088191') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3014' and s.statestudentidentifier = '5831466574') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3022' and s.statestudentidentifier = '9098665292') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3022' and s.statestudentidentifier = '2474147535') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3022' and s.statestudentidentifier = '4598547006') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3022' and s.statestudentidentifier = '9208702804') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '3168' and s.statestudentidentifier = '8489847886') and activeflag is false;
 
update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '1746' and s.statestudentidentifier = '4323968248') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '1746' and s.statestudentidentifier = '8273556743') and activeflag is false; 

update enrollment set activeflag = true, exitwithdrawaldate = null, exitwithdrawaltype = null, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), notes = 'Activated enrollment as per US17724' where id = ( select e.id   from enrollment e inner join student s on e.studentid = s.id inner join organization o on e.attendanceschoolid = o.id where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))  and s.stateid = (select id from organization where displayidentifier = 'KS') and o.displayidentifier = '8064' and s.statestudentidentifier = '6380439811') and activeflag is false; 
