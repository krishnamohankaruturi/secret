--701.sql ddl
-- DE15567: Unknown Error Occurred message comes up for DLM Test Reset
CREATE OR REPLACE FUNCTION dlm_testlet_reset(student_id bigint, testsession_id bigint, content_area_id bigint, modified_user_id bigint)
  RETURNS void AS
$BODY$

  BEGIN
  IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name='tmp_dlm_data' and table_type='LOCAL TEMPORARY')
                THEN DROP TABLE IF EXISTS tmp_dlm_data; END IF;
  CREATE TEMPORARY TABLE tmp_dlm_data AS ( select stu.id as studentid, st.id as studentstestsid, st.testsessionid, str.id as studenttrackerid,
      strb.id as studenttrackerbandid
      from student stu
      join studentstests st on st.studentid = stu.id
      join studenttrackerband strb on strb.testsessionid = st.testsessionid
      join studenttracker str on strb.studenttrackerid = str.id
      where stu.id = student_id  
      and  st.testsessionid >= testsession_id
      and st.activeflag is true
      and str.contentareaid = content_area_id      
      and strb.activeflag is true
      );    
    UPDATE studentsresponses SET activeflag = false, modifieduser = modified_user_id, modifieddate = now()
    WHERE studentstestsid IN (SELECT id FROM studentstests WHERE testsessionid IN (select testsessionid from tmp_dlm_data) and studentid=student_id);
RAISE NOTICE 'Updated student responses';

UPDATE studentstestsections SET activeflag = false,  modifieduser = modified_user_id, modifieddate = now()
     WHERE studentstestid IN (SELECT id FROM studentstests WHERE testsessionid IN (select testsessionid from tmp_dlm_data) and studentid=student_id);

UPDATE studentstests SET activeflag = false, modifieduser = modified_user_id, modifieddate = now()
    WHERE testsessionid IN (select testsessionid from tmp_dlm_data) and studentid=student_id;

UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = modified_user_id
    WHERE studenttrackerid in (select studenttrackerid from tmp_dlm_data) and testsessionid in (select testsessionid from tmp_dlm_data);

update studenttracker set status = 'UNTRACKED', modifieddate = now(), modifieduser = modified_user_id where id in (select studenttrackerid from tmp_dlm_data);

UPDATE testsession SET activeflag = false, modifieddate = now(), modifieduser = modified_user_id where id in (SELECT testsessionid FROM tmp_dlm_data);

delete
    from studenttrackerblueprintstatus
    where studenttrackerid in (select studenttrackerid from tmp_dlm_data)
    and operationalwindowid = (select operationaltestwindowid from testsession where id = testsession_id)
    and statuscode = 'STCOMPLETED';
drop table if exists tmp_dlm_data;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;
  