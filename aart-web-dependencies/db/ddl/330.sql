-- Function: randomizestudenttest(text, text)

-- DROP FUNCTION randomizestudenttest(text, text);

CREATE OR REPLACE FUNCTION randomizestudenttest(IN in_username text, IN in_testtypename text)
  RETURNS TABLE(assessmentprogramid bigint, assessmentprogramname character varying, testingprogramid bigint, testingprogramname character varying, testname character varying, testformatcode character varying, studentstestsid bigint, studentid bigint, testid bigint, testcollectionid bigint, status bigint, testsessionid bigint, activeflag boolean, ticketno character varying, windowopen boolean, createduser integer, modifieduser integer, modifieddate timestamp without time zone, createddate timestamp without time zone) AS
$BODY$
  DECLARE
    studenttestid bigint;
    randomized_testid bigint;
    randomized_testname text;
    status_id bigint;
    timedout_status_id bigint;
    reactivated_status_id bigint;
    inprogress_status_id bigint;
    test_unused_status_id bigint;
    test_inprogress_status_id bigint;
    deployed_status_id bigint;
    test_testsections RECORD;
    studenttestsection RECORD;
    studenttest RECORD;
    grace_period bigint;
    current_millis decimal;
    student_id bigint;
  BEGIN
                select id into student_id from student where username=in_username;
                select into status_id category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id
                                  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
                                  and category.categorycode = 'unused'; 
                select into timedout_status_id category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id
                                  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
                                  and category.categorycode = 'inprogresstimedout';
                select into reactivated_status_id category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id
                                  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
                                  and category.categorycode = 'reactivated';
                select into inprogress_status_id category.id from public.category JOIN public.categorytype ON category.categorytypeid = categorytype.id
                                  where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
                                  and category.categorycode = 'inprogress';
                SELECT c.id into test_unused_status_id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
				AND c.categorycode = 'unused'
				AND ct.typecode='STUDENT_TEST_STATUS';
		SELECT c.id into test_inprogress_status_id FROM category c, categorytype ct WHERE c.categorytypeid = ct.id
				AND c.categorycode = 'inprogress'
				AND ct.typecode='STUDENT_TEST_STATUS';
		select category.id into deployed_status_id from category, categorytype
                where category.categorytypeid = categorytype.id and categorytype.typecode = 'TESTSTATUS' and category.categorycode = 'DEPLOYED';
                                                                  
  FOR studenttestid IN (select sts.id from studentstests sts where sts.testid is null
                                                                and sts.studentid = student_id and sts.activeflag is true
                                                                and sts.status IN (test_unused_status_id, test_inprogress_status_id))
  LOOP
    select into randomized_testid, randomized_testname  test.id, test.testname
                from (select * from studentstests stt join testcollection tc on tc.id = stt.testcollectionid where stt.id = studenttestid ) studenttesttestcollection
                join testcollectionstests tct on tct.testcollectionid = studenttesttestcollection.testcollectionid
                join test on test.id = tct.testid
                where not exists (select distinct st.testid from studentstests st where st.studentid = studenttesttestcollection.studentid 
					and st.testcollectionid = studenttesttestcollection.testcollectionid and st.testid = test.id
					and st.status IN (test_unused_status_id, test_inprogress_status_id))
                and (test.status is null or test.status = deployed_status_id)
                and test.qccomplete = true ORDER BY random() LIMIT 1;
                IF randomized_testid is not null THEN
			update public.studentstests set testid = randomized_testid where id = studenttestid;
 
			FOR test_testsections IN (select testsection.id from public.testsection where testsection.testid = randomized_testid)
                        LOOP
				insert into public.studentstestsections(studentstestid, testsectionid, statusid) values(studenttestid, test_testsections.id, status_id);
			END LOOP;
                END IF;
    END LOOP;
    --get all tests, check grace period timeout and update status
    FOR studenttest IN (SELECT st.id, st.studentid, st.testid,st.testcollectionid
                FROM studentstests st INNER JOIN test t ON st.testid = t.id
                                INNER JOIN student s ON st.studentid = s.id
                                INNER JOIN operationaltestwindow otw ON otw.testcollectionid = st.testcollectionid
                WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
                                AND st.activeflag = true
                                AND otw.suspendwindow = false
                                AND otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP
                                AND s.id = student_id order by st.id)
    LOOP
                SELECT graceperiod into grace_period FROM TestCollectionsSessionRules tcsr, category sessionrule,categorytype catType
                                WHERE tcsr.sessionruleid = sessionrule.id AND sessionrule.categorytypeid=cattype.id
                                AND sessionrule.categorycode='GRACE_PERIOD' AND cattype.typecode='SESSION_RULES'
                                                                AND tcsr.testcollectionid = studenttest.testcollectionid;
                IF grace_period IS NOT NULL THEN
			FOR studenttestsection IN (select sts.id, sts.statusid, (EXTRACT(EPOCH FROM sts.modifieddate)+grace_period) as sectionmodifiedmillis from studentstestsections sts where sts.studentstestid=studenttest.id
                                                                                                AND sts.statusid=inprogress_status_id)
			LOOP
				SELECT EXTRACT(EPOCH FROM current_timestamp) INTO current_millis;
				IF (studenttestsection.sectionmodifiedmillis < current_millis) THEN
					update public.studentstestsections set statusid = timedout_status_id, modifieddate=now() where id = studenttestsection.id;
				END IF;
			END LOOP;
                END IF;
    END LOOP;     
 
    --return all tests
    IF in_testtypename ilike 'Practice'  THEN
	RETURN QUERY SELECT ap.id AS assessmentprogramId,
	cast('Practice' as character varying) AS assessmentprogramname,
	tp.id AS testingprogramid, tp.programname AS testingprogramname,
	t.testname, t.testformatcode,
	st.id as studentstestsid, st.studentid,
	st.testid, st.testcollectionid,
	st.status, st.testsessionid,
	st.activeflag, st.ticketno,true as windowOpen,
	st.createduser, st.modifieduser,
	st.modifieddate, st.createddate
	FROM studentstests st INNER JOIN test t ON st.testid = t.id
			INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
			INNER JOIN assessment a ON atc.assessmentid = a.id
			INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
			INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
			INNER JOIN operationaltestwindow otw ON otw.testcollectionid = st.testcollectionid
	WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
			AND st.activeflag = true AND otw.suspendwindow = false
			AND otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP
			AND st.studentid = student_id AND tp.programname = 'Practice'
			order by ap.programname, st.createddate, t.testname;
    ELSE
	RETURN QUERY SELECT ap.id AS assessmentprogramId,
	ap.programname AS assessmentprogramname,
	tp.id AS testingprogramid, tp.programname AS testingprogramname,
	t.testname, t.testformatcode,
	st.id as studentstestsid, st.studentid,
	st.testid, st.testcollectionid,
	st.status, st.testsessionid,
	st.activeflag, st.ticketno,true as windowOpen,
	st.createduser, st.modifieduser,
	st.modifieddate, st.createddate
	FROM studentstests st INNER JOIN test t ON st.testid = t.id
			INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
			INNER JOIN assessment a ON atc.assessmentid = a.id
			INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
			INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
			LEFT OUTER JOIN operationaltestwindow otw ON otw.testcollectionid = st.testcollectionid
	WHERE st.status IN (test_unused_status_id, test_inprogress_status_id)
			AND st.activeflag = true AND tp.programname != 'Practice'
			AND ((otw.effectivedate <= CURRENT_TIMESTAMP 
				AND otw.expirydate >= CURRENT_TIMESTAMP
				AND otw.suspendwindow = false ) OR t.qccomplete = false)
			AND st.studentid = student_id order by ap.programname, st.createddate, t.testname;
    END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
  
