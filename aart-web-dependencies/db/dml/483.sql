--dml/483.sql

  
update batchjobschedule set jobrefname = 'batchTASCProcessJobRunScheduler', initmethod= 'run' where jobname = 'TASC Process Scheduler';

update batchjobschedule set jobrefname = 'batchTASCGetDataJobRunScheduler', initmethod= 'run' where jobname = 'TASC Get Data Scheduler';