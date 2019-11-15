Select restriction_id,organization_id,is_enforced from restrictions_organizations;

insert into restrictions_organizations(restriction_id,organization_id,is_enforced)
(Select restriction.id as restriction_id,organization.id as organization_id,true from organization,restriction);

Select * from authorities where authority like '%ROSTER%';

--none for child org.

Select * from authorities where id in (36,40);

Select restriction_id,authority_id,is_parent,is_child,is_differential from restrictions_authorities;

--36 and 40 to parent.
insert into restrictions_authorities(restriction_id,authority_id,is_parent,is_child,is_differential)
(Select restriction.id,authorities.id,true as is_parent,false as is_child,false as is_differential
 from restriction,authorities where authorities.id in (36,40));

--36,37,38,39,40,41,82 to current org/ownership org.

insert into restrictions_authorities(restriction_id,authority_id,is_parent,is_child,is_differential)
(Select restriction.id,authorities.id,false as is_parent,false as is_child,false as is_differential
 from restriction,authorities where authorities.id in (36,37,38,39,40,41,82));

 --none for child.

 --85 for differential permission.

insert into restrictions_authorities(restriction_id,authority_id,is_parent,is_child,is_differential)
(Select restriction.id,authorities.id,false as is_parent,false as is_child,true as is_differential
 from restriction,authorities where authorities.id in (85));

Select * from restrictions_authorities;