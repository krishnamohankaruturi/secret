
--US15339
delete from contentareatesttypesubjectarea
where contentareaid=(select id from contentarea where abbreviatedname='M')
and testtypesubjectareaid=(select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2' order by id asc limit 1) and subjectareaid=(select id from subjectarea where subjectareacode='GCTEA'));

delete from contentareatesttypesubjectarea
where contentareaid=(select id from contentarea where abbreviatedname='M')
and testtypesubjectareaid=(select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2' order by id asc limit 1) and subjectareaid=(select id from subjectarea where subjectareacode='SELAA'));

delete from contentareatesttypesubjectarea
where contentareaid=(select id from contentarea where abbreviatedname='M')
and testtypesubjectareaid=(select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='3') and subjectareaid=(select id from subjectarea where subjectareacode='SELAA'));


INSERT INTO testtype(testtypecode,testtypename,assessmentid,createdate,createduser,modifieddate,modifieduser) VALUES ('2','General - Computer/English',
(select id from assessment where assessmentcode='GL' and activeflag is true and testingprogramid=(select id from testingprogram where programabbr='F' and assessmentprogramid=(select id from assessmentprogram where programname = 'KAP') and activeflag is true)),now(), 
(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode='2' and activeflag is true and assessmentid=
(select id from assessment where assessmentcode='GL' and activeflag is true and testingprogramid=(select id from testingprogram where programabbr='F' and assessmentprogramid=(select id from assessmentprogram where programname = 'KAP') and activeflag is true))), 
(select id from subjectarea where subjectareaname='State Mathematics Assessment' and activeflag is true), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='M' and activeflag is true) , (select id from testtypesubjectarea where subjectareaid=(select id from subjectarea where subjectareaname='State Mathematics Assessment' and activeflag is true)
and testtypeid=(select id from testtype where testtypecode='2' and activeflag is true and assessmentid=
(select id from assessment where assessmentcode='GL' and activeflag is true and testingprogramid=(select id from testingprogram where programabbr='F' and assessmentprogramid=(select id from assessmentprogram where programname = 'KAP') and activeflag is true)))),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

INSERT INTO testtypesubjectarea(testtypeid,subjectareaid,createdate,createduser,modifieddate,modifieduser) VALUES 
	((select id from testtype where testtypecode='2' and activeflag is true and assessmentid=
(select id from assessment where assessmentcode='GL' and activeflag is true and testingprogramid=(select id from testingprogram where programabbr='F' and assessmentprogramid=(select id from assessmentprogram where programname = 'KAP') and activeflag is true))), 
(select id from subjectarea where subjectareaname='State ELA Assessment' and activeflag is true), now(), 
	(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
	
insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='ELA' and activeflag is true) , (select id from testtypesubjectarea where subjectareaid=(select id from subjectarea where subjectareaname='State ELA Assessment' and activeflag is true)
and testtypeid=(select id from testtype where testtypecode='2' and activeflag is true and assessmentid=
(select id from assessment where assessmentcode='GL' and activeflag is true and testingprogramid=(select id from testingprogram where programabbr='F' and assessmentprogramid=(select id from assessmentprogram where programname = 'KAP') and activeflag is true)))),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser,gradecourseid) 
	VALUES ((select id from assessmentprogram where abbreviatedname = 'KAP' and activeflag is true),
		(select id from testingprogram where programabbr = 'F' and assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'KAP' and activeflag is true) and activeflag is true),
		(select id from assessment where assessmentcode='GL' and activeflag is true and testingprogramid=(select id from testingprogram where programabbr='F' and assessmentprogramid=(select id from assessmentprogram where abbreviatedname = 'KAP' and activeflag is true) and activeflag is true)),
		(select id from contentareatesttypesubjectarea where contentareaid=(select id from contentarea where abbreviatedname='ELA' and activeflag is true) and testtypesubjectareaid=(select id from testtypesubjectarea where subjectareaid=(select id from subjectarea where subjectareaname='State ELA Assessment' and activeflag is true)
and testtypeid=(select id from testtype where testtypecode='2' and activeflag is true and assessmentid=
(select id from assessment where assessmentcode='GL' and activeflag is true and testingprogramid=(select id from testingprogram where programabbr='F' and assessmentprogramid=(select id from assessmentprogram where programname = 'KAP') and activeflag is true))))),
		now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'),
		null
	 ); 

	 
