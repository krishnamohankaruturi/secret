--2044 enrollments
update enrollment set currentschoolyear=2014 where currentschoolyear=2044 and studentid=494285;
update enrollment set currentschoolyear=2014 where currentschoolyear=2044 and studentid=483498;
update enrollment set currentschoolyear=2014 where currentschoolyear=2044 and studentid=483728;
update enrollment set currentschoolyear=2014 where currentschoolyear=2044 and studentid=483828;
update enrollment set currentschoolyear=2014 where currentschoolyear=2044 and studentid=483948;
update enrollment set currentschoolyear=2014 where currentschoolyear=2044 and studentid=484167;.
delete from enrollment where id=920129;
delete from enrollmentsrosters where enrollmentid=934932;

--update enrollment school year to 2015 and session school year 2015 --updated 5 enrollments
update enrollment set currentschoolyear=2015 where id in (select enr.enrollmentid from enrollment en 
join enrollmentsrosters enr on enr.enrollmentid=en.id
join roster r on r.id=enr.rosterid  where enr.rosterid in (490102,490143,490381,490384,492769,492771,514141,514142,516563,516567)
and en.currentschoolyear = 2016);

--UPDATE 191 sessions
update testsession set schoolyear=2015 where rosterid in (490102,490143,490381,490384,492769,492771,514141,514142,516563,516567);

--duplicate enrollment -- 2016 
-- update 2 session each
update testsession set schoolyear=2015 where rosterid=531525;
update testsession set schoolyear=2015 where rosterid=531526;
            
update enrollment set currentschoolyear=2015 where id=1190704;

INSERT INTO enrollment(aypschoolidentifier, residencedistrictidentifier, currentgradelevel, 
            localstudentidentifier, currentschoolyear, schoolentrydate, districtentrydate, 
            stateentrydate, exitwithdrawaldate, exitwithdrawaltype, specialcircumstancestransferchoice, 
            giftedstudent, specialedprogramendingdate, qualifiedfor504, studentid, 
            attendanceschoolid, restrictionid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, source, aypschoolid, 
            fundingschool, sourcetype)
    select aypschoolidentifier, residencedistrictidentifier, currentgradelevel, 
            localstudentidentifier, 2016, schoolentrydate, districtentrydate, 
            stateentrydate, exitwithdrawaldate, exitwithdrawaltype, specialcircumstancestransferchoice, 
            giftedstudent, specialedprogramendingdate, qualifiedfor504, studentid, 
            attendanceschoolid, restrictionid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, source, aypschoolid, 
            fundingschool, sourcetype from enrollment where id=1190704;
--UPDATE 3
update enrollmentsrosters set enrollmentid = (select id from enrollment where studentid=881643 and currentschoolyear=2016 and activeflag is true) 
where id in (select enr.id from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
   join roster r on r.id=enr.rosterid where en.studentid=881643 and r.currentschoolyear=2016);
 
--UPDATE 17  
update studentstests set enrollmentid=(select id from enrollment where studentid=881643 and currentschoolyear=2016 and activeflag is true) 
 where studentid=881643 and testsessionid in (select distinct ts.id from studentstests st inner join testsession ts on st.testsessionid=ts.id 
	where enrollmentid=1190704 and schoolyear=2016);

--update roster year to 2015
update roster set currentschoolyear=2015 where id in (885681,885867,889828);

--update session & enrollment year to 2015
update testsession set schoolyear=2015 where rosterid in (493241,493242);
            
update enrollment set currentschoolyear=2015 where id=1056789;


--count=1 update all other;
DO 
$BODY$ 
DECLARE
    CROW RECORD;
    schoolyear integer;
BEGIN
    FOR CROW IN (select enr.rosterid,count(distinct en.currentschoolyear) from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
		join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where --r.id=497262 and 
		en.currentschoolyear <> r.currentschoolyear order by enr.rosterid)
		group by enr.rosterid having count(distinct en.currentschoolyear) = 1 order by enr.rosterid) LOOP
	schoolyear := (select distinct en.currentschoolyear
		from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where r.id=CROW.rosterid);
	RAISE NOTICE  'UPDATing roster %, %', CROW.rosterid, schoolyear;
	UPDATE roster set currentschoolyear= schoolyear where id=CROW.rosterid;
    END LOOP;
END;
$BODY$;

