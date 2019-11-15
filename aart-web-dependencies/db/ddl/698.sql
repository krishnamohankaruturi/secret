-- F510: F510 Student Tracker Enhancements
ALTER TABLE student ADD COLUMN writingbandid BIGINT;

ALTER TABLE student ADD CONSTRAINT writingbandid_student_fk FOREIGN KEY (writingbandid) REFERENCES category(id);

ALTER TABLE studenttrackerband DROP CONSTRAINT fk_studenttrackerband_complexityband;

ALTER TABLE complexityband DROP CONSTRAINT complexityband_pkey;

ALTER TABLE complexityband ADD CONSTRAINT complexityband_pkey PRIMARY KEY (id, bandcode);
