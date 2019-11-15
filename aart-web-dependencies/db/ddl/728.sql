-- ddl/727.sql

DROP TABLE IF EXISTS tempuploadgrffile;

CREATE TABLE tempuploadgrffile
(
  id bigserial NOT NULL,
  batchuploadid bigint,
  linenumber bigint,
  uniquerowidentifier character varying,
  studentid character varying,
  statestudentidentifier character varying,  
  aypschoolidentifier character varying,
  currentgradelevel character varying,
  studentlegalfirstname character varying,
  studentlegalmiddlename character varying,
  studentlegallastname character varying,
  generationcode character varying,
  username character varying,
  firstlanguage character varying,
  dateofbirth character varying,
  gender character varying,
  comprehensiverace character varying,
  hispanicethnicity character varying,
  primarydisabilitycode character varying,
  esolparticipationcode character varying,
  schoolentrydate character varying,
  districtentrydate character varying,
  stateentrydate character varying,
  attendanceschoolprogramidentifier character varying,
  state character varying,
  districtcode character varying,
  district character varying,
  schoolcode character varying,
  school character varying,
  educatorfirstname character varying,
  educatorlastname character varying,
  educatorusername character varying,
  educatoridentifier character varying,
  kiteeducatoridentifier character varying,
  subject character varying,
  finalband character varying,
  sgp character varying,
  performancelevel character varying,
  invalidationcode character varying,
  ee1 character varying,
  ee2 character varying,
  ee3 character varying,
  ee4 character varying,
  ee5 character varying,
  ee6 character varying,
  ee7 character varying,
  ee8 character varying,
  ee9 character varying,
  ee10 character varying,
  ee11 character varying,
  ee12 character varying,
  ee13 character varying,
  ee14 character varying,
  ee15 character varying,
  ee16 character varying,
  ee17 character varying,
  ee18 character varying,
  ee19 character varying,
  ee20 character varying,
  ee21 character varying,
  ee22 character varying,
  ee23 character varying,
  ee24 character varying,
  ee25 character varying,
  ee26 character varying,
  exitwithdrawaldate character varying,
  totallinkagelevelsmastered character varying,
  iowalinkagelevelsmastered character varying,
  exitwithdrawalcode character varying, 
  nyperformancelevel character varying,
  localstudentidentifier character varying,
  gradechange boolean NOT NULL DEFAULT false
);


-- Function: grf_file_main_validator(bigint, bigint, text, bigint, bigint, bigint)

DROP FUNCTION IF EXISTS grf_file_main_validator(bigint, bigint, text, bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION grf_file_main_validator(uploadstateid bigint, uploadbatchid bigint, uploadtype text, assessmentprogramid bigint, uploadreportyear bigint, createduser bigint)
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
	      update tempuploadgrffile set subject = 'M' where upper(subject) = 'MATH';

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
	 	                          having count(studentid) >1) selrow on selrow.subject = tmp.subject and tmp.studentid = selrow.studentid
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
			    END IF;--6
		       END IF;--5    
		  END IF;--4		  
	    END IF;--3	  
          END IF;--2     
     ELSE
     RAISE NOTICE 'Inside ELSE';
	For ref in select uploadbatchid as batchuploadid, '0'::text as line, ''::text as fieldname,'record Count is not matched with original GRF record'::text as reason,  'reject'::text as errortype
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




DROP  FUNCTION IF EXISTS GRF_File_Common_Validator(bigint, bigint, bigint);
CREATE OR REPLACE FUNCTION GRF_File_Common_Validator(
     stateid bigint,
     uploadbatchid bigint,
     reportyear bigint)

RETURNS TABLE(batchuploadid bigint,
  line text,
  fieldname text,
  reason text,
  errortype text) AS

	
