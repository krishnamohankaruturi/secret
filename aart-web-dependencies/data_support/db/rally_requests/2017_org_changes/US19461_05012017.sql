/*
--validation
-- student 1390062 :with dual roster example
select er.rosterid,er.id erid,e.studentid,er.enrollmentid ,er.activeflag er_active,er.modifieddate,r.coursesectionname, statesubjectareaid,teacherid from enrollment e
inner join enrollmentsrosters er on er.enrollmentid=e.id
inner join roster r on r.id=er.rosterid
where studentid=863609
order by statesubjectareaid,er.activeflag;

select ts.id tsid,ts.rosterid,st.id stid,st.enrollmentid,st.testid,st.status,attendanceschoolid,contentareaid,st.activeflag,st.id ,ts.source
from studentstests st 
inner join testsession ts on ts.id=st.testsessionid
inner join testcollection tc on tc.id=ts.testcollectionid
where studentid =863609 order by contentareaid,enrollmentid,rosterid;
*/
begin;
--studentid:317658
--ELA

update ititestsessionhistory
set    studentenrlrosterid=15595361,
       modifieddate =now(),
       modifieduser =174744
where studentid =317658 and rosterid=1161170;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15595363,
       modifieddate =now(),
       modifieduser =174744
where studentid =317658 and rosterid=1161171;


--studentid:409084
--ELA
set    studentenrlrosterid=15595035,
       modifieddate =now(),
       modifieduser =174744
where studentid =409084 and rosterid=1161105;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15595037,
       modifieddate =now(),
       modifieduser =174744
where studentid =409084 and rosterid=1161106;

--sid:500671

--ELA
set    studentenrlrosterid=15595036,
       modifieddate =now(),
       modifieduser =174744
where studentid =500671 and rosterid=1161105;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15595038,
       modifieddate =now(),
       modifieduser =174744
where studentid =500671 and rosterid=1161106;


--sid:666043
--ELA
update ititestsessionhistory
set    studentenrlrosterid=14611051,
       modifieddate =now(),
       modifieduser =174744
where studentid =666043 and rosterid=1076683;

--Math

update ititestsessionhistory
set    studentenrlrosterid=14611026,
       modifieddate =now(),
       modifieduser =174744
where studentid =666043 and rosterid=1076682;

update studentstests 
set    enrollmentid = 2421359,
       modifieddate =now(),
       modifieduser =174744
where studentid =666043 and enrollmentid =2595866 ;

--sid:688765
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15593503,
       modifieddate =now(),
       modifieduser =174744
where studentid =688765 and rosterid=1073977;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15593504,
       modifieddate =now(),
       modifieduser =174744
where studentid =688765 and rosterid=1073978;

update studentstests 
set    enrollmentid = 2928095,
       modifieddate =now(),
       modifieduser =174744
where studentid =688765 and enrollmentid =2563383 ;


--sid:699788
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15592272,
       modifieddate =now(),
       modifieduser =174744
where studentid =699788 and rosterid=1160292;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15592273,
       modifieddate =now(),
       modifieduser =174744
where studentid =699788 and rosterid=1160293;

--sid:734624
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15590179,
       modifieddate =now(),
       modifieduser =174744
where studentid =734624 and rosterid=1159729;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15590181,
       modifieddate =now(),
       modifieduser =174744
where studentid =734624 and rosterid=1159730;

update studentstests 
set    enrollmentid = 2927264,
       modifieddate =now(),
       modifieduser =174744
where studentid =734624 and enrollmentid =2413585 ;


--sid:753629
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15593493,
       modifieddate =now(),
       modifieduser =174744
where studentid =753629 and rosterid=1073746;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15593494,
       modifieddate =now(),
       modifieduser =174744
where studentid =753629 and rosterid=1073750;

update studentstests 
set    enrollmentid = 2928092,
       modifieddate =now(),
       modifieduser =174744
where studentid =753629 and enrollmentid =2419008 ;

