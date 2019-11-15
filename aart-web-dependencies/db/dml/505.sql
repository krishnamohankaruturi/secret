--dml/505.sql 
-- Scoring State Lead
 
INSERT INTO groups(organizationid, groupname, defaultrole, createddate, activeflag, 
            createduser, modifieduser, modifieddate, organizationtypeid, 
            roleorgtypeid, groupcode)
    VALUES ((select id from organization where displayidentifier = 'CETE'), 'Scoring State Lead', FALSE, CURRENT_TIMESTAMP, TRUE, 
        (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), CURRENT_TIMESTAMP, 
        (select id from organizationtype where typecode = 'ST'), 
        (select id from organizationtype where typecode = 'ST'), 'SCOSL');
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring State Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TEST_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring State Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TESTSESSION_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring State Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TEST_SEARCH'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring State Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TESTSESSION_MONITOR'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring State Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_ROSTERRECORD_VIEWALL'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring State Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'HIGH_STAKES_TESTSESSION_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring State Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_USER_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring State Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_SCORING_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring State Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_SCORING_ASSIGNSCORER'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
-- Scoring Building Lead

INSERT INTO groups(organizationid, groupname, defaultrole, createddate, activeflag, 
            createduser, modifieduser, modifieddate, organizationtypeid, 
            roleorgtypeid, groupcode)
    VALUES ((select id from organization where displayidentifier = 'CETE'), 'Scoring Building Lead', FALSE, CURRENT_TIMESTAMP, TRUE, 
            (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), CURRENT_TIMESTAMP, 
            (select id from organizationtype where typecode = 'SCH'), 
            (select id from organizationtype where typecode = 'BLDG'), 'SCOBL');
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring Building Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TEST_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring Building Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TESTSESSION_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring Building Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TEST_SEARCH'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring Building Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TESTSESSION_MONITOR'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring Building Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_ROSTERRECORD_VIEWALL'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));

  INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring Building Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'HIGH_STAKES_TESTSESSION_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring Building Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_USER_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring Building Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_SCORING_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring Building Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_SCORING_ASSIGNSCORER'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
-- Scoring District Lead

INSERT INTO groups(organizationid, groupname, defaultrole, createddate, activeflag, 
            createduser, modifieduser, modifieddate, organizationtypeid, 
            roleorgtypeid, groupcode)
    VALUES ((select id from organization where displayidentifier = 'CETE'), 'Scoring District Lead', FALSE, CURRENT_TIMESTAMP, TRUE, 
            (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), CURRENT_TIMESTAMP, 
            (select id from organizationtype where typecode = 'DT'), 
            (select id from organizationtype where typecode = 'DT'), 'SCODL');
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring District Lead' and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TEST_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin')); 
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring District Lead' and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TESTSESSION_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring District Lead' and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TEST_SEARCH'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring District Lead'and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_TESTSESSION_MONITOR'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring District Lead' and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_ROSTERRECORD_VIEWALL'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring District Lead' and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'HIGH_STAKES_TESTSESSION_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring District Lead' and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_USER_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring District Lead' and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_SCORING_VIEW'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));

 INSERT INTO groupauthorities (groupid,authorityid,createduser,activeflag,modifieduser)
 VALUES ((select id from groups where groupname='Scoring District Lead' and 
 organizationid = (select id from organization where displayidentifier = 'CETE')), 
 (select id from authorities where authority = 'PERM_SCORING_ASSIGNSCORER'),    
 (Select id from aartuser where username = 'cetesysadmin'),true,
 (Select id from aartuser where username = 'cetesysadmin'));
 
 --Script from changepond
 INSERT INTO taskvariantessentialelementlinkage(taskvariantid, essentialelementlinkageid)
(SELECT id,essentialelementlinkageid from taskvariant where essentialelementlinkageid IS NOT NULL);


 