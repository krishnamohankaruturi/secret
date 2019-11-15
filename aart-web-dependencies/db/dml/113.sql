

--DE4675: CSV uploaded PNP Data is not reflected in the UI 

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
	VALUES ('directionsOnly', null, NULL, NULL, 30, false, false, NULL, 'directionsOnly', false, now(),
		(Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('directionsOnly' ,'directionsOnly'));


INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
	VALUES ('scanSpeed', null, NULL, NULL, 30, false, false, NULL, 'scanSpeed', false, now(),
		(Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('scanSpeed' ,'scanSpeed'));


INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
	VALUES ('automaticScanInitialDelay', null, NULL, NULL, 30, false, false, NULL, 'automaticScanInitialDelay', false, now(),
		(Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('automaticScanInitialDelay' ,'automaticScanInitialDelay'));


INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, 
	rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
	VALUES ('automaticScanRepeat', null, NULL, NULL, 30, false, false, NULL, 'automaticScanRepeat', false, now(),
		(Select id from aartuser where username = 'cetesysadmin'), true, now(), (Select id from aartuser where username = 'cetesysadmin'), false);

INSERT INTO fieldspecificationsrecordtypes
    SELECT id,(Select id from category where categorycode='PERSONAL_NEEDS_PROFILE_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin')
    FROM fieldspecification
    WHERE (fieldname,mappedname) in (('automaticScanRepeat' ,'automaticScanRepeat'));