--future enrollments
--DELETE 364
delete from enrollmentsrosters where enrollmentid in (select id from enrollment 
 where currentschoolyear in (2023, 2026, 2019, 2020, 2030, 2041, 2018, 2017, 2021, 2022, 2032, 2033, 2034, 2038, 2039, 2042, 
 2046, 2050, 2051, 2052, 2055, 2057, 2058, 2066, 2067, 2071, 2075, 2089, 2090, 2091, 2025, 2028, 2056, 2054, 2031, 2035, 2048, 
 2036, 2037, 2027, 2072, 2077, 2092, 2074));
--DELETE 223
delete from enrollment where currentschoolyear in (2023, 2026, 2019, 2020, 2030, 2041, 2018, 2017, 2021, 2022, 2032, 2033, 2034, 2038, 2039, 2042, 
 2046, 2050, 2051, 2052, 2055, 2057, 2058, 2066, 2067, 2071, 2075, 2089, 2090, 2091, 2025, 2028, 2056, 2054, 2031, 2035, 2048, 
 2036, 2037, 2027, 2072, 2077, 2092, 2074);
 
--DELETE 58
delete from enrollmentsrosters where enrollmentid in (select id from enrollment where currentschoolyear in (2024, 2040, 2053, 2073, 2076, 2029, 2047));
--DELETE 34
delete from enrollment where currentschoolyear in (2024, 2040, 2053, 2073, 2076, 2029, 2047);

--rosterid=874088
delete from enrollmentsrosters where id in (select enr.id from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
join roster r on r.id=enr.rosterid where en.studentid=1201161 and enr.rosterid=874088);
update testsession set schoolyear=2016 where rosterid=874088;
 
--eyear 2016, roster 2015, testsessionyear 2015
--UPDATE 23
update enrollment set currentschoolyear=2015 where id in (select distinct enr.enrollmentid
		from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid 
		join testsession ts on ts.rosterid=r.id
		join studentstests st on st.enrollmentid=en.id
		where r.id in (select enr.rosterid from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
		join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where 
		en.currentschoolyear <> r.currentschoolyear order by enr.rosterid)
		group by enr.rosterid having count(distinct en.currentschoolyear) = 2)
		and en.currentschoolyear=2016 and r.currentschoolyear=2015 and ts.schoolyear=2015);
		
-- studentid=483800;	
update enrollment set currentschoolyear=2014 where id in (499685, 493975);

-- studentid=484068;	
update enrollment set currentschoolyear=2014 where id in (494299);
 
--eyear 2016, roster 2014, testsessionyear null
--UPDATE 4
update enrollment set currentschoolyear=2014 where id in (select distinct enr.enrollmentid
		from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid 
		left join testsession ts on ts.rosterid=r.id
		left join studentstests st on st.enrollmentid=en.id
		where r.id in (select enr.rosterid from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
		join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where 
		en.currentschoolyear <> r.currentschoolyear order by enr.rosterid)
		group by enr.rosterid having count(distinct en.currentschoolyear) = 2)
		and en.currentschoolyear=2016 and r.currentschoolyear=2014);

--eyear 2012
--NOTICE:  Created new roster 990237, for old 13232, school year 2012
--NOTICE:  Created new roster 990238, for old 13986, school year 2012
--NOTICE:  Created new roster 990239, for old 13987, school year 2012
--NOTICE:  Created new roster 990240, for old 13989, school year 2012
--NOTICE:  Created new roster 990241, for old 18393, school year 2012
--NOTICE:  Created new roster 990242, for old 18409, school year 2012
--NOTICE:  Created new roster 990243, for old 219832, school year 2012
DO 
$BODY$ 
DECLARE
    CROW RECORD;
    newrosterid bigint;
BEGIN
    FOR CROW IN (select enr.id, enr.modifieddate, enr.rosterid, enr.enrollmentid, en.studentid, en.createddate, en.modifieddate,en.currentschoolyear "enrollmentyear", r.currentschoolyear "rosteryear", r.statesubjectareaid,r.statecoursesid, o.displayidentifier from enrollment en 
			join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid 
			join student s on en.studentid=s.id
			left join organization o on o.id=s.stateid
			where enr.rosterid in (13232,13986,13987,13989,18393,18409,219832) and en.currentschoolyear=2012) LOOP
	IF CROW.enrollmentyear <> CROW.rosteryear 
		AND ((select count(*) from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear) = 0) THEN
		newrosterid := (select nextval('roster_id_seq'::regclass));
		--do select insert roster
		INSERT INTO roster(id,coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, currentschoolyear, aypschoolid, tempoldrid)
		    select newrosterid, coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, CROW.enrollmentyear, aypschoolid, CROW.rosterid from roster where id=CROW.rosterid;
		update enrollmentsrosters set rosterid = newrosterid where id=CROW.id;

		RAISE NOTICE  'Created new roster %, for old %, school year %', newrosterid, CROW.rosterid, CROW.enrollmentyear;
	ELSIF CROW.enrollmentyear <> CROW.rosteryear THEN
		update enrollmentsrosters set rosterid = (select id from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear)
			where id=CROW.id;
	END IF;
    END LOOP;
