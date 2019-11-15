--ddl/733.sql

CREATE INDEX idx_contentframeworkdetail_parentcontentframeworkdetailid
    ON public.contentframeworkdetail USING btree
    (parentcontentframeworkdetailid)
    TABLESPACE pg_default;
    
CREATE INDEX idx_contentframeworkdetail_frameworklevelid
    ON public.contentframeworkdetail USING btree
    (frameworklevelid)
    TABLESPACE pg_default;
    
CREATE INDEX idx_subscoreframework_frameworklevel1
    ON public.subscoreframework USING btree
    (frameworklevel1 COLLATE pg_catalog."default")
    TABLESPACE pg_default;
    
CREATE INDEX idx_subscoreframework_frameworklevel2
    ON public.subscoreframework USING btree
    (frameworklevel2 COLLATE pg_catalog."default")
    TABLESPACE pg_default;
    
CREATE INDEX idx_subscoreframework_frameworklevel3
    ON public.subscoreframework USING btree
    (frameworklevel3 COLLATE pg_catalog."default")
    TABLESPACE pg_default;
    
    
    
-- Function: grf_file_main_validator(bigint, bigint, text, bigint, bigint, bigint)

-- DROP FUNCTION grf_file_main_validator(bigint, bigint, text, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION grf_file_main_validator(
    uploadstateid bigint,
    uploadbatchid bigint,
    uploadtype text,
    assessmentprogramid bigint,
    uploadreportyear bigint,
    createduser bigint)
  RETURNS SETOF record AS
$BODY$
DECLARE
  ref record;
  valid boolean = true;
BEGIN  
    RAISE NOTICE 'Process starting';
    --Check the row count......If original GRF then no need of row count check 
     IF (uploadtype = 'Original GRF' OR (select (select count(*) from tempuploadgrffile where batchuploadid = uploadbatchid) = (select count(*) from uploadgrffile ugf where ugf.stateid = uploadstateid and ugf.reportyear = uploadreportyear and recentversion is true) as rowcount) is true)   THEN
        RAISE NOTICE 'inside if';
	--Primary validation
          For ref in select batchuploadid, line, fieldname, reason, errortype  
	   from GRF_File_Common_Validator(uploadstateid, uploadbatchid,uploadreportyear)
	   LOOP
	     return next ref;
	     valid = false;
	   END LOOP;
	   
          RAISE NOTICE 'Primary Validation Completed: %', valid;
          IF valid is true THEN--2
	      --Update all subject code Math to M
	      update tempuploadgrffile set subject = 'M' where upper(subject) = 'MATH' and batchuploadid = uploadbatchid;
			      	      
              --secondary/custom validation
              For ref in select batchuploadid, line, fieldname, reason, errortype  
		   from GRF_File_Custom_Validator(uploadstateid, uploadbatchid,uploadreportyear)
		   LOOP
		     return next ref;
		     valid = false;
		   END LOOP;

	      RAISE NOTICE 'Secondary Validation Completed: %', valid;
	      	
	      IF valid is true THEN--3
		  --Check dupliacate entry for student and subject combination
	 	  FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'studentId'::text as fieldname,'of multiple entries for the Studentid and Subject combination entered'::text as reason,  'reject'::text as errortype 
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
		     valid = false;
		  END LOOP;
		  
		  RAISE NOTICE 'dupliacate entry for student and subject validation Completed: %', valid;
		  
		  if valid is true THEN--4
		       --Validate Unique External row identifier uniqueness
		       FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'Unique_Row_Identifier'::text as fieldname,'of more than one entry for the Unique_Row_Identifier'::text as reason,  'reject'::text as errortype 
	 	                    from tempuploadgrffile tmp
	 	                    inner join (select uniquerowidentifier 
	 	                                from tempuploadgrffile
	 	                                where batchuploadid = uploadbatchid 
	 	                                group by uniquerowidentifier 
	 	                                having count(uniquerowidentifier) >1) selrow on selrow.uniquerowidentifier = tmp.uniquerowidentifier
	 	                    where tmp.batchuploadid = uploadbatchid  
		       LOOP
		           return next ref;
			   valid = false;
		       END LOOP;
		       RAISE NOTICE 'Unique External row identifier uniqueness validation Completed: %', valid;     
		       if valid is true THEN--5
			    --Validate statestudentidentifier should not map to multiple studentid
		            FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'State_Student_Identifier'::text as fieldname,'the State_Student_Identifier is aligned to more than one Studentid'::text as reason,  'reject'::text as errortype 
	 	                    from tempuploadgrffile tmp
	 	                    inner join (select count(distinct studentid),statestudentidentifier 
	 	                                from tempuploadgrffile
	 	                                where batchuploadid = uploadbatchid 
	 	                                group by statestudentidentifier 
	 	                                having count(distinct studentid) >1) selrow on selrow.statestudentidentifier = tmp.statestudentidentifier
	 	                    where tmp.batchuploadid = uploadbatchid            
			    LOOP
			        return next ref;
			        valid = false;  
			    END LOOP;
			    RAISE NOTICE 'statestudentidentifier should not map to multiple studentid validation Completed: %', valid;
			    if valid is true THEN--6
			         --Validate studentid should not map to multiple statestudentidentifier
				 FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'StudentId'::text as fieldname,'the Studentid is aligned to more than one State_Student_Identifier'::text as reason,  'reject'::text as errortype 
	 	                    from tempuploadgrffile tmp
	 	                    inner join (select studentid, count(distinct statestudentidentifier) 
	 	                                from tempuploadgrffile
	 	                                where batchuploadid = uploadbatchid  
	 	                                group by studentid 
	 	                                having count(distinct statestudentidentifier) >1)selrow on selrow.studentid = tmp.studentid
	 	                    where tmp.batchuploadid = uploadbatchid
				 LOOP
				     return next ref; 
				     valid = false;
				 END LOOP;
				 
			         RAISE NOTICE 'Validate studentid should not map to multiple statestudentidentifier validation Completed: %', valid;     			        										   

			        --If updated GRF then delete all matching records from temp table
				IF uploadtype = 'Updated GRF' THEN
				  DELETE FROM tempuploadgrffile tmp
					USING uploadgrffile ugf
					WHERE tmp.batchuploadid = uploadbatchid and ugf.stateid = uploadstateid and ugf.reportyear = uploadreportyear and ugf.recentversion is true and coalesce(tmp.studentid::text,' ')=coalesce(ugf.studentid::text,' ') and
					coalesce(tmp.statestudentidentifier::text,' ')=coalesce(ugf.statestudentidentifier::text,' ') and
					coalesce(tmp.aypschoolidentifier::text,' ')=coalesce(ugf.aypschoolidentifier::text,' ') and
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
				END IF;
			    END IF;--6
		       END IF;--5    
		  END IF;--4		  
	    END IF;--3	  
          END IF;--2     
     ELSE
     RAISE NOTICE 'Inside ELSE';
	For ref in select uploadbatchid as batchuploadid, '0'::text as line, ''::text as fieldname,'the number of uploaded rows does not match the number of rows in the original GRF.'::text as reason,  'reject'::text as errortype
	   LOOP
	     return next ref;
	     valid = false;
	   END LOOP;
     END IF;

END;     
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
