----------------------
-- activate records --
----------------------

DO
$BODY$
DECLARE
	num_updated INTEGER;
	
	data TEXT[][][] := ARRAY[
		['5431804049', '4819', '4788']
	];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		-- reactivate records
		WITH updated_rows AS (
			update enrollment
			set activeflag = true,
			exitwithdrawaldate = null,
			exitwithdrawaltype = null,
			modifieddate = now(),
			modifieduser = (select id from aartuser where username = 'cetesysadmin'),
			notes = 'Activated enrollment as per US17793'
			where activeflag = false
			and id = (
				select e.id
				from enrollment e
				inner join student s on e.studentid = s.id
				inner join organization o on e.attendanceschoolid = o.id
				inner join organization aypSch on aypsch.id = e.aypschoolid
				where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
				and s.stateid = (select id from organization where displayidentifier = 'KS')
				and o.displayidentifier = data[i][2]
				and aypSch.displayidentifier = data[i][3]
				and s.statestudentidentifier = data[i][1]
			)
			returning 1
		)
		SELECT count(*) FROM updated_rows INTO num_updated;
		RAISE NOTICE '[''%'', ''%'', ''%''] - Activated % rows', data[i][1], data[i][2], data[i][3], num_updated;
	END LOOP;
END;
$BODY$;