DROP FUNCTION IF EXISTS convert_to_cpassorg(text);

CREATE OR REPLACE FUNCTION convert_to_cpassorg(in_orgdisplayidentifer text)
  RETURNS void AS
$BODY$ 

DECLARE
                ORGRECORD RECORD;
                ORGRELRECORD RECORD;
                USRORGRRECORD RECORD;
                ROSTERRECORD RECORD;
                ENROLLMENTRECORD RECORD;
                currentStateOrgId BIGINT;
                newStateOrgId BIGINT;
                cetesysadminid BIGINT;
                cPassAssessmntPrgid BIGINT;
                
BEGIN
                RAISE NOTICE 'STARTING converting cPass students to a NEW CONTRACTING ORGANIZATION..%', in_orgdisplayidentifer;
                SELECT INTO cetesysadminid (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
                SELECT INTO currentStateOrgId (select id from organization where displayidentifier=in_orgdisplayidentifer and contractingorganization is true and activeflag is true);
                SELECT INTO cPassAssessmntPrgid (select id from assessmentprogram where abbreviatedname='CPASS' and activeflag is true);
                
                RAISE NOTICE 'currentStateOrgId: %', currentStateOrgId;
                RAISE NOTICE 'cetesysadminid: %', cetesysadminid;
                RAISE NOTICE 'cPassAssessmntPrgid: %', cPassAssessmntPrgid;

                RAISE NOTICE 'VERIFYING IF CURRENT organization is a valid organization in EP.. ';
                IF(currentStateOrgId > 0) THEN
                                RAISE NOTICE 'VALID displayIdentifier found-- Continuing';
                ELSE
                                RAISE NOTICE 'INVALID displayIdentifier found-- Exiting the process..';
                                RETURN;
                END IF;

                RAISE NOTICE 'VERIFYING IF CURRENT organization has cPass as assessmentprogram.. ';
                IF((select count(*) from orgassessmentprogram where organizationid=currentStateOrgId and assessmentprogramid=cPassAssessmntPrgid) > 0) THEN
                
                                RAISE NOTICE 'Found CPASS assessment program-- Continuing';
                                
                                RAISE NOTICE 'INSERTING NEW CONTRACTING ORGANIZATION.. ';
                                INSERT INTO organization(
                                                    organizationname, displayidentifier, organizationtypeid, 
                                                    welcomemessage, createddate, activeflag, createduser, modifieduser, 
                                                    modifieddate, buildinguniqueness, schoolstartdate, schoolenddate, 
                                                    contractingorganization, expirepasswords, expirationdatetype, 
                                                    pooltype)
                                    (
                                                select organizationname || '-cPass' || in_orgdisplayidentifer,displayidentifier || '-cPass' || in_orgdisplayidentifer, 
                                                organizationtypeid, welcomemessage, now(), true, 
                                                cetesysadminid, cetesysadminid, now(), buildinguniqueness, schoolstartdate, 
                                                schoolenddate, contractingorganization, expirepasswords, expirationdatetype, pooltype  
                                                from organization where id=currentStateOrgId
                                    );         

                                SELECT INTO newStateOrgId (select id from organization where displayidentifier=(in_orgdisplayidentifer ||'-cPass' || in_orgdisplayidentifer) and contractingorganization is true and activeflag is true);
                                RAISE NOTICE 'newStateOrgId: %', newStateOrgId;

                                RAISE NOTICE 'REMOVING CPASS ASSESSMENT PROGRAM FROM EXISTING ORGANIZATION..';
                                DELETE from orgassessmentprogram where assessmentprogramid=cPassAssessmntPrgid and organizationid=currentStateOrgId and activeflag is true;

                                RAISE NOTICE 'ADDING CPASS ASSESSMENT PROGRAM TO NEW CONTRACTING ORGANIZATION..';
                                INSERT INTO orgassessmentprogram(
                                                    organizationid, assessmentprogramid, createddate, createduser, activeflag, modifieddate, modifieduser)
                                    VALUES (newStateOrgId, cPassAssessmntPrgid, now(), cetesysadminid, true, now(), cetesysadminid);

                                RAISE NOTICE 'SETTING UP ORG HIERARCHY FOR NEW ORGANIZATION..';
                                INSERT INTO organizationhierarchy (organizationid, organizationtypeid) (
                                                select (select id from organization where id=newStateOrgId), organizationtypeid from organizationhierarchy where organizationid=currentStateOrgId
                                );

                                RAISE NOTICE 'CREATING ALL NEW ORGANIZATIONs UNDER CONTRACTING ORGANIZATION..';
                                FOR ORGRECORD IN
                                                select * from organization_children(currentStateOrgId) order by organizationtypeid       
                                LOOP     
                                                RAISE NOTICE 'Creating new Org: %, %', ORGRECORD.id, ORGRECORD.organizationname;
                                                
                                                INSERT INTO organization(
                                                                    organizationname, displayidentifier, organizationtypeid, welcomemessage, createddate, activeflag, 
                                                                    createduser, modifieduser, modifieddate, buildinguniqueness, schoolstartdate, schoolenddate, 
                                                                    contractingorganization, expirepasswords, expirationdatetype, pooltype)
                                                    (
                                                                select organizationname || '-cPass' || in_orgdisplayidentifer, displayidentifier || '-cPass' || in_orgdisplayidentifer, 
                                                                organizationtypeid, welcomemessage, now(), true, 
                                                                cetesysadminid, cetesysadminid, now(), buildinguniqueness, schoolstartdate, schoolenddate, 
                                                                contractingorganization, expirepasswords, expirationdatetype, pooltype  from organization where id=ORGRECORD.id
                                                    );         
                                END LOOP;

                                RAISE NOTICE 'SETTING UP ORG RELATIONS OF ALL ORGS UNDER NEW CONTRACTING ORGANIZATION..';

                                INSERT INTO organizationrelation(
                                                                    organizationid, parentorganizationid, createddate, activeflag, 
                                                                    createduser, modifieduser, modifieddate)
                                                    VALUES (newStateOrgId, 
                                                                    (select id from organization where displayidentifier='CETE' and organizationtypeid=1 and activeflag is true), 
                                                                    now(), true, cetesysadminid, cetesysadminid, now());       
                                                                    
                                FOR ORGRELRECORD IN                
                                                select orgrel.organizationid, orgrel.parentorganizationid,
                                                                childorg.displayidentifier as childdisplayid, parentorg.displayidentifier as partnedisplayid 
                                                from organizationrelation orgrel
                                                join organization childorg on childorg.id = orgrel.organizationid
                                                join organization parentorg on parentorg.id = orgrel.parentorganizationid
                                                where orgrel.organizationid in (select id from organization_children(currentStateOrgId) order by organizationtypeid)        
                                LOOP     
                                                RAISE NOTICE 'Creating Org relations of : %, %, %, %', ORGRELRECORD.organizationid, ORGRELRECORD.parentorganizationid, ORGRELRECORD.childdisplayid, ORGRELRECORD.partnedisplayid;

                                                INSERT INTO organizationrelation(
                                                                    organizationid, parentorganizationid, createddate, activeflag, 
                                                                    createduser, modifieduser, modifieddate)
                                                    VALUES ((select id from organization where displayidentifier=ORGRELRECORD.childdisplayid || '-cPass' || in_orgdisplayidentifer), 
                                                                    (select id from organization where displayidentifier=ORGRELRECORD.partnedisplayid || '-cPass' || in_orgdisplayidentifer), 
                                                                    now(), true, cetesysadminid, cetesysadminid, now());            
                                END LOOP;          

                                RAISE NOTICE 'UPDATING TEACHER ORGS TO NEW CONTRACTING ORGANIZATION..';
                                FOR USRORGRRECORD IN
                                                select uo.*,org.displayidentifier from usersorganizations uo
                                                join organization org on org.id = uo.organizationid
                                                where uo.aartuserid in (
                                                                select distinct r.teacherid from enrollment en
                                                                join student st on st.id = en.studentid 
                                                                join enrollmentsrosters enrlr on enrlr.enrollmentid = en.id
                                                                join roster r on r.id = enrlr.rosterid
                                                                where st.assessmentprogramid is null 
                                                                and en.attendanceschoolid in (select id from organization_children(currentStateOrgId) union select currentStateOrgId)
                                                ) and uo.organizationid in (select id from organization_children(currentStateOrgId) where organizationtypeid=7)   
                                LOOP     
                                                RAISE NOTICE 'Updating user org table of user: %, %', USRORGRRECORD.aartuserid, USRORGRRECORD.organizationid;
                                                                                
                                                UPDATE usersorganizations 
                                                SET organizationid=(select id from organization 
                                                                where displayidentifier=USRORGRRECORD.displayidentifier || '-cPass' || in_orgdisplayidentifer)
                                                WHERE id=USRORGRRECORD.id;                               
                                END LOOP;

                                RAISE NOTICE 'UPDATING ROSTERS TO NEW CONTRACTING ORGANIZATION..';
                                FOR ROSTERRECORD IN
                                                select distinct r.id, org.displayidentifier from enrollment en
                                                join student st on st.id = en.studentid
                                                join enrollmentsrosters enrlr on enrlr.enrollmentid = en.id
                                                join roster r on r.id = enrlr.rosterid
                                                join organization org on r.attendanceschoolid = org.id
                                                where st.assessmentprogramid is null 
                                                and r.attendanceschoolid in (select id from organization_children(currentStateOrgId) union select currentStateOrgId)
                                LOOP     
                                                RAISE NOTICE 'Updating Roster attendenceschool Id: %, %', ROSTERRECORD.id, ROSTERRECORD.displayidentifier;
                                                
                                                UPDATE roster
                                                SET attendanceschoolid=(select id from organization 
                                                                where displayidentifier=ROSTERRECORD.displayidentifier || '-cPass' || in_orgdisplayidentifer)
                                                WHERE id=ROSTERRECORD.id;
                                END LOOP;

                                RAISE NOTICE 'UPDATING ENROLLMENTS TO NEW CONTRACTING ORGANIZATION..';
                                FOR ENROLLMENTRECORD IN
                                                select distinct en.id, org.displayidentifier from enrollment en
                                                join student st on st.id = en.studentid 
                                                join organization org on en.attendanceschoolid = org.id
                                                where st.assessmentprogramid is null 
                                                and en.attendanceschoolid in (select id from organization_children(currentStateOrgId) union select currentStateOrgId)
                                LOOP     
                                                RAISE NOTICE 'Updating Enrollment attendenceschool Id: %, %', ENROLLMENTRECORD.id, ENROLLMENTRECORD.displayidentifier;

                                                UPDATE enrollment
                                                SET attendanceschoolid=(select id from organization 
                                                                where displayidentifier=ENROLLMENTRECORD.displayidentifier || '-cPass' || in_orgdisplayidentifer)
                                                WHERE id=ENROLLMENTRECORD.id;
                                END LOOP;

                                RAISE NOTICE 'UPDATING NEW ORGANIZATIONS name and displayidentifier to remove -cPass at the end..';
                                -- UPDATE org name AND org display identifier to truncate -cPass             
                                UPDATE organization SET organizationname=trim(trailing ('%-cPass' || in_orgdisplayidentifer) from organizationname), 
                                                displayidentifier=trim(trailing ('%-cPass' || in_orgdisplayidentifer) from displayidentifier)
                                WHERE id IN (select id from organization where displayidentifier ilike ('%-cPass' || in_orgdisplayidentifer) and contractingorganization is not true);

                                UPDATE organization SET organizationname=trim(trailing in_orgdisplayidentifer from organizationname), 
                                                displayidentifier=trim(trailing in_orgdisplayidentifer from displayidentifier)
                                WHERE id=newStateOrgId;
                                
                                RAISE NOTICE 'FINISHED converting cPass organization with display identifer % to its OWN NEW ORGANIZATION..', in_orgdisplayidentifer;           
                                
                ELSE
                                RAISE NOTICE 'Not Found CPASS assessment program -- Exiting the process';
                END IF;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

--SQL Script
DROP FUNCTION IF EXISTS remove_student_itiplan(in_studentid bigint, in_essentialelement text);

CREATE OR REPLACE FUNCTION remove_student_itiplan(in_studentid bigint, in_essentialelement text)
  RETURNS void AS
$BODY$ 

DECLARE
                                ITIHISTORYRECORDS RECORD;
                                cetesysadminid BIGINT;
                                numberOfITIPlans INT;
BEGIN
                RAISE NOTICE 'STARTING finding itiplans of student..% with essentialelement...%', in_studentid, in_essentialelement;
                SELECT INTO cetesysadminid (SELECT id FROM aartuser WHERE username = 'cetesysadmin');
                SELECT INTO numberOfITIPlans (
                                                select count(distinct itih.id) from ititestsessionhistory itih 
                                                join testsession ts on itih.testsessionid = ts.id
                                                join studentstests stutst on stutst.testsessionid = ts.id
                                                join category c on stutst.status = c.id
                                                where itih.studentid=in_studentid
                                                and itih.essentialelementid in (select id from contentframeworkdetail where contentcode=in_essentialelement and activeflag is true)
                                                and c.categorycode != 'Complete');
                
                RAISE NOTICE 'VERIFYING IF ITI plan(s) exist for student, essential element combinationin EP.. ';
                IF(numberOfITIPlans > 0) THEN
                                RAISE NOTICE 'VALID ITI Plans found-- Continuing';
                ELSE
                                RAISE NOTICE 'VALID ITI Plans NOT found-- Exiting the process..';
                                RETURN;
                END IF;

                RAISE NOTICE 'PROCEDDING TO DELETE THE ITI PLANS.. ';

                                FOR ITIHISTORYRECORDS IN
                                                select distinct itih.id as ititestsessionhistoryid, itih.testsessionid 
                                                from ititestsessionhistory itih 
                                                join testsession ts on itih.testsessionid = ts.id
                                                join studentstests stutst on stutst.testsessionid = ts.id
                                                join category c on stutst.status = c.id
                                                where itih.studentid=in_studentid
                                                and itih.essentialelementid in (select id from contentframeworkdetail where contentcode=in_essentialelement and activeflag is true)
                                                and c.categorycode != 'Complete'
                                LOOP     
                                                RAISE NOTICE 'deleteing itiplan with ititestsessionhistoryid:%, testsessionid:%', ITIHISTORYRECORDS.ititestsessionhistoryid, ITIHISTORYRECORDS.testsessionid;
                                                delete from studentsresponses where studentstestsid in (select id from studentstests where testsessionid=ITIHISTORYRECORDS.testsessionid);
                                                
                                                delete from studentstestsections where studentstestid in (select id from studentstests where testsessionid=ITIHISTORYRECORDS.testsessionid);
                                                  
                                                delete from studentstests  where id in (select id from studentstests where testsessionid=ITIHISTORYRECORDS.testsessionid);
                                                                                                
                                                delete from testsession where id = ITIHISTORYRECORDS.testsessionid;
                                                
                                                update ititestsessionhistory set activeflag = false, 
                                                                modifieddate = now(), modifieduser = cetesysadminid
                                                                where id=ITIHISTORYRECORDS.ititestsessionhistoryid;

                                END LOOP;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
DROP INDEX IF EXISTS idx_aartuser_activeflag;
CREATE INDEX idx_aartuser_activeflag ON aartuser USING btree (activeflag);

DROP INDEX IF EXISTS idx_usertest_activeflag;
CREATE INDEX idx_usertest_activeflag ON usertest USING btree (activeflag);

DROP INDEX IF EXISTS idx_usertest_testid;
CREATE INDEX idx_usertest_testid ON usertest USING btree (testid);

DROP INDEX IF EXISTS idx_usertest_usermoduleid;
CREATE INDEX idx_usertest_usermoduleid ON usertest USING btree (usermoduleid);

DROP INDEX IF EXISTS idx_usertestresponse_usertestsectionid;
CREATE INDEX idx_usertestresponse_usertestsectionid ON usertestresponse USING btree (usertestsectionid);

DROP INDEX IF EXISTS idx_usertestresponse_testsectionid;
CREATE INDEX idx_usertestresponse_testsectionid ON usertestresponse USING btree (testsectionid);

DROP INDEX IF EXISTS idx_usertestresponse_taskvariantid;
CREATE INDEX idx_usertestresponse_taskvariantid ON usertestresponse USING btree (taskvariantid);

DROP INDEX IF EXISTS idx_usertestresponse_foilid;
CREATE INDEX idx_usertestresponse_foilid ON usertestresponse USING btree (foilid);

DROP INDEX IF EXISTS idx_usertestsection_usertestid;
CREATE INDEX idx_usertestsection_usertestid ON usertestsection USING btree (usertestid);

DROP INDEX IF EXISTS idx_usertestsection_testsectionid;
CREATE INDEX idx_usertestsection_testsectionid ON usertestsection USING btree (testsectionid);  