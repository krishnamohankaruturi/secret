--For /ddl/430.sql

--Script Bees
update surveylabel set activeflag='false' where labelnumber='Q24';

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q25_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');

INSERT INTO surveylabelprerequisite(surveylabelid, surveyresponseid, createddate, createduser, activeflag, modifieddate, modifieduser,prerequisitecondition)
VALUES ((Select id from surveylabel where labelnumber='Q151_1'), (Select sr.id from surveyresponse sr JOIN surveylabel sl ON sr.labelid=sl.id where sl.labelnumber='Q22' AND sr.responseorder=4),
now(), (Select id from aartuser where username='cetesysadmin'),true,now(), (Select id from aartuser where username='cetesysadmin'),'or');


update surveylabel set surveyorder=98  where labelnumber='Q330';

update surveylabel set surveyorder=114 where labelnumber='Q25_1';
update surveylabel set surveyorder=115 where labelnumber='Q25_2';
update surveylabel set surveyorder=116 where labelnumber='Q25_3';
update surveylabel set surveyorder=117 where labelnumber='Q25_4';
update surveylabel set surveyorder=118 where labelnumber='Q25_5';

update surveylabel set surveyorder=434 where labelnumber='Q430_1';
update surveylabel set surveyorder=435 where labelnumber='Q430_2';
update surveylabel set surveyorder=436 where labelnumber='Q430_3';

--Change Pond DE10257
update fieldspecification  set fieldname = 'exitDateStr' where fieldname = 'exitDate' and id = (select 
	fieldspecificationid from fieldspecificationsrecordtypes fs where
	 recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' )
	 and fieldspecificationid = ( select id from fieldspecification
	  where fs.fieldspecificationid = id and fs.recordtypeid = ( select id from 
	  category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'exitDate'  )
	);
	
--Change Pond DE10228 
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
    VALUES ('PERM_VIEW_STUDENT_PASSWORD','View student password','Student Management-Student Record',CURRENT_TIMESTAMP,
    	(Select id from aartuser where username='cetesysadmin'),TRUE,CURRENT_TIMESTAMP, (Select id from aartuser where username='cetesysadmin'));	
