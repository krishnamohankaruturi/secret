--F606 permissions
insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser, tabname, groupingname, level, sortorder) 
          values('VIEW_TESTING_SUMMARY_DASHBOARD','View Testing Summary','Other-Dashboard' ,NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),
                'Other', 'Dashboard', 2, 17400);
insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser, tabname, groupingname, level, sortorder) 
          values('VIEW_TESTING_SCORING_DASHBOARD','View Scoring Summary','Other-Dashboard' ,NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),
                'Other', 'Dashboard', 2, 17500);
insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser, tabname, groupingname, level, sortorder) 
          values('VIEW_REACTIVATIONS_DASHBOARD','View Reactivations','Other-Dashboard' ,NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),
                'Other', 'Dashboard', 2, 17600);                
insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser, tabname, groupingname, level, sortorder) 
          values('VIEW_TESTING_OUTHOURS_DASHBOARD','View Testing Outside Hours','Other-Dashboard' ,NOW(),
           (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
           (select id from aartuser where username='cetesysadmin'),
                'Other', 'Dashboard', 2, 17700); 