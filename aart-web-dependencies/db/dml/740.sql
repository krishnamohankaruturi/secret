--Warning message add student page

INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('add_student_exist_not_active_find','addstudentwarningmsg','This student already exists, but not active. Would you like to activate?','exist_not_active_find',12,now(),12,now());

INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('add_student_exist_not_active_noedit_perm','addstudentwarningmsg','A student with the entered State Student Identifier already exists, but not active. Please see your district or state coordinators to activate for the current school year.','exist_not_active_noedit_perm',12,now(),12,now());


INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('add_student_exist_active','addstudentwarningmsg','A student with the entered State Student Identifier already exists and active for this school year. To make changes, please use the upload, edit or transfer functions.','exist_active',12,now(),12,now());

INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('add_student_exist_active_diff_assessment_program','addstudentwarningmsg','A student with the entered State Student Identifier already exists and is active, but not associated with the selected Assessment Program. Please contact your state or district coordinators.','exist_active_diff_assessment_program',12,now(),12,now());

INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('add_student_exist_active_diff_org','addstudentwarningmsg','A student with the entered State Student Identifier already exists, but is not found in your organization.','exist_active_diff_org',12,now(),12,now());

	
	--ESOL Participation Code  
  INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_esol_Not_Eligible','st_esolparticipationcode','Not Eligible [0]','0',12,now(),12,now());
	
   INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_esol_Title_III_Funded','st_esolparticipationcode','Title III Funded [1]','1',12,now(),12,now());
	
  INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_esol_State_ESOL/Bilingual_Funded','st_esolparticipationcode','State ESOL/Bilingual Funded [2]','2',12,now(),12,now());	

  
  INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_esol_Title_III_and_State_ESOL/Bilingual_Funded','st_esolparticipationcode','Title III and State ESOL/Bilingual Funded [3]','3',12,now(),12,now());	
	
 INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_esol_Monitored_ESOL_Student','st_esolparticipationcode','Monitored ESOL Student [4]','4',12,now(),12,now());	
	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_esol_Eligible_but_not_supported','st_esolparticipationcode','Eligible but not supported [5]','5',12,now(),12,now());		
	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_esol_ESOL_but_not_funded','st_esolparticipationcode','ESOL but not funded [6]','6',12,now(),12,now());		


--GENDER	

INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_gender_male','st_gender','Male','1',12,now(),12,now());		


INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_gender_female','st_gender','Female','0',12,now(),12,now());


--Hispanic Ethnicity

INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_Hispanic_Ethenticity_Yes','st_hispanicethnicity','Yes','true',12,now(),12,now());
	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_Hispanic_Ethenticity_No','st_hispanicethnicity','No','false',12,now(),12,now());	

--Gifted Student

INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_Gifted_Student_Yes','st_giftedstudent','Yes','true',12,now(),12,now());
	
INSERT INTO public.appconfiguration(
            attrcode, attrtype, attrname, attrvalue, createduser, 
            createddate, modifieduser, modifieddate)
    VALUES ('st_Gifted_Student_No','st_giftedstudent','No','false',12,now(),12,now());
	
	
--Generation
INSERT INTO public.appconfiguration(
	id, 
	attrcode, 
	attrtype, 
	attrname, 
	attrvalue, 
	activeflag, 
	createduser, 
	createddate, 
	modifieduser, 
	modifieddate)
	VALUES ((select nextval('appconfiguration_id_seq'::regclass)), 
			'student_generation_Jr', 
			'student_generation', 
			'Jr', 
			'Jr', 
			true, 
			12, 
			now(), 
			12, 
			now());

INSERT INTO public.appconfiguration(
	id, 
	attrcode, 
	attrtype, 
	attrname, 
	attrvalue, 
	activeflag, 
	createduser, 
	createddate, 
	modifieduser, 
	modifieddate)
	VALUES ((select nextval('appconfiguration_id_seq'::regclass)), 
			'student_generation_Sr', 
			'student_generation', 
			'Sr', 
			'Sr', 
			true, 
			12, 
			now(), 
			12, 
			now());
			
INSERT INTO public.appconfiguration(
	id, 
	attrcode, 
	attrtype, 
	attrname, 
	attrvalue, 
	activeflag, 
	createduser, 
	createddate, 
	modifieduser, 
	modifieddate)
	VALUES ((select nextval('appconfiguration_id_seq'::regclass)), 
			'student_generation_II', 
			'student_generation', 
			'II', 
			'II', 
			true, 
			12, 
			now(), 
			12, 
			now());	
			
 INSERT INTO public.appconfiguration(
	id, 
	attrcode, 
	attrtype, 
	attrname, 
	attrvalue, 
	activeflag, 
	createduser, 
	createddate, 
	modifieduser, 
	modifieddate)
	VALUES ((select nextval('appconfiguration_id_seq'::regclass)), 
			'student_generation_III', 
			'student_generation', 
			'III', 
			'III', 
			true, 
			12, 
			now(), 
			12, 
			now());	
			
INSERT INTO public.appconfiguration(
	id, 
	attrcode, 
	attrtype, 
	attrname, 
	attrvalue, 
	activeflag, 
	createduser, 
	createddate, 
	modifieduser, 
	modifieddate)
	VALUES ((select nextval('appconfiguration_id_seq'::regclass)), 
			'student_generation_IV', 
			'student_generation', 
			'IV', 
			'IV', 
			true, 
			12, 
			now(), 
			12, 
			now());
