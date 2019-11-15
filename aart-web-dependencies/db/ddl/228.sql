--ddl/228.sql
--223.sql;

DROP FUNCTION if exists randomizestudenttest(bigint);

CREATE OR REPLACE FUNCTION randomizestudenttest(in_username text)
  RETURNS TABLE(assessmentprogramId bigint, assessmentprogramname character varying(100),
	testingprogramid bigint, testingprogramname character varying(50),
	testname character varying(75), testformatcode character varying(30),
	studentstestsid bigint, studentid bigint,
	testid bigint, testcollectionid bigint,
	status bigint, testsessionid bigint,
	activeflag boolean, ticketno character varying(75), windowOpen boolean,
	createduser integer, modifieduser integer,
	modifieddate timestamp, createddate timestamp) AS
$BODY$
  DECLARE
    studenttestid bigint;
    randomized_testid bigint;
    randomized_testname text;
    status_id bigint;
    test_testsections RECORD;
    ret RECORD;
  BEGIN
  select into status_id category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id 
		  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS' 
		  and category.categorycode = 'unused';
		  
  FOR studenttestid IN (select sts.id from studentstests sts inner join student st on st.id=sts.studentid 
	where sts.testid is null and st.username=in_username and sts.activeflag is true)
  LOOP 
    select into randomized_testid, randomized_testname  test.id, test.testname
	from (select * from studentstests stt join testcollection tc on tc.id = stt.testcollectionid where stt.id = studenttestid ) studenttesttestcollection
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
		update public.studentstests set testid = randomized_testid where id = studenttestid;

		FOR test_testsections IN (select testsection.id from public.testsection 
				where testsection.testid = randomized_testid)
		LOOP 
		  insert into public.studentstestsections(studentstestid, testsectionid, statusid) values(studenttestid, test_testsections.id, status_id);
		END LOOP;
	 END IF;
    END LOOP;
    RETURN QUERY
   SELECT ap.id AS assessmentprogramId, ap.programname AS assessmentprogramname,
	tp.id AS testingprogramid, tp.programname AS testingprogramname,
	t.testname, t.testformatcode,
	st.id as studentstestsid, st.studentid,
	st.testid, st.testcollectionid,
	st.status, st.testsessionid,
	st.activeflag, st.ticketno,true as windowOpen,
	st.createduser, st.modifieduser,
	st.modifieddate, st.createddate
	FROM studentstests st INNER JOIN test t ON st.testid = t.id
		INNER JOIN student s ON st.studentid = s.id 
		INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
		INNER JOIN assessment a ON atc.assessmentid = a.id
		INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
		INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
		INNER JOIN operationaltestwindow otw ON otw.testcollectionid = st.testcollectionid
	WHERE st.status IN (SELECT c.id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
		AND c.categorycode !='complete' AND ct.typecode='STUDENT_TEST_STATUS')
		AND st.activeflag = true
		AND otw.suspendwindow = false
		AND otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP
		AND s.username = in_username order by st.createddate, t.testname;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
  --224.sql;

CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatus(IN in_studenttestid bigint, IN in_testsectionid bigint, IN in_sectionscore integer, IN in_testscore integer, IN in_categorycode text)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
  BEGIN
	iscomplete := false;
	IF in_categorycode = 'inprogress' THEN
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
				and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now(), startdatetime = now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	ELSE
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
					and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now(), enddatetime = now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	END IF;

	SELECT count(1) into completecount FROM studentstestsections AS sts
		JOIN category On category.id = sts.statusid 
		JOIN public.categorytype ON category.categorytypeid = categorytype.id 
		where categorytype.typecode ='STUDENT_TESTSECTION_STATUS'
			and category.categorycode = 'complete'
			and  sts.studentstestid = in_studenttestid;

	IF completecount = 0 THEN
		iscomplete := true;
		update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'),
			 scores = in_testscore, modifieddate=now(), enddatetime = now() where id = in_studenttestid;
	ELSIF in_testscore IS NOT NULL THEN
		update public.studentstests set scores = in_testscore, modifieddate=now() where id = in_studenttestid;
	END IF;
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
  --226.sql;
DROP FUNCTION IF EXISTS updatesectionstatusandgetteststatus(bigint, bigint, integer, integer, text);

CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatus(in_studenttestid bigint, in_testsectionid bigint, in_sectionscore text, in_testscore text, in_categorycode text)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
  BEGIN
	iscomplete := false;
	IF in_categorycode = 'inprogress' THEN
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
				and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now(), startdatetime = now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	ELSE
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
					and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now(), enddatetime = now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	END IF;

	SELECT count(1) into completecount FROM studentstestsections AS sts
		JOIN category On category.id = sts.statusid 
		JOIN public.categorytype ON category.categorytypeid = categorytype.id 
		where categorytype.typecode ='STUDENT_TESTSECTION_STATUS'
			and category.categorycode != 'complete'
			and  sts.studentstestid = in_studenttestid;

	IF completecount = 0 THEN
		iscomplete := true;
		update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'),
			 scores = in_testscore, modifieddate=now(), enddatetime = now() where id = in_studenttestid;
	ELSIF in_testscore IS NOT NULL THEN
		update public.studentstests set scores = in_testscore, modifieddate=now() where id = in_studenttestid;
	END IF;
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
 


      
