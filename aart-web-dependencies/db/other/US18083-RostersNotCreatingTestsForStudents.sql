-- US18083: Rosters not creating tests for students
-- Moving all testsessions from roster "zzDo Not Use" to "Anthony" ELA roster and also removing all students from "zzDo Not Use" and inactivating the roster.

DO
$BODY$

DECLARE

   cetesysadminuserid BIGINT;
   zz_roster_id BIGINT;
   anthony_ela_roster_id BIGINT;
   ela_subject_id BIGINT;
   updatecount INTEGER;

   studentids BIGINT[] = ARRAY[1080093, 1307194, 1080092, 1080099, 1307193];

BEGIN

   SELECT id FROM contentarea WHERE abbreviatedname = 'ELA' INTO ela_subject_id;

   SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminuserid;

   SELECT id FROM roster WHERE id = 1038743 AND coursesectionname = 'zzDo Not Use' AND statesubjectareaid = ela_subject_id INTO zz_roster_id;

   SELECT id FROM roster WHERE id = 1038903 AND coursesectionname = 'Anthony' AND statesubjectareaid = ela_subject_id INTO anthony_ela_roster_id;


   FOR i IN array_lower(studentids, 1) .. array_upper(studentids, 1) LOOP

      WITH testsessionupdateCount AS (UPDATE testsession SET rosterid = anthony_ela_roster_id, modifieduser = cetesysadminuserid, modifieddate = now() 
         WHERE id IN (SELECT testsessionid FROM studentstests WHERE studentid = studentids[i]) AND rosterid = zz_roster_id RETURNING 1)
         SELECT count(*) FROM testsessionupdateCount INTO updatecount;


      RAISE NOTICE 'For student %, % number of testsessions were moved from roster % to new roster %', studentids[i], updatecount, zz_roster_id, anthony_ela_roster_id;


      WITH itiPlansUpdateCount AS (UPDATE ititestsessionhistory SET rosterid = anthony_ela_roster_id, modifieduser = cetesysadminuserid, modifieddate = now(),
            studentenrlrosterid = (SELECT enrl.id FROM enrollment en JOIN enrollmentsrosters enrl ON enrl.enrollmentid = en.id WHERE en.studentid = studentids[i] AND enrl.rosterid = zz_roster_id LIMIT 1)
            WHERE studentid = studentids[i] AND rosterid = zz_roster_id RETURNING 1)
            SELECT count(*) FROM itiPlansUpdateCount INTO updatecount;


      RAISE NOTICE 'For student %, % number of itiplans were moved from roster % to new roster %', studentids[i], updatecount, zz_roster_id, anthony_ela_roster_id;


      WITH enrollmentrosterUpdateCount AS (UPDATE enrollmentsrosters SET activeflag = false,  modifieduser = cetesysadminuserid, modifieddate = now()
                WHERE enrollmentid IN (SELECT id FROM enrollment WHERE studentid = studentids[i] AND currentschoolyear = 2016 AND activeflag IS true)
                AND rosterid = zz_roster_id RETURNING 1)
                SELECT count(*) FROM enrollmentrosterUpdateCount INTO updatecount;

      RAISE NOTICE '% Number of enrollmentsrosters records were inactivated for studentid % and roster % combination', updatecount, studentids[i], zz_roster_id;
                   
   END LOOP;

   WITH rosterupdateCount AS(UPDATE roster SET activeflag = false, modifieduser = cetesysadminuserid, modifieddate = now() WHERE id = zz_roster_id RETURNING 1) 
          SELECT count(*) FROM rosterupdateCount INTO updatecount;

   RAISE NOTICE 'Roster % is now inactivated, update count %', zz_roster_id, updatecount;

END;
$BODY$;