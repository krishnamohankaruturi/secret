--script bees changes US16363,US16370,US16371,US16372,US16373,US16374,US16375,US16379, US16217
--US16217
insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser) 
          values('DLM_MESSAGE_CREATOR','DLM Announcements Creator','Administrative-DLM Announcements', CURRENT_TIMESTAMP,
           (select id from aartuser where username='cetesysadmin'),TRUE, CURRENT_TIMESTAMP,
           (select id from aartuser where username='cetesysadmin'));
           
insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser) 
          values('DLM_MESSAGE_VIEWER','DLM Announcements Viewer','Administrative-DLM Announcements', CURRENT_TIMESTAMP,
           (select id from aartuser where username='cetesysadmin'),TRUE, CURRENT_TIMESTAMP,
           (select id from aartuser where username='cetesysadmin'));
                      
--US16363
update surveyresponse set responsevalue = 'Non-categorical/Eligible individual'
                ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelid= (select id from surveylabel where labelnumber='Q16_1') and responseorder =14 ;

update surveyresponse set responsevalue = 'Visual impairment, including blindness'
                ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelid= (select id from surveylabel where labelnumber='Q16_1') and responseorder =13 ;

update surveyresponse set responsevalue = 'Deafness'
                ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelid= (select id from surveylabel where labelnumber='Q16_1') and responseorder =15 ;

update surveylabel set label='Educational Placement: Choose the option that best describes the student''s educational placement. "Regular Class" means a typical classroom, not a resource room or separate class.' where labelnumber='Q17';

update surveyresponse set activeflag='false' where labelid= (select id from surveylabel where labelnumber='Q17') and responseorder =1 ;
update surveyresponse set activeflag='false' where labelid= (select id from surveylabel where labelnumber='Q17') and responseorder =3 ;
update surveyresponse set activeflag='false' where labelid= (select id from surveylabel where labelnumber='Q17') and responseorder =2 ;

update surveyresponse set responsevalue = 'Separate School: Includes public or private separate day school for students with disabilities, at public school expense'
                ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelid= (select id from surveylabel where labelnumber='Q17') and responseorder =4 ;

update surveyresponse set responsevalue = 'Residential Facility: Includes public or private separate residential school for students with disabilities, at public school expense'
                ,modifieddate=now(),modifieduser=(select id from aartuser where username='cetesysadmin') where labelid= (select id from surveylabel where labelnumber='Q17') and responseorder =5 ;

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q17'), 7,'80% or more of the day in Regular Class', 
 	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 7);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q17'), 8,'40% - 79% of the day in Regular Class', 
 	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 8);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q17'), 9,'Less than 40% of the day in Regular Class', 
 	 now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 9);

--US16370
update surveyresponse set activeflag='false' where id=386;

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q19'), 3,'No hearing loss suspected/documented', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

 	INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q19'), 4,'Questionable hearing, but testing inconclusive. (Skip to Hearing: Mark all that apply)', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);

 INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q330',330, 'Classification of Hearing Impairment', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q330'), 1,'Mild (26-40 dB loss)', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q330'), 2,'Moderate (41-55 dB loss)',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

 INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q330'), 3,'Moderately Severe (56-70 dB loss)',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

 INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q330'), 4,'Severe (71-90 dB loss)',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);

 INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q330'), 5,'Profound (91+ dB loss)',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 5);

 INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q330'), 6,'Unknown',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 6);

 INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
	VALUES ((Select id from surveylabel where labelnumber='Q330'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responseorder=2),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q20_5',106, 'Hearing: Mark all that apply-Uses unilateral hearing aid ', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q20_5'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ20_5Choice','Q20_5','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ20_5Choice' , 'Q20_5')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q20_6',107, 'Hearing: Mark all that apply-Uses bilateral hearing aid ', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q20_6'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ20_6Choice','Q20_6','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ20_6Choice' , 'Q20_6')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q20_7',108, 'Hearing: Mark all that apply-Has cochlear implant', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 2,(Select id from surveysection where surveysectioncode='HEARING'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q20_7'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ20_7Choice','Q20_7','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ20_7Choice' , 'Q20_7')));

