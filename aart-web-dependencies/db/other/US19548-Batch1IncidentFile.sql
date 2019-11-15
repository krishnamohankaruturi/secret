update studentstests set activeflag = false, modifieduser = 12, modifieddate =  now() AT TIME ZONE 'GMT', manualupdatereason = 'US19548: Batch 1 Incident File- inactivating the testlet with low score.'
where id in (16545539, 16676935);

update studentsresponses set activeflag = false, modifieduser = 12, modifieddate =  now() AT TIME ZONE 'GMT'
where studentstestsid in(16545539, 16676935);

update studentstestsections set activeflag = false, modifieduser = 12, modifieddate =  now() AT TIME ZONE 'GMT'
where studentstestid in(16545539, 16676935);

update testsession set activeflag = false, modifieduser = 12, modifieddate =  now() AT TIME ZONE 'GMT'
where id in(4209159, 4322319);

update studenttrackerband set testsessionid = 4204833,  modifieduser = 12, modifieddate =  now() AT TIME ZONE 'GMT'
where studenttrackerid = (select id from studenttracker where studentid = 1128321 and contentareaid = 3)
and source= 'FC' and testsessionid = 4322319;

update studenttrackerband set testsessionid = 4204191,  modifieduser = 12, modifieddate =  now() AT TIME ZONE 'GMT' 
where studenttrackerid = (select id from studenttracker where studentid =959837  and contentareaid = 441) 
and source= 'FC' and testsessionid = 4209159;
