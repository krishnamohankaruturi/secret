--/ddl/577.sql
ALTER TABLE taskvariant ADD COLUMN focusdata text;
ALTER TABLE stimulusvariant ADD COLUMN stimulusdescription text;
CREATE SEQUENCE stimulusdescriptionview_seq;

CREATE TABLE stimulusdescriptionview
(
  id integer NOT NULL,
  viewby character varying(12) NOT NULL,
  stimulusvariantid integer,
  createdate timestamp without time zone,
  modifieddate timestamp without time zone,
  CONSTRAINT stimulusdescriptionview_pkey PRIMARY KEY (id),
  CONSTRAINT stimulusvariant_stimulusvariantid_fkey FOREIGN KEY
(stimulusvariantid) REFERENCES stimulusvariant (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
