
--get the statestudentidentifiers from aartaudit db
begin;
select distinct state_student_identifier into temp temptascstudentid from tasc_record_staging  where ksdexmlaudit_id=7199 and status='COMPLETED' 
and state_subj_area_code='80' and state_student_identifier not in(select distinct state_student_identifier from tasc_record_staging where status='COMPLETED' 
and state_subj_area_code in('01','02','51','52','81','82')) and state_student_identifier not in (select distinct state_student_identifier from kids_record_staging 
where status='COMPLETED');
\copy (select * from temptascstudentid) to 'Record_DE14813.csv' DELIMITER ',' CSV HEADER;
rollback;

--copy file on to server and get the studentids and store in temp table
begin;
create temp table tascstudentId(state_student_identifier varchar);
\COPY tascstudentId from 'Record_DE14813.csv' DELIMITER ',' CSV HEADER;
 \COPY (select distinct st.id from student st join enrollment en on en.studentid=st.id and en.currentschoolyear=2017 and en.activeflag is true and 
 en.sourcetype='TASC' and st.stateid=51 and st.activeflag is true  and st.statestudentidentifier in(select state_student_identifier from tascstudentId)) 
 to 'de14813_studentid.csv'  DELIMITER ',' CSV HEADER;
rollback;

--copy file de14813_studentid.csv onto server using winscp and do below steps

DROP FUNCTION IF EXISTS updatetascenrollmentrosters();
create temp table tascstudentId(id bigint);
\COPY tascstudentId from 'de14813_studentid.csv' DELIMITER ',' CSV HEADER;

CREATE OR REPLACE FUNCTION updatetascenrollmentrosters() 
RETURNS void AS

$BODY$
 DECLARE
 enrollmentRecord RECORD;
 ela_enrollment_roster_record RECORD;
 math_enrollments_rosters_id BIGINT;
 incorrect_math_roster_id BIGINT;
 new_math_roster_id BIGINT;
 teacherid BIGINT;
 recordCount integer;
 --filepath text;
   BEGIN
	recordCount := 0;
	--filepath:='de14813_studentid.csv';
	
	--EXECUTE format($psql$COPY tascstudentId FROM %L (DELIMITER ',', HEADER TRUE, FORMAT CSV)$psql$, filepath);
	--\COPY tascstudentId from 'de14813_studentid.csv' DELIMITER ',' CSV HEADER;
	DROP TABLE IF EXISTS temp_tasc_enrollment_rosters;
	CREATE TABLE temp_tasc_enrollment_rosters AS( select distinct stu.id as studentid, stu.statestudentidentifier, 
		en.id as enrollmentid, count(distinct r.teacherid) as count
	     from student stu
	     join enrollment en on en.studentid= stu.id 
	     join enrollmentsrosters enr on enr.enrollmentid = en.id
	     join roster r on r.id = enr.rosterid   
	     where en.sourcetype = 'TASC' and en.activeflag is true and en.currentschoolyear = 2017 and r.sourcetype = 'TASC' 
	     and r.currentschoolyear = 2017  
	     and r.activeflag is true and enr.activeflag is true and r.activeflag is true and r.statesubjectareaid in (3, 440)
	     and stu.id in (select id from tascstudentId)
	     group by stu.id, stu.statestudentidentifier, en.id having  count(distinct r.teacherid) > 1);
	     
        
        FOR enrollmentRecord IN( select studentid, enrollmentid from temp_tasc_enrollment_rosters)

	LOOP
		recordCount := recordCount + 1;
		RAISE NOTICE 'Start processing for Student % and EnrollmentId %', enrollmentRecord.studentid, enrollmentRecord.enrollmentid;

		FOR ela_enrollment_roster_record IN (SELECT r.attendanceschoolid, r.aypschoolid, r.teacherid from roster r 
		join enrollmentsrosters enr on enr.rosterid = r.id 
		and enr.enrollmentid = enrollmentRecord.enrollmentid and r.statesubjectareaid = 3 and r.sourcetype = 'TASC'
		and r.currentschoolyear = 2017 and r.activeflag is true and enr.activeflag is true)		
		LOOP
			SELECT INTO math_enrollments_rosters_id (SELECT enr.id from enrollmentsrosters enr join roster r 
			ON  enr.rosterid = r.id 
			where enr.enrollmentid = enrollmentRecord.enrollmentid and r.statesubjectareaid = 440 and r.sourcetype = 'TASC'
			and r.currentschoolyear = 2017 and r.activeflag is true and enr.activeflag is true);

			SELECT INTO incorrect_math_roster_id (SELECT enr.rosterid from enrollmentsrosters enr join roster r 
			ON  enr.rosterid = r.id 
			where enr.enrollmentid = enrollmentRecord.enrollmentid and r.statesubjectareaid = 440 and r.sourcetype = 'TASC'
			and r.currentschoolyear = 2017 and r.activeflag is true and enr.activeflag is true);
			
			SELECT INTO new_math_roster_id (select r.id from roster r where r.teacherid = ela_enrollment_roster_record.teacherid
			and r.sourcetype = 'TASC' AND r.currentschoolyear = 2017 and r.activeflag is true
			and r.attendanceschoolid = ela_enrollment_roster_record.attendanceschoolid
			and r.aypschoolid = ela_enrollment_roster_record.aypschoolid
			and r.statesubjectareaid = 440);

			RAISE NOTICE 'Raplaced old math RosterId % with new RosterId % on EnrollmentsRostersId %', incorrect_math_roster_id, 
			new_math_roster_id, math_enrollments_rosters_id;
			
			update enrollmentsrosters set rosterid = new_math_roster_id
			where id = math_enrollments_rosters_id;

			
		END LOOP;
		RAISE NOTICE 'Completed processing for Student % and EnrollmentId %', enrollmentRecord.studentid, enrollmentRecord.enrollmentid;
	END LOOP;
	RAISE NOTICE 'Total Records updated %',recordCount; 
	DROP TABLE tascstudentId;
	DROP TABLE temp_tasc_enrollment_rosters;
   END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;