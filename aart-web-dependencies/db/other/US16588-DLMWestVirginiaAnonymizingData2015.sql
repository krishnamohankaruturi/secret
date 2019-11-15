-- US16588 - QA Only: DLM West Virginia Anonymizing Data

begin;
Do $$

DECLARE
STUDENT_ROW RECORD;
ENRL_ROW RECORD;
ROSTER_ROW RECORD;
ENRL_ROSTER_ROW RECORD;
TEST_SESSION_ROW RECORD;

BEGIN

For STUDENT_ROW IN

select distinct st.studentid as id
	from studentstests st 
	join student s on s.id = st.studentid and s.stateid=9590
	join testsession ts on st.testsessionid = ts.id
	join enrollment en on en.id = st.enrollmentid and en.currentschoolyear = 2015
	join operationaltestwindow o on ts.operationaltestwindowid = o.id 
where ((o.effectivedate = '2015-01-01 01:11:11+00' and o.expirydate = '2015-03-07 04:59:59+00') 
or (o.effectivedate = '2014-09-30 02:06:00+00' and o.expirydate = '2014-12-21 04:59:59+00')) and ts.source != 'ITI' 

loop

Raise Notice 'Anonymizing studentid %', STUDENT_ROW.id;

-- Insert new student row
insert into student (statestudentidentifier, legalfirstname, legalmiddlename, legallastname, dateofbirth, createddate, modifieddate, gender, firstlanguage, comprehensiverace, primarydisabilitycode, username, password, synced, createduser, activeflag, modifieduser, hispanicethnicity, commbandid, elabandid, finalelabandid, mathbandid, finalmathbandid, source, usaentrydate, esolparticipationcode, esolprogramendingdate, esolprogramentrydate, profilestatus, stateid)

select statestudentidentifier, 'First', 'M', 'Last', dateofbirth, createddate, modifieddate, gender, firstlanguage, comprehensiverace, primarydisabilitycode, STUDENT_ROW.id, 'password', synced, createduser, activeflag, modifieduser, hispanicethnicity, commbandid, elabandid, finalelabandid, mathbandid, finalmathbandid, source, usaentrydate, esolparticipationcode, esolprogramendingdate, esolprogramentrydate, profilestatus, 58550 from student where id = STUDENT_ROW.id ;

