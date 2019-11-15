--work around for the utility pages untill it is fixed.

insert into groupauthorities (groupid,authorityid,createddate,createduser,activeflag,modifieddate,modifieduser)
(select groupid,87 as authorityid,
now() as createddate,createduser,activeflag,now() as modifieddate,modifieduser
from groupauthorities where groupid=3218 and authorityid=60);