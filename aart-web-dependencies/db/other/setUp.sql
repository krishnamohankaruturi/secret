CREATE TABLE if not exists ddl_version
(
  version integer NOT NULL,
  updated timestamp with time zone DEFAULT now(),
  project character varying,
  CONSTRAINT pk_ddl_version PRIMARY KEY (version )
)
WITH (
  OIDS=FALSE
);

-- Table: data_version

-- DROP TABLE data_version;

CREATE TABLE if not exists data_version
(
  version integer,
  project character varying,
  updated timestamp with time zone DEFAULT now()
)
WITH (
  OIDS=FALSE
);

create user aart;

  