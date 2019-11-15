--US18442: SR - EP - Production - HIGH - Active Test Sessions Not Assigned to Students

--select id from aartuser where uniquecommonidentifier = '2923489799' and activeflag is true
--StateStudentIdentifier: 7690553893/ELA
update testsession 
set rosterid = 955460
where id = 2384183 and rosterid = 1037363;
--StateStudentIdentifier: 7690553893/M
update testsession 
set rosterid = 955461
where id = 2384166 and rosterid = 1037364;

--StateStudentIdentifier: 1735720305/ELA 
update testsession 
set rosterid = 955460
where id = 2384145 and rosterid = 1037363;
--StateStudentIdentifier: 1735720305/M
update testsession 
set rosterid = 955461
where id = 2384144 and rosterid = 1037364;

--StateStudentIdentifier: 1356847498/ELA 
update testsession 
set rosterid = 955460
where id = 2384157 and rosterid = 1037363;
--StateStudentIdentifier: 1356847498/M
update testsession 
set rosterid = 955461
where id = 2384154 and rosterid = 1037364;

--StateStudentIdentifier: 2787456731/ELA 
update testsession 
set rosterid = 955460
where id = 2384215 and rosterid = 1037363;
--StateStudentIdentifier: 2787456731/M
update testsession 
set rosterid = 955461
where id = 2384190 and rosterid = 1037364;
--StateStudentIdentifier: 2787456731/Sci
update testsession 
set rosterid = 955462
where id = 2384176 and rosterid = 1037367;

--StateStudentIdentifier: 7076521849/ELA 
update testsession 
set rosterid = 955460
where id = 2384134 and rosterid = 1037363;
--StateStudentIdentifier: 7076521849/M
update testsession 
set rosterid = 955461
where id = 2384136 and rosterid = 1037364;
--StateStudentIdentifier: 7076521849/Sci
update testsession 
set rosterid = 955462
where id = 2384133 and rosterid = 1037367;



--select * from roster where teacherid = (select id from aartuser where uniquecommonidentifier = '3274375799' and activeflag is true);
--StateStudentIdentifier: 1254428488/ELA 
update testsession 
set rosterid = 955435
where id = 2384272 and rosterid = 1037369;
--StateStudentIdentifier: 1254428488/M
update testsession 
set rosterid = 954948
where id = 2384243 and rosterid = 1037370;


--StateStudentIdentifier: 2210926882/ELA: UPDATE NOT NEEDED
--StateStudentIdentifier: 2210926882/M: UPDATE NOT NEEDED

--StateStudentIdentifier: 3161816838/ELA 
update testsession 
set rosterid = 955435
where id = 2384264 and rosterid = 1037369;
--StateStudentIdentifier: 3161816838/M
update testsession 
set rosterid = 954948
where id = 2384231 and rosterid = 1037370;


--select * from roster where teacherid = (select id from aartuser where uniquecommonidentifier = '9928971625' and activeflag is true);
--StateStudentIdentifier: 5932694688/Sci 
update testsession 
set rosterid = 901946
where id = 2384185 and rosterid = 1037371;

--No change needed
--StateStudentIdentifier: 2005775037/Sci


--select * from roster where teacherid = (select id from aartuser where uniquecommonidentifier = '9984956164' and activeflag is true);
--StateStudentIdentifier: 4674643368/Sci 
update testsession 
set rosterid = 955437
where id = 2919390 and rosterid = 901942;

--StateStudentIdentifier: 3220634834/Sci: Change not needed

--StateStudentIdentifier: 3658814136/Sci 
update testsession 
set rosterid = 955437
where id = 2920297 and rosterid = 901942;

--StateStudentIdentifier: 7414507599/Sci 
update testsession 
set rosterid = 955437
where id = 2924019 and rosterid = 901942;

--StateStudentIdentifier: 4985976474/Sci 
update testsession 
set rosterid = 955437
where id = 2282036 and rosterid = 901942;


