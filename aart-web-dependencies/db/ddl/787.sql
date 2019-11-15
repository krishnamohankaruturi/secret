--ddl/787.sql

ALTER TABLE operationaltestwindow ADD COLUMN instructionplannerwindow BOOLEAN;
ALTER TABLE operationaltestwindow ADD COLUMN instructionplannerdisplayname CHARACTER VARYING(50);

CREATE INDEX idx_operationaltestwindow_instructionplannerwindow
    ON operationaltestwindow
    USING btree (instructionplannerwindow);

CREATE TABLE operationaltestwindowsubjectdirections (
    id SERIAL NOT NULL PRIMARY KEY,
    operationaltestwindowid BIGINT NOT NULL,
    contentareaid BIGINT NOT NULL,
    directions TEXT,
    activeflag BOOLEAN NOT NULL,
    createduser BIGINT,
    createddate TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    modifieduser BIGINT,
    modifieddate TIMESTAMP WITH TIME ZONE,
    
    CONSTRAINT operationaltestwindowsubjectdirections_operationaltestwindowid_fk
        FOREIGN KEY (operationaltestwindowid)
        REFERENCES operationaltestwindow (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT operationaltestwindowsubjectdirections_contentareaid_fk
        FOREIGN KEY (contentareaid)
        REFERENCES contentarea (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

COMMENT ON TABLE operationaltestwindowsubjectdirections IS 'This is for the Instruction and Assessment Planner, how directions differ by window and subject.';

CREATE INDEX idx_operationaltestwindowsubjectdirections_operationaltestwindowid
    ON operationaltestwindowsubjectdirections
    USING btree (operationaltestwindowid);

CREATE INDEX idx_operationaltestwindowsubjectdirections_contentareaid
    ON operationaltestwindowsubjectdirections
    USING btree (contentareaid);


ALTER TABLE organizationdetail ALTER COLUMN itistartdate TYPE TIMESTAMP WITH TIME ZONE;
ALTER TABLE organizationdetail ALTER COLUMN itienddate TYPE TIMESTAMP WITH TIME ZONE;
ALTER TABLE organizationdetail ADD COLUMN testingcycleid BIGINT;


CREATE TABLE linkagelevelsortorder (
    id SERIAL NOT NULL PRIMARY KEY,
    displayname TEXT NOT NULL,
    contentareaid BIGINT NOT NULL,
    sortorder INTEGER NOT NULL,
    createduser BIGINT NOT NULL,
    modifieduser BIGINT,
    createddate TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    modifieddate TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    activeflag BOOLEAN NOT NULL DEFAULT TRUE,
    
    CONSTRAINT linkagelevelsortorder_contentareaid_fk
        FOREIGN KEY (contentareaid)
        REFERENCES contentarea (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

COMMENT ON TABLE linkagelevelsortorder IS 'Used in the Instruction and Assessment Planner (and potentially other places) to define the order that linkage levels should appear';

CREATE INDEX idx_linkagelevelsortorder_contentareaid
    ON linkagelevelsortorder
    USING btree (contentareaid);


ALTER TABLE ititestsessionhistory ADD COLUMN operationaltestwindowid BIGINT;

ALTER TABLE ititestsessionhistory ADD CONSTRAINT fk_ititsh_otwid
    FOREIGN KEY (operationaltestwindowid)
    REFERENCES operationaltestwindow (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

CREATE INDEX idx_ititestsessionhistory_otwid
    ON ititestsessionhistory
    USING btree (operationaltestwindowid);


ALTER TABLE ititestsessionhistory ALTER COLUMN testcollectionid DROP NOT NULL;
ALTER TABLE ititestsessionhistory ALTER COLUMN testcollectionname DROP NOT NULL;

CREATE TABLE studentsensitivitytag (
	id SERIAL NOT NULL PRIMARY KEY,
	studentid BIGINT NOT NULL,
	sensitivitytagid BIGINT NOT NULL,
	activeflag BOOLEAN NOT NULL DEFAULT TRUE,
	createddate TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
	createduser BIGINT,
	modifieddate TIMESTAMP WITH TIME ZONE,
	modifieduser BIGINT,
	
    CONSTRAINT studentsensitivitytag_studentid_fk
        FOREIGN KEY (studentid)
        REFERENCES student (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT studentsensitivitytag_sensitivitytagid_fk
        FOREIGN KEY (sensitivitytagid)
        REFERENCES sensitivitytag (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE INDEX idx_studentsensitivitytag_studentid
    ON studentsensitivitytag
    USING btree (studentid);

CREATE INDEX idx_studentsensitivitytag_sensitivitytagid
    ON studentsensitivitytag
    USING btree (sensitivitytagid);

CREATE OR REPLACE FUNCTION upsert_studentsensitivitytag(
    param_studentid BIGINT,
    param_sensitivitytagid BIGINT,
    param_userid BIGINT,
    param_activeflag BOOLEAN DEFAULT TRUE) RETURNS VOID AS
$BODY$
BEGIN
    IF EXISTS (
        SELECT id
        FROM studentsensitivitytag
        WHERE studentid = param_studentid
        AND sensitivitytagid = param_sensitivitytagid
    ) THEN
        UPDATE studentsensitivitytag
        SET activeflag = param_activeflag,
        modifieduser = param_userid,
        modifieddate = now()
        WHERE id IN (
            SELECT id
            FROM studentsensitivitytag
            WHERE studentid = param_studentid
            AND sensitivitytagid = param_sensitivitytagid
            AND activeflag = NOT param_activeflag
        );
    ELSE
    	IF param_activeflag IS NOT DISTINCT FROM TRUE THEN
            INSERT INTO studentsensitivitytag (studentid, sensitivitytagid, activeflag, createduser)
                VALUES (param_studentid, param_sensitivitytagid, param_activeflag, param_userid);
        END IF;
    END IF;
END;
$BODY$
LANGUAGE PLPGSQL VOLATILE
COST 100;
