--ddl/506.sql
--purge  'Upload Exits'
delete from restrictionsauthorities where authorityid = (select id from authorities where authority = 'PERM_EXIT_UPLOAD');
delete from groupauthorities where authorityid = (select id from authorities where authority = 'PERM_EXIT_UPLOAD');
delete from authorities where authority = 'PERM_EXIT_UPLOAD';