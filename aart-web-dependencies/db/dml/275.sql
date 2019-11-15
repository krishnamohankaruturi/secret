
--added braille test type with code of B
insert into testtype ( testtypecode, testtypename, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('B', 'General - Braille/English', (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='B'), (select id from subjectarea where subjectareacode='D74'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='B'), (select id from subjectarea where subjectareacode='D75'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='M') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='B') and subjectareaid=(select id from subjectarea where subjectareacode='D74')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='ELA') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='B') and subjectareaid=(select id from subjectarea where subjectareacode='D75')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());