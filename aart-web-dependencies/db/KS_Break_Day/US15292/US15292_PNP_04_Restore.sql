-- Restore the backup Student Profile Data
DO 
$BODY$
DECLARE
    SRECS RECORD;
    SPATTR RECORD;
    PROFILEFOUND int;
    UserSpokenPreference int;
BEGIN
	RAISE NOTICE 'Restore Student Profile Started...';
	select id into UserSpokenPreference from profileitemattributenameattributecontainer where attributenameid = (select id from profileitemattribute where attributename = 'UserSpokenPreference') and attributecontainerid = (select id from profileitemattributecontainer where attributecontainer = 'Spoken');
	FOR SRECS IN (select * from ksdbprofile) 
	LOOP
	 if(SRECS.profileexits) then
	   RAISE NOTICE 'Updating Student id - % for attribute - %',  SRECS.studentid, SRECS.profileitemattributenameattributecontainerid ;
	   update studentprofileitemattributevalue set selectedvalue = SRECS.selectedvalue where profileitemattributenameattributecontainerid = SRECS.profileitemattributenameattributecontainerid and studentid = SRECS.studentid;
	 else
	   RAISE NOTICE 'Deleting Student id - % for attribute - %',  SRECS.studentid, SRECS.profileitemattributenameattributecontainerid ;
	   delete from 	studentprofileitemattributevalue where studentid = SRECS.studentid and profileitemattributenameattributecontainerid = SRECS.profileitemattributenameattributecontainerid;
	 end if;
	END LOOP;
	RAISE NOTICE '....Restore Student Profile Completed.';

 END;
$BODY$;
