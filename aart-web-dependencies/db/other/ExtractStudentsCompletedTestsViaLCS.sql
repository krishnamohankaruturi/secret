-- need to run this query in TDE db
\copy ( select * from lcsentries) TO 'LCS_4_5_2016.csv' with CSV HEADER;

create temporary table tmp_LCS(lcsid text,studentstestsid bigint);

\COPY tmp_lcs FROM 'LCS_4_5_2016.csv' DELIMITER ',' CSV HEADER ;

-- update file name and date
\copy (select distinct st.statestudentidentifier, st.legalfirstname as studentfirstname, st.legallastname as studentlastname, otd.districtname, otd.statename,au.firstname as teacherfirstname, otd.schoolname, au.surname as teacherlastname, au.email as teacheremail, t.testname, ca.abbreviatedname as subject, stb.createddate as trackercreateddate, sts.enddatetime - interval '5 hours' as TestEndTime, stb.id as studenttrackerbandid, sts.id as studentstestid from studenttracker str join studenttrackerband stb on stb.studenttrackerid = str.id join studentstests sts on sts.testsessionid = stb.testsessionid join tmp_lcs tlcs on tlcs.studentstestsid = sts.id join contentarea ca on ca.id = str.contentareaid join test t on t.id = sts.testid join student st on st.id = str.studentid join testsession ts on ts.id = stb.testsessionid join roster r on r.id = ts.rosterid join aartuser au on au.id = r.teacherid join organizationtreedetail otd on otd.schoolid = ts.attendanceschoolid where sts.startdatetime >= '2016-03-26' and stb.operationalwindowid in (10156, 10134,10155,10152,10153,10144,10139,10135,10137,10140,10146) order by st.statestudentidentifier, stb.id) TO 'DLM_LCS_04-05-2016.csv' with CSV HEADER;
