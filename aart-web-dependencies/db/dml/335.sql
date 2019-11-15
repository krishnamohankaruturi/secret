--335.sql

-- Fix FCS Duplicate rows in surveypagestatus table
DO 
$BODY$
DECLARE
     SRECS RECORD;
BEGIN
RAISE NOTICE 'Get surveyids duplicate';
FOR SRECS IN (select sps.surveyid,sps.globalpagenum from surveypagestatus sps group by sps.surveyid,sps.globalpagenum having count(*) > 1) 
LOOP
	RAISE NOTICE 'Survey Id = %, page Num = %', SRECS.surveyid, SRECS.globalpagenum ;
	delete from surveypagestatus where surveyid = SRECS.surveyid and globalpagenum = SRECS.globalpagenum and id <> 
	(select id from surveypagestatus where surveyid = SRECS.surveyid and globalpagenum = SRECS.globalpagenum order by iscompleted desc limit 1);
END LOOP;
END;

$BODY$;