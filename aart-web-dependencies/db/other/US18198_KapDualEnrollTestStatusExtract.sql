
DROP TABLE IF EXISTS tmp_students;

CREATE TEMPORARY TABLE tmp_students( statestudentIdentifier TEXT);

\COPY tmp_students FROM 'kap_students.csv' DELIMITER ',' CSV HEADER ;
 
CREATE
TEMPORARY TABLE tmp_student_text_extract (State TEXT,District TEXT,School TEXT,SchoolIdentifier TEXT,Grade TEXT,
				StudentLastName TEXT,StudentFirstName TEXT,	StudentMiddleName TEXT,StateStudentIdentifier TEXT,LocalStudentIdentifier TEXT,Enrollmentid TEXT,
				EnrollmentStatus TEXT, Subject TEXT,TestSessionName TEXT, Testsessionid TEXT, 
				Testid TEXT, TestCollectionId TEXT, TestStatus TEXT, TestActiveflag TEXT, Stage TEXT, TotalItems TEXT,TotalOmittedItems TEXT, TestStartDate TEXT, TestEndDate TEXT, 
				SpecialCircumstance TEXT,SpecialCircumstanceStatus TEXT,LastReactivatedDateTime TEXT);

DO
$BODY$
DECLARE
	ind integer := 1;
	strecord RECORD;
	sttstrecord RECORD;
	TotalItems integer; TotalOmittedItems integer; 
BEGIN 
	FOR strecord IN ( SELECT  DISTINCT st.id AS studentstestsid, gc.abbreviatedname as grade,
		            ts.attendanceschoolid, otd.schooldisplayidentifier as schoolId,
		            otd.districtdisplayidentifier as district,
		            otd.statedisplayidentifier as state,
		            otd.schoolname as school,
	                s.legalLastname as studentLastName,
	                s.legalFirstName as studentFirstName,
	                s.legalMiddleName as studentMiddleName,
	                s.statestudentidentifier as statestudentidentifier,
	                e.localstudentidentifier as localstudentidentifier,
	                e.id as enrollmentId, e.activeflag as enrollmentStatus,
	                conArea.name as subject,
	                ts.name as testsessionname,
	                ts.id as testsessionid,
	                st.testid as testid,
	                st.activeflag as testactiveflag,
	                st.startdatetime AT TIME ZONE 'UTC' as teststartdate,
	              	st.enddatetime AT TIME ZONE 'UTC' as testenddate,
	                tc.name as testcollectionname,
	                tc.id as testcollectionid,
	                (select acteddate::TIMESTAMP WITH TIME ZONE from studentstestshistory where studentstestsid =
	                st.id and action = 'REACTIVATION' order by acteddate desc limit 1) as lastReactivateddate,
					c.categorycode as teststatus,
	                (select specialcircumstancetype from specialcircumstance where id in (select
	                    specialcircumstanceid from studentspecialcircumstance where
	                    studenttestid = st.id) limit 1) as specialcircumstance,
	               	(select categoryname from category c,studentspecialcircumstance ssc 
	                       where c.id = ssc.status and studenttestid = st.id LIMIT 1) as specialCircumstanceStatus,
	              	stg.name as stagename
	          FROM studentstests st 
				  JOIN testsession ts ON ts.id = st.testsessionid
				  JOIN testcollection tc ON tc.id = st.testcollectionid and tc.contentareaid in (3,440)
	              JOIN student s ON st.studentid = s.id AND s.statestudentidentifier IN ( SELECT statestudentIdentifier FROM tmp_students ) AND s.stateid = 51
	              JOIN category c ON c.id=st.status AND c.categorycode IN ( 'unused', 'inprogress', 'complete') 
	              JOIN enrollment e on e.id=st.enrollmentid AND e.currentschoolyear= 2016
	              JOIN gradecourse gc ON ts.gradecourseid = gc.id
		          JOIN organizationtreedetail otd ON otd.schoolid = e.attendanceschoolid
		          JOIN stage stg on stg.id = ts.stageid
		          JOIN contentarea conArea ON conArea.id = tc.contentareaid
		       WHERE ts.source='BATCHAUTO' ORDER BY  statestudentidentifier, enrollmentid, subject, testsessionname)
	LOOP
			TotalItems = 0; TotalOmittedItems = 0;
			FOR sttstrecord IN (SELECT stsc.studentstestid, stsc.ticketno, stsc.startdatetime AT TIME ZONE 'UTC' as startdate, stsc.enddatetime AT TIME ZONE 'UTC' as enddate,
   			   	(select categoryname from category where id = stsc.statusid) as studenttestsectionstatus,
   				tsection.numberoftestitems as numofitems,
   				tsection.hardbreak, tsection.sectionorder,
   				--(select count(CASE WHEN ((foilid is not null or response is not null) and activeflag is true) THEN 1 ELSE null END) from studentsresponses where studentstestsectionsid = stsc.id) as numofitemsanswered
	       		(select count(CASE WHEN ((foilid is not null or response is not null)) THEN 1 ELSE null END) from studentsresponses where studentstestsectionsid = stsc.id) as numofitemsanswered
   				FROM studentstestsections stsc
	       		INNER JOIN testsection tsection
	       		ON tsection.id = stsc.testsectionid
	       		WHERE stsc.studentstestid = strecord.studentstestsid
	       		--AND stsc.activeflag is true
	       		ORDER BY tsection.sectionorder)
       		LOOP
	       		 	TotalItems := TotalItems + sttstrecord.numofitems :: integer; 
	       			TotalOmittedItems := TotalOmittedItems + (sttstrecord.numofitems :: integer - sttstrecord.numofitemsanswered :: integer);
	       		
       		END LOOP;
		       		
			INSERT INTO tmp_student_text_extract(State,District,School,SchoolIdentifier,Grade,
				StudentLastName,StudentFirstName,StudentMiddleName,StateStudentIdentifier,LocalStudentIdentifier,
				Enrollmentid, EnrollmentStatus, Subject,TestSessionName, Testsessionid, 
				Testid, TestCollectionId, TestStatus, TestActiveflag, Stage, TotalItems,TotalOmittedItems, 
				TestStartDate, TestEndDate, 
				SpecialCircumstance,SpecialCircumstanceStatus,LastReactivatedDateTime) 
				values(strecord.state ,strecord.district ,strecord.school ,strecord.schoolId ,strecord.grade,
				strecord.studentLastName ,strecord.studentFirstName ,strecord.studentMiddleName ,strecord.statestudentidentifier ,strecord.localstudentidentifier ,
				strecord.enrollmentId, strecord.enrollmentStatus, strecord.subject ,strecord.testsessionname ,strecord.testsessionid, 
				strecord.testid, strecord.testcollectionid, strecord.teststatus ,strecord.testactiveflag,strecord.stagename, TotalItems ,TotalOmittedItems,
				strecord.teststartdate, strecord.testenddate,
				strecord.specialcircumstance ,strecord.specialCircumstanceStatus, strecord.lastReactivateddate);
	END LOOP;
			 
END; 
$BODY$;

\COPY (SELECT * FROM tmp_student_text_extract) TO 'KAP_DualEnrolled_Students_Tests_Extract_0520.csv' DELIMITER ',' CSV HEADER ;

DROP TABLE IF EXISTS tmp_student_text_extract;
DROP TABLE IF EXISTS tmp_students ;
