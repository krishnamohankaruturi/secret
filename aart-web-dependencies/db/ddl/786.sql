
-- script for ddl/785.sql

--F868 for CB.
ALTER table taskvariant ADD COLUMN IF NOT EXISTS calculatorstatus varchar (20) default null;

-----------------------------------------------------------------------------------------------------------------------------------------
--F933 EP G-Release----
ALTER TABLE student ALTER COLUMN hispanicethnicity  type varchar;
-----------------------------------------------------------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------------------------------------------------------
--F915 EP G-Release----
---------    ALTER Query to add new column in studentexitcodes table  --------------

Alter table studentexitcodes add column IF NOT EXISTS stateid bigint; 

Alter table studentexitcodes add column IF NOT EXISTS assessmentprogramid bigint; 

Alter table studentexitcodes add column IF NOT EXISTS schoolyear bigint;  

Alter table studentexitcodes add column IF NOT EXISTS isvalidforui boolean;

Alter table studentexitcodes drop constraint uk_exitcode;

Alter table studentexitcodes ADD CONSTRAINT uk_exitcode UNIQUE (code, assessmentprogramid, stateid, schoolyear);
------------------------------------------------------------------------------------------------------------------------------------------

-----------------------------------------F956 DDL SCRIPTS-----------------------------------------------------------------------------------
-----------------------------------------CREATE TABLE EXTRACTASSESSMENTPROGRAM--------------------------------------------------------------
CREATE TABLE public.statestudentidentifierlength
(
    id bigserial,
    state bigint,
    statestudentidlength bigint,
    createduser integer NOT NULL,
    createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    activeflag boolean DEFAULT true,
    modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
    modifieduser integer NOT NULL,
    CONSTRAINT statestudentidentifierlength_pkey PRIMARY KEY (id)
);

CREATE INDEX idx_statestudentidentifierlength_statestudentidlength
    ON public.statestudentidentifierlength USING btree
    (statestudentidlength)
    TABLESPACE pg_default;
------------------------------------------------------------------------------------------------------------------------------------------
--F915 EP G-Release----
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
		   from GRF_File_Common_Validator(uploadstateid, uploadbatchid, uploadreportyear, assessmentprogramid)
		   LOOP
			 return next ref;
			
		   END LOOP;
		   
			  RAISE NOTICE 'Primary Validation Original GRF Completed: %', valid;
		END IF;
	
	IF uploadtype = 'Updated GRF' THEN		
		For ref in select batchuploadid, line, fieldname, reason, errortype  
		   from GRF_File_Column_Change_Validator(uploadstateid, uploadbatchid, uploadreportyear, assessmentprogramid)
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

-- FUNCTION: public.grf_file_common_validator(bigint, bigint, bigint, bigint)

