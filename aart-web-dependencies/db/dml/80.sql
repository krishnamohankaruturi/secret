--Remove redundant data and for autoregistration

INSERT INTO testingprogram(externalid,programname,assessmentprogramid,createdate,modifieddate,originationcode,programabbr,highstake) 
VALUES (82,'Summative',(select ap.id from assessmentprogram ap where ap.programname = 'Kansas Assessment Program'),now(),now(),'CB','S', true);

INSERT INTO assessment(assessmentname,externalid,testingprogramid,createdate,modifieddate,originationcode,assessmentcode,createduser,activeflag,modifieduser) 
VALUES ('General',70,(select tp.id from testingprogram tp where tp.programabbr = 'S' 
and tp.assessmentprogramid = (select ap.id from assessmentprogram ap where ap.programname = 'Kansas Assessment Program')),now(),now(),'CB','GL',
(select id from aartuser where username='cetesysadmin'), true, (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid ,createdate,createduser,modifieddate,modifieduser) 
VALUES ((select ap.id from assessmentprogram ap where ap.programname = 'Kansas Assessment Program'),
(select tp.id from testingprogram tp where tp.programabbr = 'S' and tp.assessmentprogramid = (select ap.id from assessmentprogram ap where ap.programname = 'Kansas Assessment Program') order by tp.modifieddate desc limit 1),
(select id from assessment asst where assessmentcode = 'GL' and asst.testingprogramid = (select tp.id from testingprogram tp where tp.programabbr = 'S' 
and tp.assessmentprogramid = (select ap.id from assessmentprogram ap where ap.programname = 'Kansas Assessment Program') order by tp.modifieddate desc limit 1)),
(select cattsa.id from contentareatesttypesubjectarea cattsa 
JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id 
JOIN contentarea ca ON cattsa.contentareaid = ca.id 
where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74') 
and ca.id = (select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1)),
now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid ,createdate,createduser,modifieddate,modifieduser) 
VALUES  ((select ap.id from assessmentprogram ap where ap.programname = 'Kansas Assessment Program'),
(select tp.id from testingprogram tp where tp.programabbr = 'S' and tp.assessmentprogramid = (select ap.id from assessmentprogram ap where ap.programname = 'Kansas Assessment Program') order by tp.modifieddate desc limit 1),
(select id from assessment asst where assessmentcode = 'GL' and asst.testingprogramid = (select tp.id from testingprogram tp where tp.programabbr = 'S' 
and tp.assessmentprogramid = (select ap.id from assessmentprogram ap where ap.programname = 'Kansas Assessment Program') order by tp.modifieddate desc limit 1)),
(select cattsa.id from contentareatesttypesubjectarea cattsa 
JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id 
JOIN contentarea ca ON cattsa.contentareaid = ca.id 
where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D75') 
and ca.id = (select id from contentarea where name = 'English Language Arts' order by modifieddate desc limit 1)),
now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid ,createdate,createduser,modifieddate,modifieduser) 
VALUES ((select ap.id from assessmentprogram ap where ap.programname = 'Dynamic Learning Maps'),
(select tp.id from testingprogram tp where tp.programabbr = 'S' and tp.assessmentprogramid = (select ap.id from assessmentprogram ap where ap.programname = 'Dynamic Learning Maps') order by tp.modifieddate desc limit 1),
(select id from assessment asst where assessmentcode = 'SCD' and asst.testingprogramid = (select tp.id from testingprogram tp where tp.programabbr = 'S' 
and tp.assessmentprogramid = (select ap.id from assessmentprogram ap where ap.programname = 'Dynamic Learning Maps') order by tp.modifieddate desc limit 1)),
(select cattsa.id from contentareatesttypesubjectarea cattsa 
JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id 
JOIN contentarea ca ON cattsa.contentareaid = ca.id 
where ttsa.testtypeid = (select id from testtype where testtypecode = '3') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74') 
and ca.id = (select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1)),
now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid ,createdate,createduser,modifieddate,modifieduser) 
VALUES ((select ap.id from assessmentprogram ap where ap.programname = 'Dynamic Learning Maps'),
(select tp.id from testingprogram tp where tp.programabbr = 'S' and tp.assessmentprogramid = (select ap.id from assessmentprogram ap where ap.programname = 'Dynamic Learning Maps') order by tp.modifieddate desc limit 1),
(select id from assessment asst where assessmentcode = 'SCD' and asst.testingprogramid = (select tp.id from testingprogram tp where tp.programabbr = 'S' 
and tp.assessmentprogramid = (select ap.id from assessmentprogram ap where ap.programname = 'Dynamic Learning Maps') order by tp.modifieddate desc limit 1)),
(select cattsa.id from contentareatesttypesubjectarea cattsa 
JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id 
JOIN contentarea ca ON cattsa.contentareaid = ca.id 
where ttsa.testtypeid = (select id from testtype where testtypecode = '3') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D75') 
and ca.id = (select id from contentarea where name = 'English Language Arts' order by modifieddate desc limit 1)),
now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

