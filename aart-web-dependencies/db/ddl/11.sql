--INFO: orginal 30.sql AART related tables.
--TODO is this function necessary.

CREATE OR REPLACE FUNCTION addon(i bigint) RETURNS character varying(10) AS
        $BODY$
        BEGIN
		if(i < 1) THEN
			return '';
		else    
			RETURN '.' || (i + 1);
		end if;
        END;
$BODY$
LANGUAGE plpgsql;

ALTER TABLE student
   ALTER COLUMN username TYPE character varying(100);
   
   ALTER TABLE student ADD CONSTRAINT uk_username UNIQUE (username);

   
ALTER TABLE operationaltestwindow ADD suspendwindow boolean DEFAULT false NOT NULL;

ALTER TABLE roster ADD statesubjectcourseidentifier character varying(75);
--PNP profile changes.

-- Table: profileitemattribute

-- DROP TABLE profileitemattribute;

CREATE TABLE profileitemattribute
(
  id bigserial NOT NULL ,
  attributename character varying(100) NOT NULL,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT pk_profile_item_attribute PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);


-- Table: studentprofileitemattributevalue

-- DROP TABLE studentprofileitemattributevalue;

CREATE TABLE studentprofileitemattributevalue
(
  id bigserial NOT NULL,
  selectedvalue character varying(100) NOT NULL,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  profileitemattributeid bigint NOT NULL,
  studentid bigint NOT NULL,
  CONSTRAINT pk_student_profile_item_attribute_value PRIMARY KEY (id ),
  CONSTRAINT profile_item_attribute_fkey FOREIGN KEY (profileitemattributeid)
      REFERENCES profileitemattribute (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT student_fk FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_student_profile_item UNIQUE (studentid , profileitemattributeid )
)
WITH (
  OIDS=FALSE
);

--end pnp profile changes.

ALTER TABLE student DROP CONSTRAINT uk_state_student_identifier;

--INFO: AART related tables. original 31.sql

ALTER TABLE studentstestsections ADD COLUMN statusId bigint;

update studentstestsections
set statusId = (Select id from category where categorycode='unused')
where statusId is null;

ALTER TABLE studentstestsections ADD CONSTRAINT
fk_status FOREIGN KEY (statusid)
REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE studentstestsections
   ALTER COLUMN statusid SET NOT NULL;
   
update studentstests
set status = (Select id from category where categorycode='unused' limit 1)
where status is null;

ALTER TABLE studentstests ADD CONSTRAINT
fk_students_tests_status FOREIGN KEY (status)
REFERENCES category (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE studentstests
   ALTER COLUMN status SET NOT NULL;
   
ALTER TABLE taskvariant ADD COLUMN nodeid bigint NOT NULL default 1;

--TODO remove this after publishing changes are made.
--ALTER TABLE taskvariant ADD COLUMN nodeid drop default;

ALTER TABLE stimulusvariant ADD COLUMN nodeid bigint NOT NULL default 1;

--TODO remove this after publishing changes are made.
--ALTER TABLE taskvariant ADD COLUMN nodeid drop default;

 --US10734: Monitor High Stake Testing Status  
 ALTER TABLE testcollection ADD systemFlag boolean DEFAULT false NOT NULL;
 
 CREATE OR REPLACE VIEW node_report AS 
 SELECT s1.id AS student_id, s1.statestudentidentifier AS state_student_identifier, s1.legalfirstname AS legal_first_name, s1.legalmiddlename AS legal_middle_name, s1.legallastname AS legal_last_name, summary.node_id, summary.node_id || 'Key'::text AS node_key, st1.id AS students_tests_id, t1.id AS test_id, t1.testname AS test_name, t1.status AS test_status_id, tc1.id AS test_collection_id, tc1.name AS test_collection_name, summary.no_of_responses, summary.correct_response
   FROM student s1, studentstests st1, test t1, testcollectionstests tct1, testcollection tc1, ( SELECT sr.studentid AS student_id, tv.nodeid AS node_id, count(1) AS no_of_responses, tvf.correctresponse AS correct_response
           FROM studentsresponses sr, student s, taskvariantsfoils tvf, taskvariant tv, studentstests st, test t, testcollectionstests tct, testcollection tc
          WHERE tvf.foilid = sr.foilid AND tv.id = tvf.taskvariantid AND s.id = sr.studentid AND st.testid = t.id AND tct.testid = t.id AND tct.testcollectionid = tc.id
          GROUP BY sr.studentid, tv.nodeid, tvf.correctresponse) summary
  WHERE st1.studentid = s1.id AND st1.testid = tct1.testid AND t1.id = st1.testid AND tct1.testid = t1.id AND tct1.testcollectionid = tc1.id AND summary.student_id = s1.id
  ORDER BY s1.id, summary.node_id, summary.correct_response;
  
  --INFO: AART related tables. original 32.sql

-- Start -- Alter statements for roster table -- US10906 : Change Request: Web Service Roster (STCO) upload required field updates

ALTER TABLE roster ALTER COLUMN statecourseid DROP NOT NULL;

-- End -- Alter statements for roster table -- US10906 : Change Request: Web Service Roster (STCO) upload required field updates

--test collection flags.

ALTER TABLE category
   ALTER COLUMN categorydescription TYPE character varying(150);
   
-- US7014

CREATE TABLE testcollectionssessionrules
(
  testcollectionid bigint NOT NULL,
  sessionruleid bigint NOT NULL,
  graceperiod bigint,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT test_collections_session_rules_pkey PRIMARY KEY (testcollectionid , sessionruleid ),
  CONSTRAINT fk_test_collections_session_rules_crdusr FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_test_collections_session_rules_updusr FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT test_collections_session_rules_session_rule_id_fkey FOREIGN KEY (sessionruleid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT test_collections_session_rules_test_collection_id_fkey FOREIGN KEY (testcollectionid)
      REFERENCES testcollection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

--dropping the system flag added in R5-I2
ALTER TABLE testcollection DROP COLUMN systemFlag;