END;
$BODY$;

----eyear 2013 and eyear 2014
--NOTICE:  Created new roster 990244, for old 355789, school year 2013
--NOTICE:  Created new roster 990245, for old 220070, school year 2013
--NOTICE:  Created new roster 990246, for old 220460, school year 2013
--NOTICE:  Created new roster 990247, for old 220532, school year 2013
--NOTICE:  Created new roster 990248, for old 220259, school year 2013
--NOTICE:  Created new roster 990249, for old 355759, school year 2013
--NOTICE:  Created new roster 990250, for old 355924, school year 2013
--NOTICE:  Created new roster 990251, for old 220737, school year 2013
--NOTICE:  Created new roster 990252, for old 355790, school year 2013
--NOTICE:  Created new roster 990253, for old 356424, school year 2013
--NOTICE:  Created new roster 990254, for old 220550, school year 2013
--NOTICE:  Created new roster 990255, for old 301338, school year 2013
--NOTICE:  Created new roster 990256, for old 355294, school year 2013
--NOTICE:  Created new roster 990257, for old 355922, school year 2013
--NOTICE:  Created new roster 990258, for old 355932, school year 2013
--NOTICE:  Created new roster 990259, for old 221222, school year 2013
--NOTICE:  Created new roster 990260, for old 220739, school year 2013
--NOTICE:  Created new roster 990261, for old 355648, school year 2013
--NOTICE:  Created new roster 990262, for old 221128, school year 2013
--NOTICE:  Created new roster 990263, for old 355597, school year 2013
--NOTICE:  Created new roster 990264, for old 241439, school year 2013
--NOTICE:  Created new roster 990265, for old 289292, school year 2013
--NOTICE:  Created new roster 990266, for old 220747, school year 2013
--NOTICE:  Created new roster 990267, for old 220180, school year 2013
--NOTICE:  Created new roster 990268, for old 220512, school year 2013
--NOTICE:  Created new roster 990269, for old 356312, school year 2013
--NOTICE:  Created new roster 990270, for old 225644, school year 2013
--NOTICE:  Created new roster 990271, for old 356209, school year 2013
--NOTICE:  Created new roster 990272, for old 220496, school year 2013
--NOTICE:  Created new roster 990273, for old 220778, school year 2013
--NOTICE:  Created new roster 990274, for old 355878, school year 2013
--NOTICE:  Created new roster 990275, for old 220740, school year 2013
--NOTICE:  Created new roster 990276, for old 220990, school year 2013
--NOTICE:  Created new roster 990277, for old 355959, school year 2013
--NOTICE:  Created new roster 990278, for old 355845, school year 2013
--NOTICE:  Created new roster 990279, for old 355863, school year 2013
--NOTICE:  Created new roster 990280, for old 355925, school year 2013
--NOTICE:  Created new roster 990281, for old 241418, school year 2013
--NOTICE:  Created new roster 990282, for old 220441, school year 2013
--NOTICE:  Created new roster 990283, for old 220403, school year 2013
--NOTICE:  Created new roster 990284, for old 220600, school year 2013
--NOTICE:  Created new roster 990285, for old 355846, school year 2013
--NOTICE:  Created new roster 990286, for old 355809, school year 2013
--NOTICE:  Created new roster 990287, for old 349688, school year 2013
--NOTICE:  Created new roster 990288, for old 220440, school year 2013
--NOTICE:  Created new roster 990289, for old 220533, school year 2013
--NOTICE:  Created new roster 990290, for old 355876, school year 2013
--NOTICE:  Created new roster 990291, for old 355892, school year 2013
--NOTICE:  Created new roster 990292, for old 221758, school year 2013
--NOTICE:  Created new roster 990293, for old 220442, school year 2013
--NOTICE:  Created new roster 990294, for old 349703, school year 2013
--NOTICE:  Created new roster 990295, for old 220498, school year 2013
--NOTICE:  Created new roster 990296, for old 355848, school year 2013
--NOTICE:  Created new roster 990297, for old 356591, school year 2013
--NOTICE:  Created new roster 990298, for old 355791, school year 2013
--NOTICE:  Created new roster 990299, for old 220535, school year 2013
--NOTICE:  Created new roster 990300, for old 355897, school year 2013
--NOTICE:  Created new roster 990301, for old 220517, school year 2013

