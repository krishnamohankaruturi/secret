delete from studentsresponses
where studentstestsid in (
	select id from studentstests where testsessionid in (2203365, 2149586, 2149585, 2149576)
);

delete from studentstestsections
where studentstestid in (
	select id from studentstests where testsessionid in (2203365, 2149586, 2149585, 2149576)
);

delete from studentstests
where id in (
	select id from studentstests where testsessionid in (2203365, 2149586, 2149585, 2149576)
);

delete from testsession where id in (2203365, 2149586, 2149585, 2149576);