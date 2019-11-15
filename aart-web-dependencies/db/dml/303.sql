INSERT INTO groups (id, organizationid, groupname, defaultrole,
	createddate, activeflag, createduser, modifieduser,
	modifieddate, organizationtypeid)
VALUES (nextval('groups_id_seq'),
	(SELECT id FROM organization WHERE organizationname = 'CETE Organization' LIMIT 1),
	'Technology Director', FALSE, now(), TRUE,
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
	now(), (SELECT id FROM organizationtype WHERE typecode = 'BLDG')
);

DO 
$BODY$
DECLARE
	r RECORD;
	techdirectorid BIGINT;
	cetesysadminid BIGINT;
BEGIN
	SELECT INTO techdirectorid (SELECT id FROM groups WHERE groupname = 'Technology Director');
	SELECT INTO cetesysadminid (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
	FOR r IN
		SELECT a.id
		FROM groupauthorities ga
		INNER JOIN authorities a ON  ga.authorityid = a.id
		WHERE groupid = (SELECT id FROM groups WHERE groupname ~* 'Principal')
	LOOP
		INSERT INTO groupauthorities (id, groupid, authorityid, createddate,
			createduser, activeflag, modifieddate, modifieduser)
		VALUES (nextval('groupauthorities_id_seq'),
			techdirectorid,
			r.id,
			now(), cetesysadminid,
			TRUE,
			now(), cetesysadminid
		);
	END LOOP;
END;
$BODY$;