DO 
$BODY$ 
DECLARE
    CROW RECORD;
    newrosterid bigint;
BEGIN
    FOR CROW IN (select distinct enr.id, enr.rosterid, en.currentschoolyear as enrollmentyear, r.currentschoolyear as rosteryear, 
		schoolyear as testsessionyear, (CASE WHEN st.id is not null THEN en.currentschoolyear ELSE null END) as studenttestyear, enr.enrollmentid
			from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid 
			left join testsession ts on ts.rosterid=r.id
			left join studentstests st on st.enrollmentid=en.id
			where r.id in (select enr.rosterid from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
			join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid where 
			en.currentschoolyear <> r.currentschoolyear order by enr.rosterid)
			group by enr.rosterid having count(distinct en.currentschoolyear) = 2)
			and en.currentschoolyear =2013 and r.currentschoolyear=2014) LOOP
	IF CROW.enrollmentyear <> CROW.rosteryear 
		AND ((select count(*) from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear) = 0) THEN
		newrosterid := (select nextval('roster_id_seq'::regclass));
		--do select insert roster
		INSERT INTO roster(id,coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, currentschoolyear, aypschoolid, tempoldrid)
		    select newrosterid, coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, CROW.enrollmentyear, aypschoolid, CROW.rosterid from roster where id=CROW.rosterid;
		update enrollmentsrosters set rosterid = newrosterid where id=CROW.id;

		RAISE NOTICE  'Created new roster %, for old %, school year %', newrosterid, CROW.rosterid, CROW.enrollmentyear;
	ELSIF CROW.enrollmentyear <> CROW.rosteryear THEN
		update enrollmentsrosters set rosterid = (select id from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear)
			where id=CROW.id;
	END IF;
    END LOOP;
END;
$BODY$;

-- eyear 2013, ryear-2015
delete from enrollmentsrosters where id = (select enr.id from enrollment en 
join enrollmentsrosters enr on enr.enrollmentid=en.id join roster r on r.id=enr.rosterid 
join student s on en.studentid=s.id left join organization o on o.id=s.stateid where enr.enrollmentid=497714 and rosterid=220796);

delete from enrollmentsrosters where id in (select enr.id from enrollment en 
join enrollmentsrosters enr on enr.enrollmentid=en.id join roster r on r.id=enr.rosterid 
join student s on en.studentid=s.id left join organization o on o.id=s.stateid
where enr.enrollmentid=694953 and rosterid in (349692, 241437));

--eyear 2014-2015, ryear-2013
--UPDATE 809
update roster set currentschoolyear=2014 where id in (select distinct enr.rosterid
		from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid 
		left join testsession ts on ts.rosterid=r.id
		left join studentstests st on st.enrollmentid=en.id
		where r.id in (select enr.rosterid from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
		join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where 
		en.currentschoolyear <> r.currentschoolyear order by enr.rosterid)
		group by enr.rosterid having count(distinct en.currentschoolyear) = 2)
		and en.currentschoolyear in (2014, 2015) and r.currentschoolyear=2013);
		
--eyear 2014-2015, ryear-2013 : split roster		
DO 
$BODY$ 
DECLARE
    CROW RECORD;
    newrosterid bigint;
BEGIN
    FOR CROW IN (select distinct enr.id, enr.rosterid, en.currentschoolyear as enrollmentyear, r.currentschoolyear as rosteryear, 
		schoolyear as testsessionyear, (CASE WHEN st.id is not null THEN en.currentschoolyear ELSE null END) as studenttestyear, enr.enrollmentid
			from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid 
			left join testsession ts on ts.rosterid=r.id
			left join studentstests st on st.enrollmentid=en.id
			where r.id in (select enr.rosterid from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
			join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid where 
			en.currentschoolyear <> r.currentschoolyear order by enr.rosterid)
			group by enr.rosterid having count(distinct en.currentschoolyear) = 2)
			and en.currentschoolyear in (2014, 2015) and r.currentschoolyear=2014
			 order by enr.rosterid) LOOP
	IF CROW.enrollmentyear <> CROW.rosteryear 
		AND ((select count(*) from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear) = 0) THEN
		newrosterid := (select nextval('roster_id_seq'::regclass));
		--do select insert roster
		INSERT INTO roster(id,coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, currentschoolyear, aypschoolid, tempoldrid)
		    select newrosterid, coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, CROW.enrollmentyear, aypschoolid, CROW.rosterid from roster where id=CROW.rosterid;
		update enrollmentsrosters set rosterid = newrosterid where id=CROW.id;
		
		IF CROW.testsessionyear is not null THEN
			update testsession set schoolyear=CROW.enrollmentyear, rosterid=newrosterid where rosterid=CROW.rosterid;
		END IF;
		RAISE NOTICE  'Created new roster %, for old %, school year %', newrosterid, CROW.rosterid, CROW.enrollmentyear;
	ELSIF CROW.enrollmentyear <> CROW.rosteryear THEN
		update enrollmentsrosters set rosterid = (select id from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear)
			where id=CROW.id;
	END IF;
    END LOOP;
