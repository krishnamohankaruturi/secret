Select * from student where username='1000000024';

Select * from studentstests where studentid=391;

update studentstests set testid = (select testid from testcollectionstests tct where tct.testcollectionid=studentstests.testcollectionid) where id = 200;

Select * from category where id = 108;

Select * from testcollection where id =59;

Select * from testcollectionstests where testcollectionid=59;

124 and 125;

Select * from test where id in (133);

"standalone test" and 
"test fom 27/9"

Select * from category where id = 76;

---------

Select * from studentstest;

Select * from test order by id;

Select * from testcollectionstests where testid=134

Select * from testcollection where id = 60;

Select * from assessmentstestcollections where testcollectionid=60