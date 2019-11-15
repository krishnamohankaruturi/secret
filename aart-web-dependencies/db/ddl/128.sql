DROP FUNCTION get_adaptive_test(bigint, bigint);

CREATE OR REPLACE FUNCTION get_adaptive_test(IN _testid bigint, IN _studentstestid bigint)
  RETURNS integer AS
$BODY$
DECLARE
    subsectioncursor refcursor;
    studentadaptiveteststatusrec record; 
    adaptiveTestRecord record;    
    testcontainterthetatanoderec record;    
    testsectioncontainerid bigint;
    subsectionid bigint; 
    testsectioncontainerthetanodeid bigint;
    taskvariantid bigint;
    testpartnumber integer;
    thetavalue integer;
    thetaindex integer;
    testinprogress boolean; 
BEGIN
  testpartnumber := 0;
  thetavalue := 0;
  testinprogress := false; 
  FOR studentadaptiveteststatusrec IN select * from studentadaptivetestthetastatus where studentstestid=_studentstestid and testpartcomplete=false order by testpartnumber LIMIT 1
  LOOP   
    testpartnumber := studentadaptiveteststatusrec.testpartnumber;
    testinprogress := true; 
  END LOOP; 
  --RAISE INFO 'testpartnumber %     thetavalue %      testinprogress %', testpartnumber, thetavalue, testinprogress;   
  IF NOT testinprogress THEN     
	  FOR studentadaptiveteststatusrec IN select * from studentadaptivetestthetastatus where studentstestid=_studentstestid and testpartcomplete=true order by testpartnumber desc LIMIT 1
	      LOOP   
		testpartnumber := studentadaptiveteststatusrec.testpartnumber; 
	  END LOOP;
	  testpartnumber := testpartnumber + 1;  
	  FOR adaptiveTestRecord IN     
		     SELECT t.id as testid,
				   tp.id as testpartid,
				   tp.partnumber as testparnumber,
				   tp.selectednumberofsubsections as selectednumberofsubsections,
				   tp.administratednumberofsubsections as administratednumberofsubsections,
				   tsc.id as testsectioncontainerid,
				   tsc.sectionnumber as testsectioncontainernumber,
				   tc.constructnumber as testconstructnumber,
				   tc.thetanodevalue as testsectioncontainerthetanodevalue,
				   tscc.itemdiscriminationparametername,
				   tscc.itemdiscriminationparameterindex 
			    FROM test t
			    JOIN testpart tp ON tp.testid=t.id 
			    JOIN testsectioncontainer tsc ON tsc.id=tp.testsectioncontainerid
			    JOIN testconstruct tc ON tc.testid=t.id
			    JOIN testsectioncontainerconstruct tscc ON tscc.testconstructid = tc.id and tscc.testsectioncontainerid = tsc.id
		    WHERE t.id = _testid 
		      AND tp.partnumber = testpartnumber  
	     LOOP 
	     	      thetavalue := adaptiveTestRecord.testsectioncontainerthetanodevalue;
	      	      FOR testcontainterthetatanoderec IN select * from testsectioncontainerthetanode tsct where tsct.testsectioncontainerid = adaptiveTestRecord.testsectioncontainerid and tsct.value1 = thetavalue LIMIT 1
	              LOOP   
	     	  	thetaindex := testcontainterthetatanoderec.index; 
      	  	      END LOOP; 
      	  	      FOR studentadaptiveteststatusrec IN select * from studentadaptivetestthetastatus sat where sat.studentstestid=_studentstestid and sat.testpartcomplete=true and sat.testsectioncontainernumber = adaptiveTestRecord.testsectioncontainernumber order by testpartnumber desc LIMIT 1
	              LOOP   
	     	  	thetaindex := studentadaptiveteststatusrec.interimtheta; 
      	  	      END LOOP; 
		      --RAISE INFO 'testid %     testpartid %     testsectioncontainerid %      testcontainerthetaindex %       selectednumberofsubsections %      administratednumberofsubsections %', adaptiveTestRecord.testid, adaptiveTestRecord.testpartid, adaptiveTestRecord.testsectioncontainerid, thetaindex, adaptiveTestRecord.selectednumberofsubsections, adaptiveTestRecord.administratednumberofsubsections;  
		      subsectioncursor = get_adaptive_subsections(adaptiveTestRecord.testid , adaptiveTestRecord.testpartid, adaptiveTestRecord.testsectioncontainerid, thetaindex, adaptiveTestRecord.selectednumberofsubsections, adaptiveTestRecord.administratednumberofsubsections, _studentstestid);  
		      LOOP
			FETCH subsectioncursor INTO testsectioncontainerid, subsectionid, testsectioncontainerthetanodeid, taskvariantid;
			EXIT WHEN NOT FOUND;
			--RAISE INFO 'testsectioncontainerid %   subsectionid % testsectioncontainerthetanodeid %   taskvariantid %', testsectioncontainerid, subsectionid, testsectioncontainerthetanodeid, taskvariantid; 
			EXECUTE 'insert into studentsadaptivetestsections(studentstestid, testpartid, testsectioncontainerid, testsectionid, testsectioncontainerthetanodeid, taskvariantid)values(' || _studentstestid || ',' || adaptiveTestRecord.testpartid  || ',' || testsectioncontainerid  || ',' || subsectionid || ',' || testsectioncontainerthetanodeid || ',' || taskvariantid || ')';
		      END LOOP; 
		      CLOSE subsectioncursor; 
		      EXECUTE 'insert into studentadaptivetestthetastatus(studentstestid, testpartid, testpartnumber, testsectioncontainerid, testsectioncontainernumber, initialtheta, interimtheta, testpartcomplete)values(' || _studentstestid || ',' || adaptiveTestRecord.testpartid  || ',' || testpartnumber  || ',' || adaptiveTestRecord.testsectioncontainerid  || ',' || adaptiveTestRecord.testsectioncontainernumber || ',' || thetaindex ||',0 ,false)';		      
	     END LOOP; 
     END IF;

     RETURN testpartnumber;
 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
ALTER FUNCTION get_adaptive_test(bigint, bigint) OWNER TO aart;