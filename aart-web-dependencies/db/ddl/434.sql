--ddl/434.sql

CREATE TABLE communicationmessageorgassessmentprogram
    (
      organizationid bigint NOT NULL,
      comminicationmessageid bigint NOT NULL,
      CONSTRAINT cmstate_organizationid_fk FOREIGN KEY (organizationid)
          REFERENCES organization (id) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE CASCADE,
      CONSTRAINT cmstate_comminicationmessageid_fk FOREIGN KEY (comminicationmessageid)
          REFERENCES communcationmessages (id) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE CASCADE                  
      );

