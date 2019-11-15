--ddl/589.sql

CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatusforlcs(in_studenttestid bigint, in_testsectionid bigint, in_sectionscore text, in_testscore text, in_categorycode text, in_interimthetavalue double precision, in_stdteststartdatetime timestamp without time zone, in_stdtestenddatetime timestamp without time zone, in_stdtestsecstartdatetime timestamp without time zone, in_stdtestsecenddatetime timestamp without time zone)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
    trackerid bigint;
    testunusedstatusid bigint;
  BEGIN
	iscomplete := false;
	IF in_categorycode = 'inprogress' THEN
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
				and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	ELSE
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
					and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	END IF;
	
	IF in_stdtestsecstartdatetime IS NOT NULL THEN
		IF in_categorycode != 'complete' THEN
			update public.studentstestsections set startdatetime = in_stdtestsecstartdatetime,enddatetime=null where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
		ELSE 
			update public.studentstestsections set startdatetime = in_stdtestsecstartdatetime where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
		END IF;
	END IF;
	IF in_stdtestsecenddatetime IS NOT NULL AND in_categorycode = 'complete' THEN
		update public.studentstestsections set enddatetime = in_stdtestsecenddatetime where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
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
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'PROCESS_LCS_RESPONSES'),
			 scores = in_testscore, interimtheta = in_interimthetavalue, modifieddate=now() where id = in_studenttestid;

	ELSIF in_testscore IS NOT NULL THEN
		update public.studentstests set scores = in_testscore, modifieddate=now() where id = in_studenttestid;
	END IF;
	
	
	IF in_stdteststartdatetime IS NOT NULL THEN
		IF iscomplete = false THEN
			update public.studentstests set startdatetime = in_stdteststartdatetime,enddatetime=null where id = in_studenttestid; 
		ELSE 
			update public.studentstests set startdatetime = in_stdteststartdatetime where id = in_studenttestid; 
		END IF;
	END IF;
	IF in_stdtestenddatetime IS NOT NULL AND iscomplete = true THEN
		update public.studentstests set enddatetime = in_stdtestenddatetime where id = in_studenttestid; 
	END IF;	
	
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
