--ddl/482.sql
-- three new columns added to OTW table
alter table operationaltestwindow add assessmentprogramid bigint;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='operationaltestwindow' and column_name='autoenrollmentflag') THEN
		raise NOTICE 'autoenrollmentflag found in operationaltestwindow';
	else
		ALTER TABLE operationaltestwindow ADD COLUMN autoenrollmentflag boolean;
END IF;
END
$BODY$;

alter table operationaltestwindow add autoenrollmentmethodid bigint;

alter table operationaltestwindow add constraint fk_autoenrollmentmethodid foreign key( autoenrollmentmethodid)
 references testenrollmentmethod(id);
 
alter table batchuploadreason add column errortype varchar(10);
alter table batchupload add column alertcount bigint;
 
 --script from CB team
 CREATE TABLE brailleaccommodation
(
  id bigint NOT NULL,
  externalid bigint NOT NULL,
  contentgroupid bigint NOT NULL,
  brailleabbreviation character varying(30),
  defaultorder integer,
  alternateorder integer,
  accessibilityfileid bigint,
  createuserid bigint,
  createdate timestamp with time zone,
  modifieddate timestamp with time zone,
  modifieduserid bigint,
  originationcode character varying(20) NOT NULL,
  CONSTRAINT brailleacc_pkey PRIMARY KEY (id),
  CONSTRAINT brailleacc_accessibilityfileid_fkey FOREIGN KEY (accessibilityfileid)
      REFERENCES accessibilityfile (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT brailleacc_contentgroupid_fkey FOREIGN KEY (contentgroupid)
      REFERENCES contentgroup (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

