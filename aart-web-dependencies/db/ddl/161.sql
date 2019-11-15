
--US14216
alter table testcollection add column gradebandid bigint;
alter table testcollection add   CONSTRAINT testcollection_gradebandid_fkey FOREIGN KEY (gradebandid)
      REFERENCES gradeband (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;