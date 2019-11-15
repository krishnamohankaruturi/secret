-- First Update query
BEGIN;

select count(st.*) from studentstests st join testsession ts on ts.id = st.testsessionid
join organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid join roster r on r.id = ts.rosterid
 where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true and st.status = 86 and r.statesubjectareaid not in (3, 440, 441);

select st.id as studentstestsid, st.testsessionid, st.enrollmentid, r.statesubjectareaid INTO temp temp_completed_other_surveys 
from studentstests st join testsession ts on ts.id = st.testsessionid
join organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid join roster r on r.id = ts.rosterid
 where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true and st.status = 86 and r.statesubjectareaid not in (3, 440, 441);

WITH rosterdetails AS(SELECT r.statesubjectareaid, enr.enrollmentid, enr.rosterid
 from enrollmentsrosters enr 
 join roster r on r.id = enr.rosterid
 join temp_completed_other_surveys tempsur on tempsur.enrollmentid = enr.enrollmentid
 where r.statesubjectareaid in (3, 440, 441) and enr.activeflag is true
)
update testsession ts set rosterid = CASE WHEN ((select count(*) from rosterdetails where enrollmentid = tempothsur.enrollmentid and statesubjectareaid = 3) > 0) THEN
  (select rosterid from rosterdetails where enrollmentid =tempothsur.enrollmentid and statesubjectareaid = 3 LIMIT 1)
  WHEN((select count(*) from rosterdetails where enrollmentid =tempothsur.enrollmentid and statesubjectareaid = 440) > 0) THEN
  (select rosterid from rosterdetails where enrollmentid =tempothsur.enrollmentid and statesubjectareaid = 440 LIMIT 1)
   WHEN ((select count(*) from rosterdetails where enrollmentid =tempothsur.enrollmentid and statesubjectareaid = 441) > 0) THEN
  (select rosterid from rosterdetails where enrollmentid =tempothsur.enrollmentid and statesubjectareaid = 441 LIMIT 1)
  ELSE 
     ts.rosterid
  END
   ,modifieduser = 12, modifieddate = now()
  from temp_completed_other_surveys tempothsur
  where tempothsur.testsessionid = ts.id and ts.activeflag is true;

select count(st. *) from studentstests st join testsession ts on ts.id = st.testsessionid
join organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid join roster r on r.id = ts.rosterid
 where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true and st.status = 86 and r.statesubjectareaid not in (3, 440, 441);

select st.*, ts.rosterid from studentstests st join testsession ts on ts.id = st.testsessionid
join organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid join roster r on r.id = ts.rosterid
 where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true and st.status = 86 and r.statesubjectareaid not in (3, 440, 441);

COMMIT;


-- Second update
BEGIN;
select count(*) from (select distinct st.studentid, st.enrollmentid, count(st.*) from studentstests st join testsession ts on ts.id = st.testsessionid
 where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
 group by st.studentid, st.enrollmentid having count(st.*) > 1)asp;
 
SELECT st.studentid, st.enrollmentid,st.testsessionid INTO temp temp_students_completed_surveys
  from studentstests st 
  join testsession ts on ts.id = st.testsessionid  
  where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
  and st.status = 86;


update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12, enhancednotes = 'Inactivating the duplicate research survey tests that are not completed'
   where id in (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid join temp_students_completed_surveys tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);


update testsession set activeflag = false, modifieddate = now(), modifieduser = 12 
     where id in (select st.testsessionid from studentstests st join testsession ts on ts.id = st.testsessionid join temp_students_completed_surveys tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);


SELECT st.studentid, st.enrollmentid, st.testsessionid, st.status into temp temp_surveys_on_ela
  from studentstests st
  join testsession ts on ts.id = st.testsessionid
  join roster r on r.id = ts.rosterid
  join organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid
  where ts.source = 'RESEARCHSURVEY' and r.statesubjectareaid = 3 and st.activeflag is true and ts.activeflag is true
  and otd.statedisplayidentifier in ('CO', 'NH', 'UT', 'ND', 'VT');


update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12, enhancednotes = 'Inactivating the duplicate research survey tests that are not completed and not rostered to ELA'
   where id in (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid join temp_surveys_on_ela tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);


update testsession set activeflag = false, modifieddate = now(), modifieduser = 12 
     where id in (select st.testsessionid from studentstests st join testsession ts on ts.id = st.testsessionid join temp_surveys_on_ela tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);



SELECT st.studentid, st.enrollmentid, st.testsessionid, st.status into temp temp_surveys_on_surveyg
  from studentstests st
  join testsession ts on ts.id = st.testsessionid  
  join organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid
  join testcollection tc on tc.id = st.testcollectionid
  where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
  and tc.name = 'Teacher Survey G'
  and otd.statedisplayidentifier in ('AK', 'IL', 'BIE-Miccosukee', 'OK', 'WV', 'WI', 'IA', 'KS', 'MO');


