INSERT INTO  batchjobschedule(jobname, jobrefname, initmethod, cronexpression, scheduled, allowedserver) 
	VALUES ('KAP Adaptive Interim Scheduler','kapAdaptiveInterimRunScheduler','run','* */2 * * * ?', FALSE,'epbp1.prodku.cete.us');
