
--US15080
CREATE TABLE modulereport
(
  id bigserial NOT NULL,
  groupid bigint,
  reporttype character varying(30),
  description character varying(100),
  stateid bigint NULL,
  statusid bigint NOT NULL,
  filename character varying(125),
  activeflag boolean DEFAULT true,
  createduser integer NOT NULL,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,  
  CONSTRAINT pk_modulereport PRIMARY KEY (id),	
  CONSTRAINT fk_modulereport_group FOREIGN KEY (groupid)
	REFERENCES groups (id) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_modulereport_status FOREIGN KEY (statusid)
	REFERENCES category (id) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_modulereport_state FOREIGN KEY (stateid)
      REFERENCES organization (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION			
);
