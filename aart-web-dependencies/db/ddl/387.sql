-- Upload Reports

CREATE TABLE batchuploadreason
(
  batchuploadid bigint NOT NULL,
  line character varying(25),
  fieldName character varying(300),
  reason text,
  CONSTRAINT batchuploadreason_batchuploadid_fkey FOREIGN KEY (batchuploadid)
      REFERENCES batchupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


alter table fieldspecificationsrecordtypes add column mappedname character varying(250);
alter table fieldspecification add column fieldtype character varying(75);	

CREATE TABLE excludeditems
(
  id bigserial NOT NULL,
  schoolyear bigint not null,
  assessmentprogram bigint not null,
  subject bigint not null,
  grade bigint not null,
  taskvariant bigint not null,
  batchuploadid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  CONSTRAINT excludeditems_id_pk PRIMARY KEY (id),
  CONSTRAINT excludeditems_batchuploadid_fk FOREIGN KEY (batchuploadid)
      REFERENCES batchupload (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

 