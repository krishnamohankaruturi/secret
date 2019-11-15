-- Backup existing profile attributes for each student 
DO 
$BODY$
DECLARE
    SRECS RECORD;
    SPATTR RECORD;
    PROFILEFOUND int :=0;
    UserSpokenPreference int;
    SpokenSourcePreference int;
    ReadAtStartPreference int;
    DirectionsOnly int;
    ActivateByDefault int;
    AssignedSupport int;
BEGIN
	RAISE NOTICE 'Student Profile Backup Started....';
	select id into UserSpokenPreference from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'UserSpokenPreference') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into SpokenSourcePreference from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'SpokenSourcePreference') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into ReadAtStartPreference from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'ReadAtStartPreference') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into DirectionsOnly from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'directionsOnly') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into ActivateByDefault from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'activateByDefault') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	select id into AssignedSupport from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'assignedSupport') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');

	FOR SRECS IN (select st.id from student st join enrollment en on st.id = en.studentid and en.currentschoolyear = 2015 and st.assessmentprogramid is null and en.attendanceschoolid in (select id from organization_children((select org.id from organization org join organizationtype ot on org.organizationtypeid = ot.id where org.displayidentifier='KS' and org.activeflag is true and ot.typecode='ST')))) 
	LOOP
	 PROFILEFOUND := 0;
	 RAISE NOTICE 'Student id = %',SRECS.ID;
 	 FOR SPATTR IN (select * from studentprofileitemattributevalue where studentid = SRECS.id and profileitemattributenameattributecontainerid in (UserSpokenPreference,SpokenSourcePreference,ReadAtStartPreference,DirectionsOnly,ActivateByDefault,AssignedSupport))
	 LOOP
		RAISE NOTICE 'Attr Id: = %, value : = %',SPATTR.profileitemattributenameattributecontainerid, SPATTR.selectedvalue ;
		PROFILEFOUND :=1;
		insert into ksdbprofile values(SRECS.id, SPATTR.selectedvalue, SPATTR.profileitemattributenameattributecontainerid, SPATTR.activeflag, true);
	 end loop;
	 if PROFILEFOUND = 0 then
	   RAISE NOTICE 'Profile not found for Student id : = %',SRECS.ID;
	   Insert into ksdbprofile values(SRECS.id, NULL, UserSpokenPreference, NULL, FALSE);
	   Insert into ksdbprofile values(SRECS.id, NULL, SpokenSourcePreference, NULL, FALSE);
	   Insert into ksdbprofile values(SRECS.id, NULL, ReadAtStartPreference, NULL, FALSE);
       Insert into ksdbprofile values(SRECS.id, NULL, DirectionsOnly, NULL, FALSE);
       Insert into ksdbprofile values(SRECS.id, NULL, ActivateByDefault, NULL, FALSE);
       Insert into ksdbprofile values(SRECS.id, NULL, AssignedSupport, NULL, FALSE);
	 end if;
	END LOOP;
	RAISE NOTICE '....Student Profile Backup Completed';
END;
$BODY$;