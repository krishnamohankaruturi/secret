-- 573.sql
-- 573.sql
 --Table For firstcontactsurveysettings. Per US17690
CREATE TABLE firstcontactsurveysettings
(
  categoryid bigint NOT NULL,
  organizationid bigint NOT NULL,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT first_contact_survey_settings_pk PRIMARY KEY (categoryid , organizationid ),
  CONSTRAINT first_contact_survey_category_Id_fkey FOREIGN KEY (categoryid)
      REFERENCES category (id),
  CONSTRAINT first_contact_survey_organization_Id_fkey FOREIGN KEY (organizationid)
      REFERENCES organization (id),
  CONSTRAINT first_contact_survey_createduser_fkey FOREIGN KEY (createduser)
      REFERENCES aartuser (id),
  CONSTRAINT first_contact_survey_modifieduser_fkey FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id)
);

-- New column added to support order of the tabs.
Alter table surveysection add sectionorder bigint;

-- Add new column for labeltype
alter table surveylabel add labeltype bigint;
alter table surveylabel add constraint cfk_surveylabel_labeltype foreign key(labeltype) REFERENCES category (id);

-- complexityband participation flag in surveylabel table
alter table surveylabel add complexityband boolean;

-- US18081
-- Add new column to support DLM science option
ALTER TABLE firstcontactsurveysettings ADD COLUMN scienceflag boolean;

--US18044
-- Column to hold the survey setting while last edit session.
alter table survey add column lasteditedoption bigint;
alter table survey add foreign key (lasteditedoption) references category(id);

 -- DE13403
alter table surveysection add column instructionnote varchar(125);