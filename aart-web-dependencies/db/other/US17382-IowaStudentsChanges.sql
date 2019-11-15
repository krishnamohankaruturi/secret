----US17382 : Iowa student changes

-- Inactivate wrong students
update student set activeflag = false where id in (1204542,1204678,1209870,1204551,1204550,1238263,1204598,1226970,1206265,1208575,1226973,1204394,1206243,1211226,1204553,1218098,1220573,
1228224,1206258,1226971,1204081,1206259,1208035,1208039,1204601,1206266,1204324,1152010,1204134,1207515,1204445,1226972,1226968,1275569,1204845,1217740);

--
update studentassessmentprogram set activeflag = false where studentid in (1204542,1204678,1209870,1204551,1204550,1238263,1204598,1226970,1206265,1208575,1226973,1204394,1206243,1211226,1204553,1218098,1220573,
1228224,1206258,1226971,1204081,1206259,1208035,1208039,1204601,1206266,1204324,1152010,1204134,1207515,1204445,1226972,1226968,1275569,1204845,1217740);

--Update Enrollment Rosters

--update enrollmentsrosters set enrollmentid = 1901660 where enrollmentid = 1769855;
update enrollmentsrosters set enrollmentid = 1901660 where id = 13221842;

--Update  enrollments with new studentids

update enrollment set activeflag = false where id = 1998457;
update enrollment set activeflag = false where id = 2070731;
update enrollment set activeflag = false where id = 1769855;
update enrollment set activeflag = false where id = 1704194;

update enrollment set studentid =855448 where id =1770870;
update enrollment set studentid =877799 where id =1966648;
update enrollment set studentid =679334 where id =1966645;
update enrollment set studentid =873138 where id =1904488;
update enrollment set studentid =890229 where id =1791665;
update enrollment set studentid =909804 where id =1799708;
update enrollment set studentid =909805 where id =1799720;
update enrollment set studentid =881293 where id =1801257;
update enrollment set studentid =863505 where id =1770435;
update enrollment set studentid =855167 where id =1770119;
update enrollment set studentid =873135 where id =1791645;
update enrollment set studentid =862227 where id =1787621;
update enrollment set studentid =868570 where id =1797686;
update enrollment set studentid =679193 where id =1842099;
update enrollment set studentid =900171 where id =1898863;
update enrollment set studentid =877796 where id =1966650;
update enrollment set studentid =877800 where id =1966647;
update enrollment set studentid =890228 where id =1791666;
update enrollment set studentid =857417 where id =1777503;
update enrollment set studentid =857728 where id =1769043;
update enrollment set studentid =860603 where id =1771209;
update enrollment set studentid =800111 where id =1770914;
update enrollment set studentid =877797 where id =1966649;
update enrollment set studentid =890230 where id =1791566;
update enrollment set studentid =854143 where id =1771530;
update enrollment set studentid =876849 where id =1771198;
update enrollment set studentid =890344 where id =1806705;
update enrollment set studentid =873134 where id =1791641;
update enrollment set studentid =856762 where id =1770912;
update enrollment set studentid =856761 where id =1770911;
update enrollment set studentid =857878 where id =2273256;
update enrollment set studentid =853227 where id =1768748;
update enrollment set studentid =872911 where id =1920032;

-- Inactivate old profile attibutes and update new attributes with correct studentid

update studentprofileitemattributevalue set activeflag = false where studentid in 
(855448,877799,679334,873138,890229,909804,909805,881293,863505,855167,873135,862227,868570,679193,900171,877796,877800,890228,857417,857728,860603,800111,877797,890230,854143,876849,890344,873134,856762,856761,857878,853227,872911);

