
--338.sql
UPDATE testtypesubjectarea set activeflag=true,
	subjectareaid=(select id from subjectarea where subjectareacode='SELAA' and activeflag is true)
where testtypeid = (select id from testtype where testtypecode='P' and activeflag is true) 
and subjectareaid=(select id from subjectarea where subjectareacode='D75') and activeflag is false;

UPDATE contentareatesttypesubjectarea set activeflag=true
where testtypesubjectareaid=(select id from testtypesubjectarea
	where testtypeid=(select id from testtype where testtypecode='P' and activeflag is true) and
	subjectareaid=(select id from subjectarea where subjectareacode='SELAA' and activeflag is true) and activeflag is true);