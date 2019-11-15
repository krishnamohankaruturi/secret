--/dml/451.sql
--Computer instruction and Special education scripts 
--UPDATE surveysection SET  surveysectionname='Computer Access and Attention During Instruction' WHERE id=21;
update surveysection set surveysectionname='Computer Access and Attention During Instruction-REMOVED' where surveysectioncode='ACCESS_AND_SWITCHES';
update surveysection set surveysectionname='Computer Access and Attention During Instruction' where surveysectioncode='COMPUTER_USE';
update surveyresponse set responsevalue = 'Homebound/Hospital Environment: Includes students placed in and receiving special education in a hospital or homebound program' where labelid= (select id from surveylabel where labelnumber='Q17') and responselabel='6';
