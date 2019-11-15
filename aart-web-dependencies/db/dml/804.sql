--dml/804.sql

--DE19707 
update specialcircumstance
set assessmentprogramid =null ,modifieddate=now(),modifieduser=(select id from aartuser where email='cete@ku.edu') 
where specialcircumstancetype in (
 'Student took this grade level assessment last year',
 'Other',
 'Student used math journal (non-IEP)',
 'Foreign exchange student',
 'Truancy - no paperwork filed',
 'Chronic absences',
 'Special treatment center',
 'Special detention center',
 'Non-special education student used calculator on non-calculator items',
 'Cheating',
 'Truancy - paperwork filed',
 'Long-term suspension - non-special education',
 'Medical Waiver',
 'Reading passage read to student (IEP)',
 'Parent refusal',
 'Incarcerated at adult facility') and assessmentprogramid is not null and activeflag is true;
