--ELA Total Scores
update studentsresponses
set activeflag = false
where studentid = 1176354 and testid = 38689 and taskvariantid = 330150 and studentstestsid = 12104165
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38689 and tv.id = 330150);

update studentsresponses
set activeflag = false
where studentid = 158392 and testid = 38840 and taskvariantid  in (331704,331706,331703) and studentstestsid = 12114639 
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38840 and tv.id in (331704,331706,331703));


update studentsresponses
set activeflag = false
where studentid = 158392 and testid = 38840 and taskvariantid = 331706 and studentstestsid = 12114639
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38840 and tv.id = 331706);


update studentsresponses
set activeflag = false
where studentid = 158392 and testid = 38840 and taskvariantid = 331704 and studentstestsid = 12114639
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38840 and tv.id = 331704);

update studentsresponses
set activeflag = false
where studentid = 849113 and testid = 38840 and taskvariantid = 331705 and studentstestsid = 11973944
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38840 and tv.id = 331705);

update studentsresponses
set activeflag = false
where studentid = 240132 and testid = 38855 and taskvariantid = 331951 and studentstestsid = 12046969
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38855 and tv.id = 331951);

update studentsresponses
set activeflag = false
where studentid = 586494 and testid = 38855 and taskvariantid = 331948 and studentstestsid = 12075072
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38855 and tv.id = 331948);

update studentsresponses
set activeflag = false
where studentid = 634636 and testid = 38876 and taskvariantid = 332305 and studentstestsid = 9992878
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38876 and tv.id = 332305);

update studentsresponses
set activeflag = false
where studentid = 669088 and testid = 38868 and taskvariantid = 332174 and studentstestsid = 12057662
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38868 and tv.id = 332174);

--MATH Total Scores 
update studentsresponses
set activeflag = false
where studentid = 721288 and testid = 38689 and taskvariantid = 330150 and studentstestsid = 12104165
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38689 and tv.id = 330150);

update studentsresponses
set activeflag = false
where studentid = 257498 and testid = 38476 and taskvariantid in (329035,329040,329038) and studentstestsid = 12192120
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=38476 and tv.id in (329035,329040,329038));

update studentsresponses
set activeflag = false
where studentid = 688688 and testid = 39184 and taskvariantid in (335959,335964) and studentstestsid = 12158218
and testsectionid not in (select ts.id as testsectionid from test t inner join testsection ts on ts.testid = t.id inner join testsectionstaskvariants tstv on tstv.testsectionid = ts.id inner join taskvariant tv on tv.id = tstv.taskvariantid 
where t.id=39184 and tv.id  in (335959,335964));