END;
$BODY$;

-- en.currentschoolyear =2015 and r.currentschoolyear=2015 and ts.schoolyear=2016
--UPDATE 45 , rosterid: 357099,357116,357189,357206
update testsession set schoolyear=2015 where rosterid in (select distinct enr.rosterid
		from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid 
		left join testsession ts on ts.rosterid=r.id
		left join studentstests st on st.enrollmentid=en.id
		where r.id in (select enr.rosterid from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
		join enrollmentsrosters enr on enr.enrollmentid=en.id
		join roster r on r.id=enr.rosterid where 
		en.currentschoolyear <> r.currentschoolyear order by enr.rosterid)
		group by enr.rosterid having count(distinct en.currentschoolyear) = 2)
		and en.currentschoolyear =2015 and r.currentschoolyear=2015 and ts.schoolyear=2016) and schoolyear=2016

--en.currentschoolyear=2014 and r.currentschoolyear=2015 and ts.schoolyear=2015
--NOTICE:  Created new roster 995568, for old 1121, school year 2014
DO 
$BODY$ 
DECLARE
    CROW RECORD;
    newrosterid bigint;
BEGIN
    FOR CROW IN (select distinct enr.rosterid, en.currentschoolyear as enrollmentyear, r.currentschoolyear as rosteryear, schoolyear as testsessionyear
			from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid 
			join testsession ts on ts.rosterid=r.id
			--join studentstests st on st.enrollmentid=en.id
			where r.id in (select enr.rosterid from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
			join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid where 
			en.currentschoolyear <> r.currentschoolyear order by enr.rosterid)
			group by enr.rosterid having count(distinct en.currentschoolyear) = 2)
			and en.currentschoolyear=2014 and r.currentschoolyear=2015 and ts.schoolyear=2015
			 order by enr.rosterid) LOOP
	IF CROW.enrollmentyear <> CROW.rosteryear 
		AND ((select count(*) from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear) = 0) THEN
		newrosterid := (select nextval('roster_id_seq'::regclass));
		--do select insert roster
		INSERT INTO roster(id,coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, currentschoolyear, aypschoolid, tempoldrid)
		    select newrosterid, coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, CROW.enrollmentyear, aypschoolid, CROW.rosterid from roster where id=CROW.rosterid;
			    
		update enrollmentsrosters set rosterid = newrosterid where enrollmentid in (select en.id from enrollment en 
			join enrollmentsrosters enr on enr.enrollmentid=en.id where enr.rosterid = CROW.rosterid 
			and en.currentschoolyear=CROW.enrollmentyear) and rosterid = CROW.rosterid;
		
		RAISE NOTICE  'Created new roster %, for old %, school year %', newrosterid, CROW.rosterid, CROW.enrollmentyear;
	ELSIF CROW.enrollmentyear <> CROW.rosteryear THEN
		update enrollmentsrosters set rosterid = (select id from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear)
			where enrollmentid in (select en.id from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id 
			where enr.rosterid = CROW.rosterid and en.currentschoolyear=CROW.enrollmentyear) and rosterid = CROW.rosterid;
	END IF;
    END LOOP;
END;
$BODY$;

--n.currentschoolyear=2014 and r.currentschoolyear=2015 and ts.schoolyear is null
DO 
$BODY$ 
DECLARE
    CROW RECORD;
    newrosterid bigint;
