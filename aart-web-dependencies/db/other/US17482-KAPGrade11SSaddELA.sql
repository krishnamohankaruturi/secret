-- US17482 - Technical: Prepare script for adding ELA test types to grade 11 students to include them in Performance window

begin;
Do $$

DECLARE
ENRL_TTSA_ROW RECORD;

BEGIN

For ENRL_TTSA_ROW IN
select enttsa.* from enrollment en
join enrollmenttesttypesubjectarea enttsa on enttsa.enrollmentid=en.id
join subjectarea sa on sa.id = enttsa.subjectareaid
--join studentassessmentprogram sap on sap.studentid=en.studentid
join testtype tt on tt.id = enttsa.testtypeid
where en.attendanceschoolid in (select schoolid from organizationtreedetail where stateid=51) and en.currentschoolyear=2016 
and en.activeflag is true and en.currentgradelevel in (select id from gradecourse where abbreviatedname='11')
--and sap.assessmentprogramid=(select id from assessmentprogram where abbreviatedname='KAP' and activeflag is true)
and enttsa.activeflag is true and sa.id=(select id from subjectarea where activeflag is true and subjectareacode='SHISGOVA')
and tt.activeflag is true and tt.testtypecode='2'
loop

INSERT INTO enrollmenttesttypesubjectarea(enrollmentid, testtypeid, subjectareaid, createddate, modifieddate, 
            createduser, modifieduser, activeflag, groupingindicator1, groupingindicator2)

    SELECT enrollmentid, (select id from testtype where testtypecode='2' limit 1), 
    (select id from subjectarea where subjectareacode='SELAA'), now(), now(), 
    12, 12, true, groupingindicator1, groupingindicator2 
    from enrollmenttesttypesubjectarea where id=ENRL_TTSA_ROW.id
    and not exists (select * from enrollmenttesttypesubjectarea 
			where enrollmentid =ENRL_TTSA_ROW.enrollmentid 
			and testtypeid in (select id from testtype where testtypecode='2' and activeflag is true)
			and subjectareaid = (select id from subjectarea where subjectareacode='SELAA'));

end loop; --ENRL_TTSA_ROW
end $$;

commit;