
--R8 -Iter3
--US11878 Name: Implement annual KSDE webservice changes

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES ('accountabilityschoolidentifier', NULL, NULL, NULL, 30, false, false, NULL, 'ACCOUNTABILITY_Bldg_No', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('accountabilityschoolidentifier',  'ACCOUNTABILITY_Bldg_No'));

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='ROSTER_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('accountabilityschoolidentifier',  'ACCOUNTABILITY_Bldg_No'));