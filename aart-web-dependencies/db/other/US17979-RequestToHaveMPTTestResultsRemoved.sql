-- US17979: Request to have MPT test results removed.
DO
$BODY$
DECLARE
  enrollment_id BIGINT;
  number_of_updates INTEGER;

BEGIN

  SELECT id FROM enrollment WHERE studentid = 1084892 AND currentschoolyear = 2016 
      AND attendanceschoolid = (SELECT schoolid FROM organizationtreedetail WHERE schooldisplayidentifier = '8194' AND statedisplayidentifier = 'KS') INTO enrollment_id;

  WITH student_responses_update AS(
  UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (SELECT id FROM studentstests WHERE enrollmentid = enrollment_id AND 
          testsessionid IN (SELECT id FROM testsession WHERE name = '2016_8194_Grade 3_Mathematics_Performance')) RETURNING 1) 
          SELECT count(*) FROM student_responses_update INTO number_of_updates;


  RAISE NOTICE 'Deactivated % number of student responses', number_of_updates;
  -- Update count: 5            
  

  WITH student_testsections_update AS(
  UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()  
      WHERE studentstestid IN (SELECT id FROM studentstests WHERE enrollmentid = enrollment_id AND 
          testsessionid IN (SELECT id FROM testsession WHERE name = '2016_8194_Grade 3_Mathematics_Performance')) RETURNING 1)
          SELECT count(*) FROM student_testsections_update INTO number_of_updates;

   RAISE NOTICE 'Deactivated % number of student testsections', number_of_updates;
   -- Update count: 1          

 WITH student_testsections_update AS(
  UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE enrollmentid = enrollment_id AND testsessionid IN (SELECT id FROM testsession WHERE name = '2016_8194_Grade 3_Mathematics_Performance')RETURNING 1)
     SELECT count(*) FROM student_testsections_update INTO number_of_updates;

   RAISE NOTICE 'Deactivated % number of studentstests', number_of_updates;
     
   -- Update count: 1

END;
$BODY$;