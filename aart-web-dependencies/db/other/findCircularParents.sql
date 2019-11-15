Select * from
 organizationrelation orgRel, organization org, organization parentorg,organizationtype orgType,organizationtype parentOrgType
where
orgRel.organizationid=org.id and
orgRel.parentorganizationid = parentOrg.id and
orgType.id = org.organizationtypeid and
parentOrgType.id = parentOrg.organizationtypeid and
orgType.typeLevel > parentOrgType.typeLevel

Select * from 
organizationrelation orgrel,
(Select org.id as organizationid,orgType.* from organization org, organizationtype orgType where org.organizationtypeid = orgType.id) org,
(Select parentorg.id as parentorganizationid,parentOrgType.* from organization parentOrg, organizationtype parentOrgType where parentOrg.organizationtypeid = parentOrgType.id) parent_org
where
orgrel.organizationid = org.id and
orgrel.parentorganizationid = parent_org.parentorganizationid and
parent_org.typelevel > org.typelevel

Select * from organization where id in (6,2808,2618,1689);

--delete from organizationrelation where organizationid=6 and parentorganizationid in (2808,2618,1689);

--the gremlin.
--SELECT * FROM organization_children(11)

Select org.*,orgType.id as organization_type_id,orgType.typename,orgType.typelevel,
(Select orgType2.typelevel from organizationtype orgType2,organization org2 where orgType2.id = org2.Organizationtypeid and org2.id = orgRel.parentorganizationid) as parentOrgTypelevel
from organizationrelation orgRel,organization org, organizationtype orgType
 where
 orgRel.organizationid = org.id and
 org.organizationtypeid = orgType.id

Select a.* from 
 (Select org.*,orgType.id as organization_type_id,orgType.typename,orgType.typelevel,
(Select orgType2.typelevel from organizationtype orgType2,organization org2 where orgType2.id = org2.Organizationtypeid and org2.id = orgRel.parentorganizationid) as parentOrgTypelevel
from organizationrelation orgRel,organization org, organizationtype orgType
 where
 orgRel.organizationid = org.id and
 org.organizationtypeid = orgType.id) a where a.parentOrgTypelevel >= typelevel ;

--final
 Select org.*,orgType.id as organization_type_id,orgType.typelevel,
 parentOrgAndType.*
from organizationrelation orgRel,organization org, organizationtype orgType,
(Select org2.*,orgType2.id as organization_type_id,orgType2.typelevel from organizationtype orgType2,organization org2 where orgType2.id = org2.Organizationtypeid) parentOrgAndType
 where
 orgRel.organizationid = org.id and
 orgRel.parentorganizationid = parentOrgAndType.id and
 org.organizationtypeid = orgType.id and
 parentOrgAndType.typelevel >= orgType.typelevel;

--deletion.

--run this if you don't want to do back up.

create table org_rel_backup as 
 Select org.id as organizationid,parentOrgAndType.id as parentorganizationid
from organizationrelation orgRel,organization org, organizationtype orgType,
(Select org2.*,orgType2.id as organization_type_id,orgType2.typelevel from organizationtype orgType2,organization org2 where orgType2.id = org2.Organizationtypeid) parentOrgAndType
 where
 orgRel.organizationid = org.id and
 orgRel.parentorganizationid = parentOrgAndType.id and
 org.organizationtypeid = orgType.id and
 parentOrgAndType.typelevel >= orgType.typelevel;


--delete these circular relationships.

delete from organizationrelation where (organizationid,parentorganizationid) in 
(
 Select org.id as organizationid,parentOrgAndType.id as parentorganizationid
from organizationrelation orgRel,organization org, organizationtype orgType,
(Select org2.*,orgType2.id as organization_type_id,orgType2.typelevel from organizationtype orgType2,organization org2 where orgType2.id = org2.Organizationtypeid) parentOrgAndType
 where
 orgRel.organizationid = org.id and
 orgRel.parentorganizationid = parentOrgAndType.id and
 org.organizationtypeid = orgType.id and
 parentOrgAndType.typelevel >= orgType.typelevel
);
 