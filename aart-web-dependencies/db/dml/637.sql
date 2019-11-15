-- DE15269 ---
update fieldspecification  set fieldlength = 200 where fieldname = 'assignmentName';

--DML for DE15353: DLM blueprint table corrections
update blueprint set numberrequired = 3 where gradecourseid = (select id from gradecourse where abbreviatedname = '8' and contentareaid = (select id from contentarea where abbreviatedname = 'ELA'))
       and contentareaid = (select id from contentarea where abbreviatedname = 'ELA')
       and criteria = 2 and groupnumber = 40;

update blueprint set numberrequired = 3 where gradecourseid = (select id from gradecourse where abbreviatedname = '7' and contentareaid = (select id from contentarea where abbreviatedname = 'ELA'))
       and contentareaid = (select id from contentarea where abbreviatedname = 'ELA')
       and criteria = 2 and groupnumber = 33;


insert into blueprintessentialelements(blueprintid, essentialelementid, essentialelement, ordernumber)
  values((SELECT id FROM blueprint where gradecourseid = (select id from gradecourse where abbreviatedname = '5' and contentareaid = (select id from contentarea where abbreviatedname = 'ELA'))
       and contentareaid = (select id from contentarea where abbreviatedname = 'ELA') and criteria = 2 and groupnumber = 19), 
       (select distinct essentialelementid from blueprintessentialelements where essentialelement = 'ELA.EE.L.5.4.a'), 'ELA.EE.L.5.4.a', 4);

insert into blueprintessentialelements(blueprintid, essentialelementid, essentialelement, ordernumber)
  values((SELECT id FROM blueprint where gradecourseid = (select id from gradecourse where abbreviatedname = '5' and contentareaid = (select id from contentarea where abbreviatedname = 'ELA'))
       and contentareaid = (select id from contentarea where abbreviatedname = 'ELA') and criteria = 2 and groupnumber = 19), 
       (select distinct essentialelementid from blueprintessentialelements where essentialelement = 'ELA.EE.L.5.5.c'), 'ELA.EE.L.5.5.c', 5);
       
DELETE FROM blueprintessentialelements where essentialelement = 'ELA.EE.RI.5.2' 
and blueprintid = (SELECT id FROM blueprint WHERE gradecourseid = (select id from gradecourse where abbreviatedname = '5' and contentareaid = (select id from contentarea where abbreviatedname = 'ELA'))
       and contentareaid = (select id from contentarea where abbreviatedname = 'ELA') and criteria = 2 and groupnumber = 18);
       
       
-- DE15372: DLM - Science final band's are not calculating even after submitting FCS
update student set finalscibandid = (case when scibandid < commbandid then scibandid else commbandid end), modifieddate = now()
where id in 
(select id from student where stateid in (select organizationid from 
	firstcontactsurveysettings where activeflag  is true and scienceflag is true and schoolyear = 2017)                                           
and id in (select studentid from enrollment where currentschoolyear = 2017 and activeflag is true) 
and finalscibandid is null and scibandid is not null and commbandid is not null
and id in (select studentid from survey where status = 222 and activeflag is true) 
and id in (select studentid from studentassessmentprogram where assessmentprogramid = 3 and activeflag is true)
);
