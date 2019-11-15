--ddl/13.sql	

DROP FUNCTION IF EXISTS tasc_xml_creation(integer, character varying);
CREATE OR REPLACE FUNCTION tasc_xml_creation(ksdexmlauditid integer, 
				subjectarea character varying)
  RETURNS xml AS
$BODY$
      BEGIN
      RETURN   
      (

select xmlelement( name "TASC_Data", 
(
select xmlagg(TASCSTAGINGROW.tascXML) from 
(SELECT 
         xmlelement(name "TASC_Record",
         xmlconcat(
         xmlelement( name "Create_Date", create_date),
         xmlelement( name "Record_Common_ID", record_common_id),
         xmlelement( name "Record_Type", record_type),
         xmlelement( name "State_Student_Identifier", state_student_identifier),
         xmlelement( name "AYP_QPA_Bldg_No", ayp_qpa_bldg_no),
         xmlelement( name "Student_Legal_Last_Name", student_legal_last_name),
         xmlelement( name "Student_Legal_First_Name", student_legal_first_name),
         xmlelement( name "Student_Legal_Middle_Name", student_legal_middle_name),
         xmlelement( name "Student_Generation_Code", student_generation_code),
         xmlelement( name "Student_Gender", student_gender),
         xmlelement( name "Current_Grade_Level", current_grade_level),
         xmlelement( name "Current_School_Year", current_school_year),
         xmlelement( name "Attendance_Bldg_No", attendance_bldg_no),
         xmlelement( name "Educator_Bldg_No", educator_bldg_no),
         xmlelement( name "State_Subj_Area_Code", state_subj_area_code),
         xmlelement( name "Local_Course_ID", local_course_id),
         xmlelement( name "Course_Status", course_status),
         xmlelement( name "Teacher_Identifier", teacher_identifier),
         xmlelement( name "Teacher_Last_Name", teacher_last_name),
         xmlelement( name "Teacher_First_Name", teacher_first_name),
         xmlelement( name "Teacher_Middle_Name", teacher_middle_name),
         xmlelement( name "Teacher_District_Email", teacher_district_email)
         )::xml
       ) as tascXML FROM tasc_record_staging where ksdexmlaudit_id = ksdexmlauditId
	and state_subj_area_code = subjectArea order by id)  TASCSTAGINGROW
       ))
      );             
      END;
       $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100; 