INSERT INTO autoregistrationcriteria(assessmentprogramid , testingprogramid , assessmentid , contentareatesttypesubjectareaid, createdate,createduser,modifieddate,modifieduser,gradecourseid) 
	VALUES ((select id from assessmentprogram where abbreviatedname = 'KAP' and activeflag is true),
		(select id from testingprogram where programabbr = 'F' and assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'KAP' and activeflag is true) and activeflag is true),
		(select id from assessment where assessmentcode='GL' and activeflag is true and testingprogramid=(select id from testingprogram where programabbr='F' and assessmentprogramid=(select id from assessmentprogram where abbreviatedname = 'KAP' and activeflag is true) and activeflag is true)),
		(select id from contentareatesttypesubjectarea where contentareaid=(select id from contentarea where abbreviatedname='M' and activeflag is true) and testtypesubjectareaid=(select id from testtypesubjectarea where subjectareaid=(select id from subjectarea where subjectareaname='State Mathematics Assessment' and activeflag is true)
and testtypeid=(select id from testtype where testtypecode='2' and activeflag is true and assessmentid=
(select id from assessment where assessmentcode='GL' and activeflag is true and testingprogramid=(select id from testingprogram where programabbr='F' and assessmentprogramid=(select id from assessmentprogram where programname = 'KAP') and activeflag is true))))),
		now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'),
		null
	 );
	 
delete from contentareatesttypesubjectarea
where contentareaid =(select id from contentarea where abbreviatedname='M') 
and testtypesubjectareaid=(select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2' order by id limit 1) and subjectareaid=(select id from subjectarea where subjectareacode='GCTEA'));

delete from contentareatesttypesubjectarea
where contentareaid =(select id from contentarea where abbreviatedname='ELA') 
and testtypesubjectareaid=(select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2' order by id limit 1) and subjectareaid=(select id from subjectarea where subjectareacode='GCTEA'));

insert into contentareatesttypesubjectarea (contentareaid, testtypesubjectareaid, createduser, modifieduser, createdate, modifieddate)
values ((select id from contentarea where abbreviatedname='GKS') , (select id from testtypesubjectarea where testtypeid=(select id from testtype where testtypecode='2' order by id limit 1) and subjectareaid=(select id from subjectarea where subjectareacode='GCTEA')),  
(Select id from aartuser where username='cetesysadmin'), (Select id from aartuser where username='cetesysadmin'), now(), now());

update contentareatesttypesubjectarea
set contentareaid=(select id from contentarea where abbreviatedname='Arch&Const') where testtypesubjectareaid=(select ttsa.id from testtypesubjectarea ttsa inner join subjectarea sa on ttsa.subjectareaid=sa.id inner join testtype tt on tt.id = ttsa.testtypeid where tt.testtypecode='F' and sa.subjectareacode='EOPA');

update contentareatesttypesubjectarea
set contentareaid=(select id from contentarea where abbreviatedname='Arch&Const') where testtypesubjectareaid=(select ttsa.id from testtypesubjectarea ttsa inner join subjectarea sa on ttsa.subjectareaid=sa.id inner join testtype tt on tt.id = ttsa.testtypeid where tt.testtypecode='F' and sa.subjectareacode='Arch&Const');


--DE7829
  
CREATE OR REPLACE FUNCTION organization_child_tree(IN parentid bigint, IN currentlevel integer)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying, relatedorganizationid bigint, isparent boolean, currentlevel integer) AS
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
           from organization org,organization_child_tree_relation where org.activeflag = true and org.id=organization_child_tree_relation.organizationid;
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;


CREATE OR REPLACE FUNCTION organization_children(IN parentid bigint)
  RETURNS TABLE(id bigint, organizationname character varying, displayidentifier character varying, organizationtypeid bigint, welcomemessage character varying, contractingorganization boolean, schoolstartdate timestamp with time zone, schoolenddate timestamp with time zone, expirepasswords boolean, expirationdatetype bigint) AS
$BODY$
        WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
        SELECT organizationid, parentorganizationid FROM organizationrelation WHERE parentorganizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation as parentorganization_relation
          WHERE organizationrelation.parentorganizationid = parentorganization_relation.organizationid)
          SELECT o.id, o.organizationname, o.displayidentifier, o.organizationtypeid, o.welcomemessage, o.contractingorganization, o.schoolstartdate, o.schoolenddate, o.expirepasswords,  o.expirationdatetype 
          from organization o where o.activeflag = true and o.id in (Select organizationid FROM organization_relation);
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;



