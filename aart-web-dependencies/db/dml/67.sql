
--R8 - Release Prep.
--DE4674

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
 iskeyvaluepairfield)values('questionQ16_1Choice','Q16_1','{'''',1,2,3,4,5,6,7,8,9,10,11,12,13,14}',2,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
 (Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
 (Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
 modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ16_1Choice' , 'Q16_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
 (Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
 modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ19Choice' , 'Q19')));


insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
 (Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
 modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ22Choice' , 'Q22')));


insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
 (Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
 modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ23_1Choice' , 'Q23_1')));

 insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
 (Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
 modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ23_2Choice' , 'Q23_2')));

 insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
 (Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
 modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ23_3Choice' , 'Q23_3')));

 insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
 (Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
 modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ135Choice' , 'Q135')));


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