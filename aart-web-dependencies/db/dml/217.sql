--217.sq;

update contentareatesttypesubjectarea set contentareaid=(select id from contentarea where abbreviatedname='Sci')
	where testtypesubjectareaid in (select ttsa.id from testtype tt 
					inner join testtypesubjectarea ttsa on tt.id=ttsa.testtypeid
					inner join subjectarea sa on sa.id=ttsa.subjectareaid 
					where testtypecode='2' and subjectareacode in ('D76','D77','D78'));

update contentareatesttypesubjectarea set contentareaid=(select id from contentarea where abbreviatedname='SS')
	where testtypesubjectareaid in (select ttsa.id from testtype tt 
					inner join testtypesubjectarea ttsa on tt.id=ttsa.testtypeid
					inner join subjectarea sa on sa.id=ttsa.subjectareaid 
					where testtypecode='2' and subjectareacode in ('D79','D80','D81'));

update contentareatesttypesubjectarea set contentareaid=(select id from contentarea where abbreviatedname='Sci')
	where contentareaid=(select id from contentarea where abbreviatedname='SCI');
	
update roster set statesubjectareaid=(select id from contentarea where abbreviatedname='Sci')
	where statesubjectareaid=(select id from contentarea where abbreviatedname='SCI');
	
update stimulusvariantcontentarea set contentareaid=(select id from contentarea where abbreviatedname='Sci')
	where contentareaid=(select id from contentarea where abbreviatedname='SCI');
	
update taskvariant set contentareaid=(select id from contentarea where abbreviatedname='Sci')
	where contentareaid=(select id from contentarea where abbreviatedname='SCI');

update testcollection set contentareaid=(select id from contentarea where abbreviatedname='Sci')
	where contentareaid=(select id from contentarea where abbreviatedname='SCI');
	
update test set contentareaid=(select id from contentarea where abbreviatedname='Sci')
	where contentareaid=(select id from contentarea where abbreviatedname='SCI');
	
update testlet set contentareaid=(select id from contentarea where abbreviatedname='Sci')
	where contentareaid=(select id from contentarea where abbreviatedname='SCI');

update contentframework set contentareaid=(select id from contentarea where abbreviatedname='Sci')
	where contentareaid=(select id from contentarea where abbreviatedname='SCI');
	
update gradecourse set contentareaid=(select id from contentarea where abbreviatedname='Sci')
	where contentareaid=(select id from contentarea where abbreviatedname='SCI');
	
delete from contentarea where abbreviatedname='SCI';

update contentareatesttypesubjectarea set contentareaid=(select id from contentarea where abbreviatedname='M')
	where contentareaid=(select id from contentarea where abbreviatedname='Mathematics');
	
update roster set statesubjectareaid=(select id from contentarea where abbreviatedname='M')
	where statesubjectareaid=(select id from contentarea where abbreviatedname='Mathematics');
	
update stimulusvariantcontentarea set contentareaid=(select id from contentarea where abbreviatedname='M')
	where contentareaid=(select id from contentarea where abbreviatedname='Mathematics');
	
update taskvariant set contentareaid=(select id from contentarea where abbreviatedname='M')
	where contentareaid=(select id from contentarea where abbreviatedname='Mathematics');

update testcollection set contentareaid=(select id from contentarea where abbreviatedname='M')
	where contentareaid=(select id from contentarea where abbreviatedname='Mathematics');
	
update test set contentareaid=(select id from contentarea where abbreviatedname='M')
	where contentareaid=(select id from contentarea where abbreviatedname='Mathematics');
	
update testlet set contentareaid=(select id from contentarea where abbreviatedname='M')
	where contentareaid=(select id from contentarea where abbreviatedname='Mathematics');

update contentframework set contentareaid=(select id from contentarea where abbreviatedname='M')
	where contentareaid=(select id from contentarea where abbreviatedname='Mathematics');
	
--update gradecourse set contentareaid=(select id from contentarea where abbreviatedname='M')
--	where contentareaid=(select id from contentarea where abbreviatedname='Mathematics');
	
--delete from contentarea where abbreviatedname='Mathematics';