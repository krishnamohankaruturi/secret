--1.a:validation: Find the all mandatory questions were not answered and iscompleted set true (note:part 1 regardless of yes or no)
update surveypagestatus
set iscompleted =false,
    modifieddate=now(),
    modifieduser=12
where id in (
with all_ques as
(
select sv.id surveyid,sl.globalpagenum,count(distinct sl.labelnumber) cnt
from survey sv
inner join student s                       on s.id=sv.studentid and s.activeflag is true and sv.activeflag is true
inner JOIN enrollment e                    ON e.studentid = s.id and e.activeflag is true
inner join organization o                  on s.stateid=o.id and o.activeflag is true
inner join organizationtreedetail ot       on ot.schoolid=e.attendanceschoolid and ot.stateid=o.id 
inner join firstcontactsurveysettings fcss on fcss.organizationid=o.id and fcss.activeflag is true and e.currentschoolyear=fcss.schoolyear
inner join category cafcss                 on fcss.categoryid=cafcss.id 
cross join surveylabel  sl 
where e.currentschoolyear=2017  and sl.activeflag is true 
and optional= case when cafcss.categorycode='CORE_SET_QUESTIONS' then false else optional end
and sl.globalpagenum=7 and sl.id=5 --(Q39)
group by sv.id,sl.globalpagenum
)
,all_answ as
(
select sv.id surveyid,sp.globalpagenum,count(distinct sl.labelnumber) cnt
from survey sv
inner join student s                       on s.id=sv.studentid and s.activeflag is true and sv.activeflag is true
inner JOIN enrollment e                    ON e.studentid = s.id and e.activeflag is true
inner join organization o                  on s.stateid=o.id and o.activeflag is true
inner join organizationtreedetail ot       on ot.schoolid=e.attendanceschoolid and ot.stateid=o.id 
inner join firstcontactsurveysettings fcss on fcss.organizationid=o.id and fcss.activeflag is true and e.currentschoolyear=fcss.schoolyear
inner join category cafcss                 on fcss.categoryid=cafcss.id 
inner JOIN studentsurveyresponse ssr       ON sv.id = ssr.surveyid and ssr.activeflag is true
inner JOIN surveyresponse sr               ON ssr.surveyresponseid = sr.id and sr.activeflag is true
inner JOIN surveylabel sl                  ON sr.labelid = sl.id and sl.activeflag is true
inner join surveypagestatus sp             on sp.surveyid=sv.id and sl.globalpagenum=sp.globalpagenum and sp.activeflag is true
where e.currentschoolyear=2017  and sl.activeflag is true 
and optional= case when cafcss.categorycode='CORE_SET_QUESTIONS' then false else sl.optional end
and sl.globalpagenum=7 and sl.id=5 --(Q39)
group by sv.id,sp.globalpagenum
)
select sp.id 
from all_ques tgt
inner join surveypagestatus sp on sp.surveyid=tgt.surveyid and tgt.globalpagenum=sp.globalpagenum and sp.activeflag is true
left outer join all_answ src on tgt.surveyid=src.surveyid and tgt.globalpagenum=src.globalpagenum
where coalesce(src.cnt,0)<>coalesce(tgt.cnt,0) and sp.iscompleted is true); 


--1.b:validation: Find the all mandatory questions were answered and iscompleted set false (note:only for No)
update surveypagestatus
set iscompleted =true,
    modifieddate=now(),
    modifieduser=12
