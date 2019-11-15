--332.sql
--DE8040, ITI settings permission
insert into authorities (id, authority, displayname, objecttype, createddate, createduser, activeflag, modifieddate, modifieduser)
values (nextval('authorities_id_seq'), 'CHANGE_ITI_CONFIG', 'Change Instructional Tools Support Settings', 'Administrative-ITI',
now(), (select id from aartuser where username = 'cetesysadmin'),
true,
now(), (select id from aartuser where username = 'cetesysadmin'));