\COPY (
	select * from (
		select otd.districtname as "District", otd.schoolname as "School",
			gc.abbreviatedname as "Grade", ca.abbreviatedname as "Content Area",
			rms.studentcount as "Number of Students", rms.subscoredefinitionname as "Subscore Definition Name",
			rms.rating as "Rating"
		from reportsmedianscore rms
		join organizationtreedetail otd on rms.organizationid = otd.schoolid and rms.organizationtypeid = (select id from organizationtype where typecode = 'SCH')
		join contentarea ca on rms.contentareaid = ca.id
		join gradecourse gc on rms.gradeid = gc.id
		where rms.schoolyear = 2016
		and ca.abbreviatedname in ('ELA', 'M')
		and rms.rating is not null
		union
		select otd.districtname as "District", null as "School",
			gc.abbreviatedname as "Grade", ca.abbreviatedname as "Content Area",
			rms.studentcount as "Number of Students", rms.subscoredefinitionname as "Subscore Definition Name",
			rms.rating as "Rating"
		from reportsmedianscore rms
		join organizationtreedetail otd on rms.organizationid = otd.districtid and rms.organizationtypeid = (select id from organizationtype where typecode = 'DT')
		join contentarea ca on rms.contentareaid = ca.id
		join gradecourse gc on rms.gradeid = gc.id
		where rms.schoolyear = 2016
		and ca.abbreviatedname in ('ELA', 'M')
		and rms.rating is not null
	) as school_and_district_subscores
	order by "Content Area", "District", "Grade"::integer, "School" nulls first, "Subscore Definition Name"
) TO 'school-district-subscore-aggregation.csv' DELIMITER ',' CSV HEADER;