insert into category(categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate,
createduser,activeflag, modifieddate, modifieduser) values 
('PNP Unenrolled', 'pnpunenrolled','PNP Unenrolled',(select id from categorytype where typecode = 'STUDENT_TEST_STATUS')
,4,'AART_ORIG',now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'));

insert into category(categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate,
createduser,activeflag, modifieddate, modifieduser) values 
('PNP Unenrolled', 'pnpunenrolled','PNP Unenrolled',(select id from categorytype where typecode = 'STUDENT_TESTSECTION_STATUS')
,4,'AART_ORIG',now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'));