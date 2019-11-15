-- 37.sql

create table audittype(
Id bigserial,
auditname character varying(80),
dispalyname character varying(80),
activeflag Boolean,
createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
createduser integer,
modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
modifieduser integer
);

CREATE INDEX idx_audittype_Id  ON audittype  USING btree  (Id);
CREATE INDEX idx_audittype_dispalyname  ON audittype  USING btree  (dispalyname);
CREATE INDEX idx_audittype_auditname  ON audittype  USING btree  (auditname);
CREATE INDEX idx_audittype_activeflag ON audittype  USING btree  (activeflag);
	    
--ActivationtEmailTplate
ALTER  TABLE  activationtemplateaudittrailhistory ADD  COLUMN  stateids text;
ALTER TABLE activationtemplateaudittrailhistory ADD  COLUMN  statenames text;
ALTER TABLE  activationtemplateaudittrailhistory ADD  COLUMN assessmentprogram character varying(75);
ALTER TABLE  activationtemplateaudittrailhistory ADD  COLUMN assessmentprogramid bigint;
ALTER TABLE activationtemplateaudittrailhistory ADD  COLUMN  templatename character varying;
ALTER TABLE activationtemplateaudittrailhistory ADD  COLUMN  modifiedusername character varying;
ALTER TABLE activationtemplateaudittrailhistory ADD  COLUMN  modifieduserfirstname character varying;
ALTER TABLE activationtemplateaudittrailhistory ADD  COLUMN  modifieduserlastName character varying;
ALTER TABLE activationtemplateaudittrailhistory ADD  COLUMN  modifiedUsereducatoridentifier character varying;

CREATE INDEX idx_activationtemplateaudittrailhistory_stateids  
	ON activationtemplateaudittrailhistory  USING btree  (stateids);
CREATE INDEX idx_activationtemplateaudittrailhistory_statenames  
	ON activationtemplateaudittrailhistory  USING btree  (statenames);
CREATE INDEX idx_activationtemplateaudittrailhistory_assessmentprogram  
	ON activationtemplateaudittrailhistory  USING btree  (assessmentprogram);
CREATE INDEX idx_activationtemplateaudittrailhistory_assessmentprogramid  
	ON activationtemplateaudittrailhistory  USING btree  (assessmentprogramid);
CREATE INDEX idx_activationtemplateaudittrailhistory_templatename  
	ON activationtemplateaudittrailhistory  USING btree  (templatename);
CREATE INDEX idx_activationtemplateaudittrailhistory_createddate  
	ON activationtemplateaudittrailhistory  USING btree  (createddate);
	
--Organization Name Changes 
ALTER TABLE organizationaudittrailhistory ADD  COLUMN  statename character varying(100);
ALTER TABLE organizationaudittrailhistory ADD  COLUMN  stateid bigint;
ALTER TABLE organizationaudittrailhistory ADD  COLUMN  districtname character varying(100);
ALTER TABLE organizationaudittrailhistory ADD  COLUMN  districtid bigint;
ALTER TABLE organizationaudittrailhistory ADD  COLUMN  schoolname character varying(100);
ALTER TABLE organizationaudittrailhistory ADD  COLUMN  schoolid bigint;
ALTER TABLE organizationaudittrailhistory ADD  COLUMN  modifiedusername character varying;
ALTER TABLE organizationaudittrailhistory ADD  COLUMN  modifieduserfirstname character varying;
ALTER TABLE organizationaudittrailhistory ADD  COLUMN  modifieduserlastName character varying;
ALTER TABLE organizationaudittrailhistory ADD  COLUMN  modifiedUsereducatoridentifier character varying;

CREATE INDEX idx_organizationaudittrailhistory_statename  
	ON organizationaudittrailhistory  USING btree  (statename);
CREATE INDEX idx_organizationaudittrailhistory_stateid  
	ON organizationaudittrailhistory  USING btree  (stateid);
CREATE INDEX idx_organizationaudittrailhistory_districtname  
	ON organizationaudittrailhistory  USING btree  (districtname);
CREATE INDEX idx_organizationaudittrailhistory_districtid  
	ON organizationaudittrailhistory  USING btree  (districtid);
