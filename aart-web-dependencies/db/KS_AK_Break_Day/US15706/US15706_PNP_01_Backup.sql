CREATE TABLE IF NOT EXISTS ksdbprofile
(
  studentid bigint NOT NULL,
  selectedvalue text,
  profileitemattributenameattributecontainerid bigint,
  activeflag boolean,
  profileexits boolean NOT NULL
);

delete from ksdbprofile;

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
    PreferenceSubject int;
BEGIN
	RAISE NOTICE 'Student Profile Backup Started....';
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
	 PROFILEFOUND := 0;
	 RAISE NOTICE 'Student id = %', SRECS.ID;
 	 FOR SPATTR IN (select * from studentprofileitemattributevalue where studentid = SRECS.id and profileitemattributenameattributecontainerid in (UserSpokenPreference,SpokenSourcePreference,ReadAtStartPreference,DirectionsOnly,ActivateByDefault,AssignedSupport,PreferenceSubject))
	 LOOP
		RAISE NOTICE 'Attr Id: = %, value : = %', SPATTR.profileitemattributenameattributecontainerid, SPATTR.selectedvalue ;
		PROFILEFOUND := 1;
		INSERT INTO ksdbprofile VALUES(SRECS.id, SPATTR.selectedvalue, SPATTR.profileitemattributenameattributecontainerid, SPATTR.activeflag, true);
	 END LOOP;
	 
	 IF PROFILEFOUND = 0 THEN
	   RAISE NOTICE 'Profile not found for Student id : = %', SRECS.ID;
	   Insert into ksdbprofile values(SRECS.id, NULL, UserSpokenPreference, NULL, FALSE);
	   Insert into ksdbprofile values(SRECS.id, NULL, SpokenSourcePreference, NULL, FALSE);
	   Insert into ksdbprofile values(SRECS.id, NULL, ReadAtStartPreference, NULL, FALSE);
       Insert into ksdbprofile values(SRECS.id, NULL, DirectionsOnly, NULL, FALSE);
       Insert into ksdbprofile values(SRECS.id, NULL, ActivateByDefault, NULL, FALSE);
       Insert into ksdbprofile values(SRECS.id, NULL, AssignedSupport, NULL, FALSE);
       Insert into ksdbprofile values(SRECS.id, NULL, PreferenceSubject, NULL, FALSE);
	 END IF;
	END LOOP;
	RAISE NOTICE '....Student Profile Backup Completed';
END;
$BODY$;