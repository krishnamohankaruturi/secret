--603.sql (For ddl/603.sql)
--US18605.sql for DML
insert into testenrollmentmethod(assessmentprogramid,methodcode,methodname,methodtype) values 
((select id from assessmentprogram where abbreviatedname='K-ELPA'),'MNL','Manual','SAM');

insert into testenrollmentmethod(assessmentprogramid,methodcode,methodname,methodtype) values 
((select id from assessmentprogram where abbreviatedname='K-ELPA'),'AABR','Auto Assignment By Roster','SAM');

update testenrollmentmethod set methodtype = 'TEM' where methodcode in ('ADP','MLTSTG','FXD','MLTASGN','STDNTTRKR','MLTSTG','MLTASGNFT','INTERIM');

            