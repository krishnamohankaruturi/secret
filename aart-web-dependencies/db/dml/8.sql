update category set categoryname='Roster' where categoryname = 'SCRS';

update category set categoryname='User' where categoryname = 'USER';

--AART relted table
--Original 15.sql

--update will fail silently if there are users with the same username with changes in capitalization.
update aartuser set username = lower(username)
where not exists
(Select 1 as duplication_exists from aartuser group by lower(username) having count(1) >1);

update aartuser set email = lower(email)
where not exists
(Select 1 as duplication_exists from aartuser group by lower(email) having count(1) >1);


--AART relted table
--Original 16.sql

insert into authorities (authority) values ('CETE_SYS_ADMIN')
except (select authority from authorities where authority = 'CETE_SYS_ADMIN');

insert into groupauthorities (groupid, authorityid) ((select groups.id, authorities.id 
    from groups, authorities 
    where groupname = 'System Administrator' 
    and organizationid = (select id from organization where displayidentifier = 'CETE') 
    and authority = 'CETE_SYS_ADMIN' 
    and displayname is null 
    and objecttype is null))
except (select groupid, authorityid from groupauthorities);