--sid:854698
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15587716,
       modifieddate =now(),
       modifieduser =174744
where studentid =854698 and rosterid=1095742;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15587717,
       modifieddate =now(),
       modifieduser =174744
where studentid =854698 and rosterid=1095743;

update studentstests 
set    enrollmentid = 2922314,
       modifieddate =now(),
       modifieduser =174744
where studentid =854698 and enrollmentid =2640907 ;

--sid:854943
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15596281,
       modifieddate =now(),
       modifieduser =174744
where studentid =854943 and rosterid=1069719;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15596282,
       modifieddate =now(),
       modifieduser =174744
where studentid =854943 and rosterid=1069717;

update studentstests 
set    enrollmentid = 2928714,
       modifieddate =now(),
       modifieduser =174744
where studentid =854943 and enrollmentid =2413667 ;

--sid:858023
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15595374,
       modifieddate =now(),
       modifieduser =174744
where studentid =858023 and rosterid=1160318;

--Math

update ititestsessionhistory
set    studentenrlrosterid=14908629,
       modifieddate =now(),
       modifieduser =174744
where studentid =858023 and rosterid=1089709;

--sid:859266
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15602236,
       modifieddate =now(),
       modifieduser =174744
where studentid =859266 and rosterid=1068969;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15602237,
       modifieddate =now(),
       modifieduser =174744
where studentid =859266 and rosterid=1068971;

update studentstests 
set    enrollmentid = 2933346,
       modifieddate =now(),
       modifieduser =174744
where studentid =859266 and enrollmentid =2422104 ;

--sid:860861

update studentstests 
set    enrollmentid = 2671527,
       modifieddate =now(),
       modifieduser =174744
where studentid =860861 and enrollmentid =2922409 ;


--sid:863609 good
--sid:866394
--Math

update ititestsessionhistory
set    studentenrlrosterid=15601839,
       modifieddate =now(),
       modifieduser =174744
where studentid =866394 and rosterid=1080427;

update studentstests 
set    enrollmentid = 2933274,
       modifieddate =now(),
       modifieduser =174744
where studentid =866394 and enrollmentid =2421633 ;

--sid:882573
--ELA

update ititestsessionhistory
set    studentenrlrosterid=15596280,
       modifieddate =now(),
       modifieduser =174744
where studentid =882573 and rosterid=1161497;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15596279,
       modifieddate =now(),
       modifieduser =174744
where studentid =882573 and rosterid=1161496;

update studentstests 
set    enrollmentid = 2922335,
       modifieddate =now(),
       modifieduser =174744
where studentid =882573 and enrollmentid =2412943 ;


--sid:890533
--ELA

update ititestsessionhistory
set    studentenrlrosterid=15601836,
       modifieddate =now(),
       modifieduser =174744
where studentid =890533 and rosterid=1080425;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15601837,
       modifieddate =now(),
       modifieduser =174744
where studentid =890533 and rosterid=1080427;

update studentstests 
set    enrollmentid = 2933273,
       modifieddate =now(),
       modifieduser =174744
where studentid =890533 and enrollmentid =2414595 ;



-------------------------------------------------------------------------------------------------------------

--sid: 1278232
--ELA
update testsession 
set    rosterid =1102025,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1278232) and rosterid=1130791 ;



--Math

update testsession 
set    rosterid =1102023,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1278232) and rosterid=1130795;



update studentstests 
set    enrollmentid = 2928053,
       modifieddate =now(),
       modifieduser =174744
where studentid =1278232 and enrollmentid =2610696 ;

--ssid:1217514
--Math

update ititestsessionhistory
set    studentenrlrosterid=15588196,
       modifieddate =now(),
       modifieduser =174744
where studentid =1217514 and rosterid=1159350;

update studentstests 
set    enrollmentid = 2922514,
       modifieddate =now(),
       modifieduser =174744
where studentid =1217514 and enrollmentid =2450204 ;


--sid:1208789
--ELA

