-- US15536: Student Tracker - Simple Version 1 (preliminary)
CREATE OR REPLACE FUNCTION contracting_organization_id(IN childid bigint)
  RETURNS bigint AS
$BODY$
        WITH RECURSIVE organization_parent_tree_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE organizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_parent_tree_relation as parentorganization_parent_tree_relation
          WHERE organizationrelation.organizationid = parentorganization_parent_tree_relation.parentorganizationid)
        SELECT org.id        
         FROM organization org,organization_parent_tree_relation where
          org.activeflag=true and org.id=organization_parent_tree_relation.parentorganizationid and org.contractingorganization is true;
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION contracting_organization_id(bigint) OWNER TO aart;
GRANT EXECUTE ON FUNCTION contracting_organization_id(bigint) TO aart;
GRANT EXECUTE ON FUNCTION contracting_organization_id(bigint) TO public;
