--ddl/41.sql
  
--For Fcs Changes
  alter table firstcontactsurveyaudithistory add column modifieduserfirstname character varying;
  alter table firstcontactsurveyaudithistory add column modifieduserlastname character varying;
  alter table firstcontactsurveyaudithistory add column modifiedusereducatoridentifier character varying;
  alter table firstcontactsurveyaudithistory add column modifiedusername character varying;
  
  CREATE INDEX idx_firstcontactsurveyaudithistory_createddate  
 ON firstcontactsurveyaudithistory  USING btree  (createddate);
 
  
--Role Audit History
  
  alter table roleaudittrailhistory add column assessmentprogram character varying(75);
  alter table roleaudittrailhistory add column statenames text;
  alter table roleaudittrailhistory add column assessmentprogramid bigint;
  alter table roleaudittrailhistory add column affectedrolename character varying;
  alter table roleaudittrailhistory add column stateids text;
  alter table roleaudittrailhistory add column modifieduserfirstname character varying;
  alter table roleaudittrailhistory add column modifieduserlastname character varying;
  alter table roleaudittrailhistory add column modifiedusereducatoridentifier character varying;
  alter table roleaudittrailhistory add column modifiedusername character varying;
  
 CREATE INDEX idx_roleaudittrailhistory_assessmentprogramid  
 ON roleaudittrailhistory  USING btree  (assessmentprogramid);
 
 
 CREATE INDEX idx_roleaudittrailhistory_createddate  
 ON roleaudittrailhistory  USING btree  (createddate);
 
--Student Pnp
  
  alter table studentpnpsaudithistory add column modifieduserfirstname character varying;
  alter table studentpnpsaudithistory add column modifieduserlastname character varying;
  alter table studentpnpsaudithistory add column modifiedusereducatoridentifier character varying;
  alter table studentpnpsaudithistory add column modifiedusername character varying;  
  alter table studentpnpsaudithistory add column modifieduser bigint;
 
  CREATE INDEX idx_studentpnpsaudithistory_modifieduser  
 ON studentpnpsaudithistory  USING btree  (modifieduser);
 
--Organization 
  
 ALTER TABLE organizationmanagementaudit add COLUMN sourceorgnamewithidentifier  varchar(100);
 ALTER TABLE organizationmanagementaudit add column destorgnamewithidentifier  varchar(100);
 alter table organizationmanagementaudit add column rostername  varchar(75);
 alter table organizationmanagementaudit add column aartusername  varchar(254);
 alter table organizationmanagementaudit add column modifiedusername  varchar(254);
 alter table organizationmanagementaudit add column statestudentidentifier  varchar(50);
 alter table organizationmanagementaudit add column sourceorgdistrictid bigint; 
 alter table organizationmanagementaudit add column sourceorgdistrictname  varchar(100);
 alter table organizationmanagementaudit add column destorgdistrictid bigint;
 alter table organizationmanagementaudit add column destorgdistrictname  varchar(100);
 alter table organizationmanagementaudit add column stateid bigint;
 alter table organizationmanagementaudit add column statename  varchar(100);
	
CREATE INDEX idx_organizationmanagementaudit_sourceorgdistrictid
 ON organizationmanagementaudit  USING btree  (sourceorgdistrictid);
 
 CREATE INDEX idx_organizationmanagementaudit_destorgdistrictid  
 ON organizationmanagementaudit  USING btree  (destorgdistrictid);
 
 CREATE INDEX idx_organizationmanagementaudit_stateid 
 ON organizationmanagementaudit  USING btree  (stateid);
 
   CREATE INDEX idx_organizationmanagementaudit_createddate  
 ON organizationmanagementaudit  USING btree  (createddate);