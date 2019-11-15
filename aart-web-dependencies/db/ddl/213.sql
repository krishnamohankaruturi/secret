CREATE TABLE batchregistration
	(
	  id bigserial NOT NULL,
	  submissiondate timestamp with time zone NOT NULL DEFAULT now(),
	  status character varying(200),
	  assessmentprogram bigint,
	  testingprogram bigint,
	  assessment bigint,
	  testtype bigint,
	  subject bigint,
	  grade bigint,
	  successcount integer,
	  failedcount integer,
	  createddate timestamp with time zone NOT NULL DEFAULT now(),
	  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
	  createduser bigint, 
	CONSTRAINT batchregistration_pk PRIMARY KEY (id),
	CONSTRAINT batchregistration_createduser_fkey FOREIGN KEY (createduser)
		REFERENCES aartuser (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT batchregistration_testingprogram_fkey FOREIGN KEY (testingprogram)
      	REFERENCES testingprogram (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT batchregistration_assessmentprogram_fkey FOREIGN KEY (assessmentprogram)
	    REFERENCES assessmentprogram (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT batchregistration_assessment_fkey FOREIGN KEY (assessment)
		REFERENCES assessment (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT batchregistration_testtype_fkey FOREIGN KEY (testtype)
	    REFERENCES testtype (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT batchregistration_subject_fkey FOREIGN KEY (subject)
	    REFERENCES subjectarea (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT batchregistration_grade_fkey FOREIGN KEY (grade)
	    REFERENCES gradecourse (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION);
	    
	    
create table batchregistrationreason(
   batchregistrationid bigserial,
   studentid bigint,
   firstname character varying(100),
   lastname character varying(100),
   reason character varying(5000),
   CONSTRAINT batchregistrationreason_batchregistrationid_fkey FOREIGN KEY (batchregistrationid)
			REFERENCES batchregistration(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION);

create table batchregisteredtestsessions(
     batchregistrationid bigserial,
     testsessionid bigint,
     CONSTRAINT batchregisteredtestsessions_batchregistrationid_fkey FOREIGN KEY (batchregistrationid)
			REFERENCES batchregistration(id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION);
