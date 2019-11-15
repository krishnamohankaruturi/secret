-- US15862: Reports - Upload conversion from raw to scale score by test - overall score - enhancements
update category set categorycode = 'RAW_TO_SCALE_SCORE' where categorycode = 'RAW_TO_SCALE_SCORES_RECORD_TYPE'; 

delete from fieldspecificationsrecordtypes where recordtypeid = (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid=(select id from categorytype where typecode = 'CSV_RECORD_TYPE'));

delete from fieldspecification where id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid = 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid=(select id from categorytype where typecode = 'CSV_RECORD_TYPE')));

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('rawScore', null, 0.0001, null, null, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'decimal'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('scaleScore', null, 1, null, null, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'number'
);

insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('standardError', null, 0.0001, null, null, true,
 true, null, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'decimal'
);    
    
insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'schoolYear' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'School_Year'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'assessmentProgram' and mappedname is null), 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Assessment_Program'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'subject' and allowablevalues is null and mappedname is null), 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Subject'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'grade' and mappedname is null), 
(select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Grade'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'testId1' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'TestID_1'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'testId2' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'TestID_2'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'rawScore' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Raw_Score'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'scaleScore' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Scale_Score'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'standardError' and mappedname is null
), (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'CSV_RECORD_TYPE')), now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Standard_Error'
);