-- US18150: EP: Prod - Request to remove DLM Science test sessions from students who are not taking the science test this year.
DO
$BODY$

DECLARE

    dlmassessmentprogram_id BIGINT;
    science_contentareaid BIGINT;
    enrollments_rosters_record RECORD;
    gradesneedto_removerecords RECORD;
    ks_state_id BIGINT;
    cetesysadminid BIGINT;

    totalStuentresponsesinactivated INTEGER;
    totalstudenttestsectionsinactivated INTEGER; 
    totalstudentstestsinactivated INTEGER;
    totalenrollmentrostersinactivated INTEGER;
    updatecount INTEGER;
    totaltestsessionsinactivated INTEGER;
    msg TEXT;

BEGIN

    SELECT id FROM aartuser WHERE username = 'cetesysadmin' INTO cetesysadminid;
    SELECT id FROM contentarea WHERE abbreviatedname = 'Sci' INTO science_contentareaid;
    SELECT id FROM assessmentprogram WHERE abbreviatedname = 'DLM' INTO dlmassessmentprogram_id;
    SELECT id FROM organization WHERE displayidentifier = 'KS' INTO ks_state_id;
    msg :='';

    FOR gradesneedto_removerecords IN (SELECT id, abbreviatedname FROM gradecourse WHERE abbreviatedname in ('3','4','6', '7','9','10','12') AND assessmentprogramgradesid IS not null) LOOP

        msg :='For Grade: ' || gradesneedto_removerecords.abbreviatedname || ' and contetentarea : Science, ';
        totalStuentresponsesinactivated := 0;
        totalstudenttestsectionsinactivated := 0;
        totalstudentstestsinactivated := 0;
        totalenrollmentrostersinactivated := 0;
        totaltestsessionsinactivated := 0;
                
        FOR enrollments_rosters_record IN (SELECT enrl.rosterid, enrl.enrollmentid, enrl.id as enrollmentsrosterid FROM student stu JOIN enrollment en ON en.studentid = stu.id
                JOIN enrollmentsrosters enrl ON enrl.enrollmentid = en.id JOIN roster r ON r.id = enrl.rosterid JOIN studentassessmentprogram stuasp ON stuasp.studentid = stu.id
                WHERE stu.stateid = ks_state_id AND en.currentgradelevel = gradesneedto_removerecords.id AND en.activeflag is true AND en.currentschoolyear = 2016
                AND r.statesubjectareaid = science_contentareaid AND stuasp.assessmentprogramid = dlmassessmentprogram_id) LOOP

          IF((SELECT count(st.*) FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id WHERE st.enrollmentid = enrollments_rosters_record.enrollmentid AND ts.rosterid = enrollments_rosters_record.rosterid) > 0) THEN

             WITH inactivestudentresponses AS (UPDATE studentsresponses SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
                WHERE studentstestsid IN (SELECT st.id FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id 
                     WHERE st.enrollmentid = enrollments_rosters_record.enrollmentid AND ts.rosterid = enrollments_rosters_record.rosterid AND st.activeflag IS true) RETURNING 1)
                SELECT count(*) FROM inactivestudentresponses INTO updatecount;

             totalStuentresponsesinactivated :=  totalStuentresponsesinactivated + updatecount;
            
             WITH inactivestudentstestsections AS (UPDATE studentstestsections SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
                           WHERE studentstestid IN (SELECT st.id FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id 
                              WHERE st.enrollmentid = enrollments_rosters_record.enrollmentid AND ts.rosterid = enrollments_rosters_record.rosterid AND st.activeflag IS true) RETURNING 1)
                           SELECT count(*) FROM inactivestudentstestsections INTO updatecount;

              totalstudenttestsectionsinactivated :=  totalstudenttestsectionsinactivated +  updatecount;


             WITH inactivestudentstests AS (UPDATE studentstests SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
                        WHERE id IN (SELECT st.id FROM testsession ts JOIN studentstests st ON st.testsessionid = ts.id 
                              WHERE st.enrollmentid = enrollments_rosters_record.enrollmentid AND ts.rosterid = enrollments_rosters_record.rosterid AND st.activeflag IS true) RETURNING 1)
                        SELECT count(*) FROM inactivestudentstests INTO updatecount;

             totalstudentstestsinactivated := totalstudentstestsinactivated + updatecount;

             WITH inactiveenrollmentsrosters AS (UPDATE enrollmentsrosters SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
                                       WHERE id = enrollments_rosters_record.enrollmentsrosterid RETURNING 1) SELECT count(*) FROM inactiveenrollmentsrosters INTO updatecount;                                       

             totalenrollmentrostersinactivated := totalenrollmentrostersinactivated + updatecount;

             IF((SELECT count(*) FROM studentstests WHERE testsessionid IN (SELECT id FROM testsession WHERE rosterid = enrollments_rosters_record.rosterid)) <= 1) THEN

                  WITh inactivetestsessions AS (UPDATE testsession SET activeflag = FALSE, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
                       WHERE rosterid =  enrollments_rosters_record.rosterid RETURNING 1) SELECT count(*) FROM inactivetestsessions INTo updatecount;

                  totaltestsessionsinactivated := totaltestsessionsinactivated + updatecount;

             END IF;

          END IF;
        END LOOP;

        msg := msg || ' Total studentsresponses inactivated = ' || totalStuentresponsesinactivated || ', Total studentstestsections inactivated = ' || totalstudenttestsectionsinactivated
               || ', Total studentstests inactivated = ' || totalstudentstestsinactivated || ', Total enrollmentsrosters inactivated = ' || totalenrollmentrostersinactivated
               || ', Total testsessions inactivated = ' || totaltestsessionsinactivated;

        RAISE NOTICE '%', msg;

    END LOOP;

END;
$BODY$;