--424.sql Change pond

/************ US16239 *************/
create table userassessmentprograms
(
	id 				bigserial NOT NULL,
	aartuserid				bigint NOT NULL,	
	assessmentprogramid		bigint NOT NULL,	
	activeflag 			boolean DEFAULT true,
	
  CONSTRAINT pk_userassessmentprograms PRIMARY KEY (id),
  CONSTRAINT userassmentprgm_aartuserid_fk FOREIGN KEY (aartuserid)
      REFERENCES aartuser (id), 
  CONSTRAINT userassmentprgm_assessmentprogramid_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram(id)
);
/************ US16239 *************/