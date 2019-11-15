
--US11228 - Process Student Exit
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
('exitWithdrawalDate', NULL, NULL, NULL, 10, false, false, NULL, 'Exit_Withdrawal_Date', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('exitWithdrawalDate',  'Exit_Withdrawal_Date'));


INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength,rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)VALUES 
('exitWithdrawalType', NULL, NULL, NULL, 2, false, false, NULL, 'Exit_Withdrawal_Type', false, now(), (Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecificationsrecordtypes SELECT id,(Select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), 
(select id from aartuser where username='cetesysadmin')FROM fieldspecification WHERE (fieldname,mappedname) in (('exitWithdrawalType',  'Exit_Withdrawal_Type'));
