--aart2.qa

--id received from tde2.qa
--select max(id)  from studentstests;
SELECT setval('public.studentstests_id_seq', 27700, true);
Select nextval('studentstests_id_seq'::regclass);

--aart3.qa

--id received from tde3.qa
--select max(id)  from studentstests;
SELECT setval('public.studentstests_id_seq', 27700, true);
Select nextval('studentstests_id_seq'::regclass);

--aart1.mci-staging

--id received from tde1.mci-staging
--select max(id)  from studentstests;
SELECT setval('public.studentstests_id_seq', 27700, true);
Select nextval('studentstests_id_seq'::regclass);

--aart1.mci-prod

--id received from tde1.mci-prod
--select max(id)  from studentstests;
SELECT setval('public.studentstests_id_seq', 27700, true);
Select nextval('studentstests_id_seq'::regclass);