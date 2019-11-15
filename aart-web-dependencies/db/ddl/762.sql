ALTER TABLE dailyaccesscode ALTER COLUMN gradecourseid DROP NOT null;

ALTER TABLE dailyaccesscode ADD COLUMN IF NOT EXISTS gradebandid bigint;

ALTER TABLE dailyaccesscode 
ADD CONSTRAINT dailyaccesscode_fk5 FOREIGN KEY (gradebandid)
        REFERENCES public.gradeband (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;