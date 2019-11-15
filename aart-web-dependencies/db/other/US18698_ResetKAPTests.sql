
--8794989729	ELA		stage 1
--2656895928	ELA 	Stage 1
--3211933492	ELA 	Stage 1
--3751763481	ELA 	Stage 1
--4792433037	ELA 	Stage 1
--8381782244	ELA 	Stage 1
--2870137265	ELA 	Stage 1
--4310895387	ELA 	Stage 1

DO
$BODY$
DECLARE
	strecord RECORD;
BEGIN
	--Reactivate ELA Stage1
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'8794989729',
		'2656895928',
		'3211933492',
		'3751763481',
		'4792433037',
		'8381782244',
		'2870137265',
		'4310895387') and s.stateid = 51
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
		'8794989729',
		'2656895928',
		'3211933492',
		'3751763481',
		'4792433037',
		'8381782244',
		'2870137265',
		'4310895387') and s.stateid = 51
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
	
	-------------
	--9989183945	ELA 	Stage 3
	--6314373417	ELA		Stage 3

	--Reactivate ELA Stage3
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'9989183945',
		'6314373417'
		) and s.stateid = 51
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
	
	--Inactivate ELA Stage 4
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'9989183945',
		'6314373417') and s.stateid = 51
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
	
	-------------
--8456036463	Math	Stage 3
--9054194529	Math	Stage 3

	--Reactivate Math Stage3
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'8456036463',
		'9054194529'
		) and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(4) 
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
	
	--Inactivate Math Stage 4
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'8456036463',
		'9054194529') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(5) 
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
	
	--9909474743	Math	Stage 2
	--Reactivate Math Stage2
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'9909474743') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(2) 
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
	
	--Inactivate Math Stage 3,4
	FOR strecord IN (select distinct st.id as stdtstid
		from studentstests st
		join student s ON st.studentid = s.id and s.statestudentidentifier in(
		'9909474743') and s.stateid = 51
		join testsession ts ON ts.id = st.testsessionid and ts.stageid in(4,5) 
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
END;
$BODY$;