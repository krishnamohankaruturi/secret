-- US18107: EP: Prod - Request to reset KAP test per SEA

-- Inactivating all ELA stages for studentid = 1227694
update studentstests set activeflag = false, modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now() 
where studentid = 1227694 and 
testsessionid in (select id from testsession where name in ('2016_1834_Grade 7_English Language Arts_Stage 2', '2016_1834_Grade 7_English Language Arts_Stage 4', '2016_1834_Grade 7_English Language Arts_Stage 1'));

-- Records going to update : 3


update studentstestsections set activeflag = false, modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now()
     where studentstestid in 
       (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid where st.studentid = 1227694
          and ts.name in ('2016_1834_Grade 7_English Language Arts_Stage 2', '2016_1834_Grade 7_English Language Arts_Stage 4', '2016_1834_Grade 7_English Language Arts_Stage 1'));
                    
-- Records going to update : 9


update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in      
          (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid where st.studentid = 1227694
          and ts.name in ('2016_1834_Grade 7_English Language Arts_Stage 2', '2016_1834_Grade 7_English Language Arts_Stage 4', '2016_1834_Grade 7_English Language Arts_Stage 1'));

-- Records going to update : 25


--  Inactivating Stage-1, stage-2 and stage-3 tests for kid 1184154        
update studentstests set activeflag = false, modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now() 
where studentid = 1184154 and 
testsessionid in (select id from testsession where name in ('2016_0765_Grade 3_English Language Arts_Stage 2', '2016_0765_Grade 3_English Language Arts_Stage 1', '2016_0765_Grade 3_English Language Arts_Stage 3'));

-- Records going to update : 3


update studentstestsections set activeflag = false, modifieduser = (select id from aartuser where username = 'cetesysadmin'), modifieddate = now()
     where studentstestid in 
       (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid where st.studentid = 1184154
          and ts.name in ('2016_0765_Grade 3_English Language Arts_Stage 2', '2016_0765_Grade 3_English Language Arts_Stage 1', '2016_0765_Grade 3_English Language Arts_Stage 3'));
                    
-- Records going to update : 9


update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       where studentstestsid in      
          (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid where st.studentid = 1184154
          and ts.name in ('2016_0765_Grade 3_English Language Arts_Stage 2', '2016_0765_Grade 3_English Language Arts_Stage 1', '2016_0765_Grade 3_English Language Arts_Stage 3'));

-- Records going to update : 15          