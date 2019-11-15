\COPY (
	select * from (
		select otd.districtname as "District", otd.schoolname as "School",
			gc.abbreviatedname as "Grade", ca.abbreviatedname as "Content Area",
			rms.studentcount as "Number of Students", rms.score as "Median Score",
			rms.standarderror as "Standard Error"
		from reportsmedianscore rms
		join organizationtreedetail otd on rms.organizationid = otd.schoolid and rms.organizationtypeid = (select id from organizationtype where typecode = 'SCH')
		join contentarea ca on rms.contentareaid = ca.id
		join gradecourse gc on rms.gradeid = gc.id
		where rms.schoolyear = 2016
		and ca.abbreviatedname in ('ELA', 'M')
		and rms.studentcount is not null
		union
		select otd.districtname as "District", null as "School",
			gc.abbreviatedname as "Grade", ca.abbreviatedname as "Content Area",
			rms.studentcount as "Number of Students", rms.score as "Median Score",
			rms.standarderror as "Standard Error"
		from reportsmedianscore rms
		join organizationtreedetail otd on rms.organizationid = otd.districtid and rms.organizationtypeid = (select id from organizationtype where typecode = 'DT')
		join contentarea ca on rms.contentareaid = ca.id
		join gradecourse gc on rms.gradeid = gc.id
		where rms.schoolyear = 2016
		and ca.abbreviatedname in ('ELA', 'M')
		and rms.studentcount is not null
	) as school_and_district_medians
	order by "Content Area", "District", "Grade"::integer, "School" nulls first
) TO 'school-district-aggregation.csv' DELIMITER ',' CSV HEADER;