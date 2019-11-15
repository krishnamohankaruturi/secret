-- 573.sql dml

-- 573.sql dml
-- Move down temporarily to accommodate "Eligible individual"
update surveyresponse set responseorder=16 , responselabel='16' where responsevalue='Deafness' and activeflag='true';

-- update existing entry to split into two entries.
update surveyresponse set responsevalue = 'Non-categorical',
	modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') 
	where labelid = (select id from surveylabel where labelnumber='Q16_1') and responseorder = 14;
-- Add new entry
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
      VALUES ((select id from surveylabel where labelnumber='Q16_1'), 15,'Eligible individual', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
     now(), (Select id from aartuser where username = 'cetesysadmin'), '15');

-- For IOWA state students if the answer is Non-Categorical remap to Eligible individual
update studentsurveyresponse set surveyresponseid = (select id from  surveyresponse where responsevalue = 'Eligible individual')
	where id in (SELECT ssr.id FROM surveylabel sl
		JOIN surveyresponse sr ON sl.id = sr.labelid
		LEFT JOIN (SELECT * FROM studentsurveyresponse WHERE surveyid IN (select srv.id from survey srv 
		inner join student st on srv.studentid=st.id 
		inner join organization org on st.stateid=org.id 
		where org.displayidentifier='IA')) ssr ON (ssr.surveyresponseid=sr.id) 
		JOIN surveysection ss ON sl.surveysectionid = ss.id 
		WHERE 
			sr.labelid=sl.id AND sl.activeflag = true AND sr.activeflag = true AND sr.responselabel != '-1' 
			AND sl.globalPageNum =1 AND sl.labelnumber='Q16_1' AND sr.responsevalue = 'Non-categorical');

-- re-organize for sorted items.
update surveyresponse set responseorder=0, responselabel='0' where responsevalue='Deafness';
update surveyresponse set responseorder=16, responselabel='16' where responsevalue='Eligible individual';
update surveyresponse set responseorder=15, responselabel='15' where responsevalue='Non-categorical';
update surveyresponse set responseorder=14, responselabel='14' where responsevalue='Visual impairment, including blindness';
update surveyresponse set responseorder=13, responselabel='13' where responsevalue='Traumatic brain injury';
update surveyresponse set responseorder=12, responselabel='12' where responsevalue='Speech or language impairment'; 
update surveyresponse set responseorder=11, responselabel='11' where responsevalue='Specific learning disability'; 
update surveyresponse set responseorder=10, responselabel='10' where responsevalue='Other health impairment';
update surveyresponse set responseorder=9, responselabel='09' where responsevalue='Orthopedic impairment' ;
update surveyresponse set responseorder=8, responselabel='08' where responsevalue='Multiple disabilities' ;
update surveyresponse set responseorder=7, responselabel='07' where responsevalue='Intellectual disability' ;
update surveyresponse set responseorder=6, responselabel='06' where responsevalue='Hearing impairment';
update surveyresponse set responseorder=5, responselabel='05' where responsevalue='Emotional disturbance';
update surveyresponse set responseorder=4, responselabel='04' where responsevalue='Developmental delay' ;
update surveyresponse set responseorder=3, responselabel='03' where responsevalue='Deafness';

-- Edit First Contact Settings permission
insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser) 
	values('EDIT_FIRST_CONTACT_SETTINGS','Edit First Contact Settings','Student Management-First Contact', CURRENT_TIMESTAMP,
	(select id from aartuser where username='cetesysadmin'),TRUE, CURRENT_TIMESTAMP,
	(select id from aartuser where username='cetesysadmin'));
    
-- Category Type for First Contact Settings. Per US17690
insert into categorytype (typename, typecode,typedescription, createduser, activeflag, modifieduser)
	values ('Firct Contact Settings', 'FIRST CONTACT SETTINGS', 'First Contact Settings Questions Sets',
	(Select id from aartuser where username='cetesysadmin'), true, 
	(Select id from aartuser where username='cetesysadmin'));

--Question set categories for First Contact Settings. Per US17690
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
	VALUES ('Core set', 'CORE_SET_QUESTIONS',(select id from categorytype where typecode= 'FIRST CONTACT SETTINGS'),
	'Core Set of First Contact Settings');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
    VALUES ('All Questions', 'ALL_QUESTIONS',(select id from categorytype where typecode= 'FIRST CONTACT SETTINGS'),
    'All First Contact Questions');

