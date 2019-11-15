
--US14611
 INSERT INTO groups (organizationid, groupname, defaultrole, createduser,modifieduser, organizationtypeid)
 (select id,'Scorer' as groupname, false as defaulrole,
 (Select id from aartuser where username = 'cetesysadmin') as createduser,
 (Select id from aartuser where username = 'cetesysadmin') as modifieduser,
 (Select id from organizationtype where typecode='BLDG') as organizationtypeid
 from organization where displayidentifier = 'CETE' );
 
 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scorer'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'TEST_COORD_TESTSESSION_SCORING'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scorer'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'HIGH_STAKES_TESTSESSION_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
  INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scorer'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TEST_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
  INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scorer'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TEST_SEARCH'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
  INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scorer'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_STUDENTRECORD_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
  INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scorer'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_STUDENTRECORD_SEARCH'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 