BEGIN
    FOR CROW IN (select distinct enr.rosterid, en.currentschoolyear as enrollmentyear, r.currentschoolyear as rosteryear, 
			schoolyear as testsessionyear from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
				join roster r on r.id=enr.rosterid 
				left join testsession ts on ts.rosterid=r.id
				where r.id in (select enr.rosterid from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
				join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
				join enrollmentsrosters enr on enr.enrollmentid=en.id
				join roster r on r.id=enr.rosterid where 
				en.currentschoolyear <> r.currentschoolyear order by enr.rosterid)
				group by enr.rosterid having count(distinct en.currentschoolyear) = 2)
				and en.currentschoolyear=2014 and r.currentschoolyear=2015 
			order by enr.rosterid, en.currentschoolyear, r.currentschoolyear) LOOP
	IF CROW.enrollmentyear <> CROW.rosteryear AND CROW.testsessionyear is null
		AND ((select count(*) from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear) = 0) THEN
		newrosterid := (select nextval('roster_id_seq'::regclass));
		--do select insert roster
		INSERT INTO roster(id,coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, currentschoolyear, aypschoolid, tempoldrid)
		    select newrosterid, coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, CROW.enrollmentyear, aypschoolid, CROW.rosterid from roster where id=CROW.rosterid;
			    
		update enrollmentsrosters set rosterid = newrosterid where enrollmentid in (select en.id from enrollment en 
			join enrollmentsrosters enr on enr.enrollmentid=en.id where enr.rosterid = CROW.rosterid 
			and en.currentschoolyear=CROW.enrollmentyear) and rosterid = CROW.rosterid;
		
		RAISE NOTICE  'Created new roster %, for old %, school year %', newrosterid, CROW.rosterid, CROW.enrollmentyear;
	ELSIF CROW.enrollmentyear <> CROW.rosteryear AND CROW.testsessionyear is null THEN
		update enrollmentsrosters set rosterid = (select id from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear)
			where enrollmentid in (select en.id from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id 
			where enr.rosterid = CROW.rosterid and en.currentschoolyear=CROW.enrollmentyear) and rosterid = CROW.rosterid;
	END IF;
    END LOOP;
END;
$BODY$;

--move session to new roster from old
DO 
$BODY$ 
DECLARE
    CROW RECORD;
BEGIN
	FOR CROW IN (select ts.id as "testsessionid", schoolyear, r.id as "rosterid", r.tempoldrid from testsession ts 
		inner join roster r on r.tempoldrid=ts.rosterid
		where ts.schoolyear = r.currentschoolyear -- and r.tempoldrid=28164
		) LOOP
		RAISE NOTICE  'moving session to new roster %, for old %, school year %', CROW.rosterid, CROW.tempoldrid, CROW.schoolyear;
		update testsession set rosterid = CROW.rosterid where id = CROW.testsessionid;
	END LOOP;
END;
$BODY$;

--delete future enrollments
--DELETE 136
delete from enrollmentsrosters where enrollmentid in (select id from enrollment where currentschoolyear in ( 2043, 2045, 2049, 2059, 2060, 2061, 2062, 2063, 2064, 2065, 2068, 2069, 2070, 2078, 2079, 2080, 2081, 2082, 2083, 2084, 2085, 2086, 2087, 2088, 2093, 2094, 2095, 2096, 2097, 2098, 2099))
--DELETE 82
delete from enrollment where currentschoolyear in ( 2043, 2045, 2049, 2059, 2060, 2061, 2062, 2063, 2064, 2065, 2068, 2069, 2070, 2078, 2079, 2080, 2081, 2082, 2083, 2084, 2085, 2086, 2087, 2088, 2093, 2094, 2095, 2096, 2097, 2098, 2099);

-- rid:913390 |  eid:1047446 |    sid:854009
delete from enrollmentsrosters where enrollmentid = 1047446 and rosterid=913390;

-- rid:913388 |  eid:1047446 |    sid:854009
delete from enrollmentsrosters where enrollmentid = 1047446 and rosterid=913388;

-- rid:913387 |  eid:1047446 |    sid:854009
delete from enrollmentsrosters where enrollmentid = 1047446 and rosterid=913387;

--eid:1401752 |   sid:910344
--rid:357165,357167,357253,357255
update enrollment set currentschoolyear=2015 where id=1401752 and studentid=910344;

-- eid:493663 |sid:483557, rid:220346,355706
update enrollment set currentschoolyear=2014 where id=493663 and studentid=483557;

-- eid:493662 |sid:483556, rid:220346,355706
update enrollment set currentschoolyear=2014 where id=493662 and studentid=483556;

-- rid:355753 | eid:493730 |sid:483618
update enrollment set currentschoolyear=2014 where id=493730 and studentid=483618;

