
--US13298
update fieldspecification set fieldname='educatorIdentifier' where fieldname='userIdentifier';


-- auto registration.
/*
INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Social Studies' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') and subjectareaid = (select id from subjectarea where subjectareacode = 'D79')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Social Studies' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') and subjectareaid = (select id from subjectarea where subjectareacode = 'D80')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Social Studies' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') and subjectareaid = (select id from subjectarea where subjectareacode = 'D81')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
	
*/


