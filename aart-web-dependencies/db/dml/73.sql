
--US13122 - remove the QC ADMIN role
DELETE FROM groupauthorities where groupid in (select id from groups where groupname='QC Admin'and 
 organizationid = (select id from organization where displayidentifier = 'CETE'));

DELETE FROM userorganizationsgroups where groupid = (select id from groups where groupname='QC Admin'and 
 organizationid = (select id from organization where displayidentifier = 'CETE'));

DELETE FROM groups where groupname = 'QC Admin' and 
 organizationid = (select id from organization where displayidentifier = 'CETE');
