-- INFO original 26.sql
 
 --PNP CREATE profileitemattributecontainer
CREATE TABLE profileitemattributecontainer
(
  id bigserial NOT NULL,
  attributecontainer character varying(100) NOT NULL,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT pk_profile_item_attribute_container PRIMARY KEY (id),
  CONSTRAINT uk_profile_item_attribute_container UNIQUE (attributecontainer)
)
WITH (
  OIDS=FALSE
);
 

 --PNP CREATE profileitemattributenickname
CREATE TABLE profileitemattributenickname
(
  id bigserial NOT NULL,
  attributenickname character varying(100) NOT NULL,
  attributenicknamestandardid bigint,
  attributenicknamecode bigint,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT pk_profile_item_attribute_nick_name PRIMARY KEY (id),
  CONSTRAINT uk_profile_item_attribute_nick_name UNIQUE (attributenickname)
)
WITH (
  OIDS=FALSE
);
 
 
 --PNP CREATE profileitemattributenicknamecontainer
CREATE TABLE profileitemattributenicknamecontainer
(
  id bigserial NOT NULL,
  attributenameid bigint,
  attributecontainerid bigint,
  attributenicknameid bigint,
  createddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser integer NOT NULL,
  activeflag boolean DEFAULT true,
  modifieddate timestamp without time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser integer NOT NULL,
  CONSTRAINT pk_profile_item_attribute_name_container PRIMARY KEY (id),
  CONSTRAINT uk_profile_item_attribute_name_container UNIQUE (attributenameid, attributecontainerid, attributenicknameid),
  CONSTRAINT fk_profile_item_attribute_nickname_container_attribute_name_id FOREIGN KEY (attributenameid)
      REFERENCES profileitemattribute (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_profile_item_attribute_nickname_container_attribute_container_id FOREIGN KEY (attributecontainerid)
      REFERENCES profileitemattributecontainer (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_profile_item_attribute_nickname_container_attribute_nickname_id FOREIGN KEY (attributenicknameid) 
	REFERENCES profileitemattributenickname (id) ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

--PNP ALTER studentprofileitemattributevalue

ALTER TABLE  studentprofileitemattributevalue ADD profileitemattributenicknamecontainerid bigint;

ALTER TABLE studentprofileitemattributevalue ADD CONSTRAINT fk_profile_item_attribute_nickname_container_id FOREIGN KEY (profileitemattributenicknamecontainerid) 
	REFERENCES profileitemattributenicknamecontainer (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE studentprofileitemattributevalue DROP CONSTRAINT "profile_item_attribute_fkey";

ALTER TABLE studentprofileitemattributevalue DROP CONSTRAINT "uk_student_profile_item";

--US10897 change for MC-MS item type.

CREATE OR REPLACE FUNCTION string_to_integer_array(in inputText text)
  RETURNS bigint[] AS
$BODY$ 

  DECLARE

    elems text[];      

    elemsOut bigint[];

    validInput boolean;

  BEGIN

	inputText := trim( both ']' from (trim(both '[' from inputText) ) );

	Select inputText similar to '(\d+)(,\s*\d+)*' into validInput;
	
	if (inputText is null) OR (inputText = '0') OR (inputText = '') OR ( validInput is null) OR (validInput is false) then

		return null;

	end if;

    elems := string_to_array(inputText, ',');

	if elems is null then

		return null;

	end if;

    FOR i IN array_lower(elems, 1) .. array_upper(elems, 1) LOOP

      elemsOut[i]=cast(elems[i] as bigint);

    END LOOP;

    return elemsOut;

  END

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

CREATE OR REPLACE VIEW node_report AS 
 SELECT s1.id AS student_id, s1.statestudentidentifier AS state_student_identifier, s1.legalfirstname AS legal_first_name, 
 s1.legalmiddlename AS legal_middle_name, s1.legallastname AS legal_last_name, summary.node_key, st1.id AS students_tests_id,
  t1.id AS test_id, t1.testname AS test_name, t1.status AS test_status_id, tc1.id AS test_collection_id, tc1.name AS test_collection_name, summary.no_of_responses, summary.correct_response
   FROM student s1, studentstests st1, test t1, testcollectionstests tct1, testcollection tc1,
    ( SELECT sr.studentid AS student_id, tvln.nodecode AS node_key, count(1) AS no_of_responses, tvf.correctresponse AS correct_response
           FROM studentsresponses sr, student s, taskvariantsfoils tvf, taskvariant tv, taskvariantlearningmapnode tvln,
            studentstests st, test t, testcollectionstests tct, testcollection tc
          WHERE sr.studentstestsid = st.id
           AND ( 
		tvf.foilid = sr.foilid
		 or
		  ( tvf.foilid is null and response is not null and response like '[%]'and trim( both ']' from (trim(both '[' from response) ) ) similar to '(\d+)(,\s*\d+)*' and tvf.foilid=any(string_to_integer_array(response)) )     
		)
            AND tv.id = tvf.taskvariantid
           AND tv.id = tvln.taskvariantid AND s.id = sr.studentid AND st.testid = t.id AND tct.testid = t.id AND tct.testcollectionid = tc.id
          GROUP BY sr.studentid, tvln.nodecode, tvf.correctresponse) summary
  WHERE st1.studentid = s1.id AND st1.testid = tct1.testid AND t1.id = st1.testid
   AND tct1.testid = t1.id AND tct1.testcollectionid = tc1.id AND summary.student_id = s1.id
  ORDER BY s1.id, summary.node_key, summary.correct_response;

  