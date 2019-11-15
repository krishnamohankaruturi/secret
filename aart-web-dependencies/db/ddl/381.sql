--US15823, Questar Scoring Info

CREATE TABLE studentresponsescore (
	studentstestsectionsid BIGINT NOT NULL,
	taskvariantid BIGINT NOT NULL,
	score NUMERIC(6,3),
	dimension VARCHAR(25),
	diagnosticstatement TEXT,
	raterid BIGINT,
	ratername TEXT,
	raterorder SMALLINT,
	raterexposure INTEGER,
	createdate TIMESTAMP WITHOUT TIME ZONE,
	modifieddate TIMESTAMP WITHOUT TIME ZONE,
	activeflag BOOLEAN NOT NULL DEFAULT TRUE,
	CONSTRAINT studentresponsescore_pkey PRIMARY KEY (studentstestsectionsid, taskvariantid, raterid, dimension)
);

CREATE TABLE studentresponsescorerater (
	raterid BIGINT NOT NULL,
	userid BIGINT NOT NULL,
	CONSTRAINT studentresponsescorerater_fkey1 FOREIGN KEY (userid)
		REFERENCES aartuser (id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);
