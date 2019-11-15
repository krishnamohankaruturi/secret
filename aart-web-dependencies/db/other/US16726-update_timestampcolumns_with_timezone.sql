--US16726 : Fix timestamp columns in all EP tables to Timestamp with timezone
--Function to find all tables having timestamp without timezone column and modify it to with timezone column

CREATE OR REPLACE FUNCTION modifyTimestampColumns() RETURNS INTEGER AS 
$BODY$
DECLARE  
    tname             information_schema.tables.table_name%TYPE;
    tcolumn	      information_schema.columns.column_name%TYPE;
    columnCount	           integer = 0;
    tableCursor CURSOR FOR 
      SELECT table_name,column_name from information_schema.columns where table_name in(
	   SELECT table_name FROM information_schema.tables where table_type = 'BASE TABLE' AND table_schema = 'public' ORDER BY table_name)
           and data_type='timestamp without time zone' order by table_name; 
        

BEGIN
RAISE INFO '---------Enter---------------';   
OPEN tableCursor;   
	LOOP
		
		FETCH tableCursor INTO tname, tcolumn;  
		EXIT WHEN NOT FOUND ;  		
				
		IF tname <>' ' AND tname IS NOT NULL AND tname <> 'studentsresponses'
		 		 
		THEN 
			RAISE INFO '-------------Updating table %, column % -----------', tname, tcolumn;
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN ' || tcolumn || ' TYPE timestamp with time zone';
			EXECUTE 'ALTER TABLE ' || tname || ' ALTER COLUMN ' || tcolumn || ' SET DEFAULT (''now''::text)::timestamp without time zone';
			columnCount = columnCount + 1;		
			RAISE INFO '-------------Update completed for table %, column % -----------', tname, tcolumn;
		END IF;
		 
		
	END LOOP;
CLOSE tableCursor;  
RAISE INFO '---------Exit with %--------------- ', columnCount;   
RETURN columnCount;  
EXCEPTION 
            WHEN undefined_object THEN
            RAISE NOTICE 'caught exception %', columnCount;
            RETURN columnCount;
END
$BODY$
LANGUAGE plpgsql;

--drop view assessment_program_participation which has dependency column createddate of studentstests table
DROP VIEW assessment_program_participation;

--set timezone to CST
set timezone = 'US/Central';

--Execute the function to update column type
select * from modifyTimestampColumns();

--drop temp function 
drop FUNCTION modifyTimestampColumns();

--recreate deleted view
CREATE OR REPLACE VIEW assessment_program_participation AS 
 SELECT ap.programname AS assessment_program_name,
    tc.name AS test_collection_name,
    t.testname AS test_name,
    gpo.organizationname AS state,
    po.organizationname AS district,
    co.organizationname AS school,
    st.createddate::date AS day,
    c.categoryname AS status,
    count(st.*) AS total
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

ALTER TABLE assessment_program_participation
  OWNER TO postgres;