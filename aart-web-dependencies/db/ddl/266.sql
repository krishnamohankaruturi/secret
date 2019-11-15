
--US14894
ALTER TABLE module ADD COLUMN requiredflag boolean;
ALTER TABLE module ALTER COLUMN requiredflag SET DEFAULT false;