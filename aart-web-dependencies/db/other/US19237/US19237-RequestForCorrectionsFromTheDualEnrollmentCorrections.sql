BEGIN;
DROP TABLE IF EXISTS temp_studentdata;

CREATE temp TABLE temp_studentdata(statestudentid character varying, schoolid bigint, correctenrollmentid bigint, incorrectenrollment bigint);

\COPY temp_studentdata FROM 'US19237_input.csv' WITH (FORMAT CSV, HEADER);

DO
$BODY$
DECLARE
old_sch_testsessions_record RECORD;
enrollmentrecords RECORD;
new_sch_testsessionid BIGINT;
inactivatedcount INTEGER;
cetesysadminid BIGINT;
correct_enrollment_studentstestsid BIGINT;
rtn_msg TEXT;
BEGIN

    SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
    rtn_msg := '';
   inactivatedcount := 0;
  FOR enrollmentrecords IN (SELECT * FROM temp_studentdata) LOOP
	RAISE NOTICE 'Moving testsessions for student %', enrollmentrecords.statestudentid;
	WITH updatecount AS (UPDATE studentstests SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid,
	    manualupdatereason = 'US19237-EP-Prod - Request for corrections from the Dual Enrollment corrections--Inactivating pending and unused tests'		
	    WHERE enrollmentid = enrollmentrecords.incorrectenrollment
	    AND status in (84,465) AND activeflag is TRUE RETURNING 1) SELECT count(*) FROM updatecount INTO inactivatedcount;
	    
	RAISE NOTICE 'Inactivated % unused and pending tests in old school', inactivatedcount;

        FOR old_sch_testsessions_record IN (SELECT * FROM studentstests WHERE enrollmentid = enrollmentrecords.incorrectenrollment
              AND activeflag IS true) LOOP

		IF((SELECT count(*) FROM studentstests WHERE enrollmentid = enrollmentrecords.correctenrollmentid
			AND testcollectionid = old_sch_testsessions_record.testcollectionid
			AND activeflag IS true) < 1) THEN

		    IF((SELECT count(*) FROM testsession WHERE testcollectionid = old_sch_testsessions_record.testcollectionid
		       AND attendanceschoolid = enrollmentrecords.schoolid) >= 1) THEN

			SELECT id FROM testsession WHERE testcollectionid = old_sch_testsessions_record.testcollectionid
				AND attendanceschoolid = enrollmentrecords.schoolid INTO new_sch_testsessionid;

			WITH updatecount AS(UPDATE studentstests SET enrollmentid = enrollmentrecords.correctenrollmentid, testsessionid = new_sch_testsessionid,
			          modifieduser = cetesysadminid, modifieddate = now(), 
			          manualupdatereason = 'US19237-EP-Prod - Request for corrections from the Dual Enrollment corrections'
			          WHERE id =  old_sch_testsessions_record.id RETURNING 1) SELECT count(*) FROM updatecount INTO inactivatedcount;
			   IF(inactivatedcount > 0) THEN
				RAISE NOTICE 'Inside moving testsession. moved studentstests %', old_sch_testsessions_record.id;
			   END IF;
			
		    ELSE
			RAISE NOTICE 'Unable to move studentstest: %, no testsessions found in new school', old_sch_testsessions_record.id;
		    END IF;

		ELSE		

		  SELECT id FROM studentstests WHERE enrollmentid = enrollmentrecords.correctenrollmentid
			AND testcollectionid = old_sch_testsessions_record.testcollectionid
			AND activeflag IS true INTO correct_enrollment_studentstestsid;

		  SELECT testsessionid FROM studentstests WHERE enrollmentid = enrollmentrecords.correctenrollmentid
			AND testcollectionid = old_sch_testsessions_record.testcollectionid
			AND activeflag IS true INTO new_sch_testsessionid;
			
		  IF((SELECT SUM(score) FROM studentsresponses WHERE studentstestsid = correct_enrollment_studentstestsid AND activeflag IS true) >=
		     (SELECT SUM(score) FROM studentsresponses WHERE studentstestsid = old_sch_testsessions_record.id AND activeflag IS true)) THEN


			UPDATE studentsresponses SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid
			     WHERE studentstestsid = old_sch_testsessions_record.id;

			UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid
			    WHERE studentstestid = old_sch_testsessions_record.id;
			    
			UPDATE studentstests SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid,
				manualupdatereason = 'US19237-EP-Prod - Request for corrections from the Dual Enrollment corrections--Inactivating because of low score when compare to score in new school'		
				WHERE id = old_sch_testsessions_record.id;				
			
		  ELSE
			UPDATE studentstests SET enrollmentid = enrollmentrecords.correctenrollmentid, testsessionid = new_sch_testsessionid,
			          modifieduser = cetesysadminid, modifieddate = now(), 
			          manualupdatereason = 'US19237-EP-Prod - Request for corrections from the Dual Enrollment corrections'
				  WHERE id =  old_sch_testsessions_record.id;

			 	
			UPDATE studentsresponses SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid
			     WHERE studentstestsid = correct_enrollment_studentstestsid;

			UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid
			    WHERE studentstestid = correct_enrollment_studentstestsid;
			
			UPDATE studentstests SET activeflag = false, modifieddate = now(), modifieduser = cetesysadminid,
				manualupdatereason = 'US19237-EP-Prod - Request for corrections from the Dual Enrollment corrections--Inactivating because of low score when compare to score in old school'		
				WHERE id = correct_enrollment_studentstestsid;
			
		  END IF;
			
		END IF;
		
        END LOOP;
	  UPDATE enrollment SET activeflag = false,modifieddate = now(), modifieduser = cetesysadminid, exitwithdrawaltype = -55, exitwithdrawaldate = now(),
                        notes = 'Inactivating enrollments as per US19237'
                        WHERE id = enrollmentrecords.incorrectenrollment and activeflag is true;
        		
  END LOOP;

END;
$BODY$;

DROP TABLE IF EXISTS temp_studentdata;


UPDATE enrollment SET activeflag = true, modifieddate = now(), exitwithdrawaltype= null, exitwithdrawaldate = null, modifieduser = 12, notes = 'Activating enrollments as per US19237'
 WHERE id = 2086745;
 
 
UPDATE studentstests SET enrollmentid = 2049488, testsessionid = 2271442, modifieduser = 12, modifieddate = now(), activeflag = true,
	manualupdatereason = 'US19237-EP-Prod - Request for corrections from the Dual Enrollment corrections--Reactivating Science stage-3 test which was completed in old school'
				  WHERE id =  10727705;


UPDATE studentstests SET modifieduser = 12, modifieddate = now(), activeflag = false,
	manualupdatereason = 'US19237-EP-Prod - Request for corrections from the Dual Enrollment corrections--Inactivating stage-3 science test from new school beacuse its not completed but stage-3 in old school is completed'
				  WHERE id =  10720149;

commit;