-- US16142, populate mapping table for test external ids

CREATE TABLE testexternalidmap (
	testexternalid BIGINT NOT NULL,
	mappedtestexternalid BIGINT NOT NULL
);
