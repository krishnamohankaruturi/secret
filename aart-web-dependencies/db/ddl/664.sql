-- DDL statements from Changepond - F61
CREATE TABLE organizationsnapshot
(
  id bigserial NOT NULL CONSTRAINT organizationsnapshot_id_pk PRIMARY KEY,
  organizationid bigint NOT NULL,           
  organizationname character varying(100),
  displayidentifier character varying(100) NOT NULL,
  organizationtypeid bigint NOT NULL,
  parentsnapshotid bigint,
  parentorganiationid bigint,
  parentorganizationtypeid bigint,
  parentdisplayidentifier character varying(100),
  assessemntprograms character varying(100),    
  testingmodel character varying(50),           
  schoolstartdate timestamp with time zone,
  schoolenddate timestamp with time zone,
  contractingorganization boolean DEFAULT false,
  schoolyear bigint,
  snapshotcreateddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  snapshotcreateduser integer NOT NULL,
  activeflag boolean DEFAULT true 
);

CREATE INDEX organizationsnapshot_id_index ON organizationsnapshot USING btree(id);
CREATE INDEX organizationsnapshot_organizationid_index ON organizationsnapshot USING btree(organizationid);
CREATE INDEX organizationsnapshot_displayidentifier_index ON organizationsnapshot USING btree(displayidentifier);
CREATE INDEX organizationsnapshot_organizationtypeid_index ON organizationsnapshot USING btree(organizationtypeid);
CREATE INDEX organizationsnapshot_parentsnapshotid_index ON organizationsnapshot USING btree(parentsnapshotid);
CREATE INDEX organizationsnapshot_parentorganiationid_index ON organizationsnapshot USING btree(parentorganiationid);
CREATE INDEX organizationsnapshot_schoolyear_index ON organizationsnapshot USING btree(schoolyear);
CREATE INDEX organizationsnapshot_activeflag_index ON organizationsnapshot USING btree(activeflag);

DO
$BODY$
BEGIN
IF not EXISTS (SELECT column_name  
               FROM information_schema.columns 
               WHERE table_schema='public' and table_name='studentreport' and column_name='schoolname') THEN
	ALTER TABLE studentreport ADD schoolname VARCHAR(100);
ELSE
	RAISE NOTICE 'studentreport-->schoolname Already exists';
END IF;
END
$BODY$;

DO
$BODY$
BEGIN
IF not EXISTS (SELECT column_name  
               FROM information_schema.columns 
               WHERE table_schema='public' and table_name='studentreport' and column_name='districtname') THEN
	ALTER TABLE studentreport ADD districtname VARCHAR(100);
ELSE
	RAISE NOTICE 'studentreport-->districtname Already exists';
END IF;
END
$BODY$;

-- F468 Update - Seven external roles will eventually be retired 
ALTER TABLE groups ADD COLUMN isdepreciated boolean DEFAULT false;