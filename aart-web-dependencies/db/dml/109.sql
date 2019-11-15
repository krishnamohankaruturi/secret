

--  DE5235 ,  DE5231 , DE5232


--DELETE FROM contentareatesttypesubjectarea	
--	WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
--		AND subjectareaid = (select id from subjectarea where subjectareacode = 'D79'))
--		AND id = (SELECT max(id) FROM contentareatesttypesubjectarea 	
--				WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
--					AND subjectareaid = (select id from subjectarea where subjectareacode = 'D79')));
--	
--DELETE FROM contentareatesttypesubjectarea	
--	WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
--		AND subjectareaid = (select id from subjectarea where subjectareacode = 'D80'))
--		AND id = (SELECT max(id) FROM contentareatesttypesubjectarea 	
--				WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
--					AND subjectareaid = (select id from subjectarea where subjectareacode = 'D80')));
--
--DELETE FROM contentareatesttypesubjectarea	
--	WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
--		AND subjectareaid = (select id from subjectarea where subjectareacode = 'D81'))
--		AND id = (SELECT max(id) FROM contentareatesttypesubjectarea 	
--				WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
--					AND subjectareaid = (select id from subjectarea where subjectareacode = 'D81')));


UPDATE contentareatesttypesubjectarea SET contentareaid = (select id from contentarea where name = 'Social Studies' order by modifieddate desc limit 1)
	WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
			and subjectareaid = (select id from subjectarea where subjectareacode = 'D79'));

UPDATE contentareatesttypesubjectarea SET contentareaid = (select id from contentarea where name = 'Social Studies' order by modifieddate desc limit 1)
	WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
			and subjectareaid = (select id from subjectarea where subjectareacode = 'D80'));

UPDATE contentareatesttypesubjectarea SET contentareaid = (select id from contentarea where name = 'Social Studies' order by modifieddate desc limit 1)
	WHERE testtypesubjectareaid = (select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '2') 
			and subjectareaid = (select id from subjectarea where subjectareacode = 'D81'));

