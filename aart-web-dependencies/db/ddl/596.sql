-- ddl/596.sql

drop index if exists idx_StudentProfileItemAttributeValue_assessmentprogramid;
drop INDEX if exists idx_spiav_studentid_assessmentprogramid;

CREATE INDEX idx_spiav_studentid_assessmentprogramid
  ON StudentProfileItemAttributeValue USING btree (studentid, assessmentprogramid);

create or replace function populatestudentpnpassessmentprogram()
returns void as $$
declare
	missing_student_rec record;
	student_rec record;
	student_schoolyear bigint;
	assessmentprg_id bigint;
	dateandtime timestamp;
	modified_user bigint;
begin
	RAISE NOTICE 'Populating missing user activation numbers';
	select now() into dateandtime;
	select id from aartuser where username  = 'cetesysadmin' into modified_user;
	for student_rec in select distinct studentid from StudentProfileItemAttributeValue where assessmentprogramid is null
	order by studentid asc loop 
		--RAISE NOTICE 'processing student : % ', student_rec.studentid;
		select null into student_schoolyear;
		select e.currentschoolyear from enrollment e where e.studentid=student_rec.studentid limit 1 into student_schoolyear;
		--RAISE NOTICE 'student current school year: % ', student_schoolyear;
		select getstudentassessmentprogram(student_rec.studentid, student_schoolyear) into assessmentprg_id;
		RAISE NOTICE 'updating student %, current school year: % with ap id : %', student_rec.studentid, student_schoolyear, assessmentprg_id;
		update StudentProfileItemAttributeValue spv1 set assessmentprogramid = assessmentprg_id,
		modifieddate = dateandtime,
		modifieduser = modified_user
		where 
		spv1.studentid = student_rec.studentid and spv1.assessmentprogramid is null
		and spv1.profileitemattributenameattributecontainerid not in (
			select spv2.profileitemattributenameattributecontainerid from StudentProfileItemAttributeValue spv2 where 
			spv2.studentid = spv1.studentid and spv2.assessmentprogramid is not null 
		);
	end loop;
	update StudentProfileItemAttributeValue set activeflag = false, modifieddate = dateandtime,
		modifieduser = (modified_user)
		where assessmentprogramid is null;
end;
$$language plpgsql;

create or replace function repopulateactnoexpdate()
returns void as $$
declare
	missing_user_rec record;
	user_rec record;
	userorgs_rec record;
	activation_date timestamp;
begin
	RAISE NOTICE 'Populating missing user activation expiration dates';

	for user_rec in select distinct id from aartuser where
	id in (select distinct uo.aartuserid from usersorganizations uo, userorganizationsgroups uog where uo.id = uog.userorganizationid 
		and uog.activationnoexpirationdate is null)
	order by id asc loop 
		RAISE NOTICE 'processing user : % ', user_rec.id;
		select null into activation_date;
		for userorgs_rec in select uog.id from usersorganizations uo, userorganizationsgroups uog where uo.id = uog.userorganizationid and
		uo.aartuserid = user_rec.id and uog.activationnoexpirationdate is null loop
			raise notice 'Missing activationnoexpirationdate for % with uog : %', user_rec.id, userorgs_rec;
			select  max(activationnoexpirationdate) from usersorganizations uo, userorganizationsgroups uog where uo.id = uog.userorganizationid and
				uo.aartuserid = user_rec.id and uog.activationnoexpirationdate is not null into activation_date;
			-- Update all user org groups which has at least one activation no per user
			if activation_date is not null then
				raise notice 'Updating with : %', activation_date;
				update userorganizationsgroups set activationnoexpirationdate = activation_date, modifieddate=now(), modifieduser = (select id from aartuser where username='cetesysadmin')
				where id = userorgs_rec.id;
			elsif activation_date is null then 
				raise notice 'Got % and updating with : %', activation_date, (now() + interval '-1' day);
				--Update all active users who got no activationnoexpirationdate and active with a combination of user id and org id.
				update userorganizationsgroups set activationnoexpirationdate = now() + interval '-1' day, modifieddate=now(), modifieduser = (select id from aartuser where username='cetesysadmin')
				where id = userorgs_rec.id and activationnoexpirationdate is null;
			end if;
		end loop;
	end loop;
end;
$$language plpgsql;


