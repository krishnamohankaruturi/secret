
CREATE TABLE testjson (
  testid bigint NOT NULL,
  jsonobject text,
  CONSTRAINT testjson_pkey PRIMARY KEY (testid)
);

ALTER TABLE tasktype ADD COLUMN longdescription text;
ALTER TABLE tasksubtype ADD COLUMN longdescription text;