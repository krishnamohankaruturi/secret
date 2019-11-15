--US14845
--Content Areas
--M
BEGIN;
update testcollection set contentareaid=440 where contentareaid in (200,298,422,423,424,425,426,427,428,429,430,436);
update test set contentareaid=440 where contentareaid in (200,298,422,423,424,425,426,427,428,429,430,436);
update roster set statesubjectareaid=440 where statesubjectareaid in (200,298,422,423,424,425,426,427,428,429,430,436);
delete from contentarea where id in (200,298,422,423,424,425,426,427,428,429,430,436);
COMMIT;
--ELA
BEGIN;
update testcollection set contentareaid=3 where contentareaid in (6,8,9,248,435);
update test set contentareaid=3 where contentareaid in (6,8,9,248,435);
update roster set statesubjectareaid=3 where statesubjectareaid in (6,8,9,248,435);
update studentsassessments set contentareaid=3 where contentareaid in (6,8,9,248,435);
delete from contentarea where id in (6,8,9,248,435);
COMMIT;
--Sci
BEGIN;
update testcollection set contentareaid=441 where contentareaid in (12,15);
update test set contentareaid=441 where contentareaid in (12,15);
update roster set statesubjectareaid=441 where statesubjectareaid in (12,15);
update studentsassessments set contentareaid=441 where contentareaid in (12,15);
delete from contentarea where id in (12,15);
COMMIT;
--SS
BEGIN;
update testcollection set contentareaid=443 where contentareaid in (13,14);
update test set contentareaid=443 where contentareaid in (13,14);
update roster set statesubjectareaid=443 where statesubjectareaid in (13,14);
update studentsassessments set contentareaid=443 where contentareaid in (13,14);
delete from contentarea where id in (13,14);
COMMIT;
--CTE
BEGIN;
update testcollection set contentareaid=444 where contentareaid in (261);
update test set contentareaid=444 where contentareaid in (261);
update roster set statesubjectareaid=444 where statesubjectareaid in (261);
update studentsassessments set contentareaid=444 where contentareaid in (261);
delete from contentarea where id in (261);
COMMIT;
--GKS
BEGIN;
update testcollection set contentareaid=445 where contentareaid in (247,259,380);
update test set contentareaid=445 where contentareaid in (247,259,380);
update roster set statesubjectareaid=445 where statesubjectareaid in (247,259,380);
update studentsassessments set contentareaid=445 where contentareaid in (247,259,380);
delete from contentarea where id in (247,259,380);
COMMIT;
--AgF&NR
BEGIN;
update testcollection set contentareaid=20 where contentareaid in (254,260,262,263,265,289);
update test set contentareaid=20 where contentareaid in (254,260,262,263,265,289);
update roster set statesubjectareaid=20 where statesubjectareaid in (254,260,262,263,265,289);
update studentsassessments set contentareaid=20 where contentareaid in (254,260,262,263,265,289);
delete from contentarea where id in (254,260,262,263,265,289);
COMMIT;
--OTH
BEGIN;
update testcollection set contentareaid=437 where contentareaid in (1,2,7,249,250,251,252,253,255,256,257,258,264,290,291,292,293,294,295,296,297,378,379,431,432,433,434,438,439) 
	or (contentareaid between 23 and 199) or (contentareaid between 201 and 246) or (contentareaid between 266 and 288) or (contentareaid between 300 and 375) or (contentareaid between 381 and 421);
update test set contentareaid=437 where contentareaid in (1,2,7,249,250,251,252,253,255,256,257,258,264,290,291,292,293,294,295,296,297,378,379,431,432,433,434,438,439) 
	or (contentareaid between 23 and 199) or (contentareaid between 201 and 246) or (contentareaid between 266 and 288) or (contentareaid between 300 and 375) or (contentareaid between 381 and 421);
update roster set statesubjectareaid=437 where statesubjectareaid in (1,2,7,249,250,251,252,253,255,256,257,258,264,290,291,292,293,294,295,296,297,378,379,431,432,433,434,438,439) 
	or (statesubjectareaid between 23 and 199) or (statesubjectareaid between 201 and 246) or (statesubjectareaid between 266 and 288) or (statesubjectareaid between 300 and 375) or (statesubjectareaid between 381 and 421);
update studentsassessments set contentareaid=437 where contentareaid in (1,2,7,249,250,251,252,253,255,256,257,258,264,290,291,292,293,294,295,296,297,378,379,431,432,433,434,438,439) 
	or (contentareaid between 23 and 199) or (contentareaid between 201 and 246) or (contentareaid between 266 and 288) or (contentareaid between 300 and 375) or (contentareaid between 381 and 421);	
delete from contentarea where id in (1,2,7,249,250,251,252,253,255,256,257,258,264,290,291,292,293,294,295,296,297,378,379,431,432,433,434,438,439) 
	or (id between 23 and 199) or (id between 201 and 246) or (id between 266 and 288) or (id between 300 and 375) or (id between 381 and 421);
