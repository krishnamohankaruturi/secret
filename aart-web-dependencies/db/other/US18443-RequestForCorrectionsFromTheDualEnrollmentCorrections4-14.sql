-- US18443: EP: Prod - Request for corrections from the Dual Enrollment corrections 4-14
-- For student 1
-- Math
-- Perf test
UPDATE studentstests set enrollmentid = 2313791,testsessionid = 2181694, modifieduser = 12, modifieddate = now()
      where id = 8918560;

--Stage-4
UPDATE studentstests set enrollmentid = 2313791,testsessionid = 2252997, modifieduser = 12, modifieddate = now()
      where id = 9626986;

--stage-3
UPDATE studentstests set enrollmentid = 2313791,testsessionid = 2397856, modifieduser = 12, modifieddate = now()
   where id = 12423114;

-- Stage-2
UPDATE studentstests set enrollmentid = 2313791,testsessionid = 2396755, modifieduser = 12, modifieddate = now()
   where id = 12387029;

-- Stage -1
UPDATE studentstests set enrollmentid = 2313791,testsessionid = 2252998, modifieduser = 12, modifieddate = now()
   where id = 10084131;   

--ELA
-- Perf test
UPDATE studentstests set enrollmentid = 2313791,testsessionid = 2181724, modifieduser = 12, modifieddate = now()
   where id = 9039112;

--Stage-1
UPDATE studentstests set enrollmentid = 2313791,testsessionid = 2263238, modifieduser = 12, modifieddate = now()
   where id = 10436450;

--Satge-2
UPDATE studentstests set enrollmentid = 2313791,testsessionid = 2485236, modifieduser = 12, modifieddate = now()
   where id = 13047414;

-- Activating enrollment
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Inactivating enrollments as per US18198 and reactivated according to US18443'
  where id = 2313791;



-- For student 3 (In both enrollments students tests are either unused or peniding so inactivating all studentstests from wrong school)

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = 12
       where studentstestsid in (SELECT id FROM studentstests where enrollmentid = 2199169);

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid in (SELECT id FROM studentstests where enrollmentid = 2199169);


update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where enrollmentid = 2199169;
        
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18443'
  where id = 2199169;


-- For student 2
--ELA
-- Perf test
UPDATE studentstests set enrollmentid = 1891112,testsessionid = 2176621, modifieduser = 12, modifieddate = now()
      where id = 8848370;

-- Stage-1
UPDATE studentstests set enrollmentid = 1891112,testsessionid = 2252138, modifieduser = 12, modifieddate = now()
      where id = 10098748;

-- Stage-2
UPDATE studentstests set enrollmentid = 1891112,testsessionid = 2502505, modifieduser = 12, modifieddate = now()
      where id = 12066350;
            
-- Stage-3
UPDATE studentstests set enrollmentid = 1891112,testsessionid = 2523948, modifieduser = 12, modifieddate = now()
      where id = 12141948;

-- Stage-4
UPDATE studentstests set enrollmentid = 1891112,testsessionid = 2252137, modifieduser = 12, modifieddate = now()
      where id = 9540366;

--Math
-- Perf
UPDATE studentstests set enrollmentid = 1891112,testsessionid = 2181433, modifieduser = 12, modifieddate = now()
      where id = 9025973;      

-- Stage-2
UPDATE studentstests set enrollmentid = 1891112,testsessionid = 2597608, modifieduser = 12, modifieddate = now()
      where id = 12325661;      

-- Stage-3
UPDATE studentstests set enrollmentid = 1891112,testsessionid = 2628731, modifieduser = 12, modifieddate = now()
      where id = 12639227;

-- Stage-4
UPDATE studentstests set enrollmentid = 1891112,testsessionid = 2251945, modifieduser = 12, modifieddate = now()
      where id = 9530223;

-- Inactivating stage1 of socialstudies as test status is unused.
update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = 12
       where studentstestsid = 10491950;

update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
          where studentstestid = 10491950;


update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
        where id = 10491950;            

-- Activating enrollment
UPDATE enrollment set activeflag = true, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = 0,  exitwithdrawaldate = null, notes = 'Inactivating enrollments as per US18198 and reactivated according to US18443'
  where id = 1891112;        
-- Inactivating active enrollment
UPDATE enrollment set activeflag = false, modifieduser = 12, modifieddate = now(), exitwithdrawaltype = -55,  exitwithdrawaldate = now(), notes = 'Inactivating enrollments as per US18443'
  where id = 2280192;
  