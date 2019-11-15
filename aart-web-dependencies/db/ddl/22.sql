--US12057 : PNP data upload - SIF Nickname handling and APIP hiearchy handling


ALTER TABLE profileitemattribute ADD CONSTRAINT uk_profile_item_attribute UNIQUE (attributename);

ALTER TABLE studentprofileitemattributevalue DROP column profileitemattributenicknamecontainerid;

ALTER TABLE studentprofileitemattributevalue ADD column profileitemattributenameattributecontainerid bigint;

DROP TABLE profileitemattributenickname cascade;

DROP TABLE profileitemattributenicknamecontainer cascade;


CREATE TABLE profileitemattributenameattributecontainer
(
  id bigserial NOT NULL,
  attributenameid bigint,
  attributecontainerid bigint,
  parentcontainerleveloneid bigint,
  parentcontainerleveltwoid bigint,
  parentcontainerlevelthreeid bigint,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT pk_profile_item_attribute_name_attribute_container PRIMARY KEY (id),
  CONSTRAINT uk_profile_item_attribute_name_attribute_container UNIQUE (attributenameid, attributecontainerid),
  CONSTRAINT fk_profile_item_attributename_attribute_container_attribute_name_id FOREIGN KEY (attributenameid)
      REFERENCES profileitemattribute (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_profile_item_attributename_attribute_container_attribute_container_id FOREIGN KEY (attributecontainerid)
      REFERENCES profileitemattributecontainer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

-- to be executed .
  
ALTER TABLE testsession ADD COLUMN testcollectionid bigint;

update testsession set testcollectionid=(
select testcollectionid from studentstests st where st.testsessionid=testsession.id limit 1);

delete from testsession where not exists (select 1 from studentstests st where st.testsessionid=testsession.id);

ALTER TABLE testsession
   ALTER COLUMN testcollectionid SET NOT NULL;
   
ALTER TABLE testsession ADD CONSTRAINT
test_session_test_coll_fk FOREIGN KEY (testcollectionid)
REFERENCES testcollection (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--adding attendance school id to roster.

ALTER TABLE roster ADD COLUMN attendanceschoolid bigint;

update roster set attendanceschoolid=(
select e.attendanceschoolid from enrollmentsrosters er,enrollment e where e.id = er.enrollmentid and er.rosterid=roster.id limit 1);

delete from roster where not exists (select 1 from enrollmentsrosters er where er.rosterid=roster.id);


ALTER TABLE roster
   ALTER COLUMN attendanceschoolid SET NOT NULL;
   
ALTER TABLE roster ADD CONSTRAINT
roster_attendance_school_fk FOREIGN KEY (attendanceschoolid)
REFERENCES organization (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

--CB publishing changes.

ALTER TABLE taskvariant ADD COLUMN reporttasklayoutformatid bigint;
ALTER TABLE taskvariant ADD COLUMN minchoices integer;
ALTER TABLE taskvariant ADD COLUMN maxchoices integer;
 
ALTER TABLE taskvariant ADD CONSTRAINT task_reporttasklayoutformatid_fkey FOREIGN KEY (reporttasklayoutformatid)
  REFERENCES tasklayoutformat (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION;
  
  