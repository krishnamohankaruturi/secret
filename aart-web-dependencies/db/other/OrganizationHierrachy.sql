
        WITH RECURSIVE organization_relation(org_tree, parent_tree, organizationid, parentorganizationid) AS (
          SELECT '' as org_tree, NULL as parent_tree, organizationid, parentorganizationid FROM organizationrelation2 WHERE parentorganizationid = 3
          UNION
          SELECT
            parentorganization_relation.org_tree || 
              CASE parentorganization_relation.org_tree 
                WHEN '' THEN '' 
                ELSE ',' 
              END || organizationrelation2.organizationid,
            parentorganization_relation.org_tree, organizationrelation2.organizationid, organizationrelation2.parentorganizationid
          FROM organizationrelation2, organization_relation as parentorganization_relation
          WHERE organizationrelation2.parentorganizationid = parentorganization_relation.organizationid)
        SELECT * FROM organization_relation order by organizationid


--SELECT organizationid FROM organization_relation where organizationid <> 1

--get all children

-- do explain or explain analyze to verify.

        explain analyze WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE parentorganizationid = 2
          UNION all
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.parentorganizationid = parentorganization_relation.organizationid)
        SELECT * FROM organization_relation order by organizationid

        --function



        CREATE OR REPLACE FUNCTION organization_children(parentid bigint) 
        RETURNS TABLE (organizationid bigint, orgname character varying(75),displayidentifier character varying(75))
        AS $$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE parentorganizationid = $1
          UNION all
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.parentorganizationid = parentorganization_relation.organizationid)
          SELECT org.* FROM organization_relation org_rel,organization org where org.id = org_rel.organizationid;
        $$ LANGUAGE 'sql';

        --SELECT org.* FROM organization_relation org_rel,organization org where org.id = org_rel.organizationid;
        select * from organization_children(2)

--get all parents

        explain analyze WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE organizationid = 6
          UNION all
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.organizationid = parentorganization_relation.parentorganizationid)
        SELECT * FROM organization_relation order by organizationid 

--function

        CREATE OR REPLACE FUNCTION organization_parent(childid bigint) 
        RETURNS TABLE (organizationid bigint, orgname character varying(75),displayidentifier character varying(75))
        AS $$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE organizationid = $1
          UNION ALL
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.organizationid = parentorganization_relation.parentorganizationid)
        SELECT org.* FROM organization org where org.id in (select parentorganizationid from organization_relation);
        $$ LANGUAGE 'sql';

        select * from organization_parent(6);


-- TODO good enough but concerning.

--1) Quick sort.
--2) Recursive union.ok for 6 and above ?

/**
"Sort  (cost=1320.53..1340.38 rows=7939 width=16) (actual time=0.165..0.165 rows=0 loops=1)"
"  Sort Key: organization_relation.organizationid"
"  Sort Method: quicksort  Memory: 25kB"
"  CTE organization_relation"
"    ->  Recursive Union  (cost=4.32..626.77 rows=7979 width=16) (actual time=0.093..0.093 rows=0 loops=1)"
"          ->  Bitmap Heap Scan on organizationrelation  (cost=4.32..14.86 rows=9 width=16) (actual time=0.051..0.051 rows=0 loops=1)"
"                Recheck Cond: (organizationid = 10)"
"                ->  Bitmap Index Scan on organizationrelation_pk  (cost=0.00..4.32 rows=9 width=0) (actual time=0.044..0.044 rows=0 loops=1)"
"                      Index Cond: (organizationid = 10)"
"          ->  Hash Join  (cost=2.92..45.23 rows=797 width=16) (actual time=0.040..0.040 rows=0 loops=1)"
"                Hash Cond: (public.organizationrelation.organizationid = parentorganization_relation.parentorganizationid)"
"                ->  Seq Scan on organizationrelation  (cost=0.00..27.70 rows=1770 width=16) (actual time=0.006..0.006 rows=1 loops=1)"
"                ->  Hash  (cost=1.80..1.80 rows=90 width=8) (actual time=0.004..0.004 rows=0 loops=1)"
"                      Buckets: 1024  Batches: 1  Memory Usage: 0kB"
"                      ->  WorkTable Scan on organization_relation parentorganization_relation  (cost=0.00..1.80 rows=90 width=8) (actual time=0.003..0.003 rows=0 loops=1)"
"  ->  CTE Scan on organization_relation  (cost=0.00..179.53 rows=7939 width=16) (actual time=0.096..0.096 rows=0 loops=1)"
"        Filter: (organizationid <> 10)"
"Total runtime: 0.377 ms"
**/


