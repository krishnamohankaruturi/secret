
--R8 - Release Prep.
--DE4674

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,
	modifieduser,iskeyvaluepairfield)values('questionQ17Choice','Q17','{'''',1,2,3,4,5,6}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ17Choice' , 'Q17')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ19Choice' , 'Q19')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,
	modifieduser,iskeyvaluepairfield)values('questionQ19Choice','Q19','{'''',1,2}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,
	modifieduser,iskeyvaluepairfield)values('questionQ132_1Choice','Q132_1','{'''',1}',1,null,false,true,true,
	(Select id from aartuser where username='cetesysadmin'),(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ132_2Choice','Q132_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ132_3Choice','Q132_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ132_4Choice','Q132_4','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);		

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ132_1Choice' , 'Q132_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ132_2Choice' , 'Q132_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ132_3Choice' , 'Q132_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ132_4Choice' , 'Q132_4')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ20_1Choice','Q20_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ20_2Choice','Q20_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ20_3Choice','Q20_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ20_4Choice','Q20_4','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ20_1Choice' , 'Q20_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ20_2Choice' , 'Q20_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ20_3Choice' , 'Q20_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ20_4Choice' , 'Q20_4')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ22Choice' , 'Q22')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ22Choice','Q22','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ133Choice','Q133','{'''',1,2}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ133Choice' , 'Q133')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ23_1Choice' , 'Q23_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ23_2Choice' , 'Q23_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ23_3Choice' , 'Q23_3')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ23_1Choice','Q23_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ23_2Choice','Q23_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ23_3Choice','Q23_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ151_1Choice','Q151_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ151_2Choice','Q151_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ151_3Choice','Q151_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ151_1Choice' , 'Q151_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ151_2Choice' , 'Q151_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ151_3Choice' , 'Q151_3')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ24Choice','Q24','{'''',1,2}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ24Choice' , 'Q24')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ25_1Choice','Q25_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ25_2Choice','Q25_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ25_3Choice','Q25_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ25_4Choice','Q25_4','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ25_5Choice','Q25_5','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ25_6Choice','Q25_6','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ25_7Choice','Q25_7','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ25_8Choice','Q25_8','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ25_9Choice','Q25_9','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ25_10Choice','Q25_10','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ25_1Choice' , 'Q25_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ25_2Choice' , 'Q25_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ25_3Choice' , 'Q25_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ25_4Choice' , 'Q25_4')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ25_5Choice' , 'Q25_5')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ25_6Choice' , 'Q25_6')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ25_7Choice' , 'Q25_7')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate
	,modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ25_8Choice' , 'Q25_8')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ25_9Choice' , 'Q25_9')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ25_10Choice' , 'Q25_10')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ135Choice' , 'Q135')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ135Choice','Q135','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ27_1Choice','Q27_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ27_2Choice','Q27_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ27_3Choice','Q27_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ27_4Choice','Q27_4','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ27_1Choice' , 'Q27_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ27_2Choice' , 'Q27_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ27_3Choice' , 'Q27_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ27_4Choice' , 'Q27_4')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ153Choice','Q153','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ153Choice' , 'Q153')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ29_1Choice','Q29_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ29_2Choice','Q29_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ29_3Choice','Q29_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ29_4Choice','Q29_4','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ29_1Choice' , 'Q29_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ29_2Choice' , 'Q29_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ29_3Choice' , 'Q29_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ29_4Choice' , 'Q29_4')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ31_1Choice','Q31_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ31_2Choice','Q31_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ31_3Choice','Q31_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ31_1Choice' , 'Q31_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ31_2Choice' , 'Q31_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ31_3Choice' , 'Q31_3')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ143Choice','Q143','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ143Choice' , 'Q143')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ147Choice','Q147','{'''',1,2,3,4}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ147Choice' , 'Q147')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_1Choice' , 'Q33_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_2Choice' , 'Q33_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_3Choice' , 'Q33_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_4Choice' , 'Q33_4')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_5Choice' , 'Q33_5')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_6Choice' , 'Q33_6')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_7Choice' , 'Q33_7')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_8Choice' , 'Q33_8')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_9Choice' , 'Q33_9')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_10Choice' , 'Q33_10')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ33_11Choice' , 'Q33_11')));

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_1Choice','Q33_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_2Choice','Q33_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_3Choice','Q33_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_4Choice','Q33_4','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_5Choice','Q33_5','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_6Choice','Q33_6','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_7Choice','Q33_7','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_8Choice','Q33_8','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_9Choice','Q33_9','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_10Choice','Q33_10','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ33_11Choice','Q33_11','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ34_1Choice','Q34_1','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ34_2Choice','Q34_2','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ34_3Choice','Q34_3','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ34_1Choice' , 'Q34_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ34_2Choice' , 'Q34_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ34_3Choice' , 'Q34_3')));