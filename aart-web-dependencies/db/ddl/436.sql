--ddl/436.sql

DO
$BODY$
BEGIN
      IF EXISTS (SELECT column_name 
                              FROM information_schema.columns 
                              WHERE table_name='leveldescription' and column_name='leveldescription') THEN
            raise NOTICE 'description column in leveldescription found';
      else
            alter table leveldescription add column leveldescription varchar(550);

      END IF;
END
$BODY$;

DO
$BODY$
BEGIN
      IF EXISTS (SELECT column_name 
                              FROM information_schema.columns 
                              WHERE table_name='userpdtrainingdetail' and column_name='organizationid') THEN
            raise NOTICE 'organizationid column in userpdtrainingdetail found';
      else
			ALTER TABLE userpdtrainingdetail ADD COLUMN organizationid bigint;
      END IF;
END
$BODY$;

ALTER TABLE communicationmessage ALTER COLUMN messagetitle TYPE character varying(256);
ALTER TABLE communicationmessage ALTER COLUMN messagecontent TYPE text;