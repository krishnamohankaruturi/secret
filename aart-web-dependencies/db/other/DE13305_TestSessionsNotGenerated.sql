--DE13305: EP: Production High - Student with Valid Rosters and FCS/PNP not generating Test Sessions.

UPDATE enrollmentsrosters
set activeflag = false, modifieduser = 12, modifieddate = now()
where enrollmentid in (select id from enrollment where studentid = (select id from student where statestudentidentifier = '9295032209' and stateid = 51) and activeflag is false) and activeflag is true
and rosterid in (912594, 912595);

UPDATE testsession 
SET activeflag = false, modifieduser = 12, modifieddate = now()
WHERE id = 2285571 and rosterid =912594;

UPDATE testsession 
SET activeflag = false, modifieduser = 12, modifieddate = now()
WHERE id = 2285568 and rosterid =912595;