--Insert Queries for firstcontactsurveysettings

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'AK' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'CO' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'IL' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'IA' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'KS' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'MO' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'NH' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'NJ' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'NY' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'ND' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'NC' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'OK' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'PA' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'UT' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'VT' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'WV' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));

INSERT INTO firstcontactsurveysettings (categoryid,organizationid,createddate,activeflag,modifieddate,createduser, modifieduser) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'WI' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'));


-- Move tabs to right by one position so that Language tab can be accommodated.
update surveylabel set globalpagenum=12 where globalpagenum=11;
update surveylabel set globalpagenum=13 where globalpagenum=12;
update surveylabel set globalpagenum=14 where globalpagenum=13;
update surveylabel set globalpagenum=15 where globalpagenum=14;

-- Move existing questions to right by one position 
update surveylabel set globalpagenum=12 where labelnumber in ('Q51_1','Q51_2','Q51_3','Q51_4','Q51_5','Q51_6','Q51_7','Q51_8');
update surveylabel set globalpagenum=13 where labelnumber in ('Q52','Q56_1','Q56_2','Q56_3','Q56_4','Q56_5','Q56_6','Q56_7','Q56_8');
update surveylabel set globalpagenum=14 where labelnumber in ('Q54_1','Q54_13','Q54_12','Q54_11','Q54_10','Q54_9','Q54_8',
	'Q54_7','Q54_5','Q54_4','Q54_2','Q54_6','Q54_3','Q142','Q58_2','Q58_3','Q58_4','Q58_1','Q60','Q146','Q62_TEXT','Q62');


--Remove Stars from label. stars will be displayed using the optional column value.

update surveylabel 
	set label='Does the student use speech to meet expressive communication needs?'
	where labelnumber='Q36';

update surveylabel 
	set label='Choose the highest statement that describes the student''s expressive communication with speech'
	where labelnumber='Q37';

update surveylabel 
	set label='Does the student use sign language in addition to or in place of speech to meet expressive communication needs?'
	where labelnumber='Q39';

update surveylabel 
	set label='Choose the highest statement that describes the student''s expressive communication with sign language'
	where labelnumber='Q40';

update surveylabel 
	set label='Does the student use augmentative or alternative communication in addition to or in place of speech or sign language to meet expressive communication needs?'
	where labelnumber='Q43';

update surveylabel 
	set label='Choose the highest statement that describes the student''s expressive communication with augmentative or alternative communication'
	where labelnumber='Q44';

update surveylabel 
	set label='If the student does not use speech, sign language, or augmentative or alternative communication, which of the following statements best describes the students expressive communication? Choose the highest statement that applies'
	where labelnumber='Q47';

update surveylabel 
	set label='Reading skills: MARK EACH ONE to show how consistently the student uses each skill<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%". -A) Recognizes single symbols presented visually or tactually (e.g., letters, numerals, environmental signs such as restroom symbols, logos, trademarks, or business signs such as fast food restaurants)'
	where labelnumber='Q51_1';

update surveylabel 
	set label='Student''s approximate instructional level of reading text with comprehension (print or Braille): Mark the highest one that applies'
	where labelnumber='Q52';

update surveylabel 
	set label='Math skills: MARK EACH ONE A) Creates or matches patterns of objects or imagesto show how consistently the student uses each skill<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-A) Creates or matches patterns of objects or images'
	where labelnumber='Q54_1';

update surveylabel 
	set label='Writing skills: Indicate the highest level that describes the student''s writing skills. Choose the highest level that the student has demonstrated even once during instruction, not the highest level demonstrated consistently. Writing includes any method the student uses to write using any writing tool that includes access to all 26 letters of the alphabet. Examples of these tools include paper and pencil, traditional keyboards, alternate keyboards and eye-gaze displays of letters'
	where labelnumber='Q500';

