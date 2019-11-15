-- To Add table auditing columns in organization table

ALTER TABLE organization ADD COLUMN createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone;

ALTER TABLE organization ADD COLUMN createduser character varying(50) NOT NULL DEFAULT 'cetesysadmin';

ALTER TABLE organization ADD COLUMN activeflag boolean DEFAULT true;

ALTER TABLE organization ADD COLUMN modifieddate timestamp without time zone;

ALTER TABLE organization ADD COLUMN modifieduser character varying(50);

ALTER TABLE organization ADD CONSTRAINT fk_Org_created_user FOREIGN KEY (createduser)
      
REFERENCES aartuser (username) MATCH SIMPLE
      
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE organization ADD CONSTRAINT fk_Org_updated_user FOREIGN KEY (modifieduser)
      
REFERENCES aartuser (username) MATCH SIMPLE
      
ON UPDATE NO ACTION ON DELETE NO ACTION;


-- To Add table auditing columns in organizationrelation table

ALTER TABLE organizationrelation ADD COLUMN createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone;

ALTER TABLE organizationrelation ADD COLUMN createduser character varying(50) NOT NULL DEFAULT 'cetesysadmin';

ALTER TABLE organizationrelation ADD COLUMN activeflag boolean DEFAULT true;

ALTER TABLE organizationrelation ADD COLUMN modifieddate timestamp without time zone;

ALTER TABLE organizationrelation ADD COLUMN modifieduser character varying(50);

ALTER TABLE organizationrelation ADD CONSTRAINT fk_OrgRel_created_user FOREIGN KEY (createduser)
      
REFERENCES aartuser (username) MATCH SIMPLE
      
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE organizationrelation ADD CONSTRAINT fk_OrgRel_updated_user FOREIGN KEY (modifieduser)
      
REFERENCES aartuser (username) MATCH SIMPLE
      
ON UPDATE NO ACTION ON DELETE NO ACTION;


-- To Add table auditing columns in groups table

ALTER TABLE groups ADD COLUMN createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone;

ALTER TABLE groups ADD COLUMN createduser character varying(50) NOT NULL DEFAULT 'cetesysadmin';

ALTER TABLE groups ADD COLUMN activeflag boolean DEFAULT true;

ALTER TABLE groups ADD COLUMN modifieddate timestamp without time zone;

ALTER TABLE groups ADD COLUMN modifieduser character varying(50);

ALTER TABLE groups ADD CONSTRAINT fk_Grp_created_user FOREIGN KEY (createduser)
      
REFERENCES aartuser (username) MATCH SIMPLE
      
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE groups ADD CONSTRAINT fk_Grp_updated_user FOREIGN KEY (modifieduser)
      
REFERENCES aartuser (username) MATCH SIMPLE
      
ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Modified as a result of addition of Table auditing columns

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
          SELECT id, organizationname, displayidentifier, organizationtypeid, welcomemessage from organization where id in (Select organizationid FROM organization_relation);
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
        SELECT id, organizationname, displayidentifier, organizationtypeid, welcomemessage FROM organization where id in (select parentorganizationid from organization_relation);
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
  
--INFO: AART related tables original 19.sql

-- Changes in table auditing columns for organization table


update organization set modifieddate=('now'::text)::timestamp without time zone;
ALTER TABLE organization ALTER COLUMN modifieddate SET NOT NULL;
 

ALTER TABLE organization DROP CONSTRAINT fk_Org_created_user;
ALTER TABLE organization DROP COLUMN createduser;

ALTER TABLE organization ADD COLUMN createduser Integer;
UPDATE organization set createduser = (select id from aartuser where username='cetesysadmin');
ALTER TABLE organization ALTER COLUMN createduser SET NOT NULL;

