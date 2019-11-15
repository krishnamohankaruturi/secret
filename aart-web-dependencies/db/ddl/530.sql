-- this function was added to production as part of service request US17958
--author : Rohit Yadav

CREATE OR REPLACE FUNCTION correct_student_enrollment(
	_state_student_identifier TEXT,
	_subject TEXT,
	_current_school TEXT,
	_future_school TEXT,
	_schoolyear INTEGER
)

RETURNS VOID AS $func$
DECLARE
	current_school_record RECORD;
	future_school_record RECORD;
	no_match_record RECORD;
	number_updated_enrollment INTEGER;
	number_updated_studentstestsections INTEGER;
	number_updated_students INTEGER;
	future_school_id BIGINT;
	current_school_id BIGINT;
	future_school_count BIGINT;
	test_session_id BIGINT;
	state_id BIGINT;
	future_school_curentEnrollment_id BIGINT;

BEGIN
        SELECT id FROM organization WHERE displayidentifier = 'KS' INTO state_id;
                
		SELECT schoolid FROM organizationtreedetail
  		WHERE schooldisplayidentifier =_current_school AND stateid = state_id
		INTO current_school_id;

		SELECT schoolid FROM organizationtreedetail
  		WHERE schooldisplayidentifier =_future_school AND stateid = state_id
		INTO future_school_id;

		SELECT count(st.*) as count
		FROM studentstests st
		JOIN enrollment en ON en.id = st.enrollmentid
		JOIN testcollection tc ON tc.id = st.testcollectionid
		JOIN organization attsch ON attsch.id = en.attendanceschoolid
		JOIN organization aypsch ON aypsch.id = en.aypschoolid
		JOIN contentarea ca ON ca.id = tc.contentareaid
		JOIN student stu ON stu.id = en.studentid
		JOIN testsession ts ON ts.id = st.testsessionid
		JOIN category c ON c.id = st.status
		WHERE stu.statestudentidentifier = _state_student_identifier
	  	AND stu.stateid = state_id
	  	AND en.currentschoolyear = _schoolyear
	  	AND ca.abbreviatedname = _subject
	  	AND attsch.displayidentifier = _future_school
        AND ts.operationaltestwindowid IN (10131,10132,10133)
		INTO future_school_count;

        SELECT en.id 
        FROM enrollment en 
        JOIN student stu on stu.id = en.studentid and en.attendanceschoolid = future_school_id and stu.stateid = state_id and currentschoolyear = _schoolyear LIMIT 1 
        INTO future_school_curentEnrollment_id;
                
		FOR current_school_record IN (
		SELECT stu.statestudentidentifier,
	       en.id enrollmentid,
	       en.activeflag,
	       st.id AS studentstestid,
	       st.testsessionid,
	       st.testcollectionid as testcollectionid,
	       st.testid as testid,
	       st.enrollmentid,
	       c.categorycode as status,
	       ts.stageid as stageid,
	       ts.gradecourseid as gradecourseid,
	       st.activeflag,
	       attsch.displayidentifier AS attSchBldNum,
	       ca.abbreviatedname AS contentareaname,
	       ts.name AS testsessionname
		FROM studentstests st
		JOIN enrollment en ON en.id = st.enrollmentid
		JOIN testcollection tc ON tc.id = st.testcollectionid
		JOIN organization attsch ON attsch.id = en.attendanceschoolid
		JOIN organization aypsch ON aypsch.id = en.aypschoolid
		JOIN contentarea ca ON ca.id = tc.contentareaid
		JOIN student stu ON stu.id = en.studentid
		JOIN testsession ts ON ts.id = st.testsessionid
		JOIN category c ON c.id = st.status
		WHERE stu.statestudentidentifier = _state_student_identifier
	  	AND stu.stateid = state_id
	  	AND en.currentschoolyear = _schoolyear
	  	AND ca.abbreviatedname = _subject
	  	AND attsch.displayidentifier = _current_school
        AND ts.operationaltestwindowid IN (10131,10132,10133)) LOOP

		IF (future_school_count > 0)
			THEN
				FOR future_school_record IN (
					SELECT stu.statestudentidentifier,
				       en.id enrollmentid,
				       en.activeflag,
				       st.id AS studentstestid,
				       st.testsessionid,
				       st.testcollectionid as testcollectionid,
				       st.testid as testid,
				       st.enrollmentid,
				       c.categorycode as status,
				       ts.stageid as stageid,
				       ts.gradecourseid as gradecourseid,
				       st.activeflag,
				       attsch.displayidentifier AS attSchBldNum,
				       ca.abbreviatedname AS contentareaname,
				       ts.name AS testsessionname
					FROM studentstests st
					JOIN enrollment en ON en.id = st.enrollmentid
					JOIN testcollection tc ON tc.id = st.testcollectionid
					JOIN organization attsch ON attsch.id = en.attendanceschoolid
					JOIN organization aypsch ON aypsch.id = en.aypschoolid
					JOIN contentarea ca ON ca.id = tc.contentareaid
					JOIN student stu ON stu.id = en.studentid
					JOIN testsession ts ON ts.id = st.testsessionid
					JOIN category c ON c.id = st.status
					WHERE stu.statestudentidentifier = _state_student_identifier
				  	AND stu.stateid = state_id				    	
				  	AND en.currentschoolyear = _schoolyear
				  	AND ca.abbreviatedname = _subject
				  	AND attsch.displayidentifier = _future_school
			        AND ts.operationaltestwindowid IN (10131,10132,10133)) LOOP

			        IF ((current_school_record.statestudentidentifier = future_school_record.statestudentidentifier) AND 
			        	(current_school_record.stageid = future_school_record.stageid) AND 
			        	(current_school_record.contentareaname = future_school_record.contentareaname) AND
			        	(current_school_record.gradecourseid = future_school_record.gradecourseid)
			           )
			            THEN
			            RAISE NOTICE 'Matching record found for student %',current_school_record.statestudentidentifier;
							IF ((current_school_record.status = 'complete') AND (future_school_record.status = 'inprogress'))
			                	THEN        
			                    	RAISE NOTICE 'Test status is inprogress in school % for statestudentidentifier %',future_school_record.attSchBldNum,current_school_record.statestudentidentifier;

			            	ELSIF ((current_school_record.status = 'complete') AND (future_school_record.status = 'unused'))
			                	THEN
			                        	RAISE NOTICE 'Moving student % from % to %',future_school_record.statestudentidentifier,current_school_record.attSchBldNum,future_school_record.attSchBldNum;

			                        	RAISE NOTICE 'Updating enrollment and testsessionid in studentstests table for student %',future_school_record.statestudentidentifier;
			                         	WITH updated_studentstests_enrollment_rows AS (
			                                        UPDATE studentstests
			                                        SET enrollmentid = future_school_record.enrollmentid, testsessionid = future_school_record.testsessionid,
			                                        modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
			                                        modifieddate = NOW()
			                                        WHERE id = current_school_record.studentstestid
			                         	RETURNING 1
			                         	)
			                        	SELECT count(*) FROM updated_studentstests_enrollment_rows INTO number_updated_enrollment;
			                        	RAISE NOTICE 'Deactivated % rows in studentstests table',number_updated_enrollment;



			                        	RAISE NOTICE 'Updating studentstestsections table for student %',future_school_record.statestudentidentifier;
			                        	WITH updated_studentstestsections_rows AS (
			                                        UPDATE studentstestsections
			                                        SET activeflag = FALSE,
			                                        modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
			                                        modifieddate = NOW()
			                                        WHERE studentstestid = future_school_record.studentstestid
			                        	 RETURNING 1
			                         	)
			                       		 
			                       		SELECT count(*) FROM updated_studentstestsections_rows INTO number_updated_studentstestsections;
			                        	RAISE NOTICE 'Deactivated % rows in studentstestsections table',number_updated_studentstestsections;

			                        	RAISE NOTICE 'Updating studentstests table for student %',future_school_record.statestudentidentifier;
			                        	WITH updated_studentstests_rows AS (
			                                        UPDATE studentstests
			                                        SET activeflag = FALSE,
			                                        modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
			                                        modifieddate = NOW()
			                                        WHERE id = future_school_record.studentstestid
			                         	RETURNING 1
			                         	)

			                        	SELECT count(*) FROM updated_studentstests_rows INTO number_updated_students;
			                        	RAISE NOTICE 'Deactivated % rows in studentstests table',number_updated_students;
			            	END IF;
	        		END IF;
	        	END LOOP;
		END IF;
        END LOOP;
END;
$func$ LANGUAGE 'plpgsql' STRICT;