update ititestsessionhistory
set    studentenrlrosterid=15593632,
       modifieddate =now(),
       modifieduser =174744
where studentid =1208789 and rosterid=1072063;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15593634,
       modifieddate =now(),
       modifieduser =174744
where studentid =1208789 and rosterid=1072031;

update studentstests 
set    enrollmentid = 2928131,
       modifieddate =now(),
       modifieduser =174744
where studentid =1208789 and enrollmentid =2419818 ;

--1206784
--ELA

update ititestsessionhistory
set    studentenrlrosterid=15589466,
       modifieddate =now(),
       modifieduser =174744
where studentid =1206784 and rosterid=1087271;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15589467,
       modifieddate =now(),
       modifieduser =174744
where studentid =1206784 and rosterid=1087272;

update studentstests 
set    enrollmentid = 2927119,
       modifieddate =now(),
       modifieduser =174744
where studentid =1206784 and enrollmentid =2418774 ;

--sid:1206735
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15595641,
       modifieddate =now(),
       modifieduser =174744
where studentid =1206735 and rosterid=1069372;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15595642,
       modifieddate =now(),
       modifieduser =174744
where studentid =1206735 and rosterid=1069646;

update studentstests 
set    enrollmentid = 2928588,
       modifieddate =now(),
       modifieduser =174744
where studentid =1206735 and enrollmentid =2426820 ;


--sid:1206005
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15600817,
       modifieddate =now(),
       modifieduser =174744
where studentid =1206005 and rosterid=1073182;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15600821,
       modifieddate =now(),
       modifieduser =174744
where studentid =1206005 and rosterid=1073186;

update studentstests 
set    enrollmentid = 2932987,
       modifieddate =now(),
       modifieduser =174744
where studentid =1206005 and enrollmentid =2797729 ;

--sid:1205859
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15601027,
       modifieddate =now(),
       modifieduser =174744
where studentid =1205859 and rosterid=1066761;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15601032,
       modifieddate =now(),
       modifieduser =174744
where studentid =1205859 and rosterid=1066762;

update studentstests 
set    enrollmentid = 2933018,
       modifieddate =now(),
       modifieduser =174744
where studentid =1205859 and enrollmentid =2419955 ;

--sid:1132907
--ELA
update testsession 
set    rosterid =1123135,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1132907) and rosterid=1140927 ;



--Math

update testsession 
set    rosterid =1123129,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1132907) and rosterid=1140926;



update studentstests 
set    enrollmentid = 2928118,
       modifieddate =now(),
       modifieduser =174744
where studentid =1132907 and enrollmentid =2878898 ;


--sid:1093014
--ELA
update testsession 
set    rosterid =1162157,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1093014) and rosterid=1135221;



update studentstests 
set    enrollmentid = 2933373,
       modifieddate =now(),
       modifieduser =174744
where studentid =1093014 and enrollmentid =2891383 ;


--sid:1084027
--ELA
update testsession 
set    rosterid =1067926,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1084027) and rosterid=1067929;

--sid:1071222  no active roster. so don't need update
--sid:1069642
--ELA
update testsession 
set    rosterid =1067796,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1069642) and rosterid=1133371;


--sid:1069263
--ELA
update testsession 
set    rosterid =1067796,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1069263) and rosterid=1133371;


--sid:1069249
--ELA
update testsession 
set    rosterid =1067796,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1069249) and rosterid=1093678;

--sid:1069203
--ELA
update testsession 
set    rosterid =1067796,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1069203) and rosterid=1100116;

--sid:1029843
--ELA
update ititestsessionhistory
set    studentenrlrosterid=15602099,
       modifieddate =now(),
       modifieduser =174744
where studentid =1029843 and rosterid=1161984;

--Math

update ititestsessionhistory
set    studentenrlrosterid=15602101,
       modifieddate =now(),
       modifieduser =174744
where studentid =1029843 and rosterid=1161986;

update studentstests 
set    enrollmentid = 2933317,
       modifieddate =now(),
       modifieduser =174744
