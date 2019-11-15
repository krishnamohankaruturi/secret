-- dml/532.sql

-- script changes from change pond
-- Making Existing Entries false; let me know if you wish this to be deleted.
update reportassessmentprogram set activeflag=false;

-- Insert scripts for given combinations.
Select * from reportassessmentprogram_fn('KAP','GEN_ST','ELA','VIEW_GENERAL_STUDENT_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_ST','M','VIEW_GENERAL_STUDENT_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_ST','SS','VIEW_GENERAL_STUDENT_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_ST_ALL','','VIEW_GNRL_STUDENT_RPT_BUNDLED');
Select * from reportassessmentprogram_fn('KAP','GEN_SD','ELA','VIEW_GENERAL_SCHOOL_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_SD','M','VIEW_GENERAL_SCHOOL_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_SD','SS','VIEW_GENERAL_SCHOOL_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_SS','ELA','VIEW_GENERAL_SCHOOL_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_SS','M','VIEW_GENERAL_SCHOOL_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_SS','SS','VIEW_GENERAL_SCHOOL_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_DD','ELA','VIEW_GENERAL_DISTRICT_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_DD','M','VIEW_GENERAL_DISTRICT_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_DD','SS','VIEW_GENERAL_DISTRICT_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_DS','ELA','VIEW_GENERAL_DISTRICT_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_DS','M','VIEW_GENERAL_DISTRICT_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_DS','SS','VIEW_GENERAL_DISTRICT_REPORT');
Select * from reportassessmentprogram_fn('cPass','GEN_ST','GKS','');
Select * from reportassessmentprogram_fn('cPass','GEN_ST','AGF&NR','');
Select * from reportassessmentprogram_fn('cPass','GEN_ST','MFG','');
Select * from reportassessmentprogram_fn('cPass','GEN_ST_ALL','GKS','');
Select * from reportassessmentprogram_fn('cPass','GEN_ST_ALL','AGF&NR','');
Select * from reportassessmentprogram_fn('cPass','GEN_ST_ALL','MFG','');
Select * from reportassessmentprogram_fn('cPass','GEN_SD','GKS','');
Select * from reportassessmentprogram_fn('cPass','GEN_SD','AGF&NR','');
Select * from reportassessmentprogram_fn('cPass','GEN_SD','MFG','');
Select * from reportassessmentprogram_fn('DLM','ALT_ST','ELA','VIEW_ALTERNATE_STUDENT_REPORT');
Select * from reportassessmentprogram_fn('DLM','ALT_ST','M','VIEW_ALTERNATE_STUDENT_REPORT');
Select * from reportassessmentprogram_fn('DLM','ALT_CR','ELA','VIEW_ALTERNATE_ROSTER_REPORT');
Select * from reportassessmentprogram_fn('DLM','ALT_CR','M','VIEW_ALTERNATE_ROSTER_REPORT');
Select * from reportassessmentprogram_fn('DLM','GEN_ST','ELA','');
Select * from reportassessmentprogram_fn('DLM','GEN_ST','M','');
Select * from reportassessmentprogram_fn('DLM','GEN_ST','SCI','');
Select * from reportassessmentprogram_fn('DLM','GEN_ST','SS','');
Select * from reportassessmentprogram_fn('DLM','GEN_ST_ALL','','');

-- Added access for Alaska reports
-- Still have to update ready to view flag and reports set up tab
Select * from reportassessmentprogram_fn('AMP','GEN_ST','ELA','VIEW_GENERAL_STUDENT_REPORT');
Select * from reportassessmentprogram_fn('AMP','GEN_ST','M','VIEW_GENERAL_STUDENT_REPORT');
Select * from reportassessmentprogram_fn('AMP','GEN_ST_ALL','','VIEW_GNRL_STUDENT_RPT_BUNDLED');
Select * from reportassessmentprogram_fn('AMP','GEN_SD','ELA','VIEW_GENERAL_SCHOOL_REPORT');
Select * from reportassessmentprogram_fn('AMP','GEN_SD','M','VIEW_GENERAL_SCHOOL_REPORT');
Select * from reportassessmentprogram_fn('AMP','GEN_SS','ELA','VIEW_GENERAL_SCHOOL_REPORT');
Select * from reportassessmentprogram_fn('AMP','GEN_SS','M','VIEW_GENERAL_SCHOOL_REPORT');
Select * from reportassessmentprogram_fn('AMP','GEN_DD','ELA','VIEW_GENERAL_DISTRICT_REPORT');
Select * from reportassessmentprogram_fn('AMP','GEN_DD','M','VIEW_GENERAL_DISTRICT_REPORT');
Select * from reportassessmentprogram_fn('AMP','GEN_DS','ELA','VIEW_GENERAL_DISTRICT_REPORT');
Select * from reportassessmentprogram_fn('AMP','GEN_DS','M','VIEW_GENERAL_DISTRICT_REPORT');

-- remove permission
UPDATE AUTHORITIES SET activeflag = false where displayname = 'Lock AMP Reports' and authority = 'LOCK_AMP_REPORTS';

-- add new permission
INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('VIEW_REPORT_CONTROL_ACCESS', 'View Reports Control Access', 'Reports-Performance Reports',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));
            

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('EDIT_REPORT_CONTROL_ACCESS', 'Edit Reports Control Access', 'Reports-Performance Reports',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));
            
            
update fieldspecification set rejectifempty = false, rejectifinvalid = false, showerror = false where fieldname= 'comment' and id in(
select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid =(select id from category where categorycode='COMBINED_LEVEL_MAP')
);
