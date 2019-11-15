Select * from organization;

delete from organization where id = 2;

delete from groups where organizationid=10;

delete from groupauthorities where groupid in (2);

delete from usergroups where groupid in (2);

--delete from usersorganizations where organizationid=10;

delete from organization where id = 5;

delete from organizationrelation where parentorganizationid=2;

delete from orgassessmentprogram where organizationid=2;

--delete invalid organization type.

Select * from organizationtype;

Select organizationname,count(1) from organization group by organizationname;

Select * from organization;