--Select * from testcollectionstests;

--these are the tests that need to be seperated in to their own test collection.
--Select testcollectionid,min(testid) min_test_id,max(testid) max_test_id,count(1)
 --from testcollectionstests group by testcollectionid having count(1) > 1;

--Select * from testcollectionstests where testcollectionid=6;

--Select * from test where id = 25;
--why is the gradecourse, and content area not populated...? remove the columns or populate them.

--update the grade course on cases where it is not set.
update test
 set gradecourseid = (select tc.gradecourseid from testcollection tc, testcollectionstests tct
 where tc.id = tct.testcollectionid and tct.testid= test.id limit 1)
where
 gradecourseid is null;

--update the content area on cases where it is not set.
update test
 set contentareaid = (select tc.contentareaid from testcollection tc, testcollectionstests tct
 where tc.id = tct.testcollectionid and tct.testid= test.id limit 1)
where
 contentareaid is null;

--update origniation code to CB on cases where it is not set.
update test
 set originationcode = 'CB'
where
 originationcode is null;

--make test names unique by appending id.

update test
set testname = testname || '-XYZ' ||id ;

--create test collection with test name.
 insert into testcollection(name,randomizationtype,gradecourseid,contentareaid,originationcode)
 (
 Select t.testname as testcollectionname,'enrollment' as randomizationtype, t.gradecourseid,t.contentareaid,t.originationcode
 from test t,testcollectionstests tct,
 (
 Select testcollectionid,min(testid) min_test_id,max(testid) max_test_id,count(1)
 from testcollectionstests group by testcollectionid having count(1) > 1
 ) min_max_test_collection
  where
   t.id = tct.testid and
   tct.testcollectionid = min_max_test_collection.testcollectionid and
   tct.testid <> min_test_id
 );

--delete the association for test collection having multiple tests.
delete from testcollectionstests where testcollectionid in
(
 Select testcollectionid
 from testcollectionstests group by testcollectionid having count(1) > 1
) and testid not in 
(
Select min(testid)
 from testcollectionstests group by testcollectionid having count(1) > 1 
);
--assign test collection for tests that don't have a collection.

insert into testcollectionstests
(
Select tc.id,t.id from test t,testcollection tc where t.id not in (select testid from testcollectionstests)
and tc.name = t.testname
);

--insert into assessment testcollections so that it will show up in AART.

--Select * from assessment where assessmentname='Siva''s_TestPopulation'

insert into assessmentstestcollections
(
Select a.id,tc.id from assessment a,testcollection tc
 where
   a.assessmentname='Siva''s_TestPopulation' and
   tc.id not in (select testcollectionid from assessmentstestcollections)
);

--update the test names back.
update test
set testname = regexp_replace(testname, '-XYZ......', '.');

update test
set testname = regexp_replace(testname, '-XYZ.....', '.');

update test
set testname = regexp_replace(testname, '-XYZ....', '.');

update test
set testname = regexp_replace(testname, '-XYZ...', '.');

update test
set testname = regexp_replace(testname, '-XYZ..', '.');

update test
set testname = regexp_replace(testname, '-XYZ.', '.');

update test
set testname = regexp_replace(testname, '.$', '');

--update the test collection names back

update testcollection
set name = regexp_replace(name, '-XYZ......', '.');

update testcollection
set name = regexp_replace(name, '-XYZ.....', '.');

update testcollection
set name = regexp_replace(name, '-XYZ....', '.');

update testcollection
set name = regexp_replace(name, '-XYZ...', '.');

update testcollection
set name = regexp_replace(name, '-XYZ..', '.');

update testcollection
set name = regexp_replace(name, '-XYZ.', '.');

update testcollection
set name = regexp_replace(name, '.$', '');

select regexp_replace('Mahesh.', '.$', '');
