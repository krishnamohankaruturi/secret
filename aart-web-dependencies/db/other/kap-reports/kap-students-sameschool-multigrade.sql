--kap-students-sameschool-multigrade.sql

\copy (
select st.statestudentidentifier,st.legalfirstname,st.legallastname,otd.districtname,otd.schoolname,ca.abbreviatedname as subject, gc.abbreviatedname as grade
from studentreport sr 
join student st on st.id=sr.studentid
join organizationtreedetail otd on otd.schoolid=sr.attendanceschoolid
join gradecourse gc on gc.id=sr.gradeid
join contentarea ca on ca.id=sr.contentareaid
join 
(select studentid,contentareaid,attendanceschoolid from studentreport 
where schoolyear=2016 and assessmentprogramid=12 and status is true 
group by studentid,contentareaid,attendanceschoolid having count(*) > 1) r 
on (r.studentid=sr.studentid and r.contentareaid=sr.contentareaid
and r.attendanceschoolid=sr.attendanceschoolid)
where sr.status is true and sr.schoolyear=2016 order by st.statestudentidentifier
) TO 'kap-students-sameschool-multigrade.csv' DELIMITER ',' CSV HEADER;


