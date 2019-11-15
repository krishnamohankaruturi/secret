
--US13286 Name: Auto Registration Register students to Read Aloud and Braille test types.


INSERT INTO testtype(testtypecode,testtypename,assessmentid,createdate,createduser,modifieddate,modifieduser) VALUES ('C','Clear test subject indicator',(select id from assessment where assessmentcode = 'G'),now(), 
(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtype(testtypecode,testtypename,assessmentid,createdate,createduser,modifieddate,modifieduser) VALUES ('N','Special Waiver Assessment (e. g. ACT, Explore)',(select id from assessment where assessmentcode = 'G'),now(), 
(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));



-- testtypesubjectarea inserts

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '1'), (select id from subjectarea where subjectareacode = 'D74'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '6'), (select id from subjectarea where subjectareacode = 'D74'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '8'), (select id from subjectarea where subjectareacode = 'D74'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'A'), (select id from subjectarea where subjectareacode = 'D74'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'X'), (select id from subjectarea where subjectareacode = 'D74'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'P'), (select id from subjectarea where subjectareacode = 'D74'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'L'), (select id from subjectarea where subjectareacode = 'D74'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));





INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '1'), (select id from subjectarea where subjectareacode = 'D75'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '8'), (select id from subjectarea where subjectareacode = 'D75'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'A'), (select id from subjectarea where subjectareacode = 'D75'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'L'), (select id from subjectarea where subjectareacode = 'D75'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));




INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '1'), (select id from subjectarea where subjectareacode = 'D76'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '6'), (select id from subjectarea where subjectareacode = 'D76'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '8'), (select id from subjectarea where subjectareacode = 'D76'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'X'), (select id from subjectarea where subjectareacode = 'D76'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'R'), (select id from subjectarea where subjectareacode = 'D76'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'P'), (select id from subjectarea where subjectareacode = 'D76'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'L'), (select id from subjectarea where subjectareacode = 'D76'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));





INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '1'), (select id from subjectarea where subjectareacode = 'D77'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '6'), (select id from subjectarea where subjectareacode = 'D77'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '8'), (select id from subjectarea where subjectareacode = 'D77'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'X'), (select id from subjectarea where subjectareacode = 'D77'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'R'), (select id from subjectarea where subjectareacode = 'D77'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'P'), (select id from subjectarea where subjectareacode = 'D77'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'L'), (select id from subjectarea where subjectareacode = 'D77'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));





INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '1'), (select id from subjectarea where subjectareacode = 'D78'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '6'), (select id from subjectarea where subjectareacode = 'D78'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = '8'), (select id from subjectarea where subjectareacode = 'D78'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'X'), (select id from subjectarea where subjectareacode = 'D78'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'R'), (select id from subjectarea where subjectareacode = 'D78'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'P'), (select id from subjectarea where subjectareacode = 'D78'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode = 'L'), (select id from subjectarea where subjectareacode = 'D78'), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));	
	



-- contentareatesttypesubjectarea inserts.

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '1') and subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '6') and subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '8') and subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'A') and subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'X') and subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'P') and subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Mathematics' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'L') and subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));





INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where abbreviatedname = 'ELA' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '1') and subjectareaid = (select id from subjectarea where subjectareacode = 'D75')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where abbreviatedname = 'ELA' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '8') and subjectareaid = (select id from subjectarea where subjectareacode = 'D75')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where abbreviatedname = 'ELA' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'A') and subjectareaid = (select id from subjectarea where subjectareacode = 'D75')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where abbreviatedname = 'ELA' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'L') and subjectareaid = (select id from subjectarea where subjectareacode = 'D75')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));





INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '1') and subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '6') and subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '8') and subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'X') and subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'R') and subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'P') and subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'L') and subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));





INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '1') and subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '6') and subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '8') and subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'X') and subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'R') and subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'P') and subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'L') and subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));







INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '1') and subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '6') and subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = '8') and subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'X') and subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'R') and subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
	
INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'P') and subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO contentareatesttypesubjectarea(contentareaid,testtypesubjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from contentarea where name = 'Science' order by modifieddate desc limit 1),
	(select id from testtypesubjectarea where testtypeid = (select id from testtype where testtypecode = 'L') and subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));




-- autoregistrationcriteria inserts


INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '1') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '6') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '8') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'A') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'X') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'P') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'L') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D74')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));








INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '1') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D75')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '8') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D75')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'A') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D75')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'L') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D75')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));








INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '1') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 4' and assessmentprogramgradesid is not null order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '6') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 4' and assessmentprogramgradesid is not null order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '8') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 4' and assessmentprogramgradesid is not null order by modifieddate desc limit 1),  now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'X') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 4' and assessmentprogramgradesid is not null order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'R') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 4' and assessmentprogramgradesid is not null order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'P') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 4' and assessmentprogramgradesid is not null order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'L') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 4' and assessmentprogramgradesid is not null order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));



INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '1') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 7' order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '6') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 7' order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '8') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 7' order by modifieddate desc limit 1),  now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'X') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 7' order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'R') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 7' order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'P') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 7' order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, gradecourseid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'L') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D76')),
	(select id from gradecourse where name = 'Grade 7' order by modifieddate desc limit 1), now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));






INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '1') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '6') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '8') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'X') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'R') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'P') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'L') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D77')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));







INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '1') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '6') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = '8') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'X') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'R') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'P') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser) 
	VALUES ((select id from assessmentprogram where programname = 'Kansas Assessment Program'),(select tp.id from testingprogram tp join assessmentprogram ap on ap.id = tp.assessmentprogramid where tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program' order by tp.modifieddate desc limit 1),(select asm.id from assessment asm join testingprogram tp on asm.testingprogramid = tp.id join assessmentprogram ap on ap.id = tp.assessmentprogramid where asm.assessmentcode = 'GL' and tp.programabbr = 'S' and ap.programname = 'Kansas Assessment Program'  order by tp.modifieddate desc limit 1),
	(select cattsa.id from contentareatesttypesubjectarea cattsa JOIN testtypesubjectarea ttsa ON cattsa.testtypesubjectareaid = ttsa.id where ttsa.testtypeid = (select id from testtype where testtypecode = 'L') and ttsa.subjectareaid = (select id from subjectarea where subjectareacode = 'D78')),
	 now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));