CREATE OR REPLACE FUNCTION organization_parent(IN childid bigint, OUT id bigint, OUT organizationname character varying, OUT displayidentifier character varying, OUT organizationtypeid bigint, OUT welcomemessage character varying, OUT buildinguniqueness bigint, OUT schoolstartdate timestamp with time zone, OUT schoolenddate timestamp with time zone, OUT contractingorganization boolean, OUT expirepasswords boolean, OUT expirationdatetype bigint)
  RETURNS SETOF record AS
$BODY$

WITH RECURSIVE organization_relation(organizationid, parentorganizationid) AS (
          SELECT organizationid, parentorganizationid FROM organizationrelation WHERE organizationid = $1
          UNION
          SELECT
            organizationrelation.organizationid, organizationrelation.parentorganizationid
          FROM organizationrelation, organization_relation AS parentorganization_relation
          WHERE organizationrelation.organizationid = parentorganization_relation.parentorganizationid)
        SELECT id, organizationname, displayidentifier, organizationtypeid, welcomemessage, buildinguniqueness, schoolstartdate, schoolenddate, contractingorganization, expirepasswords, expirationdatetype 
        FROM organization WHERE activeflag = true and id IN (SELECT parentorganizationid FROM organization_relation);

$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;


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
          org.activeflag=true and org.id=organization_parent_tree_relation.parentorganizationid;
        $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
  
-- Script to reflect tag name changes as suggested by KSDE
update fieldspecification set mappedname = 'State_Reading_Assess'
where id = (
select id from fieldspecification 
	where mappedname='State_ELA_Assess' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 

update fieldspecification set mappedname = 'State_History_Assess'
where id = (
select id from fieldspecification 
	where mappedname='State_Hist_Gov_Assess' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 	

update fieldspecification set mappedname = 'CTE_Assess'
where id = (
select id from fieldspecification 
	where mappedname='State_CTE_Assess' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'Pathways_Assess'
where id = (
select id from fieldspecification 
	where mappedname='State_Pathways_Assess' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'Math_Proctor_ID'
where id = (
select id from fieldspecification 
	where mappedname='Math_DLM_Proctor_ID' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 

update fieldspecification set mappedname = 'Math_Proctor_Name'
where id = (
select id from fieldspecification 
	where mappedname='Math_DLM_Proctor_Name' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'Reading_Proctor_ID'
where id = (
select id from fieldspecification 
	where mappedname='ELA_DLM_Proctor_ID' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'Reading_Proctor_Name'
where id = (
select id from fieldspecification 
	where mappedname='ELA_DLM_Proctor_Name' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'Science_Proctor_ID'
where id = (
select id from fieldspecification 
	where mappedname='Science_DLM_Proctor_ID' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 

update fieldspecification set mappedname = 'Science_Proctor_Name'
where id = (
select id from fieldspecification 
	where mappedname='Science_DLM_Proctor_Name' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'CTE_Proctor_ID'
where id = (
select id from fieldspecification 
	where mappedname='CTE_cPass_Proctor_ID' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	); 
	
update fieldspecification set mappedname = 'CTE_Proctor_Name'
where id = (
select id from fieldspecification 
	where mappedname='CTE_cPass_Proctor_Name' and id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE'))
	);  
	
UPDATE testcollection SET pooltype='MULTIEEOFG' WHERE pooltype='MULTIEE';	
 