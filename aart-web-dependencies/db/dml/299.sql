
--US15294 15295
update fieldspecification set mappedname = 'State_ELA_Assess'
where id = (
select id from fieldspecification 
	where mappedname='State_ELA_Assessment' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'State_CTE_Assess'
where id = (
select id from fieldspecification 
	where mappedname='General_CTE_Assessment' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'State_Pathways_Assess'
where id = (
select id from fieldspecification 
	where mappedname='End_of_Pathways_Assessment' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'Math_DLM_Proctor_ID'
where id = (
select id from fieldspecification 
	where mappedname='DLM_Math_Proctor_ID' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 

update fieldspecification set mappedname = 'Math_DLM_Proctor_Name'
where id = (
select id from fieldspecification 
	where mappedname='DLM_Math_Proctor_Name' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'ELA_DLM_Proctor_ID'
where id = (
select id from fieldspecification 
	where mappedname='DLM_ELA_Proctor_ID' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'ELA_DLM_Proctor_Name'
where id = (
select id from fieldspecification 
	where mappedname='DLM_ELA_Proctor_Name' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'Science_DLM_Proctor_ID'
where id = (
select id from fieldspecification 
	where mappedname='DLM_Sci_Proctor_ID' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 

update fieldspecification set mappedname = 'Science_DLM_Proctor_Name'
where id = (
select id from fieldspecification 
	where mappedname='DLM_Sci_Proctor_Name' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'CTE_cPass_Proctor_ID'
where id = (
select id from fieldspecification 
	where mappedname='CPASS_Proctor_ID' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'CTE_cPass_Proctor_Name'
where id = (
select id from fieldspecification 
	where mappedname='CPASS_Proctor_Name' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'State_Hist_Gov_Assess'
where id = (
select id from fieldspecification 
	where mappedname='State_Hist_Gov_Assessment' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 	

update fieldspecification set mappedname = 'State_Science_Assess'
where id = (
select id from fieldspecification 
	where mappedname='State_Sci_Assessment' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	);
	
delete from fieldspecificationsrecordtypes where fieldspecificationid in 
(
select id from fieldspecification where fieldname in ('stateReadingAssess', 'k8StateScienceAssess','hsStateLifeScienceAssess','hsStatePhysicalScienceAssess','k8StateHistoryGovAssess','hsStateHistoryGovWorld','hsStateHistoryGovState')
);

delete from fieldspecification where id in 
(
select id from fieldspecification where fieldname in ('stateReadingAssess', 'k8StateScienceAssess','hsStateLifeScienceAssess','hsStatePhysicalScienceAssess','k8StateHistoryGovAssess','hsStateHistoryGovWorld','hsStateHistoryGovState')
);


delete from fieldspecificationsrecordtypes where fieldspecificationid in 
(
select id from fieldspecification where fieldname in ('groupingMath1','groupingMath2','groupingReading1','groupingReading2','groupingScience1','groupingScience2','groupingHistory1','groupingHistory2','groupingWriting1','groupingWriting2','groupingKelpa1','groupingKelpa2')
);

delete from fieldspecification where id in 
(
select id from fieldspecification where fieldname in ('groupingMath1','groupingMath2','groupingReading1','groupingReading2','groupingScience1','groupingScience2','groupingHistory1','groupingHistory2','groupingWriting1','groupingWriting2','groupingKelpa1','groupingKelpa2')
);

INSERT INTO fieldspecification(
            fieldname,  fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield)
    VALUES ('groupingInd1Math', 60, 
            false, false, null, 'Grouping_Math_1', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false), 
	('groupingInd2Math', 60, 
            false, false, null, 'Grouping_Math_2', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('groupingInd1ELA', 60, 
            false, false, null, 'Grouping_ELA_1', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('groupingInd2ELA', 60, 
            false, false, null, 'Grouping_ELA_2', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),    
	('groupingInd1Sci', 60, 
            false, false, null, 'Grouping_Science_1', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('groupingInd2Sci', 60, 
            false, false, null, 'Grouping_Science_2', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('groupingInd1HistGov', 60, 
            false, false, null, 'Grouping_History_Gov_1', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),  
	('groupingInd2HistGov', 60, 
            false, false, null, 'Grouping_History_Gov_2', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('groupingInd1CTE', 60, 
            false, false, null, 'CTE_Grouping_1', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('groupingInd2CTE', 60, 
            false, false, null, 'CTE_Grouping_2', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),  
	('groupingInd1Pathways', 60, 
            false, false, null, 'Grouping_Pathways_1', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false),
	('groupingInd2Pathways', 60, 
            false, false, null, 'Grouping_Pathways_2', false, 
            now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
            false)                                                            
            ;                                                           

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ((select id from fieldspecification where mappedname='Grouping_Math_1'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='Grouping_Math_2'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='Grouping_ELA_1'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='Grouping_ELA_2'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='Grouping_Science_1'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='Grouping_Science_2'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')), 
	((select id from fieldspecification where mappedname='Grouping_History_Gov_1'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='Grouping_History_Gov_2'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='CTE_Grouping_1'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='CTE_Grouping_2'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='Grouping_Pathways_1'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),    
	((select id from fieldspecification where mappedname='Grouping_Pathways_2'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'));

--304.sql
--DE7784 email was no allowing hyphen(-) because of regex restriction
    
update public.fieldspecification set formatregex ='^[\w~`!#$%^&*_+={};:/?|-]+(\.{0,1}[\w~`!#$%^''&*_+={};:/?|-]+)*@{1}[A-Za-z0-9-]+(\.{0,1}[A-Za-z0-9]+-{0,1})*\.{1}[A-Za-z0-9]{2,}$' where fieldname = 'email';    