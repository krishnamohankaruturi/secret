
--274.sql

insert into fieldspecificationsrecordtypes values(
	(select id from fieldspecification where fieldname = 'esolParticipationCode')
	,(select id from category where categoryname = 'Enrollment')
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin')
	,true
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin'));
	
insert into fieldspecificationsrecordtypes values(
	(select id from fieldspecification where fieldname = 'esolProgramEntryDate')
	,(select id from category where categoryname = 'Enrollment')
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin')
	,true
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin'));
 
insert into fieldspecificationsrecordtypes values(
	(select id from fieldspecification where fieldname = 'esolProgramEndingDate')
	,(select id from category where categoryname = 'Enrollment')
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin')
	,true
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin'));
	
	
insert into fieldspecificationsrecordtypes values(
	(select id from fieldspecification where fieldname = 'usaEntryDate')
	,(select id from category where categoryname = 'Enrollment')
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin')
	,true
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin'));
	
	
insert into fieldspecificationsrecordtypes values(
	(select id from fieldspecification where fieldname = 'firstLanguage' and mappedname='First_Language')
	,(select id from category where categoryname = 'Enrollment')
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin')
	,true
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin'));
	
insert into fieldspecificationsrecordtypes values(
	(select id from fieldspecification where fieldname = 'hispanicEthnicity' )
	,(select id from category where categoryname = 'Enrollment')
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin')
	,true
	,current_timestamp
	,(Select id from aartuser where username='cetesysadmin'));
 
 
update  fieldspecification set rejectifinvalid = true where id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid = (select id from category where categoryname = 'Enrollment'));
update  fieldspecification set SHOWERROR = true where id in (select fieldspecificationid from fieldspecificationsrecordtypes where recordtypeid = (select id from category where categoryname = 'Enrollment'));
update fieldspecification set rejectifempty = true where fieldname='gender';
update fieldspecification set rejectifempty = true where fieldname='schoolEntryDate';
update fieldspecification set rejectifempty = true where fieldname='esolParticipationCode';
update fieldspecification set rejectifempty = true where fieldname='esolProgramEndingDate';
update fieldspecification set rejectifempty = true where fieldname='esolProgramEntryDate';
update fieldspecification set rejectifempty = true where fieldname='dateOfBirth';
update fieldspecification set formatregex = '(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9]' where fieldname = 'esolProgramEntryDate';
update fieldspecification set formatregex = '(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9]' where fieldname = 'esolProgramEndingDate';
update fieldspecification set formatregex = '(0?[1-9]|1[012])(/|-)(0?[1-9]|[12][0-9]|3[01])(/|-)(19|20)?[0-9][0-9]' where fieldname = 'usaEntryDate';
update fieldspecification set allowablevalues = '{K,1,2,3,4,5,6,7,8,9,10,11,12}' where fieldname='currentGradeLevel';


 