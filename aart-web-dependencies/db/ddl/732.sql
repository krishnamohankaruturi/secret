--ddl/732.sql

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
     join userorganizationsgroups uog on uog.userorganizationid=uo.id and groupid=(select id from groups  where groupcode ='TEA')
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