$BODY$
    select $2 as batchuploadid, (linenumber+1)::text as line, ' '::text as fieldname, 
                   case when (SELECT length(attendanceSchoolProgramIdentifier)> 100) then 'Attendance_School_Program_Identifier ~ the value in Attendance_School_Program_Identifier must be less than or equal to 100 charecters##' else '' end ||
		   case when (SELECT length(aypSchoolIdentifier)> 100) then 'AYP_School_Identifier ~ the value in AYP_School_Identifier must be less than or equal to 100 charecters##' else '' end ||
		   case when (SELECT length(coalesce(localstudentidentifier::text,'')) > 20) then 'Local_Student_Identifier ~ the value in Local_Student_Identifier must be less than or equal to 100 charecters##' else '' end ||
		   case when coalesce(comprehensiveRace,'') not in ('','1','2','4','5','6','7','8') then 'Comprehensive_Race ~ the value in Comprehensive_Race is not allowed##' else '' end ||
		   case when coalesce(currentGradelevel,'') not in ('K','1','2','3','4','5','6','7','8','9','10','11','12') then 'Current_Grade_Level ~ the value in Current_Grade_Level is not allowed##' else '' end ||
		   case when coalesce(firstLanguage,'') not in ('','0','1','2','3','4','5','6','7','8','10','11','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47') then 'First_Language ~ the value in First_Language is not allowed##' else '' end || 			
		   case when (coalesce(dateOfBirth,'')::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false then 'Date_of_Birth ~ the value in Date_of_Birth is not valid. Should be in MM/DD/YYYY format##' else '' end ||
		   case when (districtEntryDate is not null and (districtEntryDate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) then 'District_Entry_Date ~ the value in District_Entry_Date is not valid. Should be in MM/DD/YYYY format##' else '' end ||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) then 'Exit_Withdrawl_Date ~ the value in Exit_Withdrawl_Date is not valid. Should be in MM/DD/YYYY format##' else '' end ||
		   case when (districtEntryDate is not null and (districtEntryDate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is true and to_date(districtEntryDate, 'mm/dd/yyyy') < '12/12/1989') then 'District_Entry_Date ~ the value in District_Entry_Date should be greater than 1990##' else '' end||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is true and exitwithdrawaldate is not null and to_date(exitwithdrawaldate, 'mm/dd/yyyy') < '12/12/1989') then 'Exit_Withdrawl_Date ~ the value in Exit_Withdrawl_Date should be greater than 1990##' else '' end||
		   case when (SELECT length(coalesce(district::text,''))  = 0) then 'District ~ the District not specified##' when (SELECT length(coalesce(district::text,'')) > 100) then 'District ~ the value in District must be less than or equal to 100 charecters##' else '' end || 
		   case when (SELECT length(coalesce(educatorFirstName::text,'')) > 80) then 'Educator_First_Name ~ the value in field Educator_First_Name must be less than or equal to 80 charecters##' else '' end ||
		   case when coalesce(educatorIdentifier::text,'') = ''  then 'Educator_Identifier ~ Educator_Identifier is not specified##' else '' end ||
		   case when (SELECT length(coalesce(educatorLastName::text,'')) > 80) then 'Educator_Last_Name ~ the value in field Educator_Last_Name must be less than or equal to 80 characters##' else '' end ||
		   case when (SELECT length(coalesce(educatorUserName::text,'')) > 254) then 'Educator_Username ~ the value in field Educator_Username must be less than or equal to 254 characters##' else '' end ||
		   case when coalesce(esolParticipationCode,'') not in ('0','1','2','3','4','5','6') then 'ESOL_Participation_Code ~ the value in ESOL_Participation_Code is not allowed##' else '' end ||
		   case when ((coalesce(uniquerowidentifier,'') ~ '^[0-9]+$') is false) then 'Unique_Row_Identifier ~ the value in Unique_Row_Identifier is not valid##' else '' end ||
		   case when (SELECT length(coalesce(uniquerowidentifier,'')) > 8) then 'Unique_Row_Identifier ~ the value in Unique_Row_Identifier must be less than or equal to 8 digits##' else '' end ||
		   case when (SELECT length(coalesce(finalBand::text,'')) > 150) then 'Final_Band ~ the value in field Final_Band must be less than or equal to 150 charecters##' else '' end ||
		   case when lower(coalesce(gender,'')) not in ('male','female') then 'Gender ~ the value in Gender is not valid##' else '' end ||
		   case when (SELECT length(coalesce(generationCode::text,'')) > 10) then 'Generation_Code ~ the value in field Generation_Code must be less than or equal to 10 charecters##' else '' end || 
		   case when coalesce(hispanicEthnicity,'') not in ('1','0') then 'Hispanic_Ethnicity ~ the value in Hispanic_Ethnicity is not allowed##' else '' end || 
		   case when coalesce(invalidationCode,'') not in ('1','0') then 'Invalidation_Code ~ the value in Invalidation_Code is not allowed##' else '' end ||
		   case when (SELECT length(coalesce(studentlegalFirstName::text,'')) = 0) then 'Student_Legal_First_Name ~ Student_Legal_First_Name not specified##' when (SELECT length(coalesce(studentlegalFirstName::text,'')) > 60) then 'Student_Legal_First_Name ~ the value in field Student_Legal_First_Name must be less than or equal to 60 characters##'else '' end || 
		   case when (SELECT length(coalesce(studentlegalLastName::text,'')) = 0) then 'Student_Legal_Last_Name ~ Student_Legal_Last_Name not specified##' when (SELECT length(coalesce(studentlegalLastName::text,'')) > 60) then 'Student_Legal_Last_Name ~ the value in field Student_Legal_Last_Name must be less than or equal to 60 characters##'else '' end || 
		   case when (SELECT length(coalesce(studentlegalMiddleName::text,'')) > 80) then 'Student_Legal_Middle_Name ~ the value in field Student_Legal_Middle_Name must be less than or equal to 80 characters##' else '' end || 
		   case when coalesce(performanceLevel,'') not in ('1','2','3','4','9') then 'Performance_Level ~ the value in Performance_Level is not allowed##' else '' end || 
		   case when UPPER(coalesce(primaryDisabilityCode,'')) not in ('AM','DB','DD','ED','HI','LD','MD','ID','OH','OI','SL','TB','VI','ND','WD','EI','DA') then 'Primary_Disability_Code ~ the value in Primary_Disability_Code is not allowed##' else '' end ||
		   case when (SELECT length(coalesce(districtcode::text,'')) = 0) then 'District_Code ~ District_Code not specified##' when (SELECT length(coalesce(districtcode::text,'')) > 100) then 'District_Code ~ the value in District_Code is not valid##' else '' end || 
		   case when (coalesce(schoolEntryDate::text,'') ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false then 'School_Entry_Date ~ the value in School_Entry_Date is not valid. Should be in MM/DD/YYYY format##' else '' end ||
		   case when ((coalesce(schoolEntryDate::text,'') ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is true and to_date(coalesce(schoolEntryDate,''), 'mm/dd/yyyy') < '12/12/1989') then 'School_Entry_Date ~ the value in School_Entry_Date should be greater than 1990##' else '' end ||
		   case when (SELECT length(coalesce(schoolcode::text,'')) = 0) then 'School_Code ~ School_Code not specified##' when (SELECT length(coalesce(schoolcode::text,'')) > 100) then 'School_Code ~ the value in School_Code is not valid##' else '' end ||
		   case when (SELECT length(coalesce(school::text,'')) = 0) then 'School ~ School not specified##' when (SELECT length(coalesce(school::text,'')) > 100) then 'School ~ the value in School is not valid##' else '' end ||		   
		   case when (SELECT length(coalesce(sgp::text,'')) > 3) then 'sgp ~ the value in field sgp must be less than or equal to 3 characters##' else '' end ||
		   case when (SELECT length(coalesce(state::text,'')) = 0) then 'State ~ State not specified##' when (SELECT length(coalesce(state::text,'')) > 100) then 'State ~ the value in State must be less than or equal to 100 characters##' else '' end ||
		   case when (lower(coalesce(state::text,'')) = lower('new york') and coalesce(nyPerformanceLevel,'') not in ('21','22','23','24','9')) then 'NY_Performance_Level ~ the value in NY_Performance_Level is not allowed##' else '' end ||
		   case when (stateEntryDate is not null and (stateEntryDate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) then 'State_Entry_Date  ~ the value in State_Entry_Date is not valid##' else '' end ||
		   case when (stateEntryDate is not null and (stateEntryDate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is true and to_date(stateEntryDate, 'mm/dd/yyyy') < '12/12/1989') then 'State_Entry_Date ~ the value in State_Entry_Date should be greater than 1990##' else '' end ||
		   case when (SELECT length(coalesce(stateStudentIdentifier::text,'')) = 0) then 'State_Student_Identifier ~ State_Student_Identifier not specified##' when (SELECT length(coalesce(stateStudentIdentifier::text,'')) > 20) then 'State_Student_Identifier  ~ the value in field State_Student_Identifier must be less than or equal to 20 characters##'else '' end ||
		   case when ((coalesce(studentid,'') ~ '^[0-9]+$') is false) then 'Studentid ~ Studentid is not valid##' else '' end ||
		   case when (SELECT length(coalesce(studentid,'')) > 10) then 'Studentid ~ the value in field Studentid must be less than or equal to 10 digits##'else '' end ||
		   case when coalesce(subject::text,'') = '' then 'Subject ~ the value in Subject not specified##' else '' end ||
		   case when (SELECT length(coalesce(userName::text,'')) > 100)  then 'Username ~ the value in field Username must be less than or equal to 100 characters##' else '' end || 
		   case when ((SELECT length(coalesce(ee1::text,'')) > 1) OR (ee1::text ~ '^[0-9]*$') is false) then 'EE_1 ~ the value in EE_1 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee2::text,'')) > 1) OR (ee2::text ~ '^[0-9]*$') is false) then 'EE_2 ~ the value in EE_2 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee3::text,'')) > 1) OR (ee3::text ~ '^[0-9]*$') is false) then 'EE_3 ~ the value in EE_3 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee4::text,'')) > 1) OR (ee4::text ~ '^[0-9]*$') is false) then 'EE_4 ~ the value in EE_4 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee5::text,'')) > 1) OR (ee5::text ~ '^[0-9]*$') is false) then 'EE_5 ~ the value in EE_5 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee6::text,'')) > 1) OR (ee6::text ~ '^[0-9]*$') is false) then 'EE_6 ~ the value in EE_6 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee7::text,'')) > 1) OR (ee7::text ~ '^[0-9]*$') is false) then 'EE_7 ~ the value in EE_7 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee8::text,'')) > 1) OR (ee8::text ~ '^[0-9]*$') is false) then 'EE_8 ~ the value in EE_8 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee9::text,'')) > 1) OR (ee9::text ~ '^[0-9]*$') is false) then 'EE_9 ~ the value in EE_9 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee10::text,'')) > 1) OR (ee10::text ~ '^[0-9]*$') is false) then 'EE_10 ~ the value in EE_10 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee11::text,'')) > 1) OR (ee11::text ~ '^[0-9]*$') is false) then 'EE_11 ~ the value in EE_11 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee12::text,'')) > 1) OR (ee12::text ~ '^[0-9]*$') is false) then 'EE_12 ~ the value in EE_12 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee13::text,'')) > 1) OR (ee13::text ~ '^[0-9]*$') is false) then 'EE_13 ~ the value in EE_13 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee14::text,'')) > 1) OR (ee14::text ~ '^[0-9]*$') is false) then 'EE_14 ~ the value in EE_14 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee15::text,'')) > 1) OR (ee15::text ~ '^[0-9]*$') is false) then 'EE_15 ~ the value in EE_15 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee16::text,'')) > 1) OR (ee16::text ~ '^[0-9]*$') is false) then 'EE_16 ~ the value in EE_16 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee17::text,'')) > 1) OR (ee17::text ~ '^[0-9]*$') is false) then 'EE_17 ~ the value in EE_17 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee18::text,'')) > 1) OR (ee18::text ~ '^[0-9]*$') is false) then 'EE_18 ~ the value in EE_18 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee19::text,'')) > 1) OR (ee19::text ~ '^[0-9]*$') is false) then 'EE_19 ~ the value in EE_19 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee20::text,'')) > 1) OR (ee20::text ~ '^[0-9]*$') is false) then 'EE_20 ~ the value in EE_20 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee21::text,'')) > 1) OR (ee21::text ~ '^[0-9]*$') is false) then 'EE_21 ~ the value in EE_21 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee22::text,'')) > 1) OR (ee22::text ~ '^[0-9]*$') is false) then 'EE_22 ~ the value in EE_22 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee23::text,'')) > 1) OR (ee23::text ~ '^[0-9]*$') is false) then 'EE_23 ~ the value in EE_23 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee24::text,'')) > 1) OR (ee24::text ~ '^[0-9]*$') is false) then 'EE_24 ~ the value in EE_24 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee25::text,'')) > 1) OR (ee25::text ~ '^[0-9]*$') is false) then 'EE_25 ~ the value in EE_25 is not valid##' else '' end ||
		      case when ((SELECT length(coalesce(ee26::text,'')) > 1) OR (ee26::text ~ '^[0-9]*$') is false) then 'EE_26 ~ the value in EE_26 is not valid##' else '' end ::text
			as reason,  'reject'::text as errortype
		      from tempuploadgrffile 
		where batchuploadid = $2 and (SELECT length(attendanceSchoolProgramIdentifier)> 100) OR --attendanceSchoolProgramIdentifier length validation
		      (SELECT length(aypSchoolIdentifier)> 100) OR
		      
		      (coalesce(comprehensiveRace,'') not in ('','1','2','4','5','6','7','8')) OR
		      (UPPER(coalesce(currentGradelevel,'')) not in ('K','1','2','3','4','5','6','7','8','9','10','11','12')) OR
		      (UPPER(coalesce(primaryDisabilityCode,'')) not in ('AM','DB','DD','ED','HI','LD','MD','ID','OH','OI','SL','TB','VI','ND','WD','EI','DA')) OR
		      (coalesce(performanceLevel,'') not in ('1','2','3','4','9')) OR
		      (coalesce(esolParticipationCode,'') not in ('0','1','2','3','4','5','6')) OR 
		      (coalesce(hispanicEthnicity,'') not in ('1','0')) OR 
		      (coalesce(invalidationCode,'') not in ('1','0')) OR		      
		      (coalesce(firstLanguage,'') not in ('','0','1','2','3','4','5','6','7','8','10','11','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47')) OR 		      
		      ((coalesce(dateOfBirth,'') ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) OR
		      (districtEntryDate is not null and (districtEntryDate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) OR
		      (districtEntryDate is not null and (districtEntryDate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is true and districtEntryDate is not null and to_date(districtEntryDate, 'mm/dd/yyyy') < '12/12/1989') OR
		      (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) OR
		      (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is true and exitwithdrawaldate is not null and to_date(exitwithdrawaldate, 'mm/dd/yyyy') < '12/12/1989') OR
		      (SELECT length(coalesce(district::text,'')) NOT BETWEEN 1 and 100) OR 
		      (SELECT length(coalesce(educatorFirstName::text,'')) > 80) OR
		      (coalesce(educatorIdentifier::text,'') = '') OR
		      (SELECT length(coalesce(educatorLastName::text,'')) > 80) OR
		      (SELECT length(coalesce(localstudentidentifier::text,'')) > 20) OR
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
		      ((coalesce(schoolEntryDate,'') ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) OR
		      ((coalesce(schoolEntryDate,'') ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is true and to_date(schoolentrydate, 'mm/dd/yyyy') < '12/12/1989') OR
		      (SELECT length(coalesce(schoolcode::text,'')) NOT BETWEEN 1 and 100) OR 
		      (SELECT length(coalesce(school::text,'')) NOT BETWEEN 1 and 100) OR 
		      (SELECT length(coalesce(sgp::text,'')) > 3) OR
		      (SELECT length(coalesce(state::text,'')) NOT BETWEEN 1 and 100) OR
		      (lower(coalesce(state::text,'')) = lower('new york') and  coalesce(nyPerformanceLevel,'') not in ('21','22','23','24','9')) OR
		      (stateEntryDate is not null and (stateEntryDate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) OR
		      (stateEntryDate is not null and (stateEntryDate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is true and stateEntryDate is not null and to_date(stateEntryDate, 'mm/dd/yyyy') < '12/12/1989') OR
		      (SELECT length(coalesce(stateStudentIdentifier::text,'')) NOT BETWEEN 1 and 20) OR
		      ((coalesce(studentid,'') ~ '^[0-9]+$') is false) OR
		       (SELECT length(coalesce(studentid,'')) > 10) OR
		      (coalesce(subject::text,'') = '') OR
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
		      ((SELECT length(coalesce(ee26::text,'')) > 1) OR (ee26::text ~ '^[0-9]*$') is false);
$BODY$
LANGUAGE SQL VOLATILE
COST 100;



DROP FUNCTION IF EXISTS GRF_File_Custom_Validator(bigint, bigint, bigint);
CREATE OR REPLACE FUNCTION GRF_File_Custom_Validator(
     IN stateid bigint,
     IN uploadbatchid bigint,
     IN reportyear bigint)

RETURNS TABLE(batchuploadid bigint,
  line text,
  fieldname text,
  reason text,
  errortype text) AS

$BODY$
 WITH organizaton_hier as (select org.displayidentifier, parent.displayidentifier as parentdisplayidentifier, org.organizationtypeid 
                                      from organization_children_active_or_inactive(stateid) org
                                      inner join organizationrelation orgrel on orgrel.organizationid = org.id
                                      inner join organization parent on parent.id = orgrel.parentorganizationid),
		 organization_users as (select distinct BTRIM(au.uniquecommonidentifier) as educatoridentifier
					from aartuser au 
					join usersorganizations uo on au.id = uo.aartuserid and uo.activeflag = true
					where uo.organizationid in (select id from organization_children_active_or_inactive(stateid) union select stateid))
  
    		select $2 as batchuploadid, (tmp.linenumber+1)::text as line, ' '::text as fieldname,     
    		case when ca.id is null then 'Subject ~ the Subject entered is invalid##' else '' end ||
    		case when (ca.id is not null and gc.id is null) then 'Current_Grade_Level ~ the Current_Grade_Level does not exist##' else '' end||
		case when orgst.id is null then 'state ~ the state information uploaded does not match the logged in state##' else '' end ||
		case when (orgst.id is not null and orgdt.displayidentifier is null) then 'District_code ~ the District_code does not exist##' else '' end ||
		case when (orgdt.displayidentifier is not null and orgsch.displayidentifier is null) then 'School_code ~ the School_code does not exist##' else '' end ||
		case when (orgst.id is not null and orgat.displayidentifier is null) then 'Attendance_School_Program_Identifier ~ the Attendance_School_Program_Identifier does not exist##' else '' end ||
		case when (orgst.id is not null and orgayp.displayidentifier is null) then 'AYP_School_Identifier ~ the AYP_School_Identifier does not exist##' else '' end ||
		case when (orgst.id is not null and orgusers.educatoridentifier is null) then 'Educator_Identifier ~ the Educator_Identifier does not exist##' else '' end ||
		case when st.id is null then 'Studentid ~ the Studentid does not exist##' else '' end ||''::text as reason, 'reject'::text as errortype
														     
		from tempuploadgrffile tmp
		left join contentarea ca on lower(ca.abbreviatedname) = lower(TRIM(coalesce(tmp.subject,''))) and ca .activeflag is true
		left join gradecourse gc on lower(gc.abbreviatedname) = lower(TRIM(coalesce(tmp.currentgradelevel,''))) and gc.contentareaid  = ca.id and gc.activeflag is true
		left join organization orgst on lower(orgst.organizationname) =  lower(TRIM(coalesce(tmp.state,''))) and orgst.id = stateid
		left join organizaton_hier orgdt on lower(orgdt.displayidentifier) =  lower(TRIM(coalesce(tmp.districtcode,''))) and lower(orgdt.parentdisplayidentifier) = lower(orgst.displayidentifier)
		left join organizaton_hier orgsch on lower(orgsch.displayidentifier) =  lower(TRIM(coalesce(tmp.schoolcode,''))) and lower(orgsch.parentdisplayidentifier) = lower(orgdt.displayidentifier)
		left join (select * from organization_children_active_or_inactive(stateid)) orgat on lower(orgat.displayidentifier) = lower(coalesce(tmp.attendanceschoolprogramidentifier,'')) 
		                                                     and coalesce(tmp.attendanceschoolprogramidentifier,'') != '' 
		left join (select * from organization_children_active_or_inactive(stateid)) orgayp on lower(orgayp.displayidentifier) = lower(coalesce(tmp.aypschoolidentifier,'')) 
									 and coalesce(tmp.aypschoolidentifier,'') != '' 
		left join organization_users orgusers on lower(orgusers.educatoridentifier) = lower(coalesce(tmp.educatoridentifier,'')) 
		left join student st on st.id = tmp.studentid::bigint
		where batchuploadid = $2 and 
		(ca.id is null OR
		gc.id is null OR
		orgst.id is null OR
		(orgst.id is not null and orgdt.displayidentifier is null) OR
		(orgst.id is not null and orgsch.displayidentifier is null) OR
		(orgst.id is not null and orgat.displayidentifier is null) OR
		(orgst.id is not null and orgayp.displayidentifier is null) OR
		(orgst.id is not null and orgusers.educatoridentifier is null) OR
		st.id is null);
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;




DROP FUNCTION  IF EXISTS GRF_File_column_change_Validator(bigint, bigint, bigint);
 CREATE OR REPLACE FUNCTION GRF_File_column_change_Validator(
     stateid bigint,
     uploadbatchid bigint,
     reportyear bigint)

RETURNS TABLE(batchuploadid bigint,
  line text,
  fieldname text,
  reason text,
  errortype text) AS

$BODY$

select $2 as batchuploadid, (src.linenumber+1)::text as line, ' '::text as fieldname,
case when coalesce(src.finalband::text,'')<>coalesce(tgt.finalband::text,'') then 'Final_Band ~ the Final_Band does not match with the original GRF record##' else '' end
|| case when coalesce(src.sgp::text,'')<>coalesce(tgt.sgp::text,'') then 'Sgp ~ Sgp does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.performancelevel::text,'')<>coalesce(tgt.performancelevel::text,'')) then 'Performance_Level  ~ the Performance_Level does not match with the original GRF record##' else '' end
|| case when coalesce(src.nyperformancelevel::text,'')<>coalesce(tgt.nyperformancelevel::text,'') then 'NY_Performance_Level ~ the NY_Performance_Level does not match with the original GRF record##' else '' end
|| case when coalesce(src.totallinkagelevelsmastered::text,'')<>coalesce(tgt.totallinkagelevelsmastered::text,'') then 'Total_Linkage_Levels_Mastered ~ the Total_Linkage_Levels_Mastered does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee1::text,'')<>coalesce(tgt.ee1::text,'')) then 'EE_1 ~ the EE_1 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee2::text,'')<>coalesce(tgt.ee2::text,'') ) then 'EE_2 ~ the EE_2 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee3::text,'')<>coalesce(tgt.ee3::text,'') ) then 'EE_3 ~ the EE_3 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee4::text,'')<>coalesce(tgt.ee4::text,'') ) then 'EE_4 ~ the EE_4 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee5::text,'')<>coalesce(tgt.ee5::text,'') ) then 'EE_5 ~ the EE_5 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee6::text,'')<>coalesce(tgt.ee6::text,'') ) then 'EE_6 ~ the EE_6 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee7::text,'')<>coalesce(tgt.ee7::text,'') ) then 'EE_7 ~ the EE_7 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee8::text,'')<>coalesce(tgt.ee8::text,'') ) then 'EE_8 ~ the EE_8 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee9::text,'')<>coalesce(tgt.ee9::text,'') ) then 'EE_9 ~ the EE_9 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee10::text,'')<>coalesce(tgt.ee10::text,'') ) then 'EE_10 ~ the EE_10 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee11::text,'')<>coalesce(tgt.ee11::text,'') ) then 'EE_11 ~ the EE_11 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee12::text,'')<>coalesce(tgt.ee12::text,'') ) then 'EE_12 ~ the EE_12 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee13::text,'')<>coalesce(tgt.ee13::text,'') ) then 'EE_13 ~ the EE_13 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee14::text,'')<>coalesce(tgt.ee14::text,'') ) then 'EE_14 ~ the EE_14 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee15::text,'')<>coalesce(tgt.ee15::text,'') ) then 'EE_15 ~ the EE_15 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee16::text,'')<>coalesce(tgt.ee16::text,'') ) then 'EE_16 ~ the EE_16 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee17::text,'')<>coalesce(tgt.ee17::text,'') ) then 'EE_17 ~ the EE_17 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee18::text,'')<>coalesce(tgt.ee18::text,'') ) then 'EE_18 ~ the EE_18 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee19::text,'')<>coalesce(tgt.ee19::text,'') ) then 'EE_19 ~ the EE_19 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee20::text,'')<>coalesce(tgt.ee20::text,'') ) then 'EE_20 ~ the EE_20 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee21::text,'')<>coalesce(tgt.ee21::text,'') ) then 'EE_21 ~ the EE_21 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee22::text,'')<>coalesce(tgt.ee22::text,'') ) then 'EE_22 ~ the EE_22 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee23::text,'')<>coalesce(tgt.ee23::text,'') ) then 'EE_23 ~ the EE_23 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce (src.ee24::text,'')<>coalesce(tgt.ee24::text,'') ) then 'EE_25 ~ the EE_24 does not match with the original GRF record##' else '' end
|| case when (src.gradechange is false and coalesce(src.ee25::text,'')<>coalesce(tgt.ee25::text,'') ) then 'EE_25 does not match with the original GRF record##' else '' end
|| case when coalesce(src.iowalinkagelevelsmastered::text,' ')<>coalesce(tgt.iowalinkagelevelsmastered::text,'IOWA_linkage_level_mastered ~ the IOWA_linkage_level_mastered does not match with the original GRF record##') then '' else '' end
|| case when coalesce(src.uniquerowidentifier::text,' ')<>coalesce(tgt.externaluniquerowidentifier::text,'Unique_Row_Identifier ~ the Unique_Row_Identifier does not match with the original GRF record##') then '' else '' end
|| case when (src.gradechange is false and coalesce(src.ee26::text,'')<>coalesce(tgt.ee26::text,'')) then 'EE_26 ~ the EE_26 does not match with the original GRF record##' else '' end ::text as reason, 'reject'::text as errortype
from tempuploadgrffile src
inner join uploadgrffile tgt on tgt.externaluniquerowidentifier = src.uniquerowidentifier::bigint 
and tgt.stateid = $1 
and tgt.reportyear = $3 
and tgt.versionid =0
where src.batchuploadid = $2 and (
coalesce(src.finalband::text,' ')<>coalesce(tgt.finalband::text,' ') OR
coalesce(src.sgp::text,' ')<>coalesce(tgt.sgp::text,' ') OR
(src.gradechange is false and coalesce(src.performancelevel::text,' ')<>coalesce(tgt.performancelevel::text,' ')) OR
coalesce(src.nyperformancelevel::text,' ')<>coalesce(tgt.nyperformancelevel::text,' ') OR
coalesce(src.totallinkagelevelsmastered::text,' ')<>coalesce(tgt.totallinkagelevelsmastered::text,' ') OR
(src.gradechange is false and coalesce(src.ee1::text,' ')<>coalesce(tgt.ee1::text,' ')) OR
(src.gradechange is false and coalesce(src.ee2::text,' ')<>coalesce(tgt.ee2::text,' ')) OR
(src.gradechange is false and coalesce(src.ee3::text,' ')<>coalesce(tgt.ee3::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee4::text,' ')<>coalesce(tgt.ee4::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee5::text,' ')<>coalesce(tgt.ee5::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee6::text,' ')<>coalesce(tgt.ee6::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee7::text,' ')<>coalesce(tgt.ee7::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee8::text,' ')<>coalesce(tgt.ee8::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee9::text,' ')<>coalesce(tgt.ee9::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee10::text,' ')<>coalesce(tgt.ee10::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee11::text,' ')<>coalesce(tgt.ee11::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee12::text,' ')<>coalesce(tgt.ee12::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee13::text,' ')<>coalesce(tgt.ee13::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee14::text,' ')<>coalesce(tgt.ee14::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee15::text,' ')<>coalesce(tgt.ee15::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee16::text,' ')<>coalesce(tgt.ee16::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee17::text,' ')<>coalesce(tgt.ee17::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee18::text,' ')<>coalesce(tgt.ee18::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee19::text,' ')<>coalesce(tgt.ee19::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee20::text,' ')<>coalesce(tgt.ee20::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee21::text,' ')<>coalesce(tgt.ee21::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee22::text,' ')<>coalesce(tgt.ee22::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee23::text,' ')<>coalesce(tgt.ee23::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee24::text,' ')<>coalesce(tgt.ee24::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee25::text,' ')<>coalesce(tgt.ee25::text,' ') ) OR
(src.gradechange is false and coalesce(src.ee26::text,' ')<>coalesce(tgt.ee26::text,' ') ) OR
(coalesce(src.iowalinkagelevelsmastered::text,' ')<>coalesce(tgt.iowalinkagelevelsmastered::text,' ') ) OR
coalesce(src.uniquerowidentifier::text,' ')<>coalesce(tgt.externaluniquerowidentifier::text,' '));

