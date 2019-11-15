Select organizationname,count(1) from organization group by organizationname;

Select * from organization;

delete from organization where id = 7;

delete from groups where organizationid=7;

delete from groupauthorities where groupid in (1,7,9);

delete from usergroups where groupid in (1,3);

--delete from usersorganizations where organizationid=65;

delete from organization where id = 5;

delete from organizationrelation where parentorganizationid=5;

delete from orgassessmentprogram where organizationid=7;

--delete invalid organization type.

Select * from organizationtype;

delete from data_version where version > 3;