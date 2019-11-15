--INFO CB related table.

CREATE TABLE if not exists taskvariantcontentframeworkdetail
(
  taskvariantid bigint NOT NULL,
  contentframeworkdetailid bigint NOT NULL,
  isprimary boolean not null default false,
  CONSTRAINT taskvariantcontentframeworkdetail_pkey PRIMARY KEY (taskvariantid , contentframeworkdetailid ),
  CONSTRAINT taskvariantcontentframeworkdetail_fk1 FOREIGN KEY (contentframeworkdetailid)
      REFERENCES contentframeworkdetail (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT taskvariantcontentframeworkdetail_fk2 FOREIGN KEY (taskvariantid) 
      REFERENCES taskvariant (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

insert into taskvariantcontentframeworkdetail(taskvariantid,contentframeworkdetailid,isprimary)
(select id as taskvariantid ,contentframeworkdetailid, true as isprimary
from taskvariant where contentframeworkdetailid is not null except (select taskvariantid,contentframeworkdetailid,isprimary from taskvariantcontentframeworkdetail)
);

alter table taskvariant DROP COLUMN if exists contentframeworkdetailid;

ALTER TABLE studentsresponses ALTER COLUMN id DROP DEFAULT;
DROP SEQUENCE IF EXISTS studentsresponses_id_seq;

--INFO CB related table.merge this from 12.sql to 11.sql after push to all environments.

alter table taskvariant add column directions text,
                        add column cue text,
                        add column completion text;
                        
--CB related table.
--Original 13.sql

Alter table testsection drop column if exists externaltestid;

Alter table assessmentprogram alter column programname type character varying(100);

Alter table taskvariant alter column taskstem type text;

-- add the foreign key that references the test collection table
alter table studentstests add constraint studentstests_testcollectionid_fkey
foreign key (testcollectionid) references testcollection (id);

alter table studentsresponses add column studentstestsid bigint REFERENCES studentstests(id);

