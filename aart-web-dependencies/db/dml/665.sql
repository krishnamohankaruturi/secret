-- DML statements - F61

--select * from update_studentreport_school_district_name('KAP',2015);
--select * from update_studentreport_school_district_name('KAP',2016);
--select * from update_studentreport_school_district_name('KAP',2017);
--select * from update_studentreport_school_district_name('AMP',2015);
--select * from update_studentreport_school_district_name('K-ELPA',2017);

--select update_studentreport_missing_school_district_name('K-ELPA',2017);
--select update_studentreport_missing_school_district_name('KAP',2015);
--select update_studentreport_missing_school_district_name('KAP',2016);

--update externalstudentreports sr 
--set schoolname = (select organizationname from organization where id = sr.schoolid), 
--districtname = (select organizationname from organization where id = sr.districtid);

-- permissions for FCS data extract
DO
$BODY$
BEGIN
	IF ((SELECT count(id) FROM authorities WHERE authority = 'DATA_EXTRACTS_FCS_REPORT' AND activeflag = TRUE) = 0) THEN
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_FCS_REPORT does not exist. Inserting...';
		INSERT INTO authorities(
				id, authority, displayname, objecttype, createddate, createduser,
				activeflag, modifieddate, modifieduser)
			VALUES (nextval('authorities_id_seq'), 'DATA_EXTRACTS_FCS_REPORT', 'Create First Contact Survey Extract',
				'Reports-Data Extracts', now(), (select id from aartuser where username='cetesysadmin'),
				TRUE, now(), (Select id from aartuser where username='cetesysadmin'));
	ELSE
		RAISE NOTICE '%', 'Permission DATA_EXTRACTS_FCS_REPORT exists. Skipping...';
	END IF;
END;
$BODY$;
