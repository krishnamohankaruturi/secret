
--Batch cPass Grades matching
INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser) 
	select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='B' and ca.abbreviatedname='AgF&NR'AND gc.abbreviatedname = 'Asys';
	
INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser) 
	select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='A' and ca.abbreviatedname='AgF&NR'AND gc.abbreviatedname = 'CompAg';
	
INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser) 
	select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='F' and ca.abbreviatedname='Arch&Const' AND gc.abbreviatedname = 'D&PreCon';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser) 
	select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='2' and ca.abbreviatedname='GKS' AND gc.abbreviatedname = 'Comp';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser) 
	select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='E' and ca.abbreviatedname='Mfg' AND gc.abbreviatedname = 'Prod';

INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser) 
	select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='D' and ca.abbreviatedname='AgF&NR' AND gc.abbreviatedname = 'Psys';
	
--AMP
INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser) 
	select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='GN' and ca.abbreviatedname='ELA' AND gc.abbreviatedname in ('3','4','5','6','7','8','9','10');
	
INSERT INTO gradecontentareatesttypesubjectarea(contentareatesttypesubjectareaid, gradecourseid, createduser, createdate, modifieddate, modifieduser) 
	select distinct cttsa.id,gc.id,(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin') 
	from contentareatesttypesubjectarea cttsa
	   inner join testtypesubjectarea ttsa on cttsa.testtypesubjectareaid=ttsa.id
	   inner join testtype tt on tt.id = ttsa.testtypeid
	   inner join contentarea ca on ca.id=cttsa.contentareaid
	   inner join gradecourse gc on ca.id=gc.contentareaid
	where tt.testtypecode='GN' and ca.abbreviatedname='M' AND gc.abbreviatedname in ('3','4','5','6','7','8','9','10');
	
