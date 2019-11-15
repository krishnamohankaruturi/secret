--Changes from CB
ALTER TABLE rubriccategory ADD rubricminscore bigint;
ALTER TABLE rubriccategory ADD rubricmaxscore bigint;
ALTER TABLE itemstatistic ALTER COLUMN itemstatisticname TYPE character varying(35);