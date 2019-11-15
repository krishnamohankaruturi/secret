DO
$BODY$
DECLARE
	strecord RECORD;
BEGIN
--5402291071	ELA		Stage 2
--Reactivate ELA Stage2
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'5402291071') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(2) 
		join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid = 3 where st.activeflag is true)
	LOOP
		UPDATE studentstests SET activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        WHERE id  = strecord.stdtstid;

		UPDATE studentstestsections SET statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE studentstestid = strecord.stdtstid;

		INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
     	 VALUES (strecord.stdtstid, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
	END LOOP;
	
	--Inactivate ELA Stage 3,4
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'5402291071') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(4,5) 
		join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid = 3 where st.activeflag is true)
	LOOP
		update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       		where studentstestsid =strecord.stdtstid;

		update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
	          where studentstestid =strecord.stdtstid;
	          
		update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
	        where id =strecord.stdtstid;	
	     
	    INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
	     	 VALUES (strecord.stdtstid, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
	END LOOP;
	
	
--7417699472	ELA		Stage 1
--Reactivate ELA Stage1
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'7417699472') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(1) 
		join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid = 3 where st.activeflag is true)
	LOOP
		UPDATE studentstests SET activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        WHERE id  = strecord.stdtstid;

		UPDATE studentstestsections SET statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE studentstestid = strecord.stdtstid;

		INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
     	 VALUES (strecord.stdtstid, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
	END LOOP;
	
	--Inactivate ELA Stage 2,3,4
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'7417699472') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(2,4,5) 
		join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid = 3 where st.activeflag is true)
	LOOP
		update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       		where studentstestsid =strecord.stdtstid;

		update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
	          where studentstestid =strecord.stdtstid;
	          
		update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
	        where id =strecord.stdtstid;	
	     
	    INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
	     	 VALUES (strecord.stdtstid, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
	END LOOP;

--7261175323	Math	Stage 1
--Reactivate Math Stage1
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'7261175323') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(1) 
		join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid = 440 where st.activeflag is true)
	LOOP
		UPDATE studentstests SET activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        WHERE id  = strecord.stdtstid;

		UPDATE studentstestsections SET statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE studentstestid = strecord.stdtstid;

		INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
     	 VALUES (strecord.stdtstid, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
	END LOOP;
	
	--Inactivate Math Stage 2,3,4
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'7261175323') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(2,4,5) 
		join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid = 440 where st.activeflag is true)
	LOOP
		update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       		where studentstestsid =strecord.stdtstid;

		update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
	          where studentstestid =strecord.stdtstid;
	          
		update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
	        where id =strecord.stdtstid;	
	     
	    INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
	     	 VALUES (strecord.stdtstid, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
	END LOOP;
	
--9745360929	Math	Stage 3
--Reactivate Math Stage3
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'9745360929') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(4) 
		join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid = 3 where st.activeflag is true)
	LOOP
		UPDATE studentstests SET activeflag = true, status = 85,
        modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), enddatetime = null        
        WHERE id  = strecord.stdtstid;

		UPDATE studentstestsections SET statusid =  129,       
          previousstatusid = 127,
          modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')         
          WHERE studentstestid = strecord.stdtstid;

		INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
     	 VALUES (strecord.stdtstid, 86, 'REACTIVATION', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
	END LOOP;
	
	--Inactivate Math Stage 4
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'9745360929') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(5) 
		join testcollection tc ON tc.id = ts.testcollectionid and tc.contentareaid = 3 where st.activeflag is true)
	LOOP
		update studentsresponses set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')
       		where studentstestsid =strecord.stdtstid;

		update studentstestsections set modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin'), activeflag = false
	          where studentstestid =strecord.stdtstid;
	          
		update studentstests set activeflag = false, modifieddate = now(), modifieduser = (select id from aartuser where username = 'cetesysadmin')    
	        where id =strecord.stdtstid;	
	     
	    INSERT INTO studentstestshistory(studentstestsid, studentstestsstatusid, action, acteduser, acteddate) 
	     	 VALUES (strecord.stdtstid, 86, 'INACTIVATE', (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now());
	END LOOP;
END;
$BODY$;