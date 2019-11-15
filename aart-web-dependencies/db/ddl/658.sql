--ddl/658.sql

-- functions for F475
drop function if exists kelpa_get_non_scorable_codes_for_test(bigint, bigint);
create function kelpa_get_non_scorable_codes_for_test(_studentid bigint, _testid bigint) returns setof text as
$body$
begin
	return query (
		select distinct c.categorycode::text
		from studentstests st
		join scoringassignmentstudent sas on st.id = sas.studentstestsid and sas.activeflag
		join ccqscore ccq on sas.id = ccq.scoringassignmentstudentid and ccq.activeflag
		join ccqscoreitem ccqi on ccq.id = ccqi.ccqscoreid and ccqi.activeflag
		join category c on ccqi.nonscoringreason = c.id
		where st.activeflag
		and st.studentid = _studentid
		and st.testid = _testid
		and ccqi.nonscoringreason is not null
		order by 1
	);
end;
$body$
language plpgsql volatile
cost 100;

drop function if exists kelpa_get_number_items_human_scored(bigint, bigint);
create function kelpa_get_number_items_human_scored(_studentid bigint, _testid bigint) returns integer as
$body$
begin
	return (
		select count(distinct case when tv.clusterscoring and tstv.testletid is not null then tstv.testletid else tv.id end)
		from studentstests st
		join test t on st.testid = t.id
		join testsection ts on t.id = ts.testid
		join testsectionstaskvariants tstv on ts.id = tstv.testsectionid
		join taskvariant tv on tstv.taskvariantid = tv.id
		join scoringassignmentstudent sas on st.id = sas.studentstestsid and sas.activeflag
		join ccqscore ccq on sas.id = ccq.scoringassignmentstudentid and ccq.activeflag
		join ccqscoreitem ccqi on ccq.id = ccqi.ccqscoreid and tstv.taskvariantid = ccqi.taskvariantid and ccqi.activeflag
		where st.activeflag
		and st.studentid = _studentid
		and st.testid = _testid
	);
end;
$body$
language plpgsql volatile
cost 100;

drop function if exists kelpa_get_sc_codes_for_test(bigint, bigint, bigint);
create function kelpa_get_sc_codes_for_test(_stateid bigint, _studentid bigint, _testid bigint) returns setof text as
$body$
begin
	return query (
		select sc.ksdecode::text
		from studentstests st
		join studentspecialcircumstance ssc on st.id = ssc.studenttestid and ssc.activeflag
		join specialcircumstance sc on ssc.specialcircumstanceid = sc.id and sc.activeflag
		join statespecialcircumstance statesc on sc.id = statesc.specialcircumstanceid and statesc.stateid = _stateid
		join category c
			on ssc.status = c.id
			and case when statesc.requireconfirmation
				then c.categorycode = 'APPROVED'
				else true end
		where st.activeflag
		and st.studentid = _studentid
		and st.testid = _testid
	);
end;
$body$
language plpgsql volatile
cost 100;