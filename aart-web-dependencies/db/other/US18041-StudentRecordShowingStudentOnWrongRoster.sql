-- US18041: Student Record showing student on wrong Roster

-- No students tests found on this roster 
UPDATE enrollmentsrosters SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
   WHERE enrollmentid IN (SELECT id FROM enrollment WHERE studentid = 737611 AND currentschoolyear = 2016 AND activeflag is true) 
   AND rosterid IN (SELECT id FROM roster WHERE coursesectionname = '048068SCI' and teacherid = 55327);  

-- Update count: 1