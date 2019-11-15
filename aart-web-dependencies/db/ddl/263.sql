
-- 260.sql
 
CREATE TABLE ititestsessionhistory
	(
	  id bigserial NOT NULL,
	  studentid bigint NOT NULL,
	  statestudentidentifier character varying(50) NOT NULL,
	  rosterid bigint,
	  name character varying(200) NOT NULL,
	  status bigint,
	  testid bigint,
	  testcollectionid bigint NOT NULL,
	  testcollectionname character varying(200) NOT NULL,
	  saveddate timestamp without time zone,
	  confirmdate timestamp without time zone,	
	  essentialelement text,
	  linkagelevel text,
	  leveldescription text,
	  createddate timestamp without time zone DEFAULT now(),
	  modifieddate timestamp without time zone DEFAULT now(),
	  createduser bigint,
	  modifieduser bigint,
	  CONSTRAINT ititsh_pkey PRIMARY KEY (id),
	  CONSTRAINT fk_ititsh_studentid FOREIGN KEY (studentid)
	      REFERENCES student (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_ititsh_created_user FOREIGN KEY (createduser)
	      REFERENCES aartuser (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_ititsh_updated_user FOREIGN KEY (modifieduser)
	      REFERENCES aartuser (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_ititsh_testcollection FOREIGN KEY (testcollectionid)
	      REFERENCES testcollection (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_ititsh_rosterid FOREIGN KEY (rosterid)
	      REFERENCES roster (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_ititsh_status FOREIGN KEY (status)
	      REFERENCES category (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
	  
	 );
