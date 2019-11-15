

--US13081 Name:  Auto registration - Auto register General population for High School and K-8 Assessments


INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('hsStatePhysicalScienceAssess', NULL, NULL, NULL, 30, false, false, NULL, 'HS_State_Phys_Sci_Assess', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('k8StateHistoryGovAssess', NULL, NULL, NULL, 30, false, false, NULL, 'K8_State_Hist_Gov_Assess', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('hsStateHistoryGovWorld', NULL, NULL, NULL, 30, false, false, NULL, 'HS_State_Hist_Gov_World', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
	('hsStateHistoryGovState', NULL, NULL, NULL, 30, false, false, NULL, 'HS_State_Hist_Gov_State', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

					
INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('hsStatePhysicalScienceAssess', 'HS_State_Phys_Sci_Assess'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('k8StateHistoryGovAssess', 'K8_State_Hist_Gov_Assess'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('hsStateHistoryGovWorld', 'HS_State_Hist_Gov_World'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
	(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('hsStateHistoryGovState', 'HS_State_Hist_Gov_State'));


INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '2'), (select id from subjectarea where subjectareacode = 'D77'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '2'), (select id from subjectarea where subjectareacode = 'D78'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '2'), (select id from subjectarea where subjectareacode = 'D79'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '2'), (select id from subjectarea where subjectareacode = 'D80'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '2'), (select id from subjectarea where subjectareacode = 'D81'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '2'), (select id from subjectarea where subjectareacode = 'D76'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') and subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') and subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

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

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') and subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));



INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'GL' and testingprogramid = (select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1) order by modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'GL' and testingprogramid = (select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1) order by modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'GL' and testingprogramid = (select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1) order by modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D80')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'GL' and testingprogramid = (select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1) order by modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D81')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'GL' and testingprogramid = (select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1) order by modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 4' order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

	
INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'GL' and testingprogramid = (select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1) order by modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 7' order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')); 


INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'GL' and testingprogramid = (select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1) order by modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D79')),
	(select id from gradecourse where name = 'Grade 6' order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')); 


INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1),(select id from assessment where assessmentcode = 'GL' and testingprogramid = (select id from testingprogram tp where tp.programabbr = 'S' order by tp.modifieddate desc limit 1) order by modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D79')),
	(select id from gradecourse where name = 'Grade 8' order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')); 


 