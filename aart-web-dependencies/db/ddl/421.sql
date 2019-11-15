--421.sql
 
 DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='testcutscores' and column_name='levelid') THEN
		raise NOTICE 'levelid exists';
	else
		alter table testcutscores add column levelid bigint;
	END IF;
END
$BODY$;



 DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='excludeditems' and column_name='createduser') THEN
		raise NOTICE 'createduser exists';
	else
		alter table excludeditems add column createduser bigint;
	END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='studentreport' and column_name='aggregates') THEN
		raise NOTICE 'aggregates exists';
		
	else
		alter table studentreport add column aggregates boolean;
	END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='reportspercentbylevel' and column_name='level') THEN
		alter table reportspercentbylevel rename level to levelid;
	else
		raise NOTICE 'level not exists';
	END IF;
END
$BODY$;


alter table studentreport alter column status type character varying(500);

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='reportsmedianscore' and column_name='detailedreportpath') THEN
		raise NOTICE 'detailedreportpath exists in reportsmedianscore';
	else
		alter table reportsmedianscore add column detailedreportpath text;
	END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='reportsmedianscore' and column_name='schoolreportpdfpath') THEN
		raise NOTICE 'schoolreportpdfpath exists in reportsmedianscore';
	else
		alter table reportsmedianscore add column schoolreportpdfpath text;
	END IF;
END
$BODY$;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='reportsmedianscore' and column_name='schoolreportpdfsize') THEN
		raise NOTICE 'schoolreportpdfsize exists in reportsmedianscore';
	else
		alter table reportsmedianscore add column schoolreportpdfsize bigint;
	END IF;
END
$BODY$;


DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='reportsmedianscore' and column_name='schoolreportzipsize') THEN
		raise NOTICE 'schoolreportzipsize exists in reportsmedianscore';
	else
		alter table reportsmedianscore add column schoolreportzipsize bigint;
	END IF;
END
$BODY$;


DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='reportsmedianscore' and column_name='subscoredefinitionname') THEN
		raise NOTICE 'subscoredefinitionname exists in reportsmedianscore';
	else
		alter table reportsmedianscore add column subscoredefinitionname character varying(100);
	END IF;
END
$BODY$;



