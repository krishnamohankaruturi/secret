
--R8 - Iter 1 - - file 1

--US12440 Name: First Contact Survey - Special Education 
	-- and all Special Education section queries
 
UPDATE surveylabel SET optional = true, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() 
	WHERE labelnumber IN ('Q132', 'Q20', 'Q23', 'Q25', 'Q27_1', 'Q27_2', 'Q27_3', 'Q27_4', 'Q29_1', 'Q29_2', 'Q29_3', 'Q29_4', 'Q31_1', 'Q31_2', 'Q31_3',
	'Q33_1', 'Q33_10', 'Q33_11', 'Q33_2', 'Q33_3', 'Q33_4', 'Q33_5', 'Q33_6', 'Q33_7', 'Q33_8', 'Q33_9',
	'Q45_1', 'Q45_10', 'Q45_11', 'Q45_12', 'Q45_2', 'Q45_3', 'Q45_4', 'Q45_5', 'Q45_6', 'Q45_7', 'Q45_8', 'Q45_9', 'Q154_TEXT', 'Q62_TEXT', 'Q63_TEXT', 'Q65_TEXT');

UPDATE surveylabel SET optional = false, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() 
	WHERE labelnumber NOT IN ('Q132', 'Q20', 'Q23', 'Q25', 'Q27_1', 'Q27_2', 'Q27_3', 'Q27_4', 'Q29_1', 'Q29_2', 'Q29_3', 'Q29_4', 'Q31_1', 'Q31_2', 'Q31_3',
	'Q33_1', 'Q33_10', 'Q33_11', 'Q33_2', 'Q33_3', 'Q33_4', 'Q33_5', 'Q33_6', 'Q33_7', 'Q33_8', 'Q33_9',
	'Q45_1', 'Q45_10', 'Q45_11', 'Q45_12', 'Q45_2', 'Q45_3', 'Q45_4', 'Q45_5', 'Q45_6', 'Q45_7', 'Q45_8', 'Q45_9', 'Q154_TEXT', 'Q62_TEXT', 'Q63_TEXT', 'Q65_TEXT');


UPDATE surveylabel SET globalpagenum=9, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q36','Q37') AND globalpagenum=1;

UPDATE surveylabel SET globalpagenum=10, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q40','Q41','Q39') AND globalpagenum=2;

UPDATE surveylabel SET globalpagenum=11, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q43','Q44') AND globalpagenum=3;

UPDATE surveylabel SET globalpagenum=12, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q45_4','Q45_6','Q45_7','Q45_12','Q45_3','Q45_10','Q45_11','Q45_1','Q45_5','Q45_8','Q45_2','Q45_9')  AND globalpagenum=4;

UPDATE surveylabel SET globalpagenum=13, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q47','Q49_2','Q49_5','Q49_3','Q49_4','Q49_1','Q49_6') AND globalpagenum=5;

UPDATE surveylabel SET globalpagenum=14, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q51_2','Q51_3','Q51_5','Q51_6','Q51_7','Q51_8','Q51_1','Q51_4') AND globalpagenum=6;

UPDATE surveylabel SET globalpagenum=15, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q52') AND globalpagenum=7;

UPDATE surveylabel SET globalpagenum=16, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q54_11','Q54_8','Q54_12','Q54_1','Q54_2','Q54_4','Q54_5','Q54_10','Q54_13','Q54_6','Q54_9','Q54_7','Q54_3') AND globalpagenum=8;

UPDATE surveylabel SET globalpagenum=17, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q56_4','Q56_8','Q56_6','Q56_2','Q56_5','Q56_1','Q56_3','Q56_7') AND globalpagenum=9;

UPDATE surveylabel SET globalpagenum=18, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q58_4','Q58_1','Q58_3','Q58_2','Q142','Q146') AND globalpagenum=10;

UPDATE surveylabel SET globalpagenum=19, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q62','Q62_TEXT','Q60') AND globalpagenum=11;

UPDATE surveylabel SET globalpagenum=20, 
	modifieduser=(select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE labelnumber in ('Q65_TEXT','Q64') AND globalpagenum=12;



INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,createduser,modifieduser)
	values('Special Education','SPECIAL_EDUCATION','Special Education Section',
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,parentsurveySectionid,createduser,modifieduser)
	values('Special Education Services','SPECIAL_EDUCATION_SERVICES','Special Education Services Subsection',
	(Select id from surveysection where surveysectioncode='SPECIAL_EDUCATION'),
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
--Q16_1

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q16_1',36, 'Select the student''s Primary Disability-Primary Disability', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 1,(Select id from surveysection where surveysectioncode='SPECIAL_EDUCATION_SERVICES'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 1,'Autism', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 2,'Deaf-blindness', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 3,'Developmental delay', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 4,'Emotional disturbance', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 4);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 5,'Hearing impairment', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 5);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 6,'Intellectual disability', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 6);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 7,'Multiple disabilities', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 7);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 8,'Orthopedic impairment', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 8);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 9,'Other health impairment', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 9);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 10,'Specific learning disability', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 10);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 11,'Speech or language impairment', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 11);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 12,'Traumatic brain injury', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 12);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 13,'Visual impairment', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 13);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q16_1'), 14,'Noncategorical', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 14);


