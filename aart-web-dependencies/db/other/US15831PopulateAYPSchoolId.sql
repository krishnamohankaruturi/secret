----US15831 : Manage Accountability(AYP) school correctly on KSDE Test records

-- populate aypschoolid on all enrollments with no aypidentifier or no stateid on student
-- just copy attendanceschoolid to aypschoolid
DO 
$BODY$
DECLARE 
 AYPSCHOOL_ATTENDANCESCHOOL RECORD;
BEGIN
    FOR AYPSCHOOL_ATTENDANCESCHOOL IN (
		 select en.id, en.attendanceschoolid 
		 from enrollment en
		 INNER JOIN Student stu ON (en.studentid = stu.id)
		 where (stu.stateid is null OR en.aypschoolidentifier is null) 
	 ) LOOP

	UPDATE enrollment SET aypschoolid = AYPSCHOOL_ATTENDANCESCHOOL.attendanceschoolid  where id = AYPSCHOOL_ATTENDANCESCHOOL.id;
    END LOOP;
 END;
 $BODY$;
 
 
-- Now For each state populate aypschoolid based on the aypschoolidentifier populated on the enrollment/student state
DO 
$BODY$
DECLARE 
	EP_ENROLL_STATES RECORD;
	STATE_ENROLLMENTS RECORD;
	
BEGIN

	FOR EP_ENROLL_STATES IN (
		select distinct stateid  
			from enrollment en join student st on st.id = en.studentid where stateid is not null
	) LOOP
	
		FOR STATE_ENROLLMENTS IN (
			 select en.id, en.attendanceschoolid, en.aypschoolidentifier, en.aypschoolid, kssch.displayidentifier, kssch.id as newaypschoolid
			 from enrollment en
			 inner join Student stu on (en.studentid = stu.id and stu.stateid = EP_ENROLL_STATES.stateid)
			 join (select * from organization_children_oftype(EP_ENROLL_STATES.stateid, 'SCH')) kssch on (en.aypschoolidentifier = kssch.displayidentifier)
			 where en.aypschoolid is null	
		) LOOP

		    UPDATE enrollment SET aypschoolid = (CASE WHEN STATE_ENROLLMENTS.newaypschoolid IS NULL 
								THEN 
									STATE_ENROLLMENTS.attendanceschoolid	
								ELSE 
									STATE_ENROLLMENTS.newaypschoolid 
								END) 
				WHERE id = STATE_ENROLLMENTS.id;
		END LOOP;

	END LOOP;
END;
$BODY$;

-- Now for the remaining enrollments with still aypschoolid as null just copy attendanceschoolid to aypschoolid
DO 
$BODY$
DECLARE 
 AYPSCHOOL_ATTENDANCESCHOOL RECORD;
BEGIN
    FOR AYPSCHOOL_ATTENDANCESCHOOL IN (
		 select en.id, en.attendanceschoolid 
		 from enrollment en
		 where aypschoolid is null
	 ) LOOP

	UPDATE enrollment SET aypschoolid = AYPSCHOOL_ATTENDANCESCHOOL.attendanceschoolid where id = AYPSCHOOL_ATTENDANCESCHOOL.id;
    END LOOP;
 END;
 $BODY$;	

ALTER TABLE enrollment ALTER COLUMN aypSchoolId SET NOT NULL;
ALTER TABLE enrollment DROP CONSTRAINT enrollment_uk;
ALTER TABLE enrollment ADD CONSTRAINT enrollment_uk UNIQUE (studentid, attendanceschoolid, currentschoolyear, aypschoolid);