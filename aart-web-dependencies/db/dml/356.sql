-- US15772: Test Sessions: Apply Operational Test Window Dates To Session

update operationaltestwindow set activeflag = true;

INSERT INTO operationaltestwindowstestcollections 
(
	operationaltestwindowid,
	testcollectionid,
	createduser,
	createddate,
	modifieduser,
	modifieddate,
	activeflag
) 
select id, testcollectionid, createduser, createddate, modifieduser, modifieddate, activeflag from operationaltestwindow;

DO
$BODY$ 
DECLARE TTARECORD RECORD;
BEGIN

	FOR TTARECORD IN
		select otw.id as otwid, ts.id as tsid from operationaltestwindow otw
		inner join testsession ts on ts.testcollectionid=otw.testcollectionid
	LOOP
		UPDATE testsession SET operationaltestwindowid=TTARECORD.otwid
		WHERE id=TTARECORD.tsid;           
	END LOOP;
END;
$BODY$;
 