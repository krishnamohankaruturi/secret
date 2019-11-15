--Batch UI cPass Grades
CREATE TABLE gradecontentareatesttypesubjectarea(
  contentareatesttypesubjectareaid bigint not null,
  gradecourseid bigint NOT NULL,
  createduser bigint,
  createdate timestamp with time zone,
  modifieddate timestamp with time zone,
  modifieduser bigint,
  activeflag boolean DEFAULT true,
  CONSTRAINT pk_gradecontentareatesttypesubjectarea_id PRIMARY KEY (contentareatesttypesubjectareaid, gradecourseid),
  CONSTRAINT fk_gradecontentareatesttypesubjectarea_gradecourseid FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION);
      
ALTER TABLE modulereport ADD COLUMN organizationid bigint;
ALTER TABLE modulereport ADD COLUMN organizationtypeid bigint;      

ALTER TABLE batchregistration ADD COLUMN contentareaid bigint;

ALTER TABLE batchregistration
  ADD CONSTRAINT batchregistration_contentarea_fkey FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      