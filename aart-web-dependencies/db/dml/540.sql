-- dml/.sql

DO
$BODY$
DECLARE
	caid BIGINT;
	data TEXT[][] := ARRAY[
		['SS', '1'],
		['SS', '2'],
		['SS', '3'],
		['SS', '4'],
		['SS', '5'],
		['SS', '6'],
		['SS', '7'],
		['SS', '8'],
		['SS', '9'],
		['SS', '10'],
		['SS', '11'],
		['SS', '12'],
		['ELA', '3'],
		['ELA', '10'],
		['M', '3'],
		['M', '10']
	];
BEGIN
	FOR i IN array_lower(data, 1) .. array_upper(data, 1) LOOP
		IF NOT EXISTS (
			SELECT 1
			FROM suppressedlevel sl
			INNER JOIN contentarea ca ON sl.contentareaid = ca.id
			INNER JOIN gradecourse gc ON sl.gradecourseid = gc.id AND gc.contentareaid = ca.id
			WHERE sl.activeflag = TRUE AND ca.activeflag = TRUE AND gc.activeflag = TRUE
			AND ca.abbreviatedname = data[i][1]
			AND gc.abbreviatedname = data[i][2]
		) THEN
			SELECT id
			FROM contentarea
			WHERE activeflag = TRUE AND abbreviatedname = data[i][1]
			LIMIT 1
			INTO caid;
			
			INSERT INTO suppressedlevel (id, contentareaid, gradecourseid, activeflag, createddate, createduser, modifieddate, modifieduser)
			VALUES (
				NEXTVAL('suppressedlevel_id_seq'::regclass),
				caid,
				(SELECT id FROM gradecourse WHERE activeflag = TRUE AND contentareaid = caid AND abbreviatedname = data[i][2] LIMIT 1),
				TRUE,
				NOW(),
				(SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
				NOW(),
				(SELECT id FROM aartuser WHERE username = 'cetesysadmin')
			);
		ELSE
			RAISE NOTICE '% - % already found in suppressedlevel...skipping', data[i][1], data[i][2];
		END IF;
	END LOOP;
END;
$BODY$;

--Script changes from changepond
-- US17461 : removing all the "View Performance" permissions - these are all obsolete
Update authorities set activeflag = false where authority in ('PERM_REPORT_PERF_STATE_VIEW','PERM_REPORT_PERF_DISTRICT_VIEW','PERM_REPORT_PERF_SCHOOL_VIEW','PERM_REPORT_PERF_STUDENT_VIEW','PERM_REPORT_PERF_ROSTER_VIEW');

-- US17461 : Changing label of the 2 existing "alternate assessment" reports to include "instructional" test.
Update authorities set displayname = 'View Alt Assess Instructional Student Report' where authority ='VIEW_ALTERNATE_STUDENT_REPORT' and activeflag is true;
Update authorities set displayname = 'View Alt Assess Instructional Roster Report' where authority ='VIEW_ALTERNATE_ROSTER_REPORT' and activeflag is true;

-- Changed Link from "Alternate Student" to "Student Progress"
update category set categoryname = 'Student Progress' where categorycode = 'ALT_ST';

-- US17461 : Added new permissions for DLM Year End Reports
INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_ALT_YEAREND_STD_IND_REP', 'View Alt Assess Yearend Student Individual Report','Reports-Performance Reports',current_timestamp, (Select id from aartuser where username ilike ('cetesysadmin')), 
            true, current_timestamp,(Select id from aartuser where username ilike ('cetesysadmin')));

INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_ALT_YEAREND_STD_BUNDLED_REP', 'View Alt Assess Yearend Student Bundled Report','Reports-Performance Reports',current_timestamp, (Select id from aartuser where username ilike ('cetesysadmin')), 
            true, current_timestamp,(Select id from aartuser where username ilike ('cetesysadmin')));

-- US17548 - permission added for cPass Reports			
INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_CPASS_ASMNT_STUDENT_IND_REP', 'View cPass Assessment Student Individual Report','Reports-Performance Reports',current_timestamp, (Select id from aartuser where username ilike ('cetesysadmin')), 
            true, current_timestamp,(Select id from aartuser where username ilike ('cetesysadmin')));

INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_CPASS_ASMNT_STUDENT_BUN_REP', 'View cPass Assessment Student Bundled Report','Reports-Performance Reports',current_timestamp, (Select id from aartuser where username ilike ('cetesysadmin')), 
            true, current_timestamp,(Select id from aartuser where username ilike ('cetesysadmin')));

INSERT INTO authorities(
             authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_CPASS_ASMNT_SCHOOL_DTL_REP', 'View cPass Assessment School Detail Report','Reports-Performance Reports',current_timestamp, (Select id from aartuser where username ilike ('cetesysadmin')), 
            true, current_timestamp,(Select id from aartuser where username ilike ('cetesysadmin')));

