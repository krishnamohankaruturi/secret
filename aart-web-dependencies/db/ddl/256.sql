
--new table US14843
CREATE TABLE modulestate
(
  moduleid bigserial NOT NULL,
  stateid bigserial NOT NULL,
  statusid bigint NOT NULL,
  CONSTRAINT modulestate_pkey PRIMARY KEY (moduleid, stateid),
  CONSTRAINT fk_modulestate_module FOREIGN KEY (moduleid)
      REFERENCES module (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT fk_modulestate_state FOREIGN KEY (stateid)
      REFERENCES organization (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT modulestate_status_fk FOREIGN KEY (statusid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION      
);     