where studentid =1029843 and enrollmentid =2419295 ;

--sid:969329
--ELA
update testsession 
set    rosterid =1162156,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =969329) and rosterid=1135400 ;


update studentstests 
set    enrollmentid = 2933372,
       modifieddate =now(),
       modifieduser =174744
where studentid =969329 and enrollmentid =2613295 ;

--studentid=928332
--ELA
update testsession 
set    rosterid =1067796,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =928332) and rosterid=1093677;

update ititestsessionhistory
set    rosterid=1067796,
       studentenrlrosterid=14584956,
       modifieddate =now(),
       modifieduser =174744
where studentid =928332 and rosterid=1093677;
--MATH
update testsession 
set    rosterid =1067797,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =928332) and rosterid=1093675;

update ititestsessionhistory
set    rosterid=1067797,
       studentenrlrosterid=14584962,
       modifieddate =now(),
       modifieduser =174744
where studentid =928332 and rosterid=1093675;

--studentid=928006
--ELA
update testsession 
set    rosterid =1070723,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =928006) and rosterid=1068322;

update ititestsessionhistory
set    rosterid=1070723,
       studentenrlrosterid=15595303,
       modifieddate =now(),
       modifieduser =174744
where studentid =928006 and rosterid=1068322;
--MATH
update testsession 
set    rosterid =1070724,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =928006) and rosterid=1068323;

update ititestsessionhistory
set    rosterid=1070724,
       studentenrlrosterid=15595304,
       modifieddate =now(),
       modifieduser =174744
where studentid =928006 and rosterid=1068323;

update studentstests 
set    enrollmentid = 2928535,
       modifieddate =now(),
       modifieduser =174744
where studentid =928006 and enrollmentid =2412503;


--studentid=927734
--ELA
update testsession 
set    rosterid =1067796,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =927734) and rosterid=1133371;

update ititestsessionhistory
set    rosterid=1067796,
       studentenrlrosterid=15075053,
       modifieddate =now(),
       modifieduser =174744
where studentid =927734 and rosterid=1133371;



--==================================================================================
--student:1278232
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1102025,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1278232) and rosterid=1130791;

update ititestsessionhistory
set    rosterid=1102025,
       studentenrlrosterid=15593322,
       modifieddate =now(),
       modifieduser =174744
where studentid =1278232 and rosterid=1130791;

--Math

update testsession 
set    rosterid =1102023,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1278232) and rosterid=1130795;

update ititestsessionhistory
set    rosterid=1102023,
       studentenrlrosterid=15593323,
       modifieddate =now(),
       modifieduser =174744
where studentid =1278232 and rosterid=1130795;

update studentstests 
set    enrollmentid = 2928053,
       modifieddate =now(),
       modifieduser =174744
where studentid =1278232 and enrollmentid =2610696 ;


commit;

--==================================================================================
--student:1281853
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1104737,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1281853) and rosterid=1114899;

update ititestsessionhistory
set    rosterid=1104737,
       studentenrlrosterid=15602282,
       modifieddate =now(),
       modifieduser =174744
where studentid =1281853 and rosterid=1114899;

--Math

update testsession 
set    rosterid =1104743,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1281853) and rosterid=1114901;

update ititestsessionhistory
set    rosterid=1104743,
       studentenrlrosterid=15602283,
       modifieddate =now(),
       modifieduser =174744
where studentid =1281853 and rosterid=1114901;

update studentstests 
set    enrollmentid = 2933359,
       modifieddate =now(),
       modifieduser =174744
where studentid =1281853 and enrollmentid =2621977 ;


commit;


--==================================================================================
--student:1308318
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1102658,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1308318) and rosterid=1128635;

update ititestsessionhistory
set    rosterid=1102658,
       studentenrlrosterid=15601907,
       modifieddate =now(),
       modifieduser =174744
where studentid =1308318 and rosterid=1128635;

--Math

update testsession 
set    rosterid =1102659,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1308318) and rosterid=1128641;

