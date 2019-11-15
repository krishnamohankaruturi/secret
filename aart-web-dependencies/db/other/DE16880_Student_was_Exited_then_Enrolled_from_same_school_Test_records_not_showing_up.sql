-- DE16880: Student was Exited then Enrolled from same school Test records not showing up
update studentsresponses set activeflag = true, modifieduser = 12, modifieddate = now() 
where studentstestsid in (select id from studentstests where enrollmentid = 3145134 
			and status = (select id from category where categorycode = 'exitclearunenrolled-complete' 
				and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS')) and activeflag is false) and activeflag is false;

update studentstestsections set activeflag = true, modifieddate = now(), modifieduser = 12,
	statusid = (select id from category where categorycode = 'complete' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TESTSECTION_STATUS'))
where studentstestid in (select id from studentstests where enrollmentid = 3145134 
			and status = (select id from category where categorycode = 'exitclearunenrolled-complete' 
				and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS')) and activeflag is false)
and statusid = (select id from category where categorycode = 'exitclearunenrolled-complete' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TESTSECTION_STATUS'))
and activeflag is false;


update ititestsessionhistory set activeflag = true, modifieddate = now(), modifieduser = 12,
     status = (select id from category where categorycode = 'unused' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS'))
where testsessionid in (select testsessionid from studentstests where enrollmentid = 3145134 
			and status = (select id from category where categorycode = 'exitclearunenrolled-complete' 
				and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS')) and activeflag is false)
and activeflag is false;


update testsession set activeflag = true, modifieddate = now(), modifieduser = 12,
	status = (select id from category where categorycode = 'unused' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS'))
where id in (select testsessionid from studentstests where enrollmentid = 3145134 
			and status = (select id from category where categorycode = 'exitclearunenrolled-complete' 
				and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS')) and activeflag is false)
and status = (select id from category where categorycode = 'exitclearunenrolled-unused' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS'))
and activeflag is false;

update studentstests set activeflag = true, modifieddate = now(), modifieduser = 12,
     status = (select id from category where categorycode = 'complete' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS'))
where enrollmentid = 3145134
and status = (select id from category where categorycode = 'exitclearunenrolled-complete' 
				and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS')) 
and activeflag is false ;


-- DE16880: Student was Exited then Enrolled from same school Test records not showing up (Second kid - Lawrence School District, Lawrence Southwest Middle School)
update studentsresponses set activeflag = true, modifieduser = 12, modifieddate = now() 
where studentstestsid in (select id from studentstests where enrollmentid = 2952868 
			and status = (select id from category where categorycode = 'exitclearunenrolled-complete' 
				and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS')) and activeflag is false) and activeflag is false;

update studentstestsections set activeflag = true, modifieddate = now(), modifieduser = 12,
	statusid = (select id from category where categorycode = 'complete' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TESTSECTION_STATUS'))
where studentstestid in (select id from studentstests where enrollmentid = 2952868 
			and status = (select id from category where categorycode = 'exitclearunenrolled-complete' 
				and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS')) and activeflag is false)
and statusid = (select id from category where categorycode = 'exitclearunenrolled-complete' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TESTSECTION_STATUS'))
and activeflag is false;


update ititestsessionhistory set activeflag = true, modifieddate = now(), modifieduser = 12,
     status = (select id from category where categorycode = 'unused' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS'))
where testsessionid in (select testsessionid from studentstests where enrollmentid = 2952868 
			and status = (select id from category where categorycode = 'exitclearunenrolled-complete' 
				and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS')) and activeflag is false)
and activeflag is false;


update testsession set activeflag = true, modifieddate = now(), modifieduser = 12,
	status = (select id from category where categorycode = 'unused' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS'))
where id in (select testsessionid from studentstests where enrollmentid = 2952868 
			and status = (select id from category where categorycode = 'exitclearunenrolled-complete' 
				and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS')) and activeflag is false)
and status = (select id from category where categorycode = 'exitclearunenrolled-unused' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS'))
and activeflag is false;

update studentstests set activeflag = true, modifieddate = now(), modifieduser = 12,
     status = (select id from category where categorycode = 'complete' and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS'))
where enrollmentid = 2952868
and status = (select id from category where categorycode = 'exitclearunenrolled-complete' 
				and categorytypeid = (select id from categorytype where typecode= 'STUDENT_TEST_STATUS')) 
and activeflag is false ;