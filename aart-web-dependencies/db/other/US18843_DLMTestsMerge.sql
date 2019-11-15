--Please move completed DLM testlets from 2 previous schools to the student's current school. Student ID # 7594094824, Noah Kaplan
DO
$BODY$
DECLARE
	tsrecord RECORD;
BEGIN
	--ELA
	FOR tsrecord IN (select s.statestudentidentifier,ts.name,ts.rosterid, r.teacherid, r.activeflag as rosteractiveflag, st.id, st.status, st.enrollmentid, st.testsessionid, st.createddate,st.modifieddate, s.id as studentid,ts.source
						from studentstests st
						join student s ON st.studentid = s.id
						join testsession ts ON ts.id = st.testsessionid
						join roster r on r.id = ts.rosterid
						join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid in (3) where st.enrollmentid in (1891643,2378864,2385089) 
						and ts.source = 'BATCHAUTO')
					
	LOOP
	
		UPDATE testsession SET rosterid = 1055483, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  = tsrecord.testsessionid and rosterid in (896223,914943, 1051356);
	    
	    UPDATE studentstests SET enrollmentid = 2386788, 
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')     
        WHERE testsessionid  = tsrecord.testsessionid;
	
	END LOOP;
	
	--Math
	FOR tsrecord IN (select s.statestudentidentifier,ts.name,ts.rosterid, r.teacherid, r.activeflag as rosteractiveflag, st.id, st.status, st.enrollmentid, st.testsessionid, st.createddate,st.modifieddate, s.id as studentid,ts.source
						from studentstests st
						join student s ON st.studentid = s.id
						join testsession ts ON ts.id = st.testsessionid
						join roster r on r.id = ts.rosterid
						join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid in (440) where st.enrollmentid in (1891643,2378864,2385089) 
						and ts.source = 'BATCHAUTO')
					
	LOOP
	
		UPDATE testsession SET rosterid = 1055486, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
	    WHERE id  = tsrecord.testsessionid and rosterid in (896224,914944, 1051357);
	    
	    UPDATE studentstests SET enrollmentid = 2386788, 
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')       
        WHERE testsessionid  = tsrecord.testsessionid;
	
	END LOOP;
END;
$BODY$;