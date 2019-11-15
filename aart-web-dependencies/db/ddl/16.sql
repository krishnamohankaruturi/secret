
--US10940 - Organization level monitor student participation numbers (CSV)
	CREATE OR REPLACE VIEW assessment_program_participation AS
		SELECT ap.programname AS assessment_program_name, tc.name AS test_collection_name, t.testname AS test_name,
			gpo.organizationname AS state, po.organizationname AS district, co.organizationname AS school, 
			st.createddate::date AS day, c.categoryname AS status, count(st.*) AS total
		FROM studentstests st
			JOIN test t ON st.testid = t.id
			JOIN category c ON st.status = c.id
			JOIN testsession ts ON st.testsessionid = ts.id
			JOIN testcollection tc ON st.testcollectionid = tc.id 
			JOIN assessmentstestcollections atc ON tc.id = atc.testcollectionid
			JOIN assessment a ON atc.assessmentid = a.id
			JOIN testingprogram tp ON a.testingprogramid = tp.id
			JOIN assessmentprogram ap ON tp.assessmentprogramid = ap.id 
			JOIN enrollmentsrosters er ON ts.rosterid = er.rosterid
			JOIN roster r ON er.rosterid = r.id
			JOIN enrollment e ON  er.enrollmentid = e.id
			JOIN organization co ON e.attendanceschoolid = co.id
			JOIN organizationrelation por ON co.id = por.organizationid
			JOIN organization po ON por.parentorganizationid = po.id
			JOIN organizationrelation gpor ON po.id = gpor.organizationid
			JOIN organization gpo ON gpor.parentorganizationid = gpo.id	
		WHERE st.studentid = e.studentid
		GROUP BY ap.programname, tc.name, gpo.organizationname, po.organizationname, co.organizationname, t.testname, st.createddate::date, c.categoryname	 
		ORDER BY ap.programname, tc.name, gpo.organizationname, po.organizationname, co.organizationname, t.testname, st.createddate::date, c.categoryname;
		
		
	--US11520 / US11200 Record browser.	
	ALTER TABLE enrollmentsrosters ADD COLUMN id bigserial;
	ALTER TABLE enrollmentsrosters ADD CONSTRAINT enrollment_roster_uk UNIQUE (enrollmentid, rosterid);
	ALTER TABLE enrollmentsrosters DROP CONSTRAINT enrollment_roster_pk;
	ALTER TABLE enrollmentsrosters ADD CONSTRAINT enrollment_roster_pk PRIMARY KEY (id);

			
