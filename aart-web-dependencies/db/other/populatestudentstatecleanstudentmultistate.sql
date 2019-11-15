Begin;

DO
$BODY$ 
DECLARE ENRLL RECORD;
DECLARE STORGID bigint;
DECLARE newStudentId bigint;
DECLARE oldStudentStateid  bigint;
DECLARE enrollmentIdToNewStudent bigint;
DECLARE STUDENTMULTISTATE RECORD;
DECLARE STUDENTMULTIPLEENRLL RECORD;
BEGIN
	FOR ENRLL IN (
		  select distinct id, studentid, attendanceschoolid, createddate
		  from enrollment 
		  where currentschoolyear=2015  
		  and studentid not in (
			select studentid from(
				select distinct studentid, 
				(select displayidentifier from organization_parent_tree(attendanceschoolid, 70) where organizationtypeid=2)
				from enrollment 
				where studentid in(
					select en.studentid
					from enrollment en 
					join student st on en.studentid=st.id 
					where en.currentschoolyear=2015 and st.stateid is null and en.activeflag is true
					group by studentid having count(distinct attendanceschoolid) > 1 order by studentid)
			) as students
			group by studentid having count(distinct displayidentifier) > 1 order by studentid
		  )
		  order by studentid desc, createddate
	   )
	LOOP
	         
	  STORGID = null;
	  STORGID = (select id from organization_parent(ENRLL.attendanceschoolid) where contractingorganization is true);
	  
	  IF(STORGID is not null) THEN
		RAISE NOTICE 'Updating Student: % with stateid: %', ENRLL.studentid, STORGID;
		UPDATE student SET stateid=STORGID
			WHERE id=ENRLL.studentid AND stateid is NULL;
	  END IF;

	END LOOP;

	FOR STUDENTMULTISTATE IN (
		select studentid from (
			select distinct studentid, 
			(select displayidentifier from organization_parent(attendanceschoolid) where contractingorganization is true)
			from enrollment 
			where activeflag is true and studentid in(
				select en.studentid
					from enrollment en 
					join student st on en.studentid=st.id 
					where en.currentschoolyear=2015 and st.stateid is null and en.activeflag is true
					group by studentid having count(distinct attendanceschoolid) > 1 order by studentid)
		) as students
		group by studentid having count(distinct displayidentifier) > 1 order by studentid
	    )
	LOOP

		newStudentId = null;
		select nextval('student_id_seq') into newStudentId;

		oldStudentStateid = null;
		select (select id from organization_parent(attendanceschoolid) where contractingorganization is true) into oldStudentStateid
					from enrollment where studentid=STUDENTMULTISTATE.studentid order by modifieddate desc limit 1;

		update student set stateid= oldStudentStateid where id=STUDENTMULTISTATE.studentid;
		
		RAISE NOTICE 'Creating new Student: %, from student: %', newStudentId, STUDENTMULTISTATE.studentid;
		INSERT INTO student(
			    id, statestudentidentifier, createduser, profilestatus, stateid)	
			    SELECT newStudentId, statestudentidentifier, (SELECT id FROM aartuser WHERE username = 'cetesysadmin'),
					'NO SETTINGS', stateid
					FROM student where id= STUDENTMULTISTATE.studentid;
		
		FOR STUDENTMULTIPLEENRLL IN (
			select en.id as enrollmentid, studentid, attendanceschoolid,
				(select id as stateid from organization_parent(en.attendanceschoolid) where contractingorganization is true)
			from enrollment en where en.studentid=STUDENTMULTISTATE.studentid and en.activeflag is true
			and (select id from organization_parent(en.attendanceschoolid) where contractingorganization is true) 
				<> (select (select id from organization_parent(attendanceschoolid) where contractingorganization is true) 
					from enrollment where studentid=STUDENTMULTISTATE.studentid order by modifieddate desc limit 1)
		)
		LOOP	
			RAISE NOTICE 'Updating enrollment, studentstests, studentsresponses with new Studentid: %, from studentid: %', newStudentId, STUDENTMULTIPLEENRLL.studentid;
			
			UPDATE student 
				SET stateid=(select id from organization_parent(ENRLL.attendanceschoolid) where contractingorganization is true and organizationtypeid=2) 
				WHERE id=newStudentId;
				
			update enrollment set studentid = newStudentId where id= STUDENTMULTIPLEENRLL.enrollmentid;

			update studentstests set studentid = newStudentId 
				where enrollmentid=STUDENTMULTIPLEENRLL.enrollmentid and studentid=STUDENTMULTIPLEENRLL.studentid;

			update studentsresponses set studentid = newStudentId 
				where studentstestsid in (select id from studentstests 
							where enrollmentid=STUDENTMULTIPLEENRLL.enrollmentid and studentid=STUDENTMULTIPLEENRLL.studentid);
		END LOOP;

	END LOOP;
END;
$BODY$;

commit;
