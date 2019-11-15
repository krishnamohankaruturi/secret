-- 460 ddl
-- changes from cb team - US16706
CREATE TABLE testpanelscoring
(
  testpanelid bigint NOT NULL,
  externaltaskvariantid bigint,
  score double precision,
  scoreorder bigint,
  createdate timestamp with time zone,
  modifieddate timestamp with time zone,
  activeflag boolean,
  CONSTRAINT testpanelscoring_pk1 PRIMARY KEY (testpanelid,externaltaskvariantid,scoreorder),
  CONSTRAINT testpanelscoring_fk1 FOREIGN KEY (testpanelid)
      REFERENCES testpanel (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE testpanelstagemapping DROP CONSTRAINT testpanelstagemapping_fk2;
ALTER TABLE testpanelstagemapping DROP CONSTRAINT testpanelstagemapping_fk3;
ALTER TABLE testpanelstagemapping DROP CONSTRAINT testpanelstagemapping_fk4;

ALTER TABLE testpanelstagemapping RENAME testid1 to externaltestid1;
ALTER TABLE testpanelstagemapping RENAME testid2 to externaltestid2;
ALTER TABLE testpanelstagemapping RENAME testid3 to externaltestid3;

ALTER TABLE testpanelstagetestcollection DROP CONSTRAINT testpanelstagetestcollection_fk1;
ALTER TABLE testpanelstagetestcollection RENAME CONSTRAINT testpanelstagetestcollection_fk2 to testpanelstagetestcollection_fk1;
ALTER TABLE testpanelstagetestcollection RENAME testcollectionid to externaltestcollectionid;
ALTER TABLE stimulusvariant ADD COLUMN simulationitemdata text;
--ALTER TABLE stimulusvariant ADD COLUMN version bigint;

-- TDE changes requested
ALTER TABLE studentstests ADD COLUMN interimtheta double precision;
ALTER TABLE studentstests ADD COLUMN previousstudentstestid bigint;

CREATE SEQUENCE studentsresponseparameters_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE TABLE studentsresponseparameters
(
  id bigint NOT NULL DEFAULT nextval('studentsresponseparameters_id_seq'::regclass),
  studentstestsid bigint NOT NULL,
  studentstestsectionsid bigint NOT NULL,
  testid bigint NOT NULL,
  taskvariantid bigint NOT NULL,
  score numeric(6,3),
  avalue double precision,
  bvalue double precision,
  b2value double precision,
  formulacode integer,
  createddate timestamp without time zone DEFAULT now(),
  modifieddate timestamp without time zone DEFAULT now(),
  createduser integer,
  modifieduser integer,
  activeflag boolean DEFAULT true,
  CONSTRAINT studentsresponseparameters_pkey PRIMARY KEY (id),
  CONSTRAINT fk_studentsresponseparameters_studentstestsid FOREIGN KEY (studentstestsid)
        REFERENCES studentstests (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentsresponseparameters_studentstestsectionsid FOREIGN KEY (studentstestsectionsid)
          REFERENCES studentstestsections (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentsresponseparameters_testid FOREIGN KEY (testid)
            REFERENCES test (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentsresponseparameters_taskvariantid FOREIGN KEY (taskvariantid)
              REFERENCES taskvariant (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE OR REPLACE FUNCTION addorupdateresponseparameters(in_testid bigint, in_studenttestid bigint, 
in_studenttestsectionid bigint, in_taskid bigint, in_score numeric, in_avalue numeric, 
in_bvalue numeric, in_b2value numeric, in_formulacode integer)
  RETURNS integer AS
$BODY$
  BEGIN
INSERT INTO studentsresponseparameters(testid, studentstestsid, studentstestsectionsid, 
taskvariantid, avalue, bvalue, b2value, formulacode, score) 
VALUES (in_testid, in_studentTestId, in_studentTestSectionId, in_taskId, in_avalue, in_bvalue, in_b2value, in_formulacode, in_score);
RETURN 1;
  EXCEPTION 
    WHEN unique_violation THEN
--RAISE NOTICE unique violation;
UPDATE studentsresponseparameters SET avalue = in_avalue, bvalue = in_bvalue, b2value = in_b2value, formulacode = in_formulacode, score = in_score, modifieddate=now() 
WHERE studentstestsectionsid = in_studentTestSectionId and taskvariantid = in_taskId;
RETURN 1;
    WHEN OTHERS THEN
RETURN 0;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
DROP FUNCTION IF EXISTS updatesectionstatusandgetteststatus(bigint, bigint, text, text, text);

DROP FUNCTION IF EXISTS updatesectionstatusandgetteststatus(bigint, bigint, text, text, text, text);

CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatus(in_studenttestid bigint, in_testsectionid bigint, in_sectionscore text, in_testscore text, in_categorycode text, in_interimthetavalue double precision)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
    trackerid bigint;
    testunusedstatusid bigint;
  BEGIN
	iscomplete := false;
	IF in_categorycode = 'inprogress' THEN
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
				and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now(), startdatetime = now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	ELSE
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
					and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now(), enddatetime = now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	END IF;

	SELECT count(1) into completecount FROM studentstestsections AS sts
		JOIN category On category.id = sts.statusid 
		JOIN public.categorytype ON category.categorytypeid = categorytype.id 
		where categorytype.typecode ='STUDENT_TESTSECTION_STATUS'
			and category.categorycode != 'complete'
			and  sts.studentstestid = in_studenttestid;

	IF completecount = 0 THEN
		iscomplete := true;
		
		trackerid := (select studenttrackerid from studenttrackerband stb inner join studentstests sts 
					on stb.testsessionid=sts.testsessionid where sts.id = in_studenttestid);
		testunusedstatusid := (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'unused');
						
		update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'),
			 scores = in_testscore, interimtheta = in_interimthetavalue, modifieddate=now(), enddatetime = now() where id = in_studenttestid;
		--DLM	 
		IF trackerid is not null THEN
			update public.studenttracker set status='UNTRACKED' where status='TRACKED' and id = trackerid;
		END IF;
		--KAP
		update studentstests set status=testunusedstatusid where id in (select distinct nextst.id from studentstests st
						inner join testsession ts on st.testsessionid=ts.id
						inner join testcollection tc on tc.id=ts.testcollectionid
						inner join stage s on s.predecessorid=tc.stageid
						inner join testcollection nexttc on nexttc.stageid=s.id and tc.contentareaid=nexttc.contentareaid
						inner join testsession nextts on nexttc.id=nextts.testcollectionid
						inner join studentstests nextst on nextst.testsessionid=nextts.id
							and st.studentid=nextst.studentid
						inner join category nextstatus on nextst.status = nextstatus.id
					where nextstatus.categorycode = 'pending' and ts.source='BATCHAUTO' 
						and nextts.source='BATCHAUTO' and st.id = in_studenttestid);
	ELSIF in_testscore IS NOT NULL THEN
		update public.studentstests set scores = in_testscore, interimtheta = in_interimthetavalue, modifieddate=now() where id = in_studenttestid;
	END IF;
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
CREATE SEQUENCE studentadaptivetest_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
CREATE TABLE studentadaptivetest
(
  id bigint NOT NULL DEFAULT nextval('studentadaptivetest_id_seq'::regclass),
  studentstestsid bigint NOT NULL,
  nextexternaltestid bigint,
  nextstudentstestsid bigint,
  processedstatus text,
  message text,
  createddate timestamp without time zone DEFAULT now(),
  modifieddate timestamp without time zone DEFAULT now(),
  createduser integer,
  modifieduser integer,
  activeflag boolean DEFAULT true,
  CONSTRAINT studentadaptivetest_pkey PRIMARY KEY (id),
  CONSTRAINT fk_studentadaptivetest_studentstestsid FOREIGN KEY (studentstestsid)
        REFERENCES studentstests (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studentadaptivetest_assignedstudentstestsid FOREIGN KEY (nextstudentstestsid)
          REFERENCES studentstests (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE studentadaptivetest ADD CONSTRAINT studentadaptivetest_ukey UNIQUE (studentstestsid);

CREATE OR REPLACE FUNCTION addorupdatestudentadaptivetest(in_studenttestid bigint, in_nextexternaltestid bigint, in_message text)
  RETURNS integer AS
$BODY$
  BEGIN
INSERT INTO studentadaptivetest(studentstestsid, nextexternaltestid, message)
VALUES (in_studenttestid, in_nextexternaltestid, in_message);
RETURN 1;
  EXCEPTION 
    WHEN unique_violation THEN
--RAISE NOTICE unique violation;
UPDATE studentadaptivetest SET nextexternaltestid = in_nextexternaltestid, message = in_message, modifieddate=now() WHERE studentstestsid = in_studenttestid;
RETURN 1;
    WHEN OTHERS THEN
RETURN 0;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


ALTER TABLE studentsresponseparameters DROP CONSTRAINT IF EXISTS studentsresponses_ukey;
ALTER TABLE studentsresponseparameters ADD CONSTRAINT studentsresponses_ukey UNIQUE (studentstestsectionsid, taskvariantid);

CREATE TABLE studentstestshighlighterindex
(
  studenttestid bigint NOT NULL,
  highlighterdata text,
  CONSTRAINT studentstestshighlighter_pkey PRIMARY KEY (studenttestid)
)
WITH (
  OIDS=FALSE
);