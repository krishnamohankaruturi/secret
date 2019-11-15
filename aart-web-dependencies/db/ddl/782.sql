-----------------------------------------FRANKLIN RELEASE DDL SCRIPTS-----------------------------------------------------------------------

-----------------------------------------F557 DDL SCRIPTS-----------------------------------------------------------------------------------
-----------------------------------------CREATE TABLE EXTRACTASSESSMENTPROGRAM--------------------------------------------------------------

CREATE TABLE public.extractassessmentprogram
(
    id bigserial,
    extractname character varying(100) COLLATE pg_catalog."default" NOT NULL,
    extracttypeid bigint NOT NULL,
    assessmentprogramid bigint NOT NULL,
    createdate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    readytoview boolean DEFAULT false,
    activeflag boolean DEFAULT true,
    stateid bigint,
    authorityid bigint,
    CONSTRAINT pk_extractassessmentprogram PRIMARY KEY (id),
    CONSTRAINT assessmentprogramid_extracttypeid_stateid UNIQUE (assessmentprogramid, extracttypeid, stateid),
    CONSTRAINT extractassessmentprogram_assessmentprogramid_fk FOREIGN KEY (assessmentprogramid)
        REFERENCES public.assessmentprogram (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT extractassessmentprogram_authorityid_fk FOREIGN KEY (authorityid)
        REFERENCES public.authorities (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT extractassessmentprogram_stateid_fk FOREIGN KEY (stateid)
        REFERENCES public.organization (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE INDEX idx_extractassessmentprogram_extractname
    ON public.extractassessmentprogram USING btree
    (extractname)
    TABLESPACE pg_default;

CREATE INDEX idx_extractassessmentprogram_extracttypeid
    ON public.extractassessmentprogram USING btree
    (extracttypeid)
    TABLESPACE pg_default;

CREATE INDEX idx_extractassessmentprogram_assessmentprogramid
    ON public.extractassessmentprogram USING btree
    (assessmentprogramid)
    TABLESPACE pg_default;

CREATE INDEX idx_extractassessmentprogram_activeflag
    ON public.extractassessmentprogram USING btree
    (activeflag)
    TABLESPACE pg_default;

CREATE INDEX idx_extractassessmentprogram_stateid
    ON public.extractassessmentprogram USING btree
    (stateid)
    TABLESPACE pg_default;

CREATE INDEX idx_extractassessmentprogram_authorityid
    ON public.extractassessmentprogram USING btree
    (authorityid)
    TABLESPACE pg_default;
	
-----------------------------------------CREATE TABLE EXTRACTASSESSMENTPROGRAMGROUP--------------------------------------------------------

CREATE TABLE public.extractassessmentprogramgroup
(
    id bigserial,
    extractassessmentprogramid bigint NOT NULL,
    groupid bigint NOT NULL,
    activeflag boolean DEFAULT true,
    CONSTRAINT pk_extractassessmentprogramgroup PRIMARY KEY (id),
    CONSTRAINT extractassessmentprogramgroup_extractassessmentprogramid_fk FOREIGN KEY (extractassessmentprogramid)
        REFERENCES public.reportassessmentprogram (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT extractassessmentprogramgroup_groupsid_fk FOREIGN KEY (groupid)
        REFERENCES public.groups (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE INDEX idx_extractassessmentprogramgroup_activeflag
    ON public.extractassessmentprogramgroup USING btree
    (activeflag)
    TABLESPACE pg_default;

CREATE INDEX idx_extractassessmentprogramgroup_extractassessmentprogramid
    ON public.extractassessmentprogramgroup USING btree
    (extractassessmentprogramid)
    TABLESPACE pg_default;

CREATE INDEX idx_extractassessmentprogramgroup_groupid
    ON public.extractassessmentprogramgroup USING btree
    (groupid)
    TABLESPACE pg_default;	

	
-------------------------------------------CREATE FUNCTION EXTRACTASSESSMENTPROGRAM_FN-----------------------------------------------------


CREATE OR REPLACE FUNCTION public.extractassessmentprogram_fn(
	extractname text,
	extracttypeid bigint,
	assessmentprogramcode text,
	authoritycode text)
    RETURNS integer
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$

declare 
 assessmentprogram_id bigint;
 stateid bigint;
 authority_id bigint;
BEGIN

 Select into assessmentprogram_id (Select id from assessmentprogram where abbreviatedname ilike (assessmentprogramcode) and activeflag is true);
 Select into authority_id (select id from authorities where authority ilike (authoritycode) and activeflag is true limit 1);

 FOR stateid IN(Select orgass.organizationid from orgassessmentprogram orgass 
  inner join organization org on org.id = orgass.organizationid 
  where assessmentprogramid in (Select id from assessmentprogram 
  where abbreviatedname ilike (assessmentprogramcode) and activeflag is true) and organizationtypeid = 2 and orgass.activeflag is true 
  and org.activeflag is true)
 LOOP
  INSERT INTO extractassessmentprogram( extractname,
  extracttypeid, assessmentprogramid, createdate, modifieddate, 
  readytoview, activeflag, stateid, authorityid)
  VALUES ( extractname, extracttypeid, assessmentprogram_id, current_timestamp, current_timestamp, 
  false,true, stateid, authority_id);
 END LOOP;
RETURN 0;
 EXCEPTION 
  WHEN unique_violation THEN
 RAISE NOTICE 'unique key violation constraint';
RETURN 0;
    WHEN OTHERS THEN
RETURN 1;
 END;
 
$BODY$;

-------------------------------------------------------F744 DDL SCRIPTS---------------------------------------------------------------------
------------------------------------------------------- ALERT REPORTPROCESS TABLE-----------------------------------------------------------
ALTER TABLE reportprocess ADD COLUMN count BIGINT;

ALTER TABLE reportprocess ADD COLUMN state character varying;

-------------------------------------------------------F787 DDL SCRIPTS---------------------------------------------------------------------
-------------------------------------------------------ADD COLUMN IN UPLOADGRFFILE AND TEMPUPLOADGRFFILE------------------------------------

alter table tempuploadgrffile add column accountabilityDistrictIdentifier character varying;
alter table uploadgrffile add column accountabilityDistrictIdentifier character varying;

alter table tempuploadgrffile add column course character varying;
alter table uploadgrffile add column course character varying;

alter table tempuploadgrffile add column stateuse character varying;
alter table uploadgrffile add column stateuse character varying;


------------------------------------------------------------F788 DDL Scripts----------------------------------------------------------------
------------------------------------------------------------ DROP TABLE ACTDESCRIPTION-----------------------------------------------------

CREATE TABLE public.actdescription
(
    id bigserial,
    subjectid bigint,
    gradeid bigint,
    assessmentprogramid bigint,
    schoolyear bigint,
    description character varying COLLATE pg_catalog."default",
    descriptionorder integer NOT NULL,
    createduser integer NOT NULL,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    activeflag boolean DEFAULT true,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    modifieduser integer NOT NULL,
    CONSTRAINT actdescription_pkey PRIMARY KEY (id),
    CONSTRAINT actdescription_assessmentprogram_fkey FOREIGN KEY (assessmentprogramid)
        REFERENCES public.assessmentprogram (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT actdescription_grade_fkey FOREIGN KEY (gradeid)
        REFERENCES public.gradecourse (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT actdescription_subject_fkey FOREIGN KEY (subjectid)
        REFERENCES public.contentarea (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

------------------------------------------------------------DROP INDEX IDX_ACTDESCRIPTION_SUBJECTID----------------------------------------

CREATE INDEX idx_actdescription_subjectid
    ON public.actdescription USING btree
    (subjectid)
    TABLESPACE pg_default;	
    
------------------------------------------------------------ DROP INDEX IDX_ACTDESCRIPTION_GRADEID-----------------------------------------

CREATE INDEX idx_actdescription_gradeid
    ON public.actdescription USING btree
    (gradeid)
    TABLESPACE pg_default;		

------------------------------------------------------------ DROP INDEX IDX_ACTDESCRIPTION_ASSESSMENTPROGRAMID ---------------------------

CREATE INDEX idx_actdescription_assessmentprogramid
    ON public.actdescription USING btree
    (assessmentprogramid)
    TABLESPACE pg_default;	

------------------------------------------------------------ DROP INDEX IDX_ACTDESCRIPTION_SCHOOLYEAR-------------------------------------

CREATE INDEX idx_actdescription_schoolyear
    ON public.actdescription USING btree
    (schoolyear)
    TABLESPACE pg_default;	
    
------------------------------------------------------------ DROP INDEX IDX_ACTDESCRIPTION_DESCRIPTION------------------------------------

CREATE INDEX idx_actdescription_description
    ON public.actdescription USING btree
    (description)
    TABLESPACE pg_default;	

------------------------------------------------------------ DROP TABLE ACTLEVEL-----------------------------------------------------------

CREATE TABLE public.actlevel
(
    id bigserial,
    levelid bigint,
    createduser integer NOT NULL,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    activeflag boolean DEFAULT true,
    modifieduser integer NOT NULL,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    CONSTRAINT actlevel_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

------------------------------------------------------------ DROP INDEX ACTLEVEL_LEVELDID--------------------------------------------------

CREATE INDEX idx_actlevel_levelid
    ON public.actlevel USING btree
    (levelid)
    TABLESPACE pg_default;	

------------------------------------------------------------ DROP TABLE ACTDESCRIPTIONLEVELSCORE------------------------------------------

CREATE TABLE public.actdescriptionlevelscore
(
    id bigserial,
    actdescriptionid bigint NOT NULL,
    actlevelid bigint,
    maxvalue integer,
    minvalue integer,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    createduser integer NOT NULL,
    activeflag boolean DEFAULT true,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    modifieduser integer NOT NULL,
    CONSTRAINT actdescriptionlevelscore_pkey PRIMARY KEY (id),
    CONSTRAINT actdescriptionlevelscore_actdescription_fkey FOREIGN KEY (actdescriptionid)
        REFERENCES public.actdescription (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT actdescriptionlevelscore_actlevel_fkey FOREIGN KEY (actlevelid)
        REFERENCES public.actlevel (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

   
------------------------------------------------------------ DROP INDEX ACTDESCRIPTIONLEVELSCORE_ACTDESCRIPTIONID--------------------------

CREATE INDEX idx_actdescriptionlevelscore_actdescriptionid
    ON public.actdescriptionlevelscore USING btree
    (actdescriptionid)
    TABLESPACE pg_default;

-- DROP INDEX public.idx_actdescriptionlevelscore_actlevelid;

CREATE INDEX idx_actdescriptionlevelscore_actlevelid
    ON public.actdescriptionlevelscore USING btree
    (actlevelid)
    TABLESPACE pg_default;


-----------------------------------------------------------------------------------------------------------------------------------------
	
-------------------------------------------------------F885 DDL SCRIPTS-------------------------------------------------------------------
-------------------------------------------------------CREATE TABLE PERMISSIONUPLOADFILE--------------------------------------------------	
	
CREATE TABLE public.permissionsuploadfile
(
id bigserial,
batchuploadid bigint,
headercolumn json,
createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
createduser integer NOT NULL,
activeflag boolean DEFAULT true,
CONSTRAINT pk_permissionsuploadfile PRIMARY KEY (id)
)
WITH (
OIDS = FALSE
)
TABLESPACE pg_default;

------------------------------------------------------- Index: IDX_PERMISSIONSUPLOADFILE_BATCHUPLOADID---------------------------------------
------------------------------------------------------- DROP INDEX public.IDX_PERMISSIONSUPLOADFILE_BATCHUPLOADID----------------------------

CREATE INDEX idx_permissionsuploadfile_batchuploadid
ON public.permissionsuploadfile USING btree
(batchuploadid)
TABLESPACE pg_default;

------------------------------------------------------- Index: IDX_PERMISSIONSUPLOADFILE_ID-------------------------------------------------
------------------------------------------------------- DROP INDEX public.IDX_PERMISSIONSUPLOADFILE_ID--------------------------------------

CREATE INDEX idx_permissionsuploadfile_id
ON public.permissionsuploadfile USING btree
(id)
TABLESPACE pg_default;
-------------------------------------------------------------------------------------------------------------------------------------------



-------------------------------------------------------------GRF PROCESS FUNCTIONS FOR VALIDATION------------------------------------------

-------------------------------------------------------------GRF FILE MAIN VALIDATION------------------------------------------

-- FUNCTION: public.grf_file_main_validator(bigint, bigint, text, bigint, bigint, bigint)

-- DROP FUNCTION public.grf_file_main_validator(bigint, bigint, text, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION public.grf_file_main_validator(
	uploadstateid bigint,
	uploadbatchid bigint,
	uploadtype text,
	assessmentprogramid bigint,
	uploadreportyear bigint,
	createduser bigint)
    RETURNS SETOF record 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$

DECLARE
  ref record;
  valid boolean = true;
BEGIN  
    RAISE NOTICE 'Process starting';
    --Check the row count......If original GRF then no need of row count check 
     IF (uploadtype = 'Original GRF' OR (select (select count(*) from tempuploadgrffile where batchuploadid = uploadbatchid) = (select count(*) from uploadgrffile ugf where ugf.stateid = uploadstateid and ugf.reportyear = uploadreportyear and recentversion is true) as rowcount) is true)   THEN
        RAISE NOTICE 'inside if';

          IF valid is true THEN--1
	--Update all subject code Math to M
	      update tempuploadgrffile set subject = 'M' where upper(subject) = 'MATH' and batchuploadid = uploadbatchid;
		   
	--Primary Validation
	IF uploadtype = 'Original GRF' THEN		
		For ref in select batchuploadid, line, fieldname, reason, errortype  
		   from GRF_File_Common_Validator(uploadstateid, uploadbatchid, uploadreportyear)
		   LOOP
			 return next ref;
			
		   END LOOP;
		   
			  RAISE NOTICE 'Primary Validation Original GRF Completed: %', valid;
		END IF;
	
	IF uploadtype = 'Updated GRF' THEN		
		For ref in select batchuploadid, line, fieldname, reason, errortype  
		   from GRF_File_Column_Change_Validator(uploadstateid, uploadbatchid, uploadreportyear)
		   LOOP
			 return next ref;
			
		   END LOOP;
		   
			  RAISE NOTICE 'Primary Validation Updated GRF Completed: %', valid;
		END IF;
	
	      IF valid is true THEN--2
		   
			   --secondary/custom validation
				  For ref in select batchuploadid, line, fieldname, reason, errortype  
			   from GRF_File_Custom_Validator(uploadstateid, uploadbatchid,uploadreportyear)
			   LOOP
				 return next ref;
				 
			   END LOOP;

			  RAISE NOTICE 'Secondary Validation Completed: %', valid;
	      
		  IF valid is true THEN--3
		  --Check duplicate entry for student and subject combination
	 	  FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'Kite_Student_Identifier'::text as fieldname,(select attrvalue from appconfiguration where attrcode = 'GRF_MAIN_VAL_STUDENT_SUBJECT_DUPLICATE')::text as reason,  'reject'::text as errortype 
	 	              from tempuploadgrffile tmp
	 	              inner join (select studentid,subject 
	 	                          from tempuploadgrffile 
	 	                          where batchuploadid = uploadbatchid 
	 	                          group by studentid, subject 
	 	                          having count(studentid) >1) selrow on coalesce(selrow.subject::text,' ') = coalesce(tmp.subject::text,' ') 
	 	                          and tmp.studentid = selrow.studentid
	 	              where tmp.batchuploadid = uploadbatchid              
		  LOOP
		     return next ref;
		    
		  END LOOP;
		  
		  RAISE NOTICE 'duplicate entry for Kite_Student_Identifier and subject validation Completed: %', valid;
		  
		  if valid is true THEN--4
		       --Validate Unique External row identifier uniqueness
		       FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'Unique_Row_Identifier'::text as fieldname,(select attrvalue from appconfiguration where attrcode = 'GRF_MAIN_VAL_UNIQUEROWIDENTIFIER_UNIQUENESS')::text as reason,  'reject'::text as errortype 
	 	                    from tempuploadgrffile tmp
	 	                    inner join (select uniquerowidentifier 
	 	                                from tempuploadgrffile
	 	                                where batchuploadid = uploadbatchid 
	 	                                group by uniquerowidentifier 
	 	                                having count(uniquerowidentifier) >1) selrow on selrow.uniquerowidentifier = tmp.uniquerowidentifier
	 	                    where tmp.batchuploadid = uploadbatchid  
		       LOOP
		           return next ref;
			  
		       END LOOP;
		       RAISE NOTICE 'Unique External row identifier uniqueness validation Completed: %', valid;     
		       if valid is true THEN--5
			    --Validate statestudentidentifier should not map to multiple studentid
		            FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'State_Student_Identifier'::text as fieldname,(select attrvalue from appconfiguration where attrcode = 'GRF_MAIN_VAL_STATE_KITE_STUDENT_IDENTIFIER_MULT_MAP')::text as reason,  'reject'::text as errortype 
	 	                    from tempuploadgrffile tmp
	 	                    inner join (select count(distinct studentid),statestudentidentifier 
	 	                                from tempuploadgrffile
	 	                                where batchuploadid = uploadbatchid 
	 	                                group by statestudentidentifier 
	 	                                having count(distinct studentid) >1) selrow on selrow.statestudentidentifier = tmp.statestudentidentifier
	 	                    where tmp.batchuploadid = uploadbatchid            
			    LOOP
			        return next ref;
			      
			    END LOOP;
			    RAISE NOTICE 'statestudentidentifier should not map to multiple Kite_Student_Identifier validation Completed: %', valid;
			    if valid is true THEN--6
			         --Validate studentid should not map to multiple statestudentidentifier
				 FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'Kite_Student_Identifier'::text as fieldname,(select attrvalue from appconfiguration where attrcode = 'GRF_MAIN_VAL_KITE_STATE_STUDENT_IDENTIFIER_MULT_MAP')::text as reason,  'reject'::text as errortype 
	 	                    from tempuploadgrffile tmp
	 	                    inner join (select studentid, count(distinct statestudentidentifier) 
	 	                                from tempuploadgrffile
	 	                                where batchuploadid = uploadbatchid  
	 	                                group by studentid 
	 	                                having count(distinct statestudentidentifier) >1)selrow on selrow.studentid = tmp.studentid
	 	                    where tmp.batchuploadid = uploadbatchid
				 LOOP
				     return next ref; 
				    
				 END LOOP;
				 
			         RAISE NOTICE 'Validate Kite_Student_Identifier should not map to multiple statestudentidentifier validation Completed: %', valid;     			        										   
			--If updated GRF then delete all matching records from temp table
				
			IF uploadtype = 'Updated GRF' THEN			
				DELETE FROM tempuploadgrffile tmp
					USING uploadgrffile ugf
					WHERE tmp.batchuploadid = uploadbatchid and ugf.stateid = uploadstateid and ugf.reportyear = uploadreportyear and ugf.recentversion is true and coalesce(tmp.studentid::text,' ')=coalesce(ugf.studentid::text,' ') and
					coalesce(tmp.statestudentidentifier::text,' ')=coalesce(ugf.statestudentidentifier::text,' ') and
					coalesce(tmp.aypschoolidentifier::text,' ')=coalesce(ugf.aypschoolidentifier::text,' ') and
					coalesce(tmp.accountabilitydistrictidentifier::text,' ')=coalesce(ugf.accountabilitydistrictidentifier::text,' ') and
					coalesce(tmp.localstudentidentifier::text,' ')=coalesce(ugf.localstudentidentifier::text,' ') and
					coalesce(tmp.currentgradelevel::text,' ')=coalesce(ugf.currentgradelevel::text,' ') and
					coalesce(tmp.studentlegalfirstname::text,' ')=coalesce(ugf.studentlegalfirstname::text,' ') and
					coalesce(tmp.studentlegalmiddlename::text,' ')=coalesce(ugf.studentlegalmiddlename::text,' ') and
					coalesce(tmp.studentlegallastname::text,' ')=coalesce(ugf.studentlegallastname::text,' ') and
					coalesce(tmp.generationcode::text,' ')=coalesce(ugf.generationcode::text,' ') and
					coalesce(tmp.username::text,' ')=coalesce(ugf.username::text,' ') and
					coalesce(tmp.firstlanguage::text,' ')=coalesce(ugf.firstlanguage::text,' ') and
					coalesce(tmp.gender::text,' ')=coalesce(ugf.gender::text,' ') and
					coalesce(tmp.comprehensiverace::text,' ')=coalesce(ugf.comprehensiverace::text,' ') and
					coalesce(tmp.hispanicethnicity::text,' ')=coalesce(ugf.hispanicethnicity::text,' ') and
					coalesce(tmp.primarydisabilitycode::text,' ')=coalesce(ugf.primarydisabilitycode::text,' ') and
					coalesce(tmp.esolparticipationcode::text,' ')=coalesce(ugf.esolparticipationcode::text,' ') and
					coalesce(tmp.attendanceschoolprogramidentifier::text,' ')=coalesce(ugf.attendanceschoolprogramidentifier::text,' ') and
					coalesce(tmp.state::text,' ')=coalesce(ugf.state::text,' ') and
					coalesce(tmp.districtcode::text,' ')=coalesce(ugf.districtcode::text,' ') and
					coalesce(tmp.district::text,' ')=coalesce(ugf.district::text,' ') and
					coalesce(tmp.schoolcode::text,' ')=coalesce(ugf.schoolcode::text,' ') and
					coalesce(tmp.school::text,' ')=coalesce(ugf.school::text,' ') and
					coalesce(tmp.educatorfirstname::text,' ')=coalesce(ugf.educatorfirstname::text,' ') and
					coalesce(tmp.educatorlastname::text,' ')=coalesce(ugf.educatorlastname::text,' ') and
					coalesce(tmp.educatorusername::text,' ')=coalesce(ugf.educatorusername::text,' ') and
					coalesce(tmp.educatoridentifier::text,' ')=coalesce(ugf.educatoridentifier::text,' ') and
					coalesce(tmp.kiteeducatoridentifier::text,' ')=coalesce(ugf.strkiteeducatoridentifier::text,' ') and
					coalesce(tmp.subject::text,' ')=coalesce(ugf.subject::text,' ') and
					coalesce(tmp.finalband::text,' ')=coalesce(ugf.finalband::text,' ') and
					coalesce(tmp.sgp::text,' ')=coalesce(ugf.sgp::text,' ') and
					coalesce(tmp.nyperformancelevel::text,' ')=coalesce(ugf.nyperformancelevel::text,' ') and
					coalesce(tmp.invalidationcode::text,' ')=coalesce(ugf.invalidationcode::text,' ') and
					coalesce(tmp.totallinkagelevelsmastered::text,' ')=coalesce(ugf.totallinkagelevelsmastered::text,' ') and 
					coalesce(tmp.schoolentrydate::text,' ')=coalesce(to_char(ugf.schoolentrydate, 'mm/dd/yyyy')::text,' ') and
					coalesce(tmp.districtentrydate::text,' ')=coalesce(to_char(ugf.districtentrydate, 'mm/dd/yyyy')::text,' ') and
					coalesce(tmp.stateentrydate::text,' ')=coalesce(to_char(ugf.stateentrydate, 'mm/dd/yyyy')::text,' ') and
					coalesce(tmp.dateofbirth::text,' ')=coalesce(to_char(ugf.dateofbirth, 'mm/dd/yyyy')::text,' ') and
					coalesce(tmp.exitwithdrawaldate::text,' ')=coalesce(to_char(ugf.exitwithdrawaldate, 'mm/dd/yyyy')::text::text,' ') and
					coalesce(tmp.iowalinkagelevelsmastered::text,' ')=coalesce(ugf.iowalinkagelevelsmastered::text,' ') and
					coalesce(tmp.exitwithdrawalcode::text,' ')=coalesce(ugf.exitwithdrawalcode::text,' ') and
					coalesce(tmp.stateuse::text,' ')=coalesce(ugf.stateuse::text,' ') and
					coalesce(tmp.course::text,' ')=coalesce(ugf.course::text,' ') and
					(ugf.gradechange is true OR coalesce(tmp.performancelevel::text,' ')=coalesce(ugf.performancelevel::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee1::text,' ')=coalesce(ugf.ee1::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee2::text,' ')=coalesce(ugf.ee2::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee3::text,' ')=coalesce(ugf.ee3::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee4::text,' ')=coalesce(ugf.ee4::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee5::text,' ')=coalesce(ugf.ee5::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee6::text,' ')=coalesce(ugf.ee6::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee7::text,' ')=coalesce(ugf.ee7::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee8::text,' ')=coalesce(ugf.ee8::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee9::text,' ')=coalesce(ugf.ee9::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee10::text,' ')=coalesce(ugf.ee10::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee11::text,' ')=coalesce(ugf.ee11::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee12::text,' ')=coalesce(ugf.ee12::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee13::text,' ')=coalesce(ugf.ee13::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee14::text,' ')=coalesce(ugf.ee14::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee15::text,' ')=coalesce(ugf.ee15::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee16::text,' ')=coalesce(ugf.ee16::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee17::text,' ')=coalesce(ugf.ee17::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee18::text,' ')=coalesce(ugf.ee18::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee19::text,' ')=coalesce(ugf.ee19::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee20::text,' ')=coalesce(ugf.ee20::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee21::text,' ')=coalesce(ugf.ee21::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee22::text,' ')=coalesce(ugf.ee22::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee23::text,' ')=coalesce(ugf.ee23::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee24::text,' ')=coalesce(ugf.ee24::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee25::text,' ')=coalesce(ugf.ee25::text,' ')) and
					(ugf.gradechange is true OR coalesce(tmp.ee26::text,' ')=coalesce(ugf.ee26::text,' ')) and
					coalesce(tmp.uniquerowidentifier::text,' ')=coalesce(ugf.externaluniquerowidentifier::text,' ');
					
					RAISE NOTICE 'Delete Matching Record Validation Completed: %', valid;
				END IF;
				
				IF uploadtype = 'Updated GRF' THEN		
							FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'Kite_Student_Identifier'::text as fieldname,(select attrvalue from appconfiguration where attrcode = 'GRF_UPD_VAL_KITE_STD_ID_SUB_UNIQ_MISMATCH_ORG_UPLOAD')::text as reason,  'reject'::text as errortype 
								from tempuploadgrffile tmp
								left join uploadgrffile ugf on tmp.uniquerowidentifier = ugf.externaluniquerowidentifier::text 
										   and lower(coalesce(tmp.subject::text,' ')) = lower(coalesce(ugf.subject::text,' ')) 
										   and tmp.studentid= ugf.studentid::text
										   and ugf.recentversion is true
										   and ugf.reportyear = uploadreportyear
								where ugf.studentid is null and tmp.batchuploadid = uploadbatchid  
						   LOOP
							return next ref; 
						
						   END LOOP;
						RAISE NOTICE 'Validate externaluniquerowidentifier, Kite_Student_Identifier, subject combination with original file.....Extra new student also will be rejected Completed: %', valid;
		   
					RAISE NOTICE 'Updated Upload GRF Validation Completed: %', valid;
				END IF;
		
		       END IF;--6    
		  END IF;--5		  
	    END IF;--4	  
          END IF;--3     
			END IF;--2
			END IF;--1
     ELSE
     RAISE NOTICE 'Inside ELSE';
	For ref in select uploadbatchid as batchuploadid, '0'::text as line, ''::text as fieldname,'The number of uploaded rows does not match the number of rows in the original GRF.'::text as reason,  'reject'::text as errortype
	   LOOP
	     return next ref;
	     
	   END LOOP;
     END IF;

END;     

$BODY$;

ALTER FUNCTION public.grf_file_main_validator(bigint, bigint, text, bigint, bigint, bigint)
    OWNER TO aart;

-----------------------------------------------------------------------------------------------------------------------------------------


-------------------------------------------------------------GRF FILE COMMON VALIDATION--------------------------------------------------

-- FUNCTION: public.grf_file_common_validator(bigint, bigint, bigint)

-- DROP FUNCTION public.grf_file_common_validator(bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION public.grf_file_common_validator(
	stateid bigint,
	uploadbatchid bigint,
	reportyear bigint)
    RETURNS TABLE(batchuploadid bigint, line text, fieldname text, reason text, errortype text) 
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$
 
	select $2 as batchuploadid, (linenumber+1)::text as line, ' '::text as fieldname, 
           case when (attendanceSchoolProgramIdentifier::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ATTENDANCESCHOOLPROGRAMIDENTIFIER_NULL') when (SELECT length(attendanceSchoolProgramIdentifier)> 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ATTENDANCESCHOOLPROGRAMIDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(aypSchoolIdentifier)> 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ACCOUNTABILITY_SCHOOL_IDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(accountabilitydistrictidentifier)> 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ACCOUNTABILITY_DISTRICT_IDENTIFIER_LENGTH') else '' end ||		   
		   case when (SELECT length(coalesce(localstudentidentifier::text,'')) > 20) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_LOCAL_STUDENT_IDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(comprehensiveRace::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_COMPREHENSIVERACE_NULL') when coalesce(comprehensiveRace,'') not in ('1','2','4','5','6','7','8') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_COMPREHENSIVERACE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(currentGradelevel::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_CURRENT_GRADE_LEVEL_NULL') when UPPER(coalesce(currentGradelevel,'')) not in ('3','4','5','6','7','8','9','10','11','12') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_CURRENT_GRADE_LEVEL_INVALID') else '' end ||
		   case when coalesce(firstLanguage,'') not in ('','0','1','2','3','4','5','6','7','8','10','11','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_FIRSTLANGUAGE_INVALID') else '' end ||   
		   case when (dateOfBirth::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DATE_OF_BIRTH_NULL') else '' end ||
		   case when (dateOfBirth is not null and (dateOfBirth::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DATE_OF_BIRTH_FORMAT') else '' end ||
		   case when (dateOfBirth is not null and (dateOfBirth::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(dateOfBirth, 'mm/dd/yyyy') <= '01/01/1000') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DATE_OF_BIRTH_INVALID') else '' end||
		   case when (districtEntryDate is not null and (districtEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DISTRICT_ENTRY_DATE_FORMAT') else '' end ||
		   case when (districtEntryDate is not null and (districtEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(districtEntryDate, 'mm/dd/yyyy') <= '01/01/1000') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DISTRICT_ENTRY_DATE_INVALID') else '' end||
		   case when (stateEntryDate is not null and (stateEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_ENTRY_DATE_FORMAT') else '' end ||
		   case when (stateEntryDate is not null and (stateEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(stateEntryDate, 'mm/dd/yyyy') <= '01/01/1000') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_ENTRY_DATE_INVALID') else '' end||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_FORMAT') else '' end ||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') <= '01/01/1000') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_INVALID') else '' end||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') < '01/01/1989') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_1989') else '' end||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') > (current_timestamp  at time zone 'US/Central')::date) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_CURRENTDATE') else '' end||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') <= to_date (schoolEntryDate, 'mm/dd/yyyy')) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_SCHOOLDATE') else '' end||
		   case when (exitwithdrawaldate is not null  and exitwithdrawalcode is null ) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_NULL') else '' end||
		   case when (exitwithdrawalcode is not null  and exitwithdrawaldate is null ) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_NULL') else '' end||
		   case when coalesce(exitwithdrawalcode,'') not in ('','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','30','98') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(educatorIdentifier::text,'')) > 254) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_EDUCATOR_IDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(educatorIdentifier::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_EDUCATOR_IDENTIFIER_NULL') else '' end ||
		   case when (educatorFirstName::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_FIRST_NAME_NULL') else '' end || 
		   case when (educatorLastName::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_LAST_NAME_NULL') else '' end || 
		   case when (educatorUserName::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_USER_NAME_NULL') else '' end || 
		   case when (SELECT length(coalesce(educatorFirstName::text,'')) > 80) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_FIRST_NAME_LENGTH') else '' end || 
		   case when (SELECT length(coalesce(educatorLastName::text,'')) > 80) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_LAST_NAME_LENGTH') else '' end || 
		   case when (SELECT length(coalesce(educatorUserName::text,'')) > 254) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_USER_NAME_LENGTH')  else '' end || 
		   case when (SELECT length(coalesce(esolParticipationCode::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ESOLPARTICIPATIONCODE_NULL') when coalesce(esolParticipationCode,'') not in ('0','1','2','3','4','5','6') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ESOLPARTICIPATIONCODE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(uniquerowidentifier,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_UNIQUEROWIDENTIFIER_NULL') when ((coalesce(uniquerowidentifier,'') ~ '^[0-9]+$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_UNIQUEROWIDENTIFIER_INVALID') else '' end ||
		   case when (SELECT length(coalesce(uniquerowidentifier,'')) > 8) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_UNIQUEROWIDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(finalBand::text,'')) > 150) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_FINAL_BAND_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(gender::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_GENDER_NULL') when lower(coalesce(gender,'')) not in ('male','female') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_GENDER_INVALID') else '' end ||
		   case when (SELECT length(coalesce(generationCode::text,'')) > 10) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_GENERATIONCODE_LENGTH') else '' end || 
		   case when (SELECT length(coalesce(hispanicEthnicity::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_HISPANIC_ETHNICITY_NULL') when coalesce(hispanicEthnicity,'') not in ('1','0') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_HISPANIC_ETHNICITY_INVALID') else '' end || 
		   case when coalesce(invalidationCode,'') not in ('1','0') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_INVALIDATION_CODE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(studentlegalFirstName::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STUDENT_FIRST_NAME_NULL') when (SELECT length(coalesce(studentlegalFirstName::text,'')) > 60) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STUDENT_FIRST_NAME_LENGTH') else '' end || 
		   case when (SELECT length(coalesce(studentlegalLastName::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STUDENT_LAST_NAME_NULL') when (SELECT length(coalesce(studentlegalLastName::text,'')) > 60) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STUDENT_LAST_NAME_LENGTH')  else '' end || 
		   case when (SELECT length(coalesce(studentlegalMiddleName::text,'')) > 80) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STUDENT_MIDDLE_NAME_LENGTH') else '' end || 
		   case when coalesce(performanceLevel,'') not in ('1','2','3','4','9') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_PERFORMANCE_LEVEL_INVALID') else '' end || 
		   case when (SELECT length(coalesce(primaryDisabilityCode::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_PRIMARY_DISABILITY_CODE_NULL') when UPPER(coalesce(primaryDisabilityCode,'')) not in ('AM','DB','DD','ED','HI','LD','MD','ID','OH','OI','SL','TB','VI','ND','WD','EI','DA') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_PRIMARY_DISABILITY_CODE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(districtcode::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DISTRICT_CODE_NULL') when (SELECT length(coalesce(districtcode::text,'')) > 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DISTRICT_CODE_INVALID') else '' end || 
		   case when (schoolEntryDate::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SCHOOL_ENTRY_DATE_NULL') else '' end ||
		   case when (schoolEntryDate is not null and (coalesce(schoolEntryDate::text,'') ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SCHOOL_ENTRY_DATE_FORMAT') else '' end ||
		   case when (schoolEntryDate is not null and (schoolEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(schoolEntryDate, 'mm/dd/yyyy') <= '01/01/1000') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SCHOOL_ENTRY_DATE_INVALID') else '' end||
		   case when (SELECT length(coalesce(schoolcode::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SCHOOL_CODE_NULL') when (SELECT length(coalesce(schoolcode::text,'')) > 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SCHOOL_CODE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(sgp::text,'')) > 3) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_TDB_GROWTH_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(state::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_NULL') when (SELECT length(coalesce(state::text,'')) > 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_LENGTH') else '' end ||
		   case when (lower(coalesce(state::text,'')) = lower('new york') and coalesce(nyPerformanceLevel,'') not in ('21','22','23','24','9')) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_NY_PERFORMANCE_LEVEL_INVALID') else '' end ||
		   case when (SELECT length(coalesce(stateStudentIdentifier::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_NULL') when (SELECT length(coalesce(stateStudentIdentifier::text,'')) > 10) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(studentid,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_KITE_STUDENT_IDENTIFIER_NULL') when ((coalesce(studentid,'') ~ '^[0-9]+$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_KITE_STUDENT_IDENTIFIER_INVALID') else '' end ||
		   case when (SELECT length(coalesce(studentid,'')) > 10) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_KITE_STUDENT_IDENTIFIER_LENGTH') else '' end ||
		   case when (coalesce(subject::text,'') = '' and coalesce(performancelevel,'') not in ('9')) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SUBJECT_NULL') else '' end ||
		   case when (SELECT length(coalesce(userName::text,'')) > 100)  then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_USERNAME_LENGTH') else '' end || 
		   case when ((SELECT length(coalesce(ee1::text,'')) > 1) OR (ee1::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE1_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee2::text,'')) > 1) OR (ee2::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE2_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee3::text,'')) > 1) OR (ee3::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE3_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee4::text,'')) > 1) OR (ee4::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE4_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee5::text,'')) > 1) OR (ee5::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE5_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee6::text,'')) > 1) OR (ee6::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE6_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee7::text,'')) > 1) OR (ee7::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE7_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee8::text,'')) > 1) OR (ee8::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE8_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee9::text,'')) > 1) OR (ee9::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE9_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee10::text,'')) > 1) OR (ee10::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE10_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee11::text,'')) > 1) OR (ee11::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE11_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee12::text,'')) > 1) OR (ee12::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE12_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee13::text,'')) > 1) OR (ee13::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE13_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee14::text,'')) > 1) OR (ee14::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE14_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee15::text,'')) > 1) OR (ee15::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE15_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee16::text,'')) > 1) OR (ee16::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE16_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee17::text,'')) > 1) OR (ee17::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE17_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee18::text,'')) > 1) OR (ee18::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE18_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee19::text,'')) > 1) OR (ee19::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE19_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee20::text,'')) > 1) OR (ee20::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE20_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee21::text,'')) > 1) OR (ee21::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE21_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee22::text,'')) > 1) OR (ee22::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE22_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee23::text,'')) > 1) OR (ee23::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE23_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee24::text,'')) > 1) OR (ee24::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE24_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee25::text,'')) > 1) OR (ee25::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE25_INVALID') else '' end ||
		      case when ((SELECT length(coalesce(ee26::text,'')) > 1) OR (ee26::text ~ '^[0-9]*$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EE26_INVALID') else '' end ::text
			as reason,  'reject'::text as errortype
		      from tempuploadgrffile 
		where batchuploadid = $2 and ((SELECT length(attendanceSchoolProgramIdentifier)> 100) OR
		      (SELECT length(aypSchoolIdentifier)> 100) OR
			  (SELECT length(accountabilitydistrictidentifier)> 100) OR
		      (attendanceSchoolProgramIdentifier::text is null) OR
		      (coalesce(comprehensiveRace,'') not in ('1','2','4','5','6','7','8')) OR
		      (UPPER(coalesce(currentGradelevel,'')) not in ('3','4','5','6','7','8','9','10','11','12')) OR
		      (UPPER(coalesce(primaryDisabilityCode,'')) not in ('AM','DB','DD','ED','HI','LD','MD','ID','OH','OI','SL','TB','VI','ND','WD','EI','DA')) OR
		      (coalesce(performanceLevel,'') not in ('1','2','3','4','9')) OR
		      (coalesce(esolParticipationCode,'') not in ('0','1','2','3','4','5','6')) OR 
		      (coalesce(hispanicEthnicity,'') not in ('1','0')) OR 
		      (coalesce(invalidationCode,'') not in ('1','0')) OR		      
		      (coalesce(firstLanguage,'') not in ('','0','1','2','3','4','5','6','7','8','10','11','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47')) OR 		      
			  (schoolEntryDate::text is null) OR
			  (dateOfBirth::text is null) OR
		      (dateOfBirth is not null and (dateOfBirth::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) OR
			  (dateOfBirth is not null and (dateOfBirth::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(dateOfBirth, 'mm/dd/yyyy') <= '01/01/1000') OR
		      (schoolEntryDate is not null and (coalesce(schoolEntryDate,'') ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) OR
			  (schoolEntryDate is not null and (schoolEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(schoolEntryDate, 'mm/dd/yyyy') <= '01/01/1000') OR
		      (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) OR
			  (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') <= '01/01/1000') OR
			  (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') < '01/01/1989') OR
			  (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') > (current_timestamp  at time zone 'US/Central')::date) OR
			  (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') <= to_date (schoolEntryDate, 'mm/dd/yyyy')) OR
			  (exitwithdrawaldate is not null  and exitwithdrawalcode is null ) OR
		      (exitwithdrawalcode is not null  and exitwithdrawaldate is null ) OR
			  (coalesce(exitwithdrawalcode,'') not in ('','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','30','98')) OR 
		      (stateEntryDate is not null and (stateEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) OR
			  (stateEntryDate is not null and (stateEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(stateEntryDate, 'mm/dd/yyyy') <= '01/01/1000') OR
		      (districtEntryDate is not null and (districtEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) OR
			  (districtEntryDate is not null and (districtEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(districtEntryDate, 'mm/dd/yyyy') <= '01/01/1000') OR
			  (SELECT length(coalesce(educatorIdentifier::text,'')) > 254) OR
			  (SELECT length(coalesce(educatorIdentifier::text,'')) NOT BETWEEN 1 and 254) OR
			  (SELECT length(coalesce(localstudentidentifier::text,'')) > 20) OR
			  (educatorFirstName::text is null) OR
			  (educatorLastName::text is null) OR
			  (educatorUserName::text is null) OR
			  (SELECT length(coalesce(educatorFirstName::text,'')) > 80) OR
			  (SELECT length(coalesce(educatorLastName::text,'')) > 80) OR
			  (SELECT length(coalesce(educatorUserName::text,'')) > 254) OR
		      ((coalesce(uniquerowidentifier,'')::text ~ '^[0-9]+$') is false) OR
		      (SELECT length(coalesce(uniquerowidentifier,'')) > 8) OR
		      (SELECT length(coalesce(finalBand::text,'')) > 150) OR
		      (lower(coalesce(gender,'')) not in ('male','female')) OR
		      (SELECT length(coalesce(generationCode::text,'')) > 10) OR 
		      (SELECT length(coalesce(studentlegalFirstName::text,'')) NOT BETWEEN 1 and 60) OR 
		      (SELECT length(coalesce(studentlegalLastName::text,'')) NOT BETWEEN 1 and 60) OR 
		      (SELECT length(coalesce(studentlegalMiddleName::text,'')) > 80) OR 
		      (SELECT length(coalesce(districtcode::text,'')) NOT BETWEEN 1 and 100) OR 
		      (SELECT length(coalesce(schoolcode::text,'')) NOT BETWEEN 1 and 100) OR 
		      (SELECT length(coalesce(sgp::text,'')) > 3) OR
		      (SELECT length(coalesce(state::text,'')) NOT BETWEEN 1 and 100) OR
		      (lower(coalesce(state::text,'')) = lower('new york') and  coalesce(nyPerformanceLevel,'') not in ('21','22','23','24','9')) OR
		      (SELECT length(coalesce(stateStudentIdentifier::text,'')) NOT BETWEEN 1 and 10) OR
		      ((coalesce(studentid,'') ~ '^[0-9]+$') is false) OR
		       (SELECT length(coalesce(studentid,'')) > 10) OR
		      (coalesce(subject::text,'') = '' and coalesce(performancelevel,'') not in ('9')) OR
		      (SELECT length(coalesce(userName::text,'')) > 100) OR		       
		      ((SELECT length(coalesce(ee1::text,'')) > 1) OR (ee1::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee2::text,'')) > 1) OR (ee2::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee3::text,'')) > 1) OR (ee3::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee4::text,'')) > 1) OR (ee4::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee5::text,'')) > 1) OR (ee5::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee6::text,'')) > 1) OR (ee6::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee7::text,'')) > 1) OR (ee7::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee8::text,'')) > 1) OR (ee8::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee9::text,'')) > 1) OR (ee9::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee10::text,'')) > 1) OR (ee10::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee11::text,'')) > 1) OR (ee11::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee12::text,'')) > 1) OR (ee12::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee13::text,'')) > 1) OR (ee13::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee14::text,'')) > 1) OR (ee14::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee15::text,'')) > 1) OR (ee15::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee16::text,'')) > 1) OR (ee16::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee17::text,'')) > 1) OR (ee17::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee18::text,'')) > 1) OR (ee18::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee19::text,'')) > 1) OR (ee19::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee20::text,'')) > 1) OR (ee20::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee21::text,'')) > 1) OR (ee21::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee22::text,'')) > 1) OR (ee22::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee23::text,'')) > 1) OR (ee23::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee24::text,'')) > 1) OR (ee24::text ~ '^[0-9]*$') is false) OR	
		      ((SELECT length(coalesce(ee25::text,'')) > 1) OR (ee25::text ~ '^[0-9]*$') is false) OR
		      ((SELECT length(coalesce(ee26::text,'')) > 1) OR (ee26::text ~ '^[0-9]*$') is false));

