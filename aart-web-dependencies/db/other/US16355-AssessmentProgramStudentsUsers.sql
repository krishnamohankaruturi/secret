----US16355 : Apply Pertinent Assessment Programs to Existing Users/Students

insert into  studentassessmentprogram(studentid,assessmentprogramid)
select distinct s.id, ap.id from student s join assessmentprogram ap on s.assessmentprogramid = ap.id
where ap.abbreviatedname = 'DLM' and ap.activeflag is true and s.activeflag is true and s.stateid is not null
and (s.id,ap.id) not in (select studentid,assessmentprogramid from studentassessmentprogram);

update orgassessmentprogram set activeflag = false 
where id in (select oap.id from orgassessmentprogram oap
join organization o on oap.organizationid= o.id  
join assessmentprogram ap on oap.assessmentprogramid = ap.id
where oap.activeflag is true and (o.organizationtypeid <> 2 or ap.activeflag is false));

update orgassessmentprogram set activeflag=false
where activeflag is true and organizationid in(
select id from organization where id in(
select distinct organizationid from orgassessmentprogram where activeflag is true) and activeflag is not true);

----State level users user assessment program population
insert into  userassessmentprogram(aartuserid,assessmentprogramid) 
                select distinct au.id, a.id from aartuser au 
                join usersorganizations uo on uo.aartuserid = au.id
                join organization o on uo.organizationid = o.id
                join orgassessmentprogram oap on oap.organizationid = o.id and oap.activeflag is true
                join assessmentprogram a on a.id = oap.assessmentprogramid and o.organizationtypeid=2 and a.activeflag = true
                where not exists (select aartuserid,assessmentprogramid from userassessmentprogram 
                                                                where aartuserid = au.id and assessmentprogramid = a.id);

----Region, Area, District, Building level users user assessment program population
insert into  userassessmentprogram(aartuserid,assessmentprogramid) 
                select distinct au.id, a.id from aartuser au 
                join usersorganizations uo on uo.aartuserid = au.id
                join organization o on o.id = uo.organizationid
                join orgassessmentprogram oap 
                                on (oap.organizationid = (select id from organization_parent_tree(uo.organizationid, 7) where organizationtypeid=2) 
                                                and oap.activeflag is true)
                join assessmentprogram a on a.id = oap.assessmentprogramid and o.organizationtypeid in (3,4,5,6) and a.activeflag = true
                where not exists (select aartuserid,assessmentprogramid from userassessmentprogram 
                                                                where aartuserid = au.id and assessmentprogramid = a.id);

--DLM teacher user assessment programs population
insert into  userassessmentprogram(aartuserid,assessmentprogramid) 
                SELECT distinct r.teacherid as aartuserid, sap.assessmentprogramid as apid
                FROM Student st
                JOIN enrollment enrl ON  enrl.Studentid = st.id 
                JOIN enrollmentsRosters enrlRoster ON enrl.id=enrlRoster.enrollmentid
                JOIN Roster r ON (r.id = enrlRoster.rosterId)
                JOIN studentassessmentprogram sap on sap.studentid = st.id
                WHERE enrl.activeflag is true and enrlRoster.activeflag is true     
                and r.activeflag is true and sap.activeflag is true 
                and sap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'DLM' and activeflag is true) 
                and not exists (select aartuserid,assessmentprogramid from userassessmentprogram 
                                                                where aartuserid = r.teacherid and assessmentprogramid = sap.assessmentprogramid);        

--Mark false orgs to false orgassessmentprograms                                                                
update orgassessmentprogram set activeflag=false
where activeflag is true and organizationid in(
select id from organization where id in(
select distinct organizationid from orgassessmentprogram where activeflag is true) and activeflag is not true);

-- cPass student assessment program population for all students except Kansas
insert into  studentassessmentprogram(studentid,assessmentprogramid)
select distinct id, (select id as apid from assessmentprogram where abbreviatedname='CPASS') from student
 where assessmentprogramid is null and activeflag is true and stateid in (select organizationid 
	from orgassessmentprogram where organizationid <> 51 
		and assessmentprogramid=(select id as apid from assessmentprogram where abbreviatedname='CPASS') and activeflag is true)
and (id,(select id as apid from assessmentprogram where abbreviatedname='CPASS')) not in 
				(select studentid,assessmentprogramid from studentassessmentprogram);