update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12, enhancednotes = 'Inactivating the duplicate research survey tests that are not completed and not rostered to Math and do not have ELA roster'
   where id in (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid join temp_surveys_on_surveyg tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);


update testsession set activeflag = false, modifieddate = now(), modifieduser = 12 
     where id in (select st.testsessionid from studentstests st join testsession ts on ts.id = st.testsessionid join temp_surveys_on_surveyg tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);



SELECT st.studentid, st.enrollmentid, st.testsessionid, st.status into temp temp_surveys_on_ela_2
  from studentstests st
  join testsession ts on ts.id = st.testsessionid
  join roster r on r.id = ts.rosterid
  join organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid
  where ts.source = 'RESEARCHSURVEY' and r.statesubjectareaid = 3 and st.activeflag is true and ts.activeflag is true
  and otd.statedisplayidentifier in ('AK', 'IL', 'BIE-Miccosukee', 'OK', 'WV', 'WI', 'IA', 'KS', 'MO');

update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12, enhancednotes = 'Inactivating the duplicate research survey tests that are not completed and not rostered to Sci and do not have ELA or Math rosters'
   where id in (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid join temp_surveys_on_ela_2 tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);


update testsession set activeflag = false, modifieddate = now(), modifieduser = 12 
     where id in (select st.testsessionid from studentstests st join testsession ts on ts.id = st.testsessionid join temp_surveys_on_ela_2 tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);


SELECT st.studentid, st.enrollmentid, st.testsessionid, st.status into temp temp_surveys_on_m
  from studentstests st
  join testsession ts on ts.id = st.testsessionid
  join roster r on r.id = ts.rosterid
  join organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid
  where ts.source = 'RESEARCHSURVEY' and r.statesubjectareaid = 440 and st.activeflag is true and ts.activeflag is true
  and otd.statedisplayidentifier in ('CO', 'NH', 'UT', 'ND', 'VT', 'AK', 'IL', 'BIE-Miccosukee', 'OK', 'WV', 'WI', 'IA', 'KS', 'MO');


update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12, enhancednotes = 'Inactivating the duplicate research survey tests that are not completed and not rostered to ELA'
   where id in (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid join temp_surveys_on_m tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);


update testsession set activeflag = false, modifieddate = now(), modifieduser = 12 
     where id in (select st.testsessionid from studentstests st join testsession ts on ts.id = st.testsessionid join temp_surveys_on_m tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);



SELECT st.studentid, st.enrollmentid, st.testsessionid, st.status into temp temp_surveys_on_sci
  from studentstests st
  join testsession ts on ts.id = st.testsessionid
  join roster r on r.id = ts.rosterid
  join organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid
  where ts.source = 'RESEARCHSURVEY' and r.statesubjectareaid = 441 and st.activeflag is true and ts.activeflag is true
  and otd.statedisplayidentifier in ('CO', 'NH', 'UT', 'ND', 'VT', 'AK', 'IL', 'BIE-Miccosukee', 'OK', 'WV', 'WI', 'IA', 'KS', 'MO');


update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12, enhancednotes = 'Inactivating the duplicate research survey tests that are not completed and not rostered to ELA'
   where id in (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid join temp_surveys_on_sci tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);


update testsession set activeflag = false, modifieddate = now(), modifieduser = 12 
     where id in (select st.testsessionid from studentstests st join testsession ts on ts.id = st.testsessionid join temp_surveys_on_sci tempstu on tempstu.enrollmentid = st.enrollmentid
    where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
    and st.testsessionid != tempstu.testsessionid);


select count(*) from (select distinct st.studentid, st.enrollmentid, count(st.*) from studentstests st join testsession ts on ts.id = st.testsessionid
 where ts.source = 'RESEARCHSURVEY' and st.activeflag is true and ts.activeflag is true
 group by st.studentid, st.enrollmentid having count(st.*) > 1)asp;

COMMIT;

BEGIN;
	update studentstestsections set activeflag = false, modifieddate = now(), modifieduser = 12
		where studentstestid in (select st.id from studentstests st join testsession ts on ts.id = st.testsessionid
			where ts.source = 'RESEARCHSURVEY' and st.activeflag is false);
COMMIT;


-- For the one individual student
update studentstests set activeflag = false, modifieddate = now(), modifieduser = 12
      where enrollmentid = 2905413 
      and testsessionid in (select id from testsession where rosterid = 1158499 and source = 'RESEARCHSURVEY');

update testsession set activeflag = false, modifieddate = now(), modifieduser = 12
      where id in (select testsessionid from studentstests where enrollmentid = 2905413) 
      and id in (select id from testsession where rosterid = 1158499 and source = 'RESEARCHSURVEY'); 

Update studentstestsections set activeflag = false, modifieddate = now(), modifieduser = 12
  Where studentstestid in (select id from studentstests where enrollmentid = 2905413
   and testsessionid in (select id from testsession where rosterid = 1158499 and source = 'RESEARCHSURVEY')); 
