-- US18109: Test Removal for KAP Students - Multiple Tests (Performance Tasks)

-- Inactivating the MDPT test for studetid 42347 in school 1886. There were no MPT tests found for this kid in school 1886.
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where id in (select st.id from enrollment en join studentstests st on st.enrollmentid = en.id join testsession ts on ts.id = st.testsessionid
              where en.studentid = 42347 and en.attendanceschoolid = (select schoolid from organizationtreedetail where schooldisplayidentifier = '1886' and statedisplayidentifier = 'KS')
               and en.currentschoolyear = 2016 and ts.name = '2016_1886_Grade 7_English Language Arts_Performance');

-- Records going to update : 1

update studentstestsections set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where studentstestid in (select st.id from enrollment en join studentstests st on st.enrollmentid = en.id join testsession ts on ts.id = st.testsessionid
              where en.studentid = 42347 and en.attendanceschoolid = (select schoolid from organizationtreedetail where schooldisplayidentifier = '1886' and statedisplayidentifier = 'KS')
               and en.currentschoolyear = 2016 and ts.name = '2016_1886_Grade 7_English Language Arts_Performance');
-- Records going to update : 2
               

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where studentstestsid in (select st.id from enrollment en join studentstests st on st.enrollmentid = en.id join testsession ts on ts.id = st.testsessionid
              where en.studentid = 42347 and en.attendanceschoolid = (select schoolid from organizationtreedetail where schooldisplayidentifier = '1886' and statedisplayidentifier = 'KS')
               and en.currentschoolyear = 2016 and ts.name = '2016_1886_Grade 7_English Language Arts_Performance');
-- Records going to update : 1



-- Inactivating the MDPT test for studetid 331758 in school 1886.  Leaved the MPT test in School 1886.
update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where id in (select st.id from enrollment en join studentstests st on st.enrollmentid = en.id join testsession ts on ts.id = st.testsessionid
              where en.studentid = 331758 and en.attendanceschoolid = (select schoolid from organizationtreedetail where schooldisplayidentifier = '1886' and statedisplayidentifier = 'KS')
               and en.currentschoolyear = 2016 and ts.name = '2016_1886_Grade 7_English Language Arts_Performance');

-- Records going to update : 1


update studentstestsections set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where studentstestid in (select st.id from enrollment en join studentstests st on st.enrollmentid = en.id join testsession ts on ts.id = st.testsessionid
              where en.studentid = 331758 and en.attendanceschoolid = (select schoolid from organizationtreedetail where schooldisplayidentifier = '1886' and statedisplayidentifier = 'KS')
               and en.currentschoolyear = 2016 and ts.name = '2016_1886_Grade 7_English Language Arts_Performance');

-- Records going to update : 2            

update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
      where studentstestsid in (select st.id from enrollment en join studentstests st on st.enrollmentid = en.id join testsession ts on ts.id = st.testsessionid
              where en.studentid = 331758 and en.attendanceschoolid = (select schoolid from organizationtreedetail where schooldisplayidentifier = '1886' and statedisplayidentifier = 'KS')
               and en.currentschoolyear = 2016 and ts.name = '2016_1886_Grade 7_English Language Arts_Performance');
-- Records going to update : 0 
               