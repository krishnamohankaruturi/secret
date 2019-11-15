
--KAP testassignment issue	
update testcollectionstests set testcollectionid = 4979 where testid in (35382,36414,39115);

delete from assessmentstestcollections where testcollectionid = 4980;

delete from operationaltestwindowstestcollections where  testcollectionid = 4980;

update studentstests set testcollectionid = 4979 where testcollectionid = 4980;

update testsession set testcollectionid = 4979 where testcollectionid = 4980;

delete from testcollection where id = 4980;

--ELA
INSERT INTO testaccessibilityflag(testid, accessibilityflagcode) 
	SELECT distinct id, 'spanish' from test t inner join testaccessibilityflag taf on taf.testid=t.id where t.qccomplete is true and t.status=64 
		and t.externalid in (14819,14943,14749,14846,14750,14862,14782,14878,14771,14894,14810,14921,14822,14929)
		and accessibilityflagcode <> 'spanish';
--HGSS
INSERT INTO testaccessibilityflag(testid, accessibilityflagcode) 
	SELECT distinct id, 'signed' from test t inner join testaccessibilityflag taf on taf.testid=t.id where t.qccomplete is true and t.status=64 
		and t.externalid in (14844,14847,14849)
		and accessibilityflagcode <> 'signed';
INSERT INTO testaccessibilityflag(testid, accessibilityflagcode) 
	SELECT distinct id, 'spanish' from test t inner join testaccessibilityflag taf on taf.testid=t.id where t.qccomplete is true and t.status=64 
		and t.externalid in (14844,14847,14849)
		and accessibilityflagcode <> 'spanish';

--SCI
INSERT INTO testaccessibilityflag(testid, accessibilityflagcode) 
	SELECT distinct id, 'signed' from test t inner join testaccessibilityflag taf on taf.testid=t.id where t.qccomplete is true and t.status=64 
		and t.externalid in (14585,14686,14588,14589,14590,14591,14595,14598,14599)
		and accessibilityflagcode <> 'signed';
		