-- Add missing Communication tab to surverysection
INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,createduser,modifieduser)
    values('Communication','COMMUNICATION','Communication Section',
    (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
    
-- Update typo in the name
update surveysection set surveysectionname='Motor Capabilities And Health' where surveysectionname='Motor Capabilities';
update surveysection set surveysectionname='Computer Instruction' where surveysectionname='Computer Access';

-- Disable unused sections/tabs/subsections
update surveysection set activeflag='false' where surveysectionname='Computer Access and Attention During Instruction-REMOVED';
update surveysection set activeflag='false' where surveysectionname in('Attention, Understanding Instruction, Health','Student ID','State','Augmentative or Alternate Communication','Augmentative or Alternative Communication');
update surveysection set activeflag='false' where parentsurveysectionid=(select id from surveysection where surveysectionname='Attention, Understanding Instruction, Health');
update surveysection set activeflag='false' where surveysectionname='Mobility';
update surveysection set activeflag='false' where surveysectionname='Braille & Visual Aid';

-- Reorganize communication subsections under newly added Communication section.
update surveysection set parentsurveysectionid=(select id from surveysection where surveysectionname='Communication'
) where surveysectionname in('Expressive Communication', 'Receptive Communication');

-- Order the tabs.
update surveysection set sectionorder=1 where surveysectionname='Special Education';
update surveysection set sectionorder=2 where surveysectionname='Sensory Capabilities';
update surveysection set sectionorder=3 where surveysectionname='Motor Capabilities And Health';
update surveysection set sectionorder=4 where surveysectionname='Computer Instruction';
update surveysection set sectionorder=5 where surveysectionname='Communication';
update surveysection set sectionorder=6 where surveysectionname='Language';
update surveysection set sectionorder=7 where surveysectionname='Academic';

-- Order sub sections.
update surveysection set sectionorder=20 where surveysectionname='Special Education Services';
update surveysection set sectionorder=21 where surveysectionname='Hearing';
update surveysection set sectionorder=22 where surveysectionname='Vision';
update surveysection set sectionorder=23 where surveysectionname='Arm/Hand Control and Health';
update surveysection set sectionorder=24 where surveysectionname='Computer Access and Attention During Instruction';
update surveysection set sectionorder=25 where surveysectionname='Expressive Communication';
update surveysection set sectionorder=26 where surveysectionname='Receptive Communication';
update surveysection set sectionorder=27 where surveysectionname='Primary Language';
update surveysection set sectionorder=28 where surveysectionname='Reading Skills';
update surveysection set sectionorder=29 where surveysectionname='Math Skills';
update surveysection set sectionorder=30 where surveysectionname='Writing Skills';

update surveysection set surveysectioncode = 'COMPUTER_INSTRUCTION' where surveysectioncode='COMPUTER_ACCESS';

-- Need to have these as the tabs are looking awkward with dynamic content. Will fix later.
update surveysection set surveysectiondescription= 'Special<br/>Education' where surveysectioncode = 'SPECIAL_EDUCATION';
update surveysection set surveysectiondescription= 'Sensory<br/>Capabilities' where surveysectioncode = 'SENSORY_CAPABILITIES';
update surveysection set surveysectiondescription= 'Motor Capabilities<br/>And Health' where surveysectioncode = 'MOTOR_CAPABILITIES';
update surveysection set surveysectiondescription= 'Computer<br/>Instruction' where surveysectioncode = 'COMPUTER_INSTRUCTION';
update surveysection set surveysectiondescription= '<br/>Communication<br/>' where surveysectioncode = 'COMMUNICATION';
update surveysection set surveysectiondescription= '<br/>Language<br/>' where surveysectioncode = 'LANGUAGE';
update surveysection set surveysectiondescription= '<br/>Academic<br/>' where surveysectioncode = 'ACADEMIC';

-- disable questions which are not in use.
update surveylabel set activeflag='false' where labelnumber='Q13_1' ;
update surveylabel set activeflag='false' where labelnumber='Q139' ;
update surveylabel set activeflag='false' where labelnumber='Q3' ;
update surveylabel set activeflag='false' where labelnumber='Q154_TEXT' ;
update surveylabel set activeflag='false' where labelnumber='Q33_10';

-- Category Type for First Contact Survey Questions types.
insert into categorytype (typename, typecode,typedescription, createduser, activeflag, modifieduser)
	values ('Firct Contact Question Types', 'FIRST_CONTACT_QUESTION_TYPES', 'Firct Contact Question Types',
	(Select id from aartuser where username='cetesysadmin'), true, 
	(Select id from aartuser where username='cetesysadmin'));

-- First Contact Survey Question Types.
INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
	VALUES ('Radio Button', 'radiobutton',(select id from categorytype where typecode= 'FIRST_CONTACT_QUESTION_TYPES'),
	'First Contact Survey Question (Radio Button type)');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
	VALUES ('Checkbox', 'checkbox',(select id from categorytype where typecode= 'FIRST_CONTACT_QUESTION_TYPES'),
	'First Contact Survey Question (Checkbox type)');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
	VALUES ('Dropdown', 'dropdown',(select id from categorytype where typecode= 'FIRST_CONTACT_QUESTION_TYPES'),
	'First Contact Survey Question (Dropdown type)');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
	VALUES ('Two Dimentional', 'twodimentional',(select id from categorytype where typecode= 'FIRST_CONTACT_QUESTION_TYPES'),
	'First Contact Survey Question (Two Dimentional type)');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
	VALUES ('Text', 'text',(select id from categorytype where typecode= 'FIRST_CONTACT_QUESTION_TYPES'),
	'First Contact Survey Question (Text type)');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
	VALUES ('Radio Button with Text', 'radiobuttonandtext',(select id from categorytype where typecode= 'FIRST_CONTACT_QUESTION_TYPES'),
	'First Contact Survey Question (Radio Button with Text type)');

INSERT INTO category(categoryname, categorycode, categorytypeid ,categorydescription)
	VALUES ('Rank Dropdown', 'rankdropdown',(select id from categorytype where typecode= 'FIRST_CONTACT_QUESTION_TYPES'),
	'First Contact Survey Question (Rank Dropdown type)');

-- Update label type for radio button questions.
update surveylabel set labeltype= (select id from category where categorytypeid= (select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='radiobutton') where labelnumber in ('Q143', 'Q147', 'Q17', 'Q19', 'Q133', 'Q135','Q142', 'Q146','Q153', 'Q200', 'Q201', 'Q202',
 'Q203', 'Q210', 'Q22', 'Q24old', 'Q310', 'Q330', 'Q36', 'Q37', 'Q39', 'Q40' , 'Q41', 'Q43','Q44', 'Q47','Q500', 'Q501', 'Q502', 'Q503', 'Q52', 'Q60',
 'Q64');

update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q20\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q211\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q23\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q24\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q25\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox')
	where labelnumber like 'Q29\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q300\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q33\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q429\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q132\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q31\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q429\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='checkbox') 
	where labelnumber like 'Q45\_%';

update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='text') 
	where labelnumber like 'Q154\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='text') 
	where labelnumber like 'Q63\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='text') 
	where labelnumber like 'Q65\_%';

update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='dropdown') 
	where labelnumber like  'Q16\_%';

update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='rankdropdown') 
	where labelnumber like 'Q58\_%';

