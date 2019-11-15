-- 277.sql

insert into fieldspecification (fieldname, allowablevalues, fieldlength, rejectifempty, rejectifinvalid, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) values ('hispanicEthnicity','{yes,YES,Yes,No,NO,no}',3, false, true, true, current_timestamp, (Select id from aartuser where username='cetesysadmin'), true, current_timestamp, (Select id from aartuser where username='cetesysadmin'), false );

update fieldspecificationsrecordtypes set fieldspecificationid = (select id from fieldspecification where fieldname = 'hispanicEthnicity' and mappedname is null) where recordtypeid = (select id from category where categoryname = 'Enrollment') and fieldspecificationid = (select id from fieldspecification where fieldname = 'hispanicEthnicity' and mappedname is not null );

update fieldspecification set allowablevalues = '{yes,YES,Yes,No,NO,no}', formatregex = null where fieldname = 'giftedStudent' and mappedname is null;

update fieldspecification set minimum = 1, maximum = 8, fieldlength = null, formatregex = null where fieldname = 'comprehensiveRace' and mappedname is null;

