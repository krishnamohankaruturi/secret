--DDL for F400: EP Sensitive text needs Display For Reading Only -- 
DROP TABLE IF EXISTS readingessentialelements;
DROP SEQUENCE IF EXISTS readingessentialelements_id_seq;
CREATE SEQUENCE readingessentialelements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
CREATE TABLE readingessentialelements(id BIGINT NOT NULL DEFAULT nextval('readingessentialelements_id_seq'::regclass), 
	essentialelement TEXT, 
	systemcode TEXT, 
	parentcode TEXT, 
	gradeabbrname TEXT);
	
ALTER TABLE readingessentialelements ADD CONSTRAINT readingessentialelements_pk PRIMARY KEY(id);

ALTER TABLE accessibilityfile add brailletypeabbr varchar;

-- Auto assignment feature for Interim changes.
CREATE TABLE autoassigninterim
(
  id bigserial,
  gradecourseid bigint,
  contentareaid bigint,
  rosterid bigint,
  testsessionid bigint,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser integer,
  activeflag boolean DEFAULT true,
  modifieduser integer,
  unique(gradecourseid,contentareaid,rosterid,testsessionid),
  CONSTRAINT autoassigninterim_pkey PRIMARY KEY (id),
  CONSTRAINT autoassigninterim_fkey FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT autoassigninterim_gradecourseid_fkey FOREIGN KEY (gradecourseid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT autoassigninterim_rosterid_fkey FOREIGN KEY (rosterid)
      REFERENCES roster (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT autoassigninterim_testsessionid_fkey FOREIGN KEY (testsessionid)
      REFERENCES testsession (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE testsession ADD suspend boolean DEFAULT false; 

--F145 operational and data ware house 
ALTER TABLE organization ADD COlUMN testingmodel bigint;

-- Function: organization_parent_active_or_inactive(bigint)

-- DROP FUNCTION organization_parent_active_or_inactive(bigint);

CREATE OR REPLACE FUNCTION organization_parent_active_or_inactive(IN childid bigint, OUT id bigint, OUT organizationname character varying, OUT displayidentifier character varying, OUT organizationtypeid bigint, OUT welcomemessage character varying, OUT buildinguniqueness bigint, OUT schoolstartdate timestamp with time zone, OUT schoolenddate timestamp with time zone, OUT contractingorganization boolean, OUT expirepasswords boolean, OUT expirationdatetype bigint)
  RETURNS SETOF record AS
$BODY$
 
WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE organizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation AS parentorganization_relation
          WHERE organizationrelation.organizationid = parentorganization_relation.parentorganizationid)
        SELECT id, organizationname, displayidentifier, organizationtypeid, welcomemessage, buildinguniqueness, schoolstartdate, schoolenddate, contractingorganization, expirepasswords, expirationdatetype
        FROM organization WHERE  id IN (SELECT parentorganizationid FROM organization_relation);
 
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

  
  --F565
  alter table interimtest add column currentschoolyear bigint; 