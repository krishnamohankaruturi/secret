select count(1) from userorganizationsgroups where 
groupid in (5520,5485,5547,5529,5542,
5528,9652,5490,5199,5517,
5559,5480,5189,9564,
5573,9511,5489,9596,9547);

--update for userorganizations groups

update userorganizationsgroups set groupid=9572
where groupid in (5520,5485,5547,5529,5542,
5528,9652,5490,5199,5517,
5559,5480,5189,9564,
5573,9511,5489,9596,9547);

--expected to update 69 rows. rollback otherwise.
--69 users are moved to the new role TestAdministrator.
-- for qa , no verification on count is needed.

select count(distinct(id)) from groupauthorities where groupid in
(
5520,5485,5547,5529,5542,
5528,9652,5490,5199,5517,
5559,5480,5189,9564,
5573,9511,5489,9596,9547
);

--Expected to delete 672 in prod, rollback otherwise.For QA no verification on count is
--needed.

delete from groupauthorities where groupid in
(
5520,5485,5547,5529,5542,
5528,9652,5490,5199,5517,
5559,5480,5189,9564,
5573,9511,5489,9596,9547
);

select count(distinct(id)) from groups
 where id in (
5520,5485,5547,5529,5542,
5528,9652,5490,5199,5517,
5559,5480,5189,9564,
5573,9511,5489,9596,9547
);

--Expected 19 in prod, rollback otherwise.For QA no verification on count is
--needed.

delete from groups
 where id in (
5520,5485,5547,5529,5542,
5528,9652,5490,5199,5517,
5559,5480,5189,9564,
5573,9511,5489,9596,9547
 );
