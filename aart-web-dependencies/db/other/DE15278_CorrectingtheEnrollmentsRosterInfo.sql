--DE15278: TEST for K-ELPA student not overwriting old roster, student left dual enrolled with two rosters
update enrollmentsrosters set activeflag = false, modifieddate = now(), modifieduser = 12 where enrollmentid in (2799798, 2799865);

UPDATE scoringassignmentscorer set activeflag = false where scoringassignmentid in (select scoringassignmentid from scoringassignmentstudent where studentstestsid in (select id from studentstests where enrollmentid in (2799798, 2799865)));

UPDATE scoringassignmentstudent set activeflag = false where studentstestsid in (select id from studentstests where enrollmentid in (2799798, 2799865));