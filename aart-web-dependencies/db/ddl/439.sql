--ddl/439.sql

DO
$BODY$
BEGIN
      IF EXISTS (SELECT 0 FROM pg_class where relname = 'subscoresrawtoscale_id_seq') THEN
            raise NOTICE 'subscoresrawtoscale_id_seq sequence already exists';
      else
                CREATE SEQUENCE subscoresrawtoscale_id_seq
                  INCREMENT 1
                  MINVALUE 1
                  MAXVALUE 9223372036854775807
                  START 1
                  CACHE 1;
      END IF;
END
$BODY$;

