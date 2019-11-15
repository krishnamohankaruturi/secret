CREATE OR REPLACE FUNCTION public.populatefinalsciband() 
RETURNS void AS 
$BODY$ 
declare 
    student_rec record; 
begin 
    for student_rec in select distinct s.id as studentid 
    from student s 
    inner join survey surv on surv.studentid=s.id and s.activeflag is true and surv.activeflag is true 
    inner join surveypagestatus sps on sps.surveyid=surv.id and sps.activeflag is true 
    where s.finalscibandid is null and s.scibandid is not null and surv.status=(select id from category where categorycode='COMPLETE') 
    loop 

        IF student_rec is not null  
        THEN 
            RAISE NOTICE '% studentids',student_rec.studentid; 

            update student set finalscibandid = ( 
            select distinct 
            CASE WHEN (s.scibandid is not null and s.commbandid is not null and (s.scibandid < s.commbandid)) 
            THEN s.scibandid 
            ELSE s.commbandid 
            END AS finalsciband 
             from student s 
            inner join survey surv on surv.studentid=s.id and s.activeflag is true and surv.activeflag is true 
            inner join surveypagestatus sps on sps.surveyid=surv.id and sps.activeflag is true 
            where s.finalscibandid is null and s.scibandid is not null  
            and surv.status=(select id from category where categorycode='COMPLETE') 
            and s.id=student_rec.studentid),
            modifieddate = now(),
            modifieduser = (select id from aartuser where username='cetesysadmin')
            where id=student_rec.studentid; 
        END IF; 

    end loop; 

END; 
    $BODY$ 
  LANGUAGE plpgsql VOLATILE 
  COST 100; 
