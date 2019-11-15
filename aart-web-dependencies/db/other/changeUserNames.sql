update aartuser set username = lower(username)
where not exists
(Select 1 as duplication_exists from aartuser group by lower(username) having count(1) >1);