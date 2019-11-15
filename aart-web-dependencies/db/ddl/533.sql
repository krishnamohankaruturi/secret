--For dml/*.sql
--Remove TestID columns
alter table testcutscores drop column testid1;
alter table testcutscores drop column testid2;

--Addition of 6 columns: TestID_3, TestID_4, Performance_TestID, Performance_Subject, Include_Performance_items_in_RawScore, Performance_item_weight

alter table rawtoscalescores add column testid3 bigint;
alter table rawtoscalescores add column testid4 bigint;
alter table rawtoscalescores add column performance_testid bigint;
alter table rawtoscalescores add column performance_subjectid bigint;
alter table rawtoscalescores add column performance_rawscore_include_flag boolean;
alter table rawtoscalescores add column performance_item_weight numeric;

CREATE INDEX idx_rawtoscalescores_testid3
  ON rawtoscalescores
  USING btree
  (testid3);

CREATE INDEX idx_rawtoscalescores_testid4
  ON rawtoscalescores
  USING btree
  (testid4);
  
CREATE INDEX idx_rawtoscalescores_performance_testid
  ON rawtoscalescores
  USING btree
  (performance_testid);
  
CREATE INDEX idx_rawtoscalescores_performance_subjectid
  ON rawtoscalescores
  USING btree
  (performance_subjectid);

CREATE INDEX idx_rawtoscalescores_performance_rawscore_include_flag
  ON rawtoscalescores
  USING btree
  (performance_rawscore_include_flag);
  
CREATE INDEX idx_rawtoscalescores_performance_item_weight
  ON rawtoscalescores
  USING btree
  (performance_item_weight);  