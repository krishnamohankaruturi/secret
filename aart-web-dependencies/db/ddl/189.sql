
--DE6054 need to get new org fields added to this function
DROP FUNCTION IF EXISTS organization_parent(bigint);

CREATE OR REPLACE FUNCTION organization_parent(IN childid bigint, OUT id bigint, OUT organizationname CHARACTER VARYING, OUT displayidentifier CHARACTER VARYING, OUT organizationtypeid bigint, OUT welcomemessage CHARACTER VARYING, OUT buildinguniqueness bigint, OUT schoolstartdate TIMESTAMP WITH TIME ZONE, OUT schoolenddate TIMESTAMP WITH TIME ZONE)
RETURNS SETOF record AS

$BODY$

WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE organizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation AS parentorganization_relation
          WHERE organizationrelation.organizationid = parentorganization_relation.parentorganizationid)
        SELECT id, organizationname, displayidentifier, organizationtypeid, welcomemessage, buildinguniqueness, schoolstartdate, schoolenddate FROM organization WHERE id IN (SELECT parentorganizationid FROM organization_relation);

$BODY$

LANGUAGE sql VOLATILE
COST 100;
ALTER FUNCTION organization_parent(bigint)
  OWNER TO aart;
GRANT EXECUTE ON FUNCTION organization_parent(bigint) TO aart;
GRANT EXECUTE ON FUNCTION organization_parent(bigint) TO public;