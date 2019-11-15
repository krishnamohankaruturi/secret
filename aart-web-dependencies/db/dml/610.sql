--610.sql
--F118
INSERT INTO authorities(
            authority, displayname, objecttype, createddate, 
            createduser, activeflag, modifieddate, modifieduser)
    VALUES ( 'VIEW_KELPA_ELP_STUDENT_SCORE', 'View K-ELPA Eng Language Proficincy Students Score', 'Reports-Performance Reports', current_timestamp,
    	(Select id from aartuser where username ilike 'cetesysadmin'), true, current_timestamp,(Select id from aartuser where username ilike 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ( 'Students Score File', 'KELPA_ELP_STUDENT_SCORE_FILE','View K-ELPA Englisg Language Proficiency Students Score File',(Select id from categorytype where typecode = 'REPORT_TYPES_UI'), 
            null, 'AART_ORIG_CODE', current_timestamp,(Select id from aartuser where username ilike 'cetesysadmin'), true, 
            current_timestamp, (Select id from aartuser where username ilike 'cetesysadmin'));

SELECT reportassessmentprogram_fn('K-ELPA',
    'KELPA_ELP_STUDENT_SCORE_FILE',
    'ELP',
    'VIEW_KELPA_ELP_STUDENT_SCORE'
);