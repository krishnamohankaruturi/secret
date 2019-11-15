-- US17061: DLM Lockdown Service Desk Data Scripts
-- Updating the studentstests, student testsections, testsessions, student tracker, and ITI testsessions

DROP FUNCTION IF EXISTS inActivateStuTestsTrackerITITestsessions(bigint, bigint, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION inActivateStuTestsTrackerITITestsessions(studentTestsId bigint, inActiveStuTestSecStatusId bigint, inActiveStuTestStatusId bigint, testsession_Id bigint, student_Id bigint)
       RETURNS VOID AS
$BODY$
   DECLARE
     ceteSysAdminUserId BIGINT;
	
	BEGIN
	  SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
	  UPDATE studentstestsections SET activeflag=false,modifieddate=CURRENT_TIMESTAMP, modifieduser = ceteSysAdminUserId,statusid=inActiveStuTestSecStatusId WHERE studentstestid=studentTestsId;
				
	  RAISE NOTICE 'Updated the studentstestsections records with studentstetsid: %', studentTestsId;
		
	  UPDATE studentstests SET activeflag=false,modifieddate=CURRENT_TIMESTAMP,modifieduser=ceteSysAdminUserId,status=inActiveStuTestSecStatusId WHERE id=studentTestsId;		

	  RAISE NOTICE 'Updated the studentstests records with id: %', studentTestsId;

	  IF ((SELECT count(*) FROM studentstests WHERE testsessionid = testsession_Id) = 1) THEN
		UPDATE testsession SET activeflag=false,modifieddate=CURRENT_TIMESTAMP,modifieduser=ceteSysAdminUserId,status=inActiveStuTestSecStatusId WHERE id=testsession_Id;		

		RAISE NOTICE 'Updated the testsession records with id: %', testsession_Id;
          END IF;
		
	  UPDATE studenttrackerband stb SET testsessionid=null,modifieddate=CURRENT_TIMESTAMP,modifieduser=ceteSysAdminUserId FROM studenttracker st 
		       WHERE st.id=stb.studenttrackerid AND st.studentid=student_Id and stb.testsessionid=testsession_Id;		
                
		       
	  UPDATE ititestsessionhistory SET activeflag=false,modifieddate=CURRENT_TIMESTAMP,modifieduser=ceteSysAdminUserId,status=inActiveStuTestSecStatusId
			WHERE studentid=student_Id AND testsessionid=testsession_Id;  		
        END;

$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;
      