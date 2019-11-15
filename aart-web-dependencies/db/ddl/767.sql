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
          WITH st_timezone AS (
            SELECT COALESCE(c.categorycode, default_timezone) AS usertimezone,st.id,st.testcollectionid,st.testsessionid
            FROM studentstests st
            JOIN testsession ts ON st.testsessionid = ts.id
            JOIN student s on st.studentid = s.id
            JOIN enrollment e on e.studentid = s.id AND st.enrollmentid = e.id
            JOIN organization o on e.attendanceschoolid = o.id
            JOIN organization org on org.id = s.stateid
            LEFT OUTER JOIN category c on o.timezoneid = c.id
            WHERE st.id = _studentstestid  
            AND st.activeflag is true 
            AND s.activeflag is true
            AND ts.schoolyear = extract(year from org.schoolenddate)  
          )
          SELECT dac.id, dac.accesscode,
          CASE WHEN (otwsr.dacstarttime AT TIME ZONE stz.usertimezone)::TIME WITHOUT TIME ZONE<=(CURRENT_TIME::TIME WITH TIME ZONE AT TIME ZONE stz.usertimezone)::TIME WITHOUT TIME ZONE 
          AND (otwsr.dacendtime AT TIME ZONE stz.usertimezone)::TIME WITHOUT TIME ZONE>=(CURRENT_TIME::TIME WITH TIME ZONE AT TIME ZONE stz.usertimezone)::TIME WITHOUT TIME ZONE THEN true ELSE false  
          END AS dacstatus
          FROM st_timezone stz
          INNER JOIN testcollection tc on tc.id = stz.testcollectionid 
          INNER JOIN testsession ts on ts.id = stz.testsessionid 
          INNER JOIN operationaltestwindowsessionrule otwsr on otwsr.operationaltestwindowid = ts.operationaltestwindowid 
          INNER JOIN organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid
          LEFT OUTER JOIN dailyaccesscode dac on dac.contentareaid = tc.contentareaid AND dac.stageid = ts.stageid 
          AND (dac.gradebandid IN (SELECT id FROM gradeband WHERE abbreviatedname=(SELECT abbreviatedname FROM gradeband WHERE id = ts.gradebandid)) OR
              dac.gradecourseid IN (SELECT id FROM gradecourse WHERE abbreviatedname=(SELECT abbreviatedname FROM gradecourse WHERE id = ts.gradecourseid)) )
          AND dac.operationaltestwindowid = otwsr.operationaltestwindowid 
          AND dac.effectivedate = date_trunc('day', CURRENT_TIMESTAMP at time zone stz.usertimezone) 
          WHERE otwsr.sessionruleid IN (SELECT id FROM category WHERE categorycode='DAILY_ACCESS_CODES' AND activeflag is true);
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
