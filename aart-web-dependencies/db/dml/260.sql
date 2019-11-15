
--US14846
update fieldspecification set allowablevalues='{'''',0,1,2,3,4,5,6,7,8,10,11,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47}' where fieldname='firstLanguage';
update fieldspecification set fieldlength=1, allowablevalues='{'''',0,1,2,3,4,5,6}' where fieldname='esolParticipationCode';
update fieldspecification set mappedname = 'Primary_Disability_Code' where fieldname='primaryDisabilityCode';
update fieldspecification set fieldname='primaryDisabilityCode', allowablevalues='{'''',AM,DB,DD,ED,HI,LD,MD,MR,ID,OH,OI,SL,TB,VI}' where fieldname='primaryExceptionalityCode';

insert into fieldspecification (fieldname, allowablevalues, fieldlength, rejectifempty, rejectifinvalid, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
values ('secondaryDisabilityCode', '{'''',AM,DB,DD,ED,HI,LD,MD,MR,ID,OH,OI,SL,TB,VI}', 2, false, false, 'Secondary_Exceptionality_Code', false, now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'), false);

insert into fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid, createddate, createduser, activeflag, modifieddate, modifieduser)
values ((select id from fieldspecification where fieldname='secondaryDisabilityCode'),(select id from category where categorycode='KID_RECORD_TYPE'), now(), (select id from aartuser where username='cetesysadmin'), true, now(), (select id from aartuser where username='cetesysadmin'));
