Select * from category where categorytypeid=10 and categorycode not like 'GRADE%';

delete from category where categorytypeid=10 and categorycode not like 'GRADE%';

Select * from test where gradeid between 49 and 55;

begin;

update test set gradeid = 19 where gradeid=50;

delete from category where categorytypeid=10 and categorycode not like 'GRADE%';

commit;

Select * from 

Select categorytypeid,categoryname,count(1) from category group by categorytypeid,categoryname having count(1) > 1;




select * from categorytype;