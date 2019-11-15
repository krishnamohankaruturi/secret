-- Student ID-2552283997

update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1084254,1095015);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1084254,1095015));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1084254,1095015);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1084254,1095015);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=917728;
update enrollmentsrosters set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (7759869,7759870);

-- Student ID-8677316967

update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1084165,1095001);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1084165,1095001));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1084165,1095001);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1084165,1095001);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=917712;
update enrollmentsrosters set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (7759828,7759825);

-- Student ID-5191962372

update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1084070,1094984);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1084070,1094984));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1084070,1094984);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1084070,1094984);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=917337;
update enrollmentsrosters set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (7759742,7759741);

-- Student ID-4505795465

update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1084213,1095010);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1084213,1095010));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1084213,1095010);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1084213,1095010);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=917722;
update enrollmentsrosters set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (7759856,7759855);

-- Student ID-5866426760   
 
update studentstests set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1084154,1094999);
update studentstestsections set activeflag=false,modifieddate=CURRENT_TIMESTAMP where studentstestid in (select id from studentstests where testsessionid in (1084154,1094999));
update testsession set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (1084154,1094999);
update studenttrackerband set activeflag=false,modifieddate=CURRENT_TIMESTAMP where testsessionid in (1084154,1094999);
update studenttracker set status='UNTRACKED',modifieddate=CURRENT_TIMESTAMP where studentid=917711;
update enrollmentsrosters set activeflag=false,modifieddate=CURRENT_TIMESTAMP where id in (7759824,7759821);
