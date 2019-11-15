--ddl/659.sql

-- F475, changed function to provide a bit more use instead of being specific to KS
-- the caller can now select the columns they need off of the result, instead of the ksdecode just being returned

drop function if exists kelpa_get_sc_codes_for_test(bigint, bigint, bigint);
drop function if exists get_sc_codes_for_test(bigint, bigint);

create function get_sc_codes_for_test(_studentid bigint, _testid bigint) returns setof public.specialcircumstance as
$body$
begin
	-- returns the SC codes that currently apply to the student's test
	-- does NOT return inactive records or SC codes that have not been approved (if approval is necessary)
	return query (
		select sc.*
		from student s
		join studentstests st on s.id = st.studentid and st.testid = _testid and st.activeflag
		join studentspecialcircumstance ssc on st.id = ssc.studenttestid and ssc.activeflag
		join specialcircumstance sc on ssc.specialcircumstanceid = sc.id and sc.activeflag
		join statespecialcircumstance statesc on sc.id = statesc.specialcircumstanceid and statesc.stateid = s.stateid
		join category c
			on ssc.status = c.id
			and case when statesc.requireconfirmation
				then c.categorycode = 'APPROVED'
				else true end
		where s.activeflag
		and s.id = _studentid
	);
end;
$body$
language plpgsql volatile
cost 100;


--F457 add district name to operationalreportdetails and externalstudentreports
ALTER TABLE externalstudentreports  ADD districtname VARCHAR(100);
ALTER TABLE organizationreportdetails ADD districtname VARCHAR(100);