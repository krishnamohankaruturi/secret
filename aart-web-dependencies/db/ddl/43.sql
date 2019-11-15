alter table student add column assessmentprogramid bigint;
alter table student add   CONSTRAINT student_assessmentprogram_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
alter table gradecourse add column contentareaid bigint;
alter table gradecourse add CONSTRAINT gradecourse_contentarea_fk FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
CREATE SEQUENCE assessmentprogramgrades_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
create table assessmentprogramgrades(
	id bigint NOT NULL DEFAULT nextval('assessmentprogramgrades_id_seq'::regclass),
	assessmentprogramid bigint NOT NULL,
	gradecode character varying(25) NOT NULL,
	gradename character varying(100) NOT NULL,
	CONSTRAINT assessmentprogramgrades_pkey PRIMARY KEY (id)
	);
alter table assessmentprogramgrades add constraint assessmentprogramgrades_assessmentprogram_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;      
alter table gradecourse add column assessmentprogramgradesid bigint;
alter table gradecourse add CONSTRAINT gradecourse_assessmentprogramgrades_fk1 FOREIGN KEY (assessmentprogramgradesid)
      REFERENCES assessmentprogramgrades (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;      