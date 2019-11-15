UPDATE studentstests SET studentid = 1307042 , enrollmentid = 2346322, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE id in (14089056, 14090551, 14086097, 14086378, 14064021, 14063957);

          
