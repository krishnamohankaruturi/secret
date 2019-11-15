--ddl/694.sql

ALTER TABLE organization ADD COLUMN timezoneid BIGINT;
ALTER TABLE organization ADD CONSTRAINT organization_timezone_fk FOREIGN KEY (timezoneid)
	REFERENCES public.category (id) MATCH SIMPLE
	ON UPDATE NO ACTION
	ON DELETE NO ACTION;

ALTER TABLE organization ADD COLUMN observesdaylightsavings BOOLEAN NOT NULL DEFAULT TRUE;

