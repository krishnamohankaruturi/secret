--US18470: EP Prod - Edit grade level for DLM student who is not having any tests assigned - High

UPDATE testsession 
set activeflag = false
where id in (select testsessionid from ititestsessionhistory  
where studentid = (select id from student where statestudentidentifier = '9095567109' and stateid = 51)
and rosterid in (892615, 892616)
and testcollectionid in (select id from testcollection where contentareaid in (3, 440)));


UPDATE ititestsessionhistory  
set activeflag = false
where studentid = (select id from student where statestudentidentifier = '9095567109' and stateid = 51)
and rosterid in (892615, 892616)
and testcollectionid in (select id from testcollection where contentareaid in (3, 440));
	
UPDATE studentstestsections 
set activeflag = false
where studentstestid in (select id from studentstests where testcollectionid in (select id from testcollection where contentareaid in (3, 440)) 
and studentid = (select id from student where statestudentidentifier = '9095567109' and stateid = 51) and activeflag is true);
  
UPDATE studentsresponses 
set activeflag = false
where studentstestsid in (select id from studentstests where testcollectionid in (select id from testcollection where contentareaid in (3, 440)) 
and studentid = (select id from student where statestudentidentifier = '9095567109' and stateid = 51) and activeflag is true);

UPDATE studentstests 
set activeflag = false
where testcollectionid in (select id from testcollection where contentareaid in (3, 440)) 
and studentid = (select id from student where statestudentidentifier = '9095567109' and stateid = 51) and activeflag is true;





