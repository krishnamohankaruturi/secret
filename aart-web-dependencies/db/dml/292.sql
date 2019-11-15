
--US15080
insert into categorytype (typename, typecode, typedescription, originationcode, createddate, modifieddate, createduser, modifieduser)
values ('PD Report Status', 'PD_REPORT_STATUS', 'Status of professional development report creation is in progress', 'AART_ORIG_CODE', now(), now(), (select id from aartuser where username='cetesysadmin'), (select id from aartuser where username='cetesysadmin'));

insert into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, modifieddate, createduser, modifieduser)
values ('In Progress', 'IN_PROGRESS', 'Professional development report creation is in progress.',(select id from categorytype where typecode='PD_REPORT_STATUS'),'AART_ORIG_CODE', now(), now(), (select id from aartuser where username='cetesysadmin'), (select id from aartuser where username='cetesysadmin'));
insert into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, modifieddate, createduser, modifieduser)
values ('Completed', 'COMPLETED', 'Professional development report creation is completed.',(select id from categorytype where typecode='PD_REPORT_STATUS'),'AART_ORIG_CODE', now(), now(), (select id from aartuser where username='cetesysadmin'), (select id from aartuser where username='cetesysadmin'));