--For dml/*.sql

--changes from change pond CB team
ALTER TABLE taskvariant ADD COLUMN numberofparts integer;
CREATE SEQUENCE multiparttaskvariantid_seq;

CREATE TABLE multiparttaskvariant (
  id bigint NOT NULL,
  externalid bigint,
  taskvariantid bigint,
  numberofresponses integer,
  minchoices integer,
  maxchoices integer,
  tasktypeid bigint,
  taskstem text,
  score bigint,
  scoringdependency character varying(20),
  createdate timestamp with time zone,
  modifieddate timestamp with time zone,
  CONSTRAINT multiparttaskvariant_pkey PRIMARY KEY (id),
  CONSTRAINT multiparttaskvariant_taskvariantid_fkey FOREIGN KEY (taskvariantid)
      REFERENCES taskvariant (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT multiparttaskvariant_tasktypeid_fkey FOREIGN KEY (tasktypeid)
      REFERENCES tasktype (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE foil ADD  COLUMN multiparttaskvariantid bigint;
ALTER TABLE foil ADD  CONSTRAINT foil_multiparttaskvariantid_fkey FOREIGN KEY (multiparttaskvariantid)
      REFERENCES multiparttaskvariant (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE taskvariantsstimulusvariants ADD  column multiparttaskvariantid bigint;
ALTER TABLE taskvariantsstimulusvariants ADD  CONSTRAINT taskvariantsstimulusvariants_multiparttaskvariantid_fkey FOREIGN KEY
(multiparttaskvariantid) REFERENCES multiparttaskvariant (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE foilsstimulusvariants ADD  column multiparttaskvariantid bigint;
ALTER TABLE foilsstimulusvariants ADD CONSTRAINT foilsstimulusvariants_multiparttaskvariantid_fkey FOREIGN KEY
(multiparttaskvariantid) REFERENCES multiparttaskvariant (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE taskvariantsstimulusvariants DROP CONSTRAINT IF EXISTS taskvariantsstimulusvariants_pkey;

-- Changes from scriptbees team
ALTER TABLE groups ADD singleuser boolean DEFAULT false;


