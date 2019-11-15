--611.sql

insert into category (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, activeflag, modifieddate)
values ('In Progress Timed Out', 'inprogresstimedout', 'The student is kicked out because grace period exceeded', 
(select id from categorytype where typecode='STUDENT_TEST_STATUS'), 'TDE', now(), true, now());

