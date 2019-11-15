-- 570.sql
-- Schema changes from change pond

ALTER TABLE taskvariant ADD expectednumberofattempts bigint;
ALTER TABLE taskvariant ADD maxtimeallowedperattempt bigint;

ALTER TABLE contentgroup ADD COLUMN multiparttaskvariantid bigint;

ALTER TABLE contentgroup ADD CONSTRAINT contentgroup_multiparttaskvariantid_fk FOREIGN KEY (multiparttaskvariantid)
      REFERENCES multiparttaskvariant (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION;
      
ALTER TABLE multiparttaskvariant DROP COLUMN IF EXISTS score;
ALTER TABLE multiparttaskvariant ADD COLUMN partorder integer;

-- Change from CB team
ALTER TABLE taskvariantsfoils ADD  COLUMN multiparttaskvariantid bigint;
ALTER TABLE taskvariantsfoils ADD  CONSTRAINT tasksfoils_multiparttaskvariantid_fkey FOREIGN KEY (multiparttaskvariantid)
      REFERENCES multiparttaskvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;