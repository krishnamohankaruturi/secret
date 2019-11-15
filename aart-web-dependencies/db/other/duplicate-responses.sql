-- Function: getduplicateresponses(text, text)

-- DROP FUNCTION getduplicateresponses();

CREATE OR REPLACE FUNCTION getduplicateresponses()
  RETURNS TABLE(studenttestsectionid bigint, duplicatetaskid text) AS
$BODY$
  DECLARE
    in_studenttestid bigint;

  BEGIN

  FOR in_studenttestid IN (select id from studentstests where testsessionid in (
				select id from testsession where testcollectionid in
				( select testcollectionid from assessmentstestcollections where assessmentid in
				( select id from assessment where testingprogramid in
				( select id from testingprogram where programname='Summative' and assessmentprogramid = ( select id from assessmentprogram where abbreviatedname='CPASS'))))

			))
  LOOP
	  RETURN QUERY
	    SELECT studentstestsectionsid, array_to_string(array_agg(taskvariantid), ',') as duplicateresponsetasks
		FROM studentsresponses
		WHERE response IN (
			SELECT response
			FROM studentsresponses
			where studentstestsid = in_studenttestid
			GROUP BY response
			HAVING COUNT(response) > 1
		)
	    and studentstestsid = in_studenttestid
	    group by studentstestsectionsid, response;

  END LOOP;



END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

select * from getduplicateresponses();
