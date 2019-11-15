
--for CB
CREATE TABLE compositestimulusvariant
(
  compositestimulusvariantid bigint NOT NULL,
  stimulusvariantid bigint NOT NULL,
  CONSTRAINT compositestimulusvariant_compositestimulusvariantid_fkey FOREIGN KEY (compositestimulusvariantid)
      REFERENCES stimulusvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION, 
  CONSTRAINT compositestimulusvariant_stimulusvariantid_fkey FOREIGN KEY (stimulusvariantid)
      REFERENCES stimulusvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
