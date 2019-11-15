--ddl/*.sql ==> For dml/587.sql
alter table interimtest add column organizationid BIGINT;
alter table interimgroup add column organizationid BIGINT;
