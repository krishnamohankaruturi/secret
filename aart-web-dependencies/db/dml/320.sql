-- Batch Auto Registration UI changes
UPDATE assessmentprogram SET abbreviatedname='CPASS' WHERE programname='Career Pathways Collaborative' AND activeflag IS true;

update testtype set assessmentid=(select id from assessment where assessmentcode='GL' and testingprogramid=(
	select id from testingprogram where programabbr='S' and activeflag is true and assessmentprogramid=(
	select id from assessmentprogram where abbreviatedname='CPASS'))
	)
where testtypecode in ('A','B','D','E','F','G','H','I') and activeflag is true;

update testtype set assessmentid=(select id from assessment where assessmentcode='GL' and testingprogramid=(
	select id from testingprogram where programabbr='S' and activeflag is true and assessmentprogramid=(
	select id from assessmentprogram where abbreviatedname='AMP'))
	)
where testtypecode in ('GN','L','BR','P','R') and activeflag is true;

INSERT INTO testtypeassessment(
            testtypeid, assessmentid, createduser, createdate, modifieddate, 
            modifieduser, activeflag)
    VALUES ((select id from testtype where testtypecode='2' and activeflag is true order by id asc limit 1), (
	select id from assessment where assessmentcode='GL' and testingprogramid=(
	select id from testingprogram where programabbr='S' and activeflag is true and assessmentprogramid=(
	select id from assessmentprogram where abbreviatedname='CPASS'))
	), 
	(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), (SELECT id FROM aartuser WHERE username = 'cetesysadmin'), true);

DO
$BODY$ 
DECLARE TTARECORD RECORD;
BEGIN

	FOR TTARECORD IN
		select distinct id, testtypecode, assessmentid, activeflag from testtype where activeflag is true order by id
	LOOP
		INSERT INTO testtypeassessment(
			testtypeid, assessmentid, createduser, createdate, modifieddate, modifieduser, activeflag)
		VALUES (TTARECORD.id, TTARECORD.assessmentid, 
		(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), now(), now(), 
		(SELECT id FROM aartuser WHERE username = 'cetesysadmin'), TTARECORD.activeflag);            
	END LOOP;
END;
$BODY$;

UPDATE assessment SET autoenrollmentflag=true where id in(
select distinct assessmentid from testtypeassessment where activeflag is true);
