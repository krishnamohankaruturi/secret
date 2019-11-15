
-- 287.sql
-- HgZ4XaBF
-- u2ejjRKx
insert into aartuser(username,ukey, password, email, createduser, modifieduser ) 
values('mcitiuser', 'u2ejjRKx','6d87e2c100cabb1b1cdb8416b1192dd20a3e51f7d77ee32842041c71f6fb5b123b8857694453f8be8d804d900aba3ad509d8de58ac7e61baa0a02b08b16086d5', 'testiti@ku.edu', (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'));
