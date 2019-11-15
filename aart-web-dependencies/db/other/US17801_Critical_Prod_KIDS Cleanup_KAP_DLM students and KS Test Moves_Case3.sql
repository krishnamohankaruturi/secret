BEGIN;
/*
--===============================================================================================================
-- ROW:1
-- Student ID	Bldgtestcompleted	Movetesttobldg	Teststomove(subjects)	Service Desk
-- 2926963114	4141	818(0936)	MDPT MPT	SDM
--===============================================================================================================
--//////////Validation Script//////////////////////////////

select s.statestudentidentifier, s.id as studentid, e.id as enrollmentid, esa.testtypeid, esa.subjectareaid,e.activeflag as enrollactiveflag,
(select organizationname from organization where id = e.attendanceschoolid), (select displayidentifier from organization where id = e.attendanceschoolid)
 from enrollmenttesttypesubjectarea esa join enrollment e on esa.enrollmentid = e.id join student s on s.id = e.studentid
 where --esa.activeflag is true and e.activeflag is true and s.activeflag is true and s.stateid=51
  s.statestudentidentifier in ('2926963114');

 --Find Tests for a student:

select st.id,st.status,st.enrollmentid,st.testsessionid,st.studentid,st.createddate,ts.rosterid, ts.source,ts.name, st.activeflag
 from studentstests st join testsession ts on ts.id=st.testsessionid 
 where st.studentid in (select id from student where activeflag is true and stateid=51 
 and statestudentidentifier in ('2926963114')) and ts.source ilike 'BATCHAUTO' order by st.enrollmentid;

 select s.statestudentidentifier,o.displayidentifier,st.id,c.categoryname,st.enrollmentid,st.testsessionid,st.studentid,st.createddate,ts.rosterid, ts.source,ts.name, st.activeflag
       from student s 
       inner join enrollment e on e.studentid=s.id and s.activeflag is true 
       inner join organization o on o.id=e.attendanceschoolid
       inner join enrollmenttesttypesubjectarea esa
       inner join studentstests st on st.studentid=s.id and st.enrollmentid=e.id
       inner join category c on c.id=st.status
       Inner join testsession ts on ts.id=st.testsessionid 
       where s.statestudentidentifier in ('2926963114') ;
          and ts.source ilike 'BATCHAUTO' and e.activeflag is true and s.stateid=51
          order by st.enrollmentid ;

--above both script or below one to see the existing values for tests need to move 
 select s.statestudentidentifier,e.id,o.displayidentifier,st.id,c.categoryname,st.enrollmentid,st.testsessionid,st.createddate,ts.rosterid, ts.source,ts.name, st.activeflag--,esa.testtypeid
       from student s 
	       inner join enrollment e on e.studentid=s.id and s.activeflag is true 
	       inner join organization o on o.id=e.attendanceschoolid
	       --inner join enrollmenttesttypesubjectarea esa on esa.enrollmentid = e.id 
	       inner join studentstests st on st.studentid=s.id and st.enrollmentid=e.id
	       inner join category c on c.id=st.status
	       Inner join testsession ts on ts.id=st.testsessionid 
	       where s.statestudentidentifier in ('2926963114') 
          and ts.source ilike 'BATCHAUTO' and e.activeflag is true and s.stateid=51
          order by st.enrollmentid ;
--//////////update Scripts//////////////////////////////
 
--update
update studentstestsections 
  set   activeflag = false, 
        modifieddate = now(),
	modifieduser = 12 
  where  studentstestid in (9218801,9219464);
	
update studentstests 
      set activeflag = false, 
          modifieddate = now(),
	  modifieduser = 12
 where id in (9218801,9219464);

--english	
update studentstests set enrollmentid = 2370562, testsessionid=2179199, modifieddate = now(),
	modifieduser = 12  where id in (8852081);
--math	
update studentstests set enrollmentid = 2370562, testsessionid=2178741, modifieddate = now(),
	modifieduser = 12  where id in (8814252);
*/		
--===============================================================================================================
-- ROW:2
 --Student ID	Bldg test completed	Move test to bldg	Tests to move(subjects)	Service Desk
---5833988705	  4952	                     4976	            MDPT	        Connie B.
--===============================================================================================================
update studentstestsections 
  set   activeflag = false, 
        modifieddate = now(),
	modifieduser = 12 
  where  studentstestid in (8712625);
	
update studentstests 
      set activeflag = false, 
          modifieddate = now(),
	  modifieduser = 12
 where id = 8712625;
--english 	
update studentstests set enrollmentid = 2137645, testsessionid=2175346, modifieddate = now(),
	modifieduser = 12  where id = 8565917;

--===============================================================================================================
-- ROW:3
 --Student ID	Bldg test completed	Move test to bldg	Tests to move(subjects)	Service Desk
