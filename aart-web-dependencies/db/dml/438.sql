--/438.sql 

UPDATE SURVEYLABEL SET OPTIONAL='FALSE' WHERE LABELNUMBER IN ('Q200','Q201','Q202','Q143','Q147');

update fieldspecification set fieldlength=15, rejectifempty=true,rejectifinvalid=true, formatregex='^\d++$', fieldname='id'
where id=(select id from fieldspecification where fieldname='uniqueCommonIdentifier' and mappedname is null);

insert into  studentassessmentprogram(studentid,assessmentprogramid)
select distinct s.id, ap.id from student s join assessmentprogram ap on s.assessmentprogramid = ap.id
where ap.abbreviatedname = 'DLM' and ap.activeflag is true and s.activeflag is true and s.stateid is not null
and (s.id,ap.id) not in (select studentid,assessmentprogramid from studentassessmentprogram);

 