-- 476.sql DML
insert into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createduser, modifieduser) 
	values('In Queue','IN_QUEUE','Data  extraction report creation is in Queue', 
		(select id from categorytype where typecode ='PD_REPORT_STATUS'),'AART_ORIG_CODE', 
		(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'));
		
update category set categoryname = '08/16/2015 11:45:00 PM' where categorycode = 'TASC_SCHEDULED_WEB_SERVICE_START_TIME';

update category set categoryname = '08/17/2015 12:00:00 AM' where categorycode = 'TASC_SCHEDULED_WEB_SERVICE_END_TIME';

		