where id in (
with all_ques as
(
select sv.id surveyid,sl.globalpagenum,count(distinct sl.labelnumber) cnt
from survey sv
inner join student s                       on s.id=sv.studentid and s.activeflag is true and sv.activeflag is true
inner JOIN enrollment e                    ON e.studentid = s.id and e.activeflag is true
inner join organization o                  on s.stateid=o.id and o.activeflag is true
inner join organizationtreedetail ot       on ot.schoolid=e.attendanceschoolid and ot.stateid=o.id 
inner join firstcontactsurveysettings fcss on fcss.organizationid=o.id and fcss.activeflag is true and e.currentschoolyear=fcss.schoolyear
inner join category cafcss                 on fcss.categoryid=cafcss.id 
cross join surveylabel  sl 
where e.currentschoolyear=2017  and sl.activeflag is true 
and optional= case when cafcss.categorycode='CORE_SET_QUESTIONS' then false else optional end
and sl.globalpagenum=7 and sl.id=5 --(Q39)
group by sv.id,sl.globalpagenum
)
,all_answ as
(
select sv.id surveyid,sp.globalpagenum,count(distinct sl.labelnumber) cnt
from survey sv
inner join student s                       on s.id=sv.studentid and s.activeflag is true and sv.activeflag is true
inner JOIN enrollment e                    ON e.studentid = s.id and e.activeflag is true
inner join organization o                  on s.stateid=o.id and o.activeflag is true
inner join organizationtreedetail ot       on ot.schoolid=e.attendanceschoolid and ot.stateid=o.id 
inner join firstcontactsurveysettings fcss on fcss.organizationid=o.id and fcss.activeflag is true and e.currentschoolyear=fcss.schoolyear
inner join category cafcss                 on fcss.categoryid=cafcss.id 
inner JOIN studentsurveyresponse ssr       ON sv.id = ssr.surveyid and ssr.activeflag is true
inner JOIN surveyresponse sr               ON ssr.surveyresponseid = sr.id and sr.activeflag is true
inner JOIN surveylabel sl                  ON sr.labelid = sl.id and sl.activeflag is true
inner join surveypagestatus sp             on sp.surveyid=sv.id and sl.globalpagenum=sp.globalpagenum and sp.activeflag is true
where e.currentschoolyear=2017  and sl.activeflag is true 
and optional= case when cafcss.categorycode='CORE_SET_QUESTIONS' then false else sl.optional end
and sl.globalpagenum=7 and sl.id=5 --(Q39)
and sr.id in (22,239)              --(No,Null)
group by sv.id,sp.globalpagenum
)
select sp.id 
from all_ques tgt
inner join surveypagestatus sp on sp.surveyid=tgt.surveyid and tgt.globalpagenum=sp.globalpagenum and sp.activeflag is true
left outer join all_answ src on tgt.surveyid=src.surveyid and tgt.globalpagenum=src.globalpagenum
where coalesce(src.cnt,0)=coalesce(tgt.cnt,0) and sp.iscompleted is false); 

--1.c:validation: Find the all mandatory questions were not answered and iscompleted set true (note:Q39 response Yes)
update surveypagestatus
set iscompleted =false,
    modifieddate=now(),
    modifieduser=12
where id in (
with all_ques as
(
select sv.id surveyid,sp.globalpagenum,count(distinct sl.labelnumber) cnt
from survey sv
inner join student s                       on s.id=sv.studentid and s.activeflag is true and sv.activeflag is true
inner JOIN enrollment e                    ON e.studentid = s.id and e.activeflag is true
inner join organization o                  on s.stateid=o.id and o.activeflag is true
inner join organizationtreedetail ot       on ot.schoolid=e.attendanceschoolid and ot.stateid=o.id 
inner join firstcontactsurveysettings fcss on fcss.organizationid=o.id and fcss.activeflag is true and e.currentschoolyear=fcss.schoolyear
inner join category cafcss                 on fcss.categoryid=cafcss.id 
inner JOIN studentsurveyresponse ssr       ON sv.id = ssr.surveyid and ssr.activeflag is true
inner JOIN surveyresponse sr               ON ssr.surveyresponseid = sr.id and sr.activeflag is true
inner JOIN surveylabel sl                  ON sr.labelid = sl.id and sl.activeflag is true
inner join surveypagestatus sp             on sp.surveyid=sv.id and sl.globalpagenum=sp.globalpagenum and sp.activeflag is true
where e.currentschoolyear=2017  and sl.activeflag is true 
and optional= case when cafcss.categorycode='CORE_SET_QUESTIONS' then false else sl.optional end
and sl.globalpagenum=7 and sl.id=5 --(Q39)
and sr.id =21                      --(Yes)
group by sv.id,sp.globalpagenum
)
,all_answ as
(
select cafcss.categorycode,sv.id surveyid,sp.globalpagenum,count(distinct sl.labelnumber) cnt
from survey sv
inner join student s                       on s.id=sv.studentid and s.activeflag is true and sv.activeflag is true
inner JOIN enrollment e                    ON e.studentid = s.id and e.activeflag is true
inner join organization o                  on s.stateid=o.id and o.activeflag is true
inner join organizationtreedetail ot       on ot.schoolid=e.attendanceschoolid and ot.stateid=o.id 
inner join firstcontactsurveysettings fcss on fcss.organizationid=o.id and fcss.activeflag is true and e.currentschoolyear=fcss.schoolyear
inner join category cafcss                 on fcss.categoryid=cafcss.id 
inner JOIN studentsurveyresponse ssr       ON sv.id = ssr.surveyid and ssr.activeflag is true
inner JOIN surveyresponse sr               ON ssr.surveyresponseid = sr.id and sr.activeflag is true
inner JOIN surveylabel sl                  ON sr.labelid = sl.id and sl.activeflag is true
inner join surveypagestatus sp             on sp.surveyid=sv.id and sl.globalpagenum=sp.globalpagenum and sp.activeflag is true
where e.currentschoolyear=2017  and sl.activeflag is true 
and optional= case when cafcss.categorycode='CORE_SET_QUESTIONS' then false else sl.optional end
and sl.globalpagenum=7 and sl.id in (6,7) --(Q40,Q41)
group by cafcss.categorycode,sv.id,sp.globalpagenum
)
select sp.id 
from all_ques tgt
inner join surveypagestatus sp on sp.surveyid=tgt.surveyid and tgt.globalpagenum=sp.globalpagenum and sp.activeflag is true
left outer join all_answ src on tgt.surveyid=src.surveyid and tgt.globalpagenum=src.globalpagenum
where  coalesce(src.cnt,0) < case when categorycode='CORE_SET_QUESTIONS' then 1 else 2 end
and coalesce(tgt.cnt,0)=1  and sp.iscompleted is true); 

