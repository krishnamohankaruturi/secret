--dml/727.sql

----F749_DML ---

-- To update leveldescription content for KELPA Student Report

    update leveldescription set leveldescription = 'Students are proficient when they attain a level of English language skill necessary to independently produce, interpret, collaborate on, and succeed in grade-level content-related academic tasks in English. This is indicated by attaining performance levels of Early Advanced or higher in all domains.' 
    where schoolyear = (SELECT extract(year FROM CURRENT_DATE)) and assessmentprogramid = (select id from assessmentprogram  where abbreviatedname = 'KELPA2' limit 1) and level = 3 
    and testingprogramid = (select id from public.testingProgram where assessmentprogramid = (select id from assessmentprogram  where abbreviatedname = 'KELPA2' limit 1) and programabbr in('S') and activeflag is true);
    
-- F680 populate report type for previous year reports    
--DLM
-------
update externalstudentreports set reporttype = 'ALT_ST_IND', modifieddate = now() where schoolyear = 2016 and assessmentprogramid = 3 and filepath like '/reports/external/DLM/2016/ISR%';  --count(173892)


update organizationreportdetails set reporttype = 'ALT_ST_ALL', modifieddate = now() where assessmentprogramid = 3 and schoolyear = 2016 and detailedreportpath like '/reports/external/DLM/2016/SB%'; --28584


--CPASS
------
update externalstudentreports set reporttype = 'CPASS_GEN_ST', modifieddate = now() where schoolyear = 2016 and assessmentprogramid = 11; -- count(1051)

update organizationreportdetails set reporttype = 'CPASS_GEN_SD', modifieddate = now() where assessmentprogramid = 11 and schoolyear = 2016 and detailedreportpath like '/reports/external/CPASS/2016/SD/%';--28

update organizationreportdetails set reporttype = 'CPASS_GEN_ST_ALL', modifieddate = now() where assessmentprogramid = 11 and schoolyear = 2016 and detailedreportpath like '/reports/external/CPASS/2016/SB/%';--74


--KAP
------

--2016
update organizationreportdetails set reporttype = 'GEN_DS', modifieddate = now() where assessmentprogramid = 12 and schoolyear = 2016 and detailedreportpath like '/reports/2016/DS%'; --913

update organizationreportdetails set reporttype = 'GEN_SS', modifieddate = now() where assessmentprogramid = 12 and schoolyear = 2016 and detailedreportpath like '/reports/2016/SS%';--3825

update organizationreportdetails set reporttype = 'GEN_ST_ALL', modifieddate = now() where assessmentprogramid = 12 and schoolyear = 2016 and detailedreportpath like '/reports/2016/SB%'; --6170


--2015
update organizationreportdetails set reporttype = 'GEN_DS', modifieddate = now() where assessmentprogramid = 12 and schoolyear = 2015 and detailedreportpath like '/reports/DS%'; --918

update organizationreportdetails set reporttype = 'GEN_SS', modifieddate = now() where assessmentprogramid = 12 and schoolyear = 2015 and detailedreportpath like '/reports/SS%'; --4278

update organizationreportdetails set reporttype = 'GEN_ST_ALL', modifieddate = now() where assessmentprogramid = 12 and schoolyear = 2015 and detailedreportpath like '/reports/SB%'; --6205   
    
    