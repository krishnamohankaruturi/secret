--ddl/693.sql

alter table interimgroupstudent 
	add column modifieddate timestamp with time zone DEFAULT ('now'::text)::timestamp without time zone,
	add column modifieduser integer DEFAULT 12;
	
	
CREATE OR REPLACE FUNCTION public.dlm_testlet_reset(
	student_id bigint,
	testsession_id bigint,
	content_area_id bigint,
	modified_user_id bigint)
    RETURNS void AS
 $BODY$

  DECLARE temptable RECORD;

  BEGIN
  CREATE TEMPORARY TABLE tmp_dlm_data (studentid bigint, studentstestsid bigint, testsessionid bigint, studenttrackerid bigint, studenttrackerbandid bigint);
  
  FOR temptable IN ( select stu.id as studentid, st.id as studentstestsid, st.testsessionid, str.id as studenttrackerid,
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
      )
    LOOP
        INSERT INTO tmp_dlm_data(studentid , studentstestsid , testsessionid , studenttrackerid , studenttrackerbandid ) 
                values( temptable.studentid,temptable.studentstestsid,temptable.testsessionid,temptable.studenttrackerid,
                temptable.studenttrackerbandid);
    END LOOP;
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

delete
    from studenttrackerblueprintstatus
    where studenttrackerid in (select studenttrackerid from tmp_dlm_data)
    and operationalwindowid = (select operationaltestwindowid from testsession where id = testsession_id)
    and statuscode = 'STCOMPLETED';

DROP TABLE  tmp_dlm_data;
END;
$BODY$
 LANGUAGE 'plpgsql'
COST 100.0;