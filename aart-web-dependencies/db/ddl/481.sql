--ddl/481.sql

alter table scoringassignment add column ccqtestname character varying(200);
alter table scoringassignment add CONSTRAINT uk_ccqtestname UNIQUE (ccqtestname);