--ddl/707.sql

-- DBA team updated this function, only putting it here because it's already executed manually in production, so it doesn't need a new DDL.

CREATE OR REPLACE FUNCTION updatestudentgrade(
    state_student_identifier character varying,
    state_displayidentifier character varying,
    att_sch_displayidentifier character varying,
    ayp_sch_displayidentifier character varying,
    schoolyear bigint,
    old_grade character varying,
    new_grade character varying)
  RETURNS text AS
$BODY$
             DECLARE
             state_Id BIGINT;
             ceteSysAdminUserId BIGINT;
             att_sch_id BIGINT;
             ayp_sch_id BIGINT;
             old_grade_Id BIGINT;
             new_grade_Id BIGINT;
             enrollmentRecord RECORD;
             studentTestsRecords RECORD;
             exitStuTestSecsStatus BIGINT;
             exitStuTestsStatus BIGINT;
             error_msg text ;

           BEGIN
                error_msg :='';
   IF (select count(*) from (select o.schooldisplayidentifier tree_schooldisplayidentifier,o.stateDisplayidentifier tree_stateDisplayidentifier from organizationtreedetail o) org 
            where lower(trim(org.tree_stateDisplayidentifier))=lower(trim(state_displayidentifier))
            and (lower(trim(org.tree_schooldisplayidentifier))=lower(trim(att_sch_displayidentifier)))  
       group by org.tree_schooldisplayidentifier,org.tree_stateDisplayidentifier order by 1 desc limit 1 ) >1
   THEN 
       error_msg := '<error> Duplicate display Identifier on school more info>> '||'Student:'||COALESCE(state_student_identifier,'NULL')||';State:'||COALESCE(state_displayidentifier,'NULL')||';AND Attendance school:'||COALESCE(att_sch_displayidentifier,'NULL');
       RAISE NOTICE '%',error_msg;
   ELSE
             SELECT INTO state_Id (SELECT id FROM organization WHERE lower(trim(displayidentifier)) = lower(trim(state_displayidentifier)) AND activeflag is true );
             SELECT INTO att_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(trim(schooldisplayidentifier)) = lower(trim(att_sch_displayidentifier)));
             SELECT INTO ayp_sch_id (SELECT schoolid FROM organizationtreedetail WHERE stateid = state_Id AND lower(trim(schooldisplayidentifier)) = lower(trim(ayp_sch_displayidentifier)));
             SELECT INTO ceteSysAdminUserId (SELECT id FROM aartuser WHERE username='cetesysadmin');
             SELECT INTO old_grade_Id (SELECT id FROM gradecourse gc WHERE contentareaid IS NULL AND assessmentprogramgradesid IS NOT NULL
          					AND trim(abbreviatedname) = trim(old_grade) AND activeflag is true );
             SELECT INTO new_grade_Id (SELECT id FROM gradecourse gc WHERE contentareaid IS NULL AND assessmentprogramgradesid IS NOT NULL
          					AND trim(abbreviatedname) = trim(new_grade) AND activeflag is true );
             SELECT INTO exitStuTestSecsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TESTSECTION_STATUS' AND categorycode = 'exitclearunenrolled');
             SELECT INTO exitStuTestsStatus (SELECT c.id FROM category c INNER JOIN categorytype ct ON c.categorytypeid=ct.id WHERE ct.typecode='STUDENT_TEST_STATUS' AND categorycode = 'exitclearunenrolled');

             IF((SELECT count(stu.*) FROM student stu JOIN enrollment en ON en.studentid = stu.id
                        WHERE trim(stu.statestudentidentifier) = trim(state_student_identifier) AND stu.stateid = state_Id  and en.activeflag is true and stu.activeflag is true AND en.attendanceschoolid = att_sch_id
                           AND en.currentschoolyear = schoolyear AND en.currentgradelevel = old_grade_Id) <= 0) THEN
          	RAISE NOTICE 'Student % not found in state %, attendace school %, grade %, and current school %', state_student_identifier,state_displayidentifier, att_sch_displayidentifier,  old_grade, schoolyear;
          	error_msg := '<error>' || 'Student:'||coalesce(state_student_identifier,'NULL')|| ';not found in state:' ||coalesce(state_displayidentifier,'NULL')||';attendace school:'||coalesce(att_sch_displayidentifier,'NULL')||';grade:'||coalesce(old_grade,'NULL')||';and current school:'|| cast(coalesce(schoolyear,0)as text);
                  RAISE NOTICE '%',error_msg;
             ELSE

                FOR enrollmentRecord IN (SELECT stu.id as studentid, stu.stateid, en.* FROM student stu JOIN enrollment en ON en.studentid = stu.id
                        WHERE trim(stu.statestudentidentifier) = trim(state_student_identifier) AND stu.stateid = state_Id  and en.activeflag is true and stu.activeflag is true AND en.attendanceschoolid = att_sch_id 
                           AND en.currentschoolyear = schoolyear AND en.currentgradelevel = old_grade_Id) LOOP

          	UPDATE enrollment SET currentgradelevel = new_grade_Id, modifieddate = now(), modifieduser = ceteSysAdminUserId WHERE id = enRollmentRecord.id;
