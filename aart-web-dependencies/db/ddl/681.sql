--ddl -- F570 - Interim Predictive Reports scripts from branch

--begin;

--Rawtoscalescores upload
ALTER TABLE rawtoscalescores ADD COLUMN testingprogramid BIGINT,
				   ADD COLUMN reportcycle text,
				   ADD CONSTRAINT rawtoscalescores_testingprogramid_fk FOREIGN KEY (testingprogramid)
				   REFERENCES testingprogram (id) MATCH SIMPLE
				   ON UPDATE NO ACTION ON DELETE NO ACTION;	

DROP INDEX IF EXISTS idx_rawtoscalescores_testingprogramid;
CREATE INDEX idx_rawtoscalescores_testingprogramid
  ON rawtoscalescores
  USING btree
  (testingprogramid);

DROP INDEX IF EXISTS idx_rawtoscalescores_reportcycle;
CREATE INDEX idx_rawtoscalescores_reportcycle
  ON rawtoscalescores
  USING btree
  (reportcycle COLLATE pg_catalog."default");

  
--TestCutScores upload	   			
ALTER TABLE testcutscores ADD COLUMN testingprogramid BIGINT,
			ADD COLUMN reportcycle text,
			ADD CONSTRAINT testcutscores_testingprogramid_fk FOREIGN KEY (testingprogramid)
				   REFERENCES testingprogram (id) MATCH SIMPLE
				    ON UPDATE NO ACTION ON DELETE NO ACTION;	

DROP INDEX IF EXISTS idx_testcutscores_testingprogramid;
CREATE INDEX idx_testcutscores_testingprogramid
  ON testcutscores
  USING btree
  (testingprogramid);

DROP INDEX IF EXISTS idx_testcutscores_reportcycle;
CREATE INDEX idx_testcutscores_reportcycle
  ON testcutscores
  USING btree
  (reportcycle COLLATE pg_catalog."default");

  
--LevelDescriptions upload
ALTER TABLE leveldescription ADD COLUMN testingprogramid BIGINT,
				ADD COLUMN reportcycle text,
				ADD CONSTRAINT leveldescription_testingprogramid_fk FOREIGN KEY (testingprogramid)
      REFERENCES testingprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;	

DROP INDEX IF EXISTS idx_leveldescription_testingprogramid;
CREATE INDEX idx_leveldescription_testingprogramid
  ON leveldescription
  USING btree
  (testingprogramid);

DROP INDEX IF EXISTS idx_leveldescription_reportcycle;
CREATE INDEX idx_leveldescription_reportcycle
  ON leveldescription
  USING btree
  (reportcycle COLLATE pg_catalog."default");

  
--Report process batch entry table used in calculations and report generation	   
ALTER TABLE reportprocess ADD COLUMN testingprogramid bigint,
						ADD COLUMN testingprogramname text,
						ADD COLUMN reportcycle text,
			ADD CONSTRAINT reportprocess_testingprogramid_fk FOREIGN KEY (testingprogramid)
				   REFERENCES testingprogram (id) MATCH SIMPLE
				   ON UPDATE NO ACTION ON DELETE NO ACTION;	

DROP INDEX IF EXISTS idx_reportprocess_testingprogramid;
CREATE INDEX idx_reportprocess_testingprogramid
  ON reportprocess
  USING btree
  (testingprogramid);

ALTER TABLE studentreportreprocess ADD COLUMN testingprogramid bigint,
								   ADD COLUMN testingprogramname text;

DROP INDEX IF EXISTS idx_studentreportreprocess_testingprogramid;
CREATE INDEX idx_studentreportreprocess_testingprogramid
  ON studentreportreprocess
  USING btree
  (testingprogramid);
  
-- Keep track of testingcycles used in a schoolyear
CREATE TABLE testingcycle(
	id bigserial NOT NULL,
	schoolyear bigint NOT NULL,
	assessmentprogramid bigint NOT NULL,
	testingprogramid bigint NOT NULL,
	testingcyclename text NOT NULL,
	operationaltestwindowid bigint,
	sortorder integer NOT NULL,
	activeflag boolean DEFAULT true,
	createduser bigint,
	modifieduser bigint,
	createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	CONSTRAINT testingcycle_pkey PRIMARY KEY (id)
);

DROP INDEX IF EXISTS idx_testingcycle_schoolyear;
CREATE INDEX idx_testingcycle_schoolyear
  ON testingcycle
  USING btree
  (schoolyear);

DROP INDEX IF EXISTS idx_testingcycle_assessmentprogramid;
CREATE INDEX idx_testingcycle_assessmentprogramid
  ON testingcycle
  USING btree
  (assessmentprogramid);