-- 5821732026	9043	1600	MDPT MPT	Steve W.
--===============================================================================================================
update studentstestsections 
  set   activeflag = false, 
        modifieddate = now(),
	modifieduser = 12 
  where  studentstestid in (9341629,9341611);
	
update studentstests 
      set activeflag = false, 
          modifieddate = now(),
	  modifieduser = 12
 where id in  (9341629,9341611);

--english	
update studentstests set enrollmentid = 2380421, testsessionid=2176624, modifieddate = now(),
	modifieduser = 12  where id in (8579117);
--math
update studentstests set enrollmentid = 2380421, testsessionid=2181434, modifieddate = now(),
	modifieduser = 12  where id in (8897845);
	
--===============================================================================================================
-- ROW:4
-- Student ID	Bldgtestcompleted	Movetesttobldg	Teststomove(subjects)	Service Desk
-- 2045517616	8532	9894	MPT	Steve W.
--===============================================================================================================
update studentstestsections 
  set   activeflag = false, 
        modifieddate = now(),
	modifieduser = 12 
  where  studentstestid in (8702588);
	
update studentstests 
      set activeflag = false, 
          modifieddate = now(),
	  modifieduser = 12
 where id in (8702588);

--math	
update studentstests set enrollmentid = 2111407, testsessionid=2178882, modifieddate = now(),
	modifieduser = 12  where id in (8677755);

--===============================================================================================================
-- ROW:5
-- Student ID	Bldgtestcompleted	Movetesttobldg	Teststomove(subjects)	Service Desk
-- 2216570656	0774	9019	MDPT MPT	Howie
--===============================================================================================================

update studentstestsections 
  set   activeflag = false, 
        modifieddate = now(),
	modifieduser = 12 
  where  studentstestid in (8729076,8760971);
	
update studentstests 
      set activeflag = false, 
          modifieddate = now(),
	  modifieduser = 12
 where id in (8729076,8760971);


--english	
update studentstests set enrollmentid = 2180912, testsessionid=2179627, modifieddate = now(),
	modifieduser = 12  where id in (8753710);
--math	
update studentstests set enrollmentid = 2180912, testsessionid=2179302, modifieddate = now(),
	modifieduser = 12  where id in (8722981);	
--===============================================================================================================
-- ROW:6
-- Student ID	Bldgtestcompleted	Movetesttobldg	Teststomove(subjects)	Service Desk
-- 8135279892	1833	4824	MDPT MPT	Howie
--===============================================================================================================
update studentstestsections 
  set   activeflag = false, 
        modifieddate = now(),
	modifieduser = 12 
  where  studentstestid in (9032660,8864972);
	
update studentstests 
      set activeflag = false, 
          modifieddate = now(),
	  modifieduser = 12
 where id in (9032660,8864972);
--english	
update studentstests set enrollmentid = 2313668, testsessionid=2175224, modifieddate = now(),
	modifieduser = 12  where id in (8800023);
--math	
update studentstests set enrollmentid = 2313668, testsessionid=2180574, modifieddate = now(),
	modifieduser = 12  where id in (9002071);
	
--===============================================================================================================
-- ROW:7
-- Student ID	Bldgtestcompleted	Movetesttobldg	Teststomove(subjects)	Service Desk
-- 5410595033	4828	4824	MDPT MPT	Howie
--===============================================================================================================
update studentstestsections 
  set   activeflag = false, 
        modifieddate = now(),
	modifieduser = 12 
  where  studentstestid in (9038070,8883472);
	
update studentstests 
      set activeflag = false, 
          modifieddate = now(),
	  modifieduser = 12
 where id in (9038070,8883472);

--math	
update studentstests set enrollmentid = 2359621, testsessionid=2180574, modifieddate = now(),
	modifieduser = 12  where id in (8937193);
--english	
update studentstests set enrollmentid = 2359621, testsessionid=2175224, modifieddate = now(),
	modifieduser = 12  where id in (8665824);	
--===============================================================================================================
-- ROW:8
-- Student ID	Bldgtestcompleted	Movetesttobldg	Teststomove(subjects)	Service Desk
-- 4224568713	6825	5558	MDPT MPT	Connie B.
--===============================================================================================================
update studentstestsections 
  set   activeflag = false, 
        modifieddate = now(),
	modifieduser = 12 
  where  studentstestid in (9191502,9191833);
	
update studentstests 
      set activeflag = false, 
          modifieddate = now(),
	  modifieduser = 12
 where id in (9191502,9191833);

--math 
update studentstests set enrollmentid = 2368895, testsessionid=2183404, modifieddate = now(),
	modifieduser = 12  where id in (8919125);
--english 
update studentstests set enrollmentid = 2368895, testsessionid=2179659, modifieddate = now(),
	modifieduser = 12  where id in (8599311);	
			
COMMIT;

	