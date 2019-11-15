--ddl/504.sql empty
-- Drop existing requireconfirmaion column from specialcircumstance
alter table specialcircumstance drop column requireconfirmation ;

-- Add status for saving special circumstance status for student
alter table studentspecialcircumstance add column status bigint references category(id);

-- Add approvedby for approval auditing
alter table studentspecialcircumstance add column approvedby bigint references aartuser(id);
