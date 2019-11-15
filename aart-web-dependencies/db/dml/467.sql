-- 467.sql

-- delete the older assignments
delete from groupauthorities where authorityid in (select id from authorities where objecttype = 'Test Management-Test Window');
-- delete the old role.
delete from  authorities where objecttype = 'Test Management-Test Window';              