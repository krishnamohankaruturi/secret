----ddl/*.sql ==> For dml/*.sql
--US19228: Ability to exclude permission from roles

DROP TABLE IF EXISTS permissionexclusion;

DROP TABLE IF EXISTS groupauthoritiesexclusion;
CREATE TABLE IF NOT EXISTS groupauthoritiesexclusion
(
  id bigserial NOT NULL,
  groupid bigint NOT NULL,
  authorityid bigint NOT NULL,
  assessmentprogramid bigint NOT NULL,
  stateid bigint NOT NULL,
  activeflag boolean DEFAULT true,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  CONSTRAINT groupauthoritiesexclusion_pkey PRIMARY KEY (id)
);
