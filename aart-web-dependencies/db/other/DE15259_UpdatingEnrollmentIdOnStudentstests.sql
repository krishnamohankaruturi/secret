DO 
$BODY$
DECLARE
    studentstesidupdated BIGINT;
    count INTEGER := 0;
    totalUpdatedCount INTEGER := 0;
    studentIds BIGINT[] := ARRAY[212214, 239189, 451127, 511932, 530050, 539971, 641553, 658879, 707164, 725530, 729159, 776819, 801764, 828112,
                   853270, 855550, 855616, 856431, 856453, 857974, 858281, 858298, 859338, 863506, 873153, 880235, 881631, 883792, 927082, 927110, 929238,
                   948048, 1077992, 1092484, 1162944, 1204160, 1205110, 1217988, 1323240, 1323977, 1324266, 1324598, 1324949, 1325441, 1328192];
                   
BEGIN
     FOR i IN array_lower(studentIds, 1) .. array_upper(studentIds, 1) LOOP
	  UPDATE studentstests SET enrollmentid = (SELECT id FROM enrollment WHERE studentid = studentIds[i] AND currentschoolyear = 2017 and activeflag is true LIMIT 1)
		WHERE enrollmentid in (SELECT id FROM enrollment WHERE studentid = studentIds[i] AND currentschoolyear = 2017 and activeflag is false)
		 AND testsessionid in (select id from testsession where rosterid in (select rosterid from enrollmentsrosters where 
		enrollmentid in (select id from enrollment where studentid = studentIds[i] AND currentschoolyear = 2017 and activeflag is true) and activeflag is true))
		AND activeflag is true; 
	 GET DIAGNOSTICS count = ROW_COUNT;	
	 totalUpdatedCount := totalUpdatedCount + count;
	 RAISE NOTICE 'Studentid: %, Studentstests updated: %', studentIds[i], count;
	 	 
     END LOOP;
     RAISE NOTICE 'Total records updated: %', totalUpdatedCount;
END;
$BODY$;