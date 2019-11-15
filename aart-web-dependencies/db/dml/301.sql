
--DE8029 ESOL fields
delete from fieldspecificationsrecordtypes 
where fieldspecificationid=(select id from fieldspecification where fieldname='usaEntryDate' and mappedname='USA_Entry_Date')
and recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE');
update fieldspecification set mappedname = null where id = (select id from fieldspecification where fieldname='usaEntryDate' and mappedname='USA_Entry_Date');

delete from fieldspecificationsrecordtypes 
where fieldspecificationid=(select id from fieldspecification where fieldname='esolProgramEntryDate' and mappedname='ESOL_Program_Entry_Date')
and recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE');
update fieldspecification set mappedname = null where id = (select id from fieldspecification where fieldname='esolProgramEntryDate' and mappedname='ESOL_Program_Entry_Date');

delete from fieldspecificationsrecordtypes 
where fieldspecificationid=(select id from fieldspecification where fieldname='esolProgramEndingDate' and mappedname='ESOL_Program_Ending_Date')
and recordtypeid=(select id from category where categorycode='KID_RECORD_TYPE');
update fieldspecification set mappedname = null where id = (select id from fieldspecification where fieldname='esolProgramEndingDate' and mappedname='ESOL_Program_Ending_Date');

INSERT INTO fieldspecification(
            fieldname,  fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield)
	VALUES ('usaEntryDate',  30, 
		false, true, null, 'USA_Entry_Date', true, 
		now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
		false),
		('esolProgramEntryDate',  30, 
		    false, true, null, 'ESOL_Program_Entry_Date', true, 
		    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
		    false),
		('esolProgramEndingDate',  30, 
		    false, true, null, 'ESOL_Program_Ending_Date', true, 
		    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'), 
		    false);


INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ((select id from fieldspecification where mappedname='USA_Entry_Date'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='ESOL_Program_Entry_Date'), (select id from category where categorycode='KID_RECORD_TYPE'), 
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin')),
	((select id from fieldspecification where mappedname='ESOL_Program_Ending_Date'), (select id from category where categorycode='KID_RECORD_TYPE'),
    now(), (Select id from aartuser where username='cetesysadmin'), true, now(), (Select id from aartuser where username='cetesysadmin'));