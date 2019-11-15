-- 413 sql

alter table testcutscores rename column assessmentprogram to assessmentprogramid;
alter table testcutscores rename column subject to subjectid;
alter table testcutscores rename column grade to gradeid;

 CREATE INDEX idx_testcutscores_assessmentprogramid
  ON testcutscores
  USING btree
  (assessmentprogramid);

CREATE INDEX idx_testcutscores_subjectid
  ON testcutscores
  USING btree
  (subjectid);

  CREATE INDEX idx_testcutscores_gradeid
  ON testcutscores
  USING btree
  (gradeid);

  CREATE INDEX idx_testcutscores_testid1
  ON testcutscores
  USING btree
  (testid1);

  CREATE INDEX idx_testcutscores_testid2
  ON testcutscores
  USING btree
  (testid2);

  CREATE INDEX idx_excludeditems_taskvariant
  ON excludeditems
  USING btree
  (taskvariant);

  drop table rawtoscalescore;
  
  alter table rawtoscalescores rename column assessmentprogram to assessmentprogramid;
alter table rawtoscalescores rename column subject to subjectid;
alter table rawtoscalescores rename column grade to gradeid;


CREATE INDEX idx_rawtoscalescores_assessmentprogramid
  ON rawtoscalescores
  USING btree
  (assessmentprogramid);

CREATE INDEX idx_rawtoscalescores_subjectid
  ON rawtoscalescores
  USING btree
  (subjectid);

  CREATE INDEX idx_rawtoscalescores_gradeid
  ON rawtoscalescores
  USING btree
  (gradeid);

  CREATE INDEX idx_rawtoscalescores_testid1
  ON rawtoscalescores
  USING btree
  (testid1);

  CREATE INDEX idx_rawtoscalescores_testid2
  ON rawtoscalescores
  USING btree
  (testid2);

  CREATE INDEX idx_rawtoscalescores_rawscore
  ON rawtoscalescores
  USING btree
  (rawscore);
  
  
  alter table leveldescription rename column assessmentprogram to assessmentprogramid;
alter table leveldescription rename column subject to subjectid;
alter table leveldescription rename column grade to gradeid;



CREATE INDEX idx_leveldescription_assessmentprogramid
  ON leveldescription
  USING btree
  (assessmentprogramid);

CREATE INDEX idx_leveldescription_subjectid
  ON leveldescription
  USING btree
  (subjectid);

  CREATE INDEX idx_leveldescription_gradeid
  ON leveldescription
  USING btree
  (gradeid);

  CREATE INDEX idx_leveldescription_testid1
  ON leveldescription
  USING btree
  (testid1);

  CREATE INDEX idx_leveldescription_testid2
  ON leveldescription
  USING btree
  (testid2);

alter table subscoresdescription rename column assessmentprogram to assessmentprogramid;
alter table subscoresdescription rename column subject to subjectid;

CREATE INDEX idx_subscoresdescription_assessmentprogramid
  ON subscoresdescription
  USING btree
  (assessmentprogramid);

CREATE INDEX idx_subscoresdescription_subjectid
  ON subscoresdescription
  USING btree
  (subjectid);

CREATE INDEX idx_subscoresdescription_subscoredefinitionname
  ON subscoresdescription
  USING btree
  (subscoredefinitionname);

  CREATE INDEX idx_subscoresdescription_report
  ON subscoresdescription
  USING btree
  (report);
  
  
  alter table subscoreframework rename column assessmentprogram to assessmentprogramid;
alter table subscoreframework rename column subject to subjectid;
alter table subscoreframework rename column grade to gradeid;


alter table subscoresrawtoscale rename column assessmentprogram to assessmentprogramid;
alter table subscoresrawtoscale rename column subject to subjectid;
alter table subscoresrawtoscale rename column grade to gradeid;


CREATE INDEX idx_subscoreframework_assessmentprogramid
  ON subscoreframework
  USING btree
  (assessmentprogramid);

CREATE INDEX idx_subscoreframework_subjectid
  ON subscoreframework
  USING btree
  (subjectid);

  CREATE INDEX idx_subscoreframework_gradeid
  ON subscoreframework
  USING btree
  (gradeid);
  
  CREATE INDEX idx_subscoresrawtoscale_assessmentprogramid
  ON subscoresrawtoscale
  USING btree
  (assessmentprogramid);

CREATE INDEX idx_subscoresrawtoscale_subjectid
  ON subscoresrawtoscale
  USING btree
  (subjectid);

  CREATE INDEX idx_subscoresrawtoscale_gradeid
  ON subscoresrawtoscale
  USING btree
  (gradeid);
  


  