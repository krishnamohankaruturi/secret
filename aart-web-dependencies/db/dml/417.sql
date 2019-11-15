--For /dml/417.sql
--US16245  : User Activate/Deactivate Change pond

insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser) 
          values('PERM_USER_INACTIVATE','Inactivate User','Administrative-User', CURRENT_TIMESTAMP,
          	(select id from aartuser where username='cetesysadmin'),TRUE, CURRENT_TIMESTAMP,
          	(select id from aartuser where username='cetesysadmin'));

insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser) 
          values('PERM_USER_ACTIVATE','Activate User','Administrative-User', CURRENT_TIMESTAMP,
          (select id from aartuser where username='cetesysadmin'),TRUE, CURRENT_TIMESTAMP,
          (select id from aartuser where username='cetesysadmin'));