--ddl/776.sql
--interim teacher feedback

CREATE TABLE IF NOT EXISTS testletfeedback
( 
	id bigserial NOT NULL UNIQUE,
	testletexternalid bigint,
	taskvariantid bigint,
	assessmentprogramid bigint not NULL,
	startdate timestamp with time ZONE NOT NULL, 
	enddate timestamp with time ZONE NOT NULL,
	createduser bigint, 
	modifieduser bigint, 
	createddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp with time zone, 
	modifieddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp with time zone,
	CONSTRAINT testletfeedback_ap_fkey FOREIGN KEY (assessmentprogramid) 
		REFERENCES assessmentprogram (id) MATCH SIMPLE
		ON UPDATE NO ACTION 
		ON DELETE NO ACTION
);
CREATE INDEX if not exists idx_testletfeedback_testletid ON testletfeedback (testletexternalid);
CREATE INDEX if not exists idx_testletfeedback_taskvariantid ON testletfeedback (taskvariantid);
CREATE INDEX if not exists idx_testletfeedback_assessmentprogramid ON testletfeedback (assessmentprogramid);
CREATE INDEX if not exists idx_testletfeedback_startdate ON testletfeedback (startdate);
CREATE INDEX if not exists idx_testletfeedback_enddate ON testletfeedback (enddate);
    
CREATE TABLE IF NOT EXISTS feedbackquestions
( 
	id bigserial NOT NULL UNIQUE,
	tasktypeexternalid bigint,
	questionsequence int,
	questiontext TEXT,
	questionoptions jsonb,
	required boolean DEFAULT false,
	startdate timestamp with time ZONE NOT NULL, 
	enddate timestamp with time ZONE NOT NULL,
	assessmentprogramid bigint NOT NULL,
	contentareaid bigint DEFAULT NULL,
	createduser bigint, 
	modifieduser bigint, 
	createddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp with time zone, 
	modifieddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp with time zone,
	CONSTRAINT feedbackquestions_ap_fkey FOREIGN KEY (assessmentprogramid) 
		REFERENCES assessmentprogram (id) MATCH SIMPLE
		ON UPDATE NO ACTION 
		ON DELETE NO ACTION,
	CONSTRAINT feedbackquestions_ca_fkey FOREIGN KEY (contentareaid) 
		REFERENCES contentarea (id) MATCH SIMPLE
		ON UPDATE NO ACTION 
		ON DELETE NO ACTION
);
CREATE INDEX if not exists idx_feedbackquestions_tasktype ON feedbackquestions (tasktypeexternalid);
CREATE INDEX if not exists idx_feedbackquestions_required ON feedbackquestions (required);
CREATE INDEX if not exists idx_feedbackquestions_assessprog_contentarea ON feedbackquestions (assessmentprogramid, contentareaid);
CREATE INDEX if not exists idx_feedbackquestions_startdate ON feedbackquestions (startdate);
CREATE INDEX if not exists idx_feedbackquestions_enddate ON feedbackquestions (enddate);

CREATE TABLE IF NOT EXISTS testletfeedbackresponses
(
	id bigserial NOT NULL,
	testletfeedbackid bigint NOT NULL,
	feedbackquestionid bigint NOT NULL,
	userid bigint,
	response TEXT,
	activeflag boolean DEFAULT TRUE,
	createddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp with time zone, 
	modifieddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp with time zone,
	CONSTRAINT testletfeedbackresponses_tf_fkey FOREIGN KEY (testletfeedbackid) 
		REFERENCES testletfeedback(id) MATCH SIMPLE
		ON UPDATE NO ACTION 
		ON DELETE NO ACTION,
	CONSTRAINT testletfeedbackresponses_fq_fkey FOREIGN KEY (feedbackquestionid) 
		REFERENCES feedbackquestions (id) MATCH SIMPLE
		ON UPDATE NO ACTION 
		ON DELETE NO ACTION,
	CONSTRAINT testletfeedbackresponses_user_fkey FOREIGN KEY (userid) 
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION 
		ON DELETE NO ACTION
);
CREATE INDEX if not exists idx_testletfeedbackresponses_testletfeedbackid ON testletfeedbackresponses (testletfeedbackid);
CREATE INDEX if not exists idx_testletfeedbackresponses_feedbackquestionid ON testletfeedbackresponses (feedbackquestionid);
CREATE INDEX if not exists idx_testletfeedbackresponses_userid ON testletfeedbackresponses (userid);