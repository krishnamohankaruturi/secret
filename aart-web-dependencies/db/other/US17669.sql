
--input all applicable windows;
update studentstestsections set statusid = (select c.id from category c, categorytype ct where c.categorytypeid = ct.id 
			and c.categorycode='complete' and ct.typecode='STUDENT_TESTSECTION_STATUS'),modifieddate = now(),
		modifieduser = (select id from aartuser where username='cetesysadmin'),
		manualupdatereason='US17669:forcecompleteafterwindow'
 where studentstestid in (select id from studentstests where testsessionid in (select id from testsession where operationaltestwindowid in (<1>,<2>)));

update studentstests set status=(select c.id from category c, categorytype ct where c.categorytypeid = ct.id 
			and c.categorycode='complete' and ct.typecode='STUDENT_TEST_STATUS'),modifieddate = now(),
		modifieduser = (select id from aartuser where username='cetesysadmin'),
		manualupdatereason='US17669:forcecompleteafterwindow'
	where id in (select id from studentstests where testsessionid in (select id from testsession where operationaltestwindowid in (<1>,<2>));
	