
--DE7312
update student set secondarydisabilitycode = null where secondarydisabilitycode is not null;
alter table student drop column secondarydisabilitycode;