--US16371
update surveyresponse set responsevalue='No vision loss suspected or documented' where id=396;
update surveyresponse set responseorder=4 where id=398;

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q22'), 3,'Questionable vision but testing inconclusive (Skip to Vision: Mark all that apply)', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q203',203,'Classification of Visual Impairment', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ203Choice','Q203','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ203Choice' , 'Q203')));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q203'), 1,'Low Vision (acuity of 20/70 to 20/200 in the better eye with correction)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q203'), 2,'Legally Blind (acuity of 20/200 or less or field loss to 20 degrees or less in the better eye with correction)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q203'), 3,'Light Perception Only', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q203'), 4,'Totally Blind', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q203'), 5,'Cortical Visual Impairment', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 5);

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q203'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

update surveylabel set label='Vision: Mark all that apply-Requires tactical media (objects, tactile graphics and tactile symbols)' where labelnumber='Q23_2';
update surveylabel set label='UEB' where labelnumber='Q151_3';
update surveylabel set label='Technological Visual Aids: Mark all that apply-Screen magnification device (fits over standard monitor) or software (e.g., Closeview for Mac, ZoomText)' where labelnumber='Q25_1';
update surveylabel set label='Technological Visual Aids: Mark all that apply-CCTV' where labelnumber='Q25_2';
update surveylabel set label='Technological Visual Aids: Mark all that apply-Screen reader and/or talking word processor' where labelnumber='Q25_3';
update surveylabel set label='Technological Visual Aids: Mark all that apply-Manual (e.g., Perkins Brailler) or Electronic (e.g., Mountbatten Brailler) Braille writing device' where labelnumber='Q25_4';
update surveylabel set label='Technological Visual Aids: Mark all that apply-Device with refreshable Braille display' where labelnumber='Q25_5';

update surveylabel set activeflag='false'  where labelnumber='Q25_6';
update surveylabel set activeflag='false'  where labelnumber='Q25_7';
update surveylabel set activeflag='false'  where labelnumber='Q25_8';
update surveylabel set activeflag='false'  where labelnumber='Q25_9';
update surveylabel set activeflag='false'  where labelnumber='Q25_10';
update surveylabel set label='UEB' where labelnumber='Q151_3';

--US16372
update surveylabel set label='Receptive communication: MARK EACH ONE to show the approximate percent of time that the student uses each skill. <font size = "5" color = "red" >*</font><br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%". -A) Can point to, look at, or touch things in the immediate vicinity when asked (e.g., pictures, objects, body parts)' where labelnumber='Q49_1';

update surveyresponse set activeflag='false' where id =37;
update surveyresponse set activeflag='false' where id =42;
update surveyresponse set activeflag='false' where id =47;
update surveyresponse set activeflag='false' where id =52;
update surveyresponse set activeflag='false' where id =57;
update surveyresponse set activeflag='false' where id =62;

update surveyresponse set responsevalue='0% - 20% of the time-Almost never' where id=38;
update surveyresponse set responsevalue='21% - 50% of the time-Occasionally' where id=39;
update surveyresponse set responsevalue='51%   80% of the time - Frequently' where id=40;
update surveyresponse set responsevalue='More than 80% of the time - Consistently' where id=41;	 

--US16373
UPDATE surveysection SET  surveysectionname='Arm/Hand Control and Health' WHERE id=19;

UPDATE surveylabel SET label = 'Arm and hand control: Mark all that apply-Uses only one hand to perform tasks'
    WHERE labelnumber = 'Q29_2';

