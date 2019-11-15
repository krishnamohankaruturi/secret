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
completed_section_count bigint; --F723
complete_status_id bigint; --F723
test_complete_status_id bigint; --F723
test_inprogress_status_id bigint; --F723

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
-- F723 get test section completestatusid for a studentTestSection i.e., 127
select into complete_status_id category.id from public.category 
	JOIN public.categorytype ON category.categorytypeid = categorytype.id
	where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
  	and category.categorycode = 'complete';
-- F723 get test completestatusid for a studentTest i.e., 86
SELECT c.id into test_complete_status_id FROM category c, categorytype ct 
    WHERE c.categorytypeid = ct.id AND c.categorycode = 'complete' AND ct.typecode='STUDENT_TEST_STATUS';
-- F723 get test inprogress statusid for a studentTest i.e., 85
SELECT c.id into test_inprogress_status_id FROM category c, categorytype ct 
    WHERE c.categorytypeid = ct.id AND c.categorycode = 'inprogress' AND ct.typecode='STUDENT_TEST_STATUS';
                                  
FOR STUDENTTEST_ROW IN
(select st.id,ts.operationaltestwindowid, ap.abbreviatedname as assessmentprogramname,
(select count(id) from studentstestsections where studentstestid = st.id and statusid = timedout_status_id) as gracestatuscount
 from studentstests st
inner join testsession ts on ts.id = st.testsessionid
left join operationaltestwindow otw on otw.id = ts.operationaltestwindowid 
AND ((otw.effectivedate <= CURRENT_TIMESTAMP AND otw.expirydate >= CURRENT_TIMESTAMP AND otw.suspendwindow IS false) 
OR (otw.effectivedate <= CURRENT_TIMESTAMP AND ts.windowexpirydate >= CURRENT_TIMESTAMP))
INNER JOIN assessmentstestcollections atc ON st.testcollectionid = atc.testcollectionid
INNER JOIN assessment a ON atc.assessmentid = a.id
INNER JOIN testingprogram tp ON a.testingprogramid=tp.id
INNER JOIN assessmentprogram ap ON tp.assessmentprogramid=ap.id
inner join category cat on cat.id = st.status
where ap.abbreviatedname in ('KAP','DLM','K-ELPA','I-SMART','I-SMART2','PLTW') and st.activeflag=true and cat.categorycode='inprogress')--F723 added Ismart and Ismart2

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
			update studentsteststimes stt set studentstests_endtime=now(),studentstestsections_endtime=now(),action='INPROGRESS_TIMEOUT' 
			where stt.id=(select stt1.id from studentsteststimes stt1 where stt1.studentstestsid=STUDENTTESTSECTION_ROW.studentstestid 
			and stt1.studentstestsectionid=STUDENTTESTSECTION_ROW.id and stt1.activeflag is true order by stt1.id desc limit 1)
			and action='TAKE_TEST';
		END IF;
	      
	      END LOOP;

	END IF;
	
	IF (STUDENTTEST_ROW.assessmentprogramname = 'DLM' OR STUDENTTEST_ROW.assessmentprogramname = 'I-SMART') THEN  
		FOR STUDENTTESTSECTION_ROW IN 
		(SELECT sts.id, sts.statusid, (EXTRACT(EPOCH FROM (case when sts.lastactive is null then sts.modifieddate else sts.lastactive end))+(90*60)) AS lastactivemillis 
		FROM studentstestsections sts where sts.studentstestid = STUDENTTEST_ROW.id AND sts.statusid=inprogress_status_id)
	        LOOP
			SELECT EXTRACT(EPOCH FROM current_timestamp) INTO current_millis_tocheck;
			SELECT (EXTRACT(EPOCH FROM MAX(sr.modifieddate))+(90*60)) INTO lastmodifieddatemillis 
			FROM studentsresponses AS sr WHERE sr.studentstestsectionsid = STUDENTTESTSECTION_ROW.id;
--			RAISE NOTICE 'DLM ,%,%,%,%,%,%,%,%',STUDENTTEST_ROW.id,STUDENTTESTSECTION_ROW.id,test_unused_status_id,status_id,lastmodifieddatemillis,current_millis_tocheck,lastmodifieddatemillis,STUDENTTESTSECTION_ROW.lastactivemillis;
			IF ((lastmodifieddatemillis IS NULL OR current_millis_tocheck > lastmodifieddatemillis) 
			    AND current_millis_tocheck > STUDENTTESTSECTION_ROW.lastactivemillis) THEN
--			      RAISE NOTICE 'DLM IF ,%,%,%,%',STUDENTTEST_ROW.id,STUDENTTESTSECTION_ROW.id,test_unused_status_id,status_id;
				update public.studentsresponses set activeflag = false, modifieddate=now() where studentstestsectionsid = STUDENTTESTSECTION_ROW.id;
				update public.studentstestsections set statusid = status_id, modifieddate=now() where id = STUDENTTESTSECTION_ROW.id;
				--F723 Ismart Org :code to check number of test sections completed
				SELECT INTO completed_section_count count(*) FROM studentstestsections sts 
					JOIN studentstests st ON st.id=sts.studentstestid
					where sts.studentstestid = STUDENTTEST_ROW.id AND sts.statusid=complete_status_id AND st.status!=test_complete_status_id;
					IF(completed_section_count>=1) THEN
						update public.studentstests set status = test_inprogress_status_id, modifieddate=now() where id = STUDENTTEST_ROW.id;
					ELSE	
						update public.studentstests set status = test_unused_status_id, modifieddate=now() where id = STUDENTTEST_ROW.id;
					END IF;
				update studentsteststimes stt set studentstests_endtime=now(),studentstestsections_endtime=now(),action='RESET_TEST' 
				where stt.id=(select stt1.id from studentsteststimes stt1 where stt1.studentstestsid=STUDENTTEST_ROW.id and stt1.studentstestsectionid=STUDENTTESTSECTION_ROW.id 
				 and stt1.activeflag is true order by stt1.id desc limit 1) and action='TAKE_TEST';
			END IF;
		END LOOP;
	END IF;

END LOOP;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;