--ddl/492.sql
CREATE TABLE organizationtreedetail (
  schoolid bigint NOT NULL,
  schoolname character varying(200) NOT NULL,
  schooldisplayidentifier character varying(100) NOT NULL,
  districtid bigint,
  districtname character varying(200),
  districtdisplayidentifier character varying(100),
  stateid bigint,
  statename character varying(200),
  statedisplayidentifier character varying(100),
  createddate timestamp with time zone NOT NULL DEFAULT now(), 
  CONSTRAINT organizationtreedetail_pkey PRIMARY KEY (schoolid),
  CONSTRAINT organizationtreedetail_fk1 FOREIGN KEY (schoolid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT organizationtreedetail_fk2 FOREIGN KEY (districtid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT organizationtreedetail_fk3 FOREIGN KEY (stateid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX idx1_organizationtreedetail
  ON organizationtreedetail USING btree (schooldisplayidentifier);

CREATE INDEX idx2_organizationtreedetail
  ON organizationtreedetail USING btree (districtid);

CREATE INDEX idx3_organizationtreedetail
  ON organizationtreedetail USING btree (stateid);
  
CREATE OR REPLACE FUNCTION refresh_organization_detail()
  RETURNS void AS
$BODY$
	DECLARE 
		storgtypeid bigint;
		dtorgtyoeid bigint;
		schorgtypeid bigint;
		schid bigint;
		dtorgrecord record;
		storgrecord record;
	BEGIN
  		SELECT INTO storgtypeid (select id from organizationtype where typecode='ST' and activeflag is true);
  		SELECT INTO dtorgtyoeid (select id from organizationtype where typecode='DT' and activeflag is true);
  		SELECT INTO schorgtypeid (select id from organizationtype where typecode='SCH' and activeflag is true);
  		
		DELETE from organizationtreedetail;
		
  		FOR schid IN (select id from organization where organizationtypeid=schorgtypeid and activeflag = true)
		  LOOP
		  select id,organizationname,displayidentifier into dtorgrecord from organization_parent(schid) where organizationtypeid=dtorgtyoeid limit 1;
		  select id,organizationname,displayidentifier into storgrecord from organization_parent(schid) where organizationtypeid=storgtypeid limit 1;
		  INSERT INTO organizationtreedetail(schoolid, schoolname, schooldisplayidentifier, districtid, districtname, 
			districtdisplayidentifier, stateid, statename, statedisplayidentifier)
		     select org1.id as schoolid,organizationname as schoolname,
		       displayidentifier as schooldisplayidentifier,
		       (dtorgrecord.id) districtid,
		       (dtorgrecord.organizationname) districtname,
		       (dtorgrecord.displayidentifier) districtdisplayidentifier,
		       (storgrecord.id) stateid,
		       (storgrecord.organizationname) statename,
		       (storgrecord.displayidentifier) statedisplayidentifier
		   from organization org1
		   where id=schid and activeflag = true;
		END LOOP; 
        END;
	$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
