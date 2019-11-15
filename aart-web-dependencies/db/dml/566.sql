--566.sql
--code merge from change pond
--US18886
INSERT INTO fieldspecification(
            id, fieldname, allowablevalues, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ((select max(id)+1 from fieldspecification), 'assessmentProgram4', '',100, 
            false, false, '^[A-z0-9!@#$%^&*()-_''~`:,\[\]{}<>+=\./\ ]++$',true, 
            current_timestamp, 12, true, current_timestamp, 12, 
            false, '', '', '');

 insert into fieldspecificationsrecordtypes 
 select id, (select id as recordtypeid from category where categorycode='SCRS_RECORD_TYPE'),current_timestamp,
(select id from aartuser where username='cetesysadmin'),
 true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Assessment Program 1' 
from fieldspecification where fieldname = 'assessmentProgram1' and mappedname is null;

 insert into fieldspecificationsrecordtypes 
 select id, (select id as recordtypeid from category where categorycode='SCRS_RECORD_TYPE'),current_timestamp,
(select id from aartuser where username='cetesysadmin'),
 true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Assessment Program 2' 
from fieldspecification where fieldname = 'assessmentProgram2' and mappedname is null;

insert into fieldspecificationsrecordtypes 
 select id, (select id as recordtypeid from category where categorycode='SCRS_RECORD_TYPE'),current_timestamp,
(select id from aartuser where username='cetesysadmin'),
 true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Assessment Program 3' 
from fieldspecification where fieldname = 'assessmentProgram3' and mappedname is null;

insert into fieldspecificationsrecordtypes 
 select id, (select id as recordtypeid from category where categorycode='SCRS_RECORD_TYPE'),current_timestamp,
(select id from aartuser where username='cetesysadmin'),
 true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Assessment Program 4' 
from fieldspecification where fieldname = 'assessmentProgram4' and mappedname is null;

--US18891
insert into fieldspecificationsrecordtypes 
   select id, (select id as recordtypeid from category where categorycode='TEC_RECORD_TYPE'),current_timestamp,
(select id from aartuser where username='cetesysadmin'),
   true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Grade' 
   from fieldspecification where fieldname = 'grade' ;
