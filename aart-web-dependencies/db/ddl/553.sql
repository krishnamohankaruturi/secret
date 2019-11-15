DROP FUNCTION IF EXISTS update_special_circumstance_status(filepath text);

CREATE OR REPLACE FUNCTION update_special_circumstance_status()
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
	RAISE INFO 'Started processing';
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
				--SELECT id INTO assessmentprogram_id FROM assessmentprogram WHERE programname ILIKE TRIM(temprecord.assessmentProgram);
				
				--get restricted special circumstance code
				SELECT id INTO specialcircumstance_id FROM specialcircumstance sc 
				JOIN statespecialcircumstance ssc ON ssc.specialcircumstanceid = sc.id 
					AND ssc.requireconfirmation is true
					AND ssc.activeflag is true
				WHERE sc.cedscode = temprecord.cedsCodeNumber :: bigint 
					--AND sc.assessmentprogramid = assessmentprogram_id 
					AND ssc.stateid = state_id;
					
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
						SET status = (SELECT ID FROM category WHERE categoryname iLIKE trim_status 
										AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'SPECIAL CIRCUMSTANCE STATUS')),
							modifieddate = now(),
							modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
							approvedby = approved_user
					WHERE studentTestId = stdtestid AND activeflag is true AND specialcircumstanceid = specialcircumstance_id;
				END LOOP;
			END IF;
		END IF;
	END LOOP;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

--operationaltestwindow indexes

CREATE INDEX idx_operationaltestwindow_id
  ON operationaltestwindow
  USING btree
  (id);

  CREATE INDEX idx_operationaltestwindow_windowname
  ON operationaltestwindow
  USING btree
  (windowname);

  
  CREATE INDEX idx_operationaltestwindow_assessmentprogramid
  ON operationaltestwindow
  USING btree
  (assessmentprogramid);


 --operationaltestwindowmultiassigndetail

 CREATE INDEX idx_operationaltestwindowmultiassigndetail_operationaltestwindowid
  ON operationaltestwindowmultiassigndetail
  USING btree
  (operationaltestwindowid);


   CREATE INDEX idx_operationaltestwindowmultiassigndetail_contentareaid
  ON operationaltestwindowmultiassigndetail
  USING btree
  (contentareaid);


 --operationaltestwindowsessionrule 

   CREATE INDEX idx_operationaltestwindowsessionrule_operationaltestwindowid
  ON operationaltestwindowsessionrule
  USING btree
  (operationaltestwindowid);


  
   CREATE INDEX idx_operationaltestwindowsessionrule_sessionruleid
  ON operationaltestwindowsessionrule
  USING btree
  (sessionruleid);


 --operationaltestwindowstate

     CREATE INDEX idx_operationaltestwindowstate_operationaltestwindowid
  ON operationaltestwindowstate
  USING btree
  (operationaltestwindowid);


  
     CREATE INDEX idx_operationaltestwindowstate_stateid
  ON operationaltestwindowstate
  USING btree
  (stateid);


 --operationaltestwindowstestcollections


      CREATE INDEX idx_operationaltestwindowstestcollections_operationaltestwindowid
  ON operationaltestwindowstestcollections
  USING btree
  (operationaltestwindowid);
  
	
  CREATE INDEX idx_operationaltestwindowstestcollections_testcollectionid
  ON operationaltestwindowstestcollections
  USING btree
  (testcollectionid);