--/ddl/576.sql
ALTER TABLE firstcontactsurveysettings ADD COLUMN schoolyear bigint;
UPDATE firstcontactsurveysettings SET schoolyear=2017 where activeflag is true;
ALTER TABLE firstcontactsurveysettings DROP CONSTRAINT first_contact_survey_settings_pk;
ALTER TABLE firstcontactsurveysettings ADD CONSTRAINT first_contact_survey_settings_pk PRIMARY KEY (categoryid , organizationid,  schoolyear);
