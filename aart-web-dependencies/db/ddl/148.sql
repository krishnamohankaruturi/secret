
--US13712 Name:  Reports - Report UI - Report Data Management UI/Summative Reports Data Upload 
 
CREATE TABLE userreportupload
(
  id bigserial NOT NULL,
  aartuserid bigint NOT NULL,
  filetypeid bigint NOT NULL,
  uploadedfilename character varying(100),
  filedata text,
  createddate timestamp with time zone NOT NULL DEFAULT now(),
  modifieddate timestamp with time zone NOT NULL DEFAULT now(),
  createduser bigint,
  modifieduser bigint,
  activeflag boolean DEFAULT true,  
  CONSTRAINT userreportupload_id_fk PRIMARY KEY (id),
  CONSTRAINT userreportupload_aartuserid_fk FOREIGN KEY (aartuserid)
      REFERENCES aartuser (id) MATCH FULL ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT userreportupload_category_fileTypeid_fk FOREIGN KEY (fileTypeid)
      REFERENCES category (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);



