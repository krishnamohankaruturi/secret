--Enrolled kid in Elem school instead of High school, so scoring assignments needs to be removed
--studentids: 564398, 564381
--remove scoring assignmentid
update studentstests
set scoringassignmentid = null,
modifieddate = now(),
modifieduser = (select id from aartuser where email = 'ktaduru_sta@ku.edu')
where id in(21653738,21653736);

--Inactivate scoringassignment on studentstestsid from elem school
update scoringassignmentstudent 
set activeflag = false,
modifieddate = now(),
modifieduser = (select id from aartuser where email = 'ktaduru_sta@ku.edu')
where studentstestsid in(21653738,21653736);

--There are no other students on this scoringassignment other than these 2 kids, so inactivate this as well
update scoringassignment
set activeflag = false,
ccqtestname = ccqtestname || '-' || (SELECT (EXTRACT (MILLISECONDS from now()::time))*1000),
modifieddate = now(),
modifieduser = (select id from aartuser where email = 'ktaduru_sta@ku.edu')
where id = 39062 and testsessionid = 6225798;