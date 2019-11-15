--DE15436: Completed testlets not showing for transferred students

update studentstests set enrollmentid = 2927650, modifieddate = now() AT TIME ZONE 'GMT', modifieduser = 12
  where id in (select id from studentstests where enrollmentid = 2846524 and activeflag is true);

update testsession set rosterid = 1160190, modifieddate = now() AT TIME ZONE 'GMT', modifieduser = 12
  where id in (4551832, 4493074, 4492241, 4490290, 4462980, 4462545, 4462030, 4424042, 4329361, 4325518);

-- Inactivating the unused student survey
update studentstestsections set  activeflag = false, modifieduser = 12, modifieddate = now() AT TIME ZONE 'GMT' where studentstestid = 17583313;
  
update studentstests set activeflag = false, modifieduser = 12, modifieddate = now() AT TIME ZONE 'GMT' where id = 17583313;
  
update testsession  set activeflag = false, modifieduser = 12, modifieddate = now() AT TIME ZONE 'GMT' where id = 4799222;

-- For WI student in the comment sections
begin;
update testsession set rosterid = 1159733, modifieduser = 12, modifieddate = now() AT TIME ZONE 'GMT'
 where id in (select testsessionid from studentstests where studentid = 1389414 and activeflag is true)
 and rosterid = 1159086;

update testsession set rosterid = 1159734, modifieduser = 12, modifieddate = now() AT TIME ZONE 'GMT'
 where id in (select testsessionid from studentstests where studentid = 1389414 and activeflag is true)
 and rosterid = 1159087; 

commit;