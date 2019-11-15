
-- 369.sql
-- 368.sql
-- US15813: Test Coordination - require confirm for some Special Circumstance codes

UPDATE specialcircumstance SET requireConfirmation = false;
UPDATE specialcircumstance SET requireConfirmation = TRUE
WHERE (ksdecode='08' and cedscode=13813) or (ksdecode='41' and cedscode=9999) or (ksdecode='39' and cedscode=9999) or (ksdecode='27' and cedscode=13820);

update assessment set autoenrollmentflag = true where assessmentcode = 'SCD' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'KAP'
));

insert into testtype(testtypecode, testtypename, assessmentid, 
createduser, createdate, modifieddate, modifieduser, 
accessibilityflagcode, activeflag) 
values(3, 'Alternate', (select id from assessment where assessmentcode = 'SCD' and testingprogramid = (select id from testingprogram where assessmentprogramid = 12 and programname = 'Summative')),
(select id from aartuser where username='cetesysadmin'), now(), now(), (select id from aartuser where username='cetesysadmin'),
null, true);

insert into testtypesubjectarea(testtypeid, subjectareaid, createduser, createdate, modifieddate, modifieduser, activeflag, assessmentid)
values ((SELECT id FROM TESTTYPE where testtypename = 'Alternate' and assessmentid = (select id from assessment where assessmentcode = 'SCD' and testingprogramid = (select id from testingprogram where assessmentprogramid = 12 and programname = 'Summative'))
), (select id from subjectarea where subjectareacode = 'SHISGOVA'), (select id from aartuser where username='cetesysadmin'), 
now(), now(), (select id from aartuser where username='cetesysadmin'), true, 
(select id from assessment where assessmentcode = 'SCD' and testingprogramid = (select id from testingprogram where assessmentprogramid = 12 and programname = 'Summative')));

insert into contentareatesttypesubjectarea(contentareaid, testtypesubjectareaid, 
createduser, createdate, modifieddate, modifieduser, activeflag)
values ((select id from contentarea where abbreviatedname = 'SS'), (select id from testtypesubjectarea where testtypeid =  
(SELECT id FROM TESTTYPE where testtypename = 'Alternate' and assessmentid = (select id from assessment where assessmentcode = 'SCD' 
and testingprogramid = (select id from testingprogram where assessmentprogramid = 12 and programname = 'Summative')))),
 (select id from aartuser where username='cetesysadmin'), now(), now(),(select id from aartuser where username='cetesysadmin'),true );

insert into gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser,activeflag)
values ((select id from contentareatesttypesubjectarea where contentareaid = (select id from contentarea where abbreviatedname = 'SS') and testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid =  
(SELECT id FROM TESTTYPE where testtypename = 'Alternate' and assessmentid = (select id from assessment where assessmentcode = 'SCD' 
and testingprogramid = (select id from testingprogram where assessmentprogramid = 12 and programname = 'Summative'))))), 
(select id from gradecourse where abbreviatedname = '6' and contentareaid = (select id from contentarea where abbreviatedname = 'SS')),
(select id from aartuser where username='cetesysadmin'), now(), now(), (select id from aartuser where username='cetesysadmin'), true);

insert into gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser,activeflag)
values ((select id from contentareatesttypesubjectarea where contentareaid = (select id from contentarea where abbreviatedname = 'SS') and testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid =  
(SELECT id FROM TESTTYPE where testtypename = 'Alternate' and assessmentid = (select id from assessment where assessmentcode = 'SCD' 
and testingprogramid = (select id from testingprogram where assessmentprogramid = 12 and programname = 'Summative'))))), 
(select id from gradecourse where abbreviatedname = '8' and contentareaid = (select id from contentarea where abbreviatedname = 'SS')),
(select id from aartuser where username='cetesysadmin'), now(), now(), (select id from aartuser where username='cetesysadmin'), true);

insert into gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser,activeflag)
values ((select id from contentareatesttypesubjectarea where contentareaid = (select id from contentarea where abbreviatedname = 'SS') and testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid =  
(SELECT id FROM TESTTYPE where testtypename = 'Alternate' and assessmentid = (select id from assessment where assessmentcode = 'SCD' 
and testingprogramid = (select id from testingprogram where assessmentprogramid = 12 and programname = 'Summative'))))), 
(select id from gradecourse where abbreviatedname = '11' and contentareaid = (select id from contentarea where abbreviatedname = 'SS')),
(select id from aartuser where username='cetesysadmin'), now(), now(), (select id from aartuser where username='cetesysadmin'), true);
