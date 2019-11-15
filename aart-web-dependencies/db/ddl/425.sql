-- 425.sql

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='organization' and column_name='reportprocess') THEN
		raise NOTICE 'reportprocess found';
	else
		alter table organization add column reportprocess boolean default true;
	END IF;
END
$BODY$;

CREATE INDEX idx_organization_reportprocess
  ON organization
  USING btree
  (reportprocess);