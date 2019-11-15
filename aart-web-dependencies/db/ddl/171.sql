
--CB changes
ALTER TABLE test ADD COLUMN maxattempts smallint;
ALTER TABLE test ADD COLUMN maxtimesaday smallint;
ALTER TABLE test ADD COLUMN feedbackneeded boolean;
ALTER TABLE test ADD COLUMN outcometypecode character varying(75);
ALTER TABLE test ADD COLUMN nbrofoutcomes smallint;

CREATE TABLE testfeedbackrules
(
  testid bigint NOT NULL,
  displayscore boolean,
  totalscorerangeminvalue smallint,
  totalscorerangemaxvalue smallint,
  percentagerangeminvalue numeric,
  percentagerangemaxvalue numeric,  
  feedbacktext text,
  CONSTRAINT testfeedbackrules_testid_fkey FOREIGN KEY (testid)
      REFERENCES test (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE studentstests ADD scores text;
ALTER TABLE studentstestsections ADD scores text;