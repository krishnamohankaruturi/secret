-- US19442 -  "In Progress Timed Out" Scheduler/Process
-- TDE/Mahesh

CREATE OR REPLACE FUNCTION updateinprogresstimeoutstatus()
  RETURNS void AS
$BODY$

DECLARE

STUDENTTEST_ROW RECORD;
timedout_status_id bigint;
test_timedout_status_id bigint;
test_unused_status_id bigint;
status_id bigint;
grace_period bigint;
current_millis decimal;
current_millis_tocheck decimal;
lastmodifieddatemillis decimal;
lastactivemillis decimal;
inprogress_status_id bigint;
STUDENTTESTSECTION_ROW RECORD;

BEGIN
select into timedout_status_id category.id from public.category 
	JOIN public.categorytype ON category.categorytypeid = categorytype.id
	where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
	and category.categorycode = 'inprogresstimedout';
select into test_timedout_status_id category.id from public.category 
	JOIN public.categorytype ON category.categorytypeid = categorytype.id
	where categorytype.typecode = 'STUDENT_TEST_STATUS'
	and category.categorycode = 'inprogresstimedout';
select into inprogress_status_id category.id from public.category 
	JOIN public.categorytype ON category.categorytypeid = categorytype.id
	where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
	and category.categorycode = 'inprogress';
select into status_id category.id from public.category 
	JOIN public.categorytype ON category.categorytypeid = categorytype.id
	where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
	and category.categorycode = 'unused';
SELECT c.id into test_unused_status_id FROM category c, categorytype ct 
	WHERE c.categorytypeid = ct.id AND c.categorycode = 'unused' AND ct.typecode='STUDENT_TEST_STATUS';
                                  
FOR STUDENTTEST_ROW IN
(select st.id,ts.operationaltestwindowid, ap.abbreviatedname as assessmentprogramname,
(select count(id) from studentstestsections where studentstestid = st.id and statusid = timedout_status_id) as gracestatuscount
 from studentstests st
inner join testsession ts on ts.id = st.testsessionid
inner join operationaltestwindow otw on otw.id = ts.operationaltestwindowid
inner join assessmentprogram ap on ap.id = otw.assessmentprogramid
inner join category cat on cat.id = st.status
where ap.abbreviatedname in ('KAP','DLM','K-ELPA') and st.activeflag=true and cat.categorycode='inprogress'
AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false) 
OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP)) )

LOOP

SELECT graceperiod into grace_period FROM operationaltestwindowsessionrule otwsr, category sessionrule,categorytype catType
WHERE otwsr.sessionruleid = sessionrule.id AND sessionrule.categorytypeid=cattype.id
AND sessionrule.categorycode='GRACE_PERIOD' AND cattype.typecode='SESSION_RULES'
AND otwsr.operationaltestwindowid = STUDENTTEST_ROW.operationaltestwindowid and otwsr.activeflag=true;

	IF grace_period IS NOT NULL and STUDENTTEST_ROW.gracestatuscount = 0 THEN
	
	      FOR STUDENTTESTSECTION_ROW IN (select sts.id, sts.statusid, sts.studentstestid, 
	      (EXTRACT(EPOCH FROM sts.modifieddate)+(grace_period*60)) as sectionmodifiedmillis 
	      from studentstestsections sts 
	      where sts.studentstestid=STUDENTTEST_ROW.id AND sts.statusid=inprogress_status_id)
	      
	      LOOP
	      
		SELECT EXTRACT(EPOCH FROM current_timestamp) INTO current_millis;
		IF (STUDENTTESTSECTION_ROW.sectionmodifiedmillis < current_millis) THEN
--			RAISE NOTICE 'KAP ,%,%,%,%',STUDENTTESTSECTION_ROW.studentstestid,STUDENTTESTSECTION_ROW.id,test_timedout_status_id,timedout_status_id;
			update public.studentstestsections set statusid = timedout_status_id, modifieddate=now() where id = STUDENTTESTSECTION_ROW.id;
			update public.studentstests set status = test_timedout_status_id, modifieddate=now() where id = STUDENTTESTSECTION_ROW.studentstestid;
		END IF;
	      
	      END LOOP;

	END IF;
	
	IF (STUDENTTEST_ROW.assessmentprogramname = 'DLM') THEN  
		FOR STUDENTTESTSECTION_ROW IN 
		(SELECT sts.id, sts.statusid, (EXTRACT(EPOCH FROM sts.lastactive)+(90*60)) AS lastactivemillis 
		FROM studentstestsections sts where sts.studentstestid = STUDENTTEST_ROW.id AND sts.statusid=inprogress_status_id)
	        LOOP
			SELECT EXTRACT(EPOCH FROM current_timestamp) INTO current_millis_tocheck;
			SELECT (EXTRACT(EPOCH FROM MAX(sr.modifieddate))+(90*60)) INTO lastmodifieddatemillis 
			FROM studentsresponses AS sr WHERE sr.studentstestsectionsid = STUDENTTESTSECTION_ROW.id;
--			RAISE NOTICE 'DLM ,%,%,%,%,%,%,%,%',STUDENTTEST_ROW.id,STUDENTTESTSECTION_ROW.id,test_unused_status_id,status_id,lastmodifieddatemillis,current_millis_tocheck,lastmodifieddatemillis,STUDENTTESTSECTION_ROW.lastactivemillis;
			IF ((lastmodifieddatemillis IS NULL OR current_millis_tocheck > lastmodifieddatemillis) 
			    AND current_millis_tocheck > STUDENTTESTSECTION_ROW.lastactivemillis) THEN
--			      RAISE NOTICE 'DLM IF ,%,%,%,%',STUDENTTEST_ROW.id,STUDENTTESTSECTION_ROW.id,test_unused_status_id,status_id;
				insert into public.studentsresponsescopy select sr.*, now() from studentsresponses as sr where sr.studentstestsectionsid = STUDENTTESTSECTION_ROW.id;
				delete from public.studentsresponses AS sr where sr.studentstestsectionsid = STUDENTTESTSECTION_ROW.id;
				update public.studentstestsections set statusid = status_id where id = STUDENTTESTSECTION_ROW.id;
				update public.studentstests set status = test_unused_status_id where id = STUDENTTEST_ROW.id;
			END IF;
		END LOOP;
	END IF;

END LOOP;

END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;