-- 678.sql dml version

update studentassessmentprogram set kelpa2017flag=activeflag where id in(select id from studentassessmentprogram where assessmentprogramid=47);

update studentassessmentprogram set activeflag=false,modifieduser=12, modifieddate=now() 
where id in(select id from studentassessmentprogram where assessmentprogramid=47 and activeflag is true);


--retiring CAPCAD,HS roles
update groups set isdepreciated = true, modifieddate =now(), modifieduser = (select Id from aartuser where username ='cetesysadmin') 
where groupcode in('CAPAD','HS');

--DE16078
update fieldspecification set allowablevalues = '{21,22,23,24,9}', fieldlength=2 where fieldname = 'nyPerformanceLevel';

--Science report access setup
Select * from reportassessmentprogram_fn('KAP','GEN_ST','Sci','VIEW_GENERAL_STUDENT_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_SS','Sci','VIEW_GENERAL_STUDENT_REPORT');
Select * from reportassessmentprogram_fn('KAP','GEN_DS','Sci','VIEW_GENERAL_STUDENT_REPORT');

update reportassessmentprogram set readytoview=true 
	where subjectid=441 and stateid=51 and assessmentprogramid=12 and readytoview=false;
	
