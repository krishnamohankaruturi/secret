--dml/559.sql empty 

--US18774: Reports: Upload subscore descriptions - add indentation option
--Create the fieldspec for the new column
insert into fieldspecification 
(fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, 
rejectifinvalid, formatregex, showerror, createddate, createduser,
activeflag, modifieduser, iskeyvaluepairfield, fieldtype)	
values 
('indentNeeded', '{'''',yes,yeS,yES,YES,YEs,Yes,no,nO,NO,No}', null, null, 3, false,
 true, null, true, now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, (Select id from aartuser where username = 'cetesysadmin'), false, 'string'
);

insert into fieldspecificationsrecordtypes 
(fieldspecificationid, recordtypeid, 
 createddate, createduser, 
 activeflag, modifieddate, modifieduser, mappedname)
values
((select id from fieldspecification where fieldname = 'indentNeeded' and mappedname is null), 
(select id from category where categorycode = 'SUBSCORE_DESCRIPTION_AND_REPORT_USAGE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE')), 
 now(), (Select id from aartuser where username = 'cetesysadmin'),
 true, now(), (Select id from aartuser where username = 'cetesysadmin'), 'Indent_needed'
);