--US11518 - Implement First Contact Data Points from Ref Spec - Attention Section
			
	CREATE TABLE surveylabels
	(
	  id bigserial NOT NULL,
	  sectionid bigint NOT NULL,
	  labelnumber character varying(100) NOT NULL,
	  position bigint,
	  label character varying(2000),
	  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
	  createduser integer NOT NULL,
	  activeflag boolean DEFAULT true,
	  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
	  modifieduser integer NOT NULL,
	  CONSTRAINT pk_survey_labels PRIMARY KEY (id),
	  CONSTRAINT fk_survey_labels_section_id FOREIGN KEY (sectionid) 
		REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT uk_survey_labels_label_number UNIQUE (labelnumber)
	)
	WITH (
	  OIDS=FALSE
	);
		
	CREATE TABLE surveyresponses
	(
	  id bigserial NOT NULL,
	  labelid bigint,
	  responsesequence bigint,
	  responsevalue character varying(2000),
	  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
	  createduser integer NOT NULL,
	  activeflag boolean DEFAULT true,
	  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
	  modifieduser integer NOT NULL,
	  CONSTRAINT pk_survey_responses PRIMARY KEY (id),
	  CONSTRAINT fk_survey_responses_label_id FOREIGN KEY (labelid) 
		REFERENCES surveylabels (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT uk_survey_responses_labelsequence_label UNIQUE (labelid,responsesequence)
	)
	WITH (
	  OIDS=FALSE
	);	
	
	CREATE TABLE studentssurveyresponses
	(
	  id bigserial NOT NULL,
	  studentid bigint NOT NULL,
	  surveyresponseid bigint NOT NULL,
	  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
	  createduser integer NOT NULL,
	  activeflag boolean DEFAULT true,
	  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
	  modifieduser integer NOT NULL,
	  CONSTRAINT pk_students_survey_responses PRIMARY KEY (id),
	  CONSTRAINT fk_students_survey_responses_student_id FOREIGN KEY (studentid) 
		REFERENCES student (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT students_survey_responses_survey_response_id FOREIGN KEY (surveyresponseid) 
		REFERENCES surveyresponses (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT uk_students_survey_responses UNIQUE (studentid,surveyresponseid)
	)
	WITH (
	  OIDS=FALSE
	);

	
--US7899: User Story DLM - Student Basic Performance Report
	DROP VIEW node_report;
	
	CREATE OR REPLACE FUNCTION nvl(i bigint)
  	RETURNS bigint AS
	$BODY$
        BEGIN
  			if(i > -1) THEN
   				return i;
  			else    
   				RETURN 0;
  			end if;
        END;
	$BODY$
  	LANGUAGE plpgsql VOLATILE COST 100;
	
	CREATE OR REPLACE FUNCTION string_to_integer_array(inputtext text)
  	RETURNS bigint[] AS
	$BODY$ 
		DECLARE
	    elems text[];
	    elemsOut bigint[];
	    validInput boolean;
  		BEGIN
		 inputText := trim( both ']' from (trim(both '[' from inputText) ) );
		 Select inputText similar to '(\d+)(,\s*\d+)*' into validInput;		 
		 if (inputText is null) OR (inputText = '0') OR (inputText = '') OR ( validInput is null) OR (validInput is false) then		
		 	return null;
 		 end if;
    	 elems := string_to_array(inputText, ',');
 		 if elems is null then
  		 	 return null;
 		 end if;

    	 FOR i IN array_lower(elems, 1) .. array_upper(elems, 1) LOOP
      		 elemsOut[i]=cast(elems[i] as bigint);
    	 END LOOP;
    	 return elemsOut;
  	  END
	$BODY$
    LANGUAGE plpgsql VOLATILE COST 100;
  
	CREATE OR REPLACE VIEW node_report AS 
	 SELECT 
		s1.id AS student_id, s1.statestudentidentifier AS state_student_identifier, s1.legalfirstname AS legal_first_name, s1.legalmiddlename AS legal_middle_name, 
		s1.legallastname AS legal_last_name, s1.comprehensiverace AS comprehensive_race, s1.gender, s1.primarydisabilitycode AS primary_disability_code, summary.node_key, 
		st1.id AS students_tests_id, t1.id AS test_id, t1.testname AS test_name, t1.status AS test_status_id, tc1.id AS test_collection_id, tc1.name AS test_collection_name, 
		summary.no_of_responses, summary.correct_response, summary.no_of_answered_items, summary.total_raw_score, summary.contentframeworkdetailcode, 
		summary.contentframeworkdetaildescription, summary.total_items_presented As total_items_presented
	 FROM student s1, studentstests st1, test t1, testcollectionstests tct1, testcollection tc1, 
			(SELECT 
				sr.studentid AS student_id, tvln.nodecode AS node_key, count(1) AS no_of_responses, tvf.correctresponse AS correct_response, 	
				count(DISTINCT tvln.taskvariantid) AS no_of_answered_items,
				(select count(1) from testsectionstaskvariants tsi, test t2 where tsi.testsectionid = t2.id and t2.id = st.testid) AS total_items_presented,
				sum(nvl(tvf.responsescore::bigint)) AS total_raw_score,
				cfd.contentcode AS contentframeworkdetailCode, cfd.description AS contentframeworkdetaildescription
			 FROM studentsresponses sr, student s, taskvariantsfoils tvf, taskvariant tv, taskvariantlearningmapnode tvln, studentstests st, test t, 
				testcollectionstests tct, testcollection tc,taskvariantcontentframeworkdetail tvcfd, contentframeworkdetail cfd
			 WHERE sr.studentstestsid = st.id AND (tvf.foilid = sr.foilid OR tvf.foilid IS NULL AND sr.response IS NOT NULL AND sr.response ~~ '[%]'::text 
				AND btrim(btrim(sr.response, '['::text), ']'::text) ~ similar_escape('(\d+)(,\s*\d+)*'::text, NULL::text) 
				AND (tvf.foilid = ANY (string_to_integer_array(sr.response)))) AND tv.id = tvf.taskvariantid AND tv.id = tvln.taskvariantid AND s.id = sr.studentid 
				AND st.testid = t.id AND tct.testid = t.id AND tct.testcollectionid = tc.id AND tv.id = tvcfd.taskvariantid AND tvcfd.contentframeworkdetailid = cfd.id
				AND tvcfd.isprimary = true
			 GROUP BY sr.studentid,st.testid, tvln.nodecode, tvf.correctresponse, cfd.id, cfd.description
			) summary
	  WHERE st1.studentid = s1.id AND st1.testid = tct1.testid AND t1.id = st1.testid AND tct1.testid = t1.id AND tct1.testcollectionid = tc1.id AND summary.student_id = s1.id
	  ORDER BY s1.id, summary.node_key, summary.correct_response;

--CB Publishing changes.

ALTER TABLE readaloudaccommodation ALTER COLUMN syntheticpronoun TYPE text;
 
ALTER TABLE contentgroup ADD COLUMN stimulusvariantid bigint;

ALTER TABLE contentgroup ADD CONSTRAINT contentgroup_stimulusvariantid_fk FOREIGN KEY (stimulusvariantid)

REFERENCES stimulusvariant (id) MATCH FULL;
	