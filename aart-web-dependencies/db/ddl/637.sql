--dml/637.sql
-- Index: idx_helpcontent_activeflag
CREATE INDEX idx_helpcontent_activeflag
  ON helpcontent
  USING btree
  (activeflag);

-- Index: idx_helpcontent_expiredate
CREATE INDEX idx_helpcontent_expiredate
  ON helpcontent
  USING btree
  (expiredate);

-- Index: idx_helpcontent_helptopicid
CREATE INDEX idx_helpcontent_helptopicid
  ON helpcontent
  USING btree
  (helptopicid);

-- Index: idx_helpcontent_status
CREATE INDEX idx_helpcontent_status
  ON helpcontent
  USING btree
  (status COLLATE pg_catalog."default");


-- Index: idx_helpcontentcontext_activeflag
CREATE INDEX idx_helpcontentcontext_activeflag
  ON helpcontentcontext
  USING btree
  (activeflag);

-- Index: idx_helpcontentcontext_assessmentprogramid
CREATE INDEX idx_helpcontentcontext_assessmentprogramid
  ON helpcontentcontext
  USING btree
  (assessmentprogramid);

-- Index: idx_helpcontentcontext_helpcontentid
CREATE INDEX idx_helpcontentcontext_helpcontentid
  ON helpcontentcontext
  USING btree
  (helpcontentid);

-- Index: idx_helpcontentcontext_rolesid
CREATE INDEX idx_helpcontentcontext_rolesid
  ON helpcontentcontext
  USING btree
  (rolesid);

-- Index: idx_helpcontentcontext_stateid
CREATE INDEX idx_helpcontentcontext_stateid
  ON helpcontentcontext
  USING btree
  (stateid);


-- Index: idx_helpcontentslug_activeflag
CREATE INDEX idx_helpcontentslug_activeflag
  ON helpcontentslug
  USING btree
  (activeflag);

-- Index: idx_helpcontentslug_helpcontentid
CREATE INDEX idx_helpcontentslug_helpcontentid
  ON helpcontentslug
  USING btree
  (helpcontentid);

-- Index: idx_helpcontentslug_url
CREATE INDEX idx_helpcontentslug_url
  ON helpcontentslug
  USING btree
  (url COLLATE pg_catalog."default");

-- Index: idx_helpcontenttags_activeflag
CREATE INDEX idx_helpcontenttags_activeflag
  ON helpcontenttags
  USING btree
  (activeflag);

-- Index: idx_helpcontenttags_helpcontentid
CREATE INDEX idx_helpcontenttags_helpcontentid
  ON helpcontenttags
  USING btree
  (helpcontentid);

-- Index: idx_helpcontenttags_helptagid
CREATE INDEX idx_helpcontenttags_helptagid
  ON helpcontenttags
  USING btree
  (helptagid);

-- Index: idx_helptags_activeflag
CREATE INDEX idx_helptags_activeflag
  ON helptags
  USING btree
  (activeflag);

-- Index: idx_helptags_tag
CREATE INDEX idx_helptags_tag
  ON helptags
  USING btree
  (tag COLLATE pg_catalog."default");

-- Index: idx_helptopic_activeflag
CREATE INDEX idx_helptopic_activeflag
  ON helptopic
  USING btree
  (activeflag);

-- Index: idx_helptopicslug_activeflag
CREATE INDEX idx_helptopicslug_activeflag
  ON helptopicslug
  USING btree
  (activeflag);

-- Index: idx_helptopicslug_helptopicid
CREATE INDEX idx_helptopicslug_helptopicid
  ON helptopicslug
  USING btree
  (helptopicid);
  
--dml/635.sql

-- inexes on scoring tables
CREATE INDEX idx_scoringassignment_testsessionid
ON scoringassignment
USING btree
(testsessionid);

CREATE INDEX idx_scoringassignment_rosterid
ON scoringassignment
USING btree
(rosterid);

--
CREATE INDEX idx_scoringassignment_ccqtestname
ON scoringassignment
USING btree
(ccqtestname);

CREATE INDEX idx_scoringassignment_activeflag
ON scoringassignment
USING btree
(activeflag);
--
CREATE INDEX idx_scoringassignmentstudent_studentstestsid
ON scoringassignmentstudent
USING btree
(studentstestsid);

CREATE INDEX idx_scoringassignmentstudent_studentid
ON scoringassignmentstudent
USING btree
(studentid);

CREATE INDEX idx_scoringassignmentstudent_scoringassignmentid
ON scoringassignmentstudent
USING btree
(scoringassignmentid);

CREATE INDEX idx_scoringassignmentstudent_activeflag
ON scoringassignmentstudent
USING btree
(activeflag);

CREATE INDEX idx_scoringassignmentscorer_scoringassignmentid
ON scoringassignmentscorer
USING btree
(scoringassignmentid);

CREATE INDEX idx_scoringassignmentscorer_scorerid
ON scoringassignmentscorer
USING btree
(scorerid);

CREATE INDEX idx_scoringassignmentscorer_activeflag
ON scoringassignmentscorer
USING btree
(activeflag);

CREATE INDEX idx_ccqscoreitem_ccqscoreid
ON ccqscoreitem
USING btree
(ccqscoreid);

CREATE INDEX idx_ccqscoreitem_rubriccategoryid
ON ccqscoreitem
USING btree
(rubriccategoryid);

CREATE INDEX idx_ccqscoreitem_activeflag
ON ccqscoreitem
USING btree
(activeflag);

CREATE INDEX idx_ccqscoreitem_taskvariantid
ON ccqscoreitem
USING btree
(taskvariantid);

CREATE INDEX idx_ccqscore_scoringassignmentstudentid
ON ccqscore
USING btree
(scoringassignmentstudentid);

CREATE INDEX idx_ccqscore_scoringassignmentscorerid
ON ccqscore
USING btree
(scoringassignmentscorerid);

CREATE INDEX idx_ccqscore_activeflag
ON ccqscore
USING btree
(activeflag);

-- DE15269 ---
ALTER TABLE scoringuploadfile ALTER COLUMN filename TYPE text;

DROP INDEX IF EXISTS idx_studentstests_transferedenrollmentid;
CREATE INDEX idx_studentstests_transferedenrollmentid
  ON studentstests
  USING btree
  (transferedenrollmentid);
  