--split remaining mis-match rosters/enrollment
DO 
$BODY$ 
DECLARE
    CROW RECORD;
    newrosterid bigint;
BEGIN
    FOR CROW IN (select distinct enr.rosterid, en.currentschoolyear as enrollmentyear, r.currentschoolyear as rosteryear
			from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id join roster r on r.id=enr.rosterid 
			where r.id in (select enr.rosterid from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
			join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid where en.currentschoolyear <> r.currentschoolyear order by enr.rosterid)
			group by enr.rosterid having count(distinct en.currentschoolyear) > 1)
			order by enr.rosterid, en.currentschoolyear) LOOP
	IF CROW.enrollmentyear <> CROW.rosteryear 
		AND ((select count(*) from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear) = 0) THEN
		newrosterid := (select nextval('roster_id_seq'::regclass));
		--do select insert roster
		INSERT INTO roster(id,coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, currentschoolyear, aypschoolid, tempoldrid)
		    select newrosterid, coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, CROW.enrollmentyear, aypschoolid, CROW.rosterid from roster where id=CROW.rosterid;
			    
		update enrollmentsrosters set rosterid = newrosterid where enrollmentid in (select en.id from enrollment en 
			join enrollmentsrosters enr on enr.enrollmentid=en.id where enr.rosterid = CROW.rosterid 
			and en.currentschoolyear=CROW.enrollmentyear) and rosterid = CROW.rosterid;
		
		RAISE NOTICE  'Created new roster %, for old %, school year %', newrosterid, CROW.rosterid, CROW.enrollmentyear;
	ELSIF CROW.enrollmentyear <> CROW.rosteryear THEN
		update enrollmentsrosters set rosterid = (select id from roster where tempoldrid=CROW.rosterid and currentschoolyear=CROW.enrollmentyear)
			where enrollmentid in (select en.id from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id 
			where enr.rosterid = CROW.rosterid and en.currentschoolyear=CROW.enrollmentyear) and rosterid = CROW.rosterid;
	END IF;
    END LOOP;
END;
$BODY$;


--correct 874088 roster session data
INSERT INTO roster(coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, currentschoolyear, aypschoolid, tempoldrid)
		    select coursesectionname, coursesectiondescription, teacherid, statesubjectareaid, 
			    courseenrollmentstatusid, statecourseid, restrictionid, createddate, 
			    createduser, activeflag, modifieddate, modifieduser, statesubjectcourseidentifier, 
			    localcourseid, educatorschooldisplayidentifier, attendanceschoolid, 
			    prevstatesubjectareaid, statecoursecode, source, sourcetype, 
			    statecoursesid, 2015, aypschoolid, 874088 from roster where id=874088;
INSERT INTO enrollmentsrosters(
            enrollmentid, rosterid, createddate, createduser, activeflag, 
            modifieddate, modifieduser, courseenrollmentstatusid, source, 
            trackerstatus)
    SELECT 1765169, 1023639, createddate, createduser, activeflag, 
       modifieddate, modifieduser, courseenrollmentstatusid, source, 
       trackerstatus
  FROM enrollmentsrosters where rosterid =874088;
  
update testsession set schoolyear=2015, rosterid=1023639 where id=1898764 and rosterid=874088;

INSERT INTO testsession(rosterid, name, status, createddate, createduser, activeflag, 
            modifieduser, modifieddate, testid, testcollectionid, source, 
            attendanceschoolid, operationaltestwindowid, testtypeid, gradecourseid, 
            stageid, windowexpirydate, schoolyear, testpanelid, subjectareaid)
SELECT 874088, name, status, createddate, createduser, activeflag, 
       modifieduser, modifieddate, testid, testcollectionid, source, 
       attendanceschoolid, operationaltestwindowid, testtypeid, gradecourseid, 
       stageid, windowexpirydate, 2016, testpanelid, subjectareaid
  FROM testsession where rosterid=1023639;

update studentstests set testsessionid=2152051 where enrollmentid=1765170 and testsessionid=1898764;

--UPDATE 14
update testsession set schoolyear=2015 where id in (select distinct ts.id
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join enrollment en on st.enrollmentid=en.id
inner join roster r on ts.rosterid=r.id
where ts.schoolyear <> en.currentschoolyear and ts.rosterid=955751);

--UPDATE 644
update testsession set schoolyear=2015 where id in (select distinct ts.id
from testsession ts 
inner join studentstests st on st.testsessionid=ts.id
inner join enrollment en on st.enrollmentid=en.id
inner join roster r on ts.rosterid=r.id
where ts.schoolyear <> en.currentschoolyear);

