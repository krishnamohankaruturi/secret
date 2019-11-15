-- 627 DML
--US19354: DLM Provide Research Survey Auto Enrollment
INSERT INTO testenrollmentmethod(assessmentprogramid, methodcode, methodname, methodtype)
    VALUES((SELECT id FROM assessmentprogram where abbreviatedname='DLM'), 'RESEARCHSURVEY', 'Research Survey', 'TEM');

INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled,  allowedserver)
    VALUES ('DLM Student tracker research survey', 'dlmResearchSurveySchedular', 'run', '0 0 3 * * ?', false, 'ep1.prodku.cete.us');

    