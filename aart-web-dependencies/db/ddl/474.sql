-- 474.sql
DROP VIEW assessment_program_participation;

ALTER TABLE category ALTER COLUMN categoryname TYPE character varying(100);

CREATE OR REPLACE VIEW assessment_program_participation AS 
 SELECT ap.programname AS assessment_program_name, 
    tc.name AS test_collection_name, t.testname AS test_name, 
    gpo.organizationname AS state, po.organizationname AS district, 
    co.organizationname AS school, st.createddate::date AS day, 
    c.categoryname AS status, count(st.*) AS total
   FROM studentstests st
   JOIN test t ON st.testid = t.id
   JOIN category c ON st.status = c.id
   JOIN testsession ts ON st.testsessionid = ts.id
   JOIN testcollection tc ON st.testcollectionid = tc.id
   JOIN assessmentstestcollections atc ON tc.id = atc.testcollectionid
   JOIN assessment a ON atc.assessmentid = a.id
   JOIN testingprogram tp ON a.testingprogramid = tp.id
   JOIN assessmentprogram ap ON tp.assessmentprogramid = ap.id
   JOIN enrollmentsrosters er ON ts.rosterid = er.rosterid
   JOIN roster r ON er.rosterid = r.id
   JOIN enrollment e ON er.enrollmentid = e.id
   JOIN organization co ON e.attendanceschoolid = co.id
   JOIN organizationrelation por ON co.id = por.organizationid
   JOIN organization po ON por.parentorganizationid = po.id
   JOIN organizationrelation gpor ON po.id = gpor.organizationid
   JOIN organization gpo ON gpor.parentorganizationid = gpo.id
  WHERE st.studentid = e.studentid
  GROUP BY ap.programname, tc.name, gpo.organizationname, po.organizationname, co.organizationname, t.testname, st.createddate::date, c.categoryname
  ORDER BY ap.programname, tc.name, gpo.organizationname, po.organizationname, co.organizationname, t.testname, st.createddate::date, c.categoryname;
