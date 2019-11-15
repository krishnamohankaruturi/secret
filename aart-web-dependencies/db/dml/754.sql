--This is requested by DBA for PLTW 

insert into dashboardconfig (dashboardentityname, assessmentprogramid, operationaltestwindowid, configcode, configvalue, createddate, modifieddate)
values      ('General', null, null, 'schoolyear', '2019', now(), now()),
	    ('General', null, null, 'startdatetime', '2018-09-19', now(), now()),
	    ('General', null, null, 'endtime', null, now(), now()),
	    ('General', null, null, 'middayruncomplete', 'false', now(), now());
		