update ititestsessionhistory
set    rosterid=1102659,
       studentenrlrosterid=15601908,
       modifieddate =now(),
       modifieduser =174744
where studentid =1308318 and rosterid=1128641;

update studentstests 
set    enrollmentid = 2933295,
       modifieddate =now(),
       modifieduser =174744
where studentid =1308318 and enrollmentid =2758879 ;


commit;


--==================================================================================
--student:1321533
--==================================================================================
begin;

update studentstests 
set    enrollmentid = 2928594,
       modifieddate =now(),
       modifieduser =174744
where studentid =1321533 and enrollmentid =2426770 ;


commit;

--==================================================================================
--student:1324507
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1072116,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1324507) and rosterid=1070140;

update ititestsessionhistory
set    rosterid=1072116,
       studentenrlrosterid=15594851,
       modifieddate =now(),
       modifieduser =174744
where studentid =1324507 and rosterid=1070140;

--Math

update testsession 
set    rosterid =1072117,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1324507) and rosterid=1070141;

update ititestsessionhistory
set    rosterid=1072117,
       studentenrlrosterid=15594852,
       modifieddate =now(),
       modifieduser =174744
where studentid =1324507 and rosterid=1070141;

update studentstests 
set    enrollmentid = 2883736,
       modifieddate =now(),
       modifieduser =174744
where studentid =1324507 and enrollmentid =2414524 ;


commit;

--==================================================================================
--student:1325205
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1075520,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1325205) and rosterid=1072547;

update ititestsessionhistory
set    rosterid=1075520,
       studentenrlrosterid=15589309,
       modifieddate =now(),
       modifieduser =174744
where studentid =1325205 and rosterid=1072547;

--Math

update testsession 
set    rosterid =1075522,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1325205) and rosterid=1072549;

update ititestsessionhistory
set    rosterid=1075522,
       studentenrlrosterid=15589310,
       modifieddate =now(),
       modifieduser =174744
where studentid =1325205 and rosterid=1072549;

update studentstests 
set    enrollmentid = 2927058,
       modifieddate =now(),
       modifieduser =174744
where studentid =1325205 and enrollmentid =2419222 ;


commit;

--==================================================================================
--student:1325267
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1072063,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1325267) and rosterid=1072647;

update ititestsessionhistory
set    rosterid=1072063,
       studentenrlrosterid=15593633,
       modifieddate =now(),
       modifieduser =174744
where studentid =1325267 and rosterid=1072647;

--Math

update testsession 
set    rosterid =1072031,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1325267) and rosterid=1072649;

update ititestsessionhistory
set    rosterid=1072031,
       studentenrlrosterid=15593635,
       modifieddate =now(),
       modifieduser =174744
where studentid =1325267 and rosterid=1072649;

update studentstests 
set    enrollmentid = 2928132,
       modifieddate =now(),
       modifieduser =174744
where studentid =1325267 and enrollmentid =2419812 ;


commit;

--==================================================================================
--student:1325398
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1159749,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1325398) and rosterid=1072139;

update ititestsessionhistory
set    rosterid=1159749,
       studentenrlrosterid=15590252,
       modifieddate =now(),
       modifieduser =174744
where studentid =1325398 and rosterid=1072139;

--Math

update testsession 
set    rosterid =1159750,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1325398) and rosterid=1072140;

update ititestsessionhistory
set    rosterid=1159750,
       studentenrlrosterid=15590255,
       modifieddate =now(),
       modifieduser =174744
where studentid =1325398 and rosterid=1072140;

update studentstests 
set    enrollmentid = 2927279,
       modifieddate =now(),
       modifieduser =174744
where studentid =1325398 and enrollmentid =2420935 ;


commit;

--==================================================================================
--student:1328395
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1073346,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1328395) and rosterid=1080209;

update ititestsessionhistory
set    rosterid=1073346,
       studentenrlrosterid=15591331,
       modifieddate =now(),
       modifieduser =174744
