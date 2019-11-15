-- Function: organization_children(bigint)

-- DROP FUNCTION organization_children(bigint);

CREATE OR REPLACE FUNCTION organization_children(IN parentid bigint)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying) AS
$BODY$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE parentorganizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as childorganization_relation
          WHERE organizationrelation.parentorganizationid = childorganization_relation.organizationid)
          SELECT org.* FROM organization_relation org_rel,organization org where org.id = org_rel.organizationid;
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION organization_children(bigint)
  OWNER TO postgres;
