--dml/676.sql

CREATE OR REPLACE FUNCTION organization_parent_active_or_inactive(
    IN childid bigint,
    OUT id bigint,
    OUT organizationname character varying,
    OUT displayidentifier character varying,
    OUT organizationtypeid bigint,
    OUT welcomemessage character varying,
    OUT buildinguniqueness bigint,
    OUT schoolstartdate timestamp with time zone,
    OUT schoolenddate timestamp with time zone,
    OUT contractingorganization boolean,
    OUT expirepasswords boolean,
    OUT expirationdatetype bigint)
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
        FROM organization WHERE id IN (SELECT parentorganizationid FROM organization_relation);
 
$BODY$
  LANGUAGE sql VOLATILE;


CREATE OR REPLACE FUNCTION organization_children_active_or_inactive(IN parentid bigint)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying, contractingorganization boolean, schoolstartdate timestamp with time zone, schoolenddate timestamp with time zone, expirepasswords boolean, expirationdatetype bigint) AS
$BODY$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
        SELECT organizationid, parentorganizationid FROM organizationrelation WHERE parentorganizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.parentorganizationid = parentorganization_relation.organizationid)
          SELECT o.id, o.organizationname, o.displayidentifier, o.organizationtypeid, o.welcomemessage, o.contractingorganization, o.schoolstartdate, o.schoolenddate, o.expirepasswords,  o.expirationdatetype
          from organization o where o.id in (Select organizationid FROM organization_relation);
        $BODY$
  LANGUAGE sql VOLATILE;
  