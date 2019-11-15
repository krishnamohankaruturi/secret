
INSERT INTO fieldspecification(
            fieldname, fieldlength, 
            rejectifempty, rejectifinvalid, allowablevalues, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield)            
    VALUES ('stateSciAssessment',  1, 
            false, true, '{'''',0,2,3,N,C}', 'State_Sci_Assessment', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('stateHistGovAssessment', 1, 
            false, true, '{'''',0,2,C}', 'State_Hist_Gov_Assessment', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false);

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ((select id from fieldspecification where mappedname='State_Sci_Assessment'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='State_Hist_Gov_Assessment'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'));     

update fieldspecification set allowablevalues = '{2,3,A,BR,B,D,E,F,G,GN,H,I,L,P,R}'
where id = (
select id from fieldspecification 
	where fieldname='testType' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='TEC_RECORD_TYPE'))
	);	

update testtype set activeflag=true where id in (
	select id from testtype where testtypecode in ('N','C') and activeflag=false);
	
    INSERT INTO subjectarea(subjectareacode, subjectareaname, createduser, modifieduser, createdate, modifieddate)
    VALUES ('SSCIA', 'State Science Assessment', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
	   ('SHISGOVA', 'State History/Gov Assessment', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());  

insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='2' and activeflag=true), (select id from subjectarea where subjectareacode='SSCIA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='3' and activeflag=true), (select id from subjectarea where subjectareacode='SSCIA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='2' and activeflag=true), (select id from subjectarea where subjectareacode='SHISGOVA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='3' and activeflag=true), (select id from subjectarea where subjectareacode='SHISGOVA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='Sci') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='SSCIA'  and activeflag=true)),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='Sci') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='3' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='SSCIA' and activeflag=true)),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='SS') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='SHISGOVA'  and activeflag=true)),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='SS') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='3' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='SHISGOVA' and activeflag=true)),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());	