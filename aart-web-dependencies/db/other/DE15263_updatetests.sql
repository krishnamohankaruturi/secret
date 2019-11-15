--DE15263 - update kelpa tests
update studentstests set activeflag = false,
modifieddate = now(),
modifieduser = 12
where id in(15118938, 15118957, 15118951, 15118931, 15119025, 15119042, 15119034, 15119013, 15141028, 15141040, 15141051, 15141062,
15118016, 15118034, 15118007, 15118027, 15198038, 15198048, 15198056, 15198063, 15329492, 15119975, 15119985, 15119996, 15120893, 15120912, 15120886,
15120902, 15329776, 15146778, 15146771, 15146789, 15329500, 15121777, 15121767, 15121785, 15231485, 15231489, 15231497, 15231500);


update studentstests set testsessionid=4023225,
modifieduser=12, modifieddate=now()
where id=15146801;

update studentstests set testsessionid=4023225,
modifieduser=12, modifieddate=now()
where id=15121795;

update studentstests set testsessionid=4023225,
modifieduser=12, modifieddate=now()
where id=15120006;

--SSID: 5925743245

update studentstests set activeflag = false,
modifieddate = now(),
modifieduser = 12
where id in(15184134, 15184140, 15184162, 15399151);
	
update studentstests set testsessionid=4019055,
modifieduser=12, modifieddate=now()
where id=15184151;

update studentstests set testsessionid=4023108,
modifieduser=12, modifieddate=now()
where id=15399151;

update scoringassignmentstudent set scoringassignmentid=17563 where id=127722 and studentstestsid=15184151;
update scoringassignmentstudent set activeflag=false where id=128013;
