--dml/504.sql 

INSERT INTO authorities(authority,displayname,objecttype,createduser,createddate,modifieduser, modifieddate) values
	('VIEW_DAILY_ACCESS_CODES','View Daily Access Codes','Test Management-Daily Access Codes', (Select id from aartuser where username='cetesysadmin'),now(), (Select id from aartuser where username='cetesysadmin'), now());
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Building Test Coordinator'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'VIEW_DAILY_ACCESS_CODES'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='State System Administrator'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'VIEW_DAILY_ACCESS_CODES'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='District Test Coordinator'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'VIEW_DAILY_ACCESS_CODES'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
  INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='State Assessment Administrator'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'VIEW_DAILY_ACCESS_CODES'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
 INSERT INTO categorytype(typename, typecode,modifieduser)
    VALUES  ('Special Circumstance Status', 'SPECIAL CIRCUMSTANCE STATUS', (Select id from aartuser where username='cetesysadmin'));

INSERT INTO category(categoryname, categorycode, categorytypeid)
    VALUES ('Saved', 'SAVED',(select id from categorytype where typecode= 'SPECIAL CIRCUMSTANCE STATUS'));

INSERT INTO category(categoryname, categorycode, categorytypeid)
    VALUES ('Approved','APPROVED',(select id from categorytype where typecode= 'SPECIAL CIRCUMSTANCE STATUS'));

INSERT INTO category(categoryname, categorycode, categorytypeid)
    VALUES ('Not Approved', 'NOT_APPROVED',(select id from categorytype where typecode= 'SPECIAL CIRCUMSTANCE STATUS'));
    
INSERT INTO category(categoryname, categorycode, categorytypeid)
    VALUES ('Pending', 'PENDING',(select id from categorytype where typecode= 'SPECIAL CIRCUMSTANCE STATUS'));

INSERT INTO category(categoryname, categorycode, categorytypeid)
    VALUES ('Pending Further Review', 'PENDING_FURTHER_REVIEW',(select id from categorytype where typecode= 'SPECIAL CIRCUMSTANCE STATUS'));

-- updating existing records with saved status
update studentspecialcircumstance set status = (
select id from category where categorycode ='SAVED' and categorytypeid =
	(select id from categorytype where typecode ='SPECIAL CIRCUMSTANCE STATUS')
);

-- populating existing records with 
update studentspecialcircumstance set createduser = (select id from aartuser where username='cetesysadmin') where createduser is null;
update studentspecialcircumstance set modifieduser  = (select id from aartuser where username='cetesysadmin') where modifieduser is null;

update studentspecialcircumstance set approvedby = (select id from aartuser where username='cetesysadmin');