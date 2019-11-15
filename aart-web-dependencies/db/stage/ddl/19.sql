-- stage/ddl/19.sql
-- functions to retrieve results of audit log
--Function for retriving all the roster audit information by roster
 CREATE OR REPLACE FUNCTION roster_audit_info(
    IN aartuserid bigint,
    OUT modifieduser bigint,
    OUT affectedroster bigint,
    OUT source character varying,
    OUT eventtype character varying,
    OUT eventname character varying,
    OUT eventdate timestamp with time zone,
    OUT beforevalue json,
    OUT currentvalue json
    )
  RETURNS SETOF record AS
$BODY$

   select 
   uath.modifieduser as modifieduser,
   uath.affectedroster as affecteduser,
   dah.source as source,
   dah.action as eventtype,
   uath.eventname as eventname,
   dah.createddate as eventdate,
   uath.beforevalue as beforevalue,
   uath.currentvalue as currentvalue      
       from domainaudithistory dah
    inner join rosteraudittrailhistory uath on uath.domainaudithistoryid = dah.id
   where dah.objectid = aartuserid

$BODY$
 LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000; 
  --Function for retriving all the student audit information by student 
  CREATE OR REPLACE FUNCTION student_audit_info(
    IN studentid bigint,
    OUT modifieduser bigint,
    OUT statestudentid character varying,
    OUT studentfirstname character varying,
    OUT studentlastname character varying,
    OUT source character varying,
    OUT eventtype character varying,
    OUT eventdate timestamp with time zone,
    OUT eventname character varying,   
    OUT beforevalue json,
    OUT currentvalue json 
    )
  RETURNS SETOF record AS
$BODY$
   select  
   sath.modifieduser as modifieduser,
   sath.statestudentid as statestudentid,
   sath.studentfirstname as studentfirstname,
   sath.studentlastname as studentlastname,
   dah.source as source, 
   dah.action as eventtype,  
   dah.createddate as eventdate,
   sath.eventname as eventname,
   sath.beforevalue as beforevalue,
   sath.currentvalue as currentvalue      
   from domainaudithistory dah
    inner join studentaudittrailhistory sath on sath.domainaudithistoryid = dah.id
   where dah.objectid = studentid
     $BODY$
 LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
  
 --Function to generate CSV files  
 
CREATE OR REPLACE FUNCTION export_audit_log(id bigint,file_name VARCHAR(255),objecttype varchar(20))
 RETURNS VOID
 AS 
 $BODY$
 DECLARE
            select_stmt VARCHAR(100);
 BEGIN
  IF UPPER(objecttype) = 'USER' THEN
    select_stmt := 'select * from user_audit_info('||id||')'; 
  END IF;
  
  IF UPPER(objecttype) = 'ORGANIZATION' THEN
    select_stmt := 'select * from organization_audit_info('||id||')'; 
  END IF;

  IF UPPER(objecttype) = 'ROLES' THEN
    select_stmt := 'select * from role_audit_info('||id||')'; 
  END IF;

  IF UPPER(objecttype) = 'ROSTER' THEN
    select_stmt := 'select * from roster_audit_info('||id||')'; 
  END IF;

  IF UPPER(objecttype) = 'STUDENT' THEN
    select_stmt := 'select * from student_audit_info('||id||')'; 
  END IF;
  
  IF select_stmt is null THEN
    RAISE EXCEPTION 'Nonexistent objecttype --> %', objecttype
                       USING HINT = 'Please check your objecttype';
  ELSE
    EXECUTE('COPY (' || select_stmt || ') TO ' || QUOTE_LITERAL(file_name) || ' WITH CSV HEADER');  
  END IF;
        END;
       $BODY$
LANGUAGE plpgsql VOLATILE;

--select query to genarate CSV file
--select export_audit_log(objectid,'D:\\filename.csv','OBJECT');

DROP SEQUENCE IF EXISTS studentaudittrailhistory_id_seq;
CREATE SEQUENCE studentaudittrailhistory_id_seq;
