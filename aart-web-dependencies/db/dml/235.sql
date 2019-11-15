
--US14616
insert into category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser) values
('FC Unenrolled', 'fcunenrolled', 'FC Unenrolled', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS') , 4, 'AART_ORIG', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

insert into category (categoryname, categorycode, categorydescription, categorytypeid, externalid, originationcode, createddate, createduser, modifieddate, modifieduser) values
('FC Mid Unenrolled', 'fcmidunenrolled', 'FC Mid Unenrolled', (select id from categorytype where typecode = 'STUDENT_TEST_STATUS') , 5, 'AART_ORIG', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));