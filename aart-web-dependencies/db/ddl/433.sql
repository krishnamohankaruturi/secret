--ddl/433.sql

DO
$BODY$
BEGIN
      IF EXISTS (SELECT 1 FROM   information_schema.tables  WHERE  table_schema = 'public' AND    table_name = 'organizationreportdetails') THEN
            raise NOTICE 'table already exists';

      else
            CREATE TABLE organizationreportdetails
            (
              id bigserial NOT NULL,
              assessmentprogramid bigint NOT NULL,
              contentareaid bigint NOT NULL,
              gradeid bigint NOT NULL,
              organizationid bigint,
              schoolyear bigint,
              createddate timestamp with time zone NOT NULL DEFAULT now(),
              detailedreportpath text,
              schoolreportpdfpath text,
              schoolreportpdfsize bigint,
              schoolreportzipsize bigint,
              batchreportprocessid bigint NOT NULL,
              CONSTRAINT organizationreportdetails_batchreportprocessid_fk FOREIGN KEY (batchreportprocessid)
                  REFERENCES reportprocess (id) MATCH SIMPLE
                  ON UPDATE NO ACTION ON DELETE CASCADE
              );
      END IF;
END
$BODY$;
      
      
      
 DO
$BODY$
BEGIN
      IF EXISTS (SELECT column_name 
                              FROM information_schema.columns 
                              WHERE table_name='reportspercentbylevel' and column_name='levelid') THEN
            alter table reportspercentbylevel rename levelid to level;
      else
                        raise NOTICE 'levelid not found';

      END IF;
END
$BODY$;


DO
$BODY$
BEGIN
      IF EXISTS (SELECT column_name 
                              FROM information_schema.columns 
                              WHERE table_name='reportspercentbylevel' and column_name='schoolyear') THEN
            alter table reportspercentbylevel alter column schoolyear type bigint;
      else
                        raise NOTICE 'schoolyear not found';

      END IF;
END
$BODY$;