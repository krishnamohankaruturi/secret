--ddl/731.sql

--grf_file_common_validator 

CREATE OR REPLACE FUNCTION grf_file_common_validator(
    IN stateid bigint,
    IN uploadbatchid bigint,
    IN reportyear bigint)
  RETURNS TABLE(batchuploadid bigint, line text, fieldname text, reason text, errortype text) AS
$BODY$
    select $2 as batchuploadid, (linenumber+1)::text as line, ' '::text as fieldname, 
                   case when (SELECT length(attendanceSchoolProgramIdentifier)> 100) then 'Attendance_School_Program_Identifier ~ the value in Attendance_School_Program_Identifier must be less than or equal to 100 charecters##' else '' end ||
		   case when (SELECT length(aypSchoolIdentifier)> 100) then 'AYP_School_Identifier ~ the value in AYP_School_Identifier must be less than or equal to 100 charecters##' else '' end ||
		   case when (SELECT length(coalesce(localstudentidentifier::text,'')) > 20) then 'Local_Student_Identifier ~ the value in Local_Student_Identifier must be less than or equal to 100 charecters##' else '' end ||
		   case when coalesce(comprehensiveRace,'') not in ('','1','2','4','5','6','7','8') then 'Comprehensive_Race ~ the value in Comprehensive_Race is not allowed##' else '' end ||
		   case when coalesce(currentGradelevel,'') not in ('K','1','2','3','4','5','6','7','8','9','10','11','12') then 'Current_Grade_Level ~ the value in Current_Grade_Level is not allowed##' else '' end ||
		   case when coalesce(firstLanguage,'') not in ('','0','1','2','3','4','5','6','7','8','10','11','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47') then 'First_Language ~ the value in First_Language is not allowed##' else '' end ||   
		   case when (dateOfBirth is not null and (dateOfBirth::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) then 'Date_of_Birth ~ the value in Date_of_Birth is not valid. Should be in MM/DD/YYYY format##' else '' end ||
		   case when (districtEntryDate is not null and (districtEntryDate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) then 'District_Entry_Date ~ the value in District_Entry_Date is not valid. Should be in MM/DD/YYYY format##' else '' end ||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) then 'Exit_Withdrawl_Date ~ the value in Exit_Withdrawl_Date is not valid. Should be in MM/DD/YYYY format##' else '' end ||
		   case when (districtEntryDate is not null and (districtEntryDate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is true and to_date(districtEntryDate, 'mm/dd/yyyy') < '12/12/1989') then 'District_Entry_Date ~ the value in District_Entry_Date should be greater than 1990##' else '' end||
		   case when (exitwithdrawaldate is not null and (exitwithdrawaldate::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is true and exitwithdrawaldate is not null and to_date(exitwithdrawaldate, 'mm/dd/yyyy') < '12/12/1989') then 'Exit_Withdrawl_Date ~ the value in Exit_Withdrawl_Date should be greater than 1990##' else '' end||
		   case when (SELECT length(coalesce(district::text,''))  = 0) then 'District ~ the District not specified##' when (SELECT length(coalesce(district::text,'')) > 100) then 'District ~ the value in District must be less than or equal to 100 charecters##' else '' end || 
		   case when (SELECT length(coalesce(educatorFirstName::text,'')) > 80) then 'Educator_First_Name ~ the value in field Educator_First_Name must be less than or equal to 80 charecters##' else '' end ||
		   case when coalesce(educatorIdentifier::text,'') = ''  then 'Educator_Identifier ~ Educator_Identifier is not specified##' else '' end ||
		   case when (SELECT length(coalesce(educatorLastName::text,'')) > 80) then 'Educator_Last_Name ~ the value in field Educator_Last_Name must be less than or equal to 80 characters##' else '' end ||
		   case when (SELECT length(coalesce(educatorUserName::text,'')) > 254) then 'Educator_Username ~ the value in field Educator_Username must be less than or equal to 254 characters##' else '' end ||
		   case when coalesce(esolParticipationCode,'') not in ('','0','1','2','3','4','5','6') then 'ESOL_Participation_Code ~ the value in ESOL_Participation_Code is not allowed##' else '' end ||
		   case when ((coalesce(uniquerowidentifier,'') ~ '^[0-9]+$') is false) then 'Unique_Row_Identifier ~ the value in Unique_Row_Identifier is not valid##' else '' end ||
		   case when (SELECT length(coalesce(uniquerowidentifier,'')) > 8) then 'Unique_Row_Identifier ~ the value in Unique_Row_Identifier must be less than or equal to 8 digits##' else '' end ||
		   case when (SELECT length(coalesce(finalBand::text,'')) > 150) then 'Final_Band ~ the value in field Final_Band must be less than or equal to 150 charecters##' else '' end ||
		   case when lower(coalesce(gender,'')) not in ('','male','female') then 'Gender ~ the value in Gender is not valid##' else '' end ||
		   case when (SELECT length(coalesce(generationCode::text,'')) > 10) then 'Generation_Code ~ the value in field Generation_Code must be less than or equal to 10 charecters##' else '' end || 
		   case when coalesce(hispanicEthnicity,'') not in ('1','0') then 'Hispanic_Ethnicity ~ the value in Hispanic_Ethnicity is not allowed##' else '' end || 
		   case when coalesce(invalidationCode,'') not in ('1','0') then 'Invalidation_Code ~ the value in Invalidation_Code is not allowed##' else '' end ||
		   case when (SELECT length(coalesce(studentlegalFirstName::text,'')) = 0) then 'Student_Legal_First_Name ~ Student_Legal_First_Name not specified##' when (SELECT length(coalesce(studentlegalFirstName::text,'')) > 60) then 'Student_Legal_First_Name ~ the value in field Student_Legal_First_Name must be less than or equal to 60 characters##'else '' end || 
		   case when (SELECT length(coalesce(studentlegalLastName::text,'')) = 0) then 'Student_Legal_Last_Name ~ Student_Legal_Last_Name not specified##' when (SELECT length(coalesce(studentlegalLastName::text,'')) > 60) then 'Student_Legal_Last_Name ~ the value in field Student_Legal_Last_Name must be less than or equal to 60 characters##'else '' end || 
		   case when (SELECT length(coalesce(studentlegalMiddleName::text,'')) > 80) then 'Student_Legal_Middle_Name ~ the value in field Student_Legal_Middle_Name must be less than or equal to 80 characters##' else '' end || 
		   case when coalesce(performanceLevel,'') not in ('1','2','3','4','9') then 'Performance_Level ~ the value in Performance_Level is not allowed##' else '' end || 
		   case when UPPER(coalesce(primaryDisabilityCode,'')) not in ('','AM','DB','DD','ED','HI','LD','MD','ID','OH','OI','SL','TB','VI','ND','WD','EI','DA') then 'Primary_Disability_Code ~ the value in Primary_Disability_Code is not allowed##' else '' end ||
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
		   case when (coalesce(subject::text,'') = '' and coalesce(performancelevel,'') not in ('9')) then 'Subject ~ the value in Subject not specified##' else '' end ||
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
		      (UPPER(coalesce(primaryDisabilityCode,'')) not in ('','AM','DB','DD','ED','HI','LD','MD','ID','OH','OI','SL','TB','VI','ND','WD','EI','DA')) OR
		      (coalesce(performanceLevel,'') not in ('1','2','3','4','9')) OR
		      (coalesce(esolParticipationCode,'') not in ('','0','1','2','3','4','5','6')) OR 
		      (coalesce(hispanicEthnicity,'') not in ('1','0')) OR 
		      (coalesce(invalidationCode,'') not in ('1','0')) OR		      
		      (coalesce(firstLanguage,'') not in ('','0','1','2','3','4','5','6','7','8','10','11','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47')) OR 		      
		      (dateOfBirth is not null and (dateOfBirth::text ~ '^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$') is false) OR
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
		      (lower(coalesce(gender,'')) not in ('','male','female')) OR
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
		      ((SELECT length(coalesce(ee26::text,'')) > 1) OR (ee26::text ~ '^[0-9]*$') is false);
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;


  --grf_file_custom_validator

  CREATE OR REPLACE FUNCTION grf_file_custom_validator(
    IN stateid bigint,
    IN uploadbatchid bigint,
    IN reportyear bigint)
  RETURNS TABLE(batchuploadid bigint, line text, fieldname text, reason text, errortype text) AS
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


  case when coalesce(performancelevel,'') in ('9') and lower(TRIM(coalesce(tmp.subject,''))) =''
  then 
   case when (gc.id is null) then 'Current_Grade_Level ~ the Current_Grade_Level does not exist##' else '' end||
   case when orgst.id is null then 'state ~ the state information uploaded does not match the logged in state##' else '' end ||
   case when (orgst.id is not null and orgdt.displayidentifier is null) then 'District_code ~ the District_code does not exist##' else '' end ||
   case when (orgdt.displayidentifier is not null and orgsch.displayidentifier is null) then 'School_code ~ the School_code does not exist##' else '' end ||
   case when (orgst.id is not null and orgat.displayidentifier is null) then 'Attendance_School_Program_Identifier ~ the Attendance_School_Program_Identifier does not exist##' else '' end ||
   case when (orgst.id is not null and orgayp.displayidentifier is null) then 'AYP_School_Identifier ~ the AYP_School_Identifier does not exist##' else '' end ||
   case when (orgst.id is not null and orgusers.educatoridentifier is null) then 'Educator_Identifier ~ the Educator_Identifier does not exist##' else '' end ||
   case when st.id is null then 'Studentid ~ the Studentid does not exist##' else '' end ||''::text
 
  else 
  
   case when ca.id is null then 'Subject ~ the Subject entered is invalid##' else '' end ||
   case when (ca.id is not null and gc.id is null) then 'Current_Grade_Level ~ the Current_Grade_Level does not exist##' else '' end||
   case when orgst.id is null then 'state ~ the state information uploaded does not match the logged in state##' else '' end ||
   case when (orgst.id is not null and orgdt.displayidentifier is null) then 'District_code ~ the District_code does not exist##' else '' end ||
   case when (orgdt.displayidentifier is not null and orgsch.displayidentifier is null) then 'School_code ~ the School_code does not exist##' else '' end ||
   case when (orgst.id is not null and orgat.displayidentifier is null) then 'Attendance_School_Program_Identifier ~ the Attendance_School_Program_Identifier does not exist##' else '' end ||
   case when (orgst.id is not null and orgayp.displayidentifier is null) then 'AYP_School_Identifier ~ the AYP_School_Identifier does not exist##' else '' end ||
   case when (orgst.id is not null and orgusers.educatoridentifier is null) then 'Educator_Identifier ~ the Educator_Identifier does not exist##' else '' end ||
   case when st.id is null then 'Studentid ~ the Studentid does not exist##' else '' end ||''::text 
 
  END as reason, 'reject'::text as errortype
                   
  from tempuploadgrffile tmp
  left join contentarea ca on lower(ca.abbreviatedname) = lower(TRIM(coalesce(tmp.subject,''))) and ca .activeflag is true
  left join gradecourse gc on lower(gc.abbreviatedname) = lower(TRIM(coalesce(tmp.currentgradelevel,''))) 
and gc.contentareaid  = (case when (coalesce(performancelevel,'') in ('9') and lower(TRIM(coalesce(tmp.subject,''))) ='') 
  THEN (select id from contentarea where abbreviatedname = 'ELA' order by modifieddate desc limit 1) ELSE ca.id END) and gc.activeflag is true
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
  ((ca.id is null and coalesce(performancelevel,'') not in ('9')) OR
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