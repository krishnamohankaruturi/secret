--dml/703.sql

insert into testenrollmentmethod  ( assessmentprogramid, methodcode, methodname, methodtype) 
values ( ( select id from assessmentprogram where programname='KAP'),'AABR','Auto Assignment By Roster','SAM');