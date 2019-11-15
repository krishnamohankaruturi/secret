--dml/716.sql

insert into authorities
   (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,level,sortorder)
select 'VIEW_PREDICTIVE_STUDENT_SCORE','View Interim Predictive Student Score','Other-Interim',NOW(),
          (select id from aartuser where username='cetesysadmin'),TRUE, NOW(),
          (select id from aartuser where username='cetesysadmin'),'Other','Interim',2,
          ((select sortorder from authorities where authority='PERM_INTERIM_ACCESS')+
          (select sortorder from authorities where authority='VIEW_DASHBOARD_MENU'))/2
where not exists(
select 1 from authorities where authority='VIEW_PREDICTIVE_STUDENT_SCORE'
);