--update roster year with enrollment year
DO 
$BODY$ 
DECLARE
    CROW RECORD;
BEGIN
	FOR CROW IN (select distinct enr.rosterid, en.currentschoolyear as enrollmentyear, r.currentschoolyear as rosteryear
			from enrollment en join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid where r.id in (select enr.rosterid from enrollment en 
			join enrollmentsrosters enr on enr.enrollmentid=en.id
			join roster r on r.id=enr.rosterid where r.id in (select distinct enr.rosterid from enrollment en 
			join enrollmentsrosters enr on enr.enrollmentid=en.id join roster r on r.id=enr.rosterid where 
			en.currentschoolyear <> r.currentschoolyear order by enr.rosterid) group by enr.rosterid)
			order by enr.rosterid, en.currentschoolyear) LOOP
		RAISE NOTICE  'updating roster year %,% ', CROW.rosterid, CROW.enrollmentyear;	
		update roster set currentschoolyear = CROW.enrollmentyear where id=CROW.rosterid;
	END LOOP;
END;
$BODY$;

--QC data
update enrollment set currentschoolyear=2016 where attendanceschoolid in (select schoolid 
from organizationtreedetail where stateid in (select id from organization where EXTRACT(YEAR FROM schoolenddate)=2015 
	and contractingorganization is true and activeflag is true)) and currentschoolyear=2015 and id not in (1425504);

update roster set currentschoolyear=2016 where attendanceschoolid in (select schoolid 
from organizationtreedetail where stateid in (select id from organization where EXTRACT(YEAR FROM schoolenddate)=2015 
	and contractingorganization is true and activeflag is true)) and currentschoolyear=2015;

update testsession set schoolyear=2016 where attendanceschoolid in (select schoolid 
from organizationtreedetail where stateid in (select id from organization where EXTRACT(YEAR FROM schoolenddate)=2015 
	and contractingorganization is true and activeflag is true)) and schoolyear=2015;

update organization set schoolstartdate= schoolstartdate + interval '1 year', schoolenddate=schoolenddate + interval '1 year' 
	where EXTRACT(YEAR FROM schoolenddate)=2015 and contractingorganization is true and activeflag is true;
	
delete from enrollmentsrosters where rosterid=518873 and enrollmentid=858804;
update roster set currentschoolyear=2015 where id =518873;

update roster set currentschoolyear=2015 where id =529545;

delete from enrollmentsrosters where rosterid=546196 and enrollmentid=858804;
update roster set currentschoolyear=2015 where id =546196;

update roster set currentschoolyear=2015 where id =529546;

update studentstests set testsessionid=2054034 where testsessionid=442427 and studentid=676846;

update studentstests set testsessionid=2054034 where testsessionid=1898768 and enrollmentid=858804;

update testsession set schoolyear=2016 where id in(select distinct ts.id
	from testsession ts 
		inner join studentstests st on st.testsessionid=ts.id
		inner join enrollment en on st.enrollmentid=en.id
		inner join student s on s.id=st.studentid
where en.currentschoolyear <> ts.schoolyear and s.stateid in (21814, 58538,58539,58540) );

update studentstests set testsessionid=2054034 where testsessionid=1898768 and enrollmentid=858804;

update testsession set schoolyear=2016 where id in(select distinct ts.id as testsessionid from testsession ts inner join roster r on ts.rosterid=r.id
where r.currentschoolyear <> ts.schoolyear
) and schoolyear=2015;

update testsession set schoolyear=2015 where id in(select distinct ts.id as testsessionid from testsession ts inner join roster r on ts.rosterid=r.id
where r.currentschoolyear <> ts.schoolyear
) and schoolyear=2014;

--after delete
--DELETE 40106
delete from studentstestshistory where id in (select sth.id from studentstestshistory sth 
left join studentstests st on sth.studentstestsid=st.id where st.id is null);

delete from testsession where rosterid=1898768;

--DELETE 218586
delete from ititestsessionsensitivitytags where ititestsessionhistoryid in (select id from ititestsessionhistory where rosterid not in (select id from roster));
--DELETE 223922
delete from ititestsessionhistory where rosterid not in (select id from roster);
--DELETE 944202
delete from studenttrackerband where studenttrackerid in (select id from studenttracker where schoolyear=2015);
--DELETE 138589
delete from studenttracker where schoolyear=2015;
