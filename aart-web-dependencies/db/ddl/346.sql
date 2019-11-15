--346.sql
CREATE INDEX idx_studentstests_status ON studentstests USING btree (status);

CREATE TABLE studenttracker
(
  id bigserial NOT NULL,
  studentid bigint NOT NULL,
  contentareaid bigint NOT NULL,
  status character varying(20) NOT NULL,
  activeflag boolean DEFAULT true,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser bigint,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser bigint,
  CONSTRAINT pk_studenttracker PRIMARY KEY (id),
  CONSTRAINT fk_studenttracker_contentarea FOREIGN KEY (contentareaid)
      REFERENCES contentarea (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studenttracker_student FOREIGN KEY (studentid)
      REFERENCES student (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX idx_studenttracker_contentarea ON studenttracker USING btree (contentareaid);
CREATE INDEX idx_studenttracker_status ON studenttracker USING btree (status);
CREATE INDEX idx_studenttracker_student ON studenttracker USING btree(studentid);

CREATE TABLE studenttrackerband
(
  id bigserial NOT NULL,
  studenttrackerid bigint NOT NULL,
  complexitybandid bigint NOT NULL,
  testsessionid bigint,
  source character varying(40) NOT NULL,
  activeflag boolean DEFAULT true,
  createddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  createduser bigint,
  modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
  modifieduser bigint,
  CONSTRAINT pk_studenttrackerband PRIMARY KEY (id),
  CONSTRAINT fk_studenttrackerband_complexityband FOREIGN KEY (complexitybandid)
      REFERENCES complexityband (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studenttrackerband_contentarea FOREIGN KEY (studenttrackerid)
      REFERENCES studenttracker (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_studenttrackerband_testsession FOREIGN KEY (testsessionid)
      REFERENCES testsession (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX idx_studenttrackerband_studenttracker ON studenttrackerband
  USING btree (studenttrackerid);
CREATE INDEX idx_studenttrackerband_complexityband ON studenttrackerband
  USING btree (complexitybandid);
CREATE INDEX idx_studenttrackerband_testsession ON studenttrackerband
  USING btree (testsessionid);
 
--testspec
ALTER TABLE testspecification ADD COLUMN activeflag boolean;
ALTER TABLE testspecification ALTER COLUMN activeflag SET DEFAULT true;
update testspecification set activeflag=true;
ALTER TABLE testspecification ALTER COLUMN activeflag SET NOT NULL;

CREATE INDEX idx_test_status ON test USING btree (status);
CREATE INDEX idx_test_testspecification ON test USING btree (testspecificationid);
CREATE INDEX idx_test_contentarea ON test USING btree (contentareaid);
CREATE INDEX idx_test_gradecourse ON test USING btree (gradecourseid);
CREATE INDEX idx_test_gradeband ON test USING btree (gradebandid);
ALTER TABLE test DROP CONSTRAINT lmassessmentmodelrule_testspecificationid_fk1;
ALTER TABLE test RENAME CONSTRAINT testcollection_contentareaid_fkey TO test_contentareaid_fkey;
ALTER TABLE test RENAME CONSTRAINT testcollection_gradecourseid_fkey TO test_gradecourseid_fkey;

CREATE INDEX idx_lmassessmentmodelrule_contentframeworkdetail ON lmassessmentmodelrule USING btree (contentframeworkdetailid);
CREATE INDEX idx_lmassessmentmodelrule_testspecification ON lmassessmentmodelrule USING btree (testspecificationid);
CREATE INDEX idx_lmassessmentmodelrule_tranking ON lmassessmentmodelrule USING btree (ranking);

CREATE INDEX idx_testspecification_contentpool ON testspecification USING btree (contentpool);
CREATE INDEX idx_testspecification_phase ON testspecification USING btree (phase);
CREATE INDEX idx_roster_statecoursesid ON roster USING btree (statecoursesid);

--for tde
CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatus(in_studenttestid bigint, in_testsectionid bigint, in_sectionscore text, in_testscore text, in_categorycode text)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
    trackerid bigint;
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
		update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'),
			 scores = in_testscore, modifieddate=now(), enddatetime = now() where id = in_studenttestid;
			 
		IF trackerid is not null THEN
			update public.studenttracker set status='UNTRACKED' where status='TRACKED' and id = trackerid;
		END IF;
	ELSIF in_testscore IS NOT NULL THEN
		update public.studentstests set scores = in_testscore, modifieddate=now() where id = in_studenttestid;
	END IF;
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
