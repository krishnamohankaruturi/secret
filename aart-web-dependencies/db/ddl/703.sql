--ddl/703.sql

/*
drop table if exists moodleupload;
CREATE TABLE moodleupload
(
  id bigserial NOT NULL,
  aartuserid bigint,
  schoolyear int not null,
  email character varying(254),
  course character varying(254),
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser integer NOT NULL,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieduser integer not NULL,
  CONSTRAINT moodleupload_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE moodleupload
  OWNER TO aart;
COMMENT ON TABLE moodleupload
  IS 'DLM Moodle Upload Extract Incremental users Tracking.';


CREATE INDEX idx_moodleupload_aartuserid
  ON moodleupload
  USING btree
  (aartuserid);

insert into moodleupload(aartuserid,schoolyear,email,course,createddate,createduser,modifieddate,modifieduser)
select a.id aartuserid,
       2018 schoolyear,
       a.email email,
       'NewReturning' course,
       now() createddate,
       (select id from aartuser  where email='ats_dba_team@ku.edu') createduser,
       now() modifieddate,
       (select id from aartuser  where email='ats_dba_team@ku.edu') modifieduser
from aartuser a 
join domainaudithistory d on d.objectid=a.id and d.source='MOODLE' and d.objecttype='aartuser' and d.action='EXTRACT';

-- ddl/permissions.sql for  sequence
*/