where studentid =1328395 and rosterid=1080209;


update studentstests 
set    enrollmentid = 2927324,
       modifieddate =now(),
       modifieduser =174744
where studentid =1328395 and enrollmentid =2449704 ;


commit;
--==================================================================================
--student:1335746
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1069373,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1335746) and rosterid=1089513;

update ititestsessionhistory
set    rosterid=1069373,
       studentenrlrosterid=15601807,
       modifieddate =now(),
       modifieduser =174744
where studentid =1335746 and rosterid=1089513;

--Math

update testsession 
set    rosterid =1069647,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1335746) and rosterid=1089514;

update ititestsessionhistory
set    rosterid=1069647,
       studentenrlrosterid=15601810,
       modifieddate =now(),
       modifieduser =174744
where studentid =1335746 and rosterid=1089514;

update studentstests 
set    enrollmentid = 2933261,
       modifieddate =now(),
       modifieduser =174744
where studentid =1335746 and enrollmentid =2531037 ;


commit;

--==================================================================================
--student:1339287
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1162088,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1339287) and rosterid=1146306;

update ititestsessionhistory
set    rosterid=1162088,
       studentenrlrosterid=15602353,
       modifieddate =now(),
       modifieduser =174744
where studentid =1339287 and rosterid=1146306;

--Math

update testsession 
set    rosterid =1162090,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1339287) and rosterid=1146307;

update ititestsessionhistory
set    rosterid=1162090,
       studentenrlrosterid=15602355,
       modifieddate =now(),
       modifieduser =174744
where studentid =1339287 and rosterid=1146307;

update studentstests 
set    enrollmentid = 2933333,
       modifieddate =now(),
       modifieduser =174744
where studentid =1339287 and enrollmentid =2557753 ;


commit;


--==================================================================================
--student:1341207
--==================================================================================
begin;

update studentstests 
set    enrollmentid = 2927414,
       modifieddate =now(),
       modifieduser =174744
where studentid =1341207 and enrollmentid =2591579 ;


commit;

--==================================================================================
--student:1341818
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1140359,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1341818) and rosterid=1098343;

update ititestsessionhistory
set    rosterid=1140359,
       studentenrlrosterid=15592547,
       modifieddate =now(),
       modifieduser =174744
where studentid =1341818 and rosterid=1098343;

--Math

update testsession 
set    rosterid =1140360,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1341818) and rosterid=1098345;

update ititestsessionhistory
set    rosterid=1140360,
       studentenrlrosterid=15592548,
       modifieddate =now(),
       modifieduser =174744
where studentid =1341818 and rosterid=1098345;

update studentstests 
set    enrollmentid = 2927923,
       modifieddate =now(),
       modifieduser =174744
where studentid =1341818 and enrollmentid =2595962 ;


commit;

--==================================================================================
--student:1398663
--==================================================================================
begin;
--ELA
update testsession 
set    rosterid =1073346,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =1398663) and rosterid=1136227;

update ititestsessionhistory
set    rosterid=1073346,
       studentenrlrosterid=15591330,
       modifieddate =now(),
       modifieduser =174744
where studentid =1398663 and rosterid=1136227;


update studentstests 
set    enrollmentid = 2927323,
       modifieddate =now(),
       modifieduser =174744
where studentid =1398663 and enrollmentid =2823253 ;


commit;

--==================================================================================
--student:860861
--==================================================================================
begin;
--sci
update testsession 
set    rosterid =1112614,
       modifieddate =now(),
       modifieduser =174744
where id in (select testsessionid from studentstests where studentid =860861) and rosterid=1159334;

update ititestsessionhistory
set    rosterid=1112614,
       studentenrlrosterid=15602170,
       modifieddate =now(),
       modifieduser =174744
where studentid =860861 and rosterid=1159334;



update studentstests 
set    enrollmentid = 2671527,
       modifieddate =now(),
       modifieduser =174744
where studentid =860861 and enrollmentid =2922409 ;


commit;