update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='twodimentional') 
	where labelnumber like 'Q34\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='twodimentional') 
	where labelnumber like 'Q49\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='twodimentional') 
	where labelnumber like 'Q51\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='twodimentional') 
	where labelnumber like 'Q54\_%';
update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='twodimentional') 
	where labelnumber like 'Q56\_%';

update surveylabel set labeltype= (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='radiobuttonandtext') 
	where labelnumber like 'Q62\_%';

update surveylabel set complexityband=true where labelnumber 
	in ('Q51_1','Q52','Q54_3','Q54_5','Q54_7','Q54_8','Q36','Q37','Q39','Q40','Q43','Q44');

update surveylabel set complexityband=false where labelnumber 
	not in ('Q51_1','Q52','Q54_3','Q54_5','Q54_7','Q54_8','Q36','Q37','Q39','Q40','Q43','Q44') and activeflag=true;


-- Updates for Science Skills questions.
update surveysection set sectionorder= 31 where surveysectionname = 'Science Skills' and surveysectioncode = 'SCIENCE';

update surveylabel set labeltype = (select id from category where categorytypeid= 
	(select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='twodimentional')
where labelnumber like  'Q212\_%';

-- Removing _ to ease implementation
update surveylabel  set labelnumber = 'Q16' where labelnumber = 'Q16_1';

-- Remove _ character from two dimentional questions. It is becoming too difficult to implement. Only Checkbox questions should have an '_'
update surveylabel set labelnumber = replace(labelnumber, '_','') where labelnumber like '%\_%' and activeflag is true 
and labeltype = (select id from category where categorytypeid= 
    (select id from categorytype where typecode ='FIRST_CONTACT_QUESTION_TYPES')  and categorycode='twodimentional');

-- US18081
-- Update all DLM states with default value (i.e. false).
update firstcontactsurveysettings set scienceflag=false;


--US18044
-- Update existing FCS with core set option.
update survey set lasteditedoption = (
	select id from category where categorycode = 'CORE_SET_QUESTIONS' and categorytypeid = (
		select id from categorytype where typecode= 'FIRST CONTACT SETTINGS'));

--set active flag true for languagetab and science skills page
update surveysection set activeflag='true' where surveysectionname='Language';
update surveysection set activeflag='true' where surveysectionname='Primary Language';
update surveysection set activeflag='true' where surveysectionname='Science Skills';
update surveylabel set activeflag='true' where labelnumber in('Q501','Q502','Q503');
update surveylabel set activeflag='true' where labelnumber in('Q212_1','Q212_2','Q212_3','Q212_4','Q212_5','Q212_6','Q212_7','Q212_8');
update surveyresponse set activeflag='true' where labelid in (select id from surveylabel where labelnumber in ('Q501','Q502','Q503'));
update surveyresponse set activeflag='true' where labelid in (select id from surveylabel where labelnumber in('Q212_1','Q212_2','Q212_3','Q212_4','Q212_5','Q212_6','Q212_7','Q212_8'));

--set optional to true for language tab questions
update surveylabel set optional='true' where globalpagenum=11;

-- Apply fix for Language page and Writing skills position.
update surveylabel set globalpagenum=11 where labelnumber in ('Q501','Q502','Q503');
update surveylabel set globalpagenum=15 where labelnumber in ('Q500');

-- DE13403
update surveysection set instructionnote = 'Indicate the student''s use of English as a primary language. "Primary" means it is used more than 50% of the time.'
where surveysectioncode = 'PRIMARY LANGUAGE';

-- DE13396
insert into firstcontactsurveysettings (categoryid, organizationid,createddate,  activeflag, modifieddate, createduser, modifieduser, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'BIE-Choctaw' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), false);

