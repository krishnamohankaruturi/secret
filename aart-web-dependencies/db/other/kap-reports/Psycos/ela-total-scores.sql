-- ELA Total Scores
\COPY (
	select s.id as "Student DB ID",
		s.statestudentidentifier as "State Student Identifier",
		otd.districtname as "District", otd.schoolname as "School",
		gc.abbreviatedname as "Grade", ca.abbreviatedname as "Content Area",
		sr.externaltest1id as "Test ID 1", sr.externaltest2id as "Test ID 2", sr.externaltest3id as "Test ID 3", sr.externaltest4id as "Test ID 4",
		sr.performancetestexternalid as "Performance Test ID",
		sr.incompletestatus::text as "Incomplete Status",
		sr.rawscore as "Raw Score", sr.mdptscore as "MDPT Score", sr.scalescore as "Scale Score",
		sr.standarderror as "Standard Error", sr.combinedlevel as "Combined Level"
	from student s
	join studentreport sr on s.id = sr.studentid
	join organizationtreedetail otd on sr.attendanceschoolid = otd.schoolid and sr.districtid = otd.districtid and sr.stateid = otd.stateid
	join contentarea ca on sr.contentareaid = ca.id
	join gradecourse gc on sr.gradeid = gc.id
	where sr.schoolyear = 2016
	and ca.abbreviatedname = 'ELA'
	order by ca.abbreviatedname, otd.districtname, otd.schoolname, gc.abbreviatedname::integer, s.statestudentidentifier
) TO 'ela-totalscores.csv' DELIMITER ',' CSV HEADER;