
-- Updates for auto registration defects - DE5328, DE5329, DE5330

UPDATE contentareatesttypesubjectarea SET contentareaid = (select id from contentarea where name = 'Science' AND abbreviatedname = 'Sci' order by modifieddate desc limit 1)
	WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
			and subjectareaid = (select id from subjectarea where subjectareacode = 'D76'));

UPDATE contentareatesttypesubjectarea SET contentareaid = (select id from contentarea where name = 'Science' AND abbreviatedname = 'Sci' order by modifieddate desc limit 1)
	WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
			and subjectareaid = (select id from subjectarea where subjectareacode = 'D77'));

UPDATE contentareatesttypesubjectarea SET contentareaid = (select id from contentarea where name = 'Science' AND abbreviatedname = 'Sci' order by modifieddate desc limit 1)
	WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
			and subjectareaid = (select id from subjectarea where subjectareacode = 'D78'));

			

UPDATE testcollection SET contentareaid = (select id from contentarea where name = 'Science' AND abbreviatedname = 'Sci' order by modifieddate desc limit 1) 
	WHERE contentareaid = (select id from contentarea where name = 'Science' AND abbreviatedname = 'SCI' order by modifieddate desc limit 1);
 

UPDATE test SET contentareaid = (select id from contentarea where name = 'Science' AND abbreviatedname = 'Sci' order by modifieddate desc limit 1) 
	WHERE contentareaid = (select id from contentarea where name = 'Science' AND abbreviatedname = 'SCI' order by modifieddate desc limit 1);
			

	