update studentprofileitemattributevalue set studentid = 855448 where studentid = 1204542 ;
update studentprofileitemattributevalue set studentid = 877799 where studentid = 1226971 ;
update studentprofileitemattributevalue set studentid = 679334 where studentid = 1226968 ;
update studentprofileitemattributevalue set studentid = 873138 where studentid = 1218098 ;
update studentprofileitemattributevalue set studentid = 890229 where studentid = 1206265 ;
update studentprofileitemattributevalue set studentid = 909804 where studentid = 1208035 ;
update studentprofileitemattributevalue set studentid = 909805 where studentid = 1208039 ;
update studentprofileitemattributevalue set studentid = 881293 where studentid = 1208575 ;
update studentprofileitemattributevalue set studentid = 863505 where studentid = 1204445 ;
update studentprofileitemattributevalue set studentid = 855167 where studentid = 1204394 ;
update studentprofileitemattributevalue set studentid = 873135 where studentid = 1206259 ;
update studentprofileitemattributevalue set studentid = 862227 where studentid = 1152010 ;
update studentprofileitemattributevalue set studentid = 868570 where studentid = 1207515 ;
update studentprofileitemattributevalue set studentid = 679193 where studentid = 1211226 ;
update studentprofileitemattributevalue set studentid = 900171 where studentid = 1217740 ;
update studentprofileitemattributevalue set studentid = 877796 where studentid = 1226973 ;
update studentprofileitemattributevalue set studentid = 877800 where studentid = 1226970 ;
update studentprofileitemattributevalue set studentid = 890228 where studentid = 1206266 ;
update studentprofileitemattributevalue set studentid = 857417 where studentid = 1204845 ;
update studentprofileitemattributevalue set studentid = 857728 where studentid = 1204134 ;
update studentprofileitemattributevalue set studentid = 860603 where studentid = 1204601 ;
update studentprofileitemattributevalue set studentid = 800111 where studentid = 1204553 ;
update studentprofileitemattributevalue set studentid = 877797 where studentid = 1226972 ;
update studentprofileitemattributevalue set studentid = 890230 where studentid = 1206243 ;
update studentprofileitemattributevalue set studentid = 854143 where studentid = 1204678 ;
update studentprofileitemattributevalue set studentid = 876849 where studentid = 1204598 ;
update studentprofileitemattributevalue set studentid = 890344 where studentid = 1209870 ;
update studentprofileitemattributevalue set studentid = 873134 where studentid = 1206258 ;
update studentprofileitemattributevalue set studentid = 856762 where studentid = 1204551 ;
update studentprofileitemattributevalue set studentid = 856761 where studentid = 1204550 ;
update studentprofileitemattributevalue set studentid = 857878 where studentid = 1275569 ;
update studentprofileitemattributevalue set studentid = 853227 where studentid = 1204081 ;
update studentprofileitemattributevalue set studentid = 872911 where studentid = 1220573 ;

-- update studentsresponses with correct studentids

update studentsresponses set studentid = 855448 where studentstestsid in (select id from studentstests where studentid = 1204542) ;
update studentsresponses set studentid = 877799 where studentstestsid in (select id from studentstests where studentid = 1226971) ;
update studentsresponses set studentid = 679334 where studentstestsid in (select id from studentstests where studentid = 1226968) ;
update studentsresponses set studentid = 873138 where studentstestsid in (select id from studentstests where studentid = 1218098) ;
update studentsresponses set studentid = 890229 where studentstestsid in (select id from studentstests where studentid = 1206265) ;
update studentsresponses set studentid = 909804 where studentstestsid in (select id from studentstests where studentid = 1208035) ;
update studentsresponses set studentid = 909805 where studentstestsid in (select id from studentstests where studentid = 1208039) ;
update studentsresponses set studentid = 881293 where studentstestsid in (select id from studentstests where studentid = 1208575) ;
update studentsresponses set studentid = 863505 where studentstestsid in (select id from studentstests where studentid = 1204445) ;
update studentsresponses set studentid = 855167 where studentstestsid in (select id from studentstests where studentid = 1204394) ;
update studentsresponses set studentid = 873135 where studentstestsid in (select id from studentstests where studentid = 1206259) ;
update studentsresponses set studentid = 862227 where studentstestsid in (select id from studentstests where studentid = 1152010) ;
update studentsresponses set studentid = 868570 where studentstestsid in (select id from studentstests where studentid = 1207515) ;
update studentsresponses set studentid = 679193 where studentstestsid in (select id from studentstests where studentid = 1211226) ;
update studentsresponses set studentid = 900171 where studentstestsid in (select id from studentstests where studentid = 1217740) ;
update studentsresponses set studentid = 877796 where studentstestsid in (select id from studentstests where studentid = 1226973) ;
update studentsresponses set studentid = 877800 where studentstestsid in (select id from studentstests where studentid = 1226970) ;
update studentsresponses set studentid = 890228 where studentstestsid in (select id from studentstests where studentid = 1206266) ;
update studentsresponses set studentid = 857417 where studentstestsid in (select id from studentstests where studentid = 1204845) ;
update studentsresponses set studentid = 857728 where studentstestsid in (select id from studentstests where studentid = 1204134) ;
update studentsresponses set studentid = 860603 where studentstestsid in (select id from studentstests where studentid = 1204601) ;
update studentsresponses set studentid = 800111 where studentstestsid in (select id from studentstests where studentid = 1204553) ;
update studentsresponses set studentid = 877797 where studentstestsid in (select id from studentstests where studentid = 1226972) ;
update studentsresponses set studentid = 890230 where studentstestsid in (select id from studentstests where studentid = 1206243) ;
update studentsresponses set studentid = 854143 where studentstestsid in (select id from studentstests where studentid = 1204678) ;
update studentsresponses set studentid = 876849 where studentstestsid in (select id from studentstests where studentid = 1204598) ;
update studentsresponses set studentid = 890344 where studentstestsid in (select id from studentstests where studentid = 1209870) ;
update studentsresponses set studentid = 873134 where studentstestsid in (select id from studentstests where studentid = 1206258) ;
update studentsresponses set studentid = 856762 where studentstestsid in (select id from studentstests where studentid = 1204551) ;
update studentsresponses set studentid = 856761 where studentstestsid in (select id from studentstests where studentid = 1204550) ;
update studentsresponses set studentid = 857878 where studentstestsid in (select id from studentstests where studentid = 1275569) ;
update studentsresponses set studentid = 853227 where studentstestsid in (select id from studentstests where studentid = 1204081) ;
update studentsresponses set studentid = 872911 where studentstestsid in (select id from studentstests where studentid = 1220573) ;

