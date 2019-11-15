-- DML statements - F61
--Manage Org Permission--
INSERT INTO authorities(
            authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_ORG_MANAGE', 'Manage Organizations', 'Administrative-Organization', now(), (select id from aartuser where username like 'cetesysadmin'), 
            true, now(),(select id from aartuser where username like 'cetesysadmin'));


--populate schoolname and district name for old records--
--update studentreport sr set schoolname = (select organizationname from organization where id = sr.attendanceschoolid), districtname = (select organizationname from organization where id = sr.districtid);

--populate schoolname and district name for old records--
update externalstudentreports sr set schoolname = (select organizationname from organization where id = sr.schoolid), districtname = (select organizationname from organization where id = sr.districtid); 

--populate expiry password as true for all contracting organization--
update organization set expirepasswords = true where contractingorganization is true;

--Correcting the name--
update category set categoryname  = 'Multi EE - EOC',categorydescription = 'Multi EE - End of Course' where categorycode = 'MEOC';
update category set categoryname  = 'Multi EE - EOG',categorydescription = 'Multi EE - End of Grade' where categorycode = 'MEOG';

-- Seven external roles will eventually be retired from (i.e., hidden in) Educator Portal for new/edit user
-- 1. Scoring District Lead 
-- 2. Building Principal 
-- 3. Scorer
-- 4. Scoring Building Lead
-- 5. State Scorer
-- 6. Teacher: PNP Read Only
-- 7. Technology Director

UPDATE groups SET isdepreciated = true,modifieduser = (select Id from aartuser where username ='cetesysadmin'), 
modifieddate= now() WHERE groupcode in ('SCODL','PRN','SCO','SCOBL','SSCO','TEAR','TD');

select * from DFMInsert(44,'Organization_Records_Extract_Data_Dictionary.pdf','datadictionaries',null,null,'cetesysadmin',null);
UPDATE datadictionaryfilemapping SET activeflag = false WHERE extracttypeid = 44;