$BODY$;

ALTER FUNCTION public.grf_file_common_validator(bigint, bigint, bigint)
    OWNER TO aart;
-------------------------------------------------------------------------------------------------------------------------------------------


------------------------------------------------GRF FILE CUSTOM VALIDATION-----------------------------------------------------------------

-- FUNCTION: public.grf_file_custom_validator(bigint, bigint, bigint)

-- DROP FUNCTION public.grf_file_custom_validator(bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION public.grf_file_custom_validator(
	stateid bigint,
	uploadbatchid bigint,
	reportyear bigint)
    RETURNS TABLE(batchuploadid bigint, line text, fieldname text, reason text, errortype text) 
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$

 WITH organizaton_hier as (select distinct(org.displayidentifier), 
						           case 
						             when (org.organizationtypeid = 5 and parent.organizationtypeid > 2) then 
			                           (select displayidentifier from organization_parent_active_or_inactive(parent.id)
						                  where organizationtypeid = 2)
						             when (org.organizationtypeid = 7 and parent.organizationtypeid > 5) then
						                (select displayidentifier from organization_parent_active_or_inactive(parent.id)
						                  where organizationtypeid = 5) 						           
						             else 
						                 parent.displayidentifier end  as parentdisplayidentifier,
						
						              org.organizationtypeid 
                                      from organization_children_active_or_inactive(stateid) org
                                      inner join organizationrelation orgrel on orgrel.organizationid = org.id
                                      inner join organization parent on parent.id = orgrel.parentorganizationid),
   organization_users as (select distinct BTRIM(au.uniquecommonidentifier) as educatoridentifier
     from aartuser au 
     join usersorganizations uo on au.id = uo.aartuserid
     join userorganizationsgroups uog on uog.userorganizationid=uo.id
     where uo.organizationid in (select id from organization_children_active_or_inactive(stateid) union select stateid))
  
  select $2 as batchuploadid, (tmp.linenumber+1)::text as line, ' '::text as fieldname, 
  
  case when coalesce(performancelevel,'') in ('9') and lower(TRIM(coalesce(tmp.subject,''))) =''
  then 
   case when tmp.currentgradelevel is not null then (
   case when (gc.id is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_CURRENT_GRADE_LEVEL_NULL') else '' end) else '' end ||
   case when tmp.state is not null then (
   case when orgst.id is null then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_STATE_MISMATCH') else '' end) else '' end ||
   case when (tmp.schoolcode is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgdt.displayidentifier is not null and orgsch.displayidentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_SCHOOL_CODE_NULL') else '' end) else '' end ||
   case when (tmp.schoolcode is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgsch.displayidentifier is not null and orgschdt.displayidentifier is null ) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_SCHOOL_CODE_MISMATCH_PARNDIST') else '' end) else '' end ||
   case when (tmp.districtcode is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgdt.displayidentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_DISTRICT_CODE_NULL') else '' end) else '' end ||
   case when tmp.attendanceschoolprogramidentifier is not null then (
   case when (orgst.id is not null and orgat.displayidentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_ATTENDANCESCHOOLPROGRAMIDENTIFIER_NULL') else '' end) else '' end ||
   case when tmp.educatoridentifier is not null then (
   case when (orgst.id is not null and orgusers.educatoridentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_STATE_EDUCATOR_IDENTIFIER_NULL') else '' end) else '' end ||
   case when (tmp.accountabilitydistrictidentifier is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgaypdt.displayidentifier is null ) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_ACCOUNTABILITY_DISTRICT_IDENTIFIER_NULL') else '' end ) else '' end ||
   case when (tmp.aypschoolidentifier is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgaypsch.displayidentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_ACCOUNTABILITY_SCHOOL_IDENTIFIER_NULL') else '' end ) else '' end ||
   case when (tmp.aypschoolidentifier is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgaypsch.displayidentifier is not null and orgaypschdt.displayidentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_ACCOUNTABILITY_SCHOOL_IDENTIFIER_MISMATCH_PARNDIST') else '' end  ) else '' end ||
   case when tmp.studentid is not null then (
   case when st.id is null then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_KITE_STUDENT_IDENTIFIER_NULL') else '' end) else '' end ||''::text
 
  else 
   case when tmp.subject is not null then (
   case when ca.id is null then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_SUBJECT_INVALID') else '' end) else '' end ||
   case when tmp.currentgradelevel is not null then (
   case when (ca.id is not null and gc.id is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_CURRENT_GRADE_LEVEL_NULL') else '' end) else '' end ||
   case when tmp.state is not null then (
   case when orgst.id is null then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_STATE_MISMATCH') else '' end) else '' end ||
   case when (tmp.schoolcode is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgdt.displayidentifier is not null and orgsch.displayidentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_SCHOOL_CODE_NULL') else '' end) else '' end ||
   case when (tmp.schoolcode is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgsch.displayidentifier is not null and orgschdt.displayidentifier is null ) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_SCHOOL_CODE_MISMATCH_PARNDIST') else '' end) else '' end ||
   case when (tmp.districtcode is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgdt.displayidentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_DISTRICT_CODE_NULL') else '' end) else '' end ||
   case when tmp.attendanceschoolprogramidentifier is not null then (
   case when (orgst.id is not null and orgat.displayidentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_ATTENDANCESCHOOLPROGRAMIDENTIFIER_NULL') else '' end) else '' end ||
   case when tmp.educatoridentifier is not null then (
   case when (orgst.id is not null and orgusers.educatoridentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_STATE_EDUCATOR_IDENTIFIER_NULL') else '' end) else '' end ||
   case when (tmp.accountabilitydistrictidentifier is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgaypdt.displayidentifier is null)  then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_ACCOUNTABILITY_DISTRICT_IDENTIFIER_NULL') else '' end ) else '' end ||
   case when (tmp.aypschoolidentifier is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgaypsch.displayidentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_ACCOUNTABILITY_SCHOOL_IDENTIFIER_NULL') else '' end  ) else '' end ||
   case when (tmp.aypschoolidentifier is not null and tmp.state is not null) then (
   case when (orgst.id is not null and orgaypsch.displayidentifier is not null and orgaypschdt.displayidentifier is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_ACCOUNTABILITY_SCHOOL_IDENTIFIER_MISMATCH_PARNDIST') else '' end  ) else '' end ||
   case when tmp.studentid is not null then (
   case when st.id is null then (select attrvalue from appconfiguration where attrcode = 'GRF_CUST_VAL_KITE_STUDENT_IDENTIFIER_NULL') else '' end)else '' end ||''::text 
 
  END as reason, 'reject'::text as errortype
                   
  from tempuploadgrffile tmp
  left join contentarea ca on lower(ca.abbreviatedname) = lower(TRIM(coalesce(tmp.subject,''))) and ca .activeflag is true
  left join gradecourse gc on lower(gc.abbreviatedname) = lower(TRIM(coalesce(tmp.currentgradelevel,''))) 
  and gc.contentareaid  = (case when (coalesce(performancelevel,'') in ('9') and lower(TRIM(coalesce(tmp.subject,''))) ='') 
  THEN (select id from contentarea where abbreviatedname = 'ELA' order by modifieddate desc limit 1) ELSE ca.id END) and gc.activeflag is true
  left join organization orgst on lower(orgst.organizationname) =  lower(TRIM(coalesce(tmp.state,''))) and orgst.id = stateid
  left join organizaton_hier orgdt on lower(orgdt.displayidentifier) =  lower(TRIM(coalesce(tmp.districtcode,''))) and lower(orgdt.parentdisplayidentifier) = lower(orgst.displayidentifier)
  left join organizaton_hier orgsch on lower(orgsch.displayidentifier) =  lower(TRIM(coalesce(tmp.schoolcode,'')))
  left join organizaton_hier orgschdt on lower(orgschdt.displayidentifier) =  lower(TRIM(coalesce(tmp.schoolcode,''))) and lower(orgschdt.parentdisplayidentifier) = lower(orgdt.displayidentifier)
  left join (select * from organization_children_active_or_inactive(stateid)) orgat on lower(orgat.displayidentifier) = lower(coalesce(tmp.attendanceschoolprogramidentifier,'')) and coalesce(tmp.attendanceschoolprogramidentifier,'') != '' 
  left join organizaton_hier orgaypdt on lower(orgaypdt.displayidentifier) =  lower(TRIM(coalesce(tmp.accountabilitydistrictidentifier,'')))and lower(orgaypdt.parentdisplayidentifier) = lower(orgst.displayidentifier) 
  and coalesce(tmp.accountabilitydistrictidentifier,'') != '' 
  left join organizaton_hier orgaypsch on lower(orgaypsch.displayidentifier) =  lower(TRIM(coalesce(tmp.aypschoolidentifier,''))) and coalesce(tmp.aypschoolidentifier,'') != ''
  left join organizaton_hier orgaypschdt on lower(orgaypschdt.displayidentifier) =  lower(TRIM(coalesce(tmp.aypschoolidentifier,''))) and case when (tmp.accountabilitydistrictidentifier is not null) then lower(orgaypschdt.parentdisplayidentifier) else 'S' end = case when (tmp.accountabilitydistrictidentifier is not null) then lower(orgaypdt.displayidentifier) else 'S' end 
  and coalesce(tmp.aypschoolidentifier,'') != ''
  left join organization_users orgusers on lower(orgusers.educatoridentifier) = lower(coalesce(tmp.educatoridentifier,'')) 
  left join student st on st.id = (case when ((coalesce(tmp.studentid,'') ~ '^[0-9]+$') is true) THEN (tmp.studentid::bigint ) ELSE null END)
  where batchuploadid = $2 and 
  ((tmp.subject is not null and ca.id is null and coalesce(performancelevel,'') not in ('9')) OR
  (tmp.currentgradelevel is not null and gc.id is null) OR
  (tmp.state is not null and orgst.id is null) OR
  (tmp.state is not null and tmp.districtcode is not null and orgst.id is not null and orgdt.displayidentifier is null) OR
  (tmp.state is not null and tmp.schoolcode is not null and orgst.id is not null and orgsch.displayidentifier is null) OR
  (tmp.state is not null and tmp.schoolcode is not null and orgst.id is not null and orgsch.displayidentifier is not null and orgschdt.displayidentifier is null) OR
  (tmp.attendanceschoolprogramidentifier is not null and orgst.id is not null and orgat.displayidentifier is null) OR
  (tmp.state is not null and tmp.accountabilitydistrictidentifier is not null and orgst.id is not null and orgaypdt.displayidentifier is null) OR
  (tmp.state is not null and tmp.aypschoolidentifier is not null and orgst.id is not null and orgaypsch.displayidentifier is null) OR
  (tmp.state is not null and tmp.aypschoolidentifier is not null and orgst.id is not null and orgaypsch.displayidentifier is not null and orgaypschdt.displayidentifier is null) OR
  (tmp.educatoridentifier is not null and orgst.id is not null and orgusers.educatoridentifier is null) OR
  (tmp.studentid is not null and st.id is null));

$BODY$;

ALTER FUNCTION public.grf_file_custom_validator(bigint, bigint, bigint)
    OWNER TO aart;

-------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------GRF FILE UPDATED VALIDATION--------------------------------------------------------------------
-- FUNCTION: public.grf_file_updated_validator(bigint, bigint, text, bigint, bigint, bigint)

-- DROP FUNCTION public.grf_file_updated_validator(bigint, bigint, text, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION public.grf_file_updated_validator(
	uploadstateid bigint,
	uploadbatchid bigint,
	uploadtype text,
	assessmentprogramid bigint,
	uploadreportyear bigint,
	createduser bigint)
    RETURNS SETOF record 
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$

DECLARE
  ref record;
  valid boolean = true;
BEGIN    
       --Validate externaluniquerowidentifier, Kite_Student_Identifier, subject combination with original file.....Extra new student also will be rejected
       FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'Kite_Student_Identifier'::text as fieldname,(select attrvalue from appconfiguration where attrcode = 'GRF_UPD_VAL_KITE_STD_ID_SUB_UNIQ_MISMATCH_ORG_UPLOAD')::text as reason,  'reject'::text as errortype 
		from tempuploadgrffile tmp
		left join uploadgrffile ugf on tmp.uniquerowidentifier = ugf.externaluniquerowidentifier::text 
					   and lower(coalesce(tmp.subject::text,' ')) = lower(coalesce(ugf.subject::text,' ')) 
					   and tmp.studentid= ugf.studentid::text
					   and ugf.recentversion is true
					   and ugf.reportyear = uploadreportyear
		where ugf.studentid is null and tmp.batchuploadid = uploadbatchid  
       LOOP
	return next ref; 
	valid = false;  
       END LOOP;
    RAISE NOTICE 'Validate externaluniquerowidentifier, Kite_Student_Identifier, subject combination with original file.....Extra new student also will be rejected Completed: %', valid;

    RAISE NOTICE 'GRF_File_update_Validator Completed: %', valid;	
 END;     

$BODY$;

ALTER FUNCTION public.grf_file_updated_validator(bigint, bigint, text, bigint, bigint, bigint)
    OWNER TO aart;

-------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------GRF FILE COLUMN CHANGE VALIDATION--------------------------------------------------------------------
-- FUNCTION: public.grf_file_column_change_validator(bigint, bigint, bigint)

-- DROP FUNCTION public.grf_file_column_change_validator(bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION public.grf_file_column_change_validator(
	stateid bigint,
	uploadbatchid bigint,
	reportyear bigint)
    RETURNS TABLE(batchuploadid bigint, line text, fieldname text, reason text, errortype text) 
    LANGUAGE 'sql'

    COST 100
    VOLATILE 
    ROWS 1000
AS $BODY$

select $2 as batchuploadid, (linenumber+1)::text as line, ' '::text as fieldname, 
           case when (attendanceSchoolProgramIdentifier::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ATTENDANCESCHOOLPROGRAMIDENTIFIER_NULL') when (SELECT length(attendanceSchoolProgramIdentifier)> 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ATTENDANCESCHOOLPROGRAMIDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(aypSchoolIdentifier)> 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ACCOUNTABILITY_SCHOOL_IDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(accountabilitydistrictidentifier)> 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ACCOUNTABILITY_DISTRICT_IDENTIFIER_LENGTH') else '' end ||		   
		   case when (SELECT length(coalesce(localstudentidentifier::text,'')) > 20) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_LOCAL_STUDENT_IDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(comprehensiveRace::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_COMPREHENSIVERACE_NULL') when coalesce(comprehensiveRace,'') not in ('1','2','4','5','6','7','8') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_COMPREHENSIVERACE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(currentGradelevel::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_CURRENT_GRADE_LEVEL_NULL') when UPPER(coalesce(currentGradelevel,'')) not in ('3','4','5','6','7','8','9','10','11','12') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_CURRENT_GRADE_LEVEL_INVALID') else '' end ||
		   case when coalesce(firstLanguage,'') not in ('','0','1','2','3','4','5','6','7','8','10','11','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_FIRSTLANGUAGE_INVALID') else '' end ||   
		   case when (dateOfBirth::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DATE_OF_BIRTH_NULL') else '' end ||
		   case when (dateOfBirth is not null and (dateOfBirth::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DATE_OF_BIRTH_FORMAT') else '' end ||
		   case when (dateOfBirth is not null and (dateOfBirth::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(dateOfBirth, 'mm/dd/yyyy') <= '01/01/1000') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DATE_OF_BIRTH_INVALID') else '' end||
		   case when (districtEntryDate is not null and (districtEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DISTRICT_ENTRY_DATE_FORMAT') else '' end ||
		   case when (districtEntryDate is not null and (districtEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(districtEntryDate, 'mm/dd/yyyy') <= '01/01/1000') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DISTRICT_ENTRY_DATE_INVALID') else '' end||
		   case when (stateEntryDate is not null and (stateEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_ENTRY_DATE_FORMAT') else '' end ||
		   case when (stateEntryDate is not null and (stateEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(stateEntryDate, 'mm/dd/yyyy') <= '01/01/1000') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_ENTRY_DATE_INVALID') else '' end||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_FORMAT') else '' end ||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') <= '01/01/1000') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_INVALID') else '' end||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') < '01/01/1989') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_1989') else '' end||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') > (current_timestamp  at time zone 'US/Central')::date) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_CURRENTDATE') else '' end||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') <= to_date (schoolEntryDate, 'mm/dd/yyyy')) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_SCHOOLDATE') else '' end||
		   case when (exitwithdrawaldate is not null  and exitwithdrawalcode is null ) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_NULL') else '' end||
		   case when (exitwithdrawalcode is not null  and exitwithdrawaldate is null ) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_DATE_NULL') else '' end||
		   case when coalesce(exitwithdrawalcode,'') not in ('','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','30','98') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(educatorIdentifier::text,'')) > 254) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_EDUCATOR_IDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(educatorIdentifier::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_EDUCATOR_IDENTIFIER_NULL') else '' end ||
		   case when (educatorFirstName::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_FIRST_NAME_NULL') else '' end || 
		   case when (educatorLastName::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_LAST_NAME_NULL') else '' end || 
		   case when (educatorUserName::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_USER_NAME_NULL') else '' end || 
		   case when (SELECT length(coalesce(educatorFirstName::text,'')) > 80) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_FIRST_NAME_LENGTH') else '' end || 
		   case when (SELECT length(coalesce(educatorLastName::text,'')) > 80) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_LAST_NAME_LENGTH') else '' end || 
		   case when (SELECT length(coalesce(educatorUserName::text,'')) > 254) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EDUCATOR_USER_NAME_LENGTH')  else '' end || 
		   case when (SELECT length(coalesce(esolParticipationCode::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ESOLPARTICIPATIONCODE_NULL') when coalesce(esolParticipationCode,'') not in ('0','1','2','3','4','5','6') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_ESOLPARTICIPATIONCODE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(uniquerowidentifier,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_UNIQUEROWIDENTIFIER_NULL') when ((coalesce(uniquerowidentifier,'') ~ '^[0-9]+$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_UNIQUEROWIDENTIFIER_INVALID') else '' end ||
		   case when (SELECT length(coalesce(uniquerowidentifier,'')) > 8) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_UNIQUEROWIDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(gender::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_GENDER_NULL') when lower(coalesce(gender,'')) not in ('male','female') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_GENDER_INVALID') else '' end ||
		   case when (SELECT length(coalesce(generationCode::text,'')) > 10) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_GENERATIONCODE_LENGTH') else '' end || 
		   case when (SELECT length(coalesce(hispanicEthnicity::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_HISPANIC_ETHNICITY_NULL') when coalesce(hispanicEthnicity,'') not in ('1','0') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_HISPANIC_ETHNICITY_INVALID') else '' end || 
		   case when coalesce(invalidationCode,'') not in ('1','0') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_INVALIDATION_CODE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(studentlegalFirstName::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STUDENT_FIRST_NAME_NULL') when (SELECT length(coalesce(studentlegalFirstName::text,'')) > 60) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STUDENT_FIRST_NAME_LENGTH') else '' end || 
		   case when (SELECT length(coalesce(studentlegalLastName::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STUDENT_LAST_NAME_NULL') when (SELECT length(coalesce(studentlegalLastName::text,'')) > 60) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STUDENT_LAST_NAME_LENGTH')  else '' end || 
		   case when (SELECT length(coalesce(studentlegalMiddleName::text,'')) > 80) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STUDENT_MIDDLE_NAME_LENGTH') else '' end || 
		   case when (SELECT length(coalesce(primaryDisabilityCode::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_PRIMARY_DISABILITY_CODE_NULL') when UPPER(coalesce(primaryDisabilityCode,'')) not in ('AM','DB','DD','ED','HI','LD','MD','ID','OH','OI','SL','TB','VI','ND','WD','EI','DA') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_PRIMARY_DISABILITY_CODE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(districtcode::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DISTRICT_CODE_NULL') when (SELECT length(coalesce(districtcode::text,'')) > 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_DISTRICT_CODE_INVALID') else '' end || 
		   case when (schoolEntryDate::text is null) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SCHOOL_ENTRY_DATE_NULL') else '' end ||
		   case when (schoolEntryDate is not null and (coalesce(schoolEntryDate::text,'') ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SCHOOL_ENTRY_DATE_FORMAT') else '' end ||
		   case when (schoolEntryDate is not null and (schoolEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(schoolEntryDate, 'mm/dd/yyyy') <= '01/01/1000') then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SCHOOL_ENTRY_DATE_INVALID') else '' end||
		   case when (SELECT length(coalesce(schoolcode::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SCHOOL_CODE_NULL') when (SELECT length(coalesce(schoolcode::text,'')) > 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SCHOOL_CODE_INVALID') else '' end ||
		   case when (SELECT length(coalesce(state::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_NULL') when (SELECT length(coalesce(state::text,'')) > 100) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(stateStudentIdentifier::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_NULL') when (SELECT length(coalesce(stateStudentIdentifier::text,'')) > 10) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_LENGTH') else '' end ||
		   case when (SELECT length(coalesce(studentid,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_KITE_STUDENT_IDENTIFIER_NULL') when ((coalesce(studentid,'') ~ '^[0-9]+$') is false) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_KITE_STUDENT_IDENTIFIER_INVALID') else '' end ||
		   case when (SELECT length(coalesce(studentid,'')) > 10) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_KITE_STUDENT_IDENTIFIER_LENGTH') else '' end ||
		   case when (coalesce(subject::text,'') = '' and coalesce(performancelevel,'') not in ('9')) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_SUBJECT_NULL') else '' end ||
		   case when (SELECT length(coalesce(userName::text,'')) > 100)  then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_USERNAME_LENGTH') else '' end ::text
			as reason,  'reject'::text as errortype
		      from tempuploadgrffile 
		where batchuploadid = $2 and ((SELECT length(attendanceSchoolProgramIdentifier)> 100) OR
		      (SELECT length(aypSchoolIdentifier)> 100) OR
			  (SELECT length(accountabilitydistrictidentifier)> 100) OR
		      (attendanceSchoolProgramIdentifier::text is null) OR
		      (coalesce(comprehensiveRace,'') not in ('1','2','4','5','6','7','8')) OR
		      (UPPER(coalesce(currentGradelevel,'')) not in ('3','4','5','6','7','8','9','10','11','12')) OR
		      (UPPER(coalesce(primaryDisabilityCode,'')) not in ('AM','DB','DD','ED','HI','LD','MD','ID','OH','OI','SL','TB','VI','ND','WD','EI','DA')) OR
		      (coalesce(esolParticipationCode,'') not in ('0','1','2','3','4','5','6')) OR 
		      (coalesce(hispanicEthnicity,'') not in ('1','0')) OR 
		      (coalesce(invalidationCode,'') not in ('1','0')) OR		      
		      (coalesce(firstLanguage,'') not in ('','0','1','2','3','4','5','6','7','8','10','11','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47')) OR 		      
			  (schoolEntryDate::text is null) OR
			  (dateOfBirth::text is null) OR
		      (dateOfBirth is not null and (dateOfBirth::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) OR
			  (dateOfBirth is not null and (dateOfBirth::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(dateOfBirth, 'mm/dd/yyyy') <= '01/01/1000') OR
		      (schoolEntryDate is not null and (coalesce(schoolEntryDate,'') ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) OR
			  (schoolEntryDate is not null and (schoolEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(schoolEntryDate, 'mm/dd/yyyy') <= '01/01/1000') OR
		      (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) OR
			  (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') <= '01/01/1000') OR
			  (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') < '01/01/1989') OR
			  (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') > (current_timestamp  at time zone 'US/Central')::date) OR
			  (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(exitwithdrawaldate, 'mm/dd/yyyy') <= to_date (schoolEntryDate, 'mm/dd/yyyy')) OR
			  (exitwithdrawaldate is not null  and exitwithdrawalcode is null ) OR
		      (exitwithdrawalcode is not null  and exitwithdrawaldate is null ) OR
			  (coalesce(exitwithdrawalcode,'') not in ('','1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','30','98')) OR 
		      (stateEntryDate is not null and (stateEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) OR
			  (stateEntryDate is not null and (stateEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(stateEntryDate, 'mm/dd/yyyy') <= '01/01/1000') OR
		      (districtEntryDate is not null and (districtEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is false) OR
			  (districtEntryDate is not null and (districtEntryDate::text ~ '^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$') is true and to_date(districtEntryDate, 'mm/dd/yyyy') <= '01/01/1000') OR
			  (SELECT length(coalesce(educatorIdentifier::text,'')) > 254) OR
			  (SELECT length(coalesce(educatorIdentifier::text,'')) NOT BETWEEN 1 and 254) OR
			  (SELECT length(coalesce(localstudentidentifier::text,'')) > 20) OR
			  (educatorFirstName::text is null) OR
			  (educatorLastName::text is null) OR
			  (educatorUserName::text is null) OR
			  (SELECT length(coalesce(educatorFirstName::text,'')) > 80) OR
			  (SELECT length(coalesce(educatorLastName::text,'')) > 80) OR
			  (SELECT length(coalesce(educatorUserName::text,'')) > 254) OR
		      ((coalesce(uniquerowidentifier,'')::text ~ '^[0-9]+$') is false) OR
		      (SELECT length(coalesce(uniquerowidentifier,'')) > 8) OR
		      (lower(coalesce(gender,'')) not in ('male','female')) OR
		      (SELECT length(coalesce(generationCode::text,'')) > 10) OR 
		      (SELECT length(coalesce(studentlegalFirstName::text,'')) NOT BETWEEN 1 and 60) OR 
		      (SELECT length(coalesce(studentlegalLastName::text,'')) NOT BETWEEN 1 and 60) OR 
		      (SELECT length(coalesce(studentlegalMiddleName::text,'')) > 80) OR 
		      (SELECT length(coalesce(districtcode::text,'')) NOT BETWEEN 1 and 100) OR 
		      (SELECT length(coalesce(schoolcode::text,'')) NOT BETWEEN 1 and 100) OR 
		      (SELECT length(coalesce(state::text,'')) NOT BETWEEN 1 and 100) OR
		      (SELECT length(coalesce(stateStudentIdentifier::text,'')) NOT BETWEEN 1 and 10) OR
		      ((coalesce(studentid,'') ~ '^[0-9]+$') is false) OR
		       (SELECT length(coalesce(studentid,'')) > 10) OR
		      (coalesce(subject::text,'') = '' and coalesce(performancelevel,'') not in ('9')) OR
		      (SELECT length(coalesce(userName::text,'')) > 100));

$BODY$;

ALTER FUNCTION public.grf_file_column_change_validator(bigint, bigint, bigint)
    OWNER TO aart;

-------------------------------------------------------------------------------------------------------------------------------------------
 
 ---F835 rationale for TE Item - CB feature
    
    alter table taskvariant add column rationale text;

 ---DE19162
    alter table studentexitdetails alter subjectid drop not null;