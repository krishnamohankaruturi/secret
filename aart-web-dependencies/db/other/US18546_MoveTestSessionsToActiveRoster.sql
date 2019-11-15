--US18546: Move test sessions to active roster

UPDATE enrollmentsrosters
set activeflag = false
where enrollmentid in (select id from enrollment where studentid = (select id from student where statestudentidentifier = '6340102964' and stateid = 9591) and activeflag is true)
and rosterid in (880975, 880978);

UPDATE testsession 
SET rosterid = (select id from roster where (coursesectionname = 'DLM_ELA') AND aypschoolid = 19519)
WHERE id = 2568492 and rosterid =880975;

UPDATE testsession 
SET rosterid = (select id from roster where (coursesectionname = 'DLM_MA') AND aypschoolid = 19519)
WHERE id = 2567976 and rosterid =880978;
