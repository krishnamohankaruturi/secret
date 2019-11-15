--328.sql
CREATE TABLE public.testspecification (
    id bigserial NOT NULL, 
    externalid bigint,      
    createdate timestamp with time zone,
    modifieddate timestamp with time zone,
    specificationname character varying(100),
    phase character varying(20),
    contentpool character varying(20),
    minimumnumberofees integer,
    CONSTRAINT testspecification_pkey PRIMARY KEY (id)
);

-- Modification for EP Test table
ALTER TABLE public.test ADD testspecificationid bigint;
 
ALTER TABLE public.test ADD CONSTRAINT test_testspecificationid_fk FOREIGN KEY (testspecificationid)
REFERENCES public.testspecification (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
 
ALTER TABLE public.test DROP COLUMN IF EXISTS phase;
ALTER TABLE public.test DROP COLUMN IF EXISTS contentpool;
ALTER TABLE public.test DROP COLUMN IF EXISTS minimumnumberofees;
 
-- Modification for EP lmassessmentmodelrule table
ALTER TABLE public.lmassessmentmodelrule DROP CONSTRAINT lmassessmentmodelrule_testid_fk1;
ALTER TABLE public.lmassessmentmodelrule RENAME COLUMN testid TO testspecificationid;

Delete from public.lmassessmentmodelrule;
ALTER TABLE public.test ADD CONSTRAINT lmassessmentmodelrule_testspecificationid_fk1 FOREIGN KEY (testspecificationid)
REFERENCES public.testspecification (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;