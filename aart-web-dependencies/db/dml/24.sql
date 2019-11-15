insert into surveysection(surveysectionname,surveysectioncode,
surveysectiondescription,createduser,modifieduser)
(
Select categoryname,categorycode,categorydescription,createduser,
modifieduser from category where categorytypeid=(Select id from categorytype where typecode='SURVEY_SECTION')
);


insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,
parentsurveySectionid,
createduser,modifieduser)
values
(
'Understanding Instruction','UNDERSTANDING_INSTRUCTION','Understanding Instruction Subsection',
(Select id from surveysection where surveysectioncode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'),
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);

insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,
parentsurveySectionid,
createduser,modifieduser)
values
(
'Health','HEALTH','Health Subsection',
(Select id from surveysection where surveysectioncode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'),
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);

insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,
parentsurveySectionid,
createduser,modifieduser)
values
(
'Attention','ATTENTION','Attention Subsection',
(Select id from surveysection where surveysectioncode='ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH'),
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);

insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,
parentsurveySectionid,
createduser,modifieduser)
values
(
'Expressive Communication','EXPRESSIVE_COMMUNICATIONS','Expressive Communications Subsection',
(Select id from surveysection where surveysectioncode='COMMUNICATION'),
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);


insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,
parentsurveySectionid,
createduser,modifieduser)
values
(
'Alternate Communication','AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS','Expressive Communications Subsection',
(Select id from surveysection where surveysectioncode='COMMUNICATION'),
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);

insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,
parentsurveySectionid,
createduser,modifieduser)
values
(
'Receptive Communication','RECEPTIVE_COMMUNICATION','Receptive Communication Subsection',
(Select id from surveysection where surveysectioncode='COMMUNICATION'),
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);

insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,
parentsurveySectionid,
createduser,modifieduser)
values
(
'Reading Skills','READING','Reading Subsection',
(Select id from surveysection where surveysectioncode='ACADEMIC'),
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);

insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,
parentsurveySectionid,
createduser,modifieduser)
values
(
'Math Skills','MATH','Math Subsection',
(Select id from surveysection where surveysectioncode='ACADEMIC'),
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);

insert into surveysection(surveysectionname,surveysectioncode,surveysectiondescription,
parentsurveySectionid,
createduser,modifieduser)
values
(
'Writing Skills','WRITING','Writing Subsection',
(Select id from surveysection where surveysectioncode='ACADEMIC'),
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);


