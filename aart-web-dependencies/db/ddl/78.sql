-- US13041: Auto registration - Auto register Alternate population

update contentareatesttypesubjectarea set contentareaid = (select id from contentarea where name = 'English Language Arts' order by modifieddate desc limit 1) 
	where contentareaid = (select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1)
		and testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '3') and subjectareaid = (select id from subjectarea where subjectareacode = 'D75'));
		

