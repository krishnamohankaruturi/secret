-- 389.sql CB changes

-- CB ASL stories on task & media 
create sequence signedaccomodation_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;

CREATE TABLE signedaccommodation
(
  id bigint NOT NULL DEFAULT nextval('signedaccomodation_id_seq'::regclass),
  externalid bigint NOT NULL,
  contentgroupid bigint NOT NULL,
  signedabbreviation character varying(30),
  defaultorder integer,
  alternateorder integer,
  accessibilityfileid bigint,
  starttime integer,
  endtime integer,
  createuserid bigint,
  createdate timestamp with time zone,
  modifieddate timestamp with time zone,
  modifieduserid bigint,
  originationcode character varying(20) NOT NULL,
  CONSTRAINT signedacc_pkey PRIMARY KEY (id),
  CONSTRAINT signedacc_contentgroupid_fkey FOREIGN KEY (contentgroupid)
      REFERENCES contentgroup (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT signedacc_accessibilityfileid_fkey FOREIGN KEY (accessibilityfileid)
      REFERENCES accessibilityfile (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP INDEX IF EXISTS idx_signedaccommodation_accessibilityfileid;
CREATE INDEX idx_signedaccommodation_accessibilityfileid ON signedaccommodation USING btree (accessibilityfileid);

DROP INDEX IF EXISTS idx_signedaccommodation_contentgroupid;
CREATE INDEX idx_signedaccommodation_contentgroupid ON signedaccommodation USING btree (contentgroupid);

--CB US15817 : DB Schema Changes in EP
create sequence testsectionstaskvariants_id_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;
	
ALTER TABLE testsectionstaskvariants ADD COLUMN id bigint NOT NULL DEFAULT nextval('testsectionstaskvariants_id_seq'::regclass);
ALTER TABLE testsectionstaskvariants DROP CONSTRAINT testsectionstasks_pk;
ALTER TABLE testsectionstaskvariants ALTER COLUMN taskvariantid DROP NOT NULL;
ALTER TABLE testsectionstaskvariants ADD CONSTRAINT testsectionstaskvariants_pkey PRIMARY KEY (id);