-- update studentstests with correct studentids

update studentstests set studentid = 855448 where studentid = 1204542 ;
update studentstests set studentid = 877799 where studentid = 1226971 ;
update studentstests set studentid = 679334 where studentid = 1226968 ;
update studentstests set studentid = 873138 where studentid = 1218098 ;
update studentstests set studentid = 890229 where studentid = 1206265 ;
update studentstests set studentid = 909804 where studentid = 1208035 ;
update studentstests set studentid = 909805 where studentid = 1208039 ;
update studentstests set studentid = 881293 where studentid = 1208575 ;
update studentstests set studentid = 863505 where studentid = 1204445 ;
update studentstests set studentid = 855167 where studentid = 1204394 ;
update studentstests set studentid = 873135 where studentid = 1206259 ;
update studentstests set studentid = 862227 where studentid = 1152010 ;
update studentstests set studentid = 868570 where studentid = 1207515 ;
update studentstests set studentid = 679193 where studentid = 1211226 ;
update studentstests set studentid = 900171 where studentid = 1217740 ;
update studentstests set studentid = 877796 where studentid = 1226973 ;
update studentstests set studentid = 877800 where studentid = 1226970 ;
update studentstests set studentid = 890228 where studentid = 1206266 ;
update studentstests set studentid = 857417 where studentid = 1204845 ;
update studentstests set studentid = 857728 where studentid = 1204134 ;
update studentstests set studentid = 860603 where studentid = 1204601 ;
update studentstests set studentid = 800111 where studentid = 1204553 ;
update studentstests set studentid = 877797 where studentid = 1226972 ;
update studentstests set studentid = 890230 where studentid = 1206243 ;
update studentstests set studentid = 854143 where studentid = 1204678 ;
update studentstests set studentid = 876849 where studentid = 1204598 ;
update studentstests set studentid = 890344 where studentid = 1209870 ;
update studentstests set studentid = 873134 where studentid = 1206258 ;
update studentstests set studentid = 856762 where studentid = 1204551 ;
update studentstests set studentid = 856761 where studentid = 1204550 ;
update studentstests set studentid = 857878 where studentid = 1275569 ;
update studentstests set studentid = 853227 where studentid = 1204081 ;
update studentstests set studentid = 872911 where studentid = 1220573 ;



-- Update ititestsessionhistory with correct studentids

update ititestsessionhistory set studentid = 855448 where studentid = 1204542 ;
update ititestsessionhistory set studentid = 877799 where studentid = 1226971 ;
update ititestsessionhistory set studentid = 679334 where studentid = 1226968 ;
update ititestsessionhistory set studentid = 873138 where studentid = 1218098 ;
update ititestsessionhistory set studentid = 890229 where studentid = 1206265 ;
update ititestsessionhistory set studentid = 909804 where studentid = 1208035 ;
update ititestsessionhistory set studentid = 909805 where studentid = 1208039 ;
update ititestsessionhistory set studentid = 881293 where studentid = 1208575 ;
update ititestsessionhistory set studentid = 863505 where studentid = 1204445 ;
update ititestsessionhistory set studentid = 855167 where studentid = 1204394 ;
update ititestsessionhistory set studentid = 873135 where studentid = 1206259 ;
update ititestsessionhistory set studentid = 862227 where studentid = 1152010 ;
update ititestsessionhistory set studentid = 868570 where studentid = 1207515 ;
update ititestsessionhistory set studentid = 679193 where studentid = 1211226 ;
update ititestsessionhistory set studentid = 900171 where studentid = 1217740 ;
update ititestsessionhistory set studentid = 877796 where studentid = 1226973 ;
update ititestsessionhistory set studentid = 877800 where studentid = 1226970 ;
update ititestsessionhistory set studentid = 890228 where studentid = 1206266 ;
update ititestsessionhistory set studentid = 857417 where studentid = 1204845 ;
update ititestsessionhistory set studentid = 857728 where studentid = 1204134 ;
update ititestsessionhistory set studentid = 860603 where studentid = 1204601 ;
update ititestsessionhistory set studentid = 800111 where studentid = 1204553 ;
update ititestsessionhistory set studentid = 877797 where studentid = 1226972 ;
update ititestsessionhistory set studentid = 890230 where studentid = 1206243 ;
update ititestsessionhistory set studentid = 854143 where studentid = 1204678 ;
update ititestsessionhistory set studentid = 876849 where studentid = 1204598 ;
update ititestsessionhistory set studentid = 890344 where studentid = 1209870 ;
update ititestsessionhistory set studentid = 873134 where studentid = 1206258 ;
update ititestsessionhistory set studentid = 856762 where studentid = 1204551 ;
update ititestsessionhistory set studentid = 856761 where studentid = 1204550 ;
update ititestsessionhistory set studentid = 857878 where studentid = 1275569 ;
update ititestsessionhistory set studentid = 853227 where studentid = 1204081 ;
update ititestsessionhistory set studentid = 872911 where studentid = 1220573 ;

