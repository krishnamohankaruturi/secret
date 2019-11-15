-- Function: organization_parent(bigint)

-- DROP FUNCTION organization_parent(bigint);

CREATE OR REPLACE FUNCTION organization_parent(IN childid bigint)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying) AS
$BODY$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE organizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.organizationid = parentorganization_relation.parentorganizationid)
        SELECT org.* FROM organization org where org.id in (select parentorganizationid from organization_relation);
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION organization_parent(bigint)
  OWNER TO postgres;
