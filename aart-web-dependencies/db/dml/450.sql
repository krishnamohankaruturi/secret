--/dml/450.sql
--Changes from script bees for FCS, 
--Script to remove the *
update surveylabel set label='Computer Use: Select the student''s primary use of a computer during instruction' where labelnumber='Q143';
update surveylabel set label='Why has this student not had the opportunity to access a computer during instruction?' where labelnumber='Q147';

UPDATE SURVEYLABEL SET OPTIONAL='TRUE' WHERE LABELNUMBER IN ('16_1','Q17','Q19','Q330','Q22','Q24','Q201','Q202','Q143','Q147','Q41','Q210','Q49_1','Q49_2','Q49_3','Q49_4','Q49_5','Q49_6','Q400');

delete from surveylabelprerequisite where surveylabelid=(Select id from surveylabel where labelnumber='Q202') and surveyresponseid=(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responselabel='1');
delete from surveylabelprerequisite where surveylabelid=(Select id from surveylabel where labelnumber='Q202') and surveyresponseid=(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responselabel='2');
delete from surveylabelprerequisite where surveylabelid=(Select id from surveylabel where labelnumber='Q202') and surveyresponseid=(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responselabel='3');
delete from surveylabelprerequisite where surveylabelid=(Select id from surveylabel where labelnumber='Q202') and surveyresponseid=(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responselabel='4');
delete from surveylabelprerequisite where surveylabelid=(Select id from surveylabel where labelnumber='Q202') and surveyresponseid=(Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q143' AND sr.responselabel='5');
