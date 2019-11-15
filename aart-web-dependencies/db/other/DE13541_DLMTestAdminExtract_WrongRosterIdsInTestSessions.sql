--DE13541_DLMTestAdminExtract_WrongRosterIdsInTestSessions.sql
UPDATE testsession 
SET rosterid = 943387
WHERE id in (
select st.testsessionid 
from studentstests st 
join testsession ts on st.testsessionid = ts.id and ts.activeflag is true and st.activeflag is true 
join roster r on r.id = ts.rosterid and r.activeflag is true
where st.studentid in (select id from student where statestudentidentifier in 
('8199753813', '3988703512', '4091348552')
) 
and r.statesubjectareaid = 3
);

UPDATE testsession 
SET rosterid = 943388
WHERE id in (
select st.testsessionid 
from studentstests st 
join testsession ts on st.testsessionid = ts.id and ts.activeflag is true and st.activeflag is true 
join roster r on r.id = ts.rosterid and r.activeflag is true
where st.studentid in (select id from student where statestudentidentifier in 
('8199753813', '3988703512', '4091348552')
) 
and r.statesubjectareaid = 440
);