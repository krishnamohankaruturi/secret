DO
$BODY$
BEGIN
  IF EXISTS (
    SELECT column_name
    FROM information_schema.columns 
    WHERE table_name='enrollment' AND column_name='notes'
  ) THEN
    RAISE NOTICE 'notes column found in enrollment already, skipping add';
  ELSE
    RAISE NOTICE 'notes column not found in enrollment, adding';
    ALTER TABLE enrollment ADD COLUMN notes TEXT;
  END IF;
  
  IF EXISTS (
    SELECT column_name
    FROM information_schema.columns 
    WHERE table_name='roster' AND column_name='notes'
  ) THEN
    RAISE NOTICE 'notes column found in roster already, skipping add';
  ELSE
    RAISE NOTICE 'notes column not found in roster, adding';
    ALTER TABLE roster ADD COLUMN notes TEXT;
  END IF;
END;
$BODY$;

DO
$BODY$
DECLARE
  enrollmentrecord RECORD;
  enrollmentsrostersrecord RECORD;
  studentstestsrecord RECORD;
BEGIN
  
  FOR enrollmentrecord IN (
    SELECT e.id
    FROM organization o
    INNER JOIN enrollment e ON o.id = e.attendanceschoolid AND e.activeflag = TRUE
    INNER JOIN student s ON e.studentid = s.id AND s.activeflag = TRUE
    WHERE o.id in (
      SELECT id FROM organization_children((SELECT id FROM organization WHERE displayidentifier = 'D0272'))
    )
    AND e.currentschoolyear = 2016
  )
  LOOP
    FOR enrollmentsrostersrecord IN (
      SELECT er.id, er.rosterid
      FROM enrollmentsrosters er
      WHERE er.enrollmentid = enrollmentrecord.id
    )
    LOOP
      UPDATE ititestsessionhistory
      SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
      WHERE activeflag = TRUE AND studentenrlrosterid = enrollmentsrostersrecord.id;
      
      -- this will only run once per roster (the next time it won't be active)
      UPDATE roster
      SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
      notes = 'deactivated for US17444 - wipe data from Waconda district'
      WHERE activeflag = TRUE AND id = enrollmentsrostersrecord.rosterid;
      
      UPDATE enrollmentsrosters
      SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
      WHERE activeflag = TRUE AND id = enrollmentsrostersrecord.id;
            
    END LOOP;
      
    FOR studentstestsrecord IN (
      SELECT id, testsessionid FROM studentstests WHERE activeflag = TRUE AND enrollmentid = enrollmentrecord.id
    )
    LOOP
      UPDATE studentstestsections
      SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
      WHERE studentstestid = studentstestsrecord.id;
      
      UPDATE studentsresponses
      SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
      WHERE studentstestsid = studentstestsrecord.id;
      
      UPDATE studentstests
      SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
      WHERE id = studentstestsrecord.id;
      
      -- this will only run once per test session (the next time it won't be active)
      UPDATE testsession
      SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
      WHERE activeflag = TRUE AND id = studentstestsrecord.testsessionid;
    END LOOP;
    
    UPDATE enrollmenttesttypesubjectarea 
     SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE enrollmentid = enrollmentrecord.id;
    	 
    UPDATE enrollment
    SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
    notes = 'deactivated for US17444 - wipe data from Waconda district'
    WHERE id = enrollmentrecord.id;
  END LOOP;
  
END;
$BODY$;