--423.sql

/************ US16246 : To make primary role mandatory in user upload *************/
update fieldspecification set rejectifempty = true,rejectifinvalid = true where fieldname = 'primaryRole';

insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser) 
          values('PD_TRAINING_EXPORT_FILE_CREATOR','PD Training Export File Creator','Professional Development-Professional Development', CURRENT_TIMESTAMP,
           (select id from aartuser where username='cetesysadmin'),TRUE, CURRENT_TIMESTAMP,
           (select id from aartuser where username='cetesysadmin'));

insert into authorities(authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser) 
          values('PD_TRAINING_RESULTS_UPLOADER','PD Training Results Uploader','Professional Development-Professional Development', CURRENT_TIMESTAMP,
           (select id from aartuser where username='cetesysadmin'),TRUE, CURRENT_TIMESTAMP,
           (select id from aartuser where username='cetesysadmin'));