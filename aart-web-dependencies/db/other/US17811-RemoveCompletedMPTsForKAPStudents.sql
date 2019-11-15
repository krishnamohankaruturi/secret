-- US17811: SR: EP High - Prod - Remove Completed MPTs for KAP Students

-- Inactivating the completed MPT tests for Student 1:4534046545 Kailyn Younger and Student 2: 8771609644 Keona Nash from District: Paola and School: Paola Middle (4694)

DO
$BODY$
DECLARE
	studentsTestsRecords RECORD;
	numOf_studentsTests_updated INTEGER;	
	data TEXT[][][] := ARRAY[
		['4534046545', '4694', 'M'],
		['8771609644', '4694', 'M']
	];
BEGIN
    FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP

        FOR studentsTestsRecords IN (select st.* from studentstests st join enrollment en on en.id = st.enrollmentid
					join student stu on stu.id = en.studentid 
					join organization attsch on attsch.id = en.attendanceschoolid
					join testcollection tc on tc.id = st.testcollectionid
					where stu.statestudentidentifier = data[i][1]
					and stu.stateid = (select id from organization where displayidentifier = 'KS')
					and en.currentschoolyear =2016 and attsch.displayidentifier = data[i][2]
					and tc.contentareaid in (select id from contentarea where abbreviatedname = data[i][3])
					and st.status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'complete')) LOOP

		WITH studentsRecordsUpdated_rows AS (UPDATE studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = studentsTestsRecords.id 
		returning 1) SELECT count(*) FROM studentsRecordsUpdated_rows INTO numOf_studentsTests_updated;
                IF numOf_studentsTests_updated = 1 THEN
			RAISE NOTICE 'Student testid: % is set to inactive as per US17811. Student identifier: % , Attenance school: % and Subject: %', studentsTestsRecords.id, data[i][1],data[i][2], data[i][3];
		END IF;
		UPDATE studentstestsections set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin') where studentstestid = studentsTestsRecords.id;
      END LOOP;
    END LOOP;
END;
$BODY$;



-- Moving the MDPT & MPT tests for  4395271502 from school 8004 to 5556
-- Moving MPT test
update studentstests set enrollmentid = 2349853, testsessionid = 2175351, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
        where id = 8732400;
        
update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8841238;

-- Moving MDPT test
update studentstests set enrollmentid = 2349853, testsessionid = 2175362, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where id = 8764310;
       
update  studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8883725;
	

	

-- 	Moving the MDPT Test for 5630363174 from school 7986 to school 8002
update studentstests set enrollmentid = 2364307, testsessionid = 2177000,modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
        where id = 9156862; 
	
update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9193715;
	
	
	

-- 	Moving the MPT Test for 3499849267 from school 1892 to school 1798
update studentstests set enrollmentid = 2239139, testsessionid = 2182545, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where id = 8918483;

update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id =9005454;
	
	
	
-- Moving MPT & MDPT tests for 1533593892 from school 0770 to school 9194
-- Moving MPT test
update studentstests set enrollmentid = 2279534, testsessionid = 2180227, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
     where id = 8972146;
     
update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin')   where id = 9012878;
	
-- Moving MDPT test
update studentstests set enrollmentid = 2279534, testsessionid = 2175148, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where id = 8773372;
      
update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin')   where id = 8855972;
	
	

-- Moving MDPT and MPT tests for 8547364498 from school 1558 to school 1596
-- Moving MDPT test
update studentstests set enrollmentid = 2348944, testsessionid = 2176696, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where id = 8719850;

update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin')   where id = 8886626;
	
	
	
-- Moving MPT test for 6827014495 from school 0290 to school 0288
-- KID has MPT test only in 0290 school, so moving the studenttest to school 0288 with the grde 5 testsession 2016_0288_Grade 5_Mathematics_Performance.
update studentstests set enrollmentid = 2143689, testsessionid = 2181221, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
     where id = 8890166;
     
     
     
-- Moving MPT test for 3055842219 from school 0290 to school 0288
-- KID has MPT test only in 0290 school, so moving the studenttest to school 0288 with the grde 6 testsession 2016_0288_Grade 6_Mathematics_Performance
update studentstests set enrollmentid = 2143688, testsessionid = 2181312, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where id = 8893546;

      
      
-- Moving MDPT & MPT tests for 8004080839 from school 4762 to school 0482
-- KID has MPT test only in 4762 school, so moving the studenttest to school 0482 with grade 4 testsession 2016_0482_Grade 4_Mathematics_Performance
update studentstests set enrollmentid = 2363990, testsessionid = 2178019, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where id = 8579469;
      
-- KID has MDPT test only in 4762 school, so moving the studenttest to school 0482 with grade 4 testsession 2016_0482_Grade 4_English Language Arts_Performance
update studentstests set enrollmentid = 2363990, testsessionid = 2178476,  modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where id = 8591728;
       
       

       

-- Moving MDPT & MPT tests for 1297885392 from school 4762 to school 0482
-- KID has MPT test only in 4762 school, so moving the studenttest to school 0482 with grade 4 testsession 2016_0482_Grade 4_Mathematics_Performance
update studentstests set enrollmentid = 2363989, testsessionid = 2178019, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where id = 8579461;
    
-- KID has MDPT test only in 4762 school, so moving the studenttest to school 0482 with grade 4 testsession 2016_0482_Grade 4_English Language Arts_Performance
update studentstests set enrollmentid = 2363989, testsessionid = 2178476,  modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where id = 8591716;
       

       

       
-- Moving MDPT for 8771609644 from school 4694 to school 0792
update studentstests set enrollmentid = 2269155, testsessionid = 2175868, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where id = 8663912;

update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9135336;
	
	
	
	
-- Moving MDPT for 4534046545 from school 4694 to school 0792
update studentstests set enrollmentid = 2269170, testsessionid = 2175868,  modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where id = 8593457;

update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9135338;
	
	

	
	
-- 	Moving MDPT& MPT tests for 3422647465 from school 4022 to school 4029
-- Moving MPT tests
update studentstests set enrollmentid = 2133618, testsessionid = 2181731,  modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where id = 8911643;
    
update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 8969198;
	

	
-- Moving MDPT test for 2428233688 from school 1886 to school 1706
update studentstests set enrollmentid = 2362219, testsessionid = 2178199, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
    where id = 8635014;
    
update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9090021; 
	
	
	
-- Moving MDPT and MPT tests for 4534046545 from school 4694 to school 0792
-- Updating MDPT tests
update studentstests set enrollmentid = 2269170, testsessionid = 2175868, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
     where id = 8593457;
     
update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9135338;
	
	

	
-- Moving MDPT and MPT tests for 8771609644 from school 4694 to school 0792
-- updating MDPT tests
update studentstests set enrollmentid = 2269155, testsessionid = 2175868, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where id = 8663912;
       
update studentstests set  activeflag = false, modifieddate = now(),
	modifieduser = (select id from aartuser where username = 'cetesysadmin') where id = 9135336;    