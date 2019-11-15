-- DE15381:EP Prod: DLM Student Progress Report showing EEs as Planned when EE was already assessed
update ititestsessionhistory set activeflag = false, modifieddate = now(), modifieduser = 12, 
      status = (select id from category where categorycode = 'iticancel' and categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'))
where testsessionid in (select ts.id from studentstests st join testsession ts on ts.id = st.testsessionid  
	where st.enrollmentid = 2513918 and st.studentid = 1333670 and ts.source = 'ITI' 
	and st.status = (select id from category where categorycode = 'unused' 
		and categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'))); 



update testsession set activeflag = false, modifieddate = now(), modifieduser = 12, 
      status = (select id from category where categorycode = 'iticancel' and categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'))
where id in (select ts.id from studentstests st join testsession ts on ts.id = st.testsessionid  
	where st.enrollmentid = 2513918 and st.studentid = 1333670 and ts.source = 'ITI' 
	and st.status = (select id from category where categorycode = 'unused' 
		and categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS')));


update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12, 
      status = (select id from category where categorycode = 'iticancel' and categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'))
where id in (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid  
	where st.enrollmentid = 2513918 and st.studentid = 1333670 and ts.source = 'ITI' 
	and st.status = (select id from category where categorycode = 'unused' 
		and categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS')));