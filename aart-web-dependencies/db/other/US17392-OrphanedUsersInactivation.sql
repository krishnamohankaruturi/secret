-- US17392 - Orphaned users inactivation script

select distinct au.* from aartuser au 
 join usersorganizations uso on uso.aartuserid = au.id
 join organization org on org.id = uso.organizationid
 where au.activeflag is true and (select count(*) from usersorganizations where aartuserid = au.id) 
 								= (select count(*) from organization where id in (select organizationid 
 										from usersorganizations where aartuserid = au.id) and activeflag is false)
 order by  au.surname,au.firstname, au.username;
 
DO
$BODY$
DECLARE
aartuserdetailsRec record;
recordsUpdated integer;
BEGIN
    recordsUpdated := 0;
    FOR aartuserdetailsRec IN (select distinct au.* from aartuser au join usersorganizations uso on uso.aartuserid = au.id join organization org on org.id = uso.organizationid
                where au.activeflag is true and (select count(*) from usersorganizations where aartuserid = au.id) 
			= (select count(*) from organization where id in (select organizationid from usersorganizations where aartuserid = au.id) and activeflag is false)) 

	LOOP
		update aartuser set modifieduser=12, modifieddate=now(), username = aartuserdetailsRec.username || '-orphan', email = aartuserdetailsRec.email || '-orphan', activeflag = false where id = aartuserdetailsRec.id;
		recordsUpdated := recordsUpdated + 1;
	END LOOP;
   RAISE NOTICE 'Total records updated: %', recordsUpdated;
END;
$BODY$;