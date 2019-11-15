-- ddl/597.sql

drop INDEX if exists idx_spiav_studentid_assessmentprogramid;

DROP FUNCTION if exists populatestudentpnpassessmentprogram();

alter table studentprofileitemattributevalue drop column IF EXISTS assessmentprogramid ;