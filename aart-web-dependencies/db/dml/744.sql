--dml/744.sql F725 ISMART

--insert an entry into batchjobschedule table for ISMART batch auto enrollment job
INSERT INTO batchjobschedule (jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
SELECT 'ISMART Batch auto job', 'iSmartAutoRunScheduler', 'run', '0 0 21 * * ?', FALSE, 'eapepbp03.prod.east.cete.us'
WHERE NOT EXISTS (
    SELECT 1 FROM batchjobschedule WHERE jobrefname = 'iSmartAutoRunScheduler');
    
--Insert testenrollment method for ISMART
insert into testenrollmentmethod(assessmentprogramid, methodcode, methodname, methodtype)
values((select id from assessmentprogram where activeflag is true and abbreviatedname='I-SMART'), 'FXD', 'Fixed', 'TEM');


--Insert complexitybands for ISMART by copying over from DLM Sci bands
DO
$BODY$
DECLARE
    contentAreaSci TEXT := 'Sci';
    contentAreaIdSci BIGINT := (SELECT id FROM contentarea WHERE abbreviatedname = contentAreaSci);    
    ismartContentAreaId BIGINT := (SELECT id FROM contentarea WHERE abbreviatedname = 'I-SMART Science');
	
   	iSmartAssessmentProgramId BIGINT := (select id from assessmentprogram where activeflag is true and abbreviatedname ='I-SMART');
	dlmAssessmentProgramId BIGINT := (select id from assessmentprogram where activeflag is true and abbreviatedname ='DLM');
BEGIN
    IF EXISTS (
        SELECT 1
        FROM complexityband
        WHERE contentareaid = ismartContentAreaId
			AND assessmentprogramid = iSmartAssessmentProgramId
    ) THEN
        RAISE NOTICE 'Found % complexity bands for ISMART program. Skipping...', contentAreaSci;
    ELSE
        RAISE NOTICE 'Did not find % complexity bands. Copying from % to %...', contentAreaSci, dlmAssessmentProgramId, iSmartAssessmentProgramId;
        
        --id is not auto-generated for this table, so just adjust by 10
        INSERT INTO complexityband (id, bandname, bandcode, minrange, maxrange, contentareaid, assessmentprogramid)
            SELECT id + 100 AS id, bandname, bandcode, minrange, maxrange, ismartContentAreaId as contentareaid, iSmartAssessmentProgramId as assesmentprogramid
            FROM complexityband
            WHERE contentareaid = contentAreaIdSci
				AND assessmentprogramid = dlmAssessmentProgramId;
        
        RAISE NOTICE 'Complete.';
    END IF;
END;
$BODY$;


--insert into orgassessmentprogram table for Maryland, Missouri, NewJersey, NewYork and Oklahoma states with ISMART program
insert into orgassessmentprogram(organizationid,assessmentprogramid,createduser,modifieduser, isdefault, reportyear)
select id as organizationid ,(select id as assessmentprogramid from assessmentprogram where activeflag is true and abbreviatedname='I-SMART') ,
(SELECT id as createduser FROM aartuser WHERE username = 'cetesysadmin'),
(SELECT id as modifieduser FROM aartuser WHERE username = 'cetesysadmin'),
false,
2018 as reportyear
from organization where displayidentifier in('MD','MO','NJ','NY','OK');

insert into orgassessmentprogram(organizationid,assessmentprogramid,createduser,modifieduser, isdefault, reportyear)
select id as organizationid ,(select id as assessmentprogramid from assessmentprogram where activeflag is true and abbreviatedname='I-SMART2') ,
(SELECT id as createduser FROM aartuser WHERE username = 'cetesysadmin'),
(SELECT id as modifieduser FROM aartuser WHERE username = 'cetesysadmin'),
false,
2018 as reportyear
from organization where displayidentifier in('MD','MO','NJ','NY','OK');


