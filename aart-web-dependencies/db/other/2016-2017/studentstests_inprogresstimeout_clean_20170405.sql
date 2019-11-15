update studentstests set status = 85,modifieddate = now()
where id in 
(
select st.id 
from studentstests st
join testsession ts on (st.testsessionid = ts.id)
join operationaltestwindow otw on (ts.operationaltestwindowid = otw.id)
where st.status = 659 and st.activeflag is true and date(st.modifieddate) >= '2017-04-04'
and otw.id in (10172,10174)
);