UPDATE surveylabel SET label = 'Arm and hand control: Mark all that apply-Requires physical assistance to perform tasks with hands'
    WHERE labelnumber = 'Q29_3';

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q200',200,'Does the student have any health issues (e.g., fragile medical condition, seizures, therapy or treatment that prevents the student from accessing instruction, medications, etc.) that interfere with instruction or assessment?<font size = "5" color = "red" >*</font>', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 5,(Select id from surveysection where surveysectioncode='ARM_AND_HAND_CONTROL'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q200'), 1,'yes', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q200'), 2,'no', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ200Choice','Q200','{'',1,2}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ200Choice' , 'Q200')));

--US16374

UPDATE surveysection SET  surveysectionname='Computer Use and Instruction' WHERE id=21;

UPDATE surveylabel SET label = 'Computer Use: Select the student&#39;s primary use of a computer during instruction<font size = "5" color = "red" >*</font>'
       WHERE labelnumber = 'Q143';

update surveyresponse set responseorder=4 where id=361;
update surveyresponse set responseorder=3 where id=360;

UPDATE surveyresponse SET responsevalue = 'Uses a computer with human support (with or without assistive technology)' WHERE id=360;

 INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
     VALUES ((select id from surveylabel where labelnumber='Q143'), 2,'Accesses a computer independently given assistive technology', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
     now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
     VALUES ((select id from surveylabel where labelnumber='Q143'), 5,'This student cannot access a computer with human or assistive technology support', now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
     now(), (Select id from aartuser where username = 'cetesysadmin'), 5);

update surveylabel set label='Why has this student not had the opportunity to access a computer during instruction? <font size = "5" color = "red" >*</font>' where labelnumber='Q147';

UPDATE surveyresponse SET responsevalue = 'The equipment is unavailable' WHERE id=363;
UPDATE surveyresponse SET responsevalue = 'I (or other educators at this school) have not had the opportunity to instruct the student on computer usage' WHERE id=365;

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q147'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=5),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

UPDATE surveylabel SET label = 'Computer access during instruction: Mark all that apply<font size = "5" color = "red" >*</font>-Standard computer keyboard' WHERE labelnumber = 'Q33_1';

update surveylabel set activeflag='false' where labelnumber='Q33_2';

UPDATE surveylabel SET label = 'Computer access during instruction: Mark all that apply<font size = "5" color = "red" >*</font>-Keyboard with large keys or alternative keyboard (e.g., Intellikeys)'
       WHERE labelnumber = 'Q33_3';

update surveylabel set activeflag='false' where labelnumber='Q33_4';

UPDATE surveylabel SET label = 'Computer access during instruction: Mark all that apply<font size = "5" color = "red" >*</font>-Standard mouse or head mouse'
       WHERE labelnumber = 'Q33_6';

update surveylabel set activeflag='false' where labelnumber='Q33_7';

UPDATE surveylabel SET label = 'Computer access during instruction: Mark all that apply<font size = "5" color = "red" >*</font>-Eye gaze technology (e.g., Tobii, EyeGaze Edge)'
       WHERE labelnumber = 'Q33_8';

update surveylabel set activeflag='false' where labelnumber='Q33_9';

UPDATE surveylabel SET label = 'Computer access during instruction: Mark all that apply<font size = "5" color = "red" >*</font>-Scanning with switches (one or two-switch scanning)'
       WHERE labelnumber = 'Q33_11';

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q201',201,'Level of attention to computer-directed instruction<font size = "5" color = "red" >*</font>', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 7,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ201Choice','Q201','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ201Choice' , 'Q201')));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q201'), 1,'Generally sustains attention to computer-directed instruction', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q201'), 2,'Demonstrates fleeting attention to computer-directed instructional activities and requires repeated bids or prompts for attention', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q201'), 3,'Demonstrates little or no attention to computer-directed instructional activities', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q202',202,'Level of attention to teacher-directed instruction<font size = "5" color = "red" >*</font>', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 7,(Select id from surveysection where surveysectioncode='COMPUTER_USE'));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ202Choice','Q202','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ202Choice' , 'Q202')));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q202'), 1,'Generally sustains attention to teacher-directed instruction', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q202'), 2,'Demonstrates fleeting attention to teacher-directed instructional activities and requires repeated bids or prompts for attention', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q202'), 3,'Demonstrates little or no attention to teacher-directed instructional activities', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);
 	 
