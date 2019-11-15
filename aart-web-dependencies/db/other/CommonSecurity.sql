Select * from restriction;

Select * from restrictions_authorities;

Select * from restrictions_organizations;

select * from category;

drop view if exists resource_restriction;

create table resource_restriction as
Select
 res.restriction_name,res.restriction_code,res.restriction_description,res.restricted_resource_type_id,
 resOrgs.id as restrictions_organizations_id,resOrgs.restriction_id, resOrgs.organization_id, resOrgs.is_enforced,
 resauths.id as restrictions_authorities_id, resauths.authority_id,resauths.is_parent,resauths.is_child,
 resauths.authority_id as differential_authority_id
 from
 restrictions_organizations resOrgs,
 restrictions_authorities resAuths,
 restriction res left join restrictions_authorities diffAuths on
  (diffAuths.restriction_id = res.id and
   diffAuths.is_parent=false and 
   diffAuths.is_child=false and 
   diffAuths.is_differential = true)
 where
 res.id = resOrgs.restriction_id and
 resAuths.restriction_id = res.id and
 resauths.is_differential = false and
 organization_id=19

106

 Select * from restrictions_authorities

drop table if exists resource_restriction;
 -- the view sql.
Create view resource_restriction as 
 Select
 res.restriction_name,res.restriction_code,res.restriction_description,res.restricted_resource_type_id,
 resOrgs.id as restrictions_organizations_id,resOrgs.restriction_id, resOrgs.organization_id, resOrgs.is_enforced,
 resauths.id as restrictions_authorities_id, resauths.authority_id,resauths.is_parent,resauths.is_child,
 diffAuths.authority_id as differential_authority_id
 from
 restrictions_organizations resOrgs,
 restrictions_authorities resAuths,
 restriction res left join restrictions_authorities diffAuths on
  (diffAuths.restriction_id = res.id and
   diffAuths.is_parent=false and 
   diffAuths.is_child=false and 
   diffAuths.is_differential = true)
 where
 res.id = resOrgs.restriction_id and
 resAuths.restriction_id = res.id and
 resauths.is_differential = false;

 -- Table: resource_restriction

-- DROP TABLE resource_restriction;

CREATE TABLE resource_restriction
(
  restriction_name character varying(75) NOT NULL DEFAULT ' '::character varying,
  restriction_code character varying(75) NOT NULL DEFAULT ' '::character varying,
  restriction_description character varying(75) NOT NULL DEFAULT ' '::character varying,
  restricted_resource_type_id bigint NOT NULL,
  restrictions_organizations_id bigint NOT NULL,
  restriction_id bigint NOT NULL,
  organization_id bigint NOT NULL,
  is_enforced boolean NOT NULL,
  restrictions_authorities_id bigint NOT NULL,
  authority_id bigint NOT NULL,
  is_parent boolean NOT NULL,
  is_child boolean NOT NULL,
  differential_authority_id bigint NOT NULL
)
WITH (
  OIDS=FALSE
);
ALTER TABLE resource_restriction
  OWNER TO postgres;

---

Select * from resource_restriction;

Select * from restrictions_authorities;
