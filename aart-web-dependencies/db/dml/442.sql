--For /dml/442.sql
--Script Bees Team
--Remove checked answers
INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q210'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q310'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_5'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q43' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

--Remove checked in computer access
INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q202'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder='1'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q202'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder='2'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q202'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder='3'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q202'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder='4'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q202'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder='5'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

-- update for US16585
update surveyresponse set responselabel='01' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='1';
update surveyresponse set responselabel='02' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='2';
update surveyresponse set responselabel='03' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='3';
update surveyresponse set responselabel='04' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='4';
update surveyresponse set responselabel='05' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='5';
update surveyresponse set responselabel='06' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='6';
update surveyresponse set responselabel='07' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='7';
update surveyresponse set responselabel='08' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='8';
update surveyresponse set responselabel='09' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='9';
update surveyresponse set responselabel='10' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='10';
update surveyresponse set responselabel='11' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='11';
update surveyresponse set responselabel='12' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='12';
update surveyresponse set responselabel='13' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='13';
update surveyresponse set responselabel='14' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='14';
update surveyresponse set responselabel='15' where labelid=(select id from surveylabel where labelnumber='Q16_1') and responselabel='91';

--ScriptBees Team 
--US16553
INSERT INTO authorities(
            authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ( 'PREM_TEST_WINDOW_VIEW','View Operational Tets Window' ,'Test Management -Test WIndow', current_timestamp,
    (Select id from aartuser where username='cetesysadmin') ,
	     true,current_timestamp, (Select id from aartuser where username='cetesysadmin'));

-- US16344
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('DATA_EXTRACTS_TESTFORM_ASIGNMENT', 'Create Test Form Assignment QC Extract', 'Reports-Data Extracts',CURRENT_TIMESTAMP,(select id from aartuser where username='cetesysadmin'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where username='cetesysadmin'));

--	US16343				
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('DATA_EXTRACTS_TESTFORM_MEDIA', 'Create Test Form Media Resource QC Extract', 
	'Reports-Data Extracts',CURRENT_TIMESTAMP,(select id from aartuser where username='cetesysadmin'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where username='cetesysadmin'));

-- US16560
insert into authorities(authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser) 
values('PERM_EXIT_STUDENT' ,'Exit Student' ,'Student Management-EXIT' , CURRENT_TIMESTAMP ,(select id from aartuser where email='cete@ku.edu') ,true , CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

UPDATE authorities SET objecttype = 'Student Management-EXIT' WHERE id = (select id from authorities where authority = 'PERM_EXIT_UPLOAD');
UPDATE authorities SET objecttype = 'Test Management-Test Window' WHERE id = (select id from authorities where authority = 'PREM_TEST_WINDOW_VIEW');
