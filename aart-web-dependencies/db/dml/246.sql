
-- 245.sql

insert into fieldspecification 
(fieldname, minimum, maximum, fieldlength,  rejectifempty, rejectifinvalid, mappedname, showerror,
createddate, activeflag, modifieddate, iskeyvaluepairfield, createduser, modifieduser)
values
('achievementLevelName', 0, 0, 100, false, false, 'AchievementLevelLabel', true, current_timestamp, true, current_timestamp, false, (select id from aartuser where username='cetesysadmin'),(select id from aartuser where username='cetesysadmin'));


insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser)
values 
((select id from  fieldspecification where fieldname = 'achievementLevelName'), (select id from category where categorycode = 'TEST_CUT_SCORES_RECORD_TYPE')
, current_timestamp, (select id from aartuser where username='cetesysadmin'), true, current_timestamp, (select id from aartuser where username='cetesysadmin'));
