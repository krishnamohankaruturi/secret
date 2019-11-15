
--US14897
insert into categorytype (typename, typecode, typedescription, originationcode, createddate, modifieddate, createduser, modifieduser)
values ('Org Password Expriration Type', 'ORG_PASS_EXP_TYPE', 'When the user password expires for a contracting organization', 'AART_ORIG_CODE', now(), now(), (select id from aartuser where username='cetesysadmin'), (select id from aartuser where username='cetesysadmin'));

insert into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, modifieddate, createduser, modifieduser)
values ('Start Date', 'START_DATE', 'User passwords expire at the start of the organization school year.',(select id from categorytype where typecode='ORG_PASS_EXP_TYPE'),'AART_ORIG_CODE', now(), now(), (select id from aartuser where username='cetesysadmin'), (select id from aartuser where username='cetesysadmin'));
insert into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, modifieddate, createduser, modifieduser)
values ('End Date', 'END_DATE', 'User passwords expire at the end of the organization school year.',(select id from categorytype where typecode='ORG_PASS_EXP_TYPE'),'AART_ORIG_CODE', now(), now(), (select id from aartuser where username='cetesysadmin'), (select id from aartuser where username='cetesysadmin'));