-- Inactivate old suveys

update survey set activeflag = false where studentid in 
(855448,877799,679334,873138,890229,909804,909805,881293,863505,855167,873135,862227,868570,679193,900171,877796,877800,890228,857417,857728,860603,800111,877797,890230,854143,876849,890344,873134,856762,856761,857878,853227,872911);

--Update latest Survey with correct studentid

update survey set studentid = 855448 where studentid = 1204542 ;
update survey set studentid = 877799 where studentid = 1226971 ;
update survey set studentid = 679334 where studentid = 1226968 ;
update survey set studentid = 873138 where studentid = 1218098 ;
update survey set studentid = 890229 where studentid = 1206265 ;
update survey set studentid = 909804 where studentid = 1208035 ;
update survey set studentid = 909805 where studentid = 1208039 ;
update survey set studentid = 881293 where studentid = 1208575 ;
update survey set studentid = 863505 where studentid = 1204445 ;
update survey set studentid = 855167 where studentid = 1204394 ;
update survey set studentid = 873135 where studentid = 1206259 ;
update survey set studentid = 862227 where studentid = 1152010 ;
update survey set studentid = 868570 where studentid = 1207515 ;
update survey set studentid = 679193 where studentid = 1211226 ;
update survey set studentid = 900171 where studentid = 1217740 ;
update survey set studentid = 877796 where studentid = 1226973 ;
update survey set studentid = 877800 where studentid = 1226970 ;
update survey set studentid = 890228 where studentid = 1206266 ;
update survey set studentid = 857417 where studentid = 1204845 ;
update survey set studentid = 857728 where studentid = 1204134 ;
update survey set studentid = 860603 where studentid = 1204601 ;
update survey set studentid = 800111 where studentid = 1204553 ;
update survey set studentid = 877797 where studentid = 1226972 ;
update survey set studentid = 890230 where studentid = 1206243 ;
update survey set studentid = 854143 where studentid = 1204678 ;
update survey set studentid = 876849 where studentid = 1204598 ;
update survey set studentid = 890344 where studentid = 1209870 ;
update survey set studentid = 873134 where studentid = 1206258 ;
update survey set studentid = 856762 where studentid = 1204551 ;
update survey set studentid = 856761 where studentid = 1204550 ;
update survey set studentid = 857878 where studentid = 1275569 ;
update survey set studentid = 853227 where studentid = 1204081 ;
update survey set studentid = 872911 where studentid = 1220573 ;

-- Update StateStudentIdentifier with correct value

update student set statestudentidentifier = '6747707806' where id = 1207283;
update student set statestudentidentifier = '8186118058' where id = 1205780;
update student set statestudentidentifier = '9548400257' where id = 1226967;
update student set statestudentidentifier = '2078109489' where id = 1210698;
update student set statestudentidentifier = '2532564490' where id = 1210702;
update student set statestudentidentifier = '5046527201' where id = 1204136;
update student set statestudentidentifier = '5297283253' where id = 1208034;
update student set statestudentidentifier = '4209035621' where id = 1217575;
update student set statestudentidentifier = '2327538561' where id = 1226966;
update student set statestudentidentifier = '3822035430' where id = 1226969;
update student set statestudentidentifier = '3007373676' where id = 1236215;
update student set statestudentidentifier = '8012818862' where id = 1220570;
update student set statestudentidentifier = '9684746116' where id = 1220571;
update student set statestudentidentifier = '5819659968' where id = 1204552;
update student set statestudentidentifier = '3040426469' where id = 1207889;
update student set statestudentidentifier = '2592293305' where id = 1204453;
update student set statestudentidentifier = '9704260806' where id = 1207601;
