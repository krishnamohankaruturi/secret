--253.sql
CREATE TABLE usertestsectiontask
(
  taskid bigint NOT NULL,
  sortorder integer NOT NULL,
  usertestsectionid bigint NOT NULL,
  createddate timestamp without time zone DEFAULT now(),
  modifieddate timestamp without time zone DEFAULT now(),
  CONSTRAINT usertestsectiontask_pkey PRIMARY KEY (usertestsectionid, taskid),
  CONSTRAINT usertestsectiontask_usertestsectionid_fkey FOREIGN KEY (usertestsectionid)
      REFERENCES usertestsection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT usertestsectiontask_taskid_fkey FOREIGN KEY (taskid)
      REFERENCES taskvariant (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE usertestsectiontaskfoil
(
  taskid bigint NOT NULL,
  foilid bigint NOT NULL,
  sortorder integer NOT NULL,
  usertestsectionid bigint NOT NULL,
  createddate timestamp without time zone DEFAULT now(),
  modifieddate timestamp without time zone DEFAULT now(),
  CONSTRAINT usertestsectiontaskfoil_pkey PRIMARY KEY (usertestsectionid, taskid, foilid),
  CONSTRAINT usertestsectiontaskfoil_foilid_fkey FOREIGN KEY (foilid)
      REFERENCES foil (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT usertestsectiontaskfoil_usertestsectionid_fkey FOREIGN KEY (usertestsectionid)
      REFERENCES usertestsection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT usertestsectiontaskfoil_taskid_fkey FOREIGN KEY (taskid)
      REFERENCES taskvariant (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
  