----------------------
-- activate records --
----------------------

DO
$BODY$
DECLARE
	num_updated INTEGER;
	
	data TEXT[][] := ARRAY[
		['4323968248', '1746'],
		['8273556743', '1746'],
		['4302299061', '6532'],
		['7247014636', '6532'],
		['8584517219', '7612'],
		['2486727647', '0781'],
		['5144858244', '1618'],
		['6941265949', '1798'],
		['9155488927', '1640'],
		['3150311349', '1664'],
		['7282625824', '1664'],
		['8329374165', '1646'],
		['7512493568', '1810'],
		['3876376939', '1810'],
		['6020697703', '1808'],
		['5836811059', '1808'],
		['8966763693', '1662'],
		['4415128327', '1836'],
		['8919960895', '1674'],
		['3318022314', '1625'],
		['7761943095', '1625'],
		['2356051821', '1682'],
		['7074479918', '1684'],
		['6865921887', '1814'],
		['1182884121', '1814'],
		['9440394374', '1846'],
		['2987732732', '1690'],
		['6412548645', '1694'],
		['5313114182', '1817'],
		['7671807419', '1817'],
		['4157496981', '1817'],
		['6776860904', '1704'],
		['2156799873', '1718'],
		['2186517787', '1617'],
		['3535497167', '1627'],
		['4276730864', '1837'],
		['5647894976', '1838'],
		['9216357827', '1838'],
		['4787495763', '1838'],
		['8394906338', '1838'],
		['6779160768', '1838'],
		['8869652386', '1838'],
		['9578876971', '1838'],
		['5193610595', '1838'],
		['1033098566', '1778'],
		['4255699879', '1828'],
		['6040544376', '1830'],
		['9303727886', '1772'],
		['3097698868', '1840'],
		['9607506901', '1840'],
		['3531734393', '1842'],
		['8398560584', '1780'],
		['2688910981', '1780'],
		['4421070181', '1785'],
		['7713722548', '1834'],
		['1367179408', '1834'],
		['5504774403', '1834'],
		['5405906484', '1834'],
		['5621349768', '1834'],
		['3849215466', '1834'],
		['5734817767', '1834'],
		['5952638538', '1790'],
		['9866185788', '1790'],
		['7578144863', '1792'],
		['4886861539', '1833'],
		['3207288626', '1833']
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
				where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
				and s.stateid = (select id from organization where displayidentifier = 'KS')
				and o.displayidentifier = data[i][2]
				and s.statestudentidentifier = data[i][1]
			)
			returning 1
		)
		SELECT count(*) FROM updated_rows INTO num_updated;
		RAISE NOTICE '[''%'', ''%''] - Activated % rows', data[i][1], data[i][2], num_updated;
	END LOOP;
END;
$BODY$;

------------------------
-- deactivate records --
------------------------

DO
$BODY$
DECLARE
	num_updated INTEGER;
	
	data TEXT[][] := ARRAY[
		['3928572067', '0898'],
		['8004080839', '4762'],
		['1297885392', '4762']
	];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		-- deactivate records
		WITH updated_rows AS (
			update enrollment
			set activeflag = false,
			exitwithdrawaldate = now(),
			exitwithdrawaltype = -55,
			modifieddate = now(),
			modifieduser = (select id from aartuser where username = 'cetesysadmin'),
			notes = 'Deactivated enrollment as per US17793'
			where id = (
				select e.id
				from enrollment e
				inner join student s on e.studentid = s.id
				inner join organization o on e.attendanceschoolid = o.id
				where o.id in (select id from organization_children((select id from organization where displayidentifier = 'KS')))
				and s.stateid = (select id from organization where displayidentifier = 'KS')
				and o.displayidentifier = data[i][2]
				and s.statestudentidentifier = data[i][1]
			)
			returning 1
		)
		SELECT count(*) FROM updated_rows INTO num_updated;
		RAISE NOTICE '[''%'', ''%''] - Deactivated % rows', data[i][1], data[i][2], num_updated;
	END LOOP;
END;
$BODY$;