--UPDATE SECTION IDS FOR THE SURVEY

 update surveylabel
 set 
 surveysectionid=(Select id from surveySection where surveySectionCode='UNDERSTANDING_INSTRUCTION'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q60','Q62','Q62_TEXT');

 update surveylabel
 set 
 surveysectionid=(Select id from surveySection where surveySectionCode='HEALTH'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q64','Q65_TEXT');
 
 update surveylabel
 set 
 surveysectionid=(Select id from surveySection where surveySectionCode='ATTENTION'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q142','Q146','Q58_1','Q58_2','Q58_3','Q58_4');
 
 
 update surveylabel
 set 
 surveysectionid=(Select id from surveySection where surveySectionCode='EXPRESSIVE_COMMUNICATIONS'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q36','Q37','Q39','Q40','Q44','Q41');
 
 update surveylabel
 set 
 surveysectionid=(Select id from surveySection
 where surveySectionCode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q43','Q44');
 
 update surveylabel
 set 
 surveysectionid=(Select id from surveySection
 where surveySectionCode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q45_1','Q45_2','Q45_3','Q45_4','Q45_5','Q45_6','Q45_7','Q45_8','Q45_9','Q45_10','Q45_11','Q45_12');
 
 update surveylabel
 set 
 surveysectionid=(Select id from surveySection
 where surveySectionCode='RECEPTIVE_COMMUNICATION'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q47','Q49_1','Q49_2','Q49_3','Q49_4','Q49_5','Q49_6'); 
 
 update surveylabel
 set 
 surveysectionid=(Select id from surveySection
 where surveySectionCode='READING'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q52','Q51_1','Q51_2','Q51_3','Q51_4','Q51_5','Q51_6','Q51_7','Q51_8');
 
 update surveylabel
 set 
 surveysectionid=(Select id from surveySection
 where surveySectionCode='MATH'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q54_1','Q54_2','Q54_3','Q54_4','Q54_5','Q54_6','Q54_7',
 'Q54_8','Q54_9','Q54_10','Q54_11','Q54_12','Q54_13');


 update surveylabel
 set 
 surveysectionid=(Select id from surveySection
 where surveySectionCode='WRITING'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q56_1','Q56_2','Q56_3','Q56_4','Q56_5','Q56_6','Q56_7','Q56_8');
 
 CREATE TABLE surveyLabelPreRequisite
(
  surveyLabelId bigint NOT NULL,
  surveyResponseId bigint NOT NULL,
  createdDate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  createdUser integer,
  activeFlag boolean DEFAULT true,
  modifiedDate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifiedUser integer,
  CONSTRAINT survey_label_pre_requisite_pk PRIMARY KEY (surveyLabelId , surveyResponseId ),
  CONSTRAINT survey_label_pre_requisite_survey_Label_Id_fkey FOREIGN KEY (surveyLabelId)
      REFERENCES surveylabel (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT survey_label_pre_requisite_surveyResponseId_fkey FOREIGN KEY (surveyResponseId)
      REFERENCES surveyresponse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


update surveylabel set globalpagenum=1, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q36','Q37');

update surveylabel set globalpagenum=2, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q40','Q41','Q39');

update surveylabel set globalpagenum=3, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q43','Q44');

update surveylabel set globalpagenum=4, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q45_4','Q45_6','Q45_7','Q45_12','Q45_3','Q45_10','Q45_11','Q45_1','Q45_5','Q45_8','Q45_2','Q45_9');

update surveylabel set globalpagenum=5, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q47','Q49_2','Q49_5','Q49_3','Q49_4','Q49_1','Q49_6');

update surveylabel set globalpagenum=6, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q51_2','Q51_3','Q51_5','Q51_6','Q51_7','Q51_8','Q51_1','Q51_4');

update surveylabel set globalpagenum=7, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q52');

update surveylabel set globalpagenum=8, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q54_11','Q54_8','Q54_12','Q54_1','Q54_2','Q54_4','Q54_5','Q54_10','Q54_13','Q54_6','Q54_9','Q54_7','Q54_3');

update surveylabel set globalpagenum=9, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q56_4','Q56_8','Q56_6','Q56_2','Q56_5','Q56_1','Q56_3','Q56_7');

update surveylabel set globalpagenum=10, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q58_4','Q58_1','Q58_3','Q58_2','Q142','Q146');

update surveylabel set globalpagenum=11, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q62','Q62_TEXT','Q60');

update surveylabel set globalpagenum=12, 
	modifieduser=(select id from aartuser where username='cetesysadmin') , 
	modifieddate=now() where labelnumber in ('Q65_TEXT','Q64');

-- US12368,US12370,US12371:	First Contact Survey - Communications - Skip Logic

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q37'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q36'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q40'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q39'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q41'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q39'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q44'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_1'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_2'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_3'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_4'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_5'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_6'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_7'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_8'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_9'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_10'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_11'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q45_12'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='Yes'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 

INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q47'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q36'
AND sr.responsevalue='No'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 



INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q47'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q39'
AND sr.responsevalue='No'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 



INSERT INTO surveylabelprerequisite(
            surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ((Select id from surveylabel where labelnumber='Q47'),
    (Select sr.id from 
surveyresponse sr
JOIN surveylabel sl ON sr.labelid=sl.id
where sl.labelnumber='Q43'
AND sr.responsevalue='No'),
    now(),
(Select id from aartuser where username='cetesysadmin'),true,now(),
(Select id from aartuser where username='cetesysadmin')); 



--insert for US12404

update SurveyLabel
 set label = 'Please type the students state issued unique identifier used for assessment* in the text box below *If you do not have this identifier, please check with your administrator or testing coordinator to obtain it before starting the survey.'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q3';

update SurveyLabel
 set label = 'If the student uses switches to access a computer, please indicate how many switches and what body part the student uses to access the switches -Head'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q34_1';

Select * from surveylabel where labelnumber ilike '%Q39%';

update SurveyLabel
 set label = 'Does the student use sign language in addition to or in place of speech to meet expressive communication needs?'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q39';

update SurveyLabel
 set label = 'Choose the highest statement that describes the students expressive communication with sign language'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q40';

update SurveyLabel
 set label = 'Does the student use augmentative or alternative communication in addition to or in place of speech or sign language to meet expressive communication needs?'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q43';

update SurveyLabel
 set label = 'Choose the highest statement that describes the students expressive communication with augmentative or alternative communication'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q44';

update SurveyLabel
 set label = 'Choose the highest statement that describes the students expressive communication with augmentative or alternative communication'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q44';

update SurveyLabel
 set label = 'If the student does not use speech, sign language, or augmentative or alternative communication, which of the following statements best describes the students expressive communication? Choose the highest statement that applies'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q47';


update SurveyLabel set label = 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -A) Recognizes single symbols presented visually or tactually (e.g., letters, numerals, environmental signs such as restroom symbols, logos, trademarks, or business signs such as fast food restaurants)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q51_1';
update SurveyLabel set label = 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -B) Understands purpose of print or Braille but not necessarily by manipulating a book (e.g., knows correct orientation, can find beginning of text, understands purpose of text in print or Braille, enjoys being read to)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q51_2';
update SurveyLabel set label = 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -C) Matches sounds to symbols or signs to symbols (e.g., matches sounds to letters presented visually or tactually, matches spoken or signed words to to written words)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q51_3';
update SurveyLabel set label = 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -D) Reads words, phrases, or sentences in print or Braille when symbols are provided with the words'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q51_4';
update SurveyLabel set label = 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -E) Identifies individual words without symbol support (e.g., recognizes words in print or Braille; can choose correct word using eye gaze)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q51_5';
update SurveyLabel set label = 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -F) Reads text presented in print or Braille without symbol support but WITHOUT comprehension'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q51_6';
update SurveyLabel set label = 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -G) Reads text presented in print or Braille without symbol support and WITH comprehension (e.g., locates answers in text, reads and answers questions, retells after reading, completes maze task)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q51_7';
update SurveyLabel set label = 'Reading skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -H) Explains or elaborates on text read in print or Braille'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q51_8';
update SurveyLabel set label = 'Students approximate instructional reading level in print or Braille: Mark the highest one that applies'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q52';
update SurveyLabel set label = 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -A) Makes random marks or scribbles with pencil or marker'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q56_1';
update SurveyLabel set label = 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -B) Randomly selects letters or symbols when asked to write, with or without requiring use of pencil or marker (e.g., writes single letters or numbers with crayon, randomly selects letters from alphabet or on keyboard, randomly selects symbols from communication board)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q56_2';
update SurveyLabel set label = 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -C) Copies letters and words with pencil, pen, marker, or keyboard, but cannot produce independent writing'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q56_3';
update SurveyLabel set label = 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -D) Selects symbols to express meaning when asked to write (e.g., writes letters with pencil or pen, chooses letters on keyboard, selects symbols on communication board)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q56_4';
update SurveyLabel set label = 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -E) Writes using word banks to select or copy words (e.g., copies words with pencil or pen, copies words using keyboard, selects words on communication board)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q56_5';
update SurveyLabel set label = 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -F) Uses letters to accurately reflect sounds in words when writing (e.g., writes own name using pencil or keyboard, writes letters without copying, uses keyboard or other technology to select letters without copying)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q56_6';
update SurveyLabel set label = 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -G) Uses spelling (not always correct) to write simple phrases and sentences (e.g., writes phrases and sentences independently without copying, uses keyboard or other technology to produce phrases and sentences without copying)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q56_7';
update SurveyLabel set label = 'Writing skills: MARK EACH ONE to show the approximate percent of time that the student uses each skill -H) Uses spelling (not always correct) to write paragraph- length text (e.g., produces text by writing or using keyboard or other technology without copying)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q56_8';