--US16375
update surveysection set surveysectionname='Augmentative or Alternative Communication'  where id='10';

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q210',210,'How many symbols does the student choose from when communicating? (choose the highest that applies)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q210'), 1,'1 or 2 at a time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q210'), 2,'3 or 4 at a time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q210'), 3,'5 to 9 at a time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q210'), 4,'10 or more at a time', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ210Choice','Q210','{'',1,2,3,4}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ210Choice' , 'Q210')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q211_1',211, 'What types of symbols does the student use? (choose all that apply)-Real objects', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q211_1'), 1,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ211_1Choice','Q211_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ211_1Choice' , 'Q211_1')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q211_2',212, 'What types of symbols does the student use? (choose all that apply)-Tactual symbols', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q211_2'), 1,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ211_2Choice','Q211_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ211_2Choice' , 'Q211_2')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q211_3',213, 'What types of symbols does the student use? (choose all that apply)-Photos', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q211_3'), 1,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ211_3Choice','Q211_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ211_3Choice' , 'Q211_3')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q211_4',214, 'What types of symbols does the student use? (choose all that apply)-Line drawing symbol sets (Boardmaker, PCS, Symbol Stix, other)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q211_4'), 1,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ211_4Choice','Q211_4','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ211_4Choice' , 'Q211_4')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q211_5',215, 'What types of symbols does the student use? (choose all that apply)-Text Only', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q211_5'), 1,true, now(), (Select id from aartuser where username = 'cetesysadmin'), true, 
	 now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ211_5Choice','Q211_5','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ211_5Choice' , 'Q211_5')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q300_1',300, 'What voice output technology does the student use?-Single message devices (e.g., BIGmac)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q300_1'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ300_1Choice','Q300_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ300_1Choice' , 'Q300_1')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q300_2',302, 'What voice output technology does the student use?-Simple devices (e.g., GoTalk; QuickTalker; SuperTalker)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q300_2'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ300_2Choice','Q300_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ300_2Choice' , 'Q300_2')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q300_3',303, 'What voice output technology does the student use?-Speech generating device (e.g., Tobii-DynaVox, PRC/PrentkeRomich)', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q300_3'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ300_3Choice','Q300_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ300_3Choice' , 'Q300_3')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q300_4',304, 'What voice output technology does the student use?-None', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q300_4'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ300_4Choice','Q300_4','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ300_4Choice' , 'Q300_4')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q310',311,'If the student does not use speech, sign language, or augmentative or alternative communication, which of the following statements best describes the student''s expressive communication? Choose the highest statement that applies<font size = "5" color = "red" >*</font>', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 11,(Select id from surveysection where surveysectioncode='AUGMENTATIVE_OR_ALTERNATE_COMMUNICATIONS'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q310'), 1,'Uses conventional gestures (e.g., waving, nodding and shaking head, thumbs up/down), looking, pointing, and/or vocalizations to communicate intentionally but does not yet use symbols or sign language', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q310'), 2,'Uses only unconventional vocalizations (e.g., grunts), unconventional gestures (e.g., opening mouth wide to indicate hunger), and/or body movement to communicate intentionally', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
 	 VALUES ((select id from surveylabel where labelnumber='Q310'), 3,'Exhibits behaviors that may be reflexive and are not intentionally communicative but can be interpreted by others as communication (e.g., crying, laughing, reaching for an object, pushing an object away)', now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ310Choice','Q310','{'',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ310Choice' , 'Q310')));

update surveylabel set activeflag='false' where labelnumber IN ('Q45_1','Q45_2','Q45_3','Q45_4','Q45_5','Q45_6','Q45_7','Q45_8','Q45_9','Q45_10','Q45_11','Q45_12');

--US16379
update surveylabel set label='Reading skills: MARK EACH ONE to show how consistently the student uses each skill <font size = "5" color = "red" >*</font><br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%". -A) Recognizes single symbols presented visually or tactually (e.g., letters, numerals, environmental signs such as restroom symbols, logos, trademarks, or business signs such as fast food restaurants)' where labelnumber='Q51_1';

update surveyresponse set activeflag='false' where id =67;
update surveyresponse set activeflag='false' where id =72;
update surveyresponse set activeflag='false' where id =77;
update surveyresponse set activeflag='false' where id =82;
update surveyresponse set activeflag='false' where id =87;
update surveyresponse set activeflag='false' where id =92;
update surveyresponse set activeflag='false' where id =97;
update surveyresponse set activeflag='false' where id =102;

update surveyresponse set responsevalue='0% - 20% of the time-Almost never' where id=68;
update surveyresponse set responsevalue='21% - 50% of the time-Occasionally' where id=69;
update surveyresponse set responsevalue='51%   80% of the time - Frequently' where id=70;
update surveyresponse set responsevalue='More than 80% of the time - Consistently' where id=71;
update surveylabel set label='Student''s approximate instructional level of reading text with comprehension (print or Braille): Mark the highest one that applies<font size = "5" color = "red" >*</font>' where labelnumber='Q52';
update surveylabel set label='Math skills: MARK EACH ONE to show how consistently the student uses each skill <font size = "5" color = "red" >*</font>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-A) Creates or matches patterns of objects or images' where labelnumber='Q54_1';

update surveyresponse set activeflag='false' where id =113;
update surveyresponse set activeflag='false' where id =118;
update surveyresponse set activeflag='false' where id =123;
update surveyresponse set activeflag='false' where id =128;
update surveyresponse set activeflag='false' where id =133;
update surveyresponse set activeflag='false' where id =138;
update surveyresponse set activeflag='false' where id =143;
update surveyresponse set activeflag='false' where id =148;
update surveyresponse set activeflag='false' where id =153;
update surveyresponse set activeflag='false' where id =158;
update surveyresponse set activeflag='false' where id =163;
update surveyresponse set activeflag='false' where id =168;
update surveyresponse set activeflag='false' where id =173;

update surveyresponse set responsevalue='0% - 20% of the time-Almost never' where id=114;
update surveyresponse set responsevalue='21% - 50% of the time-Occasionally' where id=115;
update surveyresponse set responsevalue='51%   80% of the time - Frequently' where id=116;
update surveyresponse set responsevalue='More than 80% of the time - Consistently' where id=117;

update surveylabel set activeflag=false where labelnumber IN('Q56_1','Q56_2','Q56_3','Q56_4','Q56_5','Q56_6','Q56_7','Q56_8');

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q400',400, 'Writing skills: Indicate the highest level that describes the student''s writing skills. Choose the highest level that the student has demonstrated even once during instruction, not the highest level demonstrated consistently. Writing includes any method the student uses to write using any writing tool that includes access to all 26 letters of the alphabet. Examples of these tools include paper and pencil, traditional keyboards, alternate keyboards and eye-gaze displays of letters', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 16,(Select id from surveysection where surveysectioncode='WRITING'));

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q400'), 1,'Writes paragraph length text without copying using spelling (with or without word prediction)', 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q400'), 2,'Writes sentences or complete ideas without copying using spelling (with or without word prediction)',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 2);

 INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q400'), 3,'Writes words or simple phrases without copying using spelling (with or without word prediction)',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 3);

 INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q400'), 4,'Writes words using letters to accurately reflect some of the sounds',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 4);

 INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q400'), 5,'Writes using word banks or picture symbols',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 5);

 INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q400'), 6,'Writes by copying words or letters',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 6);

 INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q400'), 7,'Scribbles or randomly writes/selects letters or symbols',
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 7);

update surveylabel set label='Math skills: MARK EACH ONE to show how consistently the student uses each skill <font size = "5" color = "red" >*</font><br>If the student previously demonstrated and no longer receives instruction, mark "More than 80%".-A) Creates or matches patterns of objects or images' where labelnumber='Q54_1'; 	
