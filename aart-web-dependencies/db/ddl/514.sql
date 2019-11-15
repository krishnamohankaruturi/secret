
--ddl/514.sql
CREATE OR REPLACE FUNCTION insert_lockdown_date(IN statedisplayidentifier text, fromdate timestamp with time zone, todate timestamp with time zone)
  RETURNS void AS
$BODY$ 
DECLARE
    CROW RECORD;
    orgid bigint;
    adminuser bigint;
BEGIN
	orgid:= (select id from organization where displayidentifier=statedisplayidentifier and activeflag is true
			and organizationtypeid=(select id from organizationtype where typecode='ST' and activeflag is true));
	adminuser := (select id from aartuser where email='cete@ku.edu');
	FOR CROW IN (SELECT id FROM groupauthorities where authorityid in (select id from authorities 
		where authority in ( 'PERM_ENRL_UPLOAD', 'PERM_ROSTERRECORD_CREATE', 'PERM_ROSTERRECORD_UPLOAD', 'PERM_ROSTERRECORD_MODIFY', 
		'PERM_EXIT_STUDENT', 'PERM_TRANSFER_STUDENT','PERM_STUDENTRECORD_MODIFY') and activeflag is true) and activeflag is true) LOOP
		INSERT INTO groupauthoritylockdownperiod(organizationid, groupauthorityid, fromdate, todate, createduser, 
					createddate, modifieduser, modifieddate, activeflag)
			VALUES (orgid, CROW.id, fromdate, todate, adminuser, now(), adminuser, now(), true);
	END LOOP;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

ALTER TABLE aartuser ALTER COLUMN displayname TYPE character varying(160);

CREATE OR REPLACE FUNCTION addglobalsystemadminrole(IN emailAddress text)
  RETURNS void AS
$BODY$
	DECLARE 
		userId bigint;
		userOrgId bigint;
	BEGIN
  		SELECT INTO userId (select id from aartuser where email=emailAddress and activeflag is true);
  		userOrgId := (select nextval('usersorganizations_id_seq'::regclass));
		INSERT INTO usersorganizations(id, aartuserid, organizationid, isdefault, createddate, createduser, modifieddate, modifieduser)
			VALUES (userOrgId, userId, (select id from organization where displayidentifier='CETE' limit 1), false, now(), 
			(select id from aartuser where email='cete@ku.edu' and activeflag is true), now(), 
			(select id from aartuser where email='cete@ku.edu' and activeflag is true));
            
		INSERT INTO userorganizationsgroups(groupid, status, userorganizationid, createddate, createduser, modifieddate, modifieduser)
			VALUES ((select id from groups where groupcode='GSAD'), 2, userOrgId, now(), (select id from aartuser where email='cete@ku.edu' and activeflag is true), now(), 
			(select id from aartuser where email='cete@ku.edu' and activeflag is true));
        END;
	$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- function to get a number such that (start_int <= number < end_int)
CREATE OR REPLACE FUNCTION get_random_number(integer, integer)
  RETURNS integer AS
$BODY$
DECLARE
	start_int ALIAS FOR $1;
	end_int ALIAS FOR $2;
BEGIN
	RETURN trunc(random() * (end_int - start_int) + start_int);
END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;

-- function to generate a password using the same strategy as EP
CREATE OR REPLACE FUNCTION generate_student_password()
  RETURNS text AS
$BODY$
DECLARE
	MAX_PASSWORD_LENGTH INTEGER := 5;
	password TEXT;
	chars_needed INTEGER;
	i INTEGER;
BEGIN
	SELECT word
	FROM studentpassword
	WHERE char_length(word) <= MAX_PASSWORD_LENGTH
	ORDER BY random()
	LIMIT 1
	INTO password;

	SELECT MAX_PASSWORD_LENGTH - char_length(password) INTO chars_needed;
	
	IF chars_needed > 0 THEN
		SELECT concat(
			password,
			(select get_random_number(
				(select (10 ^ (chars_needed - 1))::INTEGER), -- lowest number with # digits = chars_needed
				(select (10 ^ chars_needed)::INTEGER)))) -- lowest number with # digits = chars_needed + 1
		INTO password;
	END IF;
	
	RETURN password;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100; 