update SurveyLabel set label = 'Order all modes of instructional delivery by the students level of attention (i.e., move the delivery mode that elicits the highest level of attention to the top (rank of 1), move the next highest delivery mode into position 2, etc.)-x1'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q58_1';

update SurveyLabel set label = 'Order all modes of instructional delivery by the students level of attention (i.e., move the delivery mode that elicits the highest level of attention to the top (rank of 1), move the next highest delivery mode into position 2, etc.)-x2'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q58_2';

update SurveyLabel set label = 'Order all modes of instructional delivery by the students level of attention (i.e., move the delivery mode that elicits the highest level of attention to the top (rank of 1), move the next highest delivery mode into position 2, etc.)-x3'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q58_3';

update SurveyLabel set label = 'Order all modes of instructional delivery by the students level of attention (i.e., move the delivery mode that elicits the highest level of attention to the top (rank of 1), move the next highest delivery mode into position 2, etc.)-x4'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q58_4';

update SurveyLabel set label = 'General level of understanding instruction: Choose the highest one that applies-x4'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q60';
update SurveyLabel set label = 'Does this student have a behavior intervention plan? If so, please briefly name the behavioral goal(s)'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q62';
update SurveyLabel set label = 'Does this student have a behavior intervention plan? If so, please briefly name the behavioral goal(s) -TEXT'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q62_TEXT';
update SurveyLabel set label = 'Does the student have any health issues (e.g., fragile medical condition, seizures, therapy or treatment that prevents the student from accessing instruction, impact of medications, etc.) or personal care issues (e.g., feeding, hygiene) that interfere with instruction or assessment?'
,modifieddate=now(),
modifieduser=(select id from aartuser where username='cetesysadmin')
where
labelnumber='Q64';

