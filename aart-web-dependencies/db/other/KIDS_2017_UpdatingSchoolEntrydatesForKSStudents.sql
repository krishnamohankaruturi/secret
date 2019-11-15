-- For these 6 kids have school entry date as null. So going to update school entry date which we got from kids_records_staging table

update enrollment set schoolentrydate='1/4/2017 12:00:00 AM', districtentrydate='1/4/2017 12:00:00 AM' 
    where studentid in (select id from student where statestudentidentifier='2541445571' and stateid = 51) and currentschoolyear=2017
    and aypschoolidentifier = '0776';



update enrollment set schoolentrydate='1/5/2017 12:00:00 AM', districtentrydate='1/5/2017 12:00:00 AM' 
    where studentid in (select id from student where statestudentidentifier='4804968008' and stateid = 51) and currentschoolyear=2017
    and aypschoolidentifier = '7787';


update enrollment set schoolentrydate='1/4/2017 12:00:00 AM', districtentrydate='1/4/2017 12:00:00 AM' 
    where studentid in (select id from student where statestudentidentifier='1913198502' and stateid = 51) and currentschoolyear=2017
    and aypschoolidentifier = '7776';


update enrollment set schoolentrydate='12/1/2016 12:00:00 AM', districtentrydate='12/1/2016 12:00:00 AM' 
    where studentid in (select id from student where statestudentidentifier='5290272909' and stateid = 51) and currentschoolyear=2017
    and aypschoolidentifier = '0769';


update enrollment set schoolentrydate='12/1/2016 12:00:00 AM', districtentrydate='12/1/2016 12:00:00 AM' 
    where studentid in (select id from student where statestudentidentifier='8944810516' and stateid = 51) and currentschoolyear=2017
    and aypschoolidentifier = '0769'; 


update enrollment set schoolentrydate='1/5/2017 12:00:00 AM', districtentrydate='1/5/2017 12:00:00 AM' 
    where studentid in (select id from student where statestudentidentifier='4942153395' and stateid = 51) and currentschoolyear=2017
    and aypschoolidentifier = '7787';

update enrollment set schoolentrydate='1/10/2017 12:00:00 AM', districtentrydate='1/10/2017 12:00:00 AM' 
    where studentid in (select id from student where statestudentidentifier='5671488028' and stateid = 51) and currentschoolyear=2017
    and aypschoolidentifier = '4974';


update enrollment set schoolentrydate='1/4/2017 12:00:00 AM', districtentrydate='1/4/2017 12:00:00 AM' 
    where studentid in (select id from student where statestudentidentifier='3781352617' and stateid = 51) and currentschoolyear=2017
    and aypschoolidentifier = '0776';    