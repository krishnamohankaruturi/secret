
--fix for new studentsresponses table
alter table studentsresponses rename column aciveflag to activeflag;


--removed the cb changes to move to 122.sql for prod patch 12/20