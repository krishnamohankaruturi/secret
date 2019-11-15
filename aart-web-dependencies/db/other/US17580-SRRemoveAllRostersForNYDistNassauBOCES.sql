-- US17580: SR Remove All Rosters for NY Dist Nassau BOCES
-- Inactivating all the studentstests, studenttestsections, testsessions, itiplans, rosters, enrollmentsrosters for 
--- Dist- BOCES - 000000000009
-- School Dist- Nassau - 289000000000

-- Verified the studenttestsession as it is in "unused" status, changing the status to "rosterunenrolled".

update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP, modifieduser = (select id from aartuser where username='cetesysadmin'),
      statusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'rosterunenrolled')       
     where studentstestid in (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid join roster r on r.id = ts.rosterid
                               where r.attendanceschoolid in (select schoolid from organizationtreedetail where schooldisplayidentifier = '289000000000' 
                               and lower(schoolname) = lower('NASSAU BOCES') and statedisplayidentifier = 'NY'));
-- Records going to update: 1


update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP, modifieduser = (select id from aartuser where username='cetesysadmin'),
      status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled') 
     where id in (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid join roster r on r.id = ts.rosterid
                               where r.attendanceschoolid in (select schoolid from organizationtreedetail where schooldisplayidentifier = '289000000000' 
                                          and lower(schoolname) = lower('NASSAU BOCES') and statedisplayidentifier = 'NY'));
-- Records going to update: 1


update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP, modifieduser = (select id from aartuser where username='cetesysadmin'),
      status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled') 
     where id in (select ts.id from testsession ts join roster r on r.id = ts.rosterid
                               where r.attendanceschoolid in (select schoolid from organizationtreedetail where schooldisplayidentifier = '289000000000' 
					and lower(schoolname) = lower('NASSAU BOCES') and statedisplayidentifier = 'NY'));

-- Records going to update: 1



update ititestsessionhistory set activeflag=false,modifieddate=CURRENT_TIMESTAMP, modifieduser = (select id from aartuser where username='cetesysadmin'),
      status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'rosterunenrolled')       
     where rosterid in (select id from roster where attendanceschoolid in (select schoolid from organizationtreedetail where schooldisplayidentifier = '289000000000' 
		and lower(schoolname) = lower('NASSAU BOCES') and statedisplayidentifier = 'NY'));                               

-- Records going to update: 2


update enrollmentsrosters set activeflag=false,modifieddate=CURRENT_TIMESTAMP, modifieduser = (select id from aartuser where username='cetesysadmin')      
       where rosterid in (select id from roster where attendanceschoolid in (select schoolid from organizationtreedetail where 
				lower(schoolname) = lower('NASSAU BOCES') and schooldisplayidentifier = '289000000000' and statedisplayidentifier = 'NY'));

-- Records going to update: 2436


update roster set activeflag=false,modifieddate=CURRENT_TIMESTAMP, modifieduser = (select id from aartuser where username='cetesysadmin'), coursesectionname = 'zzdonotuse', 
   notes = 'Inactivating roster as per US17580'
   where attendanceschoolid in (select schoolid from organizationtreedetail where schooldisplayidentifier = '289000000000' 
	and lower(schoolname) = lower('NASSAU BOCES') and statedisplayidentifier = 'NY');

-- Records going to update: 447   
       