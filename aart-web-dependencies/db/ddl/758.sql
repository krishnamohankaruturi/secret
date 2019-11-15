--ddl/758.sql F842 PLTW Batch auto enrollment

ALTER TABLE testsession ADD COLUMN IF NOT EXISTS  gradebandid BIGINT;
ALTER TABLE testsession ADD CONSTRAINT testsession_gradebandid_fkey FOREIGN KEY (gradebandid) 
REFERENCES gradeband (id) MATCH SIMPLE 
ON UPDATE NO ACTION 
ON DELETE NO ACTION;

CREATE TABLE IF NOT EXISTS dashboardmessage
( 
    id bigserial NOT NULL, 
    assessmentprogramid bigint not null, 
    recordtype text NULL,     
    studentid bigint, 
    enrollmentid bigint, 
    attendanceschoolid bigint, 
    contentareaid bigint, 
	gradebandid bigint,
    schoolyear bigint, 
    rosterid bigint, 
    classroomid bigint,
    message text, 
	batchregistrationid bigint, 
    activeflag boolean default true, 
    createduser bigint, 
    modifieduser bigint, 
    createddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp with time zone, 
    modifieddate timestamp with time zone NOT NULL DEFAULT ('now'::text)::timestamp with time zone,
	CONSTRAINT dashboardmessage_ap_fkey FOREIGN KEY (assessmentprogramid) 
    REFERENCES assessmentprogram (id) MATCH SIMPLE 
    ON UPDATE NO ACTION 
    ON DELETE NO ACTION,
    CONSTRAINT dashboardmessage_studentid_fkey FOREIGN KEY (studentid) 
    REFERENCES student (id) MATCH SIMPLE 
    ON UPDATE NO ACTION 
    ON DELETE NO ACTION,
    CONSTRAINT dashboardmessage_enrollmentid_fkey FOREIGN KEY (enrollmentid) 
    REFERENCES enrollment (id) MATCH SIMPLE 
    ON UPDATE NO ACTION 
    ON DELETE NO ACTION,
    CONSTRAINT dashboardmessage_contentareaid_fkey FOREIGN KEY (contentareaid) 
    REFERENCES contentarea (id) MATCH SIMPLE 
    ON UPDATE NO ACTION 
    ON DELETE NO ACTION,
    CONSTRAINT dashboardmessage_rosterid_fkey FOREIGN KEY (rosterid) 
    REFERENCES roster (id) MATCH SIMPLE 
    ON UPDATE NO ACTION 
    ON DELETE NO ACTION);

DROP INDEX IF EXISTS idx_dashboardmessage_assessmentprogramid;
CREATE INDEX idx_dashboardmessage_assessmentprogramid
ON dashboardmessage USING btree
(assessmentprogramid)
TABLESPACE pg_default;

DROP INDEX IF EXISTS idx_dashboardmessage_attendanceschoolid;
CREATE INDEX idx_dashboardmessage_attendanceschoolid
ON dashboardmessage USING btree
(attendanceschoolid)
TABLESPACE pg_default;

DROP INDEX IF EXISTS idx_dashboardmessage_studentid;
CREATE INDEX idx_dashboardmessage_studentid
ON dashboardmessage USING btree
(studentid)
TABLESPACE pg_default;

DROP INDEX IF EXISTS idx_dashboardmessage_recordtype;
CREATE INDEX idx_dashboardmessage_recordtype
ON dashboardmessage USING btree
(recordtype)
TABLESPACE pg_default;

DROP INDEX IF EXISTS idx_dashboardmessage_schoolyear;
CREATE INDEX idx_dashboardmessage_schoolyear
ON dashboardmessage USING btree
(schoolyear)
TABLESPACE pg_default;

DROP INDEX IF EXISTS idx_dashboardmessage_contentareaid;
CREATE INDEX idx_dashboardmessage_contentareaid
ON dashboardmessage USING btree
(contentareaid)
TABLESPACE pg_default;


--F752
ALTER TABLE modulereport ADD COLUMN IF NOT EXISTS pdffilename text;

