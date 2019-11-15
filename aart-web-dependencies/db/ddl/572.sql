-- 572.sql

alter table userassessmentprogram add column userorganizationsgroupsid bigint;
ALTER TABLE userassessmentprogram ADD CONSTRAINT userorganizationsgroupsfk 
	FOREIGN KEY (userorganizationsgroupsid) REFERENCES userorganizationsgroups (id);

CREATE INDEX idx_comb_csap ON userassessmentprogram
  USING btree (aartuserid, assessmentprogramid, userorganizationsgroupsid);

create or replace function populateuserassessmentprograms()
returns void as $$
declare
	userorggroup_rec record;
	userassmnt_rec record;
	state_rec record;
	user_rec record;
	userorg_rec record;
	assmnt_value userassessmentprogram.assessmentprogramid%TYPE;
	final_assmnt_value userassessmentprogram.assessmentprogramid%TYPE;
	org_value usersorganizations.organizationid%TYPE;
begin
	RAISE NOTICE 'Populating userassessmentprogram...';
	for user_rec in select id from aartuser where username ='cetesysadmin' loop end loop;
	for userorggroup_rec in select uog.id as usrorggrpid,  uog.userorganizationid userorganizationid
	from userorganizationsgroups uog  where uog.activeflag is true
	order by uog.id
	loop
		for userorg_rec in select uo.organizationid as organizationid, uo.aartuserid as aartuserid from usersorganizations uo where uo.activeflag is true 
		and uo.id = userorggroup_rec.userorganizationid loop end loop;

		-- get state id for the 
		for state_rec in select id from organization_parent(userorg_rec.organizationid) orgpa where organizationtypeid = 2 loop end loop;
		org_value = case when state_rec.id is null then userorg_rec.organizationid else state_rec.id end;

		for userassmnt_rec in select ua.assessmentprogramid as assessmentprogramid from orgassessmentprogram oa inner join userassessmentprogram ua 
			on ua.assessmentprogramid = oa.assessmentprogramid and ua.activeflag is true and ua.userorganizationsgroupsid  is null
			where oa.organizationid = org_value 
			and ua.aartuserid = userorg_rec.aartuserid 
		loop 
			raise notice 'inserting.... % % % % % % % %', userorg_rec.aartuserid, userassmnt_rec.assessmentprogramid , true, 
			now(), user_rec.id, now(), user_rec.id ,userorggroup_rec.usrorggrpid;
			execute 'insert into userassessmentprogram 
			(aartuserid, assessmentprogramid, activeflag, createddate, createduser, modifieddate, modifieduser, userorganizationsgroupsid)
			select $1, $2, $3, $4, $5, $6, $7, $8 where not exists (
			select id from userassessmentprogram where aartuserid = $1 and assessmentprogramid = $2 and userorganizationsgroupsid = $8)' 
			using userorg_rec.aartuserid, userassmnt_rec.assessmentprogramid , true, 
			now(), user_rec.id, now(), user_rec.id ,userorggroup_rec.usrorggrpid;
		end loop;
	end loop;
	for userassmnt_rec in select distinct aartuserid from userassessmentprogram where userorganizationsgroupsid  is not null and activeflag is true
	loop
		update userassessmentprogram set isdefault = true where  userorganizationsgroupsid  is not null and activeflag is true
		and  aartuserid = userassmnt_rec.aartuserid and id = 
		(select max(id) from userassessmentprogram where userorganizationsgroupsid  is not null and activeflag is true
		and  aartuserid = userassmnt_rec.aartuserid);
		update userassessmentprogram set isdefault = false where  userorganizationsgroupsid  is not null and activeflag is true
		and  aartuserid = userassmnt_rec.aartuserid and id != 
		(select max(id) from userassessmentprogram where userorganizationsgroupsid  is not null and activeflag is true
		and  aartuserid = userassmnt_rec.aartuserid);
	end loop;
end;
$$language plpgsql;

