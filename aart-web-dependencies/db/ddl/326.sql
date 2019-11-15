--US15601
CREATE SEQUENCE questarrequest_id_seq 
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE questarrequest
(
  id bigint NOT NULL,
  createduser integer,
  startdate timestamp with time zone,
  enddate timestamp with time zone,
  numberofresponses integer,
  CONSTRAINT pk_questarrequest_id PRIMARY KEY (id),
  CONSTRAINT fk_questarrequest_createduser FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE studentsresponses ADD COLUMN questarrequestid BIGINT;

DROP TABLE studentsteststags;

CREATE TABLE studentsteststags
(
  studenttestid bigint NOT NULL,
  testletid bigint NOT NULL,
  tagdata text,
  CONSTRAINT studentsteststags_pkey PRIMARY KEY(studenttestid,testletid)
);
