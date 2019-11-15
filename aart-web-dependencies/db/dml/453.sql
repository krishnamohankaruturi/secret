--453.sql

--delete complexity band rules for responses that aren't active anymore

delete from complexitybandrules where rule similar to ('%"responseid":(' ||
	array_to_string(
	array(
		select sr.id
		from surveyresponse sr
		inner join surveylabel sl on sr.labelid = sl.id
		where sl.labelnumber in ('Q36', 'Q37', 'Q39', 'Q40', 'Q43', 'Q44', 'Q51_1', 'Q52', 'Q54_3', 'Q54_5', 'Q54_7', 'Q54_8')
		and sr.activeflag = false
	), '|')
|| ')}%');

-- Changes from script bees, came as a result UAT session
update surveylabel set optional='false' where labelnumber='Q400';

update surveylabel set label='Writing skills: Indicate the highest level that describes the student''s writing skills. Choose the highest level that the student has demonstrated even once during instruction, not the highest level demonstrated consistently. Writing includes any method the student uses to write using any writing tool that includes access to all 26 letters of the alphabet. Examples of these tools include paper and pencil, traditional keyboards, alternate keyboards and eye-gaze displays of letters <font size = "5" color = "red" >*</font>' where labelnumber='Q400';

update surveyresponse set responselabel='4'  where responsevalue='This student has not had the opportunity to access a computer';
update surveyresponse set responselabel='3'  where responsevalue='Uses a computer with human support (with or without assistive technology)';

UPDATE operationaltestwindow SET createddate = modifieddate;

update surveylabel set globalpagenum=9 where labelnumber='Q47';
update surveylabel
 set 
 surveysectionid=(Select id from surveySection
 where surveySectionCode='EXPRESSIVE_COMMUNICATIONS'),
 modifieddate=now(),
 modifieduser=(Select id from aartuser where username='cetesysadmin')
 where labelnumber in ('Q43','Q44','Q47','Q210','Q211_1','Q211_2','Q211_3','Q211_4','Q211_5','Q300_1','Q300_2','Q300_3','Q300_4');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q210'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_5'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q36' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q210'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q211_5'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q300_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q39' AND sr.responsevalue='Yes'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');
