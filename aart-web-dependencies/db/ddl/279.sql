--testlet sensitivity tags by content area

ALTER TABLE testletsensitivitytag DROP CONSTRAINT testletsensitivitytag_sensitivitytagid_fk;

create table sensitivitytag (
  id bigserial NOT NULL,
  externalid bigint,  
  name character varying(256),
  abbreviation character varying(30),
  contentareaid bigint, 
  inuse boolean DEFAULT true,
  originationcode character varying(20),
  createdate timestamp with time zone,
  modifieddate timestamp with time zone,
  CONSTRAINT sensitivitytag_pkey PRIMARY KEY (id),
  CONSTRAINT sensitivitytag_contentareaid_fkey FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE testletsensitivitytag
  ADD CONSTRAINT testletsensitivitytag_sensitivitytagid_fk FOREIGN KEY (sensitivitytagid)
      REFERENCES sensitivitytag (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
ALTER TABLE ititestsessionsensitivitytags DROP CONSTRAINT fk_itisensitivity_tagid;

ALTER TABLE ititestsessionsensitivitytags
  ADD CONSTRAINT fk_itisensitivity_tagid FOREIGN KEY (sensitivitytag)
      REFERENCES sensitivitytag (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;      