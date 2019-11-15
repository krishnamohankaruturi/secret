--421.sql
 
DELETE from groupauthorities WHERE authorityid in (select id from authorities WHERE authority='VIEW_ALTERNATE_STUDENT_REPORT');
DELETE from authorities WHERE authority='VIEW_ALTERNATE_STUDENT_REPORT';