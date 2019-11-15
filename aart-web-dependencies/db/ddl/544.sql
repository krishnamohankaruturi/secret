DROP INDEX IF EXISTS idx_testsession_name;
CREATE INDEX idx_testsession_name ON testsession using btree(name);

--DROP FUNCTION update_special_circumstance_status(filepath text);

CREATE OR REPLACE FUNCTION update_special_circumstance_status(filepath text)
  RETURNS void AS
$BODY$
DECLARE
	stdtestid bigint;
	stdtestids bigint[];
	temprecord record;
	trim_status text;
	assessmentprogram_id bigint;
	specialcircumstance_id bigint;
	approved_user bigint;
	state_id bigint;
BEGIN
	IF(filepath is NULL OR filepath = '') THEN
		RAISE NOTICE 'Please provide file path';
		RETURN;
	ELSE
		--cretae temp table
		DROP TABLE IF EXISTS tmp_x;
		CREATE TEMP TABLE tmp_x (state text, district text, school text, educatorLastName text, educatorFirstName text, studentLastName text,
			studentFirstName text, studentMiddleName text, stateStudentIdentifier text,	assessmentProgram text, subject text, testSessionName text,
			scCodeDescription text, cedsCodeNumber text, stateCodeNumber text, approvalStatus text, approverLastName text, approverFirstName text,
			approvalDateTime text); 

		-- copy csv data to temp table
		EXECUTE format($psql$COPY tmp_x FROM %L (DELIMITER ',', HEADER TRUE, FORMAT CSV)$psql$, filepath);
		
		RAISE NOTICE 'Started processing %', filepath;
		
		FOR temprecord IN     
		     SELECT * FROM tmp_x
		LOOP
			SELECT TRIM(temprecord.approvalStatus) INTO trim_status;
			IF(trim_status != '' AND trim_status is not null AND (trim_status = 'Approved' OR trim_status = 'Not Approved' OR trim_status ='Pending Further Review' )) THEN
			   --get studentstest ids
				SELECT array_agg(st.id) INTO stdtestids FROM
					studentstests st
					JOIN student s ON st.studentid = s.id AND s.statestudentidentifier LIKE TRIM(temprecord.stateStudentIdentifier)
					JOIN organizationtreedetail otd ON s.stateid = otd.stateid 
													AND otd.schoolname ilike TRIM(temprecord.school)
													AND otd.districtname ilike TRIM(temprecord.district)
													AND otd.statename ilike TRIM(temprecord.state)
					JOIN enrollment en ON en.studentid = s.id AND en.attendanceschoolid = otd.schoolid
					JOIN testsession ts ON ts.id = st.testsessionid AND ts.name iLIKE TRIM(temprecord.testSessionName)
					JOIN testcollection tc ON tc.id = ts.testcollectionid 
								AND tc.contentareaid in (SELECT id FROM contentarea WHERE name iLIKE TRIM(temprecord.subject));
										
				SELECT stateid INTO state_id FROM organizationtreedetail WHERE statename ilike TRIM(temprecord.state);
				
				RAISE NOTICE 'Updating specialcircumstance status for student - ''%'' on Testsession - ''%''', temprecord.stateStudentIdentifier, temprecord.testSessionName;
				
				IF (stdtestids IS NOT NULL AND array_length(stdtestids,1) > 0) THEN
					SELECT id INTO assessmentprogram_id FROM assessmentprogram WHERE programname ILIKE TRIM(temprecord.assessmentProgram);
					SELECT id INTO specialcircumstance_id FROM specialcircumstance WHERE cedscode = temprecord.cedsCodeNumber :: bigint and assessmentprogramid = assessmentprogram_id;
					IF (temprecord.approverFirstName IS NOT NULL AND temprecord.approverLastName IS NOT NULL) THEN
						SELECT au.id INTO approved_user
						FROM aartuser au 
							JOIN usersorganizations uo ON au.id = uo.aartuserid 
							JOIN userorganizationsgroups uog ON uog.userorganizationid = uo.id
							JOIN groups g ON g.id = uog.groupid and g.groupname ilike 'State Assessment Administrator'
						WHERE firstname ilike temprecord.approverFirstName AND surname ilike temprecord.approverLastName and uo.organizationid = state_id;
					ELSE
						SELECT NULL INTO approved_user;
					END IF;
					FOR stdtestid IN SELECT * FROM unnest(stdtestids) 
					LOOP
						UPDATE studentspecialcircumstance 
							SET status = (SELECT ID FROM category WHERE categoryname LIKE trim_status 
											AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'SPECIAL CIRCUMSTANCE STATUS')),
								modifieddate = now(),
								modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
								approvedby = approved_user
						WHERE studentTestId = stdtestid AND activeflag is true AND specialcircumstanceid = specialcircumstance_id;
					END LOOP;
				END IF;
			ELSE
				RAISE INFO 'Skipped for Student ''%'' with Testsession name ''%'' and ''%'' status.', temprecord.stateStudentIdentifier,  temprecord.testSessionName, trim_status;
			END IF;
		END LOOP;
		DROP TABLE tmp_x;
	END IF;
	END;
	$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
  