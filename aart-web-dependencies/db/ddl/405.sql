--  405.sql



alter table studentreport add column scalescore bigint;

alter table studentreport add column standarderror numeric(6,3);
Delete from studentreport;
alter table studentreport alter column studenttest2id drop not null;
alter table studentreport alter column externaltest2id drop not null;
alter table studentreport alter column studenttest1id drop not null;
alter table studentreport alter column externaltest1id drop not null;

alter table studentreport add column assessmentprogram bigint;

ALTER TABLE testcutscores DROP COLUMN levellowcutscore;
Delete from testcutscores;
ALTER TABLE testcutscores add COLUMN levellowcutscore bigint not null;

ALTER TABLE testcutscores DROP COLUMN levelhighcutscore;
ALTER TABLE testcutscores add COLUMN levelhighcutscore bigint not null;


alter table reportprocessreason add column testid1 bigint;
alter table reportprocessreason add column testid2 bigint;
