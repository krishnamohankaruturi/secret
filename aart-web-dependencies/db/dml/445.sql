--445.sql

--Changes from Script Bees
--changed label number Q20_
UPDATE SURVEYLABEL SET LABELNUMBER='Q20_1' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q425_1');
UPDATE SURVEYLABEL SET LABELNUMBER='Q20_2' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q425_2');
UPDATE SURVEYLABEL SET LABELNUMBER='Q20_3' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q425_3');
UPDATE SURVEYLABEL SET LABELNUMBER='Q20_4' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q425_4');
UPDATE SURVEYLABEL SET LABELNUMBER='Q20_5' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q425_5');
UPDATE SURVEYLABEL SET LABELNUMBER='Q20_6' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q425_6');
UPDATE SURVEYLABEL SET LABELNUMBER='Q20_7' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q425_7');

--changed label number Q23_
update surveylabel set labelnumber='Q23_1' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q430_1');
update surveylabel set labelnumber='Q23_2' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q430_2');
update surveylabel set labelnumber='Q23_3' WHERE ID=(SELECT ID FROM SURVEYLABEL WHERE LABELNUMBER='Q430_3');

update surveylabel set label='Level of attention to teacher-directed instruction' where labelnumber='Q202';
update surveylabel set label='Level of attention to computer-directed instruction' where labelnumber='Q201';
UPDATE surveylabel SET label = 'Computer access during instruction: Mark all that apply-Standard computer keyboard' WHERE labelnumber = 'Q33_1';

update surveylabel set optional='true' where labelnumber in ('Q16_1','Q17','Q19','Q22','Q200');
update surveylabel set label='Vision' where labelnumber='Q22';
update surveylabel set label='Does the student have any health issues (e.g., fragile medical condition, seizures, therapy or treatment that prevents the student from accessing instruction, medications, etc.) that interfere with instruction or assessment?' where labelnumber='Q200';
update surveysection set surveysectionname='Computer Access and Attention During Instruction' where  surveysectioncode='ACCESS_AND_SWITCHES';

update surveylabel set activeflag='false' where labelnumber='Q60';
update surveylabel set globalpagenum=17 where globalpagenum=18;
UPDATE surveylabel SET label = 'Classification of Visual Impairment: Mark all that apply-Low Vision (acuity of 20/70 to 20/200 in the better eye with correction)'WHERE labelnumber = 'Q429_1';

--Changes from change pond
update fieldspecification  set formatregex = '(0?[1-9]|1[012])(-)(0?[1-9]|[12][0-9]|3[01])(-)(19|20)?[0-9][0-9]|^$', minimum = 2, maximum=4 where fieldname = 'exitDateStr' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and 
fs.recordtypeid = (select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'exitDateStr')
);

update authorities set displayname='View Operational Test Window' where authority ='PREM_TEST_WINDOW_VIEW';