--All cPassKansas students
insert into  studentassessmentprogram(studentid,assessmentprogramid)
select distinct st.id, (select id as apid from assessmentprogram where abbreviatedname='CPASS') from enrollmenttesttypesubjectarea enttsa
join enrollment en on en.id = enttsa.enrollmentid
join student st on st.id = en.studentid and stateid=51
where enttsa.testtypeid in (
select id from testtype where activeflag is true and assessmentid in (select id from assessment where activeflag is true and testingprogramid in (select id from testingprogram where activeflag is true and assessmentprogramid=(select id from assessmentprogram where abbreviatedname='CPASS' and activeflag is true))))
and (st.id,(select id as apid from assessmentprogram where abbreviatedname='CPASS')) not in 
				(select studentid,assessmentprogramid from studentassessmentprogram);
				
--All AMP students				
insert into  studentassessmentprogram(studentid,assessmentprogramid)
select distinct id, (select id as apid from assessmentprogram where abbreviatedname='AMP') from student
 where assessmentprogramid is null and activeflag is true 
 and stateid in (select organizationid from orgassessmentprogram where activeflag is true and assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP'))
 and id not in (select studentid from studentassessmentprogram where assessmentprogramid=(select id from assessmentprogram where abbreviatedname='AMP'));
 
 --All KAP students
 DO
$BODY$ 
DECLARE
	DISTINCTSTRECORD RECORD;
BEGIN           
	FOR DISTINCTSTRECORD IN                
		select id from student where stateid in (51,58538) and assessmentprogramid is null and activeflag is true 
		and id not in(select studentid from studentassessmentprogram where assessmentprogramid in (11,3, 12) and studentid in(select distinct id from student where stateid in (51,58538) and assessmentprogramid is null))
	LOOP 
                INSERT INTO studentassessmentprogram(studentid,assessmentprogramid) VALUES (DISTINCTSTRECORD.id, 12);
	END LOOP;
END;
$BODY$;

--cPass teacher user assessment programs population
insert into  userassessmentprogram(aartuserid,assessmentprogramid) 
                SELECT distinct r.teacherid as aartuserid, sap.assessmentprogramid as apid
                FROM Student st
                JOIN enrollment enrl ON  enrl.Studentid = st.id 
                JOIN enrollmentsRosters enrlRoster ON enrl.id=enrlRoster.enrollmentid
                JOIN Roster r ON (r.id = enrlRoster.rosterId)
                JOIN studentassessmentprogram sap on sap.studentid = st.id
                WHERE enrl.activeflag is true and enrlRoster.activeflag is true     
                and r.activeflag is true and sap.activeflag is true 
                and sap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'CPASS' and activeflag is true) 
                and not exists (select aartuserid,assessmentprogramid from userassessmentprogram 
                                                                where aartuserid = r.teacherid and assessmentprogramid = sap.assessmentprogramid);
--AMP teacher user assessment programs population
insert into  userassessmentprogram(aartuserid,assessmentprogramid) 
                SELECT distinct r.teacherid as aartuserid, sap.assessmentprogramid as apid
                FROM Student st
                JOIN enrollment enrl ON  enrl.Studentid = st.id 
                JOIN enrollmentsRosters enrlRoster ON enrl.id=enrlRoster.enrollmentid
                JOIN Roster r ON (r.id = enrlRoster.rosterId)
                JOIN studentassessmentprogram sap on sap.studentid = st.id
                WHERE enrl.activeflag is true and enrlRoster.activeflag is true     
                and r.activeflag is true and sap.activeflag is true 
                and sap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'AMP' and activeflag is true) 
                and not exists (select aartuserid,assessmentprogramid from userassessmentprogram 
                                                                where aartuserid = r.teacherid and assessmentprogramid = sap.assessmentprogramid);
--KAP teacher user assessment programs population
insert into  userassessmentprogram(aartuserid,assessmentprogramid) 
                SELECT distinct r.teacherid as aartuserid, sap.assessmentprogramid as apid
                FROM Student st
                JOIN enrollment enrl ON  enrl.Studentid = st.id 
                JOIN enrollmentsRosters enrlRoster ON enrl.id=enrlRoster.enrollmentid
                JOIN Roster r ON (r.id = enrlRoster.rosterId)
                JOIN studentassessmentprogram sap on sap.studentid = st.id
                WHERE enrl.activeflag is true and enrlRoster.activeflag is true     
                and r.activeflag is true and sap.activeflag is true 
                and sap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'KAP' and activeflag is true) 
                and not exists (select aartuserid,assessmentprogramid from userassessmentprogram 
                                                                where aartuserid = r.teacherid and assessmentprogramid = sap.assessmentprogramid);
         
 