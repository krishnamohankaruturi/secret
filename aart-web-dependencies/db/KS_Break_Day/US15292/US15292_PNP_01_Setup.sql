-- create a temporary backup table
CREATE TABLE ksdbprofile
(
  studentid bigint NOT NULL,
  selectedvalue text,
  profileitemattributenameattributecontainerid bigint,
  activeflag boolean,
  profileexits boolean NOT NULL
)


-- query to get all students in KAP and not DLM
select st.id
from student st 
join   enrollment en on st.id = en.studentid and en.currentschoolyear = 2015 
and st.assessmentprogramid is null and en.attendanceschoolid in (
	select id from organization_children((select org.id from organization org 
   join organizationtype ot on org.organizationtypeid = ot.id
   where org.displayidentifier='KS' and org.activeflag is true and ot.typecode='ST') 
));