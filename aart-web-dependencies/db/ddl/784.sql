-- Author : Rohit Yadav
-- Function to get daily access codes based on studentstestid

DROP FUNCTION IF EXISTS public.getdailyaccesscode(bigint);

CREATE OR REPLACE FUNCTION getdailyaccesscode(IN _studentstestid BIGINT)
    RETURNS TABLE(_dacid BIGINT, _accesscode character varying(30), _dacstatus BOOLEAN) AS 
$BODY$

DECLARE
    default_timezone TEXT;
BEGIN
    default_timezone := 'US/Central';
-- In compass we don't have organizationtreedetail table, so check if organizationtreedetail table exists.
    IF EXISTS (
          SELECT 1
          FROM pg_catalog.pg_class c
          JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
          WHERE n.nspname = ANY(current_schemas(FALSE))
          AND n.nspname NOT LIKE 'pg_%'  -- exclude system schemas!
          AND c.relname = 'organizationtreedetail'
          AND c.relkind = 'r'
    ) THEN
        RETURN QUERY
         SELECT
          dac.id, dac.accesscode,
          (
              CURRENT_TIME AT TIME ZONE default_timezone BETWEEN 
              (otwsr.dacstarttime AT TIME ZONE default_timezone) AND (otwsr.dacendtime AT TIME ZONE default_timezone)
          ) AS dacstatus
      FROM studentstests st
      JOIN testsession ts on ts.id = st.testsessionid
      JOIN testcollection tc on ts.testcollectionid = tc.id
      JOIN operationaltestwindowsessionrule otwsr on otwsr.operationaltestwindowid = ts.operationaltestwindowid
      JOIN organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid
      JOIN organization school on school.id = ts.attendanceschoolid
      JOIN organization state on otd.stateid = state.id
      LEFT JOIN category schooltz on schooltz.id = school.timezoneid
      LEFT JOIN category statetz on statetz.id = state.timezoneid
      LEFT JOIN dailyaccesscode dac on dac.contentareaid = tc.contentareaid AND dac.stageid = ts.stageid
      AND (
          dac.gradebandid IN (
              SELECT id
              FROM gradeband
              WHERE abbreviatedname = (
                  SELECT abbreviatedname
                  FROM gradeband
                  WHERE id = ts.gradebandid
              )
          )
          OR dac.gradecourseid IN (
              SELECT id
              FROM gradecourse
              WHERE abbreviatedname = (SELECT abbreviatedname FROM gradecourse WHERE id = ts.gradecourseid)
          )
      )
      AND dac.operationaltestwindowid = otwsr.operationaltestwindowid
      AND dac.effectivedate = date_trunc('day', CURRENT_TIMESTAMP at time zone COALESCE(schooltz.categorycode, statetz.categorycode, default_timezone))
      WHERE st.id = _studentstestid
      AND otwsr.sessionruleid IN (SELECT id FROM category WHERE categorycode = 'DAILY_ACCESS_CODES' AND activeflag is true);
    ELSE
      -- Retun zero records because compass does not use dac codes
        RETURN QUERY
          SELECT NULL::BIGINT as _dacid,''::character varying(30) as _accesscode,false as _dacstatus
          FROM category
          WHERE 1 = 2; 
          -- SELECT NULL::BIGINT as _dacid,''::character varying(30) as _accesscode,false as _dacstatus;
    END IF;
END;
$BODY$
LANGUAGE PLPGSQL VOLATILE
COST 100;


                                                                                        