--Q17

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q17',37, 'Classroom setting: Choose the option that best describes the student''s class placement', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 1,(Select id from surveysection where surveysectioncode='SPECIAL_EDUCATION_SERVICES'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q17'), 1,'Regular Class: includes students who receive the majority of their education program in a regular classroom and receive special education and related services outside the regular classroom for less than 21 percent of the school day', 
 	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q17'), 2,'Resource Room: includes students who receive special education and related services outside of the regular classroom for at least 21 percent but no more than 60 percent of the school day', 
 	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q17'), 3,'Separate Class: includes students who receive special education and related services outside the regular class for more than 60 percent of the school day', 
 	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q17'), 4,'Separate School: includes students who receive special education and related services in a public or private separate day school for students with disabilities, at public expense, for more than 50 percent of the school day', 
 	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 4);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q17'), 5,'Residential Facility: includes students who receive special education in a public or private residential facility, at public expense, for more than 50 percent of the school day', 
 	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 5);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q17'), 6,'Homebound/hospital Environment: includes students placed in and receiving special education in a hospital or homebound program', 
 	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 6);


	 
--US12596 Name: First Contact Survey - Motor Capabilities- Walking 
	 -- and all Motor section queries
	 

INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,createduser,modifieduser)
	values('Motor Capabilities','MOTOR_CAPABILITIES','Motor Capabilities Section',
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,parentsurveySectionid,createduser,modifieduser)
	values('Mobility','MOBILITY','Mobility Subsection',
	(Select id from surveysection where surveysectioncode='MOTOR_CAPABILITIES'),
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,parentsurveySectionid,createduser,modifieduser)
	values('Arm & Head Control','ARM_AND_HAND_CONTROL','Arm & Head Control Subsection',
	(Select id from surveysection where surveysectioncode='MOTOR_CAPABILITIES'),
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
--Q135

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q135',68, 'Mobility: Walking', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 5,(Select id from surveysection where surveysectioncode='MOBILITY'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q135'), 1,'Walks unaided', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q135'), 2,'Walks with physical assistance', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q135'), 3,'Cannot walk', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);		


--Q27_

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q27_1',69, 'Mobility Supports: Mark all that apply-Walks with cane', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 5,(Select id from surveysection where surveysectioncode='MOBILITY'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q27_2',70, 'Mobility Supports: Mark all that apply-Uses walker for mobility', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 5,(Select id from surveysection where surveysectioncode='MOBILITY'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q27_3',71, 'Mobility Supports: Mark all that apply-Uses wheelchair for mobility without assistance', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 5,(Select id from surveysection where surveysectioncode='MOBILITY'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q27_4',72, 'Mobility Supports: Mark all that apply-Uses wheelchair for mobility with assistance', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 5,(Select id from surveysection where surveysectioncode='MOBILITY'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q27_1'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q27_2'), 2,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q27_3'), 3,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q27_4'), 4,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);


--Q153

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q153',73, 'Mobility Supports: Seating or positioning equipment', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 5,(Select id from surveysection where surveysectioncode='MOBILITY'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q153'), 1,'Requires specialized seating to maintain an upright position', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q153'), 2,'Requires specialized positioning equipment (e.g., standing frame)', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q153'), 3,'Does NOT require specialized seating or positioning equipment', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);	
 	


--Q29_

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q29_1',74, 'Arm and hand control: Mark all that apply-Uses two hands together to perform tasks', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 6,(Select id from surveysection where surveysectioncode='ARM_AND_HAND_CONTROL'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q29_2',75, 'Arm and hand control: Mark all that apply-Uses only one hand to perform tasks', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 6,(Select id from surveysection where surveysectioncode='ARM_AND_HAND_CONTROL'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q29_3',76, 'Arm and hand control: Mark all that apply-Requires physical assistance to perform tasks with hands', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 6,(Select id from surveysection where surveysectioncode='ARM_AND_HAND_CONTROL'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q29_4',77, 'Arm and hand control: Mark all that apply-Cannot use hands to complete tasks', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 6,(Select id from surveysection where surveysectioncode='ARM_AND_HAND_CONTROL'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q29_1'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q29_2'), 2,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q29_3'), 3,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q29_4'), 4,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);



