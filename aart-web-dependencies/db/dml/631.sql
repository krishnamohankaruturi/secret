update studentassessmentprogram set createddate='2010-01-01 00:00:00+00', createduser=12, modifieddate=now(), modifieduser=12;
update testsectionstaskvariants set createddate='2010-01-01 00:00:00+00', createduser=12, modifieddate=now(), modifieduser=12;
update studentstestsections set modifieddate=createddate where modifieddate is null and createddate is not null;
update userorganizationsgroups set modifieddate='2016-12-31 00:00:00+00' where modifieddate is null;
update roster set modifieddate='2016-12-31 00:00:00+00' where modifieddate is null;
update usersorganizations set modifieddate='2016-12-31 00:00:00+00' where modifieddate is null;
