select count(distinct(id)) from groupauthorities where groupid in 
(
Select id
from groups
 where id not in (Select groupid from userorganizationsgroups)
  and groupname like 'TestAdmin%'and groupname not in ('TestAdmin','TestAdmin-KS')
);

--Expected 1703 in prod, rollback otherwise.For QA no verification on count is needed.

delete from groupauthorities where groupid in 
(
Select id
from groups
 where id not in (Select groupid from userorganizationsgroups)
  and groupname like 'TestAdmin%'and groupname not in ('TestAdmin','TestAdmin-KS')
);

select count(distinct(id)) from groups
 where id not in (Select groupid from userorganizationsgroups) and groupname
  like 'TestAdmin%'and groupname not in ('TestAdmin','TestAdmin-KS');

--Expected 48 in prod, rollback otherwise.For QA no verification on count is needed.

delete from groups
 where id not in (Select groupid from userorganizationsgroups) and groupname
  like 'TestAdmin%'and groupname not in ('TestAdmin','TestAdmin-KS');
