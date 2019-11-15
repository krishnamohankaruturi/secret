--enrollment table DDL script
ALTER TABLE enrollment add COLUMN accountabilitydistrictidentifier varchar(100);
ALTER TABLE enrollment add COLUMN accountabilitydistrictid bigint;