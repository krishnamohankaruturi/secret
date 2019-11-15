-- US14291 : Add start and end date to Test and Test Section.

ALTER TABLE studentstests ADD COLUMN startdatetime timestamp without time zone;
ALTER TABLE studentstests ADD COLUMN enddatetime timestamp without time zone;

ALTER TABLE studentstestsections ADD COLUMN startdatetime timestamp without time zone;
ALTER TABLE studentstestsections ADD COLUMN enddatetime timestamp without time zone;