-- Category added for New cPass and DLM reports.
INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
	VALUES ('Student (Individual)','CPASS_GEN_ST','Cpass General Student (Individual)',(select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',current_timestamp,(select id from aartuser where username like 'cetesysadmin'),true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
	VALUES ('Students (Bundled)','CPASS_GEN_ST_ALL','Cpass General Student (Bundled)',(select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',current_timestamp,(select id from aartuser where username like 'cetesysadmin'),true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
			externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
	VALUES ('School Detail','CPASS_GEN_SD','Cpass General School Detail',(select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',current_timestamp,(select id from aartuser where username like 'cetesysadmin'),true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));
	
	
INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Student (Individual)','ALT_ST_IND','DLM General Student (Individual)',(select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',current_timestamp,(select id from aartuser where username like 'cetesysadmin'),true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));

INSERT INTO category(
             categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Students (Bundled)','ALT_ST_ALL','DLM General Student (Bundled)',(select id from categorytype where typecode = 'REPORT_TYPES_UI'),null,'AART',current_timestamp,(select id from aartuser where username like 'cetesysadmin'),true,current_timestamp,(select id from aartuser where username like 'cetesysadmin'));

-- Table reportassessmentprogram updated as per new permissions (cPass and DLM) and reports
update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'ALT_ST_IND'), authorityid = (Select id from authorities where authority = 'VIEW_ALT_YEAREND_STD_IND_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('DLM')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('ELA')) and activeflag is true ;
update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'ALT_ST_IND'), authorityid = (Select id from authorities where authority = 'VIEW_ALT_YEAREND_STD_IND_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('DLM')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('M')) and activeflag is true ;
update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'ALT_ST_IND'), authorityid = (Select id from authorities where authority = 'VIEW_ALT_YEAREND_STD_IND_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('DLM')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('SCI')) and activeflag is true ;
update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'ALT_ST_IND'), authorityid = (Select id from authorities where authority = 'VIEW_ALT_YEAREND_STD_IND_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('DLM')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('SS')) and activeflag is true ;

update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'ALT_ST_ALL'), authorityid = (Select id from authorities where authority = 'VIEW_ALT_YEAREND_STD_BUNDLED_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('DLM')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST_ALL') and activeflag is true ;

update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'CPASS_GEN_ST'), authorityid = (Select id from authorities where authority = 'VIEW_CPASS_ASMNT_STUDENT_IND_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('cPASS')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('GKS')) and activeflag is true ;
update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'CPASS_GEN_ST'), authorityid = (Select id from authorities where authority = 'VIEW_CPASS_ASMNT_STUDENT_IND_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('cPASS')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('AGF&NR')) and activeflag is true ;
update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'CPASS_GEN_ST'), authorityid = (Select id from authorities where authority = 'VIEW_CPASS_ASMNT_STUDENT_IND_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('cPASS')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('MFG')) and activeflag is true ;

update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'CPASS_GEN_ST_ALL'), authorityid = (Select id from authorities where authority = 'VIEW_CPASS_ASMNT_STUDENT_BUN_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('cPASS')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST_ALL') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('GKS')) and activeflag is true ;
update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'CPASS_GEN_ST_ALL'), authorityid = (Select id from authorities where authority = 'VIEW_CPASS_ASMNT_STUDENT_BUN_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('cPASS')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST_ALL') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('MFG')) and activeflag is true ;
update reportassessmentprogram set reporttypeid = (Select id from category where categorycode = 'CPASS_GEN_ST_ALL'), authorityid = (Select id from authorities where authority = 'VIEW_CPASS_ASMNT_STUDENT_BUN_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('cPASS')) and reporttypeid in (Select id from category where categorycode = 'GEN_ST_ALL') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('AGF&NR')) and activeflag is true ;

update reportassessmentprogram set  reporttypeid = (Select id from category where categorycode = 'CPASS_GEN_SD'), authorityid = (Select id from authorities where authority = 'VIEW_CPASS_ASMNT_SCHOOL_DTL_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('cPASS')) and reporttypeid in (Select id from category where categorycode = 'GEN_SD') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('GKS')) and activeflag is true ;
update reportassessmentprogram set  reporttypeid = (Select id from category where categorycode = 'CPASS_GEN_SD'), authorityid = (Select id from authorities where authority = 'VIEW_CPASS_ASMNT_SCHOOL_DTL_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('cPASS')) and reporttypeid in (Select id from category where categorycode = 'GEN_SD') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('AGF&NR')) and activeflag is true ;

update reportassessmentprogram set   reporttypeid = (Select id from category where categorycode = 'CPASS_GEN_SD'), authorityid = (Select id from authorities where authority = 'VIEW_CPASS_ASMNT_SCHOOL_DTL_REP' ) where assessmentprogramid in (Select id from assessmentprogram where abbreviatedname ilike ('cPASS')) and reporttypeid in (Select id from category where categorycode = 'GEN_SD') and subjectid in (Select id from contentarea where abbreviatedname  ilike ('MFG')) and activeflag is true ;