COMMIT;
--Grade Courses
--not linked to anything
BEGIN;
delete from gradecourse where id in (0,13);
COMMIT;
--1
BEGIN;
update enrollment set currentgradelevel=3 where currentgradelevel=221;
delete from gradecourse where id =221;
COMMIT;
--2
BEGIN;
update enrollment set currentgradelevel=2 where currentgradelevel=222;
delete from gradecourse where id =222;
COMMIT;
--3
BEGIN;
update enrollment set currentgradelevel=86 where currentgradelevel=211;
delete from gradecourse where id =211;
COMMIT;
--4
BEGIN;
update enrollment set currentgradelevel=37 where currentgradelevel=212;
delete from gradecourse where id =212;
COMMIT;
--5
BEGIN;
update enrollment set currentgradelevel=7 where currentgradelevel=217;
delete from gradecourse where id =217;
COMMIT;
--6
BEGIN;
update enrollment set currentgradelevel=92 where currentgradelevel=218;
delete from gradecourse where id =218;
COMMIT;
--7
BEGIN;
update enrollment set currentgradelevel=64 where currentgradelevel=213;
delete from gradecourse where id =213;
COMMIT;
--8
BEGIN;
update enrollment set currentgradelevel=91 where currentgradelevel in (53,214);
delete from gradecourse where id in (53,214);
COMMIT;
--9
BEGIN;
update enrollment set currentgradelevel=31 where currentgradelevel in (54,219);
delete from gradecourse where id in (54,219);
COMMIT;
--10
BEGIN;
update enrollment set currentgradelevel=90 where currentgradelevel in (57,216);
delete from gradecourse where id in (57,216);
COMMIT;
--11
BEGIN;
update testcollection set gradecourseid=29, contentareaid=441 where gradecourseid=11 and contentareaid is null;
update testcollection set gradecourseid=125 where gradecourseid=11 and contentareaid=3;
update testcollection set gradecourseid=225 where gradecourseid=11 and contentareaid=437;
update testcollection set gradecourseid=146 where gradecourseid=11 and contentareaid=440;
update enrollment set currentgradelevel=29 where currentgradelevel in (56,215,11);
delete from gradecourse where id in (11,56,215);
COMMIT;
--12
BEGIN;
update enrollment set currentgradelevel=27 where currentgradelevel in (55,220);
delete from gradecourse where id in (55,220);
COMMIT;


--***##*#*#*#*#*  remove enrollmentsrosters, enrollment and these gradecourses 65,82,84,223,224
BEGIN;
--remove 2012 enrollments related to bad gradecourses
delete from enrollmentsrosters where enrollmentid in (select id from enrollment where currentgradelevel in (65,82,84,223,224) and currentschoolyear=2012);
delete from enrollment where id in (select id from enrollment where currentgradelevel in (65,82,84,223,224) and currentschoolyear=2012);
--change references to 2014 enrollments related to bad gradecourses
update enrollment set currentgradelevel=98 where id in (select id from enrollment where currentgradelevel in (65,82,84,223,224) and currentschoolyear=2014);
delete from gradecourse where id in (65,82,84,223,224);
COMMIT;

--fix tests/testcollections/contentframework/testlet with references to independent gradecourses
DO
$BODY$
DECLARE 
 tc1 record;

BEGIN
FOR tc1 IN (select tc.id, tc.gradecourseid, tc.contentareaid from testcollection tc
			join contentarea ca on ca.id = tc.contentareaid
			join gradecourse gc on gc.id = tc.gradecourseid
			where gc.contentareaid is null and gc.assessmentprogramgradesid is not null)
LOOP
	update testcollection set gradecourseid = (select id from gradecourse where contentareaid=tc1.contentareaid and abbreviatedname=(select abbreviatedname from gradecourse where id=tc1.gradecourseid)) where id=tc1.id;
END LOOP;
 
END;
$BODY$;

DO
$BODY$
DECLARE 
 t1 record;

BEGIN
FOR t1 IN (select t.id, t.gradecourseid, t.contentareaid from test t
			join contentarea ca on ca.id = t.contentareaid
			join gradecourse gc on gc.id = t.gradecourseid
			where gc.contentareaid is null and gc.assessmentprogramgradesid is not null)
LOOP
	update test set gradecourseid = (select id from gradecourse where contentareaid=t1.contentareaid and abbreviatedname=(select abbreviatedname from gradecourse where id=t1.gradecourseid)) where id = t1.id;
END LOOP;
 
END;
$BODY$;

DO
$BODY$
DECLARE 
 cf1 record;

BEGIN
FOR cf1 IN (select cf.id, cf.gradecourseid, cf.contentareaid from contentframework cf
			join contentarea ca on ca.id = cf.contentareaid
			join gradecourse gc on gc.id = cf.gradecourseid
			where gc.contentareaid is null and gc.assessmentprogramgradesid is not null)