create or replace function resetdefaultuserassessmentprogram()
returns void as $$
declare
	missing_user_rec record;
	
	userassmnt_recs record;
	state_rec record;
	sys_user_rec record;
	user_rec record;
	org_value usersorganizations.organizationid%TYPE;
	updated BOOLEAN;
	inserted BOOLEAN;
	recordlength BIGINT;
	stateid BIGINT;
	update_rec_id BIGINT;
	higher_role BIGINT;
	updateddate timestamp;
	statedispidentifier varchar;
	userorgs_rec record;
begin
	RAISE NOTICE 'Populating missing user assessment programs';

	for missing_user_rec in select distinct uo.aartuserid from usersorganizations uo, userorganizationsgroups uog where uo.id = uog.userorganizationid and
	uo.aartuserid  in (
	select id from aartuser where id not in 
	(
	select distinct aartuserid from userassessmentprogram where userorganizationsgroupsid is not null
	) and activeflag is true
	)  and aartuserid not in  (select id from aartuser where username = 'cetesysadmin') loop 
		raise notice '-poulating for user : %', missing_user_rec.aartuserid;
		for userorgs_rec in select uo.organizationid as organizationid, uog.id as uogid 
		from usersorganizations uo, userorganizationsgroups uog where uo.id = uog.userorganizationid and
		uo.aartuserid = missing_user_rec.aartuserid loop
			inserted = false;
			raise notice '--poulating for user org : % groupid : %', userorgs_rec.organizationid, userorgs_rec.uogid;
			if inserted is false then 
				select count(distinct uog.id) from usersorganizations  uo 
				inner join userorganizationsgroups uog on uo.id = uog.userorganizationid
				inner join orgassessmentprogram oap on oap.organizationid = (
					select id from organization_parent(uo.organizationid) orgpa where orgpa.organizationtypeid = 2)
					or
					oap.organizationid = (select id from organization where organizationtypeid  = 2 and id = uo.organizationid)
				inner join assessmentprogram ap on ap.id = oap.assessmentprogramid and ap.abbreviatedname = 'PLYGRND'
				where uo.aartuserid = missing_user_rec.aartuserid  
				and uog.id = userorgs_rec.uogid
				and uo.organizationid = userorgs_rec.organizationid
				into recordlength;
				if recordlength >= 1 then
					raise notice 'Got PLYGRND as one of the assessment';
					insert into userassessmentprogram (aartuserid, assessmentprogramid, activeflag, isdefault, createddate, 
					createduser, modifieddate, modifieduser, userorganizationsgroupsid)
					select distinct missing_user_rec.aartuserid, id, true, false, now(), 12, now(), 12,userorgs_rec.uogid
					from assessmentprogram where abbreviatedname = 'PLYGRND';
					inserted = true;
				end if;
			end if;
			if inserted is false then 
				select count(distinct uog.id) from usersorganizations  uo 
				inner join userorganizationsgroups uog on uo.id = uog.userorganizationid
				inner join orgassessmentprogram oap on oap.organizationid = (
					select id from organization_parent(uo.organizationid) orgpa where orgpa.organizationtypeid = 2)
					or
					oap.organizationid = (select id from organization where organizationtypeid  = 2 and id = uo.organizationid)
				inner join assessmentprogram ap on ap.id = oap.assessmentprogramid and ap.abbreviatedname = 'ARMM'
				where uo.aartuserid = missing_user_rec.aartuserid  
				and uog.id = userorgs_rec.uogid
				and uo.organizationid = userorgs_rec.organizationid
				into recordlength;
				if recordlength >= 1 then
					raise notice 'Got ARMM as one of the assessment';
					insert into userassessmentprogram (aartuserid, assessmentprogramid, activeflag, isdefault, createddate, 
					createduser, modifieddate, modifieduser, userorganizationsgroupsid)
					select distinct missing_user_rec.aartuserid,  id, true, false, now(), 12, now(), 12,userorgs_rec.uogid
					from assessmentprogram where abbreviatedname = 'ARMM';
					inserted = true;
				end if;
			end if;
			if inserted is false then 
				select count(distinct uog.id) from usersorganizations  uo 
				inner join userorganizationsgroups uog on uo.id = uog.userorganizationid
				inner join orgassessmentprogram oap on oap.organizationid = (
					select id from organization_parent(uo.organizationid) orgpa where orgpa.organizationtypeid = 2)
					or
					oap.organizationid = (select id from organization where organizationtypeid  = 2 and id = uo.organizationid)
				inner join assessmentprogram ap on ap.id = oap.assessmentprogramid and ap.abbreviatedname = 'AMP'
				where uo.aartuserid = missing_user_rec.aartuserid  
				and uog.id = userorgs_rec.uogid
				and uo.organizationid = userorgs_rec.organizationid
				into recordlength;
				if recordlength >= 1 then
					raise notice 'Got AMP as one of the assessment';
					insert into userassessmentprogram (aartuserid, assessmentprogramid, activeflag, isdefault, createddate, 
					createduser, modifieddate, modifieduser, userorganizationsgroupsid)
					select distinct missing_user_rec.aartuserid,  id, true, false, now(), 12, now(), 12,userorgs_rec.uogid
					from assessmentprogram where abbreviatedname = 'AMP';
					inserted = true;
				end if;
			end if;
			if inserted is false then 
				select count(distinct uog.id) from usersorganizations  uo 
				inner join userorganizationsgroups uog on uo.id = uog.userorganizationid
				inner join orgassessmentprogram oap on oap.organizationid = (
					select id from organization_parent(uo.organizationid) orgpa where orgpa.organizationtypeid = 2)
					or
					oap.organizationid = (select id from organization where organizationtypeid  = 2 and id = uo.organizationid)
				inner join assessmentprogram ap on ap.id = oap.assessmentprogramid and ap.abbreviatedname = 'DLM'
				where uo.aartuserid = missing_user_rec.aartuserid  
				and uog.id = userorgs_rec.uogid
				and uo.organizationid = userorgs_rec.organizationid
				into recordlength;
				if recordlength >= 1 then
					raise notice 'Got DLM as one of the assessment';
					insert into userassessmentprogram (aartuserid, assessmentprogramid, activeflag, isdefault, createddate, 
					createduser, modifieddate, modifieduser, userorganizationsgroupsid)
					select distinct missing_user_rec.aartuserid,  id, true, false, now(), 12, now(), 12,userorgs_rec.uogid
					from assessmentprogram where abbreviatedname = 'DLM';
					inserted = true;
				end if;
			end if;
			if inserted is false then 
				select count(distinct ap.id) from usersorganizations  uo 
				inner join userorganizationsgroups uog on uo.id = uog.userorganizationid
				inner join orgassessmentprogram oap on oap.organizationid = (
					select id from organization_parent(uo.organizationid) orgpa where orgpa.organizationtypeid = 2)
					or
					oap.organizationid = (select id from organization where organizationtypeid  = 2 and id = uo.organizationid)
				inner join assessmentprogram ap on ap.id = oap.assessmentprogramid and ap.abbreviatedname = 'KAP'
				where uo.aartuserid = missing_user_rec.aartuserid  
				and uog.id = userorgs_rec.uogid
				and uo.organizationid = userorgs_rec.organizationid
				into recordlength;
				if recordlength >= 1 then
					raise notice 'Got KAP as one of the assessment';
					insert into userassessmentprogram (aartuserid, assessmentprogramid, activeflag, isdefault, createddate, 
					createduser, modifieddate, modifieduser, userorganizationsgroupsid)
					select distinct missing_user_rec.aartuserid,  id, true, false, now(), 12, now(), 12,userorgs_rec.uogid
					from assessmentprogram where abbreviatedname = 'KAP';
					inserted = true;
				end if;
			end if;
			if inserted is false then 
				recordlength = null;
				select distinct ap.id from usersorganizations  uo 
				inner join userorganizationsgroups uog on uo.id = uog.userorganizationid
				inner join orgassessmentprogram oap on oap.organizationid = (
					select id from organization_parent(uo.organizationid) orgpa where orgpa.organizationtypeid = 2)
					or
					oap.organizationid = (select id from organization where organizationtypeid  = 2 and id = uo.organizationid)
				inner join assessmentprogram ap on ap.id = oap.assessmentprogramid --and ap.abbreviatedname = 'PLYGRND'
				where uo.aartuserid = missing_user_rec.aartuserid  
				and uog.id = userorgs_rec.uogid
				and uo.organizationid = userorgs_rec.organizationid limit 1
				into recordlength;
				if recordlength != null then
					raise notice 'Got as one of the assessment';
					insert into userassessmentprogram (aartuserid, assessmentprogramid, activeflag, isdefault, createddate, 
					createduser, modifieddate, modifieduser, userorganizationsgroupsid)
					select distinct missing_user_rec.aartuserid,  id, true, false, now(), 12, now(), 12,userorgs_rec.uogid
					from assessmentprogram where id = recordlength;
					inserted = true;
				end if;
			end if;
		end loop;
	end loop;
	
	RAISE NOTICE 'Populating default userassessmentprogram...';
	for sys_user_rec in select id from aartuser where username ='cetesysadmin' loop end loop;
	updateddate = now();

	for user_rec in select distinct aartuserid from userassessmentprogram where --aartuserid in (45986,35789,75033) and
		activeflag is true and userorganizationsgroupsid  is not null 
	loop
		select count(1) from userassessmentprogram uap
			inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid
			inner join usersorganizations uo on uo.id = uog.userorganizationid
			where uap.aartuserid = user_rec.aartuserid into recordlength;
			
		if recordlength = 1 then
			raise notice 'got one record and updating it to default';
			execute 'update userassessmentprogram set isdefault = true, modifieddate= $2 where aartuserid = $1' using sys_user_rec.id, updateddate;
		elsif recordlength > 1 then 
			raise notice 'got more than one record ';
			updated = false;
			
			for userassmnt_recs in select uap.id as id, uap.assessmentprogramid as assessmentprogramid, 
			uo.organizationid as organizationid from userassessmentprogram uap
			inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid 
			inner join usersorganizations uo on uo.id = uog.userorganizationid
			where uap.aartuserid = user_rec.aartuserid and uap.userorganizationsgroupsid is not null
			loop 
				
				for state_rec in select id, organizationname, displayidentifier, organizationtypeid from 
				organization_parent(userassmnt_recs.organizationid) orgpa where organizationtypeid = 2 
				union select id, organizationname, displayidentifier, organizationtypeid from organization 
				where id = userassmnt_recs.organizationid and organizationtypeid = 2 loop end loop;

				raise notice 'state name %', state_rec.organizationname;
		 
				org_value = userassmnt_recs.organizationid ;
				raise notice 'org_value %', org_value;
				raise notice 'user id %', user_rec.aartuserid;

				-- Process KS - KAP 
				-- Get higher role id and get corresponding assessment programid 
				select min(id) from groups where id in (
						select distinct uog.groupid from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						left join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid
						where otd.statedisplayidentifier = 'KS' and uap.aartuserid = user_rec.aartuserid -- -- 85092;58544
						and uap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='KAP')
						--and uo.organizationid = 1312 org_value
						) into higher_role;
				raise notice 'higher_role : % ', higher_role;
				if higher_role is not null then 
					select uap.id from userassessmentprogram uap
					inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
					inner join usersorganizations uo on uo.id = uog.userorganizationid
					inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
					inner join groups g on g.id = uog.groupid and g.id = higher_role
					where otd.statedisplayidentifier = 'KS' and uap.aartuserid = user_rec.aartuserid -- 85092;58544
					and uap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='KAP')
					and uo.organizationid = org_value into update_rec_id;
					
					if update_rec_id  is not null then 
						raise notice 'found for KA-KAP : %', update_rec_id;
						if updated is false then 
							update userassessmentprogram set isdefault = true, modifieddate= updateddate,
							modifieduser = sys_user_rec.id where id = update_rec_id;
		
							update userassessmentprogram set isdefault = false, modifieddate= updateddate,
							modifieduser = sys_user_rec.id where id !=update_rec_id
							and aartuserid = user_rec.aartuserid;

							update userorganizationsgroups set isdefault = false, modifieddate= updateddate,
							modifieduser = sys_user_rec.id where id in 
							(select distinct userorganizationsgroupsid from userassessmentprogram where 
							id != update_rec_id
							and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null); 
							
							update userorganizationsgroups set isdefault = true, modifieddate= updateddate,
							modifieduser = sys_user_rec.id where id = 
							(select distinct userorganizationsgroupsid from userassessmentprogram where 
							id = update_rec_id
							and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null);
							
							update usersorganizations set isdefault = false, modifieddate= updateddate,
							modifieduser = sys_user_rec.id where id in 
							(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
							uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault != true
							and uap.userorganizationsgroupsid is not null );
							
							update usersorganizations set isdefault = true, modifieddate= updateddate,
							modifieduser = sys_user_rec.id where id = 
							(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
							uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault = true and uog.isdefault = true
							and uap.userorganizationsgroupsid is not null);
							
							updated = true;
							raise notice 'updated for KA-KAP : % - %', update_rec_id,user_rec.aartuserid;
						end if;
					else 
						raise notice 'not found KS-KAP';  
					end if;
				end if;
				-- start KS-DLM
				if updated is false then 
					select min(id) from groups where id in (
						select distinct uog.groupid from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						inner join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid
						where otd.statedisplayidentifier = 'KS' and uap.aartuserid = user_rec.aartuserid -- 85673 --
						and uap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM')
						--and uo.organizationid = 1312 org_value
						) into higher_role;
					raise notice 'higher_role : % ', higher_role;
					if higher_role is not null then 
						select uap.id from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						inner join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid and g.id = higher_role
						where otd.statedisplayidentifier = 'KS' and uap.aartuserid = user_rec.aartuserid 
						and uap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM')
						and uo.organizationid = org_value into update_rec_id;
						
						if update_rec_id  is not null then 
							raise notice 'found for KA-DLM : %', update_rec_id;
							if updated is false then 
								update userassessmentprogram set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = update_rec_id;
			
								update userassessmentprogram set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id !=update_rec_id
								and aartuserid = user_rec.aartuserid;

								update userorganizationsgroups set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id in 
								(select distinct userorganizationsgroupsid from userassessmentprogram where 
								id != update_rec_id
								and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null); 
								
								update userorganizationsgroups set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = 
								(select distinct userorganizationsgroupsid from userassessmentprogram where 
								id = update_rec_id
								and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null);
								
								update usersorganizations set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id in 
								(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
								uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault != true
								and uap.userorganizationsgroupsid is not null );
								
								update usersorganizations set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = 
								(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
								uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault = true and uog.isdefault = true
								and uap.userorganizationsgroupsid is not null);
								updated = true;
								raise notice 'updated for KA-DLM : %', update_rec_id;
							end if;
						else 
							raise notice 'not found KS-DLM';  
						end if;
					end if;
				end if; -- KS- DLM

				if updated is false then 
					select min(id) from groups where id in (
						select distinct uog.groupid from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						inner join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid
						where otd.statedisplayidentifier = 'KS' and uap.aartuserid = user_rec.aartuserid -- 85673 --
						and uap.assessmentprogramid = uap.assessmentprogramid
						--and uo.organizationid = 1312 org_value
						) into higher_role;
					raise notice 'higher_role : % ', higher_role;
					if higher_role is not null then 
						select uap.id from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						inner join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid and g.id = higher_role
						where otd.statedisplayidentifier = 'KS' and uap.aartuserid = user_rec.aartuserid 
						and uap.assessmentprogramid = uap.assessmentprogramid
						and uo.organizationid = org_value into update_rec_id;
						
						if update_rec_id  is not null then 
							raise notice 'found for KA-DLM : %', update_rec_id;
							if updated is false then 
								update userassessmentprogram set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = update_rec_id;
			
								update userassessmentprogram set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id !=update_rec_id
								and aartuserid = user_rec.aartuserid;
								
								update userorganizationsgroups set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id in 
								(select distinct userorganizationsgroupsid from userassessmentprogram where 
								id != update_rec_id
								and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null); 
								
								update userorganizationsgroups set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = 
								(select distinct userorganizationsgroupsid from userassessmentprogram where 
								id = update_rec_id
								and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null);
								
								update usersorganizations set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id in 
								(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
								uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault != true
								and uap.userorganizationsgroupsid is not null );
								
								update usersorganizations set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = 
								(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
								uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault = true and uog.isdefault = true
								and uap.userorganizationsgroupsid is not null);
								updated = true;
								raise notice 'updated for KA-OTHER : %', update_rec_id;
							end if;
						else 
							raise notice 'not found KS-OTHER';  
						end if;
					end if;
				end if; -- KS- OTHER

				-- start AK-AMP
				if updated is false then 
					select min(id) from groups where id in (
						select distinct uog.groupid from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						inner join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid
						where otd.statedisplayidentifier = 'AK' and uap.aartuserid = user_rec.aartuserid -- 85673 --
						and uap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='AMP')
						--and uo.organizationid = 1312 org_value
						) into higher_role;
					raise notice 'higher_role : % ', higher_role;
					if higher_role is not null then 
						select uap.id from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						inner join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid and g.id = higher_role
						where otd.statedisplayidentifier = 'AK' and uap.aartuserid = user_rec.aartuserid 
						and uap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='AMP')
						and uo.organizationid = org_value into update_rec_id;
						
						if update_rec_id  is not null then 
							raise notice 'found for AK-AMP : %', update_rec_id;
							if updated is false then 
								update userassessmentprogram set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = update_rec_id;
			
								update userassessmentprogram set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id !=update_rec_id
								and aartuserid = user_rec.aartuserid;
								
								update userorganizationsgroups set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id in 
								(select distinct userorganizationsgroupsid from userassessmentprogram where 
								id != update_rec_id
								and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null); 
								
								update userorganizationsgroups set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = 
								(select distinct userorganizationsgroupsid from userassessmentprogram where 
								id = update_rec_id
								and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null);
								
								update usersorganizations set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id in 
								(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
								uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault != true
								and uap.userorganizationsgroupsid is not null );
								
								update usersorganizations set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = 
								(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
								uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault = true and uog.isdefault = true
								and uap.userorganizationsgroupsid is not null);
								
								updated = true;
								raise notice 'updated for KA-KAP : %', update_rec_id;
							end if;
						else 
							raise notice 'not found AK-AMP';  
						end if;
					end if;
				end if; -- AK- AMP

				-- AK - DLM
				if updated is false then 
					select min(id) from groups where id in (
						select distinct uog.groupid from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						inner join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid
						where otd.statedisplayidentifier = 'AK' and uap.aartuserid = user_rec.aartuserid -- 85673 --85092;58544
						and uap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM')
						--and uo.organizationid = 1312 org_value
						) into higher_role;
					raise notice 'higher_role : % ', higher_role;
					if higher_role is not null then 
						select uap.id from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						inner join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid and g.id = higher_role
						where otd.statedisplayidentifier = 'AK' and uap.aartuserid = user_rec.aartuserid 
						and uap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM')
						and uo.organizationid = org_value into update_rec_id;
						
						if update_rec_id  is not null then 
							raise notice 'found for AK-DLM : %', update_rec_id;
							if updated is false then 
								update userassessmentprogram set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = update_rec_id;
			
								update userassessmentprogram set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id !=update_rec_id
								and aartuserid = user_rec.aartuserid;
								
								update userorganizationsgroups set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id in 
								(select distinct userorganizationsgroupsid from userassessmentprogram where 
								id != update_rec_id
								and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null); 
								
								update userorganizationsgroups set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = 
								(select distinct userorganizationsgroupsid from userassessmentprogram where 
								id = update_rec_id
								and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null);
								
								update usersorganizations set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id in 
								(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
								uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault != true
								and uap.userorganizationsgroupsid is not null );
								
								update usersorganizations set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = 
								(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
								uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault = true and uog.isdefault = true
								and uap.userorganizationsgroupsid is not null);
								
								updated = true;
								raise notice 'updated for KA-KAP : %', update_rec_id;
							end if;
						else 
							raise notice 'not found AK-DLM';  
						end if;
					end if;
				end if; -- AK- DLM

				-- AK - OTHER
				if updated is false then 
					select min(id) from groups where id in (
						select distinct uog.groupid from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						inner join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid
						where otd.statedisplayidentifier = 'AK' and uap.aartuserid = user_rec.aartuserid -- 85673 --85092;58544
						and uap.assessmentprogramid = uap.assessmentprogramid
						--and uo.organizationid = 1312 org_value
						) into higher_role;
					raise notice 'higher_role : % ', higher_role;
					if higher_role is not null then 
						select uap.id from userassessmentprogram uap
						inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
						inner join usersorganizations uo on uo.id = uog.userorganizationid
						inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
						inner join groups g on g.id = uog.groupid and g.id = higher_role
						where otd.statedisplayidentifier = 'AK' and uap.aartuserid = user_rec.aartuserid 
						and uap.assessmentprogramid = uap.assessmentprogramid
						and uo.organizationid = org_value into update_rec_id;
						
						if update_rec_id  is not null then 
							raise notice 'found for AK-OTHER : %', update_rec_id;
							if updated is false then 
								update userassessmentprogram set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = update_rec_id;
			
								update userassessmentprogram set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id !=update_rec_id
								and aartuserid = user_rec.aartuserid;

								
								update userorganizationsgroups set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id in 
								(select distinct userorganizationsgroupsid from userassessmentprogram where 
								id != update_rec_id
								and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null); 
								
								update userorganizationsgroups set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = 
								(select distinct userorganizationsgroupsid from userassessmentprogram where 
								id = update_rec_id
								and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null);
								
								update usersorganizations set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id in 
								(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
								uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault != true
								and uap.userorganizationsgroupsid is not null );
								
								update usersorganizations set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = 
								(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
								uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault = true and uog.isdefault = true
								and uap.userorganizationsgroupsid is not null);
								updated = true;
								raise notice 'updated for KA-OTHER : %', update_rec_id;
							end if;
						else 
							raise notice 'not found AK-OTHER';  
						end if;
					end if;
				end if; -- AK- DLM

				-- OTHER - DLM
				for statedispidentifier in select distinct displayidentifier from organization where organizationtypeid  = 2 
					and displayidentifier not in ('KS','AK')
				loop
					if updated is false then 
						raise notice 'checking for  % - DLM',statedispidentifier; 
						select min(id) from groups where id in (
							select distinct uog.groupid from userassessmentprogram uap
							inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
							inner join usersorganizations uo on uo.id = uog.userorganizationid
							inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
							inner join groups g on g.id = uog.groupid
							where otd.statedisplayidentifier = statedispidentifier and uap.aartuserid = user_rec.aartuserid -- 85673 --
							and uap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM')
							--and uo.organizationid = 1312 org_value
							) into higher_role;
						raise notice 'higher_role : % ', higher_role;
						if higher_role is not null then 
							select uap.id from userassessmentprogram uap
							inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
							inner join usersorganizations uo on uo.id = uog.userorganizationid
							inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
							inner join groups g on g.id = uog.groupid and g.id = higher_role
							where otd.statedisplayidentifier = statedispidentifier and uap.aartuserid = user_rec.aartuserid 
							and (uap.assessmentprogramid = (select id from assessmentprogram where abbreviatedname='DLM')
							 or uap.assessmentprogramid = uap.assessmentprogramid)
							and uo.organizationid = org_value into update_rec_id;
							
							if update_rec_id  is not null then 
								raise notice 'found for AK-DLM : %', update_rec_id;
								if updated is false then 
									update userassessmentprogram set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = update_rec_id;
				
									update userassessmentprogram set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id !=update_rec_id
									and aartuserid = user_rec.aartuserid;
									
									update userorganizationsgroups set isdefault = false, modifieddate= updateddate,
									modifieduser = sys_user_rec.id where id in 
									(select distinct userorganizationsgroupsid from userassessmentprogram where 
									id != update_rec_id
									and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null); 
									
									update userorganizationsgroups set isdefault = true, modifieddate= updateddate,
									modifieduser = sys_user_rec.id where id = 
									(select distinct userorganizationsgroupsid from userassessmentprogram where 
									id = update_rec_id
									and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null);
									
									update usersorganizations set isdefault = false, modifieddate= updateddate,
									modifieduser = sys_user_rec.id where id in 
									(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
									uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault != true
									and uap.userorganizationsgroupsid is not null );
									
									update usersorganizations set isdefault = true, modifieddate= updateddate,
									modifieduser = sys_user_rec.id where id = 
									(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
									uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault = true and uog.isdefault = true
									and uap.userorganizationsgroupsid is not null);
								
									updated = true;
									raise notice 'updated for KA-% : %', statedispidentifier, update_rec_id;
								end if;
							else 
								raise notice 'not found %-DLM',statedispidentifier;  
							end if;
						end if;
					end if; -- OTHER -DLM

					if updated is false then 
						raise notice 'checking for  % - OTHER',statedispidentifier; 
						select min(id) from groups where id in (
							select distinct uog.groupid from userassessmentprogram uap
							inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
							inner join usersorganizations uo on uo.id = uog.userorganizationid
							inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
							inner join groups g on g.id = uog.groupid
							where otd.statedisplayidentifier = statedispidentifier and uap.aartuserid = user_rec.aartuserid -- 85673 --
							and (uap.assessmentprogramid = uap.assessmentprogramid)
							--and uo.organizationid = 1312 org_value
							) into higher_role;
						raise notice 'higher_role : % ', higher_role;
						if higher_role is not null then 
							select uap.id from userassessmentprogram uap
							inner join userorganizationsgroups uog on uog.id = uap.userorganizationsgroupsid and uap.userorganizationsgroupsid is not null
							inner join usersorganizations uo on uo.id = uog.userorganizationid
							inner join organizationtreedetail otd on (otd.schoolid = uo.organizationid or otd.districtid=uo.organizationid or otd.stateid=uo.organizationid)
							inner join groups g on g.id = uog.groupid and g.id = higher_role
							where otd.statedisplayidentifier = statedispidentifier and uap.aartuserid = user_rec.aartuserid 
							and (uap.assessmentprogramid = uap.assessmentprogramid)
							and uo.organizationid = org_value into update_rec_id;
							
							if update_rec_id  is not null then 
								raise notice 'found for OTHER-OTHER : %', update_rec_id;
								if updated is false then 
									update userassessmentprogram set isdefault = true, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id = update_rec_id;
				
									update userassessmentprogram set isdefault = false, modifieddate= updateddate,
								modifieduser = sys_user_rec.id where id !=update_rec_id
									and aartuserid = user_rec.aartuserid;
									
									update userorganizationsgroups set isdefault = false, modifieddate= updateddate,
									modifieduser = sys_user_rec.id where id in 
									(select distinct userorganizationsgroupsid from userassessmentprogram where 
									id != update_rec_id
									and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null); 
									
									update userorganizationsgroups set isdefault = true, modifieddate= updateddate,
									modifieduser = sys_user_rec.id where id = 
									(select distinct userorganizationsgroupsid from userassessmentprogram where 
									id = update_rec_id
									and aartuserid= user_rec.aartuserid and userorganizationsgroupsid is not null);
									
									update usersorganizations set isdefault = false, modifieddate= updateddate,
									modifieduser = sys_user_rec.id where id in 
									(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
									uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault != true
									and uap.userorganizationsgroupsid is not null );
									
									update usersorganizations set isdefault = true, modifieddate= updateddate,
									modifieduser = sys_user_rec.id where id = 
									(select distinct uog.userorganizationid from userorganizationsgroups uog, userassessmentprogram uap where 
									uog.id = uap.userorganizationsgroupsid and uap.aartuserid  = user_rec.aartuserid and uap.isdefault = true and uog.isdefault = true
									and uap.userorganizationsgroupsid is not null);
									
									updated = true;
									raise notice 'updated for OTHER-% : %', statedispidentifier, update_rec_id;
								end if;
							else 
								raise notice 'not found %-OTHER',statedispidentifier;  
							end if;
						end if;
					end if; -- OTHER -DLM
					
					exit when updated is true;
				end loop;	
				exit when updated is true;
				-- exit when 1=1;
			end loop;
		end if;

	end loop;
end;
$$language plpgsql;

