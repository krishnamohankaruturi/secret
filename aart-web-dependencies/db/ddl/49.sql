drop function if exists get_adaptive_subsections(_testid bigint, _testpartid bigint, _testsectioncontainerid bigint, _thetavalue integer, _selectednumberofsubsections integer, _administratednumberofsubsections integer, _studentstestid bigint);
CREATE OR REPLACE FUNCTION get_adaptive_subsections(_testid bigint, _testpartid bigint, _testsectioncontainerid bigint, _thetavalue integer, _selectednumberofsubsections integer, _administratednumberofsubsections integer, _studentstestid bigint)
  RETURNS refcursor AS
$BODY$
DECLARE
    ref refcursor; 
    adaptiveTestRecord record;   
BEGIN
  
  --RAISE INFO 'testid %, testpartid %, _testsectioncontainerid %, _thetavalue %, _selectednumberofsubsections %, _administratednumberofsubsections %,  _studentstestid %',_testid, _testpartid, _testsectioncontainerid, _thetavalue, _selectednumberofsubsections, _administratednumberofsubsections,  _studentstestid;  
                
  OPEN ref FOR     
             select * from (SELECT tsc.id as testsectioncontainerid, 
             		   ts.id as sectionid,
	    	           tv.id as taskvariantid,
	    	           t.id as testid,
	    	           tp.id as testpartid,
	    	           tp.partnumber as testparnumber,
	    	           tp.selectednumberofsubsections as selectednumberofsubsections,
	    	           tp.administratednumberofsubsections as administratednumberofsubsections,	    	           
	    	           tsc.sectionnumber as testsectioncontainernumber,
	    	           tc.constructnumber as testconstructnumber,
	    	           tc.thetanodevalue as testconstructtheta,
	    	           tscc.itemdiscriminationparametername,
	    	           tscc.itemdiscriminationparameterindex,
	    	           tsctn.index as testsectioncontainerthetanodeindex,
	    	           tsctn.value1 as testsectioncontainerthetanodevalue,
	    	           ts.subsectionnumber as subsectionnumber,
	    	           ts.testsectionname as testsectionname,
	    	           tstn.index as testsectionthetanodeindex,
	    	           tstn.value1 as testsectionthetanodevalue,
	    	           tss.selectionstatisticvalue as testsectionselectionstatisticvalue
	    	    FROM test t
	    	    JOIN testpart tp ON tp.testid=t.id
	    	    JOIN testsectioncontainer tsc ON tsc.id=tp.testsectioncontainerid
	    	    JOIN testconstruct tc ON tc.testid=t.id
	    	    JOIN testsectioncontainerconstruct tscc ON tscc.testconstructid = tc.id
	    	    JOIN testsectioncontainerthetanode tsctn ON tsctn.testsectioncontainerid = tsc.id
	    	    JOIN testsection ts ON ts.testsectioncontainerid = tsc.id
	    	    JOIN testselectionstatistic tss ON tss.testsectioncontainerthetanodeid= tsctn.id AND tss.testsectionid = ts.id
	    	    JOIN testsectioncontainerthetanode tstn ON tstn.id= tss.testsectioncontainerthetanodeid
	    	    JOIN testsectionstaskvariants tstv ON tstv.testsectionid = ts.id
	    	    JOIN taskvariant tv ON tv.id = tstv.taskvariantid 
	    WHERE t.id = _testid 
	      AND tp.id = _testpartid
	      AND tsc.id = _testsectioncontainerid
	      AND CASE 
			WHEN _thetavalue > 0 THEN
				tstn.value1 = _thetavalue
			ELSE
				tstn.value1 = tc.thetanodevalue
		   END 
 	      AND tv.id NOT in (select itv.id from studentsadaptivetestsections sat JOIN testsectionstaskvariants itstv ON itstv.testsectionid = sat.testsectionid
	    	    JOIN taskvariant itv ON itv.id = itstv.taskvariantid where sat.studentstestid=_studentstestid) order by testsectionselectionstatisticvalue desc  limit _selectednumberofsubsections) availablesubsections order by random() limit _administratednumberofsubsections;
     RETURN ref; 
 
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;

ALTER FUNCTION get_adaptive_subsections(bigint, bigint, bigint, integer, integer, integer, bigint) OWNER TO aart;