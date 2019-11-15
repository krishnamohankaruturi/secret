-- US16671, adding Science to TEC uploads and removing BR, L, R test types

update fieldspecification set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),
	allowablevalues = '{'''',M,ELA,GKS,AgF&NR,BM&A,Arch&Const,Mfg,Sci}'
where id in (
	select fieldspecificationid
	from fieldspecificationsrecordtypes
	where recordtypeid = (select id from category where categorycode = 'TEC_RECORD_TYPE')
)
and fieldname = 'subject';

DO
$BODY$
DECLARE
	newttsaid BIGINT;
	newcattsaid BIGINT;
	ttid BIGINT;
	gcid BIGINT;
BEGIN
	FOR ttid IN (
		select id from testtype where testtypecode in ('GN', 'P') and activeflag is true
	)
	LOOP
		select into newttsaid nextval('testtypesubjectarea_id_seq');
		
		-- tie test type to science assessment
		insert into testtypesubjectarea values (
			newttsaid,
			ttid,
			(select id from subjectarea where subjectareacode = 'SSCIA'),
			(select id from aartuser where username = 'cetesysadmin'),
			now(),
			now(),
			(select id from aartuser where username = 'cetesysadmin'),
			true,
			(select a.id
				from assessment a
				inner join testingprogram tp on a.testingprogramid = tp.id
				inner join assessmentprogram ap on tp.assessmentprogramid = ap.id
				where ap.abbreviatedname = 'AMP'
				and tp.programabbr = 'S'
				and a.assessmentname = 'General'
			)
		);
		
		select into newcattsaid nextval('contentareatesttypesubjectarea_id_seq');
		
		-- tie it to contentarea
		insert into contentareatesttypesubjectarea values (
			newcattsaid,
			(select id from contentarea where abbreviatedname = 'Sci'),
			newttsaid,
			(select id from aartuser where username = 'cetesysadmin'),
			now(),
			now(),
			(select id from aartuser where username = 'cetesysadmin'),
			true
		);
		
		-- tie it to gradecourse
		FOR gcid IN (
			select id
			from gradecourse
			where abbreviatedname in ('4', '8', '10')
			and course = false
			and contentareaid = (select id from contentarea where abbreviatedname = 'Sci')
		)
		LOOP
			insert into gradecontentareatesttypesubjectarea values (
				newcattsaid,
				gcid,
				(select id from aartuser where username = 'cetesysadmin'),
				now(),
				now(),
				(select id from aartuser where username = 'cetesysadmin'),
				true
			);
		END LOOP;
	END LOOP;
END;
$BODY$;

-- remove BR, L, and R from TEC uploads
update fieldspecification set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),
	allowablevalues = '{2,3,A,B,D,E,F,G,GN,H,I,P}'
where id in (
	select fieldspecificationid
	from fieldspecificationsrecordtypes
	where recordtypeid = (select id from category where categorycode = 'TEC_RECORD_TYPE')
)
and fieldname = 'testType';

-- deactivate the corresponding entries
update testtypesubjectarea set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),
	activeflag = false
where testtypeid in (
	select id from testtype where testtypecode in ('BR', 'L', 'R') and assessmentid in (
		select a.id
		from assessment a
		inner join testingprogram tp on a.testingprogramid = tp.id
		inner join assessmentprogram ap on tp.assessmentprogramid = ap.id
		where ap.abbreviatedname = 'AMP'
	)
);

update contentareatesttypesubjectarea set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),
	activeflag = false
where testtypesubjectareaid in (
	select id from testtypesubjectarea where activeflag = false and testtypeid in (
		select id from testtype where testtypecode in ('BR', 'L', 'R') and assessmentid in (
			select a.id
			from assessment a
			inner join testingprogram tp on a.testingprogramid = tp.id
			inner join assessmentprogram ap on tp.assessmentprogramid = ap.id
			where ap.abbreviatedname = 'AMP'
		)
	)
);

update gradecontentareatesttypesubjectarea set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'),
	activeflag = false
where contentareatesttypesubjectareaid in (
	select id from contentareatesttypesubjectarea where activeflag = false and testtypesubjectareaid in (
		select id from testtypesubjectarea where activeflag = false and testtypeid in (
			select id from testtype where testtypecode in ('BR', 'L', 'R') and assessmentid in (
				select a.id
				from assessment a
				inner join testingprogram tp on a.testingprogramid = tp.id
				inner join assessmentprogram ap on tp.assessmentprogramid = ap.id
				where ap.abbreviatedname = 'AMP'
			)
		)
	)
);