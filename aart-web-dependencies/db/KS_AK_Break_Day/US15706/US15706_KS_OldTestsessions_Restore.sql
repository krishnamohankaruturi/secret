--Update testsession status to unused
UPDATE testsession SET status=(
	select id from category where categorycode='unused' and categorytypeid=(select id from categorytype where typecode='STUDENT_TEST_STATUS'))
WHERE id IN (select testsessionid from studentstests where testid in(
6928, 6929, 6930, 6931, 6932, 6933, 6934, 6935, 6936, 6937, 6938, 6939,  6940, 6941));

--Update studenttest status to unused
UPDATE studentstests SET status=(
	select id from category where categorycode='unused' and categorytypeid=(select id from categorytype where typecode='STUDENT_TEST_STATUS'))
WHERE id IN (select id from studentstests where testid in(
6928, 6929, 6930, 6931, 6932, 6933, 6934, 6935, 6936, 6937, 6938, 6939,  6940, 6941));

--Update studenttestsection status to unused
UPDATE studentstestsections SET statusid=(
	select id from category where categorycode='unused' and categorytypeid=(select id from categorytype where typecode='STUDENT_TESTSECTION_STATUS'))
WHERE id IN (select id from studentstestsections where studentstestid in (select id from studentstests where testid in(
6928, 6929, 6930, 6931, 6932, 6933, 6934, 6935, 6936, 6937, 6938, 6939,  6940, 6941)));

--Remove student responses from last KS break day.
DELETE FROM studentsresponses 
WHERE studentstestsectionsid in (
select id from studentstestsections where studentstestid in (select id from studentstests where testid in(
6928, 6929, 6930, 6931, 6932, 6933, 6934, 6935, 6936, 6937, 6938, 6939,  6940, 6941)));