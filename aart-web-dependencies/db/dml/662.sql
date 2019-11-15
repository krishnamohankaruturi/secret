/** DML for F400: EP Sensitive text needs Display For Reading Only --

DROP TABLE IF EXISTS temp_readingelements;

CREATE TABLE temp_readingelements(essentialelement text, systemcode text, parentcode TEXT, gradeabbrname TEXT);

\COPY temp_readingelements FROM 'readingessentialelements.csv'  DELIMITER ',' CSV HEADER;

INSERT INTO readingessentialelements(essentialelement, systemcode, parentcode, gradeabbrname) 
select essentialelement, systemcode, parentcode, gradeabbrname  from temp_readingelements;

DROP TABLE IF EXISTS temp_readingelements;
**/

---- F145 ----

INSERT INTO categorytype(
             typename, typecode, typedescription, externalid, originationcode, 
            createddate, createduser, activeflag, modifieddate, modifieduser)
    VALUES ('DLM Testing model','DLM_TESTING_MODEL','DLM testing model types' ,null ,'AART_ORIG_CODE', 
            current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'));
            
INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Single EE','SEE' ,'Single EE' ,(Select id from categorytype where typecode = 'DLM_TESTING_MODEL') , 
           null ,'AART_ORIG_CODE', current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Multi EE – EOG','MEOG' , 'Multi EE – End of Grade' , (Select id from categorytype where typecode = 'DLM_TESTING_MODEL') , 
           null ,'AART_ORIG_CODE', current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('Multi EE – EOC','MEOC' , 'Multi EE – End of Course' , (Select id from categorytype where typecode = 'DLM_TESTING_MODEL') , 
           null ,'AART_ORIG_CODE', current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'), true, current_timestamp, (Select id from aartuser where username  = 'cetesysadmin'));

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser,activeflag, modifieddate, modifieduser)
VALUES ('DATA_EXTRACTS_ORGANIZATIONS', 'Create Organization Records Extract', 'Reports-Data Extracts',CURRENT_TIMESTAMP,
(select id from aartuser where email='cete@ku.edu'), true,CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));

---- F521 ----
INSERT INTO groups(organizationid, groupname, defaultrole, createddate, activeflag, 
            createduser, modifieduser, modifieddate, organizationtypeid,roleorgtypeid, groupcode, hierarchy)
VALUES ((select id from organization where displayidentifier = 'CETE'),'Program Administrator',FALSE,
	    CURRENT_TIMESTAMP,TRUE,(select id from aartuser where username='cetesysadmin'), 
            (select id from aartuser where username='cetesysadmin'),CURRENT_TIMESTAMP, 
            (select id from organizationtype where typecode = 'ST'),
            (select id from organizationtype where typecode = 'ST'),'PGAD',20
 );
   
UPDATE groups SET groupname = 'QC Administrator' where groupcode = 'TAQCP';
UPDATE groups SET groupname = 'Proctor' where groupcode = 'PRO';