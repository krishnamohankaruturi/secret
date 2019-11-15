-- ddl/607.sql
create or replace function repopulateActiveNos()
returns void as $BODY$
declare
	missing_user_rec record;
	user_rec record;
	userorgs_rec record;
	activation_no varchar;
begin
	RAISE NOTICE 'Populating missing user activation numbers';

	for user_rec in select distinct id from aartuser where id in (select distinct uo.aartuserid from usersorganizations uo, 
	userorganizationsgroups uog where uo.id = uog.userorganizationid 
		and uog.activationno is null)
	order by id asc loop 
		RAISE NOTICE 'processing user : % ', user_rec.id;
		select '' into activation_no;
		for userorgs_rec in select uog.id from usersorganizations uo, userorganizationsgroups uog where uo.id = uog.userorganizationid and
		uo.aartuserid = user_rec.id and uog.activationno is null loop
			raise notice 'Missing activation no for % with uog : %', user_rec.id, userorgs_rec;
			select distinct activationno from usersorganizations uo, userorganizationsgroups uog where uo.id = uog.userorganizationid and
				uo.aartuserid = user_rec.id and uog.activationno is not null into activation_no;
			update userorganizationsgroups set activationno = case 
				when activation_no is null then (user_rec.id::text || '-' || userorgs_rec.id::text) 
				else activation_no end, 
			modifieddate=now(), 
			modifieduser= (select id from aartuser where username='cetesysadmin')
			where id = userorgs_rec.id;
		end loop;
	end loop;
end;
$BODY$ language plpgsql;
