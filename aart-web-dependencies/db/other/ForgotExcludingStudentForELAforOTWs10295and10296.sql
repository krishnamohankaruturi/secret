INSERT INTO public.operationaltestwindowstudent(studentid, contentareaid,  exclude, activeflag, operationaltestwindowid, createddate, modifieddate, createduser, modifieduser) 
VALUES (1436063, 3, true, true, 10295, now(), now(), 12, 12);

INSERT INTO public.operationaltestwindowstudent(studentid, contentareaid,  exclude, activeflag, operationaltestwindowid, createddate, modifieddate, createduser, modifieduser) 
VALUES (1436063, 3, true, true, 10296, now(), now(), 12, 12);

update studentstestsections set activeflag = false, modifieddate = now(), modifieduser =12
  where studentstestid = 20859792;

update studentstests set activeflag = false, modifieddate = now(), modifieduser =12,  manualupdatereason ='This student should exclude from the operational test window but forget while uploading for ELA subject'
 where id = 20859792;
 
update testsession set activeflag = false, modifieddate = now(), modifieduser =12
 where id = 5826730;
 
update studenttrackerband set activeflag = false, modifieddate = now(), modifieduser =12
 where testsessionid = 5826730;

update studenttracker set activeflag = false, modifieddate = now(), modifieduser =12
 where studentid = 1436063 and contentareaid = 3 and schoolyear = 2018;
 
 


