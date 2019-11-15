
--ddl/512.sql

ALTER TABLE aartuser ALTER COLUMN displayname TYPE character varying(160);
ALTER TABLE modulereport ALTER COLUMN description TYPE character varying(200);

CREATE OR REPLACE FUNCTION addglobalsystemadminrole(IN emailAddress text)
  RETURNS void AS
$BODY$
	DECLARE 
		userId bigint;
		userOrgId bigint;
	BEGIN
  		SELECT INTO userId (select id from aartuser where email=emailAddress and activeflag is true);
  		userOrgId := (select nextval('usersorganizations_id_seq'::regclass));
		INSERT INTO usersorganizations(id, aartuserid, organizationid, isdefault, createddate, createduser, modifieddate, modifieduser)
			VALUES (userOrgId, userId, (select id from organization where displayidentifier='CETE' limit 1), false, now(), 
			(select id from aartuser where email='cete@ku.edu' and activeflag is true), now(), 
			(select id from aartuser where email='cete@ku.edu' and activeflag is true));
            
		INSERT INTO userorganizationsgroups(groupid, status, userorganizationid, createddate, createduser, modifieddate, modifieduser)
			VALUES ((select id from groups where groupcode='GSAD'), 2, userOrgId, now(), (select id from aartuser where email='cete@ku.edu' and activeflag is true), now(), 
			(select id from aartuser where email='cete@ku.edu' and activeflag is true));
        END;
	$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
CREATE OR REPLACE FUNCTION updatesectionstatusandgetteststatusforlcs(in_studenttestid bigint, in_testsectionid bigint, in_sectionscore text, in_testscore text, 
in_categorycode text, in_interimthetavalue double precision, in_stdteststartdatetime timestamp without time zone, in_stdtestenddatetime timestamp without time zone,
in_stdtestsecstartdatetime timestamp without time zone, in_stdtestsecenddatetime timestamp without time zone)
  RETURNS boolean AS
$BODY$
  DECLARE
    completecount integer;
    iscomplete boolean;
    trackerid bigint;
    testunusedstatusid bigint;
  BEGIN
	iscomplete := false;
	IF in_categorycode = 'inprogress' THEN
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
				and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	ELSE
		update public.studentstestsections set statusid = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id where categorytype.typecode = 'STUDENT_TESTSECTION_STATUS'
					and category.categorycode = in_categorycode), 
			scores = in_sectionscore, modifieddate=now() where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	END IF;
	
	IF in_stdtestsecstartdatetime IS NOT NULL THEN
		update public.studentstestsections set startdatetime = in_stdtestsecstartdatetime where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	END IF;
	IF in_stdtestsecenddatetime IS NOT NULL THEN
		update public.studentstestsections set enddatetime = in_stdtestsecenddatetime where studentstestid = in_studenttestid and testsectionid = in_testsectionid; 
	END IF;
	
	SELECT count(1) into completecount FROM studentstestsections AS sts
		JOIN category On category.id = sts.statusid 
		JOIN public.categorytype ON category.categorytypeid = categorytype.id 
		where categorytype.typecode ='STUDENT_TESTSECTION_STATUS'
			and category.categorycode != 'complete'
			and  sts.studentstestid = in_studenttestid;

	IF completecount = 0 THEN
		iscomplete := true;
		
		trackerid := (select studenttrackerid from studenttrackerband stb inner join studentstests sts 
					on stb.testsessionid=sts.testsessionid where sts.id = in_studenttestid);
		testunusedstatusid := (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'unused');
						
		update public.studentstests set status = (select category.id from public.category JOIN public.categorytype 
				ON category.categorytypeid = categorytype.id 
				where categorytype.typecode = 'STUDENT_TEST_STATUS' and category.categorycode = 'complete'),
			 scores = in_testscore, interimtheta = in_interimthetavalue, modifieddate=now() where id = in_studenttestid;
		--DLM	 
		IF trackerid is not null THEN
			update public.studenttracker set status='UNTRACKED' where status='TRACKED' and id = trackerid;
		END IF;
		--KAP
		update studentstests set status=testunusedstatusid where id in (select distinct nextst.id from studentstests st
						inner join testsession ts on st.testsessionid=ts.id
						inner join testcollection tc on tc.id=ts.testcollectionid
						inner join stage s on s.predecessorid=tc.stageid
						inner join testcollection nexttc on nexttc.stageid=s.id and tc.contentareaid=nexttc.contentareaid
						inner join testsession nextts on nexttc.id=nextts.testcollectionid
						inner join studentstests nextst on nextst.testsessionid=nextts.id
							and st.studentid=nextst.studentid
						inner join category nextstatus on nextst.status = nextstatus.id
					where nextstatus.categorycode = 'pending' and ts.source='BATCHAUTO' 
						and nextts.source='BATCHAUTO' and st.id = in_studenttestid);
	ELSIF in_testscore IS NOT NULL THEN
		update public.studentstests set scores = in_testscore, modifieddate=now() where id = in_studenttestid;
	END IF;
	
	
	IF in_stdteststartdatetime IS NOT NULL THEN
		update public.studentstests set startdatetime = in_stdteststartdatetime where id = in_studenttestid; 
	END IF;
	IF in_stdtestenddatetime IS NOT NULL THEN
		update public.studentstests set enddatetime = in_stdtestenddatetime where id = in_studenttestid; 
	END IF;	
	
	RETURN iscomplete;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  