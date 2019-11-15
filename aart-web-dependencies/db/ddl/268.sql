
--US14898
alter table aartuser add column lastexpiredpasswordresetdate timestamp with time zone;

DROP FUNCTION organization_parent(bigint);

CREATE OR REPLACE FUNCTION organization_parent(IN childid bigint, OUT id bigint, OUT organizationname character varying, OUT displayidentifier character varying, 
	OUT organizationtypeid bigint, OUT welcomemessage character varying, OUT buildinguniqueness bigint, OUT schoolstartdate timestamp with time zone, 
	OUT schoolenddate timestamp with time zone, OUT contractingorganization boolean, OUT expirepasswords boolean, OUT expirationdatetype bigint)
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
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;