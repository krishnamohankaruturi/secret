--DE19496 deleting duplicated security agreement elections and adding id to the table. A indiviual record is maintained for each assessment program agreement/disagreement.
--The most recent record's aggreement election for that assessment program will be used.

--create temp table
drop table if exists tmp_delete_usersecurityagreement;
select id,row_number() over (partition by aartuserid,assessmentprogramid,schoolyear,agreementelection order by  agreementsigneddate desc) row_num  into temp tmp_delete_usersecurityagreement from usersecurityagreement ;


--delete duplicate records
delete from usersecurityagreement where id in (select id from tmp_delete_usersecurityagreement where row_num>1);

--drop temp table
drop table if exists tmp_delete_usersecurityagreement;


