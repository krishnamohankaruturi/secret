INSERT INTO fieldspecification(
            fieldname,  fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield)
    VALUES ('dlmMathProctorId',  30, 
            false, true, '^[a-zA-Z0-9]*$', 'DLM_Math_Proctor_ID', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
     ('dlmMathProctorName', 100, 
            false, true, null, 'DLM_Math_Proctor_Name', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),            
     ('dlmELAProctorId',  30, 
            false, true, '^[a-zA-Z0-9]*$', 'DLM_ELA_Proctor_ID', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
     ('dlmELAProctorName', 100, 
            false, true, null, 'DLM_ELA_Proctor_Name', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
     ('dlmSciProctorId',  30, 
            false, true, '^[a-zA-Z0-9]*$', 'DLM_Sci_Proctor_ID', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('dlmSciProctorName', 100, 
            false, true, null, 'DLM_Sci_Proctor_Name', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('cpassProctorId',  30, 
            false, true, '^[a-zA-Z0-9]*$', 'CPASS_Proctor_ID', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('cpassProctorName', 100, 
            false, true, null, 'CPASS_Proctor_Name', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false);  

INSERT INTO fieldspecification(
            fieldname, fieldlength, 
            rejectifempty, rejectifinvalid, allowablevalues, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield)            
    VALUES ('stateELAAssessment',  1, 
            false, true, '{'''',0,2,3,C,N}', 'State_ELA_Assessment', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('generalCTEAssessment', 1, 
            false, true, '{'''',0,2,C}', 'General_CTE_Assessment', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('endOfPathwaysAssessment', 1, 
            false, true, '{'''',A,B,D,E,F,G,H,I,C}', 'End_of_Pathways_Assessment', true, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false);                                                           

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ((select id from fieldspecification where mappedname='State_ELA_Assessment'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='General_CTE_Assessment'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='End_of_Pathways_Assessment'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='DLM_Math_Proctor_ID'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='DLM_Math_Proctor_Name'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='DLM_ELA_Proctor_ID'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')), 
	((select id from fieldspecification where mappedname='DLM_ELA_Proctor_Name'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='DLM_Sci_Proctor_ID'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='DLM_Sci_Proctor_Name'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='CPASS_Proctor_ID'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='CPASS_Proctor_Name'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'));
    

update subjectarea set activeflag=true;

    INSERT INTO subjectarea(subjectareacode, subjectareaname, createduser, modifieduser, createdate, modifieddate)
    VALUES ('GCTEA', 'General CTE Assessment', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
	   ('SELAA', 'State ELA Assessment', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
	   ('EOPA', 'End of Pathways Assessment', (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());        

insert into testtypesubjectarea (testtypeid, subjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from testtype where testtypecode='2' and activeflag=true), (select id from subjectarea where subjectareacode='GCTEA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),   
((select id from testtype where testtypecode='2' and activeflag=true), (select id from subjectarea where subjectareacode='SELAA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='3' and activeflag=true), (select id from subjectarea where subjectareacode='SELAA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='A' and activeflag=true), (select id from subjectarea where subjectareacode='EOPA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='B' and activeflag=true), (select id from subjectarea where subjectareacode='EOPA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='D' and activeflag=true), (select id from subjectarea where subjectareacode='EOPA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='E' and activeflag=true), (select id from subjectarea where subjectareacode='EOPA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='G' and activeflag=true), (select id from subjectarea where subjectareacode='EOPA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='H' and activeflag=true), (select id from subjectarea where subjectareacode='EOPA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='I' and activeflag=true), (select id from subjectarea where subjectareacode='EOPA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now()),
((select id from testtype where testtypecode='F' and activeflag=true), (select id from subjectarea where subjectareacode='EOPA'), (Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='M') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2') and subjectareaid=(select id from subjectarea where subjectareacode='GCTEA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='ELA') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2') and subjectareaid=(select id from subjectarea where subjectareacode='GCTEA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='M') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2') and subjectareaid=(select id from subjectarea where subjectareacode='SELAA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='ELA') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2') and subjectareaid=(select id from subjectarea where subjectareacode='SELAA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='M') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='3') and subjectareaid=(select id from subjectarea where subjectareacode='SELAA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='ELA') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='3') and subjectareaid=(select id from subjectarea where subjectareacode='SELAA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='AgF&NR' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='A' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='EOPA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='AgF&NR' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='B' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='EOPA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='AgF&NR' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='D' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='EOPA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='Mfg' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='E' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='EOPA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='Arch&Const' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='F' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='EOPA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='BM&A' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='G' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='EOPA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='BM&A' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='H' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='EOPA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='BM&A' and activeflag=true) , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='I' and activeflag=true) and subjectareaid=(select id from subjectarea where subjectareacode='EOPA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());