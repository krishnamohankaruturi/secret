--DE13046: EP: Prod - Clear record received but student still has MDPT test assigned.
--As per US17482, ELA test type records are added to grade 11 students to include them in Performance window. In our case ELA type record
--was added to History. But when a CLEAR request was sent for the history record, ELA record was not cleared. So, MDPT tests still assigned to the student 
--The following queries will clear the session information of this student for that particular MDPT subject

UPDATE enrollmenttesttypesubjectarea 
SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), testtypeid = (select id from testtype where testtypecode = 'C' and activeflag is true)
WHERE enrollmentid = 2060444 and subjectareaid = 17;

UPDATE studentstests
SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 
status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'exitclearunenrolled')
WHERE id = 8686405;
	

UPDATE studentstestsections
SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 
statusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'exitclearunenrolled')
WHERE studentstestid = 8686405;

/*
DO 
$BODY$
DECLARE
	enrl_record RECORD;
	studentstests_id BIGINT;

BEGIN

	For enrl_record IN 
	(
		select enttsa.*
		from enrollment en
		join enrollmenttesttypesubjectarea enttsa on enttsa.enrollmentid=en.id
		join subjectarea sa on sa.id = enttsa.subjectareaid
		where en.attendanceschoolid in (select schoolid from organizationtreedetail where stateid=51) and en.currentschoolyear=2016 
			and en.activeflag is true 
			and en.currentgradelevel in (select id from gradecourse where abbreviatedname='11')
			and enttsa.activeflag is false
			and sa.id=(select id from subjectarea where activeflag is true and subjectareacode='SHISGOVA')
	)
	LOOP
		select st.id into studentstests_id
		from enrollment en
		join enrollmenttesttypesubjectarea enttsa on enttsa.enrollmentid=en.id
		join subjectarea sa on sa.id = enttsa.subjectareaid
		join studentstests st on en.id = st.enrollmentid 
		where en.attendanceschoolid in (select schoolid from organizationtreedetail where stateid=51) and en.currentschoolyear=2016 
			and en.activeflag is true 
			and en.currentgradelevel in (select id from gradecourse where abbreviatedname='11')
			and enttsa.activeflag is true
			and sa.id=(select id from subjectarea where activeflag is true and subjectareacode='SELAA')	
			and en.id = enrl_record.enrollmentid
			and st.status in (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode in ('unused', 'inprogress'));


		IF studentstests_id IS NOT NULL THEN

			UPDATE enrollmenttesttypesubjectarea 
			SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), testtypeid = (select id from testtype where testtypecode = 'C' and activeflag is true)
			WHERE enrollmentid = enrl_record.enrollmentid and subjectareaid = (select id from subjectarea where activeflag is true and subjectareacode='SELAA');

			UPDATE studentstests
			SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 
				status = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'exitclearunenrolled')
			WHERE id = studentstests_id;

			UPDATE studentstestsections
			SET activeflag = FALSE, modifieddate = NOW(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), 
				statusid = (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'exitclearunenrolled')
			WHERE studentstestid = studentstests_id;

			
		END IF;

	END LOOP; 
END;
$BODY$;
*/
