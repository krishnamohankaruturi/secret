-- Update profile attributes for each student with values
DO 
$BODY$
DECLARE
    SRECS RECORD;
    SPATTR RECORD;
    PROFILEFOUND int;
    UserSpokenPreference int;
    SpokenSourcePreference int;
    ReadAtStartPreference int;
    DirectionsOnly int;
    ActivateByDefault int;
    AssignedSupport int;
    PreferenceSubject int;
BEGIN
	RAISE NOTICE 'Update Student Profile Started...';
	select id into UserSpokenPreference from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'UserSpokenPreference') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into SpokenSourcePreference from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'SpokenSourcePreference') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into ReadAtStartPreference from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'ReadAtStartPreference') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into DirectionsOnly from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'directionsOnly') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into ActivateByDefault from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'activateByDefault') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into AssignedSupport from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'assignedSupport') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into PreferenceSubject from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'preferenceSubject') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	
	FOR SRECS IN (
		select distinct st.id from student st 
				join enrollment en on (st.id = en.studentid and st.assessmentprogramid is null)
				where en.currentschoolyear = 2015
				and en.attendanceschoolid in (select id from organization_children((select org.id from organization org join organizationtype ot on org.organizationtypeid = ot.id where org.displayidentifier='AK' and org.activeflag is true and ot.typecode='ST')))	
		) 
	LOOP
	 select count(*) into PROFILEFOUND from studentprofileitemattributevalue where studentid = SRECS.id;
	 IF PROFILEFOUND > 0 THEN
	   RAISE NOTICE 'PROFILEFOUND..Updating for student - %', SRECS.id;
	   update studentprofileitemattributevalue set selectedvalue = 'textandgraphics' where profileitemattributenameattributecontainerid = UserSpokenPreference and  studentid = SRECS.id;
	   update studentprofileitemattributevalue set selectedvalue = 'synthetic' where profileitemattributenameattributecontainerid = SpokenSourcePreference and  studentid = SRECS.id;
	   update studentprofileitemattributevalue set selectedvalue = 'false' where profileitemattributenameattributecontainerid = ReadAtStartPreference and  studentid = SRECS.id;
	   update studentprofileitemattributevalue set selectedvalue = 'false' where profileitemattributenameattributecontainerid = DirectionsOnly and  studentid = SRECS.id;
	   update studentprofileitemattributevalue set selectedvalue = 'true' where profileitemattributenameattributecontainerid = ActivateByDefault and  studentid = SRECS.id;
	   update studentprofileitemattributevalue set selectedvalue = 'true' where profileitemattributenameattributecontainerid = AssignedSupport and  studentid = SRECS.id;
	   update studentprofileitemattributevalue set selectedvalue = 'math_and_ela' where profileitemattributenameattributecontainerid = PreferenceSubject and  studentid = SRECS.id;
	 ELSE
	   RAISE NOTICE 'PROFILE NOT FOUND..Inserting for student - %', SRECS.id;
	   insert into studentprofileitemattributevalue(selectedvalue, createduser, activeflag, modifieduser, studentid, profileitemattributenameattributecontainerid) 
	    values('textandgraphics', (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), SRECS.id, UserSpokenPreference );
	   insert into studentprofileitemattributevalue(selectedvalue, createduser, activeflag, modifieduser, studentid, profileitemattributenameattributecontainerid) 
	    values('synthetic', (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), SRECS.id, SpokenSourcePreference );
	   insert into studentprofileitemattributevalue(selectedvalue, createduser, activeflag, modifieduser, studentid, profileitemattributenameattributecontainerid) 
	    values('false', (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), SRECS.id, ReadAtStartPreference );
	   insert into studentprofileitemattributevalue(selectedvalue, createduser, activeflag, modifieduser, studentid, profileitemattributenameattributecontainerid) 
	    values('false', (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), SRECS.id, DirectionsOnly );
	   insert into studentprofileitemattributevalue(selectedvalue, createduser, activeflag, modifieduser, studentid, profileitemattributenameattributecontainerid) 
	    values('true', (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), SRECS.id, ActivateByDefault );
	   insert into studentprofileitemattributevalue(selectedvalue, createduser, activeflag, modifieduser, studentid, profileitemattributenameattributecontainerid) 
	    values('true', (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), SRECS.id, AssignedSupport );	    
	   insert into studentprofileitemattributevalue(selectedvalue, createduser, activeflag, modifieduser, studentid, profileitemattributenameattributecontainerid) 
	    values('math_and_ela', (select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'), SRECS.id, PreferenceSubject );	    	    
	 END IF;
	END LOOP;
	RAISE NOTICE '....Update Student Profile Completed.';

 END;
$BODY$;