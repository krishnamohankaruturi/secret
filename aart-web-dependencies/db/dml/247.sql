
-- 247.sql

update fieldspecification set fieldlength = 4, rejectifempty= true, rejectifinvalid = true, showerror = true where fieldname = 'achievementLevelLabel';
update fieldspecification set rejectifinvalid = true,rejectifempty= true, showerror = true where fieldname = 'achievementLevelName';
update fieldspecification set rejectifinvalid = true,rejectifempty= true, showerror = true where fieldname = 'achievementLevelDescription';
update fieldspecification set rejectifinvalid = true,rejectifempty= true, showerror = true where fieldname = 'testId';
update fieldspecification set rejectifempty= true, fieldlength = null, minimum = 0, maximum = 9999, rejectifinvalid = true, showerror = true where fieldname = 'lowerLevelCutScore';
update fieldspecification set rejectifempty= true, fieldlength = null, minimum = 0, maximum = 9999, rejectifinvalid = true, showerror = true where fieldname = 'upperLevelCutScore';

