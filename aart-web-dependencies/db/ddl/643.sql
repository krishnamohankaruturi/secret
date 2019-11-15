-- F460 Dynamic Bundled reports

CREATE TABLE organizationbundledreportsprocess
(
   id bigserial, 
   organizationid bigint, 
   status bigint, 
   assessmentprogramid bigint, 
   schoolyear bigint, 
   activeflag boolean, 
   createddate timestamp with time zone, 
   createduser bigint, 
   modifieddate timestamp with time zone, 
   modifieduser bigint, 
   byschool boolean, 
   separatefile boolean,
   schoolids text, 
   subjects text, 
   grades text,
   schoolnames text,
   subjectnames text,
   gradenames text, 
   sort1 text, 
   sort2 text, 
   sort3 text, 
   CONSTRAINT organizationbundledreportsprocess_pkey PRIMARY KEY (id)
);

ALTER TABLE organizationreportdetails DROP CONSTRAINT organizationreportdetails_batchreportprocessid_fk;

CREATE INDEX idx_organizationbundledreportsprocess_id
  ON organizationbundledreportsprocess
  USING btree 
  (id);

CREATE INDEX idx_organizationbundledreportsprocess_organizationid
  ON organizationbundledreportsprocess
  USING btree
  (organizationid);

CREATE INDEX idx_organizationbundledreportsprocess_status
  ON organizationbundledreportsprocess
  USING btree
  (status);

CREATE INDEX idx_organizationbundledreportsprocess_assessmentprogramid
  ON organizationbundledreportsprocess
  USING btree
  (assessmentprogramid);

CREATE INDEX idx_organizationbundledreportsprocess_schoolyear
  ON organizationbundledreportsprocess
  USING btree
  (schoolyear);

CREATE INDEX idx_organizationbundledreportsprocess_activeflag
  ON organizationbundledreportsprocess
  USING btree
  (activeflag);
