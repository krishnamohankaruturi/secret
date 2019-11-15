--420.sql
 
DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='reportspercentbylevel' and column_name='assessmentprogram') THEN
		alter table reportspercentbylevel rename assessmentprogram to assessmentprogramid;
	else
		raise NOTICE 'assessmentprogram not exists';
	END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='reportspercentbylevel' and column_name='contentarea') THEN
		alter table reportspercentbylevel rename contentarea to contentareaid;
	else
		raise NOTICE 'contentarea not exists';
	END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='reportspercentbylevel' and column_name='grade') THEN
		alter table reportspercentbylevel rename grade to gradeid;
	else
		raise NOTICE 'grade not exists';
	END IF;
END
$BODY$;

 
DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='excludeditems' and column_name='assessmentprogram') THEN
		alter table excludeditems rename assessmentprogram to assessmentprogramid;
	else
		raise NOTICE 'assessmentprogram not exists';
	END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='excludeditems' and column_name='subject') THEN
		alter table excludeditems rename subject to subjectid;
	else
		raise NOTICE 'subject	 not exists';
	END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='excludeditems' and column_name='grade') THEN
		alter table excludeditems rename grade to gradeid;
	else
		raise NOTICE 'grade not exists';
	END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='excludeditems' and column_name='taskvariant') THEN
		alter table excludeditems rename taskvariant to taskvariantid;
	else
		raise NOTICE 'taskvariant not exists';
	END IF;
END
$BODY$;


drop table if exists subscores_rawtoscale;
drop table if exists rawtoscalescore;