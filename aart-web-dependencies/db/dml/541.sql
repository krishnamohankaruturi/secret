-- dml/.sql

INSERT INTO batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, 
            allowedserver)
    VALUES ('Audit Log scheduler', 'auditLogScheduler', 'run', '0 0 22 * * ?', false, 'localhost');