CREATE INDEX idx_organizationaudittrailhistory_schoolname  
	ON organizationaudittrailhistory  USING btree  (schoolname);
CREATE INDEX idx_organizationaudittrailhistory_schoolid  
	ON organizationaudittrailhistory  USING btree  (schoolid);
CREATE INDEX idx_organizationaudittrailhistory_modifiedusername  
	ON organizationaudittrailhistory  USING btree  (modifiedusername);
CREATE INDEX idx_organizationaudittrailhistory_createddate  
	ON organizationaudittrailhistory  USING btree  (createddate);	
	
--Student Changes  
ALTER TABLE studentaudittrailhistory ADD  COLUMN  studentid bigint;
ALTER TABLE studentaudittrailhistory ADD  COLUMN  modifiedusername character varying;
ALTER TABLE studentaudittrailhistory ADD  COLUMN  modifieduserfirstname character varying;
ALTER TABLE studentaudittrailhistory ADD  COLUMN  modifieduserlastName character varying;
ALTER TABLE studentaudittrailhistory ADD  COLUMN  modifiedUsereducatoridentifier character varying;

CREATE INDEX idx_studentaudittrailhistory_createddate  
	ON studentaudittrailhistory  USING btree  (createddate);	

--User Changes 
ALTER TABLE useraudittrailhistory ADD  COLUMN  modifiedusername character varying;
ALTER TABLE useraudittrailhistory ADD  COLUMN  modifieduserfirstname character varying;
ALTER TABLE useraudittrailhistory ADD  COLUMN  modifieduserlastName character varying;
ALTER TABLE useraudittrailhistory ADD  COLUMN  modifiedUsereducatoridentifier character varying;
ALTER TABLE useraudittrailhistory ADD  COLUMN  username character varying;
ALTER TABLE useraudittrailhistory ADD  COLUMN  userfirstname character varying;
ALTER TABLE useraudittrailhistory ADD  COLUMN  userlastName character varying;
ALTER TABLE useraudittrailhistory ADD  COLUMN  usereducatoridentifier character varying;	
   
CREATE INDEX idx_useraudittrailhistory_username  
	ON useraudittrailhistory  USING btree  (username);
CREATE INDEX idx_useraudittrailhistory_usereducatoridentifier  
	ON useraudittrailhistory  USING btree  (usereducatoridentifier);
CREATE INDEX idx_useraudittrailhistory_createddate  
	ON useraudittrailhistory  USING btree  (createddate);

--Roster Changes
ALTER TABLE rosteraudittrailhistory ADD  COLUMN  modifiedusername character varying;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN  modifieduserfirstname character varying;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN  modifieduserlastName character varying;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN  modifiedUsereducatoridentifier character varying;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN rosterName character varying;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN state character varying;
ALTER  TABLE rosteraudittrailhistory ADD  COLUMN  stateid bigint;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN district character varying;
ALTER  TABLE  rosteraudittrailhistory ADD  COLUMN  districtid bigint;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN school character varying;
ALTER  TABLE  rosteraudittrailhistory ADD  COLUMN  schoolid bigint;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN educatorname character varying;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN educatorid character varying;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN subject character varying;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN subjectid bigint;
ALTER TABLE rosteraudittrailhistory ADD  COLUMN educatorinternalid bigint;

CREATE INDEX idx_rosteraudittrailhistory_educatorid  
	ON rosteraudittrailhistory  USING btree  (educatorid);
CREATE INDEX idx_rosteraudittrailhistory_subjectid  
	ON rosteraudittrailhistory  USING btree  (subjectid);
CREATE INDEX idx_rosteraudittrailhistory_schoolid  
	ON rosteraudittrailhistory  USING btree  (schoolid);
CREATE INDEX idx_rosteraudittrailhistory_districtid  
	ON rosteraudittrailhistory  USING btree  (districtid);
CREATE INDEX idx_rosteraudittrailhistory_stateid  
	ON rosteraudittrailhistory  USING btree  (stateid);
CREATE INDEX idx_rosteraudittrailhistory_createddate  
	ON rosteraudittrailhistory  USING btree  (createddate);