--US12369: First Contact Survey - Overlay Navigation

insert into surveypagestatus(iscompleted,surveyid,globalpagenum,createddate,createduser,activeflag,modifieddate,modifieduser)
(
Select false as iscompleted,s1.id as surveyid,globalpagenum,now() as createddate,
(Select id from aartuser where username='cetesysadmin') as createduser,
true as activeflag,now() as modifieddate,
(Select id from aartuser where username='cetesysadmin') as modifieduser
 from
  (select explode_array(ARRAY[1,2,3,4,5,6,7,8,9,10,11,12]) as globalpagenum) ps1 ,
  (select * from survey s where not exists (select 1 from surveypagestatus ps where s.id=ps.surveyid)) s1 
);


--CB publishing changes.R7-I2
--commented because of build failure.
--INSERT INTO CATEGORY (categoryname, categorycode, categorydescription,
--categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
--VALUES ('Paginated', 'paginated', 'Paginated',
--(select id from categorytype where typecode='TESTLET_LAYOUT'), 'CB', now(),
--(select id from aartuser where username='cetesysadmin'), now(),
--(select id from aartuser where username='cetesysadmin'));

-- CB publishing changes. R7 - I3
--variant type additions to AART
INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Variant Type', 'VARIANT_TYPE', 'Variant Type', 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Simplified Language','x1 - US','Simplified Language', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Chinese','hz - US','Chinese', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('English','en - US','English', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('French','fr - US','French', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('German','de - US','German', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Hmong','hmn -  US','Hmong', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Korean','ko - US','Korean', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Russian','ru - US','Russian', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Somali','so - US','Somali', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Spanish','es - US','Spanish', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Portuguese','pt - US','Portuguese', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Vietnamese','vi - US','Vietnamese', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('General','General','General', (select id from categorytype where typecode='VARIANT_TYPE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
--accommodation type additions to AART
INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Accommodation Type', 'ACCOMODATION_TYPE', 'Accommodation Type', 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Keyword Translation','KEYWORD_TRANSLATION','Keyword Translation', (select id from categorytype where typecode='VARIANT_TYPE'), 1, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Flagging','FLAGGING','Flagging', (select id from categorytype where typecode='VARIANT_TYPE'), 2, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Chunking','CHUNKING','Chunking', (select id from categorytype where typecode='VARIANT_TYPE'), 3, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Scaffolding','SCAFFOLDING','Scaffolding', (select id from categorytype where typecode='VARIANT_TYPE'), 4, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Reduced Answers','REDUCED_ANSWERS','Reduced Answers', (select id from categorytype where typecode='VARIANT_TYPE'), 5, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Braille','BRAILLE','Braille', (select id from categorytype where typecode='VARIANT_TYPE'), 6, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Signed Modes','SIGNED_MODES','Signed Modes', (select id from categorytype where typecode='VARIANT_TYPE'), 7, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Tactile Forms','TACTILE_FORMS','Tactile Forms', (select id from categorytype where typecode='VARIANT_TYPE'), 9, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Audio Forms','AUDIO_FORMS','Audio Forms', (select id from categorytype where typecode='VARIANT_TYPE'), 10, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Video Forms','VIDEO_FORMS','Video Forms', (select id from categorytype where typecode='VARIANT_TYPE'), 11, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Graphic Forms','GRAPHIC_FORMS','Graphic Forms', (select id from categorytype where typecode='VARIANT_TYPE'), 12, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Text Forms','TEXT_FORMS','Text Forms', (select id from categorytype where typecode='VARIANT_TYPE'), 13, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Interactive Forms','INTERACTIVE_FORMS','Interactive Forms', (select id from categorytype where typecode='VARIANT_TYPE'), 14, 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


UPDATE testletstimulusvariants SET sortorder = 0;
UPDATE testsectionstaskvariants SET sortorder = 0;



