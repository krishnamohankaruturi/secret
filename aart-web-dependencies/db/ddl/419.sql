--419.sql
--US16252 & US16246

alter table batchupload add column stateid bigint;
alter table batchupload add column districtid bigint;
alter table batchupload add column schoolid bigint;
alter table batchupload add column selectedorgid bigint;
alter table batchupload add column uploadeduserorgid bigint;
alter table batchupload add column uploadedusergroupid bigint;