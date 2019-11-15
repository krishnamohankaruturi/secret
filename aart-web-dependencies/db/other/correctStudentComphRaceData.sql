-- this script is taking long to run, 15-20 minutes is the expected time. Hence not included in the build
update student set comprehensiverace='1' where comprehensiverace='00001';
update student set comprehensiverace='2' where comprehensiverace='00100';
update student set comprehensiverace='4' where comprehensiverace='01000';
update student set comprehensiverace='8' where comprehensiverace='00010';
update student set comprehensiverace='5' where comprehensiverace='10000';

update student set comprehensiverace='7' where comprehensiverace in(
select distinct comprehensiverace from student where comprehensiverace is not null and comprehensiverace not in ('1','2','4','5','6','7','8', '00001', '00100', '01000', '00010', '10000') order by comprehensiverace);
