-- US15586: DLM Test Administration Data Extract
CREATE TABLE studentsteststags
(
  id bigserial NOT NULL,
  testletid bigserial NOT NULL,
  tagdata text,
  CONSTRAINT studentsteststags_pkey PRIMARY KEY (id)
);
