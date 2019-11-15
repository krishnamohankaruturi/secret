--ddl/4.sql
CREATE TABLE questarregistrationreason
(
  batchregistrationid bigint NOT NULL,
  studentid bigint,
  reason text,
  testsessionid bigint
);