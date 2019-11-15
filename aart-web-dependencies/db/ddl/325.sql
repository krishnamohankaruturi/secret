--Table structure redesign for test type subject area assessment relations
DROP TABLE testtypeassessment;
ALTER TABLE testtypesubjectarea ADD COLUMN assessmentid bigint;
ALTER TABLE testtypesubjectarea DROP CONSTRAINT uk_testtypesubjectarea_testtypeid_subjectareaid;

ALTER TABLE testtypesubjectarea
  ADD CONSTRAINT uk_testtypesubjectarea_testtypeid_subjectareaid_assessmentid UNIQUE(testtypeid, subjectareaid, assessmentid, activeflag);