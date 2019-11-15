-- US15088
update fieldspecification set allowablevalues = '{'''',M,ELA,GKS,AgF&NR,BM&A,Arch&Const,Mfg}', fieldlength=10
where id = (
select id from fieldspecification 
	where fieldname='subject' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE'))
	);
	
update testtype set activeflag=true;
update testtype set testtypecode='BR' where testtypecode='B';	

--inactivate these test types
update testtype set activeflag=false where id in (select id from testtype where testtypecode in ('A','D', 'F', 'G', 'H', 'P', 'R'));
  
--create inactivates test types with new descriptions
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('A', 'Comprehensive Agriculture Assessment', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('D', 'Plant Systems Assessment', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('F', 'Design and Pre Construction Assessment', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('G', 'Comprehensive Business Assessment', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('H', 'Finance Assessment', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('P', 'Paper/Pencil', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('R', 'General - Read/Aloud', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

-- new test types
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('B', 'Animal Systems Assessment', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('E', 'Manufacturing Production Assessment', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('GN', 'General', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
insert into testtype ( testtypecode, testtypename, activeflag, assessmentid, createduser, modifieduser, createdate, modifieddate)
values ('I', 'Marketing Assessment', true, (select id from assessment where  assessmentname = 'General' and testingprogramid = (select id from testingprogram where programabbr = 'S' and assessmentprogramid = (select id from assessmentprogram where programname='KAP'))), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());


update testtype set activeflag=false where id not in (
	select id from testtype where testtypecode in ('2','3','A','BR','B','D','E','F','G','GN','H','I','L','P','R') and activeflag=true);

	
INSERT INTO subjectarea(subjectareacode, subjectareaname, createduser, modifieduser, createdate, modifieddate)
    VALUES ('GKS', 'General Knowledge and Skills', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
INSERT INTO subjectarea(subjectareacode, subjectareaname, createduser, modifieduser, createdate, modifieddate)
    VALUES ('AgF&NR', 'Agriculture, Food, and Natural Resources', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
INSERT INTO subjectarea(subjectareacode, subjectareaname, createduser, modifieduser, createdate, modifieddate)
    VALUES ('BM&A', 'Business Management and Administration', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
INSERT INTO subjectarea(subjectareacode, subjectareaname, createduser, modifieduser, createdate, modifieddate)
    VALUES ('Arch&Const', 'Architecture and Construction', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
INSERT INTO subjectarea(subjectareacode, subjectareaname, createduser, modifieduser, createdate, modifieddate)
    VALUES ('Mfg', 'Manufacturing', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
    
update testtypesubjectarea set activeflag=true;
update testtypesubjectarea set activeflag=false
	where testtypeid in (select id from testtype where activeflag=false);
	
-- 2 - GKS	
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='2' and activeflag=true), (select id from subjectarea where subjectareacode='GKS'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- A - AgF&NR
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='A' and activeflag=true), (select id from subjectarea where subjectareacode='AgF&NR'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- B - AgF&NR
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='B' and activeflag=true), (select id from subjectarea where subjectareacode='AgF&NR'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- D - AgF&NR
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='D' and activeflag=true), (select id from subjectarea where subjectareacode='AgF&NR'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- E - Mfg
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='E' and activeflag=true), (select id from subjectarea where subjectareacode='Mfg'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- F - Arch&Const
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='F' and activeflag=true), (select id from subjectarea where subjectareacode='Arch&Const'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- G - BM&A
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='G' and activeflag=true), (select id from subjectarea where subjectareacode='BM&A'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- GN - D74 M
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='GN' and activeflag=true), (select id from subjectarea where subjectareacode='D74'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- GN - D75 ELA
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='GN' and activeflag=true), (select id from subjectarea where subjectareacode='D75'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- H - BM&A
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='H' and activeflag=true), (select id from subjectarea where subjectareacode='BM&A'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- I - BM&A
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='I' and activeflag=true), (select id from subjectarea where subjectareacode='BM&A'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- P - D74 M
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='P' and activeflag=true), (select id from subjectarea where subjectareacode='D74'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- P - D75 ELA
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='P' and activeflag=true), (select id from subjectarea where subjectareacode='D75'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
-- R - D74 M
insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='R' and activeflag=true), (select id from subjectarea where subjectareacode='D74'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());


update contentareatesttypesubjectarea set activeflag=true;
update contentareatesttypesubjectarea set activeflag=false
	where testtypesubjectareaid in (select id from testtypesubjectarea where activeflag=false);
	
insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='GKS' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='GKS')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='AgF&NR' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='A' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='AgF&NR')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='AgF&NR' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='B' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='AgF&NR')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='AgF&NR' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='D' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='AgF&NR')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='Mfg' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='E' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='Mfg')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='Arch&Const' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='F' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='Arch&Const')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='BM&A' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='G' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='BM&A')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='M' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='GN' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='D74')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='ELA' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='GN' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='D75')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='BM&A' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='H' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='BM&A')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='BM&A' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='I' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='BM&A')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='M' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='P' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='D74')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='ELA' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='P' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='D75')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='M' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='R' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='D74')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());
	