/*
          	INSERT INTO domainaudithistory (source,objecttype,objectid, createduserid, createddate, action, objectaftervalues) VALUES ('LOCK_DOWN_SCRIPT', 'ENROLLMENT', enrollmentRecord.id, ceteSysAdminUserId, now(),
          		'GRADE_CHANGE', ('{"studentId":' || enrollmentRecord.studentid || ',"stateId":' || state_Id
          				|| ',"stateStudentIdentifier":"' || state_student_identifier || '","aypSchool":' || enrollmentRecord.aypschoolid
          				|| ',"attendanceSchoolId":' || enrollmentRecord.attendanceschoolid
          				|| ',"newGrade":' || new_grade_Id || ',"oldGrade":' || old_grade_Id || '}')::json);
*/
          	RAISE NOTICE 'Student %  grade is changed to %', state_student_identifier, new_grade;
          	error_msg := '<success>' || coalesce(state_student_identifier,'NULL') || ':Student grade is changed' ;

          	FOR studentTestsRecords IN (SELECT  st.id, st.studentid, st.testid, st.testcollectionid, st.testsessionid, st.status,st.enrollmentid
                          FROM studentstests st JOIN testsession ts on st.testsessionid = ts.id and st.activeflag=true  and ts.activeflag=true JOIN operationaltestwindow otw on otw.id = ts.operationaltestwindowid
                           WHERE st.activeflag=true AND st.enrollmentid = enrollmentRecord.id AND (otw.effectivedate <= now() AND now() <= otw.expirydate))
          	LOOP

          	    PERFORM inActivateStuTestsTrackerITITestsessions(studentTestsId := studentTestsRecords.id , inActiveStuTestSecStatusId := exitStuTestSecsStatus,
          		      inActiveStuTestStatusId := exitStuTestsStatus, testsession_Id := studentTestsRecords.testsessionid, student_Id := studentTestsRecords.studentid);

                  END LOOP;
          	    UPDATE ititestsessionhistory SET activeflag=false,modifieddate=now(),modifieduser=ceteSysAdminUserId,status=exitStuTestsStatus
          		       WHERE studentenrlrosterid IN (SELECT id FROM enrollmentsrosters WHERE enrollmentid = enrollmentRecord.id)
                                 AND status = (SELECT cat.id FROM category cat, categorytype ct WHERE ct.id = cat.categorytypeid AND cat.categorycode='pending' AND ct.typecode = 'STUDENT_TEST_STATUS')
                                 AND activeflag IS true;
               END LOOP;
             END IF;
             END IF;
              RETURN error_msg;
          END;
          $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION updatestudentgrade(character varying, character varying, character varying, character varying, bigint, character varying, character varying)
  OWNER TO aart;
GRANT EXECUTE ON FUNCTION updatestudentgrade(character varying, character varying, character varying, character varying, bigint, character varying, character varying) TO aart;
GRANT EXECUTE ON FUNCTION updatestudentgrade(character varying, character varying, character varying, character varying, bigint, character varying, character varying) TO public;
GRANT EXECUTE ON FUNCTION updatestudentgrade(character varying, character varying, character varying, character varying, bigint, character varying, character varying) TO ep_web_user;
GRANT EXECUTE ON FUNCTION updatestudentgrade(character varying, character varying, character varying, character varying, bigint, character varying, character varying) TO ep_batch_user;
GRANT EXECUTE ON FUNCTION updatestudentgrade(character varying, character varying, character varying, character varying, bigint, character varying, character varying) TO ep_tde_kite_user;
GRANT EXECUTE ON FUNCTION updatestudentgrade(character varying, character varying, character varying, character varying, bigint, character varying, character varying) TO ep_tde_rest_user;
GRANT EXECUTE ON FUNCTION updatestudentgrade(character varying, character varying, character varying, character varying, bigint, character varying, character varying) TO ep_metadata_user;