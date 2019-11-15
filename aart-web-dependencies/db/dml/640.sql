--dml/640.sql

--F447 remove exclusions for all reports for student and student individual for DLM (all states) and CPASS (all states but KS)

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('DLM')
 order by ap.abbreviatedname, org.displayidentifier)

DELETE FROM groupauthoritiesexclusion 
USING orgsToExcludeList
WHERE groupid = (select id from groups where groupcode = 'TEA') and authorityid = (select id from authorities where authority ilike 'VIEW_ALL_STUDENT_REPORTS')  
	 and assessmentprogramid = orgsToExcludeList.apid and stateid = orgsToExcludeList.orgid;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('DLM')
 order by ap.abbreviatedname, org.displayidentifier)

DELETE FROM groupauthoritiesexclusion 
USING orgsToExcludeList
WHERE groupid = (select id from groups where groupcode = 'TEA') and authorityid = (select id from authorities where authority ilike 'VIEW_ALT_YEAREND_STD_IND_REP')  
	 and assessmentprogramid = orgsToExcludeList.apid and stateid = orgsToExcludeList.orgid;

WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2 and org.id != 51
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('CPASS')
 order by ap.abbreviatedname, org.displayidentifier)

DELETE FROM groupauthoritiesexclusion 
USING orgsToExcludeList
WHERE groupid = (select id from groups where groupcode = 'TEA') and authorityid = (select id from authorities where authority ilike 'VIEW_ALL_STUDENT_REPORTS')  
	 and assessmentprogramid = orgsToExcludeList.apid and stateid = orgsToExcludeList.orgid;
	 
WITH orgsToExcludeList AS (select ap.id as apid, org.id as orgid
 from orgassessmentprogram oa
 join organization org ON org.id = oa.organizationid and org.activeflag is true and org.contractingorganization is true and org.organizationtypeid = 2 and org.id != 51
 join assessmentprogram ap ON ap.id = oa.assessmentprogramid and ap.activeflag is true
 where oa.activeflag is true and ap.abbreviatedname in ('CPASS')
 order by ap.abbreviatedname, org.displayidentifier)

DELETE FROM groupauthoritiesexclusion 
USING orgsToExcludeList
WHERE groupid = (select id from groups where groupcode = 'TEA') and authorityid = (select id from authorities where authority ilike 'VIEW_CPASS_ASMNT_STUDENT_IND_REP')  
	 and assessmentprogramid = orgsToExcludeList.apid and stateid = orgsToExcludeList.orgid;
	 
-- prepopulate the values before moving it self.

update batchupload set assessmentprogramname=(select abbreviatedname from assessmentprogram where id = assessmentprogramid);

update batchupload set contentareaname=(select name from contentarea where id = contentareaid);

update batchupload set createduserdisplayname=(select CASE
        WHEN (au.displayname IS NULL OR au.displayname = '')
          THEN (au.firstname || ' ' || au.surname)
        ELSE au.displayname end from aartuser au where id = createduser);

update batchupload set uploadtype = (select categoryname from category where id = uploadtypeid);

INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    SELECT 'All Data Extract Scheduler', 'allExtractScheduler', 'startAllReportExtractProcess', '*/30 * * * * ?', FALSE, 'localhost'
    where not exists(select id from batchjobschedule where jobrefname='allExtractScheduler' and initmethod='startAllReportExtractProcess');

INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    SELECT 'Async Data Extract Scheduler', 'asyncExtractScheduler', 'startAsyncReportExtractProcess', '*/30 * * * * ?', FALSE, 'localhost'
  where not exists(select id from batchjobschedule where jobrefname='asyncExtractScheduler' and initmethod='startAsyncReportExtractProcess');


INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    SELECT 'School Extract Scheduler', 'schoolExtractScheduler', 'startSchoolReportExtractProcess', '*/30 * * * * ?', FALSE, 'localhost'
    where not exists(select id from batchjobschedule where jobrefname='schoolExtractScheduler' and initmethod='startSchoolReportExtractProcess');
    
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    SELECT 'District Data Extract Scheduler', 'districtExtractScheduler', 'startDistrictReportExtractProcess', '*/30 * * * * ?', FALSE, 'localhost'
    where not exists(select id from batchjobschedule where jobrefname='districtExtractScheduler' and initmethod='startDistrictReportExtractProcess');
    
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    SELECT 'State Data Extract Scheduler', 'stateExtractScheduler', 'startStateReportExtractProcess', '*/30 * * * * ?', FALSE, 'localhost'
  where not exists(select id from batchjobschedule where jobrefname='stateExtractScheduler' and initmethod='startStateReportExtractProcess');

  --F396 changes

--Start SC code changes

--SC-01
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13807 and ksdecode = '01' and activeflag is true);
--SC-04
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13810 and ksdecode = '04' and activeflag is true);
--SC-05
UPDATE statespecialcircumstance set reportscore = true where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13811 and ksdecode = '05' and activeflag is true);
--SC-07
UPDATE statespecialcircumstance set reportscore = true where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13813 and ksdecode = '07' and activeflag is true);
--SC-08
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13814 and ksdecode = '08' and activeflag is true);
--SC-16
UPDATE statespecialcircumstance set reportscore = true where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13815 and ksdecode = '16' and activeflag is true);
--SC-20
UPDATE statespecialcircumstance set reportscore = true where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13816 and ksdecode = '20' and activeflag is true);
--SC-24
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13817 and ksdecode = '24' and activeflag is true);
--SC-25
UPDATE statespecialcircumstance set reportscore = true where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13818 and ksdecode = '25' and activeflag is true);
--SC-26
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13819 and ksdecode = '26' and activeflag is true);
--SC-27
UPDATE statespecialcircumstance set reportscore = true where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13820 and ksdecode = '27' and activeflag is true);
--SC-28
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13821 and ksdecode = '28' and activeflag is true);
--SC-31
UPDATE statespecialcircumstance set reportscore = true where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13824 and ksdecode = '31' and activeflag is true);
--SC-32
UPDATE statespecialcircumstance set reportscore = true where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13825 and ksdecode = '32' and activeflag is true);
--SC-34
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13827 and ksdecode = '34' and activeflag is true);
--SC-36
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13828 and ksdecode = '36' and activeflag is true);
--SC-37
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13829 and ksdecode = '37' and activeflag is true);
--SC-41
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13832 and ksdecode = '41' and activeflag is true);
--SC-98
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 13836 and ksdecode = '98' and activeflag is true);
--SC-39
UPDATE statespecialcircumstance set reportscore = false where stateid = 51 and activeflag is true and specialcircumstanceid = (select id from specialcircumstance where cedscode = 9999 and ksdecode = '39' and activeflag is true);

-- End SC code changes

--Changes for Rawtoscalescore and testcutscore upload files to support K-ELPA testing
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
	('domain', null, null, null, null, false,
	 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'String'); 

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
	((select id from fieldspecification where fieldname = 'domain' and mappedname is null
	), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'domain'
	);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
	((select id from fieldspecification where fieldname = 'domain' and mappedname is null
	), (select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
	 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'domain'
	);
	
update subscoresmissingstages set schoolyear = 2016 where schoolyear is null;