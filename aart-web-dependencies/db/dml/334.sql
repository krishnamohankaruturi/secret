--334.sql
update stimulusvariant set stimuluscontent=replace(stimuluscontent,'alt="undefined"','alt=""') where stimuluscontent like '%alt="undefined"%';

--populate missing grades of cPass batch auto registration
INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='G' and ca.abbreviatedname='BM&A' AND gc.abbreviatedname = 'CompBus';
		
INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='H' and ca.abbreviatedname='BM&A' AND gc.abbreviatedname = 'Fin';	

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser)
select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='I' and ca.abbreviatedname='BM&A' AND gc.abbreviatedname = 'Mktg';