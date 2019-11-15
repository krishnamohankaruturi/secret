--dml/708.sql
insert into authorities (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,level,sortorder)
select 'VIEW_PERMISSIONS_AND_ROLESTAB','View Permissions And Roles' ,'Administrative-View Tools',now(),12,true,now(),12,'Tools','Permissions And Roles',1,17000
WHERE NOT EXISTS (select 1 from authorities where authority='VIEW_PERMISSIONS_AND_ROLESTAB' and displayname='View Permissions And Roles' and activeflag is true);

insert into authorities (authority,displayname,objecttype,createddate,createduser,activeflag,modifieddate,modifieduser,tabname,groupingname,level,sortorder)
select 'PERMISSIONS_AND_ROLES_EXTRACT','View Permissions And Roles Extract' ,'Administrative-View Tools',now(),12,true,now(),12,'Tools','Permissions And Roles',2,17100
WHERE NOT EXISTS (select 1 from authorities where authority='PERMISSIONS_AND_ROLES_EXTRACT' and displayname='View Permissions And Roles Extract' and activeflag is true);