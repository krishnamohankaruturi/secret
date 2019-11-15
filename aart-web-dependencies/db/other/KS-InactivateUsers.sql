-- Go through the script and run parts of the script as necessary 
ALTER TABLE aartuser ADD COLUMN ksinactivateduser boolean;
ALTER TABLE aartuser ALTER COLUMN ksinactivateduser SET DEFAULT false;
 

   SELECT count(distinct au.id)
   FROM 
    aartuser au join usersorganizations as uo on au.id = uo.aartuserid 
   WHERE au.activeflag is true    
	and uo.organizationid = ANY(select id from organization_children(51) union select 51) 
	and au.id not in (select id from aartuser where email ilike '%ku.edu%' and activeflag is true);

update aartuser set activeflag = false, ksinactivateduser = true
where id in (	SELECT distinct au.id as id
	   FROM 
	    aartuser au join usersorganizations as uo on au.id = uo.aartuserid 
	   WHERE au.activeflag is true    
		and uo.organizationid = ANY(select id from organization_children(51) union select 51) 
		and au.id not in (select id from aartuser where email ilike '%ku.edu%' and activeflag is true)
	);	

--29752
select count(*) from aartuser where activeflag is false and ksinactivateduser is true;