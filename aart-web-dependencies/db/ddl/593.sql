-- US19089: Metametrics Lexile Return File to KSDE
alter table organizationmanagementaudit add column currentschoolyear bigint;

DROP FUNCTION IF EXISTS getMetametricsFile(BIGINT, BIGINT, BIGINT);
CREATE OR REPLACE FUNCTION getMetametricsFile(kapAdaptivewindowId BIGINT, kapPerfWindowId BIGINT, reportingYear BIGINT)
   RETURNS void AS
$BODY$
DECLARE    

    studentswith_morethan1_record RECORD;
    studentstestsid_needed BIGINT;
    students_tests_record RECORD;
    row_num BIGINT;
    students_responses_record RECORD;
BEGIN
    row_num := 0;    
    DROP TABLE IF EXISTS studentestsdetails;
    DROP TABLE IF EXISTS sccodestudents;
    DROP TABLE IF EXISTS metametricsstudentsreport;
    DROP TABLE IF EXISTS metametricsstudentsresponses;

    CREATE TABLE metametricsstudentsresponses(rownumber BIGINT, statestudentidentifier character varying(50), itemid BIGINT, stage CHARACTER VARYING(20), itemnumber INTEGER, response INTEGER, score numeric(6,3), 
	gradeabbrname CHARACTER VARYING(20), subject CHARACTER VARYING(20), stageabbrname CHARACTER VARYING(20));
    
    CREATE TABLE metametricsstudentsreport(statestudentidentifier character varying(50), gradeabbrname character varying(10), grade character varying(30), subject character varying(10), subjectid BIGINT, scalescore BIGINT, level BIGINT, rawscore numeric(6,3), studentid BIGINT);

    DELETE FROM metametricsstudentsreport;
    DELETE FROM metametricsstudentsresponses;
       
    CREATE TEMP TABLE studentestsdetails(statestudentidentifier character varying(50), studentstestsid BIGINT, studentid BIGINT, stagecode character varying(10), stagename character varying(20), 
		totalrawscore numeric(6,3), testcollectionid BIGINT, subject character varying(20), gradeabbrname character varying(20), gradename character varying(20));

    CREATE TEMP TABLE sccodestudents(studentid BIGINT);

    INSERT INTO sccodestudents(studentid)
	SELECT distinct st.studentid FROM studentstests st JOIN testsession ts ON ts.id = st.testsessionid
              JOIN studentspecialcircumstance spcir ON spcir.studenttestid = st.id
              WHERE ts.operationaltestwindowid in (kapAdaptivewindowId, kapPerfWindowId)
              AND spcir.status IN (SELECT id FROM category WHERE categorycode IN ('APPROVED', 'SAVED') AND categorytypeid = (SELECT id FROM categorytype WHERE typecode = 'SPECIAL CIRCUMSTANCE STATUS'));


    INSERT INTO metametricsstudentsreport(statestudentidentifier, studentid, gradeabbrname, grade, subject, scalescore, rawscore, level)
	SELECT DISTINCT stu.statestudentidentifier, stu.id as studentid, gc.abbreviatedname as grade, gc.name, ca.abbreviatedname as subject, str.scalescore, str.rawscore, ld.level  
	FROM studentreport str 
        JOIN student stu ON stu.id = str.studentid 
        JOIN gradecourse gc ON gc.id = str.gradeid 
        JOIN contentarea ca ON ca.id = str.contentareaid 
        JOIN leveldescription ld ON ld.id = str.levelid        
        WHERE str.schoolyear = reportingYear AND str.status IS true AND ca.abbreviatedname IN ('ELA', 'M');

    EXECUTE FORMAT ($x$COPY (
    SELECT statestudentidentifier as Student_ID, grade as Grade, rawscore as Raw_Score, scalescore as Scale_Score,level as Performance_Level
             FROM metametricsstudentsreport WHERE subject = 'M' and studentid not in (SELECT studentid FROM sccodestudents) 
             ORDER BY cast(NULLIF(regexp_replace(gradeabbrname, E'\\D', '', 'g'), '') AS integer), statestudentidentifier) TO '/srv/pg_data/postgres/KS_Metametrics_Math_Student_Performance_Report.csv' WITH DELIMITER ',' CSV HEADER$x$);

    EXECUTE FORMAT ($x$COPY (
    SELECT statestudentidentifier as Student_ID, grade as Grade, rawscore as Raw_Score, scalescore as Scale_Score,level as Performance_Level
             FROM metametricsstudentsreport WHERE subject = 'ELA' and studentid not in (SELECT studentid FROM sccodestudents)
             ORDER BY cast(NULLIF(regexp_replace(gradeabbrname, E'\\D', '', 'g'), '') AS integer), statestudentidentifier) TO '/srv/pg_data/postgres/KS_Metametrics_ELA_Student_Performance_Report.csv' WITH DELIMITER ',' CSV HEADER$x$);

    INSERT INTO studentestsdetails(statestudentidentifier, studentstestsid, studentid, stagecode, stagename, totalrawscore, testcollectionid, subject, gradeabbrname, gradename)
       SELECT stu.statestudentidentifier, st.id, stu.id, stage.code, stage.name, st.totalrawscore, st.testcollectionid, ca.abbreviatedname, gc.abbreviatedname, gc.name
          FROM studentstests st JOIN testsession ts ON ts.id = st.testsessionid
          JOIN student stu ON stu.id = st.studentid
          JOIN testcollection tc ON tc.id = st.testcollectionid
          JOIN gradecourse gc ON gc.id = tc.gradecourseid
          JOIN stage stage ON stage.id = ts.stageid
          JOIN contentarea ca ON ca.id = tc.contentareaid
          WHERE st.studentid IN (SELECT studentid FROM metametricsstudentsreport WHERE studentid not in (SELECT studentid FROM sccodestudents))
          AND st.activeflag IS true AND st.status IN (85, 86)
          AND ca.abbreviatedname IN ('ELA', 'M')
          AND ts.operationaltestwindowid IN (kapAdaptivewindowId, kapPerfWindowId) 
          AND ts.schoolyear = reportingYear AND ts.activeflag IS true;

    FOR studentswith_morethan1_record IN (SELECT studentid, subject, testcollectionid, count(studentstestsid) FROM studentestsdetails GROUP BY studentid, subject, testcollectionid HAVING count(studentstestsid) > 1) LOOP

        SELECT studentstestsid INTO studentstestsid_needed FROM studentestsdetails WHERE studentid = studentswith_morethan1_record.studentid
		AND testcollectionid = studentswith_morethan1_record.testcollectionid ORDER BY totalrawscore DESC LIMIT 1;

	DELETE FROM studentestsdetails WHERE studentid = studentswith_morethan1_record.studentid 
		AND testcollectionid = studentswith_morethan1_record.testcollectionid AND studentstestsid NOT IN (studentstestsid_needed);
	
    END LOOP;

    FOR students_tests_record IN (SELECT statestudentidentifier, studentstestsid, studentid, stagecode, stagename, totalrawscore, testcollectionid, subject, gradeabbrname, gradename 
			FROM studentestsdetails ORDER BY subject, cast(NULLIF(regexp_replace(gradeabbrname, E'\\D', '', 'g'), '') AS integer), statestudentidentifier, stagecode != 'Stg1', stagecode!='Stg2', stagecode!='Stg3', stagecode!='Stg4', stagecode!='Prfrm') LOOP
					
          
	FOR students_responses_record IN (SELECT row_number() OVER(PARTITION BY st.testid ORDER BY ts.sectionorder,ttv.taskvariantposition) itemnum ,
				CASE WHEN sr.score > 0 THEN 1 
				WHEN sr.score is NULL THEN NULL
				ELSE 0 END AS response, sr.score as score, tv.externalid AS  itemid
			FROM  studentstests st 
			INNER JOIN testsection ts on st.testid=ts.testid
			INNER JOIN testsectionstaskvariants ttv on ts.id=ttv.testsectionid
			INNER JOIN taskvariant tv on tv.id = ttv.taskvariantid			
			LEFT OUTER JOIN studentstestsections stsec ON stsec.studentstestid = st.id AND stsec.testsectionid = ts.id AND stsec.activeflag IS true
			LEFT OUTER JOIN studentsresponses sr on  sr.studentstestsid=st.id AND sr.studentstestsectionsid = stsec.id AND sr.taskvariantid=ttv.taskvariantid AND sr.activeflag IS true
			WHERE st.id = students_tests_record.studentstestsid ORDER BY itemnum) LOOP

		row_num := row_num + 1;

		INSERT INTO metametricsstudentsresponses (rownumber, statestudentidentifier, itemid, stage, itemnumber, response, score, gradeabbrname, subject, stageabbrname)
			     VALUES(row_num, students_tests_record.statestudentidentifier, students_responses_record.itemid, students_tests_record.stagename, 
				students_responses_record.itemnum, students_responses_record.response, students_responses_record.score,
				students_tests_record.gradeabbrname, students_tests_record.subject, students_tests_record.stagecode);

	END LOOP;

    END LOOP;

    DELETE FROM metametricsstudentsresponses WHERE stageabbrname = 'Prfrm' AND  subject = 'ELA';

    EXECUTE FORMAT ($x$COPY (
    SELECT statestudentidentifier AS Student_ID, itemid AS Item_ID, stage AS Stage, itemnumber AS Item_Number, response AS Response, score AS Score
             FROM metametricsstudentsresponses
             ORDER BY rownumber) TO '/srv/pg_data/postgres/KS_Metametrics_Student_Responses.csv' WITH DELIMITER ',' CSV HEADER$x$);
       
    DROP TABLE IF EXISTS sccodestudents;    
    DROP TABLE IF EXISTS studentestsdetails;
    DROP TABLE IF EXISTS metametricsstudentsreport;
    DROP TABLE IF EXISTS metametricsstudentsresponses;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;