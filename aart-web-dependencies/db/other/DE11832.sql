 /*select enttsa.id,enttsa.enrollmentid,en.studentid,en.attendanceschoolid,en.activeflag,enttsa.activeflag,
 testtypecode,subjectareacode from enrollmenttesttypesubjectarea enttsa 
 join enrollment en on en.id=enttsa.enrollmentid
 join testtype tt on tt.id=enttsa.testtypeid 
 join subjectarea sa on sa.id=enttsa.subjectareaid
 where enttsa.enrollmentid in(select id from enrollment where 
 activeflag is true and currentschoolyear=2016 and studentid in(801847,391293,535718,198740));
 
select st.id,st.status,st.enrollmentid,st.createddate,st.testsessionid,ts.rosterid, ts.source,ts.source 
 from studentstests st join testsession ts on ts.id=st.testsessionid 
 where st.studentid in (801847,391293,535718,198740);*/
 
update enrollmenttesttypesubjectarea
set activeflag = false,
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
modifieddate = now()
where id in (1829136, 1837059, 1716110, 1716111, 1716116, 1716117, 1716118);

-- remove DLM
--remove FCS responses
update studentsurveyresponse 
set activeflag = false,
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
modifieddate = now()
where activeflag is true and surveyid in(
select id from survey where studentid in (
  select id
  from student
  where stateid = (select id from organization where displayidentifier = 'KS')
  and statestudentidentifier in (
    '8488149174', '6622908389', '7920228433', '2981988352', '2637162643', '3352132364', '2285746091', '6639339544', '6789962352', '1645919595', '9425043841', '9582068256'
  )
));

--remove FCS 
update survey
set activeflag = false,
modifieduser = (select id from aartuser where username = 'cetesysadmin'),
modifieddate = now()
where activeflag is true and id in(
select id from survey where studentid in (
  select id
  from student
  where stateid = (select id from organization where displayidentifier = 'KS')
  and statestudentidentifier in (
    '8488149174', '6622908389', '7920228433', '2981988352', '2637162643', '3352132364', '2285746091', '6639339544', '6789962352', '1645919595', '9425043841', '9582068256'
  )
));

update studentassessmentprogram
set activeflag = false
where activeflag = true
and studentid in (
  select id
  from student
  where stateid = (select id from organization where displayidentifier = 'KS')
  and statestudentidentifier in (
    '8488149174', '6622908389', '7920228433', '2981988352', '2637162643', '3352132364', '2285746091', '6639339544', '6789962352', '1645919595', '9425043841', '9582068256'
  )
)
and assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'DLM');

-- remove KAP
update studentassessmentprogram
set activeflag = false
where activeflag = true
and studentid in (
  select id
  from student
  where stateid = (select id from organization where displayidentifier = 'KS')
  and statestudentidentifier in (
    '8084671413', '9569564938'
  )
)
and assessmentprogramid = (select id from assessmentprogram where abbreviatedname = 'KAP');