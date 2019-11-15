--dml/635.sql
-- Defines the hierarchy which enables who can update users below their level
ALTER TABLE  groups add column hierarchy bigint;