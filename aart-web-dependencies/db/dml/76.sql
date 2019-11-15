-- US13041: Auto registration - Auto register Alternate population

INSERT INTO testtype(testtypecode,testtypename,assessmentid,createdate,createduser,modifieddate,modifieduser) 
VALUES ('3','Alternate',(select id from assessment where assessmentcode = 'SCD' limit 1),now(), (select id from aartuser where 

username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '3'), (select id from subjectarea where subjectareacode = 

'D74'),now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where 

username='cetesysadmin'));
INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '3') , (select id from subjectarea where subjectareacode = 

'D75'),now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where 

username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea

(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	( (select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1) ,(select id from 

testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '3') and subjectareaid = (select id 

from subjectarea where subjectareacode = 'D74')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where 

username='cetesysadmin'));


INSERT INTO contentareatesttypesubjectarea

(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	( (select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1),(select id from 

testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '3') and subjectareaid = (select id 

from subjectarea where subjectareacode = 'D75')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where 

username='cetesysadmin'));