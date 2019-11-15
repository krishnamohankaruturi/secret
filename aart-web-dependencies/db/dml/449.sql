--/dml/449.sql
--Reports - Reports UI - enhance control over whether report is listed
--Update to AMP permissions 
INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
	('LOCK_AMP_REPORTS','Lock AMP Reports','Reports-Performance Reports', 
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
--Lock AMP Reports
DO
$BODY$ 
DECLARE
	GROUPRECORDS RECORD;
	lockAMPAuthorityId BIGINT;
        cetesysadminid BIGINT;
BEGIN           
	SELECT INTO cetesysadminid (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
	SELECT INTO lockAMPAuthorityId (select id from authorities where authority = 'LOCK_AMP_REPORTS');
	
	FOR GROUPRECORDS IN                
		select id from groups where activeflag is true
	LOOP 
                INSERT INTO groupauthorities(groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
                GROUPRECORDS.id, lockAMPAuthorityId, cetesysadminid, true, cetesysadminid);
	END LOOP;
END;
$BODY$;	