--Q31_

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q31_1',78, 'Head control: Mark all that apply-Supports and turns head without assistance', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 6,(Select id from surveysection where surveysectioncode='ARM_AND_HAND_CONTROL'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q31_2',79, 'Head control: Mark all that apply-Has restricted range of head motion', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 6,(Select id from surveysection where surveysectioncode='ARM_AND_HAND_CONTROL'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q31_3',80, 'Head control: Mark all that apply-Requires head support or head rest throughout the day', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 6,(Select id from surveysection where surveysectioncode='ARM_AND_HAND_CONTROL'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q31_1'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q31_2'), 2,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q31_3'), 3,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);



--Skip Logic

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q27_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q27_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q27_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q27_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q27_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q27_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q27_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q27_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q135' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));
		
	
	
--US12438 Name: First Contact Survey - Computer Access - Computer Use Sub Section 
	-- and all Computer section queries.


INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,createduser,modifieduser)
	values('Computer Access','COMPUTER_ACCESS','Computer Access Section',
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,parentsurveySectionid,createduser,modifieduser)
	values('Computer Use','COMPUTER_USE','Computer Use Subsection',
	(Select id from surveysection where surveysectioncode='COMPUTER_ACCESS'),
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveysection(surveysectionname,surveysectioncode,surveysectiondescription,parentsurveySectionid,createduser,modifieduser)
	values('Access & Switches','ACCESS_AND_SWITCHES','Access & Switches Subsection',
	(Select id from surveysection where surveysectioncode='COMPUTER_ACCESS'),
	(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
	
--Q143

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q143',82, 'Computer Use: Select the student''s primary use of a computer', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 7,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q143'), 1,'Accesses a computer independently', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q143'), 2,'Uses a computer with support (human or assistive technology)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q143'), 3,'This student has not had the opportunity to access a computer', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 3);


--Q147

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q147',83, 'Why has this student not had the opportunity to access a computer?', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 7,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q147'), 1,'Student''s disability prevents the student from accessing a computer', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q147'), 2,'The equipment is unavailable at the school level', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q147'), 3,'Student refuses to try to use a computer', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 3);
	 
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q147'), 4,'I (or other educators) at this school have not had the opportunity to instruct the student on computer usage', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 4);



--Q33_


INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_1',84, 'Computer access: Mark all that apply-Standard computer keyboard using fingers', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_2',85, 'Computer access: Mark all that apply-Standard computer keyboard using pointer', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_3',86, 'Computer access: Mark all that apply-Keyboard with large keys', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_4',87, 'Computer access: Mark all that apply-Alternative keyboard (e.g., Intellikeys)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_5',88, 'Computer access: Mark all that apply-Touch screen (e.g., touch screen computer, tablet, iPad, iPod touch)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_6',89, 'Computer access: Mark all that apply-Standard mouse', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_7',90, 'Computer access: Mark all that apply-Head mouse', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_8',91, 'Computer access: Mark all that apply-Eye gaze technology', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_9',92, 'Computer access: Mark all that apply-Sip and puff technology', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_10',93, 'Computer access: Mark all that apply-Voice recognition software', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q33_11',94, 'Computer access: Mark all that apply-Switches', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

							
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_1'), 1,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_2'), 2,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_3'), 3,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_4'), 4,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 4);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_5'), 5,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 5);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_6'), 6,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 6);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_7'), 7,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 7);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_8'), 8,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 8);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_9'), 9,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 9);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_10'), 10,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 10);	 	 	 	 	 	 	 	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q33_11'), 11,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 11);	 	 	 	 	 	 	 	 


--Q34_

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q34_1',95, 'If the student uses switches to access a computer, please indicate how many switches and what body part the student uses to access the switches -Head', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q34_2',96, 'If the student uses switches to access a computer, please indicate how many switches and what body part the student uses to access the switches -Hand or arm', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q34_3',97, 'If the student uses switches to access a computer, please indicate how many switches and what body part the student uses to access the switches -Knee, foot or leg', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 8,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));


INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_1'), 1,'1 switch', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_1'), 2,'2 switches', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 2);	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_1'), 3,'3 or more switches', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 3);	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_2'), 1,'1 switch', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_2'), 2,'2 switches', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 2);	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_2'), 3,'3 or more switches', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 3);	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_3'), 1,'1 switch', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_3'), 2,'2 switches', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 2);	 

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q34_3'), 3,'3 or more switches', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 3);	


--Skip Logic

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q147'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=3),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q34_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q33_11' AND sr.responseorder=11),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q34_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q33_11' AND sr.responseorder=11),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser)
	VALUES ((Select id from surveylabel where labelnumber='Q34_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q33_11' AND sr.responseorder=11),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'));
	
	
	
