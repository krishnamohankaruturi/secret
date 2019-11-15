----US15831 : Manage Accountability(AYP) school correctly on KSDE Test records
/*DO 
$BODY$
DECLARE	
	AYPSCHOOL_ATTENDANCESCHOOL RECORD;
BEGIN
    FOR AYPSCHOOL_ATTENDANCESCHOOL IN (
	select en.id, en.attendanceschoolid, (select id from organization where displayidentifier = en.aypschoolidentifier and id in(select id from organization_children(stu.stateid)) limit 1) as aypSchoolId from enrollment en
	INNER JOIN Student stu
	ON en.studentid = stu.id) LOOP

    UPDATE enrollment SET aypschoolid = (CASE WHEN AYPSCHOOL_ATTENDANCESCHOOL.aypSchoolId IS NULL THEN AYPSCHOOL_ATTENDANCESCHOOL.attendanceschoolid	
	ELSE AYPSCHOOL_ATTENDANCESCHOOL.aypSchoolId END) where id = AYPSCHOOL_ATTENDANCESCHOOL.id;
    END LOOP;
 END;
 $BODY$; */
 