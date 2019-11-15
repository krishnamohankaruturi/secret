
--US15814

alter table complexityband add column contentareaid bigint;

CREATE INDEX idx_complexityband_contentareaid
  ON complexityband USING btree (contentareaid);  