insert into firstcontactsurveysettings (categoryid, organizationid,createddate, activeflag, modifieddate, createduser, modifieduser, scienceflag) 
values ((select id from category where categorycode = 'CORE_SET_QUESTIONS'),
(select id from organization where displayidentifier = 'BIE-Miccosukee' and organizationtypeid = (select id from organizationtype where typename='State')),
now(),TRUE,now(),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), false);

-- DE13393
update surveylabel set optional=true where globalpagenum=11;

-- sql from script bees team
INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q429_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q429_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q429_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q429_5'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q25_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q25_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q25_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q25_5'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

update surveylabelprerequisite set activeflag = false where surveylabelid = (select id from surveylabel where labelnumber = 'Q203');



-- Science skills questions 

insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,
parentsurveySectionid,
createduser,modifieduser)
values
(
'Science Skills','SCIENCE','Science Subsection',
(Select id from surveysection where surveysectioncode='ACADEMIC'),
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);

INSERT INTO surveylabel(
            sectionid, labelnumber, surveyorder, label, createddate, 
            createduser, activeflag, modifieddate, modifieduser, optional, 
            globalpagenum, surveysectionid)
    VALUES ((select id from category where categorycode='ACADEMIC'),'Q212_1', 404, 'Science skills: MARK EACH ONE to show how consistently the student uses each skill.<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-A) Sorts objects or materials by common properties (e.g., color, size, shape)', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), false, 16, 
            (select id from surveysection where surveysectionname = 'Science Skills'));

INSERT INTO surveylabel(
            sectionid, labelnumber, surveyorder, label, createddate, 
            createduser, activeflag, modifieddate, modifieduser, optional, 
            globalpagenum, surveysectionid)
    VALUES ((select id from category where categorycode='ACADEMIC'),'Q212_2', 405, 'Science skills: MARK EACH ONE to show how consistently the student uses each skill.<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-B) Identifies similarities and differences', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), false, 16, 
            (select id from surveysection where surveysectionname = 'Science Skills'));

INSERT INTO surveylabel(
            sectionid, labelnumber, surveyorder, label, createddate, 
            createduser, activeflag, modifieddate, modifieduser, optional, 
            globalpagenum, surveysectionid)
    VALUES ((select id from category where categorycode='ACADEMIC'),'Q212_3', 406, 'Science skills: MARK EACH ONE to show how consistently the student uses each skill.<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-C) Recognizes patterns', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), false, 16, 
            (select id from surveysection where surveysectionname = 'Science Skills'));

