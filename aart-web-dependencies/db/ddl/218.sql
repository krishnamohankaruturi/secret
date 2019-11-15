
--for TDE
CREATE OR REPLACE FUNCTION randomizestudenttest (in_studenttestid bigint) 
RETURNS RECORD AS
$BODY$

  DECLARE
    randomized_testid bigint;
    randomized_testname text;
    status_id bigint;
    test_testsections RECORD;
    ret RECORD;
    
  BEGIN
    select into randomized_testid, randomized_testname  test.id, test.testname
	from (select * from studentstests stt join testcollection tc on tc.id = stt.testcollectionid where stt.id = in_studenttestid ) studenttesttestcollection
	join testcollectionstests tct on tct.testcollectionid = studenttesttestcollection.testcollectionid
	join test on test.id = tct.testid 
	where not exists (select st.testid from studentstests st where st.studentid = studenttesttestcollection.studentid and st.testcollectionid = studenttesttestcollection.testcollectionid and st.testid = test.id 
	and st.status NOT IN (select category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode != 'complete'))
	and (test.status is null or test.status = (select category.id 
	from category, categorytype 
	where category.categorytypeid = categorytype.id
	and categorytype.typecode = 'TESTSTATUS'
	and category.categorycode = 'DEPLOYED' ))
	and test.qccomplete = true ORDER BY random() LIMIT 1;

    IF randomized_testid is not null THEN
    
	update public.studentstests set testid = randomized_testid where id = in_studenttestid;

	select into status_id category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id 
	  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS' 
	  and category.categorycode = 'unused';

	FOR test_testsections IN
		select testsection.id from public.studentstests 
		inner join public.test on studentstests.testid = test.id
		inner join public.testsection on testsection.testid = test.id
		where studentstests.id = in_studenttestid

	LOOP 
	  insert into public.studentstestsections(studentstestid, testsectionid, statusid) values(in_studenttestid, test_testsections.id, status_id);
	END LOOP;

	select randomized_testid, randomized_testname into ret;

    ELSE
	SELECT null, null into ret;
	
  END IF;
  RETURN ret;
END;

$BODY$
  LANGUAGE 'plpgsql';