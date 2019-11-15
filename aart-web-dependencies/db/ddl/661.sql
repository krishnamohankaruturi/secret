--ddl/661.sql

-- function for DE15817 (F475)
drop function if exists kelpa_get_number_items_human_scored(bigint, bigint);
drop function if exists kelpa_get_number_items_human_scored(bigint);
create function kelpa_get_number_items_human_scored(_testid bigint) returns integer as
$body$
begin
	return (
		select count(
			distinct case
			when tv.clusterscoring and tstv.testletid is not null
				then ('testletid-' || tstv.testletid)
			else ('taskvariantid-' || tv.id)
			end
		)
		from test t
		join testsection ts on t.id = ts.testid
		join testsectionstaskvariants tstv on ts.id = tstv.testsectionid
		join taskvariant tv on tstv.taskvariantid = tv.id
		where t.id = _testid
		and not tv.scoringneeded
	);
end;
$body$
language plpgsql volatile
cost 100;