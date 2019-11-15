--dml/582.sql

-- dml/582.sql

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_3'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=1),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_3'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=2),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_3'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=3),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_5'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=1),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_5'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=2),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_5'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=3),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_6'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=1),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_6'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=2),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_6'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=3),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_8'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=1),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_8'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=2),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_8'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=3),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_11'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=1),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_11'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=2),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, 
	modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q33_11'), 
(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responseorder=3),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');
