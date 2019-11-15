-- stage/ddl/18.sql
--Indexes for newly cretaed table

CREATE INDEX idx_useraudittrailhistory_affecteduser
ON useraudittrailhistory (affecteduser);

CREATE INDEX idx_useraudittrailhistory_domainaudithistoryid
ON useraudittrailhistory (domainaudithistoryid);




CREATE INDEX idx_organizationaudittrailhistory_affectedorganization
ON organizationaudittrailhistory (affectedorganization);

CREATE INDEX idx_organizationaudittrailhistory_domainaudithistoryid
ON organizationaudittrailhistory (domainaudithistoryid);




CREATE INDEX idx_roleaudittrailhistory_affectedrole
ON roleaudittrailhistory (affectedrole);

CREATE INDEX idx_roleaudittrailhistory_domainaudithistoryid
ON roleaudittrailhistory (domainaudithistoryid);

--ottwa ITR 3 new tables

-- Table: studentaudittrailhistory

CREATE TABLE studentaudittrailhistory
(
  id bigint NOT NULL,
  eventname text,
  modifieduser bigint,
  statestudentid varchar(50),
  studentfirstname varchar(80),
  studentlastname varchar(80), 
  beforevalue json,
  currentvalue json,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  domainaudithistoryid integer,
  CONSTRAINT fk_domainaudithistoryid FOREIGN KEY (domainaudithistoryid)
      REFERENCES domainaudithistory (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE INDEX idx_studentaudittrailhistory_modifieduser
  ON studentaudittrailhistory
  USING btree
  (modifieduser);


CREATE INDEX idx_studentnaudittrailhistory_statestudentid
  ON studentaudittrailhistory
  USING btree
  (statestudentid);


CREATE INDEX idx_studentaudittrailhistory_domainaudithistoryid
  ON studentaudittrailhistory
  USING btree
  (domainaudithistoryid);


-- Table: rosteraudittrailhistory
  
CREATE TABLE rosteraudittrailhistory
(
  id bigint NOT NULL,
  eventname text,
  modifieduser bigint,
  affectedroster bigint, 
  beforevalue json,
  currentvalue json,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  domainaudithistoryid integer,
  CONSTRAINT fk_domainaudithistoryid FOREIGN KEY (domainaudithistoryid)
      REFERENCES domainaudithistory (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE INDEX idx_rosteraudittrailhistory_modifieduser
  ON rosteraudittrailhistory
  USING btree
  (modifieduser);


CREATE INDEX idx_rosternaudittrailhistory_affectedroster
  ON rosteraudittrailhistory
  USING btree
  (affectedroster);


CREATE INDEX idx_rosternaudittrailhistory_domainaudithistoryid
  ON rosteraudittrailhistory
  USING btree
  (domainaudithistoryid);





--Function for retriving all the user audit information by user
CREATE OR REPLACE FUNCTION user_audit_info(
    IN aartuserid bigint,
    OUT modifieduser bigint,
    OUT affecteduser bigint,
    OUT source character varying,
    OUT eventtype character varying,
    OUT eventname character varying,
    OUT eventdate timestamp with time zone,
    OUT beforevalue character varying,
    OUT currentvalue character varying
    )
  RETURNS SETOF record AS
$BODY$

   select 
   uath.modifieduser as modifieduser,
   uath.affecteduser as affecteduser,
   dah.source as source,
   dah.action as eventtype,
   uath.eventname as eventname,
   dah.createddate as eventdate,
   uath.beforevalue as beforevalue,
   uath.currentvalue as currentvalue      
       from domainaudithistory dah
    inner join useraudittrailhistory uath on uath.domainaudithistoryid = dah.id
   where dah.objectid = aartuserid

$BODY$
 LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
  

  
--Function for retriving all the organization audit information by organization  
CREATE OR REPLACE FUNCTION organization_audit_info(
    IN aartuserid bigint,
    OUT modifieduser bigint,
    OUT affectedorganization bigint,
    OUT source character varying,
    OUT eventtype character varying,
    OUT eventname character varying,
    OUT eventdate timestamp with time zone,
    OUT beforevalue character varying,
    OUT currentvalue character varying
    )
  RETURNS SETOF record AS
$BODY$

   select 
   uath.modifieduser as modifieduser,
   uath.affectedorganization as affectedorganization,
   dah.source as source,
   dah.action as eventtype,
   uath.eventname as eventname,
   dah.createddate as eventdate,
   uath.beforevalue as beforevalue,
   uath.currentvalue as currentvalue      
       from domainaudithistory dah
    inner join organizationaudittrailhistory uath on uath.domainaudithistoryid = dah.id
   where dah.objectid = aartuserid

$BODY$
 LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
  
--Function for retriving all the role audit information by role    
  CREATE OR REPLACE FUNCTION role_audit_info(
    IN aartuserid bigint,
    OUT modifieduser bigint,
    OUT affectedrole bigint,
    OUT source character varying,
    OUT eventtype character varying,
    OUT eventname character varying,
    OUT eventdate timestamp with time zone,
    OUT beforevalue character varying,
    OUT currentvalue character varying
    )
  RETURNS SETOF record AS
$BODY$
   select 
   uath.modifieduser as modifieduser,
   uath.affectedrole as affectedrole,
   dah.source as source,
   dah.action as eventtype,
   uath.eventname as eventname,
   dah.createddate as eventdate,
   uath.beforevalue as beforevalue,
   uath.currentvalue as currentvalue      
       from domainaudithistory dah
    inner join roleaudittrailhistory uath on uath.domainaudithistoryid = dah.id
   where dah.objectid = aartuserid
$BODY$
 LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;


--For Exorting the result into CSV (User only need to run this function for extracting the audit log into csv file)

--Parameters --objectid                  :eg - 84319
             --file name and location    :eg - 'F:\\mycsv1.csv'
			 --objecttype                :eg - user,organization,roles etc
			 
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
		
		IF select_stmt is null THEN
		  RAISE EXCEPTION 'Nonexistent objecttype --> %', objecttype
                       USING HINT = 'Please check your objecttype';
		ELSE
		  EXECUTE('COPY (' || select_stmt || ') TO ' || QUOTE_LITERAL(file_name) || ' WITH CSV HEADER');  
		END IF;
        END;
      	$BODY$
LANGUAGE plpgsql VOLATILE;
