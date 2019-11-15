--ddl/39.sql

CREATE SEQUENCE kidsdashboardrecord_id_seq;

CREATE TABLE kidsdashboardrecord
(
id bigserial NOT NULL,
recordtype TEXT NOT NULL,
statestudentidentifier TEXT NOT NULL,
legallastname TEXT,
legalmiddlename TEXT,
legalfirstname TEXT,
dateofbirth DATE,
attendanceschoolid BIGINT NOT NULL,
attendanceschoolidentifier TEXT,
attendanceschoolname TEXT,
aypschoolid BIGINT NOT NULL,
aypschoolidentifier TEXT,
aypschoolname TEXT,
subjectarea TEXT,
currentgradelevel TEXT,
schoolyear BIGINT,
educatoridentifier TEXT,
educatorfirstname TEXT,
educatorlastname TEXT,
status TEXT,
reasons TEXT,
messagetype TEXT,
createdate TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT ('now'::text)::TIMESTAMP WITH TIME ZONE,
modifieddate TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT ('now'::text)::TIMESTAMP WITH TIME ZONE,
processeddate TIMESTAMP WITH TIME ZONE,
successfullyprocesseddate TIMESTAMP WITH TIME ZONE,
recordcommonid BIGINT
);

ALTER TABLE kidsdashboardrecord ALTER COLUMN id SET DEFAULT nextval('kidsdashboardrecord_id_seq'::regclass);

-- Scripts executed by DBA

--trunate kids staging tables of 2017 
truncate table kids_record_staging;
truncate table tasc_record_staging;
truncate table ksdexmlaudit;

-- truncate tables of batchregistration tables
truncate table batchregistrationreason;
truncate table batchregisteredscoringassignments;
truncate table batchregisteredtestsessions;
truncate table batchregistration;

