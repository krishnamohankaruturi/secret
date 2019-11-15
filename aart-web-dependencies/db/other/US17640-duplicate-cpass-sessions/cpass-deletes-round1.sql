delete from studentstestsections where studentstestid in (
	select id from studentstests where testsessionid in (
		select id from (
			select
				ts.id,
				(select count(id) from studentstests where activeflag = true and testsessionid = ts.id) as numstudentstests,
				(select count(id) from studentstests where status = 84 and activeflag = true and testsessionid = ts.id) as unused
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
		where unused = numstudentstests
	)
);

delete from studentstests where id in (
	select id from studentstests where testsessionid in (
		select id from (
			select
				ts.id,
				(select count(id) from studentstests where activeflag = true and testsessionid = ts.id) as numstudentstests,
				(select count(id) from studentstests where status = 84 and activeflag = true and testsessionid = ts.id) as unused
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
		where unused = numstudentstests
	)
);

delete from testsession where id in (
	select id from (
		select
			ts.id,
			(select count(id) from studentstests where activeflag = true and testsessionid = ts.id) as numstudentstests,
			(select count(id) from studentstests where status = 84 and activeflag = true and testsessionid = ts.id) as unused
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
		where unused = numstudentstests
);