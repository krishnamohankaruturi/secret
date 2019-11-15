/* validation
select st.id as stid, ts.id as tsid, ts.name as testsessionname, stb.activeflag as A, t.testname,
 (select categorycode as status from category where id = sts.status)
from student stud
join studenttracker st on st.studentid = stud.id
join studenttrackerband stb on stb.studenttrackerid = st.id
join testsession ts on ts.id = stb.testsessionid
join studentstests sts on sts.testsessionid = ts.id and sts.studentid = stud.id
join test t on t.id = sts.testid
where stud.statestudentidentifier = '6523688251'
and st.contentareaid =3
order by stb.createddate asc;

--only for SS ( no need to deactivate studenttracker)
select id, name,activeflag from testsession where id in (select testsessionid from studentstests where studentid = (select id from student where statestudentidentifier ='1021788481'));

--if do not have tests and testsession not assigning
--UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
   --  WHERE id in (st);
*/
BEGIN;
-- student:6523688251
UPDATE studentsresponses SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE studentstestsid IN (SELECT id FROM studentstests WHERE testsessionid IN (3781603));

UPDATE studentstestsections SET activeflag = false,  modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
      WHERE studentstestid IN (SELECT id FROM studentstests WHERE testsessionid IN (3781603));

UPDATE studentstests SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE testsessionid IN (3781603);
                 
UPDATE testsession SET activeflag = false, modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), modifieddate = now()
     WHERE id IN (3781603);

UPDATE studenttrackerband SET activeflag = false, modifieddate = now(), modifieduser = (SELECT id FROM aartuser WHERE username = 'cetesysadmin')
     WHERE testsessionid in (3781603);
     
INSERT INTO domainaudithistory (source, objecttype, objectid, createduserid, createddate, action, objectbeforevalues, objectaftervalues)
      values('SCRIPT', 'STUDENTSTESTS', 6523688251, 12, now(), 'REST_STUDENT_TRACKER', ('{"TestSessionIds": "3781603", "activeflag": true}')::JSON,  ('{"Reason": "As per US18940, inactivated the students tests"}')::JSON);
    
 update studenttracker set status = 'UNTRACKED' where id in (221459);

COMMIT;  

