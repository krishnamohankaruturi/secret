--dml/488.sql US16879, US16900 KAP adaptive enrollments

--production epbp1
INSERT INTO  batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver) 
	VALUES ('KAP Adaptive Scheduler','kapAdaptiveRunScheduler','run','* */15 * * * ?', FALSE,'epbp1.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('KAP Summative Scheduler', 'kapSummativeRunScheduler', 'run', '0 20 * * * ?', FALSE, 'epbp1.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('Upload Scheduler', 'batchUploadRunScheduler', 'startBatchUploadProcess', '*/5 * * * * ?', TRUE, 'epbp1.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('Report Upload Scheduler', 'reportBatchUploadRunScheduler', 'startBatchUploadProcess', '*/5 * * * * ?', FALSE, 'epbp1.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('SIF Service Scheduler', 'xmlBatchUploadRunScheduler', 'startBatchUploadProcess', '*/5 * * * * ?', TRUE, 'epbp1.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('Data Extract Scheduler', 'reportExtractScheduler', 'startReportExtractProcess', '*/5 * * * * ?', TRUE, 'epbp1.prodku.cete.us');

--production epbp2
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('TASC Process Scheduler', 'batchTASCProcessJobRunScheduler', 'run', '0 0/1 * * * ?', TRUE, 'epbp2.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('TASC Get Data Scheduler', 'batchTASCGetDataJobRunScheduler', 'run', '0 0/1 * * * ?', TRUE, 'epbp2.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('PWD Expiration Alert Scheduler', 'passwordExpirationAlertScheduler', 'run', '0 0 1 1/1 * ?', TRUE, 'epbp2.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('Module Report DeleteScheduler', 'moduleReportDeleteScheduler', 'run', '0 0 22 * * ?', FALSE, 'epbp2.prodku.cete.us');
    
--production ep1
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('DLM Student Tracker', 'dlmRunScheduler', 'run', '0 0/5 8-18 * * MON-FRI', TRUE, 'ep1.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('DLM Student Tracker Only Untracked', 'dlmOnlyUntrackedRunScheduler', 'run', '0 0/15 8-18 * * MON-FRI', FALSE, 'ep1.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('CPASS Summative Scheduler', 'cpassSummativeRunScheduler', 'run', '0 0 20 * * ?', FALSE, 'ep1.prodku.cete.us');
INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver)
    VALUES ('AMP Summative Scheduler', 'ampSummativeRunScheduler', 'run', '0 0 23 * * ?', FALSE, 'ep1.prodku.cete.us');
