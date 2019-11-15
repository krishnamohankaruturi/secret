DO
$BODY$
DECLARE
	strecord RECORD;
	correctRosterid bigint;
BEGIN
	FOR strecord IN (select st.id,st.studentid, st.enrollmentid, r.teacherid, ts.id as testsessionid, r.id as rosterid, st.status, r.coursesectionname, r.statesubjectareaid
			from studentstests st
			join testsession ts on ts.id = st.testsessionid and ts.activeflag is true
			join enrollmentsrosters er on er.enrollmentid = st.enrollmentid and er.rosterid = ts.rosterid
			join roster r on r.id = er.rosterid and r.activeflag is true
			where er.activeflag is false 
			and st.activeflag is true 
			--and r.statesubjectareaid = 441 
			and st.status = 86
			and r.teacherid in (select id from aartuser where uniquecommonidentifier in ('195957','206770','183372','186972','137703','190100','207906','147635'))) 
	LOOP
			select rosterid INTO correctRosterid
			from enrollmentsrosters er
			join roster r on r.id = er.rosterid and r.activeflag is true and r.statesubjectareaid = strecord.statesubjectareaid and r.teacherid = strecord.teacherid
			where er.enrollmentid =strecord.enrollmentid and er.activeflag is true;
	
			UPDATE testsession SET rosterid = correctRosterid, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          	WHERE id = strecord.testsessionid;
	END LOOP;

END;
$BODY$;
	
