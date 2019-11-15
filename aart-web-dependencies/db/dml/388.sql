
-- 388.sql

delete from fieldspecificationsrecordtypes where fieldspecificationid =
 (select id from fieldspecification where fieldname = 'achievementLevelLabel')
 and recordtypeid = (select id from category where categorycode = 'TEST_CUT_SCORES_RECORD_TYPE');

delete from fieldspecificationsrecordtypes where fieldspecificationid =
 (select id from fieldspecification where fieldname = 'achievementLevelDescription')
 and recordtypeid = (select id from category where categorycode = 'TEST_CUT_SCORES_RECORD_TYPE');

delete from fieldspecificationsrecordtypes where fieldspecificationid =
 (select id from fieldspecification where fieldname = 'achievementLevelName')
 and recordtypeid = (select id from category where categorycode = 'TEST_CUT_SCORES_RECORD_TYPE');

update category set categorycode = 'TEST_CUT_SCORES' where categorycode = 'TEST_CUT_SCORES_RECORD_TYPE';

update fieldspecification set fieldname = 'levelLowCutScore' where fieldname = 'lowerLevelCutScore';
update fieldspecification set fieldname = 'levelHighCutScore' where fieldname = 'upperLevelCutScore';


update fieldspecificationsrecordtypes set mappedname = 'Level_Low_Cut_Score' where 
recordtypeid = (select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid = (select id from categorytype where typecode = 'CSV_RECORD_TYPE' )
) and fieldspecificationid = (select id from fieldspecification where fieldname='levelLowCutScore');

update fieldspecificationsrecordtypes set mappedname = 'Level_High_Cut_Score' where 
recordtypeid = (select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid = (select id from categorytype where typecode = 'CSV_RECORD_TYPE' )
) and fieldspecificationid = (select id from fieldspecification where fieldname='levelHighCutScore');

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'assessmentProgram' and mappedname is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Assessment_Program'
);

update fieldspecification set fieldtype = 'String' where fieldname in ('subject','grade','assessmentProgram');

update fieldspecification set minimum = 0, fieldlength = null, fieldtype = 'number' where fieldname = 'level';



insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'level' and allowablevalues is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Level'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subject' and allowablevalues is null and mappedname is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Subject'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'grade' and mappedname is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Grade'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'schoolYear' and mappedname is null
), (select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'School_Year'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('testId1', null, null, null, null, true,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'number'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('testId2', null, null, null, null, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'number'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'testId1' and mappedname is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'TestId_1'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'testId2' and mappedname is null), 
(select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'TestId_2'
);


delete from fieldspecificationsrecordtypes where fieldspecificationid = (select id from fieldspecification where fieldname = 'testId'
) and recordtypeid = (select id from category where categorycode = 'TEST_CUT_SCORES' and categorytypeid = (select id from categorytype where typecode = 'CSV_RECORD_TYPE' )
);


update category set categorycode = 'TEST_CUT_SCORES' where categorycode = 'TEST_CUT_SCORES';

