--blank sql to execute dml.

--AART relted table
--Original 15.sql
--blank sql created for the username fix. TODO merge it after it is executed in all environments.

  
--AART relted table
--Original 16.sql
 CREATE OR REPLACE FUNCTION organization_children(IN parentid bigint)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying) AS
$BODY$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
        SELECT organizationid, parentorganizationid FROM organizationrelation WHERE parentorganizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.parentorganizationid = parentorganization_relation.organizationid)
          SELECT * from organization where id in (Select organizationid FROM organization_relation) ;
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
  
  
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
  
  
--AART relted table
--Original 17.sql
update fieldspecification set fieldlength = 60 where fieldname = 'firstName' or fieldName = 'lastName';  