
--Student individual
Select * from reportassessmentprogram_fn('CPASS','CPASS_GEN_ST','Arch&Const','VIEW_CPASS_ASMNT_STUDENT_IND_REP');

Select * from reportassessmentprogram_fn('CPASS','CPASS_GEN_ST','BM&A','VIEW_CPASS_ASMNT_STUDENT_IND_REP');

--Student bundled
Select * from reportassessmentprogram_fn('CPASS','CPASS_GEN_ST_ALL','Arch&Const','VIEW_CPASS_ASMNT_STUDENT_BUN_REP');

Select * from reportassessmentprogram_fn('CPASS','CPASS_GEN_ST_ALL','BM&A','VIEW_CPASS_ASMNT_STUDENT_BUN_REP');

--School details
Select * from reportassessmentprogram_fn('CPASS','CPASS_GEN_SD','Arch&Const','VIEW_CPASS_ASMNT_SCHOOL_DTL_REP');

Select * from reportassessmentprogram_fn('CPASS','CPASS_GEN_SD','BM&A','VIEW_CPASS_ASMNT_SCHOOL_DTL_REP');

--Year over year reports
Select * from reportassessmentprogram_fn('CPASS','ALL_ST_RPT_FOR_ST','Arch&Const','VIEW_ALL_STUDENT_REPORTS');

Select * from reportassessmentprogram_fn('CPASS','ALL_ST_RPT_FOR_ST','BM&A','VIEW_ALL_STUDENT_REPORTS');

--Set ready to flag to true
update reportassessmentprogram set readytoview = true where assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'CPASS' and activeflag is true) and subjectid in( (select id from contentarea where abbreviatedname = 'Arch&Const' and activeflag is true), (select id from contentarea where abbreviatedname = 'BM&A' and activeflag is true));