DROP SEQUENCE IF EXISTS questarrequest_id_seq;
DROP TABLE IF EXISTS questarrequest;

CREATE SEQUENCE studentresponseaudit_id_seq 
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;

CREATE TABLE studentresponseaudit
(
	id bigint NOT NULL,
	createduser integer,
	startdate timestamp with time zone,
	enddate timestamp with time zone,
	numberofresponses integer,
	CONSTRAINT pk_studentresponseaudit_id PRIMARY KEY (id),
	CONSTRAINT fk_studentresponseaudit_createduser FOREIGN KEY (createduser)
		REFERENCES aartuser (id) MATCH FULL
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

--US15848, add confirm for questar service
ALTER TABLE studentresponseaudit ADD COLUMN xml TEXT;
ALTER TABLE studentresponseaudit ADD COLUMN confirmed BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE studentresponseaudit ADD COLUMN confirmeddate TIMESTAMP WITH TIME ZONE;

--US15824, add group table for task variants
CREATE TABLE studentresponsetaskvariantgroup
(
	taskvariantexternalid BIGINT NOT NULL,
	groupnumber INTEGER NOT NULL,
	contentareaabbr CHARACTER VARYING (75),
	gradecourseabbr CHARACTER VARYING (10),
	processed BOOLEAN NOT NULL DEFAULT FALSE,
	processeddate TIMESTAMP WITH TIME ZONE
);


DROP INDEX IF EXISTS idx_usersecurityagreement_aartuserid_assessmentprogramid; 
DROP INDEX IF EXISTS idx_userpasswordreset_aart_user_id_auth_token; 
DROP INDEX IF EXISTS idx_modulereport_createduser_reporttypeid;
DROP INDEX IF EXISTS idx_lmassessmentmodelrule_testspecificationid_ranking; 

DROP INDEX IF EXISTS idx_studentsurveyresponse_survey_response;
DROP INDEX IF EXISTS idx_student_id;

DROP INDEX IF EXISTS idx_ititestsessionhistory_studentid_rosterid_status_testcollect;  
DROP INDEX IF EXISTS idx_ititestsessionhistory_studentid_rosterid;
DROP INDEX IF EXISTS idx_ititestsessionhistory_roster;
DROP INDEX IF EXISTS idx_ititestsessionhistory_testsession;

DROP INDEX IF EXISTS idx_stco_record_staging_ststudentid_currschyr;
DROP INDEX IF EXISTS idx_stco_record_staging_ststudentid;
CREATE INDEX idx_stco_record_staging_ststudentid ON stco_record_staging USING btree (state_student_identifier);
DROP INDEX IF EXISTS idx_stco_record_staging_currschyr;
CREATE INDEX idx_stco_record_staging_currschyr ON stco_record_staging USING btree (current_school_year);
  
DROP INDEX IF EXISTS idx_kids_record_staging_ststudentid_currschyr;
DROP INDEX IF EXISTS idx_kids_record_staging_ststudentid;
CREATE INDEX idx_kids_record_staging_ststudentid ON kids_record_staging USING btree (state_student_identifier);
  
DROP INDEX IF EXISTS idx_kids_record_staging_currschyr;
CREATE INDEX idx_kids_record_staging_currschyr ON kids_record_staging USING btree (current_school_year); 

CREATE OR REPLACE FUNCTION move_to_newtest(epnewtestid bigint, epoldtestid bigint)
RETURNS integer AS
$BODY$
DECLARE 
	studentstests_row record;
	studentstestsections_row record;
	counter bigint;
	BEGIN
		counter = 0;
		-- This function for fixing KAP tests
		-- Logic implementation for - Test taker has registered for the test but has not yet started it. We would like the test taker to get the new, updated form with all items corrected.	
		FOR studentstests_row IN (SELECT st.* FROM studentstests st JOIN category c ON st.status = c.id 
			WHERE testid = epoldtestid AND c.categorycode = 'unused' 
			AND categorytypeid = (select id from categorytype where typecode = 'STUDENT_TEST_STATUS'))
		LOOP
			counter = counter+1;
			--Statement to Update studentstests table testid column with newly published test id 
			UPDATE studentstests SET testid = epnewtestid WHERE id = studentstests_row.id;

			--Statement to Update studentstestsections table
			FOR studentstestsections_row IN (SELECT sts.id,oldts.id as oldtsid,newts.id as newtsid 
								FROM studentstestsections sts 
									JOIN testsection oldts on sts.testsectionid = oldts.id 
									JOIN testsection newts on newts.externalid=oldts.externalid 
										AND newts.testid=epnewtestid
								WHERE studentstestid = studentstests_row.id)
			LOOP
				UPDATE studentstestsections SET testsectionid = studentstestsections_row.newtsid 
					WHERE id = studentstestsections_row.id;
			END LOOP;
		END LOOP;
		RAISE INFO 'UPDATED % studentstests', counter;
	RETURN counter;
	END;	
$BODY$
LANGUAGE 'plpgsql';
