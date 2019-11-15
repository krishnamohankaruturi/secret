-- 240.sql ddl

CREATE TABLE usertest
(
  id bigserial NOT NULL,
  usermoduleid bigint NOT NULL,
  testid bigint,
  status bigint NOT NULL,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieduser integer NOT NULL,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  startdatetime timestamp without time zone,
  enddatetime timestamp without time zone,
  scores text,
  CONSTRAINT usertest_pkey PRIMARY KEY (id),
  CONSTRAINT fk_ut_usermodule FOREIGN KEY (usermoduleid)
      REFERENCES usermodule (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT fk_ut_created_user FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ut_updated_user FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_ut_status FOREIGN KEY (status)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ut_testid_fkey FOREIGN KEY (testid)
      REFERENCES test (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE usertestsection
(
  id bigserial NOT NULL,
  usertestid bigint,
  testsectionid bigint,
  statusid bigint NOT NULL,
  lastnavqnum integer DEFAULT 0,
  createddate timestamp without time zone DEFAULT now(),
  modifieddate timestamp without time zone DEFAULT now(),
  createduser integer,
  modifieduser integer,
  activeflag boolean DEFAULT true,
  startdatetime timestamp without time zone,
  enddatetime timestamp without time zone,
  scores text,
  CONSTRAINT usertestsection_pkey PRIMARY KEY (id),
  CONSTRAINT fk_usertestsection_crdusr FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usertestsection_status FOREIGN KEY (statusid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usertestsection_usertestid FOREIGN KEY (usertestid)
      REFERENCES usertest (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usertestsection_testsectionid FOREIGN KEY (testsectionid)
      REFERENCES testsection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_usertestsection_updusr FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ukey_usertestsection UNIQUE (usertestid, testsectionid)
);

CREATE TABLE userresponse
(
  userid bigint,
  testid bigint,
  testsectionid bigint,
  usertestsectionid bigint NOT NULL,
  taskvariantid bigint NOT NULL,
  foilid bigint,
  response text,
  createddate timestamp without time zone DEFAULT now(),
  modifieddate timestamp without time zone DEFAULT now(),
  score numeric(6,3),
  createduser integer,
  modifieduser integer,
  activeflag boolean DEFAULT true,
  CONSTRAINT userresponse_pkey PRIMARY KEY (usertestsectionid, taskvariantid),
  CONSTRAINT userresponse_foilid_fkey FOREIGN KEY (foilid)
      REFERENCES foil (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT userresponse_userid_fkey FOREIGN KEY (userid)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT userresponse_usertestsectionid_fkey FOREIGN KEY (usertestsectionid)
      REFERENCES usertestsection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT userresponse_taskvariantid_fkey FOREIGN KEY (taskvariantid)
      REFERENCES taskvariant (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT userresponse_testid_fkey FOREIGN KEY (testid)
      REFERENCES test (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);