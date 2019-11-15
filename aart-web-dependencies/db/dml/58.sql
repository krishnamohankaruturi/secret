
--R8 - Iter 3 

--US12803 Name: Quality Control Admin - Create new role and permission level. 
 INSERT INTO authorities(authority,displayname,objecttype,createduser,modifieduser) values
('HIGH_STAKES','High Stakes','High Stakes',
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin'));

--DE4753
update studentprofileitemattributevalue set selectedvalue = '#efee79' where selectedvalue='#f5f2a4' and profileitemattributenameattributecontainerid = (
select id from profileitemattributenameattributecontainer where attributecontainerid = (select id from profileitemattributecontainer where attributecontainer='ForegroundColour')
and attributenameid= (select id from profileitemattribute where attributename='colour'))