-- DROP FUNCTION public.grf_file_common_validator(bigint, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION public.grf_file_common_validator(
	stateid bigint,
	uploadbatchid bigint,
	reportyear bigint,
	assessmentprogramid bigint)
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
		   case when exitwithdrawalcode is not null  then (case when exists (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid = stateid and schoolYear = reportyear) then(case when coalesce(exitwithdrawalcode,'') not in (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid = stateid and schoolYear = reportyear and code = exitwithdrawalcode::bigint)then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_STATE_INVALID')  else '' end)ELSE (case when coalesce(exitwithdrawalcode,'') not in (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid is null and schoolYear is null and code = exitwithdrawalcode::bigint) THEN (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_INVALID') else '' end) end)else '' end ||
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
		   case when (SELECT length(coalesce(stateStudentIdentifier::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_NULL') else (case when exists(SELECT statestudentidlength from statestudentidentifierlength  where state = stateid) then(case when (SELECT length(coalesce(stateStudentIdentifier,'')) > (SELECT statestudentidlength from statestudentidentifierlength where state = stateid))then (SELECT concat ((select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_LENGTH'),(SELECT statestudentidlength from statestudentidentifierlength  where state = stateid),(select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_CHARACTERS'))) else '' end) ELSE (case when (SELECT length(coalesce(stateStudentIdentifier,'')) > (SELECT statestudentidlength from statestudentidentifierlength where state is null)) then (SELECT concat ((select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_LENGTH'),(SELECT statestudentidlength from statestudentidentifierlength  where state is null),(select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_CHARACTERS'))) else '' end)end )end ||
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
			  (exitwithdrawalcode is not null and ((select exists (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid = stateid and schoolYear =reportyear)) and (coalesce(exitwithdrawalcode,'') not in (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid = stateid and schoolYear = reportyear and code = exitwithdrawalcode::bigint)))) OR 	 
			  (exitwithdrawalcode is not null and (((coalesce(exitwithdrawalcode,'') not in (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid = stateid and schoolYear = reportyear and code = exitwithdrawalcode::bigint)) and (coalesce(exitwithdrawalcode,'') not in (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid is null and schoolYear is null and code = exitwithdrawalcode::bigint)))))	OR 
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
		      ((SELECT length(coalesce(stateStudentIdentifier::text,'')) =0 ) OR (SELECT length(coalesce(stateStudentIdentifier,'')) > (SELECT CASE WHEN exists (select statestudentidlength from statestudentidentifierlength where state = stateid) then (select statestudentidlength from statestudentidentifierlength where state = stateid) else (select statestudentidlength from statestudentidentifierlength where state is null) end as statestudentidlenghth))) OR
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

ALTER FUNCTION public.grf_file_common_validator(bigint, bigint, bigint, bigint)
    OWNER TO aart;
-----------------------------------------------------------------------------------------------------------------------------------------    
    
-- FUNCTION: public.grf_file_column_change_validator(bigint, bigint, bigint, bigint)

-- DROP FUNCTION public.grf_file_column_change_validator(bigint, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION public.grf_file_column_change_validator(
	stateid bigint,
	uploadbatchid bigint,
	reportyear bigint,
	assessmentprogramid bigint)
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
		   case when exitwithdrawalcode is not null  then (case when exists (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid = stateid and schoolYear = reportyear) then(case when coalesce(exitwithdrawalcode,'') not in (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid = stateid and schoolYear = reportyear and code = exitwithdrawalcode::bigint)then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_STATE_INVALID')  else '' end)ELSE (case when coalesce(exitwithdrawalcode,'') not in (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid is null and schoolYear is null and code = exitwithdrawalcode::bigint) THEN (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_EXIT_WITHDRAWAL_CODE_INVALID') else '' end) end)else '' end ||
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
		   case when (SELECT length(coalesce(stateStudentIdentifier::text,'')) = 0) then (select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_NULL') else (case when exists(SELECT statestudentidlength from statestudentidentifierlength  where state = stateid) then(case when (SELECT length(coalesce(stateStudentIdentifier,'')) > (SELECT statestudentidlength from statestudentidentifierlength where state = stateid))then (SELECT concat ((select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_LENGTH'),(SELECT statestudentidlength from statestudentidentifierlength  where state = stateid),(select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_CHARACTERS'))) else '' end) ELSE (case when (SELECT length(coalesce(stateStudentIdentifier,'')) > (SELECT statestudentidlength from statestudentidentifierlength where state is null)) then (SELECT concat ((select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_STATE_STUDENT_IDENTIFIER_LENGTH'),(SELECT statestudentidlength from statestudentidentifierlength  where state is null),(select attrvalue from appconfiguration where attrcode = 'GRF_CMN_VAL_CHARACTERS'))) else '' end)end )end ||
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
			  (exitwithdrawalcode is not null and ((select exists (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid = stateid and schoolYear =reportyear)) and (coalesce(exitwithdrawalcode,'') not in (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid = stateid and schoolYear = reportyear and code = exitwithdrawalcode::bigint)))) OR 	 
			  (exitwithdrawalcode is not null and (((coalesce(exitwithdrawalcode,'') not in (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid = stateid and schoolYear = reportyear and code = exitwithdrawalcode::bigint)) and (coalesce(exitwithdrawalcode,'') not in (SELECT code::text from studentexitcodes where assessmentprogramid = assessmentprogramid and stateid is null and schoolYear is null and code = exitwithdrawalcode::bigint)))))	OR 
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
			  ((SELECT length(coalesce(stateStudentIdentifier::text,'')) =0 ) OR (SELECT length(coalesce(stateStudentIdentifier,'')) > (SELECT CASE WHEN exists (select statestudentidlength from statestudentidentifierlength where state = stateid) then (select statestudentidlength from statestudentidentifierlength where state = stateid) else (select statestudentidlength from statestudentidentifierlength where state is null) end as statestudentidlenghth))) OR
		      ((coalesce(studentid,'') ~ '^[0-9]+$') is false) OR
		       (SELECT length(coalesce(studentid,'')) > 10) OR
		      (coalesce(subject::text,'') = '' and coalesce(performancelevel,'') not in ('9')) OR
		      (SELECT length(coalesce(userName::text,'')) > 100));

$BODY$;

ALTER FUNCTION public.grf_file_column_change_validator(bigint, bigint, bigint, bigint)
    OWNER TO aart;
-----------------------------------------------------------------------------------------------------------------------------------------

--Alter table organization--

ALTER TABLE organization
ADD COLUMN testbegintime timestamp with time zone,
ADD COLUMN	testendtime timestamp with time zone,
ADD COLUMN	testdays varchar(10),
ADD COLUMN	excludeschoolindicator boolean;

ALTER TABLE statestudentidentifierlength
ADD CONSTRAINT statestudentidentifierlength_state_unique UNIQUE (state);