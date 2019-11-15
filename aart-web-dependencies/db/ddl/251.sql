--251.sql
CREATE OR REPLACE FUNCTION pdupdatesectionstatusandgetstatus(in_studenttestid bigint, in_testsectionid bigint, in_sectionscore text, in_testscore text, in_categorycode text)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
  BEGIN
	iscomplete := false;
	IF in_categorycode = 'inprogress' THEN
		update public.usertestsection set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
				and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now(), startdatetime = now() where usertestid = in_studenttestid and testsectionid = in_testsectionid; 
	ELSE
		update public.usertestsection set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
					and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now(), enddatetime = now() where usertestid = in_studenttestid and testsectionid = in_testsectionid; 
	END IF;

	SELECT count(1) into completecount FROM usertestsection AS sts
		JOIN category On category.id = sts.statusid 
		JOIN public.categorytype ON category.categorytypeid = categorytype.id 
		where categorytype.typecode ='STUDENT_TESTSECTION_STATUS'
			and category.categorycode != 'complete'
			and  sts.usertestid = in_studenttestid;

	IF completecount = 0 THEN
		iscomplete := true;
		update public.usertest set status = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'),
			scores = in_testscore, modifieddate=now(), enddatetime = now() where id = in_studenttestid;
	ELSIF in_testscore IS NOT NULL THEN
		update public.usertest set scores = in_testscore, modifieddate=now() where id = in_studenttestid;
	END IF;
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  