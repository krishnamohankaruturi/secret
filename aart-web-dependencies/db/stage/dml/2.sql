-- set status for processing
UPDATE questar_staging SET status = 'READY', modifieddate = createdate;
