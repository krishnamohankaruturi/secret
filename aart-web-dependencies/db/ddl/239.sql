-- 239.sql ddl
CREATE TABLE tag
(
  id bigserial NOT NULL,
  tagname character varying(75),
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  CONSTRAINT tag_pkey PRIMARY KEY (id),
  CONSTRAINT fk_tag_crdusr FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION  
);
  
CREATE TABLE moduletag
(
  moduleid bigserial NOT NULL,
  tagid bigserial NOT NULL,
  CONSTRAINT moduletag_pkey PRIMARY KEY (moduleid, tagid),
  CONSTRAINT fk_moduletag_module FOREIGN KEY (moduleid)
      REFERENCES module (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT fk_moduletag_tag FOREIGN KEY (tagid)
      REFERENCES tag (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE modulegroup
(
  moduleid bigserial NOT NULL,
  groupid bigserial NOT NULL,
  CONSTRAINT modulegroup_pkey PRIMARY KEY (moduleid, groupid),
  CONSTRAINT fk_modulegroup_module FOREIGN KEY (moduleid)
      REFERENCES module (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT fk_modulegroup_tag FOREIGN KEY (groupid)
      REFERENCES groups (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);