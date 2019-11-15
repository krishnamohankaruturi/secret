

--US14170 Name:  Instructional Tools Interface (ITI) - LM-EP Micromaps Snapshot Integration 


CREATE TABLE micromap
(
  id bigserial NOT NULL,
  micromapid bigint NOT NULL,
  micromapname character varying(200),
  associatedee character varying(200),
  nodeid bigint,
  linkagelabel character varying(200),
  nodekey character varying(200),
  nodename character varying(200),
  linkagelevelshortdesc text,
  linkagelevellongdesc text,
  nodedescription text,
  versionid bigint,
  versionname character varying(200),
  versionnumber real,
  contentframeworkdetailid bigint NOT NULL,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  modifieduser bigint,
  activeflag boolean DEFAULT true,
  CONSTRAINT micromap_id_fk PRIMARY KEY (id),
  CONSTRAINT micromap_contentframeworkdetailid_fk FOREIGN KEY (contentframeworkdetailid)
      REFERENCES contentframeworkdetail (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT module_createduser_fk FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT module_modifieduser_fk FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION

);