INSERT INTO public.appconfiguration(
	id, 
	attrcode, 
	attrtype, 
	attrname, 
	attrvalue, 
	activeflag, 
	createduser, 
	createddate, 
	modifieduser, 
	modifieddate)
	VALUES ((select nextval('appconfiguration_id_seq'::regclass)), 
			'student_generation_V', 
			'student_generation', 
			'V', 
			'V', 
			true, 
			12, 
			now(), 
			12, 
			now());
INSERT INTO public.appconfiguration(
	id, 
	attrcode, 
	attrtype, 
	attrname, 
	attrvalue, 
	activeflag, 
	createduser, 
	createddate, 
	modifieduser, 
	modifieddate)
	VALUES ((select nextval('appconfiguration_id_seq'::regclass)), 
			'student_generation_VI', 
			'student_generation', 
			'VI', 
			'VI', 
			true, 
			12, 
			now(), 
			12, 
			now());	
	INSERT INTO public.appconfiguration(
	id, 
	attrcode, 
	attrtype, 
	attrname, 
	attrvalue, 
	activeflag, 
	createduser, 
	createddate, 
	modifieduser, 
	modifieddate)
	VALUES ((select nextval('appconfiguration_id_seq'::regclass)), 
			'student_generation_VII', 
			'student_generation', 
			'VII', 
			'VII', 
			true, 
			12, 
			now(), 
			12, 
			now());	
INSERT INTO public.appconfiguration(
	id, 
	attrcode, 
	attrtype, 
	attrname, 
	attrvalue, 
	activeflag, 
	createduser, 
	createddate, 
	modifieduser, 
	modifieddate)
	VALUES ((select nextval('appconfiguration_id_seq'::regclass)), 
			'student_generation_VII', 
			'student_generation', 
			'VII', 
			'VII', 
			true, 
			12, 
			now(), 
			12, 
			now());	
INSERT INTO public.appconfiguration(
	id, 
	attrcode, 
	attrtype, 
	attrname, 
	attrvalue, 
	activeflag, 
	createduser, 
	createddate, 
	modifieduser, 
	modifieddate)
	VALUES ((select nextval('appconfiguration_id_seq'::regclass)), 
			'student_generation_IX', 
			'student_generation', 
			'IX', 
			'IX', 
			true, 
			12, 
			now(), 
			12, 
			now());

--F689 dml
insert into authorities
   (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,level,sortorder)
select 'VIEW_PREDICTIVE_QUESTION_CSV','View Interim Predictive Student Question CSV','Other-Interim',NOW(),
          (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
          (select id from aartuser where username='cetesysadmin'),'Other','Interim',1,
          17180
where not exists(
select 1 from authorities where authority='VIEW_INTERIM_PREDICTIVE_QUESTION_CSV'
);


update category set categorydescription = 'I' 
where categorycode = 'NO_CREDIT' and categorytypeid in (select id from categorytype where typecode = 'CREDIT_EARNED');

update category set categorydescription = 'PC' 
where categorycode = 'PARTIAL_CREDIT' and categorytypeid in (select id from categorytype where typecode = 'CREDIT_EARNED');

update category set categorydescription = 'C' 
where categorycode = 'FULL_CREDIT' and categorytypeid in (select id from categorytype where typecode = 'CREDIT_EARNED');

update category set categorydescription = 'n/a' 
where categorycode = 'QUESTION_UNANSWERED' and categorytypeid in (select id from categorytype where typecode = 'CREDIT_EARNED');



--fieldspecification script 
INSERT INTO fieldspecification(
             fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'accountabilityDistrictIdentifier', null, null, null, 30,false,true,null,'Accountability_District_Identifier',true,now(), (select id from aartuser where email ='cete@ku.edu'),true,now(), (select id from aartuser where email ='cete@ku.edu'),false,null,null,null);
	
	

--fieldspecificationsrecordtypes script
UPDATE fieldspecificationsrecordtypes set mappedname ='Accountability_School_Identifier' where recordtypeid in 
(select id  from category where categorycode ='ENRL_RECORD_TYPE' and activeflag is true)
and fieldspecificationid in (select id from fieldspecification  where fieldname ='aypSchoolIdentifier' and mappedname ='AYP_QPA_Bldg_No'  and rejectifempty is false  and rejectifinvalid  is true and activeflag is true );


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification  where fieldname ='accountabilityDistrictIdentifier' and mappedname ='Accountability_District_Identifier'  and rejectifempty is false  and rejectifinvalid  is true and activeflag is true ), (select id  from category where categorycode ='ENRL_RECORD_TYPE' and activeflag is true), now(), (select id from aartuser where email ='cete@ku.edu'),true,now(), (select id from aartuser where email ='cete@ku.edu'), 'Accountability_District_Identifier');
	
	
	
update fieldspecification set formatregex='(0?[1-9]|1[012])(/)(0?[1-9]|[12][0-9]|3[01])(/)[1-9][0-9][0-9][0-9]'
where id in (select fieldspecificationid from fieldspecificationsrecordtypes where  recordtypeid in (select id from category where categorycode='ENRL_RECORD_TYPE' and activeflag is true
) and mappedname in ('School_Entry_Date','District_Entry_Date','State_Entry_Date','Date_of_Birth'));