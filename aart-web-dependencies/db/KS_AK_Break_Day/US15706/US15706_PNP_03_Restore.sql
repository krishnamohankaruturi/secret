-- Restore the backup Student Profile Data
DO 
$BODY$
DECLARE
    SRECS RECORD;

BEGIN
	RAISE NOTICE 'Restore Student Profile Started...';

	FOR SRECS IN (select * from ksdbprofile) 
	LOOP
	 IF(SRECS.profileexits) THEN
	   RAISE NOTICE 'Updating Student id - % for attribute - %',  SRECS.studentid, SRECS.profileitemattributenameattributecontainerid ;
	   update studentprofileitemattributevalue set selectedvalue = SRECS.selectedvalue where profileitemattributenameattributecontainerid = SRECS.profileitemattributenameattributecontainerid and studentid = SRECS.studentid;
	 ELSE
	   RAISE NOTICE 'Deleting Student id - % for attribute - %',  SRECS.studentid, SRECS.profileitemattributenameattributecontainerid ;
	   delete from 	studentprofileitemattributevalue where studentid = SRECS.studentid and profileitemattributenameattributecontainerid = SRECS.profileitemattributenameattributecontainerid;
	 END IF;
	END LOOP;
	RAISE NOTICE '....Restore Student Profile Completed.';

 END;
$BODY$;
