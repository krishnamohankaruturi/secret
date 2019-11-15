--ddl/743.sql F725 ISMART

--complexityband table schema changes to support multiple assessmentprograms

ALTER TABLE complexityband ADD COLUMN assessmentprogramid BIGINT;
ALTER TABLE complexityband ADD
CONSTRAINT complexityband_fk FOREIGN KEY (assessmentprogramid)
        REFERENCES public.assessmentprogram (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;
		
DROP INDEX IF EXISTS public.idx_complexityband_assessmentprogramid;

CREATE INDEX IF NOT EXISTS idx_complexityband_assessmentprogramid
    ON public.complexityband USING btree
    (assessmentprogramid)
    TABLESPACE pg_default;