--1.d:validation: Find the all mandatory questions were answered and iscompleted set false  (note:Q39 response Yes) 
update surveypagestatus
set iscompleted =true,
    modifieddate=now(),
    modifieduser=12
where id in (
with all_ques as
(
select sv.id surveyid,sp.globalpagenum,count(distinct sl.labelnumber) cnt
from survey sv
inner join student s                       on s.id=sv.studentid and s.activeflag is true and sv.activeflag is true
inner JOIN enrollment e                    ON e.studentid = s.id and e.activeflag is true
inner join organization o                  on s.stateid=o.id and o.activeflag is true
inner join organizationtreedetail ot       on ot.schoolid=e.attendanceschoolid and ot.stateid=o.id 
inner join firstcontactsurveysettings fcss on fcss.organizationid=o.id and fcss.activeflag is true and e.currentschoolyear=fcss.schoolyear
inner join category cafcss                 on fcss.categoryid=cafcss.id 
inner JOIN studentsurveyresponse ssr       ON sv.id = ssr.surveyid and ssr.activeflag is true
inner JOIN surveyresponse sr               ON ssr.surveyresponseid = sr.id and sr.activeflag is true
inner JOIN surveylabel sl                  ON sr.labelid = sl.id and sl.activeflag is true
inner join surveypagestatus sp             on sp.surveyid=sv.id and sl.globalpagenum=sp.globalpagenum and sp.activeflag is true
where e.currentschoolyear=2017  and sl.activeflag is true 
and optional= case when cafcss.categorycode='CORE_SET_QUESTIONS' then false else sl.optional end
and sl.globalpagenum=7 and sl.id=5 --(Q39)
and sr.id =21                      --(Yes)
group by sv.id,sp.globalpagenum
)
,all_answ as
(
select cafcss.categorycode,sv.id surveyid,sp.globalpagenum,count(distinct sl.labelnumber) cnt
from survey sv
inner join student s                       on s.id=sv.studentid and s.activeflag is true and sv.activeflag is true
inner JOIN enrollment e                    ON e.studentid = s.id and e.activeflag is true
inner join organization o                  on s.stateid=o.id and o.activeflag is true
inner join organizationtreedetail ot       on ot.schoolid=e.attendanceschoolid and ot.stateid=o.id 
inner join firstcontactsurveysettings fcss on fcss.organizationid=o.id and fcss.activeflag is true and e.currentschoolyear=fcss.schoolyear
inner join category cafcss                 on fcss.categoryid=cafcss.id 
inner JOIN studentsurveyresponse ssr       ON sv.id = ssr.surveyid and ssr.activeflag is true
inner JOIN surveyresponse sr               ON ssr.surveyresponseid = sr.id and sr.activeflag is true
inner JOIN surveylabel sl                  ON sr.labelid = sl.id and sl.activeflag is true
inner join surveypagestatus sp             on sp.surveyid=sv.id and sl.globalpagenum=sp.globalpagenum and sp.activeflag is true
where e.currentschoolyear=2017  and sl.activeflag is true 
and optional= case when cafcss.categorycode='CORE_SET_QUESTIONS' then false else sl.optional end
and sl.globalpagenum=7 and sl.id in (6,7) --(Q40,Q41)
group by cafcss.categorycode,sv.id,sp.globalpagenum
)
select sp.id 
from all_ques tgt
inner join surveypagestatus sp on sp.surveyid=tgt.surveyid and tgt.globalpagenum=sp.globalpagenum and sp.activeflag is true
left outer join all_answ src on tgt.surveyid=src.surveyid and tgt.globalpagenum=src.globalpagenum
where  coalesce(src.cnt,0) >=  case when categorycode='CORE_SET_QUESTIONS' then 1 else 2 end
and coalesce(tgt.cnt,0)=1  and sp.iscompleted is false); 

