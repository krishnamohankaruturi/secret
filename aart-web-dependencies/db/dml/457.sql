 -- 457.sql

--Give all permissions of Teacher to Teacher: PNP Read Only except EDIT/Create PNP permissions
DO
$BODY$ 
DECLARE
	AUTHORITIES RECORD;
	groupTeacherPNPId BIGINT;
        cetesysadminid BIGINT;
BEGIN           
	SELECT INTO cetesysadminid (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
	SELECT INTO groupTeacherPNPId (select id from groups where groupname = 'Teacher: PNP Read Only');
	
	delete from groupauthorities where groupid = groupTeacherPNPId;
	
	FOR AUTHORITIES IN                
		select distinct authorityid from groupauthorities where activeflag is true 
			and groupid = (select id from groups where groupname='Teacher') 
			and authorityid not in (select id from authorities where authority in ('CREATE_STUDENT_PNP','EDIT_STUDENT_PNP'))
	LOOP 
                INSERT INTO groupauthorities(groupid,authorityid,createduser,activeflag,modifieduser) VALUES (
                groupTeacherPNPId, AUTHORITIES.authorityid, cetesysadminid, true, cetesysadminid);
	END LOOP;
END;
$BODY$;

