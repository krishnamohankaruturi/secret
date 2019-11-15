-- US17629: SR Prod - Remove extra tests from student

-- Inactivating testsessions
update testsession set activeflag = false, status =(SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled'),
        modifieddate = now(), modifieduser = 12 where id in(
        select ts.id from enrollment en
        join student stu on stu.id =en.studentid
         join enrollmentsrosters enrl on enrl.enrollmentid = en.id
         join roster r on r.id = enrl.rosterid
         join ititestsessionhistory itih on itih.studentenrlrosterid = enrl.id
         join testsession ts on ts.id = itih.testsessionid
         join studentstests st on st.enrollmentid = en.id and ts.id = st.testsessionid
         where stu.statestudentidentifier = '1784875' and stu.stateid = 36560 and en.currentschoolyear = 2016 and enrl.activeflag is false
         and st.status != 86);
         
-- Records going to update: 11

         
-- Inactivating studentstests         
update studentstests set activeflag = false, status =(SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled'),
        modifieddate = now(), modifieduser = 12 where id in(
        select st.id from enrollment en
        join student stu on stu.id =en.studentid
         join enrollmentsrosters enrl on enrl.enrollmentid = en.id
         join roster r on r.id = enrl.rosterid
         join ititestsessionhistory itih on itih.studentenrlrosterid = enrl.id
         join testsession ts on ts.id = itih.testsessionid
         join studentstests st on st.enrollmentid = en.id and ts.id = st.testsessionid
         where stu.statestudentidentifier = '1784875' and stu.stateid = 36560 and en.currentschoolyear = 2016 and enrl.activeflag is false
         and st.status != 86);

-- Records going to update: 11

         
-- Inactivating ititestsessionhistory            
update ititestsessionhistory set activeflag = false, status =(SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled'),
        modifieddate = now(), modifieduser = 12 where id in(
        select itih.id from enrollment en
        join student stu on stu.id =en.studentid
         join enrollmentsrosters enrl on enrl.enrollmentid = en.id
         join roster r on r.id = enrl.rosterid
         join ititestsessionhistory itih on itih.studentenrlrosterid = enrl.id
         join testsession ts on ts.id = itih.testsessionid
         join studentstests st on st.enrollmentid = en.id and ts.id = st.testsessionid
         where stu.statestudentidentifier = '1784875' and stu.stateid = 36560 and en.currentschoolyear = 2016 and enrl.activeflag is false
         and st.status != 86);

-- Records going to update: 11      