ALTER TABLE organization ADD CONSTRAINT fk_Org_created_user FOREIGN KEY (createduser)      
REFERENCES aartuser (id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;


ALTER TABLE organization DROP CONSTRAINT fk_Org_updated_user;
ALTER TABLE organization DROP COLUMN modifieduser;

ALTER TABLE organization ADD COLUMN modifieduser Integer;
UPDATE organization set modifieduser = (select id from aartuser where username='cetesysadmin');
ALTER TABLE organization ALTER COLUMN modifieduser SET NOT NULL;

ALTER TABLE organization ADD CONSTRAINT fk_Org_updated_user FOREIGN KEY (modifieduser)      
REFERENCES aartuser (id) MATCH SIMPLE      
ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changes in table auditing columns for organizationrelation table

update organizationrelation set modifieddate=('now'::text)::timestamp without time zone;
ALTER TABLE organizationrelation ALTER COLUMN modifieddate SET NOT NULL;

ALTER TABLE organizationrelation DROP CONSTRAINT fk_OrgRel_created_user;
ALTER TABLE organizationrelation DROP COLUMN createduser;

ALTER TABLE organizationrelation ADD COLUMN createduser Integer;
UPDATE organizationrelation set createduser = (select id from aartuser where username='cetesysadmin');
ALTER TABLE organizationrelation ALTER COLUMN createduser SET NOT NULL;

ALTER TABLE organizationrelation ADD CONSTRAINT fk_OrgRel_created_user FOREIGN KEY (createduser)      
REFERENCES aartuser (id) MATCH SIMPLE      
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE organizationrelation DROP CONSTRAINT fk_OrgRel_updated_user;
ALTER TABLE organizationrelation DROP COLUMN modifieduser;

ALTER TABLE organizationrelation ADD COLUMN modifieduser Integer;
UPDATE organizationrelation set modifieduser = (select id from aartuser where username='cetesysadmin');
ALTER TABLE organizationrelation ALTER COLUMN modifieduser SET NOT NULL;

ALTER TABLE organizationrelation ADD CONSTRAINT fk_OrgRel_updated_user FOREIGN KEY (modifieduser)      
REFERENCES aartuser (id) MATCH SIMPLE      
ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Changes in table auditing columns for groups table

update groups set modifieddate=('now'::text)::timestamp without time zone;
ALTER TABLE groups ALTER COLUMN modifieddate SET NOT NULL;

ALTER TABLE groups DROP CONSTRAINT fk_Grp_created_user;
ALTER TABLE groups DROP COLUMN createduser;

ALTER TABLE groups ADD COLUMN createduser Integer;
UPDATE groups set createduser = (select id from aartuser where username='cetesysadmin');
ALTER TABLE groups ALTER COLUMN createduser SET NOT NULL;

ALTER TABLE groups ADD CONSTRAINT fk_Grp_created_user FOREIGN KEY (createduser)      
REFERENCES aartuser (id) MATCH SIMPLE      
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE groups DROP CONSTRAINT fk_Grp_updated_user;
ALTER TABLE groups DROP COLUMN modifieduser;

ALTER TABLE groups ADD COLUMN modifieduser Integer;
UPDATE groups set modifieduser = (select id from aartuser where username='cetesysadmin');
ALTER TABLE groups ALTER COLUMN modifieduser SET NOT NULL;

ALTER TABLE groups ADD CONSTRAINT fk_Grp_updated_user FOREIGN KEY (modifieduser)      
REFERENCES aartuser (id) MATCH SIMPLE      
ON UPDATE NO ACTION ON DELETE NO ACTION;

--INFO: AART related tables original 20.sql

-- To Add table auditing columns in testsession table

ALTER TABLE testsession ADD COLUMN createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone;

ALTER TABLE testsession ADD COLUMN createduser Integer;
UPDATE testsession set createduser = (select id from aartuser where username='cetesysadmin');
ALTER TABLE testsession ALTER COLUMN createduser SET NOT NULL;

ALTER TABLE testsession ADD COLUMN activeflag boolean DEFAULT true;

ALTER TABLE testsession ADD COLUMN modifieddate timestamp without time zone;
update testsession set modifieddate=('now'::text)::timestamp without time zone;
ALTER TABLE testsession ALTER COLUMN modifieddate SET NOT NULL;

ALTER TABLE testsession ADD COLUMN modifieduser Integer;
UPDATE testsession set modifieduser = (select id from aartuser where username='cetesysadmin');
ALTER TABLE testsession ALTER COLUMN modifieduser SET NOT NULL;

ALTER TABLE testsession ADD CONSTRAINT fk_ts_created_user FOREIGN KEY (createduser)      
REFERENCES aartuser (id) MATCH SIMPLE      
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE testsession ADD CONSTRAINT fk_ts_updated_user FOREIGN KEY (modifieduser)      
REFERENCES aartuser (id) MATCH SIMPLE      
ON UPDATE NO ACTION ON DELETE NO ACTION;

-- To Add table auditing columns in studentstests table

ALTER TABLE studentstests ADD COLUMN createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone;

ALTER TABLE studentstests ADD COLUMN createduser Integer;
UPDATE studentstests set createduser = (select id from aartuser where username='cetesysadmin');
ALTER TABLE studentstests ALTER COLUMN createduser SET NOT NULL;

ALTER TABLE studentstests ADD COLUMN activeflag boolean DEFAULT true;

ALTER TABLE studentstests ADD COLUMN modifieddate timestamp without time zone;
update studentstests set modifieddate=('now'::text)::timestamp without time zone;
ALTER TABLE studentstests ALTER COLUMN modifieddate SET NOT NULL;

ALTER TABLE studentstests ADD COLUMN modifieduser Integer;
UPDATE studentstests set modifieduser = (select id from aartuser where username='cetesysadmin');
ALTER TABLE studentstests ALTER COLUMN modifieduser SET NOT NULL;

ALTER TABLE studentstests ADD CONSTRAINT fk_st_created_user FOREIGN KEY (createduser)      
REFERENCES aartuser (id) MATCH SIMPLE      
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE studentstests ADD CONSTRAINT fk_st_updated_user FOREIGN KEY (modifieduser)      
REFERENCES aartuser (id) MATCH SIMPLE      
ON UPDATE NO ACTION ON DELETE NO ACTION;

--INFO: AART related tables original 21.sql

-- MODIFY COLUMNS

ALTER TABLE organization DROP COLUMN modifieddate;
ALTER TABLE organization ADD COLUMN modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone; 

ALTER TABLE organizationrelation DROP COLUMN modifieddate;
ALTER TABLE organizationrelation ADD COLUMN modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone; 

ALTER TABLE groups DROP COLUMN modifieddate;
ALTER TABLE groups ADD COLUMN modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone; 

ALTER TABLE testsession DROP COLUMN modifieddate;
ALTER TABLE testsession ADD COLUMN modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone; 

ALTER TABLE studentstests DROP COLUMN modifieddate;
ALTER TABLE studentstests ADD COLUMN modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone; 

 

-- SCRIPT FOR ALL TABLES

CREATE OR REPLACE FUNCTION addAuditColumnsInAllTable() RETURNS INTEGER AS 
$BODY$
DECLARE  
    tname             information_schema.tables.table_name%TYPE;
    tschema           information_schema.tables.table_schema%TYPE;  
    tableCount	           integer = 0;
    tableCursor CURSOR FOR  
         select table_name, table_schema from information_schema.tables where table_schema NOT IN ('pg_catalog', 'information_schema');

BEGIN
RAISE INFO '---------Enter---------------';   
OPEN tableCursor;   
	LOOP
		
		FETCH tableCursor INTO tname, tschema;  
		EXIT WHEN NOT FOUND ;  
		--RAISE INFO '%     %', table_name, table_schema;  
		IF tname <> 'organization' and tname <> 'organizationrelation' and tname <> 'groups' and tname <> 'testsession' and tname <> 'studentstests' and
		   tname <> 'cognitivetaxonomy' and tname <> 'cognitivetaxonomydimension' and tname <> 'contentframework' and tname <> 'contentframeworkdetail' and 
		   tname <> 'foil' and tname <> 'foilsstimulusvariants' and tname <> 'frameworklevel' and tname <> 'frameworktype' and tname <> 'tasklayout' and 
		   tname <> 'tasklayoutformat' and tname <> 'tasktype' and tname <> 'taskvariant' and tname <> 'taskvariantcontentframeworkdetail' and 
		   tname <> 'taskvariantitemusage' and tname <> 'taskvariantsfoils' and tname <> 'taskvariantsstimulusvariants' and tname <> 'taskvariantstools' and 
		   tname <> 'test ' and tname <> 'testcollection' and tname <> 'testcollectionstests' and tname <> 'testform' and tname <> 'testsection' and 
		   tname <> 'testsectionsrules' and tname <> 'testsectionstaskvariants' and tname <> 'testsectionstools' and tname <> 'tool' and 
		   tname <> 'usergroups' and tname <> 'ddl_version' and tname <> 'student' and tname <> 'useraudit' and tname <> 'stimulusvariantattachment' and 
		   tname <> 'stimulusvariant' and tname <> 'testingprogram' and tname <> 'assessmentprogram' and tname <> 'assessment' and tname <> 'gradecourse' and 
		   tname <> 'contentarea' and tname <> 'test' and tname <> 'resource_restriction'
		THEN

			RAISE INFO '%     %', tname, tschema;  
			EXECUTE 'ALTER TABLE ' || tname || ' ADD COLUMN createddate timestamp without time zone DEFAULT (''now''::text)::timestamp without time zone';

			EXECUTE 'ALTER TABLE ' || tname || ' ADD COLUMN createduser Integer';
			EXECUTE 'UPDATE ' || tname || ' set createduser = (select id from aartuser where username=''cetesysadmin'')';
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN createduser SET NOT NULL';

			EXECUTE 'ALTER TABLE ' || tname || ' ADD COLUMN activeflag boolean DEFAULT true';

			EXECUTE 'ALTER TABLE ' || tname || ' ADD COLUMN modifieddate timestamp without time zone DEFAULT (''now''::text)::timestamp without time zone'; 

			EXECUTE 'ALTER TABLE ' || tname || ' ADD COLUMN modifieduser Integer';
			EXECUTE 'UPDATE ' || tname || ' set modifieduser = (select id from aartuser where username=''cetesysadmin'')';
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN modifieduser SET NOT NULL';

			EXECUTE 'ALTER TABLE ' || tname || ' ADD CONSTRAINT fk_' || tname || '_crdusr FOREIGN KEY (createduser) REFERENCES aartuser (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION';

			EXECUTE 'ALTER TABLE ' || tname || ' ADD CONSTRAINT fk_' || tname || '_updusr FOREIGN KEY (modifieduser) REFERENCES aartuser (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION';

			tableCount = tableCount + 1;
		END IF;
		
		IF tname = 'student' or tname = 'assessmentprogram' or tname = 'assessment' or tname = 'gradecourse' or tname = 'contentarea' or tname = 'test'
		THEN

			RAISE INFO '%     %', tname, tschema;  
			
			EXECUTE 'ALTER TABLE ' || tname || ' ADD COLUMN createduser Integer';
			EXECUTE 'UPDATE ' || tname || ' set createduser = (select id from aartuser where username=''cetesysadmin'')';
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN createduser SET NOT NULL';

			EXECUTE 'ALTER TABLE ' || tname || ' ADD COLUMN activeflag boolean DEFAULT true';

			EXECUTE 'ALTER TABLE ' || tname || ' ADD COLUMN modifieduser Integer';
			EXECUTE 'UPDATE ' || tname || ' set modifieduser = (select id from aartuser where username=''cetesysadmin'')';
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN modifieduser SET NOT NULL';

			EXECUTE 'ALTER TABLE ' || tname || ' ADD CONSTRAINT fk_' || tname || '_crdusr FOREIGN KEY (createduser) REFERENCES aartuser (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION';

			EXECUTE 'ALTER TABLE ' || tname || ' ADD CONSTRAINT fk_' || tname || '_updusr FOREIGN KEY (modifieduser) REFERENCES aartuser (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION';

			tableCount = tableCount + 1;
		END IF;
		 
		
	END LOOP;
CLOSE tableCursor;  
RAISE INFO '---------Exit with %--------------- ', tableCount;   
RETURN tableCount;  
END
$BODY$
LANGUAGE plpgsql;

select * from addAuditColumnsInAllTable();

DROP FUNCTION IF EXISTS addAuditColumnsInAllTable();

--INFO: AART related tables original 22.sql

ALTER TABLE usergroups RENAME TO userorganizationsgroups;

CREATE TABLE usersorganizations
(
  id bigserial NOT NULL,
  aartuserid bigint NOT NULL,
  organizationid bigint NOT NULL,
  CONSTRAINT pk_usersorganizations PRIMARY KEY (id ),
  CONSTRAINT aartuser_fk FOREIGN KEY (aartuserid)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT organization_fk FOREIGN KEY (organizationid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_user_organization UNIQUE (aartuserid , organizationid )
)
WITH (
  OIDS=FALSE
);
  
insert into usersorganizations(aartuserid,organizationid)
(Select  distinct uog.aartuserid,g.organizationid from userorganizationsgroups uog,groups g
where 
uog.groupid = g.id);


ALTER TABLE userorganizationsgroups ADD COLUMN userorganizationid bigint;

update userorganizationsgroups
 set userorganizationid = (select id from usersorganizations uo where (uo.aartuserid,uo.organizationid)
  in
   (Select userorganizationsgroups.aartuserid,g.organizationid from groups g where g.id = userorganizationsgroups.groupid));

--verification.

select uog.*,uo.aartuserid as new_aart_userid from userorganizationsgroups uog,usersorganizations uo
where
uog.userorganizationid = uo.id;

--find the ones that are different.There should not be any one.

select uog.*,uo.aartuserid as new_aart_userid from userorganizationsgroups uog,usersorganizations uo
where
uog.userorganizationid = uo.id and
uog.aartuserid <> uo.aartuserid;

ALTER TABLE userorganizationsgroups ADD CONSTRAINT fk_userorganizationid
 FOREIGN KEY (userorganizationid) REFERENCES usersorganizations (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE userorganizationsgroups ADD CONSTRAINT uk_user_organizations_groups UNIQUE (userorganizationid, groupid);

--drop the redundant aartusercolumn id.

alter table userorganizationsgroups drop column aartuserid;

--these are the default roles that will be deleted.
Select uog.*,g.* from userorganizationsgroups uog,groups g where uog.groupid = g.id and g.defaultrole='t';

--delete all the non default role relations that was created before.

delete from userorganizationsgroups where groupid in (select g.id from groups g where g.defaultrole='t');

--delete all the non default roles.
delete from groups where defaultrole='t';

--delete groups with no permissions assigned to them.
delete from groups where groupname='DEFAULT' and id not in (select groupid from groupauthorities);

--INFO: AART related tables original 23.sql

 -- SCRIPT TO MODIFY INTEGRATION TABLES

CREATE OR REPLACE FUNCTION modifyAuditColumnsForCBIntegration() RETURNS INTEGER AS 
$BODY$
DECLARE  
    tname             information_schema.tables.table_name%TYPE;
    tschema           information_schema.tables.table_schema%TYPE;  
    tableCount	           integer = 0;
    tableCursor CURSOR FOR  
         select table_name, table_schema from information_schema.tables where table_schema NOT IN ('pg_catalog', 'information_schema');

BEGIN
RAISE INFO '---------Enter---------------';   
OPEN tableCursor;   
	LOOP
		
		FETCH tableCursor INTO tname, tschema;  
		EXIT WHEN NOT FOUND ;  
		--RAISE INFO '%     %', table_name, table_schema;  
				
		IF tname = 'student' or tname = 'assessmentprogram' or tname = 'assessment' or tname = 'gradecourse' or tname = 'contentarea' or tname = 'test'
		 		or tname = 'assessmentstestcollections' or tname = 'categorytype' or tname = 'category'
		THEN 
			
			EXECUTE 'ALTER TABLE ' || tname || ' DROP CONSTRAINT IF EXISTS fk_' || tname || '_crdusr';
			EXECUTE 'ALTER TABLE ' || tname || ' DROP CONSTRAINT IF EXISTS fk_' || tname || '_updusr';

			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN createduser DROP NOT NULL';
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN modifieduser DROP NOT NULL'; 
			
			tableCount = tableCount + 1;
		END IF;
		 
		
	END LOOP;
CLOSE tableCursor;  
RAISE INFO '---------Exit with %--------------- ', tableCount;   
RETURN tableCount;  
EXCEPTION 
            WHEN undefined_object THEN
            RAISE NOTICE 'caught exception %', tableCount;
            RETURN tableCount;
END
$BODY$
LANGUAGE plpgsql;

select * from modifyAuditColumnsForCBIntegration();

DROP FUNCTION IF EXISTS modifyAuditColumnsForCBIntegration();

--INFO: AART related tables original 24.sql

-- Table: userpasswordreset

-- DROP TABLE userpasswordreset;

CREATE TABLE userpasswordreset
(
  aart_user_id bigint NOT NULL,
  password_expiration_date timestamp without time zone,
  active_flag boolean DEFAULT true,
  auth_token character varying(128) NOT NULL,
  CONSTRAINT fk_userpasswordreset_aartuserid FOREIGN KEY (aart_user_id)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

--INFO: AART related tables original 25.sql

CREATE TABLE studentpassword (
    id bigserial primary key,
    word character varying(20) NOT NULL
);

--INFO: AART related tables original 26.sql

-- Function: organization_parent_tree(bigint, integer)

-- DROP FUNCTION organization_parent_tree(bigint, integer);

CREATE OR REPLACE FUNCTION organization_parent_tree(IN childid bigint, IN currentlevel integer)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying,
  organizationtypeid bigint, welcomemessage character varying, relatedorganizationid bigint,
  isparent boolean, currentlevel integer) AS
$BODY$
        WITH RECURSIVE organization_parent_tree_relation(organizationid, parentorganizationid,currentlevel) AS (
          SELECT organizationid, parentorganizationid,($2 - 1) as currentLevel FROM organizationrelation WHERE organizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid,
            (parentorganization_parent_tree_relation.currentLevel - 1) as currentlevel
          FROM organizationrelation, organization_parent_tree_relation as parentorganization_parent_tree_relation
          WHERE organizationrelation.organizationid = parentorganization_parent_tree_relation.parentorganizationid)
        SELECT id, organizationname, displayidentifier, organizationtypeid, welcomemessage,
           organization_parent_tree_relation.organizationid,false as isparent,
          organization_parent_tree_relation.currentLevel as currentLevel        
         FROM organization org,organization_parent_tree_relation where
          org.id=organization_parent_tree_relation.parentorganizationid;
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;


-- Function: organization_parent_tree(bigint, integer)

-- DROP FUNCTION organization_parent_tree(bigint, integer);

-- Function: organization_child_tree(bigint, integer)

-- DROP FUNCTION organization_child_tree(bigint, integer);

CREATE OR REPLACE FUNCTION organization_child_tree(IN parentid bigint, IN currentlevel integer)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying,
  organizationtypeid bigint, welcomemessage character varying, relatedorganizationid bigint, isparent boolean,
  currentlevel integer) AS
$BODY$
        WITH RECURSIVE organization_child_tree_relation(organizationid, parentorganizationid,currentlevel) AS (
        SELECT organizationid, parentorganizationid,($2+1) as currentLevel FROM organizationrelation WHERE parentorganizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid,
            (parentorganization_child_tree_relation.currentLevel + 1) as currentlevel
          FROM organizationrelation, organization_child_tree_relation as parentorganization_child_tree_relation
          WHERE organizationrelation.parentorganizationid = parentorganization_child_tree_relation.organizationid)
          SELECT org.id, org.organizationname, org.displayidentifier, org.organizationtypeid, org.welcomemessage,
           organization_child_tree_relation.parentorganizationid,true as isparent,
          organization_child_tree_relation.currentLevel as currentLevel
           from organization org,organization_child_tree_relation where org.id=organization_child_tree_relation.organizationid;
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
  
--INFO: AART related tables original 27.sql
-- Table: studentstestsections

-- DROP TABLE studentstestsections;

CREATE TABLE studentstestsections
(
  id bigserial,
  studentstestid bigint,
  testsectionid bigint, 
  ticketno character varying(75) DEFAULT NULL,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieduser integer NOT NULL,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  CONSTRAINT pk_studentstestsections PRIMARY KEY (id ),
  CONSTRAINT fk_studentstestsections_crdusr FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentstestsections_updusr FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION, 
  CONSTRAINT fk_studentstestsections_studentstestid FOREIGN KEY (studentstestid)
      REFERENCES studentstests (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentstestsections_testsectionid FOREIGN KEY (testsectionid)
      REFERENCES testsection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION, 
  CONSTRAINT uk_studentstestsections UNIQUE (studentstestid , testsectionid )
)
WITH (
  OIDS=FALSE
);

ALTER TABLE studentstestsections
  OWNER TO aart; 

-- flag to indicate whether testsection is ticketed or not
ALTER TABLE testsection ADD COLUMN ticketed boolean DEFAULT true;

-- system defined code for testing programs
ALTER TABLE testingprogram ADD COLUMN programabbr character varying(20);

--state student identifier is unique only with in the contracting organization tree.
--ALTER TABLE student DROP CONSTRAINT if exists uk_state_student_identifier;

--INFO AART related tables. Original 28.sql
-- Table: operationaltestwindow
CREATE TABLE operationaltestwindow
(
  id bigserial NOT NULL,
  windowname character varying(75) NOT NULL,
  testcollectionid bigint NOT NULL,
  effectivedate timestamp with time zone,
  expirydate timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  createduser integer NOT NULL,
  modifieduser integer NOT NULL,
  CONSTRAINT operationaltestwindow_pkey PRIMARY KEY (id ),
  CONSTRAINT operationaltestwindow_testcollection_fkey FOREIGN KEY (testcollectionid)
      REFERENCES testcollection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

--adding ticketNo column for test level ticket.
ALTER TABLE studentstests ADD COLUMN ticketno character varying(75) DEFAULT NULL::character varying;

--INFO original 29.sql AART related tables.

ALTER TABLE operationaltestwindow drop constraint
if exists uk_test_collection_id;

ALTER TABLE operationaltestwindow
ADD constraint uk_test_collection_id UNIQUE (testcollectionid);   