
--CB ETL changes adding gradebandid in taskvariant table 
ALTER TABLE taskvariant ADD COLUMN gradebandid bigint;

ALTER TABLE taskvariant
  ADD CONSTRAINT taskvariant_gradebandid_fkey FOREIGN KEY (gradebandid)
      REFERENCES gradeband (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE test ADD phase character varying(20);
ALTER TABLE test ADD contentpool character varying(20);
ALTER TABLE test ADD minimumnumberofees integer;

CREATE TABLE lmassessmentmodelrule(
  testid bigint NOT NULL,                                                                                                                                                  
  ranking bigint NOT NULL,                                                                                                                                                                                                              
  contentframeworkdetailid bigint NOT NULL,                                                                                                                                      
  operator character varying(5),                              
  contentcodecorder bigint ,
  CONSTRAINT lmassessmentmodelrule_pkey PRIMARY KEY (testid,ranking,contentframeworkdetailid),
  CONSTRAINT lmassessmentmodelrule_testid_fk1 FOREIGN KEY (testid)
      REFERENCES test (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
      CONSTRAINT lmassessmentmodelrule_contentframeworkdetailid_fk2 FOREIGN KEY (contentframeworkdetailid)
      REFERENCES contentframeworkdetail (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION);
      
CREATE INDEX idx_student_assessmentprogramid ON student USING btree (assessmentprogramid);
CREATE INDEX idx_survey_studentid ON survey USING btree (studentid);
CREATE INDEX idx_survey_status ON survey USING btree (status);      