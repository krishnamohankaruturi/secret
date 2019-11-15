----ddl/654.sql
CREATE TABLE grfreportdistrictshortname
(
  id bigserial NOT NULL,
  districtcode character varying(15),
  originaldistrictname character varying(75) NOT NULL,
  shortdistrictname character varying(65) NOT NULL,
  activeflag boolean DEFAULT true,
  CONSTRAINT grfreportdistrictshortname_pkey PRIMARY KEY (id)
  );

CREATE INDEX idx1_grfreportdistrictshortname ON grfreportdistrictshortname USING btree (districtcode);
CREATE INDEX idx2_grfreportdistrictshortname ON grfreportdistrictshortname USING btree (activeflag);

ALTER TABLE grfreportdistrictshortname add column organizationid bigint;