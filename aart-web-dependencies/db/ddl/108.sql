DROP FUNCTION if exists get_adaptive_subsections(bigint, bigint, bigint, integer, integer, integer, bigint);

CREATE OR REPLACE FUNCTION get_adaptive_subsections(_testid bigint, _testpartid bigint, _testsectioncontainerid bigint, _thetaindex integer, _selectednumberofsubsections integer, _administratednumberofsubsections integer, _studentstestid bigint)
  RETURNS refcursor AS
$BODY$
DECLARE
    ref refcursor;  
BEGIN
  
  --RAISE INFO 'testid %, testpartid %, _testsectioncontainerid %, _thetaindex %, _selectednumberofsubsections %, _administratednumberofsubsections %,  _studentstestid %',_testid, _testpartid, _testsectioncontainerid, _thetaindex, _selectednumberofsubsections, _administratednumberofsubsections,  _studentstestid;  
                
  OPEN ref FOR SELECT *
		FROM
		  (SELECT availablesubsections.*
		   FROM
		     (SELECT tsc.id AS testsectioncontainerid,
			     ts.id AS sectionid,
			     tss.testsectioncontainerthetanodeid,
			     tv.id AS taskvariantid
		      FROM test t
		      JOIN testpart tp ON tp.testid=t.id
		      JOIN testsectioncontainer tsc ON tsc.id=tp.testsectioncontainerid
		      JOIN testsection ts ON ts.testsectioncontainerid = tsc.id
		      JOIN testselectionstatistic tss ON tss.testsectionid = ts.id
		      JOIN testsectioncontainerthetanode tstn ON tstn.id= tss.testsectioncontainerthetanodeid
		      JOIN testsectionstaskvariants tstv ON tstv.testsectionid = ts.id
		      JOIN taskvariant tv ON tv.id = tstv.taskvariantid
		      WHERE t.id = _testid
			AND tp.id = _testpartid
			AND tsc.id = _testsectioncontainerid
			AND tstn.index = _thetaindex
			AND tv.id NOT IN
			  (SELECT itv.id
			   FROM studentsadaptivetestsections sat
			   JOIN testsectionstaskvariants itstv ON itstv.testsectionid = sat.testsectionid
			   JOIN taskvariant itv ON itv.id = itstv.taskvariantid
			   WHERE sat.studentstestid=_studentstestid)
		      GROUP BY tsc.id ,
			       ts.id,
			       tv.id,
			       tss.testsectioncontainerthetanodeid) availablesubsections
		   JOIN testselectionstatistic tss ON tss.testsectionid = availablesubsections.sectionid
		   AND tss.testsectioncontainerthetanodeid = availablesubsections.testsectioncontainerthetanodeid
		   ORDER BY selectionstatisticvalue DESC LIMIT _selectednumberofsubsections) selectedsubsections
		ORDER BY random() LIMIT _administratednumberofsubsections;

     RETURN ref; 
 
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;