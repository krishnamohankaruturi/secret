--ddl/706.sql

CREATE OR REPLACE FUNCTION public.cleaninvaliduserstatus()
RETURNS void AS
$BODY$
declare
      userId record;
      statusId bigint;
      finalStatusId bigint;
      
      begin
    RAISE NOTICE 'begining';
        for userId in select distinct uo.aartuserid as aartuserId
            from userorganizationsgroups uog 
            inner join usersorganizations uo on uog.userorganizationid=uo.id and uog.activeflag is true and uo.activeflag is true
            inner join userassessmentprogram uap on uap.userorganizationsgroupsid =uog.id and uap.activeflag is true
            group by uo.aartuserid
            having count(distinct uog.status) >1
        loop
        finalStatusId=3;
        for statusId in select distinct uog.status from 
            usersorganizations uo 
            inner JOIN userorganizationsgroups uog ON uog.userorganizationid = uo.id and uo.activeflag is true and uog. activeflag is true
            inner join userassessmentprogram uap on uap.userorganizationsgroupsid =uog.id and uap.activeflag is true
            where uo.aartuserid = userId.aartuserId order by uog.status desc
            loop
            If statusId = 2
            then
               finalStatusId=2;
               EXIT;
             elsif statusId = 1
             then 
                 finalStatusId=1;
                 EXIT;
                 end if;
            end loop;
                  update userorganizationsgroups set status =(finalStatusId),
            modifieddate=now(),
            modifieduser=(select id from aartuser where username='cetesysadmin')
            where userorganizationid in(select distinct id from usersorganizations where aartuserid=userId.aartuserId);   
        end loop;
      END;
    $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  select * from public.cleaninvaliduserstatus();
  
  