INSERT INTO surveylabel(
            sectionid, labelnumber, surveyorder, label, createddate, 
            createduser, activeflag, modifieddate, modifieduser, optional, 
            globalpagenum, surveysectionid)
    VALUES ((select id from category where categorycode='ACADEMIC'),'Q212_4', 407, 'Science skills: MARK EACH ONE to show how consistently the student uses each skill.<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-D) Compares initial and final conditions to determine if something changed.', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), false, 16, 
            (select id from surveysection where surveysectionname = 'Science Skills'));

INSERT INTO surveylabel(
            sectionid, labelnumber, surveyorder, label, createddate, 
            createduser, activeflag, modifieddate, modifieduser, optional, 
            globalpagenum, surveysectionid)
    VALUES ((select id from category where categorycode='ACADEMIC'),'Q212_5', 408, 'Science skills: MARK EACH ONE to show how consistently the student uses each skill.<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-E) Uses data to answer questions.', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), false, 16, 
            (select id from surveysection where surveysectionname = 'Science Skills'));

INSERT INTO surveylabel(
            sectionid, labelnumber, surveyorder, label, createddate, 
            createduser, activeflag, modifieddate, modifieduser, optional, 
            globalpagenum, surveysectionid)
    VALUES ((select id from category where categorycode='ACADEMIC'),'Q212_6', 409, 'Science skills: MARK EACH ONE to show how consistently the student uses each skill.<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-F) Identifies evidence that supports a claim.', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), false, 16, 
            (select id from surveysection where surveysectionname = 'Science Skills'));

INSERT INTO surveylabel(
            sectionid, labelnumber, surveyorder, label, createddate, 
            createduser, activeflag, modifieddate, modifieduser, optional, 
            globalpagenum, surveysectionid)
    VALUES ((select id from category where categorycode='ACADEMIC'),'Q212_7', 410, 'Science skills: MARK EACH ONE to show how consistently the student uses each skill.<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-G) Identifies cause and effect relationships.', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), false, 16, 
            (select id from surveysection where surveysectionname = 'Science Skills'));

INSERT INTO surveylabel(
            sectionid, labelnumber, surveyorder, label, createddate, 
            createduser, activeflag, modifieddate, modifieduser, optional, 
            globalpagenum, surveysectionid)
    VALUES ((select id from category where categorycode='ACADEMIC'),'Q212_8', 411, 'Science skills: MARK EACH ONE to show how consistently the student uses each skill.<br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-H) Uses diagrams to explain phenomena.', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), false, 16, 
            (select id from surveysection where surveysectionname = 'Science Skills'));


INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_1'), 1, '0% - 20% of the time - Almost never', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_1'), 2, '21% - 50% of the time - Occasionally', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_1'), 3, '51% - 80% of the time - Frequently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_1'), 4, 'More than 80% of the time - Consistently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);


INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_2'), 1, '0% - 20% of the time - Almost never', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_2'), 2, '21% - 50% of the time - Occasionally', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_2'), 3, '51% - 80% of the time - Frequently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_2'), 4, 'More than 80% of the time - Consistently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);


INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_3'), 1, '0% - 20% of the time - Almost never', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_3'), 2, '21% - 50% of the time - Occasionally', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_3'), 3, '51% - 80% of the time - Frequently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_3'), 4, 'More than 80% of the time - Consistently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);


INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_4'), 1, '0% - 20% of the time - Almost never', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_4'), 2, '21% - 50% of the time - Occasionally', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_4'), 3, '51% - 80% of the time - Frequently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_4'), 4, 'More than 80% of the time - Consistently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);


INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_5'), 1, '0% - 20% of the time - Almost never', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_5'), 2, '21% - 50% of the time - Occasionally', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_5'), 3, '51% - 80% of the time - Frequently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_5'), 4, 'More than 80% of the time - Consistently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);


INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_6'), 1, '0% - 20% of the time - Almost never', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_6'), 2, '21% - 50% of the time - Occasionally', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_6'), 3, '51% - 80% of the time - Frequently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_6'), 4, 'More than 80% of the time - Consistently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);


INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_7'), 1, '0% - 20% of the time - Almost never', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_7'), 2, '21% - 50% of the time - Occasionally', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_7'), 3, '51% - 80% of the time - Frequently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_7'), 4, 'More than 80% of the time - Consistently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);


INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_8'), 1, '0% - 20% of the time - Almost never', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_8'), 2, '21% - 50% of the time - Occasionally', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_8'), 3, '51% - 80% of the time - Frequently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse(
            labelid, responseorder, responsevalue, createddate, createduser, 
            activeflag, modifieddate, modifieduser, responselabel)
    VALUES ((select id from surveylabel where labelnumber='Q212_8'), 4, 'More than 80% of the time - Consistently', now(), (Select id from aartuser where username = 'cetesysadmin'), 
            true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);
          
-- Create new survey section for Language tab.
INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,createduser,modifieduser, activeflag)
	values('Language','LANGUAGE','Language Section',
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), true);

-- Create subsection Primary Language
insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,parentsurveySectionid,createddate,
	createduser,activeflag,modifieddate,modifieduser)
	values ('Primary Language','PRIMARY LANGUAGE','Primary Language Subsection',
		(Select id from surveysection where surveysectioncode='LANGUAGE'),now(),
		(Select id from aartuser where username='cetesysadmin'),true,now(),
		(Select id from aartuser where username='cetesysadmin'));

-- Add new questions for the Language section
INSERT INTO surveylabel (labelnumber,surveyorder,label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	 VALUES ('Q501',401,'Is English the student''s primary language?', 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
	 (Select id from aartuser where username = 'cetesysadmin'),false,11,(Select id from surveysection where surveysectioncode='PRIMARY LANGUAGE'));

INSERT INTO surveylabel (labelnumber,surveyorder,label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	 VALUES ('Q502',402,'Is English the primary language spoken in the student''s home?', 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
	 (Select id from aartuser where username = 'cetesysadmin'),false,11,(Select id from surveysection where surveysectioncode='PRIMARY LANGUAGE'));

INSERT INTO surveylabel (labelnumber,surveyorder,label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	 VALUES ('Q503',403,'Is English the primary language used for the student''s instruction?', 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), 
	 (Select id from aartuser where username = 'cetesysadmin'),false,11,(Select id from surveysection where surveysectioncode='PRIMARY LANGUAGE'));

-- Create responses/options for the above 3 questions.
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((Select id from surveylabel where labelnumber='Q501'), 1,'Yes', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((Select id from surveylabel where labelnumber='Q501'), 2,'No', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((Select id from surveylabel where labelnumber='Q502'), 1,'Yes', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((Select id from surveylabel where labelnumber='Q502'), 2,'No', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((Select id from surveylabel where labelnumber='Q502'), 3,'Unknown', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);	

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((Select id from surveylabel where labelnumber='Q503'), 1,'Yes', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);	

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((Select id from surveylabel where labelnumber='Q503'), 2,'No', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);	

update surveylabelprerequisite set activeflag = false where surveylabelid = (select id from surveylabel where labelnumber = 'Q203');

update surveylabel set optional=true where globalpagenum=11;

update surveysection set instructionnote = 'Indicate the student''s use of English as a primary language. "Primary" means it is used more than 50% of the time.'
where surveysectioncode = 'PRIMARY LANGUAGE';

update categorytype set typename = 'First Contact Settings' where typecode='FIRST CONTACT SETTINGS';

-- updates to Language.
update surveysection set sectionorder=6 where surveysectionname='Language';
update surveysection set sectionorder=27 where surveysectionname='Primary Language';

--set active flag true for languagetab and science skills page
update surveysection set activeflag='true' where surveysectionname='Language';
update surveysection set activeflag='true' where surveysectionname='Primary Language';
update surveysection set activeflag='true' where surveysectionname='Science Skills';
update surveylabel set activeflag='true' where labelnumber in('Q501','Q502','Q503');
update surveylabel set activeflag='true' where labelnumber in('Q212_1','Q212_2','Q212_3','Q212_4','Q212_5','Q212_6','Q212_7','Q212_8');
update surveyresponse set activeflag='true' where labelid in (select id from surveylabel where labelnumber in ('Q501','Q502','Q503'));
update surveyresponse set activeflag='true' where labelid in (select id from surveylabel where labelnumber in('Q212_1','Q212_2','Q212_3','Q212_4','Q212_5','Q212_6','Q212_7','Q212_8'));

--set optional to true for language tab questions
update surveylabel set optional='true' where globalpagenum=11;

-- Apply fix for Language page and Writing skills position.
update surveylabel set globalpagenum=11 where labelnumber in ('Q501','Q502','Q503');
update surveylabel set globalpagenum=15 where labelnumber in ('Q500');

update surveylabel set optional=true where labelnumber in ('Q501','Q502','Q503');