$BODY$
LANGUAGE SQL VOLATILE
COST 100;


DROP FUNCTION IF EXISTS grf_file_Updated_validator(bigint, bigint, bigint);

CREATE OR REPLACE FUNCTION grf_file_Updated_validator(uploadstateid bigint, uploadbatchid bigint, uploadtype text, assessmentprogramid bigint, uploadreportyear bigint, createduser bigint)
  RETURNS SETOF record AS
$BODY$
DECLARE
  ref record;
  valid boolean = true;
BEGIN    
       --Validate externaluniquerowidentifier, studentid, subject combination with original file.....Extra new student also will be rejected
       FOR ref in select tmp.batchuploadid, (tmp.linenumber+1)::text as line, 'StudentId'::text as fieldname,'the combination of Studentid, Subject, and Unique_Row_Identifier are not available in the original GRF record'::text as reason,  'reject'::text as errortype 
		from tempuploadgrffile tmp
		left join uploadgrffile ugf on tmp.uniquerowidentifier = ugf.externaluniquerowidentifier::text 
					   and lower(tmp.subject) = lower(ugf.subject) 
					   and tmp.studentid= ugf.studentid::text
					   and ugf.recentversion is true
					   and ugf.reportyear = uploadreportyear
		where ugf.studentid is null and tmp.batchuploadid = uploadbatchid  
       LOOP
	return next ref; 
	valid = false;  
       END LOOP;
    RAISE NOTICE 'Validate externaluniquerowidentifier, studentid, subject combination with original file.....Extra new student also will be rejected Completed: %', valid;
     if valid is true THEN
      --If update GRF then check for unchanged columns  
      For ref in select batchuploadid, line, fieldname, reason, errortype  
	       from GRF_File_column_change_Validator(uploadstateid, uploadbatchid,uploadreportyear)
      LOOP
	return next ref; 
	valid = false;
      END LOOP;   	
    END IF;
    RAISE NOTICE 'GRF_File_update_Validator Completed: %', valid;	
 END;     
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000; 

