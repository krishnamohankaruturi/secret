--ddl/435.sql

ALTER TABLE IF EXISTS communcationmessages RENAME TO communicationmessage;
DROP TABLE IF EXISTS communicationmessagesorgassessmentprogram;

DROP TABLE IF EXISTS cmstate;
CREATE TABLE cmstate
(
  stateid bigint NOT NULL,
  comminicationmessageid bigint NOT NULL,
      CONSTRAINT cmstate_stateid_fk FOREIGN KEY (stateid)
	  REFERENCES organization (id) MATCH SIMPLE
	  ON UPDATE NO ACTION ON DELETE CASCADE,
      CONSTRAINT cmstate_comminicationmessageid_fk FOREIGN KEY (comminicationmessageid)
	  REFERENCES communicationmessage (id) MATCH SIMPLE
	  ON UPDATE NO ACTION ON DELETE CASCADE     
)
WITH (
  OIDS=FALSE
);

ALTER TABLE IF EXISTS cmstate RENAME TO communicationmessagestate;