LOOP
	update contentframework set gradecourseid = (select id from gradecourse where contentareaid=cf1.contentareaid and abbreviatedname=(select abbreviatedname from gradecourse where id=cf1.gradecourseid)) where id = cf1.id;
END LOOP;
 
END;
$BODY$;

DO
$BODY$
DECLARE 
 t1 record;

BEGIN
FOR t1 IN (select t.id, t.gradecourseid, t.contentareaid from testlet t
			join contentarea ca on ca.id = t.contentareaid
			join gradecourse gc on gc.id = t.gradecourseid
			where gc.contentareaid is null and gc.assessmentprogramgradesid is not null)
LOOP
	update testlet set gradecourseid = (select id from gradecourse where contentareaid=t1.contentareaid and abbreviatedname=(select abbreviatedname from gradecourse where id=t1.gradecourseid)) where id = t1.id;
END LOOP;
 
END;
$BODY$;

--fix tests/testcollections/contentframework/testlet with references to wrong gradecourses (contentareas don't match)
DO
$BODY$
DECLARE 
 tc1 record;

BEGIN
FOR tc1 IN (select tc.id, tc.gradecourseid, tc.contentareaid from testcollection tc
			join contentarea ca on ca.id = tc.contentareaid
			join gradecourse gc on gc.id = tc.gradecourseid
			where gc.contentareaid != tc.contentareaid)
LOOP
	update testcollection set gradecourseid = (select id from gradecourse where contentareaid=tc1.contentareaid and abbreviatedname=(select abbreviatedname from gradecourse where id=tc1.gradecourseid)) where id=tc1.id;
END LOOP;
 
END;
$BODY$;

DO
$BODY$
DECLARE 
 t1 record;

BEGIN
FOR t1 IN (select t.id, t.gradecourseid, t.contentareaid from test t
			join contentarea ca on ca.id = t.contentareaid
			join gradecourse gc on gc.id = t.gradecourseid
			where gc.contentareaid != t.contentareaid)
LOOP
	update test set gradecourseid = (select id from gradecourse where contentareaid=t1.contentareaid and abbreviatedname=(select abbreviatedname from gradecourse where id=t1.gradecourseid)) where id=t1.id;
END LOOP;
 
END;
$BODY$;

DO
$BODY$
DECLARE 
 t1 record;

BEGIN
FOR t1 IN (select t.id, t.gradecourseid, t.contentareaid from testlet t
			join contentarea ca on ca.id = t.contentareaid
			join gradecourse gc on gc.id = t.gradecourseid
			where gc.contentareaid != t.contentareaid)
LOOP
	update testlet set gradecourseid = (select id from gradecourse where contentareaid=t1.contentareaid and abbreviatedname=(select abbreviatedname from gradecourse where id=t1.gradecourseid)) where id=t1.id;
END LOOP;
 
END;
$BODY$;

BEGIN;
update contentframework set gradecourseid=198 where contentareaid=448;
COMMIT;

DO
$BODY$
DECLARE 
 cf1 record;

BEGIN
FOR cf1 IN (select cf.id, cf.gradecourseid, cf.contentareaid from contentframework cf
			join contentarea ca on ca.id = cf.contentareaid
			join gradecourse gc on gc.id = cf.gradecourseid
			where gc.contentareaid != cf.contentareaid)
LOOP
	update contentframework set gradecourseid = (select id from gradecourse where contentareaid=cf1.contentareaid and abbreviatedname=(select abbreviatedname from gradecourse where id=cf1.gradecourseid)) where id = cf1.id;
END LOOP;
 
END;
$BODY$;

--fix old referenced data not in contentarea/gradecourse anymore
DO
$BODY$
DECLARE 
 tct1 record;

BEGIN
FOR tct1 IN (select tct.testcollectionid, tct.testid, tc.gradecourseid, tc.contentareaid 
		from test t 
		join testcollectionstests tct on t.id=tct.testid 
		join testcollection tc on tc.id = tct.testcollectionid 
		where t.contentareaid not in (select id from contentarea))
LOOP
	update test set gradecourseid = tct1.gradecourseid, contentareaid=tct1.contentareaid where id=tct1.testid;
END LOOP;

END;
$BODY$;

DO
$BODY$
DECLARE 
 tct1 record;

BEGIN
FOR tct1 IN (select tct.testcollectionid, tct.testid, tc.gradecourseid, tc.contentareaid
		from test t 
		join testcollectionstests tct on t.id=tct.testid 
		join testcollection tc on tc.id = tct.testcollectionid 
		where t.gradecourseid not in (select id from gradecourse))
LOOP
	update test set gradecourseid = tct1.gradecourseid, contentareaid=tct1.contentareaid where id=tct1.testid;
END LOOP;

END;
$BODY$;		