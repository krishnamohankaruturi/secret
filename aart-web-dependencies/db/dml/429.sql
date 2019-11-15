--For /ddl/429.sql

--Script Bees US16370 
UPDATE SURVEYLABEL SET LABELNUMBER='Q425_1' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q20_1');
UPDATE SURVEYLABEL SET LABELNUMBER='Q425_2' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q20_2');

UPDATE SURVEYLABEL SET LABELNUMBER='Q425_3' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q20_3');

UPDATE SURVEYLABEL SET LABELNUMBER='Q425_4' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q20_4');

UPDATE SURVEYLABEL SET LABELNUMBER='Q425_5' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q20_5');

UPDATE SURVEYLABEL SET LABELNUMBER='Q425_6' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q20_6');
UPDATE SURVEYLABEL SET LABELNUMBER='Q425_7' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q20_7');

--Script Bees US16371 
INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q429_1',429, 'Classification of Visual Impairment:Mark all that apply-Low Vision (acuity of 20/70 to 20/200 in the better eye with correction) ', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));



INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q429_1'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ429_1Choice','Q429_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ429_1Choice' , 'Q429_1')));


INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q429_2',430, 'Classification of Visual Impairment:Mark all that apply-Legally Blind (acuity of 20/200 or less or field loss to 20 degrees or less in the better eye with correction) ', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));



INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q429_2'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ429_2Choice','Q429_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ429_2Choice' , 'Q429_2')));


INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q429_3',431, 'Classification of Visual Impairment:Mark all that apply-Light Perception Only', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));



INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q429_3'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ429_3Choice','Q429_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ429_3Choice' , 'Q429_3')));


INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q429_4',432, 'Classification of Visual Impairment:Mark all that apply-Totally Blind', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));



INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q429_4'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ429_4Choice','Q429_4','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ429_4Choice' , 'Q429_4')));

INSERT INTO surveylabel (labelnumber, surveyorder, label, createddate, createduser, activeflag, modifieddate, modifieduser, optional, globalpagenum, surveysectionid)
	VALUES ('Q429_5',433, 'Classification of Visual Impairment:Mark all that apply-Cortical Visual Impairment', 
	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	true, 3,(Select id from surveysection where surveysectioncode='VISION'));



INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser, responselabel)
	VALUES ((select id from surveylabel where labelnumber='Q429_5'), 1,true, 
 	now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), 1);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ429_5Choice','Q429_5','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ429_5Choice' , 'Q429_5')));

update surveylabel set activeflag='false' where labelnumber='Q203';

update surveylabel set labelnumber='Q430_1' where id=(select id from surveylabel where labelnumber='Q23_1');
update surveylabel set labelnumber='Q430_2' where id=(select id from surveylabel where labelnumber='Q23_2');
update surveylabel set labelnumber='Q430_3' where id=(select id from surveylabel where labelnumber='Q23_3');

update surveyresponse set responselabel=4 where labelid=(select id from surveylabel where labelnumber='Q22') and responseorder=4;

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q429_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');


--Script Bees US 16233
--firstName
update fieldspecification set fieldlength=80 where fieldname='firstName' and mappedname is null;
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'firstName' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'FirstName');

--surName
update fieldspecification set fieldlength=80 where fieldname='lastName' and mappedname is null;
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('surName', NULL, NULL, NULL, 80, false, true, (select formatregex from fieldspecification where fieldname='firstName'), NULL, true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'surName' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'LastName');

--email
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'email' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'Email');

--idnumber
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('uniqueCommonIdentifier', NULL, NULL, NULL, 254, false, true, (select formatregex from fieldspecification where fieldname='educatorIdentifier' and fieldlength=254), NULL, true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'uniqueCommonIdentifier' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'idnumber');

--state
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('state', NULL, NULL, NULL, 100, false, false, NULL, NULL, true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'state' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'State');

--districtName		
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('districtName', NULL, NULL, NULL, 100, false, false, NULL, NULL, true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'districtName' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'DistrictName');

--districtID		
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('districtID', NULL, NULL, NULL, 100, false, false, NULL, NULL, true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'districtID' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'DistrictID');

--schoolName
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('schoolName', NULL, NULL, NULL, 100, false, false, NULL, NULL, true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'schoolName' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'SchoolName');

--schoolID
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('schoolID', NULL, NULL, NULL, 100, false, false, NULL, NULL, true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'schoolID' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'SchoolID');


--rtComplete
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('rtComplete', '{'',yes,YES,Yes,No,NO,no}', NULL, NULL, 3, false, true, NULL, NULL, true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'rtComplete' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'RTComplete');


--rtCompleteDate
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('rtCompleteDate', NULL, NULL, NULL, 30, false, false, NULL, NULL, true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser, mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'rtCompleteDate' and mappedname is NULL), (select id from category where categorycode = 'PD_TRAINING_RECORD_TYPE'),
		now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'), 'RTCompleteDate');

--Change pond new permission for merge user feature		
INSERT INTO authorities(
            authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_USER_MERGE','Merge Users','Administrative-User', current_timestamp, (Select id from aartuser where username='cetesysadmin'), 
            true, current_timestamp,(Select id from aartuser where username='cetesysadmin'));
