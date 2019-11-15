
--US14897
alter table organization add column expirepasswords boolean;
alter table organization add column expirationdatetype bigint;
alter table organization add constraint org_expdatetype_fk FOREIGN KEY (expirationdatetype)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
DROP function organization_children(bigint);

CREATE OR REPLACE FUNCTION organization_children(IN parentid bigint)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying, 
		contractingorganization boolean, schoolstartdate timestamp with time zone, schoolenddate timestamp with time zone, expirepasswords boolean, expirationdatetype bigint ) AS
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
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;      