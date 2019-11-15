--ddl/500.sql
-- description column added
alter table specialcircumstance add description varchar(70);

-- US17119
 
CREATE TABLE operationaltestwindowstudent (
	studentid bigint NOT NULL, 
	contentareaid bigint NOT NULL, 
	courseid bigint NOT NULL, 
	exclude boolean DEFAULT FALSE, 
    activeflag boolean DEFAULT true, 
    operationaltestwindowid bigint NOT NULL, 
	createddate TIMESTAMP WITH time ZONE DEFAULT ('now'::text)::TIMESTAMP WITHOUT time ZONE, 
	CONSTRAINT uk_studentid_contentareaid_courseid_operationaltestwindowid UNIQUE (studentid, contentareaid,courseid,operationaltestwindowid),
	CONSTRAINT operationaltestwindowstudent_operationaltestwindowid_fk FOREIGN KEY (operationaltestwindowid) REFERENCES operationaltestwindow (id) 
	     MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT operationaltestwindowstudent_studentid_fk FOREIGN KEY (studentid) REFERENCES student (id) 
	     MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION);

CREATE INDEX idx_operationaltestwindowstudent_studentid ON operationaltestwindowstudent USING btree (studentid);

CREATE INDEX idx_operationaltestwindowstudent_contentareaid ON operationaltestwindowstudent USING btree (contentareaid);

CREATE INDEX idx_operationaltestwindowstudent_courseid ON operationaltestwindowstudent USING btree (courseid);

CREATE INDEX idx_operationaltestwindowstudent_operationaltestwindowid ON operationaltestwindowstudent USING btree (operationaltestwindowid);

CREATE INDEX idx_operationaltestwindowstudent_activeflag ON operationaltestwindowstudent USING btree (activeflag);