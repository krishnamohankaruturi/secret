--447.sql

 update organization set reportyear = 2015 where contractingorganization is true;
 
 --Changes from Script Bees for FCS issues
 --update page numbers and attention
update surveylabel set activeflag='false' where labelnumber='Q60';
update surveylabel set globalpagenum=17 where globalpagenum=18;

--US16630, US16631
update surveylabel set activeflag='false' where labelnumber='Q20_7';
update surveylabel set label='Hearing: Mark all that apply-Uses unilateral hearing aid' where labelnumber='Q20_2';
update surveylabel set label='Hearing: Mark all that apply-Uses bilateral hearing aid' where labelnumber='Q20_3';
update surveylabel set label='Hearing: Mark all that apply-Has cochlear implant' where labelnumber='Q20_4';
update surveylabel set label='Hearing: Mark all that apply-Uses oral language' where labelnumber='Q20_5';
update surveylabel set label='Hearing: Mark all that apply-Uses sign language' where labelnumber='Q20_6';

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q20_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responselabel='3'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q20_2'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responselabel='3'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q20_3'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responselabel='3'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q20_4'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responselabel='3'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q20_5'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responselabel='3'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q20_6'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responselabel='3'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q20_5'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responselabel='2'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q20_6'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q19' AND sr.responselabel='2'),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

--US16634, US16635, US16637
update surveylabel set globalpagenum=3 where labelnumber in ('Q151_1','Q151_2','Q151_3','Q25_1','Q25_2','Q25_3','Q25_4','Q25_5','Q25_6','Q25_7');

UPDATE SURVEYLABEL SET SURVEYORDER=107 WHERE LABELNUMBER='Q429_1';
UPDATE SURVEYLABEL SET SURVEYORDER=108 WHERE LABELNUMBER='Q429_2';
UPDATE SURVEYLABEL SET SURVEYORDER=109 WHERE LABELNUMBER='Q429_3';
UPDATE SURVEYLABEL SET SURVEYORDER=110 WHERE LABELNUMBER='Q429_4';
UPDATE SURVEYLABEL SET SURVEYORDER=111 WHERE LABELNUMBER='Q429_5';

UPDATE SURVEYLABEL SET SURVEYORDER=112 WHERE LABELNUMBER='Q23_1';
UPDATE SURVEYLABEL SET SURVEYORDER=113 WHERE LABELNUMBER='Q23_2';
UPDATE SURVEYLABEL SET SURVEYORDER=114 WHERE LABELNUMBER='Q23_3';

UPDATE SURVEYLABEL SET SURVEYORDER=115 WHERE LABELNUMBER='Q151_1';
UPDATE SURVEYLABEL SET SURVEYORDER=116 WHERE LABELNUMBER='Q151_2';
UPDATE SURVEYLABEL SET SURVEYORDER=117 WHERE LABELNUMBER='Q151_3';

UPDATE SURVEYLABEL SET SURVEYORDER=118 WHERE LABELNUMBER='Q25_1';
UPDATE SURVEYLABEL SET SURVEYORDER=119 WHERE LABELNUMBER='Q25_2';
UPDATE SURVEYLABEL SET SURVEYORDER=120 WHERE LABELNUMBER='Q25_3';
UPDATE SURVEYLABEL SET SURVEYORDER=121 WHERE LABELNUMBER='Q25_4';
UPDATE SURVEYLABEL SET SURVEYORDER=122 WHERE LABELNUMBER='Q25_5';

UPDATE SURVEYLABEL SET LABEL='If the student reads Braille, select all options used for assessment purposes-Unified English Braille (UEB)' WHERE LABELNUMBER='Q151_3';
UPDATE SURVEYLABEL SET LABEL='If the student reads Braille, select all types the student uses-Uncontracted Braille' WHERE LABELNUMBER='Q151_1';
UPDATE SURVEYLABEL SET LABEL='If the student reads Braille, select all types the student uses-Contracted Braille' WHERE LABELNUMBER='Q151_2';
UPDATE SURVEYLABEL SET LABEL='If the student reads Braille, select all types the student uses-Unified English Braille (UEB)' WHERE LABELNUMBER='Q151_3';

delete from surveylabelprerequisite where surveylabelid=(Select id from surveylabel where labelnumber='Q151_1') and surveyresponseid=(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responselabel='3');

--UPDATE PAGE NUMBERS AFTER REMOVING BRAILE-AND-VISUAL PAGE
update surveylabel set globalpagenum=4 where globalpagenum=5;
update surveylabel set globalpagenum=5 where globalpagenum=6;
update surveylabel set globalpagenum=6 where globalpagenum=7;
update surveylabel set globalpagenum=7 where globalpagenum=8;
update surveylabel set globalpagenum=8 where globalpagenum=9;
update surveylabel set globalpagenum=9 where globalpagenum=10;
update surveylabel set globalpagenum=10 where globalpagenum=11; 
update surveylabel set globalpagenum=11 where globalpagenum=12;
update surveylabel set globalpagenum=12 where globalpagenum=13;
update surveylabel set globalpagenum=13 where globalpagenum=14;
update surveylabel set globalpagenum=14 where globalpagenum=15;
update surveylabel set globalpagenum=15 where globalpagenum=16;
update surveylabel set globalpagenum=16 where globalpagenum=17;

--Computer Access Page moving
UPDATE SURVEYLABEL SET GLOBALPAGENUM=5 WHERE LABELNUMBER IN ('Q33_1','Q33_2','Q33_3','Q33_4','Q33_5','Q33_6','Q33_7','Q33_8','Q33_9','Q33_10','Q33_11','Q201','Q202');

--UPDATE PAGE NUMBERS AFTER REMOVING COMPUTER AND ACCESS PAGE
update surveylabel set globalpagenum=6 where globalpagenum=7;
update surveylabel set globalpagenum=7 where globalpagenum=8;
update surveylabel set globalpagenum=8 where globalpagenum=9;
update surveylabel set globalpagenum=9 where globalpagenum=10;
update surveylabel set globalpagenum=10 where globalpagenum=11; 
update surveylabel set globalpagenum=11 where globalpagenum=12;
update surveylabel set globalpagenum=12 where globalpagenum=13;
update surveylabel set globalpagenum=13 where globalpagenum=14;
update surveylabel set globalpagenum=14 where globalpagenum=15;
update surveylabel set globalpagenum=15 where globalpagenum=16;

--DE10785,DE10786
UPDATE SURVEYLABEL SET ACTIVEFLAG='false' where labelnumber='Q310';

--DE10869
update surveylabel set label='Select the student''s Primary Disability-Primary Disability' where labelnumber='Q16_1';
