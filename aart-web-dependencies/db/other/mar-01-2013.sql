ALTER TABLE student add column hispanicethnicity boolean;

ALTER TABLE roster add column localcourseid character varying(50);

DROP VIEW node_report;

ALTER TABLE student ALTER COLUMN statestudentidentifier TYPE character varying(50);

CREATE OR REPLACE VIEW node_report AS 
 SELECT s1.id AS student_id, s1.statestudentidentifier AS state_student_identifier, 
 s1.legalfirstname AS legal_first_name, s1.legalmiddlename AS legal_middle_name,
 s1.legallastname AS legal_last_name, summary.node_key, 
 st1.id AS students_tests_id, t1.id AS test_id, t1.testname AS test_name,
 t1.status AS test_status_id, tc1.id AS test_collection_id,
 tc1.name AS test_collection_name, summary.no_of_responses, 
 summary.correct_response
   FROM student s1, studentstests st1, test t1, testcollectionstests tct1, 
   testcollection tc1, ( SELECT sr.studentid AS student_id, tvln.nodecode AS node_key,
   count(1) AS no_of_responses, tvf.correctresponse AS correct_response
           FROM studentsresponses sr, student s, taskvariantsfoils tvf, taskvariant tv,
           taskvariantlearningmapnode tvln, studentstests st, test t,
           testcollectionstests tct, testcollection tc
          WHERE sr.studentstestsid = st.id AND tvf.foilid = sr.foilid AND tv.id = tvf.taskvariantid
          AND tv.id = tvln.taskvariantid AND s.id = sr.studentid AND st.testid = t.id AND tct.testid = t.id AND tct.testcollectionid = tc.id
          GROUP BY sr.studentid, tvln.nodecode, tvf.correctresponse) summary
  WHERE st1.studentid = s1.id AND st1.testid = tct1.testid AND t1.id = st1.testid
  AND tct1.testid = t1.id AND tct1.testcollectionid = tc1.id AND summary.student_id = s1.id
  ORDER BY s1.id, summary.node_key, summary.correct_response;

  
ALTER TABLE roster add column educatorschooldisplayidentifier character varying(100);

  
UPDATE fieldspecification SET showerror = true WHERE fieldname = 'comprehensiveRace' and mappedname ='Comprehensive_Race';

UPDATE fieldspecification SET showerror = true WHERE fieldname = 'aypSchoolIdentifier' and mappedname ='AYP_QPA_Bldg_No';

UPDATE fieldspecification SET showerror = true WHERE fieldname = 'createDate' and mappedname ='Create_Date';

UPDATE fieldspecification SET showerror = true WHERE fieldname = 'recordCommonId' and mappedname ='Record_Common_ID';

-- Below 3 update queries are for increasing the column legth and its faster than Alter command.	
UPDATE pg_attribute SET atttypmod = 80+4 WHERE attrelid = 'aartuser'::regclass AND attname = 'surname';

UPDATE pg_attribute SET atttypmod = 80+4 WHERE attrelid = 'aartuser'::regclass AND attname = 'firstname';

UPDATE pg_attribute SET atttypmod = 80+4 WHERE attrelid = 'aartuser'::regclass AND attname = 'middlename';

UPDATE fieldspecification SET maximum = null WHERE fieldname = 'stateStudentIdentifier' and mappedname ='State_Student_Identifier';