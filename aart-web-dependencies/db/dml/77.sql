-- US13041: Auto registration - Auto register Alternate population

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid ,createdate,createduser,modifieddate,modifieduser) 
VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'G'),
(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid ,createdate,createduser,modifieddate,modifieduser) 
VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'G'),
(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D75')),
now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid ,createdate,createduser,modifieddate,modifieduser) 
VALUES ((select id from assessmentprogram where programname = 'Dynamic Learning Maps'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'SCD' limit 1),
(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '3') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid ,createdate,createduser,modifieddate,modifieduser) 
VALUES ((select id from assessmentprogram where programname = 'Dynamic Learning Maps'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'SCD' limit 1),
(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '3') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D75')),
now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

