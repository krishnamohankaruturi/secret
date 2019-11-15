--611.sql

--F431
ALTER TABLE studentstests ADD COLUMN transferedtestsessionid BIGINT;
ALTER TABLE studentstests ADD COLUMN transferedenrollmentid BIGINT;

--SQL Script aartuser changes for F408
-- Adding new column to capture whether user is internal or external. 
alter table aartuser add column isinternaluser boolean default false;

--isinternaluser COLUMN NAME CHANGE FOR aartuser 
ALTER TABLE aartuser RENAME COLUMN isinternaluser TO internaluserindicator;

-- table to keep track of students test that are happeneing on LCS
CREATE TABLE IF NOT EXISTS lcsstudentstests
(
  lcsid text NOT NULL,
  studentstestsid bigint NOT NULL,
  activeflag boolean DEFAULT true,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer,
  modifieduser integer
);