
--US15174 CB
ALTER TABLE taskvariant ADD COLUMN rubrictype character varying(20);
ALTER TABLE taskvariant ADD COLUMN rubricminscore bigint;
ALTER TABLE taskvariant ADD COLUMN rubricmaxscore bigint;