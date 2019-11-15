--For ddl/568.sql

alter table studentreport add column generated boolean;

update studentreport set generated=true where status=true and schoolyear=2015;
update studentreport set generated=false where status=false and schoolyear=2015;

alter table studentreport alter column generated set default false;
