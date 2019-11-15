
--US14396 Name:  First Contact: Enhancement Removing Survey Questions


DELETE FROM studentsurveyresponse WHERE surveyresponseid in 
	(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q132_1','Q132_2','Q132_3', 'Q132_4')));


DELETE FROM studentsurveyresponse WHERE surveyresponseid in 
	(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q133')));


DELETE FROM studentsurveyresponse WHERE surveyresponseid in 
	(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q135')));

	
DELETE FROM studentsurveyresponse WHERE surveyresponseid in 
	(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q27_1','Q27_2','Q27_3','Q27_4')));


DELETE FROM studentsurveyresponse WHERE surveyresponseid in 
	(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q153')));


DELETE FROM studentsurveyresponse WHERE surveyresponseid in 
	(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q31_1','Q31_2','Q31_3')));

	
DELETE FROM studentsurveyresponse WHERE surveyresponseid in 
	(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q33_10')));


DELETE FROM studentsurveyresponse WHERE surveyresponseid in 
	(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q58_1','Q58_2','Q58_3','Q58_4')));
	

DELETE FROM studentsurveyresponse WHERE surveyresponseid in 
	(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q62','Q62_TEXT')));


DELETE FROM studentsurveyresponse WHERE surveyresponseid in 
	(SELECT id FROM surveyresponse WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q63_TEXT')));


--


UPDATE surveylabel SET globalpagenum = globalpagenum - 1, 
	modifieduser = (select id from aartuser where username='cetesysadmin') ,modifieddate=now() WHERE globalpagenum >= 6;

DELETE FROM surveypagestatus WHERE globalpagenum = 5;

UPDATE surveypagestatus set globalpagenum = globalpagenum - 1 WHERE globalpagenum >= 6;


--


UPDATE surveylabel SET activeflag = false
	WHERE labelnumber in ('Q132_1','Q132_2','Q132_3', 'Q132_4');

UPDATE surveylabelprerequisite SET activeflag = false
	WHERE surveylabelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q132_1','Q132_2','Q132_3', 'Q132_4'));


UPDATE surveylabel SET activeflag = false WHERE labelnumber in ('Q133');

UPDATE surveylabelprerequisite SET activeflag = false
	WHERE surveylabelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q133'));


UPDATE surveylabel SET activeflag = false WHERE labelnumber in ('Q135');

	
UPDATE surveylabel SET activeflag = false WHERE labelnumber in ('Q27_1','Q27_2','Q27_3','Q27_4');

UPDATE surveylabelprerequisite SET activeflag = false
	WHERE surveylabelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q27_1','Q27_2','Q27_3','Q27_4'));


UPDATE surveylabel SET activeflag = false WHERE labelnumber in ('Q153');


UPDATE surveylabel SET activeflag = false WHERE labelnumber in ('Q31_1','Q31_2','Q31_3');


UPDATE surveyresponse SET activeflag = false WHERE labelid in (SELECT id FROM surveylabel WHERE labelnumber in ('Q33_10'));


UPDATE surveylabel SET activeflag = false WHERE labelnumber in ('Q58_1','Q58_2','Q58_3','Q58_4');
	

UPDATE surveylabel SET activeflag = false WHERE labelnumber in ('Q62','Q62_TEXT');


UPDATE surveylabel SET activeflag = false WHERE labelnumber in ('Q63_TEXT');


--


UPDATE surveylabel SET label = 'Arm and hand control: Mark all that apply-Uses only one hand to perform tasks independently'
	WHERE labelnumber = 'Q29_2';

UPDATE surveylabel SET label = 'Arm and hand control: Mark all that apply-Requires physical assistance to perform tasks with hands independently'
	WHERE labelnumber = 'Q29_3';

UPDATE surveylabel SET label = 'Computer access: Mark all that apply-Scanning with switches' 
	WHERE labelnumber = 'Q33_11';

UPDATE surveylabel SET label = 'Does the student have any health issues (e.g., fragile medical condition, seizures, therapy or treatment that prevents the student from accessing instruction, medications, etc.) that interfere with instruction or assessment? <font size = "5" color = "red" >*</font>' 
	WHERE labelnumber = 'Q64';

UPDATE surveyresponse SET responsevalue = 'Yes' 
	WHERE labelid=(select id from surveylabel where labelnumber ='Q64') AND responseorder = 2;

UPDATE surveyresponse SET activeflag = false 
	WHERE labelid=(select id from surveylabel where labelnumber ='Q64') AND responseorder = 3;
		
UPDATE surveyresponse SET activeflag = false 
	WHERE labelid=(select id from surveylabel where labelnumber ='Q64') AND responseorder = 4;


--


UPDATE fieldspecification SET allowablevalues = '{'''',1,2}'
	WHERE mappedname in ('Q64');

DELETE FROM fieldspecificationsrecordtypes where fieldspecificationid in
	(SELECT id FROM fieldspecification where mappedname in ('Q132_1','Q132_2','Q132_3', 'Q132_4'));
DELETE FROM fieldspecification where mappedname in ('Q132_1','Q132_2','Q132_3', 'Q132_4');


DELETE FROM fieldspecificationsrecordtypes where fieldspecificationid in
	(SELECT id FROM fieldspecification where mappedname in ('Q133'));
DELETE FROM fieldspecification where mappedname in ('Q133');


DELETE FROM fieldspecificationsrecordtypes where fieldspecificationid in
	(SELECT id FROM fieldspecification where mappedname in ('Q135'));
DELETE FROM fieldspecification where mappedname  in ('Q135');


DELETE FROM fieldspecificationsrecordtypes where fieldspecificationid in
	(SELECT id FROM fieldspecification where mappedname in ('Q27_1','Q27_2','Q27_3','Q27_4'));	
DELETE FROM fieldspecification where mappedname in ('Q27_1','Q27_2','Q27_3','Q27_4');


DELETE FROM fieldspecificationsrecordtypes where fieldspecificationid in
	(SELECT id FROM fieldspecification where mappedname in ('Q153'));
DELETE FROM fieldspecification where mappedname in ('Q153');

	
DELETE FROM fieldspecificationsrecordtypes where fieldspecificationid in
	(SELECT id FROM fieldspecification where mappedname in ('Q31_1','Q31_2','Q31_3'));
DELETE FROM fieldspecification where mappedname in ('Q31_1','Q31_2','Q31_3');


DELETE FROM fieldspecificationsrecordtypes where fieldspecificationid in
	(SELECT id FROM fieldspecification where mappedname in ('Q33_10'));
DELETE FROM fieldspecification where mappedname in ('Q33_10');


DELETE FROM fieldspecificationsrecordtypes where fieldspecificationid in
	(SELECT id FROM fieldspecification where mappedname in ('Q58_1','Q58_2','Q58_3','Q58_4'));
DELETE FROM fieldspecification where mappedname in ('Q58_1','Q58_2','Q58_3','Q58_4');
	

DELETE FROM fieldspecificationsrecordtypes where fieldspecificationid in
	(SELECT id FROM fieldspecification where mappedname in ('Q62','Q62_TEXT'));
DELETE FROM fieldspecification where mappedname in ('Q62','Q62_TEXT');


DELETE FROM fieldspecificationsrecordtypes where fieldspecificationid in
	(SELECT id FROM fieldspecification where mappedname in ('Q63_TEXT'));
DELETE FROM fieldspecification where mappedname in ('Q63_TEXT');


