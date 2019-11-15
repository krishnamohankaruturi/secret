--ddl/717.sql
 --F559 DDL---

DROP TABLE IF EXISTS studentmetametricsmeasuresscore;

CREATE TABLE studentmetametricsmeasuresscore
(
  id bigserial NOT NULL,
  assessmentprogramid bigint,
  schoolyear bigint,
  gradeid bigint,
  gradecode character varying(100),
  subjectid bigint,
  scalescore bigint NOT NULL,
  researchmeasure character varying(30),
  reportedmeasure character varying(30),
  lowerrange character varying(30),
  upperrange character varying(30),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser integer,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser integer,
  CONSTRAINT pk_student_metametricsmeasures_score PRIMARY KEY (id),
  CONSTRAINT assessmentprogram_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_student_metametricsmeasures_score_crdusr FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_student_metametricsmeasures_score_updusr FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT grade_fk FOREIGN KEY (gradeid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT subject_fk FOREIGN KEY (subjectid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE INDEX idx1_studentmetametricsmeasuresscore ON studentmetametricsmeasuresscore USING btree(assessmentprogramid);
CREATE INDEX idx2_studentmetametricsmeasuresscore ON studentmetametricsmeasuresscore USING btree(schoolyear);
CREATE INDEX idx3_studentmetametricsmeasuresscore ON studentmetametricsmeasuresscore USING btree(gradeid);
CREATE INDEX idx4_studentmetametricsmeasuresscore ON studentmetametricsmeasuresscore  USING btree(subjectid);



DROP FUNCTION IF EXISTS import_metametrics_measure(text, text);

CREATE OR REPLACE FUNCTION import_metametrics_measure(subject_code text,file_path text)  RETURNS VOID AS  
$BODY$
DECLARE
	ceteSysadmin_id BIGINT;
	assessment_program_id BIGINT;
	school_year BIGINT;
	subject_id BIGINT;
	grade_id BIGINT;	
	gradecodeRecord RECORD;	
	
    BEGIN
    
      select distinct id from assessmentprogram  where abbreviatedname  = 'KAP' limit 1 INTO assessment_program_id;
      select extract(year from now())::int INTO school_year;     
      SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO ceteSysadmin_id;
      select distinct id from contentarea  where abbreviatedname  = subject_code limit 1 INTO subject_id;    

      RAISE NOTICE 'Started processing %', file_path;       

      FOR gradecodeRecord IN (select distinct gradecode from studentmetametricsmeasuresscore where subjectid is null) LOOP
	
			select distinct id from gradecourse  where contentareaid  = subject_id	and activeflag is true and abbreviatedname = gradecodeRecord.gradecode INTO grade_id;

			     RAISE NOTICE 'grade_id %', grade_id; 

				   UPDATE studentmetametricsmeasuresscore
					SET
					assessmentprogramid = assessment_program_id,
					schoolyear = school_year,
					subjectid = subject_id,	
					gradeid = grade_id,	
					createduser = ceteSysadmin_id,          
					modifieduser = ceteSysadmin_id 
				   where gradecode = gradecodeRecord.gradecode and subjectid is null;      

      END LOOP;         
     
   RAISE NOTICE 'Process Completed';  
   
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

