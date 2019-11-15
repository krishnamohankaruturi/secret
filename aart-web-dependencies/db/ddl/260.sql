
--US14846
alter table student add column secondarydisabilitycode character varying(2);
alter table ksdexmlaudit add column errors text;
alter table ksdexmlaudit drop constraint if exists ksdexmlaudit_pkey;
alter table ksdexmlaudit add CONSTRAINT ksdexmlaudit_pkey PRIMARY KEY (id);