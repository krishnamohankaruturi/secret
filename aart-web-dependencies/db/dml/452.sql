--/dml/451.sql
 
update fieldspecification set fieldlength = 552 where fieldname='levelDescription';
update fieldspecification set rejectifempty = false where fieldname='frameworkLevel3';

--Dependent questions for 'Questionable vision but testing inconclusive' prerequisite
INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
	VALUES ((Select id from surveylabel where labelnumber='Q23_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responselabel='4'),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
	VALUES ((Select id from surveylabel where labelnumber='Q23_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responselabel='4'),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
	VALUES ((Select id from surveylabel where labelnumber='Q23_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responselabel='4'),
	now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');


UPDATE SURVEYLABEL SET LABELNUMBER='Q24old' WHERE LABELNUMBER='Q24';

UPDATE SURVEYLABEL SET LABELNUMBER='Q24_1' WHERE LABELNUMBER='Q151_1';
UPDATE SURVEYLABEL SET LABELNUMBER='Q24_2' WHERE LABELNUMBER='Q151_2';
UPDATE SURVEYLABEL SET LABELNUMBER='Q24_3' WHERE LABELNUMBER='Q151_3';
