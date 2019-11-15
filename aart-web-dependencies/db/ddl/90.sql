
--US13081 Name:  Auto registration - Auto register General population for High School and K-8 Assessments


ALTER TABLE autoregistrationcriteria ADD COLUMN gradecourseid bigint;

ALTER TABLE autoregistrationcriteria ADD CONSTRAINT fk_autoregistrationcriteria_gradecourseid FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE autoregistrationcriteria DROP CONSTRAINT uk_autoregistrationcriteria_ap_tp_asst_cattsa;

ALTER TABLE autoregistrationcriteria ADD CONSTRAINT uk_autoregistrationcriteria_ap_tp_asst_cattsa 
	UNIQUE (assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid);

