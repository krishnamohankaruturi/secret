
--remove unneed rubric data
delete from rubricinfo where rubriccategoryid in (select distinct id from rubriccategory where name is NULL);
delete from rubriccategory where name is NULL;
