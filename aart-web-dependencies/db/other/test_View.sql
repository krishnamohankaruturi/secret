--test section order
--task order
--foil order
Select 
t.id as test_id,
t.testname as test_name,
t.numitems as test_num_items,
t.externalid as test_external_id,
t.createdate as test_create_date,
t.modifieddate as test_modified_date,
t.originationcode as test_origination_code,
t.directions as test_directions,
t.uitypecode as test_ui_type_code,
t.reviewtext as test_review_text,
t.begininstructions as test_begin_instructions,
t.endinstructions as test_end_instructions,
t.gradecourseid as test_grade_course_id,
t.contentareaid as test_content_area_id,
t.status as test_status,
ts.id as test_section_id,
ts.id as test_section_external_id,
 1 as test_section_order,
ts.testsectionname as test_Section_name,
ts.numberoftestitems as test_section_number_of_items,
ts.helpnotes as test_section_help_notes,
ts.toolsusageid as test_section_tools_usage_id,
ts.taskdeliveryruleid as test_section_task_delivery_rule_id,
ts.createdate as test_section_create_date,
ts.modifieddate as test_section_modified_date,
ts.originationcode as test_section_origination_code,
ts.begininstructions as test_section_begin_instructions,
ts.endinstructions as test_section_end_instructions,
ts.contextstimulusid as test_section_context_stimulus_id,
tstv.taskvariantposition as test_section_task_variant_position,
tv.*,
  cfd.id as content_framework_detail_id,					
  cfd.externalid as content_framework_detail_external_id,				
  sortorder as content_framework_detail_sort_order,				
  name	as content_framework_detail_name,				
  contentcode as content_framework_detail_content_code,				
  description as content_framework_detail_description,
  comments as content_framework_detail_comments,				
  frameworklevelid as content_framework_detail_framework_level_id,			
  contentframeworkid as content_framework_id,			
  parentcontentframeworkdetailid as parent_content_framework_detail_id,	
  cfd.createdate as content_framework_detail_create_date,				
  cfd.modifieddate as content_framework_detail_modified_date,				
  cfd.originationcode as content_framework_detail_origination_code	
  from test t, testsection ts,
 testsectionstaskvariants tstv,taskvariant tv,
 taskvariantcontentframeworkdetail tvcfd,
 contentframeworkdetail cfd
where
t.id = ts.testid and
tstv.testsectionid = ts.id and
tstv.taskvariantid = tv.id and
tvcfd.contentframeworkdetailid = cfd.id and
tvcfd.taskvariantid = tv.id and
tvcfd.isprimary = true