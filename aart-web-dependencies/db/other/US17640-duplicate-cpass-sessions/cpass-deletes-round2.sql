-------- delete stage 2 pending test sessions ----------

delete from studentstestsections where studentstestid in (
	select id from studentstests where testsessionid in (
		select id from (
			select ts.id, (select count(id) from studentstests where status = 465 and activeflag = true and testsessionid = ts.id) as pending
			from testsession ts
			where ts.schoolyear = 2016
				and ts.source = 'BATCHAUTO'
				and ts.activeflag = true
				and ts.testtypeid in (
					SELECT tt.id
					FROM testtype tt
					INNER JOIN assessment a ON tt.assessmentid = a.id AND tt.activeflag = TRUE AND a.activeflag = TRUE
					INNER JOIN testingprogram tp ON a.testingprogramid = tp.id AND tp.activeflag = TRUE
					WHERE tp.assessmentprogramid = (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'CPASS')
				)
			) as tmp
		where pending > 0
	)
);

delete from studentstests where id in (
	select id from studentstests where testsessionid in (
		select id from (
			select ts.id, (select count(id) from studentstests where status = 465 and activeflag = true and testsessionid = ts.id) as pending
			from testsession ts
			where ts.schoolyear = 2016
				and ts.source = 'BATCHAUTO'
				and ts.activeflag = true
				and ts.testtypeid in (
					SELECT tt.id
					FROM testtype tt
					INNER JOIN assessment a ON tt.assessmentid = a.id AND tt.activeflag = TRUE AND a.activeflag = TRUE
					INNER JOIN testingprogram tp ON a.testingprogramid = tp.id AND tp.activeflag = TRUE
					WHERE tp.assessmentprogramid = (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'CPASS')
				)
		) as tmp
		where pending > 0
	)
);

delete from testsession where id in (
	select id from (
		select ts.id, substring(ts.name from 1 for 25) || '...' || substring(ts.name from (char_length(ts.name) - 20)) as name
		from testsession ts
		where ts.schoolyear = 2016
			and ts.source = 'BATCHAUTO'
			and ts.activeflag = true
			and ts.testtypeid in (
				SELECT tt.id
				FROM testtype tt
				INNER JOIN assessment a ON tt.assessmentid = a.id AND tt.activeflag = TRUE AND a.activeflag = TRUE
				INNER JOIN testingprogram tp ON a.testingprogramid = tp.id AND tp.activeflag = TRUE
				WHERE tp.assessmentprogramid = (SELECT id FROM assessmentprogram WHERE abbreviatedname = 'CPASS')
			)
		) as tmp
	where name like '%Stage 2'
);