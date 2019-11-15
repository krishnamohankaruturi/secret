-- To provide sorting option
alter table pnpaccomodations add column sortorder bigint;
-- To have multiple PNP settings for student for multiple APs
alter table studentprofileitemattributevalue add column assessmentprogramid bigint;

DROP TABLE IF EXISTS ksdexmlaudit;