DROP INDEX IF EXISTS idx_testingcycle_testingprogramid;
CREATE INDEX idx_testingcycle_testingprogramid
  ON testingcycle
  USING btree
  (testingprogramid);

DROP INDEX IF EXISTS idx_testingcycle_testingcyclename;
CREATE INDEX idx_testingcycle_testingcyclename
  ON testingcycle
  USING btree
  (testingcyclename);

  
--Predictive reports calculations and pdf file path info
CREATE TABLE interimstudentreport(
  id bigserial NOT NULL,
  studentid bigint NOT NULL,
  enrollmentid bigint NOT NULL,
  gradeid bigint,
  contentareaid bigint,
  attendanceschoolid bigint NOT NULL,  
  schoolname text,
  districtid bigint NOT NULL,  
  districtname text,
  stateid bigint NOT NULL,  
  schoolyear bigint,
  assessmentprogramid bigint, 
  testingprogramid bigint, 
  reportcycle text,
  testid bigint,
  externaltestid bigint,
  studentsteststatus bigint,
  rawscore numeric(6,3),  
  scalescore bigint,
  standarderror numeric(6,3),
  status boolean,
  scorerangedisplayreasonid bigint,
  exitstatus boolean,
  transferred Boolean,  
  totalincludeditemcount integer,
  respondeditemcount integer,
  excludeditemcount integer,
  filepath text,
  generated boolean DEFAULT false,  
  batchreportprocessid bigint NOT NULL,
  operationaltestwindowid bigint,  
  createduser bigint DEFAULT 12,
  modifieduser bigint DEFAULT 12,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
  CONSTRAINT interimstudentreport_pkey PRIMARY KEY (id),  
  CONSTRAINT interimstudentreport_studentid_fk FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT interimstudentreport_enrollmentid_fk FOREIGN KEY (enrollmentid)
      REFERENCES enrollment (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT interimstudentreport_gradeid_fk FOREIGN KEY (gradeid)
      REFERENCES gradecourse (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
  CONSTRAINT interimstudentreport_contentareaid_fk FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,          
  CONSTRAINT interimstudentreport_attendanceschoolid_fk FOREIGN KEY (attendanceschoolid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,      
  CONSTRAINT interimstudentreport_districtid_fk FOREIGN KEY (districtid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,      
  CONSTRAINT interimstudentreport_stateid_fk FOREIGN KEY (stateid)
      REFERENCES organization (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT interimstudentreport_assessmentprogramid_fk FOREIGN KEY (assessmentprogramid)
      REFERENCES assessmentprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT interimstudentreport_testingprogramid_fk FOREIGN KEY (testingprogramid)
      REFERENCES testingprogram (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT interimstudentreport_testid_fk FOREIGN KEY (testid)
      REFERENCES test (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,      
  CONSTRAINT interimstudentreport_batchreportprocessid_fk FOREIGN KEY (batchreportprocessid)
      REFERENCES reportprocess (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,  
CONSTRAINT interimstudentreport_scorerangereasonid_fk FOREIGN KEY (scorerangedisplayreasonid)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP INDEX IF EXISTS idx_interimstudentreport_studentid;
CREATE INDEX idx_interimstudentreport_studentid
  ON interimstudentreport
  USING btree
  (studentid);

DROP INDEX IF EXISTS idx_interimstudentreport_enrollmentid;
CREATE INDEX idx_interimstudentreport_enrollmentid
  ON interimstudentreport
  USING btree
  (enrollmentid);

DROP INDEX IF EXISTS idx_interimstudentreport_testingprogramid;
CREATE INDEX idx_interimstudentreport_testingprogramid
  ON interimstudentreport
  USING btree
  (testingprogramid);

DROP INDEX IF EXISTS idx_interimstudentreport_attendanceschoolid;
CREATE INDEX idx_interimstudentreport_attendanceschoolid
  ON interimstudentreport
  USING btree
  (attendanceschoolid);

DROP INDEX IF EXISTS idx_interimstudentreport_districtid;
CREATE INDEX idx_interimstudentreport_districtid
  ON interimstudentreport
  USING btree
  (districtid);

DROP INDEX IF EXISTS idx_interimstudentreport_stateid;
CREATE INDEX idx_interimstudentreport_stateid
  ON interimstudentreport
  USING btree
  (stateid);

DROP INDEX IF EXISTS idx_interimstudentreport_schoolyear;
CREATE INDEX idx_interimstudentreport_schoolyear
  ON interimstudentreport
  USING btree
  (schoolyear);

DROP INDEX IF EXISTS idx_interimstudentreport_assessmentprogramid;
CREATE INDEX idx_interimstudentreport_assessmentprogramid
  ON interimstudentreport
  USING btree
  (assessmentprogramid);

DROP INDEX IF EXISTS idx_interimstudentreport_reportcycle;
CREATE INDEX idx_interimstudentreport_reportcycle
  ON interimstudentreport
  USING btree
   (reportcycle COLLATE pg_catalog."default");

DROP INDEX IF EXISTS idx_interimstudentreport_status;
CREATE INDEX idx_interimstudentreport_status
  ON interimstudentreport
  USING btree
  (status);

DROP INDEX IF EXISTS idx_interimstudentreport_testid;
CREATE INDEX idx_interimstudentreport_testid
  ON interimstudentreport
  USING btree
  (testid);

DROP INDEX IF EXISTS idx_interimstudentreport_otwid;
CREATE INDEX idx_interimstudentreport_otwid
  ON interimstudentreport
  USING btree
  (operationaltestwindowid);


--QuestionInformation upload : 2nd page table data
DROP TABLE IF EXISTS questioninformation;
CREATE TABLE questioninformation(id bigserial not null,
	schoolyear bigint not null,
	assessmentprogramid bigint not null,
	testingprogramid bigint not null,
	reportcycle text not null,	
	contentareaid bigint not null,
	gradeid bigint not null,
	externaltestid bigint,
	taskvariantexternalid bigint not null,
	questiondescription text not null,
	creditpercent integer,
	comment text,
	activeflag boolean,
	batchuploadid bigint,
	createduser bigint,
	modifieduser bigint,
	createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	CONSTRAINT interimquestiondescriptions_pkey PRIMARY KEY (id),
	CONSTRAINT questioninformation_assessmentprogramid_fk FOREIGN KEY (assessmentprogramid)
	      REFERENCES assessmentprogram (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT questioninformation_testingprogramid_fk FOREIGN KEY (testingprogramid)
	      REFERENCES testingprogram (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT questioninformation_gradeid_fk FOREIGN KEY (gradeid)
	      REFERENCES gradecourse(id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,	      
	CONSTRAINT questioninformation_contentareaid_fk FOREIGN KEY (contentareaid)
	      REFERENCES contentarea (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP INDEX IF EXISTS idx_questioninformation_schoolyear;
CREATE INDEX idx_questioninformation_schoolyear
  ON questioninformation
  USING btree
  (schoolyear);

DROP INDEX IF EXISTS idx_questioninformation_assessmentprogramid;
CREATE INDEX idx_questioninformation_assessmentprogramid
  ON questioninformation
  USING btree
  (assessmentprogramid);

DROP INDEX IF EXISTS idx_questioninformation_testingprogramid;
CREATE INDEX idx_questioninformation_testingprogramid
  ON questioninformation
  USING btree
  (testingprogramid);

DROP INDEX IF EXISTS idx_questioninformation_reportcycle;
CREATE INDEX idx_questioninformation_reportcycle
  ON questioninformation
  USING btree
  (reportcycle COLLATE pg_catalog."default");

DROP INDEX IF EXISTS idx_questioninformation_gradeid;
CREATE INDEX idx_questioninformation_gradeid
  ON questioninformation
  USING btree
  (gradeid);

DROP INDEX IF EXISTS idx_questioninformation_contentareaid;
CREATE INDEX idx_questioninformation_contentareaid
  ON questioninformation
  USING btree
  (contentareaid);

DROP INDEX IF EXISTS idx_questioninformation_externaltestid;
CREATE INDEX idx_questioninformation_externaltestid
  ON questioninformation
  USING btree
  (externaltestid);

DROP INDEX IF EXISTS idx_questioninformation_taskvariantexternalid;
CREATE INDEX idx_questioninformation_taskvariantexternalid
  ON questioninformation
  USING btree
  (taskvariantexternalid);

DROP INDEX IF EXISTS idx_questioninformation_activeflag;
CREATE INDEX idx_questioninformation_activeflag
  ON questioninformation
  USING btree
  (activeflag);

--2nd page question details for a specific student
DROP TABLE IF EXISTS studentreportquestioninfo;
Create table studentreportquestioninfo(
	id bigserial not null,
	interimstudentreportid bigint,
	testid bigint,
	externaltestid bigint,
	taskvariantid bigint,
	taskvariantexternalid bigint,
	taskvariantposition integer,
	questioninformationid bigint,
	creditearned bigint,
	createduser bigint DEFAULT 12,
	modifieduser bigint DEFAULT 12,
	createddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp with time zone,
	CONSTRAINT studentreportquestioninfo_pkey PRIMARY KEY (id),
	CONSTRAINT studentreportquestioninfo_interimstudentreportid_fk FOREIGN KEY (interimstudentreportid)
	      REFERENCES interimstudentreport (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT studentreportquestioninfo_questioninformationid_fk FOREIGN KEY (questioninformationid)
	      REFERENCES questioninformation (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT studentreportquestioninfo_creditearned_fk FOREIGN KEY (creditearned)
	      REFERENCES category (id) MATCH SIMPLE
	      ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP INDEX IF EXISTS idx_questioninformation_interimstudentreportid;
CREATE INDEX idx_questioninformation_interimstudentreportid
  ON studentreportquestioninfo
  USING btree
  (interimstudentreportid);

DROP INDEX IF EXISTS idx_studentreportquestioninfo_qinfoid;
CREATE INDEX idx_studentreportquestioninfo_qinfoid
  ON studentreportquestioninfo
  USING btree
  (questioninformationid);
  
DROP INDEX IF EXISTS idx_studentreportquestioninfo_testid;
CREATE INDEX idx_studentreportquestioninfo_testid
  ON studentreportquestioninfo
  USING btree
  (testid);
  
DROP INDEX IF EXISTS idx_studentreportquestioninfo_externaltestid;
CREATE INDEX idx_studentreportquestioninfo_externaltestid
  ON studentreportquestioninfo
  USING btree
  (externaltestid);
  
DROP INDEX IF EXISTS idx_studentreportquestioninfo_taskvariantid;
CREATE INDEX idx_studentreportquestioninfo_taskvariantid
  ON studentreportquestioninfo
  USING btree
  (taskvariantid);
  
DROP INDEX IF EXISTS idx_studentreportquestioninfo_taskvariantexternalid;
CREATE INDEX idx_studentreportquestioninfo_taskvariantexternalid
  ON studentreportquestioninfo
  USING btree
  (taskvariantexternalid);
  
DROP INDEX IF EXISTS idx_studentreportquestioninfo_creditearned;
CREATE INDEX idx_studentreportquestioninfo_creditearned
  ON studentreportquestioninfo
  USING btree
  (creditearned);
 
--Update testingprogram value to Summative on existing data in rawtoscalescore, testcutscore and leveldescription tables
CREATE OR REPLACE FUNCTION updateTestingProgramInReportUploads(tablename text) 
RETURNS void AS $BODY$

DECLARE 
	local_cursor_p refcursor;
	assessmentprogram_rec RECORD;
	tempUpdateQuery text;
	--programname text;
BEGIN
	RAISE NOTICE 'Processing for table: %', tablename;
	--programname := 'Summative';
	open local_cursor_p FOR
		EXECUTE 'select distinct assessmentprogramid from ' || tablename;
	LOOP
		FETCH local_cursor_p INTO assessmentprogram_rec; EXIT WHEN NOT FOUND;
		RAISE NOTICE 'Processing for table assessmentprogram % in table %', assessmentprogram_rec.assessmentprogramid, tablename;
		tempUpdateQuery = 'update '||tablename||' tname set testingprogramid = tp.id from testingprogram tp where tname.assessmentprogramid = tp.assessmentprogramid and tp.activeflag is true and tp.programname= ''Summative'' and tname.assessmentprogramid = '||assessmentprogram_rec.assessmentprogramid;
		EXECUTE tempUpdateQuery;
	END LOOP;
END; 
$BODY$ LANGUAGE plpgsql;


---Start : Function for specific student report process
DROP FUNCTION updatestudentreportreprocess(text);
CREATE OR REPLACE FUNCTION updatestudentreportreprocess(indexfilename text)
  RETURNS void AS
$BODY$
DECLARE
	var_subjectid BIGINT;
	trim_subject CHARACTER VARYING;
	var_gradeid BIGINT;
	trim_grade CHARACTER VARYING;
	trim_ssid CHARACTER VARYING;
	var_studentid BIGINT;
	var_testingprogramid BIGINT;
	trim_testingprogram TEXT;		
	student_record RECORD;
	row_number INTEGER;
	final_record_count INTEGER;
BEGIN
    row_number := 1;
    
    RAISE NOTICE 'Started processing %', indexFileName;

    FOR student_record IN (SELECT * from studentreportreprocess where calculationscomplete is false) 
    LOOP
	
	SELECT TRIM(student_record.grade) INTO trim_grade;
	SELECT TRIM(student_record.contentarea) INTO trim_subject;
	SELECT TRIM(student_record.testingprogramname) INTO trim_testingprogram;
	IF(student_record.statestudentidentifier IS NOT NULL) THEN
		select st.id from student st 
		join enrollment en ON en.studentid = st.id 
		join studentassessmentprogram sap on sap.studentid = st.id
		join organizationtreedetail otd on otd.stateid = st.stateid and otd.statedisplayidentifier = 'KS'
		join assessmentprogram ap on ap.id = sap.assessmentprogramid and ap.abbreviatedname = 'KAP'
		where en.activeflag is true and en.currentschoolyear = student_record.schoolyear 
		and st.statestudentidentifier = student_record.statestudentidentifier limit 1 into var_studentid;
	END IF;
	
	
	RAISE NOTICE 'Processing Row: %, Student: %, Subject: %, Grade: %', row_number, student_record.statestudentidentifier, trim_subject, trim_grade;
	

	IF( student_record.statestudentidentifier is not null and var_studentid is null ) THEN
		RAISE NOTICE 'Error in row %, Student % not found in EP. Delete %', row_number, student_record.statestudentidentifier, student_record.id;
		DELETE FROM studentreportreprocess WHERE id = student_record.id;
	ELSE
		IF(trim_subject is not null  AND (LOWER(trim_subject) = LOWER('Sci') OR LOWER(trim_subject) = LOWER('M') OR LOWER(trim_subject) = LOWER('ELA'))) THEN
			IF(trim_testingprogram IS NULL) THEN
				trim_testingprogram := 'Summative';
			END IF;
			select id from testingprogram where assessmentprogramid = student_record.assessmentprogramid
								AND LOWER(programname) = LOWER(trim_testingprogram)
								AND activeflag is true 
								INTO var_testingprogramid;

			IF(var_testingprogramid IS NULL) THEN
				select id from testingprogram where assessmentprogramid = student_record.assessmentprogramid
								AND LOWER(programname) = LOWER('Summative')
								AND activeflag is true 
								INTO var_testingprogramid;
			END IF;
			SELECT id FROM contentarea where lower(abbreviatedname) = lower(trim(trim_subject)) INTO var_subjectid;
			IF(var_subjectid is NOT null) THEN
				
				IF(trim_grade is not null OR trim_grade != '') THEN
					SELECT id FROM gradecourse where lower(abbreviatedname) = lower(trim(trim_grade)) and contentareaid = var_subjectid INTO var_gradeid;
					IF(var_gradeid is null) THEN
						RAISE NOTICE 'Error in row %, Grade: % for Subject: % not found in EP. Delete %', row_number, trim_grade, trim_subject, student_record.id;
						DELETE FROM studentreportreprocess WHERE id = student_record.id;
					
					END IF;
				END IF;
				
			ELSE 
				RAISE NOTICE 'Error in row: %, Subject not found in EP for subject code: %', row_number, trim_subject;
				DELETE FROM studentreportreprocess WHERE id = student_record.id;
			END IF;
		ELSE
			IF(trim_subject is not null) THEN
				RAISE NOTICE 'Error in row %, Expected Subjects: ELA/M/Sci ==> FOUND: %', row_number, trim_subject;
				DELETE FROM studentreportreprocess WHERE id = student_record.id;
			END IF;
		END IF;			
		
		
	END IF;
		
	IF(var_studentid is not null) THEN
		update studentreportreprocess set studentid = var_studentid, gradeid = var_gradeid, contentareaid = var_subjectid,
		testingprogramid = var_testingprogramid,
		modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
		where id = student_record.id;
	END IF;		 
				
	row_number := row_number + 1;
	
    END LOOP;     
    select count(id) from studentreportreprocess INTO final_record_count;
    RAISE NOTICE 'Final record count % out of total %', final_record_count, row_number-1; 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
--For student reprocess on local
/*  
begin;
	delete from studentreportreprocess where calculationscomplete is false;
	COPY studentreportreprocess(statestudentidentifier, contentarea, grade, schoolyear, testingprogramname) FROM 'C://dev/KAP_REPORTS/processbystudent.csv' DELIMITER ',' CSV HEADER ;
	select updatestudentreportreprocess('processbystudent.csv');
	select * from studentreportreprocess;
commit;
rollback;  

--For Fix, Stage, Prod
begin;
	delete from studentreportreprocess where calculationscomplete is false;
	\COPY studentreportreprocess(statestudentidentifier, contentarea, grade, schoolyear, testingprogramname) FROM 'processbystudent.csv' DELIMITER ',' CSV HEADER ;
	select updatestudentreportreprocess('processbystudent.csv');
	select * from studentreportreprocess where calculationscomplete is false;
commit;
*/
---End function for specific student report process



--rollback;
--commit;

