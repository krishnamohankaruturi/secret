
-- 311.sql
update groups set groupname='State System Administrator', organizationtypeid=(select id from organizationtype where typecode='ST')
where groupname='System Administrator';

update groups set organizationtypeid=(select id from organizationtype where typecode='ST') where groupname='PD Admin';

INSERT INTO groups(organizationid, groupname, defaultrole, createddate, activeflag, 
            createduser, modifieduser, modifieddate, organizationtypeid)
    VALUES ((select id from organization where displayidentifier='CETE'), 'Global System Administrator', false, now(), true,
            (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), 
            now(), (select id from organizationtype where typecode='CONS'));
            
-- copy all permissions of State System Administrator to Global System Administrator    
DO 
$BODY$ 
DECLARE
    GRPPERMISSIONS RECORD;
    newrolegroupid bigint;
BEGIN
    newrolegroupid = (select id from groups where groupname='Global System Administrator');
    
    FOR GRPPERMISSIONS IN (
	select authorityid from groupauthorities where activeflag is true and groupid=(select id from groups where groupname='State System Administrator')) LOOP
        RAISE NOTICE  '%', GRPPERMISSIONS.authorityid;
        
	INSERT INTO groupauthorities(
		    groupid, authorityid, createddate, createduser, activeflag, 
		    modifieddate, modifieduser)
	    VALUES (newrolegroupid, GRPPERMISSIONS.authorityid, now(), (Select id from aartuser where username='cetesysadmin'), true, 
		    now(), (Select id from aartuser where username='cetesysadmin'));               
		                     
        RAISE NOTICE  'CREATED %', GRPPERMISSIONS.authorityid;
    END LOOP;
END;
$BODY$;

update userorganizationsgroups set groupid=(select id from groups where groupname='Global System Administrator') 
where userorganizationid=(select id from usersorganizations where aartuserid=(select id from aartuser where username='cetesysadmin')) 
and groupid=(select id from groups where groupname='State System Administrator');