--Batch processing table structure redesign
DO
$BODY$ 
DECLARE TTARECORD RECORD;
BEGIN

	FOR TTARECORD IN
		select distinct id, testtypecode, assessmentid, activeflag from testtype where activeflag is true order by id
	LOOP
		UPDATE testtypesubjectarea SET assessmentid=TTARECORD.assessmentid
		WHERE testtypeid=TTARECORD.id;           
	END LOOP;
END;
$BODY$;

UPDATE subjectarea set activeflag = false where subjectareacode in ('D75','D76','D77','D78','D79','D80','D81','D82','D83');
UPDATE testtypesubjectarea set activeflag = false where subjectareaid in (select id from subjectarea where activeflag is false);
UPDATE contentareatesttypesubjectarea set activeflag = false where testtypesubjectareaid in (select id from testtypesubjectarea where activeflag is false);

UPDATE testtypesubjectarea set testtypeid=(select id from testtype where testtypecode='2' order by id limit 1)
where testtypeid=(select id from testtype where testtypecode='2' order by id desc limit 1);

update testtypesubjectarea set assessmentid=(select id from assessment where assessmentcode='GL' and testingprogramid=(
	select id from testingprogram where programabbr='S' and activeflag is true and assessmentprogramid=(
	select id from assessmentprogram where abbreviatedname='CPASS'))
	)  
where testtypeid=(select id from testtype where testtypecode='2' order by id limit 1)
and subjectareaid in (select id from subjectarea where subjectareacode in ('GKS','GCTEA')) and activeflag is true;

UPDATE testtypesubjectarea set activeflag=true,
	subjectareaid=(select id from subjectarea where subjectareacode='SELAA' and activeflag is true)
where testtypeid = (select id from testtype where testtypecode='GN' and activeflag is true) 
and subjectareaid=(select id from subjectarea where subjectareacode='D75') and activeflag is false;

UPDATE contentareatesttypesubjectarea set activeflag=true
where testtypesubjectareaid=(select id from testtypesubjectarea
	where testtypeid=(select id from testtype where testtypecode='GN' and activeflag is true) and
	subjectareaid=(select id from subjectarea where subjectareacode='SELAA' and activeflag is true) and activeflag is true);
