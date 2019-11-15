--US17367: Technical: Prepare Queries to analyze ITI data to figure out any of the issues listed in this US

-- (1) Accessible form was available but not assigned to PNP student
--Tests with Accessibility form/flags not assigned to PNP students
select t.id as EP_TestID, t.externalid as CB_TestId, t.testname as TestName, 
array_to_string(ARRAY(select accessibilityflagcode from testaccessibilityflag where  testid = t.id), ',') as AccessibilityFlags, t.accessibleform, t.createdate as PublishedDate
, tc.id as EP_TestCollectionId, tc.externalid as CB_TestCollectionId, tc.name as TestCollectionName
from test t
	join testaccessibilityflag taf on taf.testid = t.id and t.accessibleform is true
	join testcollectionstests tcts on tcts.testid = t.id
	join testcollection tc on tc.id = tcts.testcollectionid 
where t.status = 64
	and t.qccomplete is true
	and t.id not in (
		-- Tests used in the ITI test sessions that are assigned to PNP students
		select distinct st.testid
		from studentstests st 
			join testsession ts on ts.id = st.testsessionid 
			join student s on s.id = st.studentid 
		where s.profilestatus ilike 'custom' 
			and st.testsessionid in (select id from testsession where source = 'ITI' and schoolyear = 2016)
		order by st.testid asc
	)
group by t.id, tc.id
order by t.id asc;

-- (2) Accessible form assigned to Non-PNP student
--Tests with Accessibility form/flags assigned to non-PNP students (Must not happen; Accessibility form/flags is for PNP students)
select t.id as EP_TestID, t.externalid as CB_TestId, t.testname as TestName, 
array_to_string(ARRAY(select accessibilityflagcode from testaccessibilityflag where  testid = t.id), ',') as AccessibilityFlags, t.accessibleform, t.createdate as PublishedDate
, tc.id as EP_TestCollectionId, tc.externalid as CB_TestCollectionId, tc.name as TestCollectionName
from test t
	join testaccessibilityflag taf on taf.testid = t.id and t.accessibleform is true
	join testcollectionstests tcts on tcts.testid = t.id
	join testcollection tc on tc.id = tcts.testcollectionid 
where t.status = 64
	and t.qccomplete is true
	and t.id in (
		-- Tests used in the ITI test sessions that are assigned to non-PNP students
		select distinct st.testid
		from studentstests st 
			join testsession ts on ts.id = st.testsessionid 
			join student s on s.id = st.studentid 
		where s.profilestatus not ilike '%custom%' 
			and st.testsessionid in (select id from testsession where source = 'ITI' and schoolyear = 2016)
		order by st.testid asc
	)
group by t.id, tc.id 
order by t.id asc;	


-- (3) Tests are not assigned based on Sensitivity tags
select distinct st.testid, tstv.testletid,  tlst.sensitivitytagid
from studentstests st 
	join ititestsessionhistory itsh ON st.testsessionid = itsh.testsessionid 
	join ititestsessionsensitivitytags itsst ON itsst.ititestsessionhistoryid = itsh.id 
	join testsection ts on ts.testid = st.testid
	join testsectionstaskvariants tstv on tstv.testsectionid = ts.id
	left join testletsensitivitytag tlst on tlst.testletid = tstv.testletid
where st.testsessionid in (select id from testsession where source = 'ITI' and schoolyear = 2016)
	and tlst.sensitivitytagid is null;
	
	
	
	
	