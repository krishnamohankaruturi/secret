INSERT INTO orgassessmentprogram (organizationid, assessmentprogramid) (SELECT (SELECT id FROM organization), (SELECT id FROM assessmentprogram WHERE programname = 'CPASS')
    where exists (select 1 from assessmentprogram where programname = 'CPASS')) except (select organizationid, assessmentprogramid from orgassessmentprogram);
INSERT INTO orgassessmentprogram (organizationid, assessmentprogramid) (SELECT (SELECT id FROM organization), (SELECT id from assessmentprogram where programname = 'Formative')
    where exists (select 1 from assessmentprogram where programname = 'Formative')) except (select organizationid, assessmentprogramid from orgassessmentprogram);
