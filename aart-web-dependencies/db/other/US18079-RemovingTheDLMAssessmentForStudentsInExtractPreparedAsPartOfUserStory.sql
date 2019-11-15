-- US18079: Create new dual assessment list (KAP/DLM and test type = 2) who have not tested.
-- Prepared the extract and provided and got the approval to remove the DLM assessment on 56 students who were listed in the extract

DO
$BODY$
DECLARE
	currentstudentid BIGINT;		
	data BIGINT[] := ARRAY[151300,1309492,87954,907051,903468,1271103,274185,1235633,699651,1147795,903349,107957,199701,1241806,
	           506140,805905,816357,566618,874560,649427,17037,268006,109011,542268,532499,845317,570003,684195,649458,271973,574955,
	           816443,883811,1235173,391243,797694,797694,169591,883816,58303,805753,647110,506141,108057,568780,1217647,1217647,321732,
	           1276473,754560,597021,1137619,94984,630245,1217513,1217513];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		SELECT id
		FROM student
		WHERE stateid = (SELECT id FROM organization WHERE displayidentifier = 'KS') AND id = data[i]
		LIMIT 1
		INTO currentstudentid;		
		
		IF currentstudentid IS NOT NULL THEN		       
		    PERFORM remove_student_from_assessment_program(currentstudentid, 'DLM', 2016);

                    UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
                          WHERE studentstestid IN (SELECT id FROM studentstests WHERE studentid = currentstudentid
                              AND testsessionid IN (SELECT id FROM testsession WHERE schoolyear = 2016 AND (source = 'ITI'
                              OR testtypeid IN
                               (SELECT tt.id
				FROM testtype tt
				INNER JOIN assessment a ON tt.assessmentid = a.id AND tt.activeflag = TRUE AND a.activeflag = TRUE
				INNER JOIN testingprogram tp ON a.testingprogramid = tp.id AND tp.activeflag = TRUE
				WHERE tp.assessmentprogramid IN (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM')))));


		     UPDATE studentstests SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
                          WHERE studentid = currentstudentid
                              AND testsessionid IN (SELECT id FROM testsession WHERE schoolyear = 2016 AND (source = 'ITI'
                              OR testtypeid IN
                               (SELECT tt.id
				FROM testtype tt
				INNER JOIN assessment a ON tt.assessmentid = a.id AND tt.activeflag = TRUE AND a.activeflag = TRUE
				INNER JOIN testingprogram tp ON a.testingprogramid = tp.id AND tp.activeflag = TRUE
				WHERE tp.assessmentprogramid IN (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM'))));


                   UPDATE testsession SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
                         WHERE id IN (SELECT testsessionid FROM ititestsessionhistory WHERE studentid = currentstudentid);		     

                    UPDATE ititestsessionhistory SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
                          WHERE studentid = currentstudentid;		    
		    
		ELSE			
		     RAISE NOTICE 'Student EP_Id % not found in KS, skipping...', data[i];
		END IF;
	END LOOP;
END;
$BODY$;