-- insert studentassessmentprogram
insert into studentassessmentprogram(studentid,assessmentprogramid,activeflag) 
	values(curRval('student_id_seq'),(select id from assessmentprogram where abbreviatedname='DLM' and activeflag is true), true);

	for ENRL_ROW IN
		select distinct en.* from studentstests st
		join testsession ts on st.testsessionid = ts.id
		join enrollment en on en.id = st.enrollmentid and en.currentschoolyear = 2015
		join operationaltestwindow o on ts.operationaltestwindowid = o.id 
		where ((o.effectivedate = '2015-01-01 01:11:11+00' and o.expirydate = '2015-03-07 04:59:59+00') 
		or (o.effectivedate = '2014-09-30 02:06:00+00' and o.expirydate = '2014-12-21 04:59:59+00')) and ts.source != 'ITI'
		and en.activeflag is true and en.studentid = STUDENT_ROW.id
	loop
		-- Insert new enrollment
		insert into enrollment (aypschoolidentifier, residencedistrictidentifier, currentgradelevel, localstudentidentifier, currentschoolyear, fundingschool, schoolentrydate, 
		districtentrydate, stateentrydate, exitwithdrawaldate, exitwithdrawaltype, specialcircumstancestransferchoice, giftedstudent, specialedprogramendingdate, qualifiedfor504, 
		studentid, attendanceschoolid, restrictionid, createddate, createduser, activeflag, modifieddate, modifieduser, source, aypschoolid)

		select 'ANON101', 'ANON100', currentgradelevel, '', currentschoolyear, '', schoolentrydate, districtentrydate, stateentrydate, exitwithdrawaldate, exitwithdrawaltype, 
		specialcircumstancestransferchoice, giftedstudent, specialedprogramendingdate, qualifiedfor504, curRval('student_id_seq'), (select id from organization where displayidentifier='ANON101' and organizationtypeid=7), restrictionid, createddate, createduser, 
		activeflag, modifieddate, modifieduser, source, (select id from organization where displayidentifier='ANON101' and organizationtypeid=7) from enrollment where id=ENRL_ROW.id;

		for ROSTER_ROW IN
			select distinct ts.rosterid
				from studentstests st 
				join student s on s.id = st.studentid and s.stateid=9590
				join testsession ts on st.testsessionid = ts.id
				join enrollment en on en.id = st.enrollmentid and en.currentschoolyear = 2015
				join operationaltestwindow o on ts.operationaltestwindowid = o.id 
			where ((o.effectivedate = '2015-01-01 01:11:11+00' and o.expirydate = '2015-03-07 04:59:59+00') 
			or (o.effectivedate = '2014-09-30 02:06:00+00' and o.expirydate = '2014-12-21 04:59:59+00')) and ts.source != 'ITI'
			and st.enrollmentid=ENRL_ROW.id
		loop	

			IF ((select count(*) from roster where tempoldrid=ROSTER_ROW.rosterid) = 0) THEN
			
				INSERT INTO roster(coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
					    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
					    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
					    localcourseid, attendanceschoolid, 
					    prevstatesubjectareaid, statecoursecode, source, sourcetype, statecoursesid, currentschoolyear, aypschoolid, tempoldrid)

				   select coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
					    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
					    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
					    localcourseid, (select id from organization where displayidentifier='ANON101' and organizationtypeid=7), 
					    prevstatesubjectareaid, statecoursecode, source, sourcetype, statecoursesid, 2015, aypschoolid, ROSTER_ROW.rosterid           
				    from roster where id=ROSTER_ROW.rosterid;
			END IF;
			
			for ENRL_ROSTER_ROW IN
				select enrs.* from enrollmentsrosters enrs where enrs.enrollmentid= ENRL_ROW.id and rosterid=ROSTER_ROW.rosterid
			loop
				INSERT INTO enrollmentsrosters(enrollmentid, rosterid, createddate, createduser, activeflag, 
				modifieddate, modifieduser, courseenrollmentstatusid, source, trackerstatus)

				select curRval('enrollment_id_seq'), (select id from roster where tempoldrid=ROSTER_ROW.rosterid), createddate, createduser, activeflag, 
					modifieddate, modifieduser, courseenrollmentstatusid, source, trackerstatus 
				from enrollmentsrosters where id=ENRL_ROSTER_ROW.id;
			end loop; --ENRL_ROSTER_ROW		

			for TEST_SESSION_ROW IN
				select distinct ts.* from studentstests st
				join testsession ts on st.testsessionid = ts.id
				join operationaltestwindow o on ts.operationaltestwindowid = o.id 
				where ((o.effectivedate = '2015-01-01 01:11:11+00' and o.expirydate = '2015-03-07 04:59:59+00') 
				or (o.effectivedate = '2014-09-30 02:06:00+00' and o.expirydate = '2014-12-21 04:59:59+00')) and ts.source != 'ITI'
				and st.studentid = STUDENT_ROW.id and st.enrollmentid=ENRL_ROW.id and ts.rosterid=ROSTER_ROW.rosterid			
			loop
				-- Update appropriate students responses
				update studentsresponses set studentid = curRval('student_id_seq') 
					where studentstestsid in (select id from studentstests where testsessionid=TEST_SESSION_ROW.id and enrollmentid=ENRL_ROW.id);

				--update enrollmentid on studentstests
				update studentstests set studentid = curRval('student_id_seq'),
							enrollmentid=curRval('enrollment_id_seq')
				 where id in (select id from studentstests where testsessionid=TEST_SESSION_ROW.id and enrollmentid=ENRL_ROW.id);
				 					
				--update attendanceschoolid, rosterid on testsession
				update testsession set rosterid=(select id from roster where tempoldrid=ROSTER_ROW.rosterid),
					attendanceschoolid=(select id from organization where displayidentifier='ANON101' and organizationtypeid=7)
				 where id = TEST_SESSION_ROW.id;
				 
			end loop; --TEST_SESSION_ROW
		end loop; --ROSTER_ROW
	end loop; --ENRL_ROW

-- Update new student row username and statestudentidentifier
update student set username = 'username' || id, statestudentidentifier = id  where id = curRval('student_id_seq');

end loop; --STUDENT_ROW
end $$;

commit;