UPDATE testsession SET rosterid = 874699, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id in(2408650,2408408,2404233,2403993,2279215);
