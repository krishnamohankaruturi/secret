--1. delete all users of Nebraska.

delete from aartuser where id in
(
select ug.aartuserid from usergroups ug, groups g, organization o
 where
 ug.groupid = g.id and
 o.id = g.organizationid and
  o.id in ( Select id from organization_children( (select id from organization where organizationname='Mississippi') ) )
);

delete from usergroups where groupid in
(
select g.id from groups g, organization o
 where
 o.id = g.organizationid and
  o.id in ( Select id from organization_children( (select id from organization where organizationname='Mississippi') ) )
);

delete from usergroups where groupid in
(
select g.id from groups g, organization o
 where
 o.id = g.organizationid and
  organizationname='Mississippi'
);

--2. delete all authorities of all roles of Mississippi.
delete from groupauthorities where groupid in
(
select g.id from groups g, organization o
 where
 o.id = g.organizationid and
  o.id in ( Select id from organization_children( (select id from organization where organizationname='Mississippi') ) )
);

delete from groupauthorities where groupid in
(
select g.id from groups g, organization o
 where
 o.id = g.organizationid and
  organizationname='Mississippi'
);

--3. delete all roles of Mississippi and its children.

delete from groups where organizationid in
(
Select id from organization_children( (select id from organization where organizationname='Mississippi') )
);

delete from groups where organizationid in
(
select id from organization where organizationname='Mississippi'
);


--4. remove all assessment programs added to Mississippi and its children
delete from orgassessmentprogram where organizationid in
(
Select id from organization_children( (select id from organization where organizationname='Mississippi') )
);

delete from orgassessmentprogram where organizationid in
(
select id from organization where organizationname='Mississippi'
);

--5. remove the restrictions for the organizations to be deleted.

delete from restrictionsorganizations where organizationid in (
Select id from organization_children( (select id from organization where organizationname='Mississippi') )
);

delete from restrictionsorganizations where organizationid in (
select id from organization where organizationname='Mississippi' 
);

--5. delete all children of Mississippi.

-- not used.
delete from policy;

--Assumption is no rosters or enrollments are created for Mississippi in prod.

-- delete the relations.

--these constraints are needed because once the relation is deleted it is not possible to identify 
--which organizations are related to each other.

--ddl is here because you cannot delete the relation and then find who is related.

ALTER TABLE organizationrelation DROP CONSTRAINT organization_fk;
ALTER TABLE organizationrelation DROP CONSTRAINT parent_organization_fk;
ALTER TABLE organizationrelation ADD CONSTRAINT organization_fk FOREIGN KEY (organizationid)
 REFERENCES organization (id) ON UPDATE NO ACTION ON DELETE CASCADE;
ALTER TABLE organizationrelation ADD CONSTRAINT parent_organization_fk FOREIGN KEY (parentorganizationid)
 REFERENCES organization (id) ON UPDATE NO ACTION ON DELETE CASCADE;

delete from organization where
 id in (Select id from organization_children( (select id from organization where organizationname='Mississippi') ));

--6. delete Mississippi.
delete from organization where organizationname='Mississippi';

ALTER TABLE organizationrelation DROP CONSTRAINT organization_fk;
ALTER TABLE organizationrelation DROP CONSTRAINT parent_organization_fk;
ALTER TABLE organizationrelation ADD CONSTRAINT organization_fk FOREIGN KEY (organizationid)
 REFERENCES organization (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE organizationrelation ADD CONSTRAINT parent_organization_fk FOREIGN KEY (parentorganizationid)
 REFERENCES organization (id) ON UPDATE NO ACTION ON DELETE NO ACTION;