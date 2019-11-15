--password: kfSTZSRXsg
insert into aartuser(username,ukey, password, email, createduser, modifieduser ) 
values('ksdereturnextractuser',
'9FuuNSW4',
'768cde09754edabbefe1724e90efa2e1a9cbf5712107dd91dc8bb4b70b333b05fe45631ed58e4a61c7db9a9981ac3dce4ce4cd713b7bf6d51a3c8d5c03982f00',
'ksdereturnextractuser@ku.edu',
(select id from aartuser where username='cetesysadmin'),
(select id from aartuser where username='cetesysadmin'));
