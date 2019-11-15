-- ddl/.sql

CREATE OR REPLACE FUNCTION updateandgetqueuedmodulereport(bigint, bigint)
  RETURNS SETOF modulereport AS
$BODY$ 
DECLARE 
	p_queuestatusid ALIAS FOR $1;
	p_inprogressstatusid ALIAS FOR $2;
	report_id bigint;
BEGIN

	select id into report_id from modulereport where statusid = p_queuestatusid and activeflag is true 
		and reporttypeid not in (select distinct reporttypeid from modulereport where activeflag is true and statusid = p_inprogressstatusid)
		and (now() at TIME ZONE 'CDT' > starttime at TIME ZONE 'CDT')
	order by modifieddate asc limit 1;  

	IF report_id IS NOT NULL THEN
		update modulereport set statusid = p_inprogressstatusid, modifieddate = now(), starttime = now() where id = report_id;
	END IF;
	RETURN QUERY SELECT * FROM modulereport where id = report_id;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;

DO
$BODY$
BEGIN
	IF EXISTS (SELECT column_name 
					FROM information_schema.columns 
					WHERE table_name='modulereport' and column_name='starttime') THEN
		raise NOTICE 'starttime found in modulereport';
	else
		ALTER TABLE modulereport ADD COLUMN starttime timestamp with time zone;
END IF;
END
$BODY$;


DROP INDEX IF EXISTS idx_aartuser_email;
create index idx_aartuser_email 
	on aartuser (lower(email) varchar_pattern_ops);

DROP INDEX  IF EXISTS idx_aartuser_username;
create index idx_aartuser_username 
	on aartuser (lower(username) varchar_pattern_ops);
	
DROP INDEX IF EXISTS idx_modulereport_starttime;
CREATE INDEX idx_modulereport_starttime
  ON modulereport USING btree (starttime);
  
DROP INDEX IF EXISTS idx_groupauthoritylockdownperiod_fromdate;
CREATE INDEX idx_groupauthoritylockdownperiod_fromdate 
	ON groupauthoritylockdownperiod USING btree (fromdate);

DROP INDEX IF EXISTS idx_groupauthoritylockdownperiod_todate;
CREATE INDEX idx_groupauthoritylockdownperiod_todate 
	ON groupauthoritylockdownperiod USING btree (todate);

DROP INDEX IF EXISTS idx_groupauthoritylockdownperiod_activeflag;
CREATE INDEX idx_groupauthoritylockdownperiod_activeflag 
	ON groupauthoritylockdownperiod USING btree (activeflag);

DROP INDEX IF EXISTS idx_studentsresponseparameters_studentstestsid;
CREATE INDEX idx_studentsresponseparameters_studentstestsid
  ON studentsresponseparameters USING btree (studentstestsid);  

DROP INDEX IF EXISTS idx_studentsresponseparameters_studentstestsectionsid;
CREATE INDEX idx_studentsresponseparameters_studentstestsectionsid
  ON studentsresponseparameters USING btree (studentstestsectionsid);  

DROP INDEX IF EXISTS idx_studentsresponseparameters_taskvariantid;
CREATE INDEX idx_studentsresponseparameters_taskvariantid
  ON studentsresponseparameters USING btree (taskvariantid);
  
DROP INDEX IF EXISTS idx_testpanelscoring_activeflag;
CREATE INDEX idx_testpanelscoring_activeflag 
	ON testpanelscoring USING btree (activeflag);

DROP INDEX IF EXISTS idx_testpanelscoring_externaltaskvariantid;
CREATE INDEX idx_testpanelscoring_externaltaskvariantid 
	ON testpanelscoring USING btree (externaltaskvariantid);

DROP INDEX IF EXISTS idx_taskvariant_externalid;
CREATE INDEX idx_taskvariant_externalid 
	ON taskvariant USING btree (externalid);
	
DROP INDEX IF EXISTS idx_studentstestshistory_action;
CREATE INDEX idx_studentstestshistory_action 
	ON studentstestshistory USING btree (action);
	
