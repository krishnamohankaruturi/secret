-- US18439:EP: Prod -Student who was corrected with KAP Dual Enrollment fix Stage 1 unused and stage 2 complete
 update studentstests set enrollmentid = 2357225, testsessionid = 2264530, modifieddate = now(), modifieduser = 12
    where id = 10397245;

update studentstests set enrollmentid = 2357225, testsessionid = 2392853, modifieddate = now(), modifieduser = 12
    where id = 11075253;

update studentstests set enrollmentid = 2357225, testsessionid = 2393622, modifieddate = now(), modifieduser = 12
    where id = 11122519;

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = 12
   where studentstestsid in (10438981, 11075312, 11122577);

update studentstestsections set activeflag = false, modifieddate = now(), modifieduser = 12
   where studentstestid in (10438981, 11075312, 11122577);

update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12
   where id in (10438981, 11075312, 11122577);
  
 
   
 
update studentstests set enrollmentid = 2359621, testsessionid = 2489382, modifieddate = now(), modifieduser = 12
    where id = 12071096;

update studentstests set enrollmentid = 2359621, testsessionid = 2248608, modifieddate = now(), modifieduser = 12
    where id = 9720996;
   
update studentstests set enrollmentid = 1885454, testsessionid = 2510993, modifieddate = now(), modifieduser = 12
    where id = 12104981;

update studentstests set enrollmentid = 2359621, testsessionid = 2489382, modifieddate = now(), modifieduser = 12
    where id = 12071096;
    
update studentstests set enrollmentid = 2282458, testsessionid = 2490978, modifieddate = now(), modifieduser = 12
    where id = 12159159;

update studentstests set enrollmentid = 2282458, testsessionid = 2486492, modifieddate = now(), modifieduser = 12
    where id = 12116681;    
    
update studentstests set enrollmentid = 2268812, testsessionid = 2253082, modifieddate = now(), modifieduser = 12
    where id = 9642049;
    
update studentstests set enrollmentid = 2359754, testsessionid = 2252244, modifieddate = now(), modifieduser = 12
    where id = 9633810;     

update studentstests set enrollmentid = 2359929, testsessionid = 2260978, modifieddate = now(), modifieduser = 12
    where id = 10232402; 
    
    

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = 12
   where studentstestsid in (10153255, 12077792, 12116119, 9625037, 11075393, 10153255, 10225786, 12108831,13118994, 10080304, 2359754, 10430325);

update studentstestsections set activeflag = false, modifieddate = now(), modifieduser = 12
   where studentstestid in (10153255, 12077792, 12116119, 9625037, 11075393, 10153255, 10225786, 12108831,13118994, 10080304, 2359754, 10430325);

update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12
   where id in (10153255, 12077792, 12116119, 9625037, 11075393, 10153255, 10225786, 12108831,13118994, 10080304, 2359754, 10430325);     
   
   