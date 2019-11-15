
--DE5207 Name: Test sessions are not being created for Auto Registration (state and K-8) 

update autoregistrationcriteria set testingprogramid =  (select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),
	assessmentid = (select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1)
	where contentareatesttypesubjectareaid = (select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D77'));

update autoregistrationcriteria set testingprogramid =  (select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),
	assessmentid = (select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1)
	where contentareatesttypesubjectareaid = (select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D78'));

update autoregistrationcriteria set testingprogramid =  (select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),
	assessmentid = (select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1)
	where contentareatesttypesubjectareaid = (select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D80'));

update autoregistrationcriteria set testingprogramid =  (select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),
	assessmentid = (select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1)
	where contentareatesttypesubjectareaid = (select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D81'));

update autoregistrationcriteria set testingprogramid =  (select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),
	assessmentid = (select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1)
	where contentareatesttypesubjectareaid = (select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76'));

update autoregistrationcriteria set testingprogramid =  (select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),
	assessmentid = (select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1)
	where contentareatesttypesubjectareaid = (select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '2') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D79'));

--US12298
update fieldspecification set fieldlength=30, formatregex='^[a-zA-Z0-9]*$',minimum=null, maximum=null  where fieldname in ('userIdentifier', 'educatorIdentifier');
	