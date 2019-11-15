--649.sql DDL for F
-- Table: organizationdetail

-- DROP TABLE organizationdetail;

CREATE TABLE organizationdetail
(
  id bigserial NOT NULL,
  organizationid bigint NOT NULL,
  createddate timestamp with time zone,
  createduser integer,
  modifieddate timestamp with time zone,
  modifieduser integer,
  activeflag boolean,
  itistartdate date,
  itienddate date,
  CONSTRAINT organizationdetail_pkey PRIMARY KEY (id),
  CONSTRAINT fk_orgdetail_created_user FOREIGN KEY (createduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_orgdetail_orgid FOREIGN KEY (organizationid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_orgdetail_updated_user FOREIGN KEY (modifieduser)
      REFERENCES aartuser (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE organizationreportdetails ADD COLUMN reporttype character varying(25);

CREATE INDEX idx_organizationreportdetails_reporttype ON organizationreportdetails USING btree (reporttype);

CREATE OR REPLACE FUNCTION DFMInsert(_extractTypeId integer, _fileName character, _fileLocation character,_specialDataDetailFileLocation character,_specialDataDetailFileName character ,_userName character,_givenAssessmentProgram character)
  RETURNS void AS
  
  $BODY$
  DECLARE
  _stateId bigint;
  _stateIds bigint[];
  _assessmentprogramid bigint;
  _assessIds bigint[];
  _user bigint;
      BEGIN

     SELECT  array_agg(distinct stateid) INTO _stateIds FROM organizationtreedetail where stateid is  not null;
      RAISE INFO 'Affected State : %', _stateIds;

      select id into _user from aartuser where username = 'cetesysadmin';
IF(_givenAssessmentProgram is NULL) THEN
       SELECT array_agg(distinct id) INTO _assessIds FROM assessmentprogram ;
	RAISE INFO 'Affected AssessmentProgram : %', _assessIds;

      FOR _stateId IN SELECT * FROM unnest(_stateIds) LOOP       

      FOR _assessmentprogramid IN SELECT * FROM unnest(_assessIds) LOOP
      
        INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filename , filelocation, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
        VALUES(_extractTypeId, _assessmentprogramid, _stateId, _fileName , _fileLocation, _specialDataDetailFileLocation, _specialDataDetailFileName, now(), _user, now(), _user);
        END LOOP;
	END LOOP;
	ELSE
 select id into _assessmentprogramid from assessmentprogram where abbreviatedname=_givenAssessmentProgram;

	IF(_assessmentprogramid is NULL) THEN
		RAISE NOTICE 'No assessmentprogramid found with the given information';
		RETURN;
	END IF;
	 FOR _stateId IN SELECT * FROM unnest(_stateIds) LOOP 
	INSERT INTO datadictionaryfilemapping(extracttypeid, assessmentprogramid, stateid, filename , filelocation, specialdatadetailfilelocation, specialdatadetailfilename, createddate, createduser, modifieddate, modifieduser)
        VALUES(_extractTypeId, _assessmentprogramid, _stateId, _fileName , _fileLocation, _specialDataDetailFileLocation, _specialDataDetailFileName, now(), _user, now(), _user);
        END LOOP;
END IF;

      END;
  $BODY$
  LANGUAGE 'plpgsql' VOLATILE
  COST 100;

--adding UNIQUE KEY for datadictionaryfilemapping
ALTER TABLE datadictionaryfilemapping ADD CONSTRAINT uk_datadictionaryfilemapping  UNIQUE (extracttypeid, assessmentprogramid, stateid,activeflag);
