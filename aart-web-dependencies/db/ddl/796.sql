CREATE SEQUENCE if not exists studentsteststimes_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 2147483647
  START 1
  CACHE 1;

CREATE TABLE IF NOT EXISTS studentsteststimes (
id				bigint DEFAULT nextval ('studentsteststimes_id_seq'::regclass),
studentstestsid			bigint,
studentstestsectionid		bigint,
studentstests_starttime		timestamp with time zone,
studentstests_endtime		timestamp with time zone,
studentstestsections_starttime	timestamp with time zone,
studentstestsections_endtime	timestamp with time zone,
action				varchar(20),
activeflag			boolean default true,
createddate			timestamp with time zone default ('now'::text)::timestamp with time zone,
modifieddate			timestamp with time zone,
createduser			integer,
modifieduser			integer
);

CREATE INDEX if not exists idx_studentsteststimes_studentstests
    ON public.studentsteststimes USING btree
    (studentstestsid);
    
CREATE INDEX if not exists idx_studentsteststimes_studentstestsections
    ON public.studentsteststimes USING btree
    (studentstestsectionid);

