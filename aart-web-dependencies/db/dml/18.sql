--US11257 Added record and permissions.

insert into category(categoryname,categorycode,categorydescription,
categorytypeid,
originationcode,createduser,modifieduser)
values
(
'First Contact','FIRST_CONTACT_RECORD_TYPE','First Contact For Students',
(Select id from categorytype where typecode='CSV_RECORD_TYPE'),
'AART',
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);

insert into authorities(authority,displayname,objecttype,
createduser,
modifieduser) values
(
'PERM_FIRST_CONTACT_UPLOAD','First Contact Upload','First Contact',
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin')
);

--insert into fieldspecification.
--insert into fieldspecification(fieldname,mappedname,fieldlength,formatregex,
--rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,iskeyvaluepairfield)
--(
--Select
 --fieldname,'Q3' as mappedname,fieldlength,formatregex,rejectifempty,rejectifinvalid,
 --showerror,createduser,modifieduser,true
--from 
--fieldspecification
--where
--fieldname='stateStudentIdentifier' and mappedname is not null
--);

insert into fieldspecification(fieldname,mappedname,allowablevalues,
fieldlength,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,iskeyvaluepairfield)
values
(
'contractingOrganizationDisplayIdentifier',
'Q13_1',
'{1,2,3,4,5,6,7,8,9,10,11,12,13,14}',2,true,true,true,
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin'),
true
);

--firstContactColumnAttributeMapStr=Q3-stateStudentIdentifier,Q13_1-contractingOrganizationDisplayIdentifier,Q16_1-question16Choice1

insert into fieldspecification(fieldname,
mappedname,allowablevalues,fieldlength,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,iskeyvaluepairfield)
values
(
'question36Choice',
'Q36',
'{1,2}',1,true,true,true,
(Select id from aartuser where username='cetesysadmin'),
(Select id from aartuser where username='cetesysadmin'),
true
);

insert into fieldspecificationsrecordtypes
(fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
(
Select id as fieldspecificationid,
(Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,
now() as createddate,modifieduser,now() as modifieddate
 from fieldspecification where fieldname in ('contractingOrganizationDisplayIdentifier','question36Choice','stateStudentIdentifier') and mappedname like 'Q%'
);

--US11257 : First Contact data upload - Communications section - fieldspecification values From  Q152


--insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,
--	modifieduser,iskeyvaluepairfield)values('questionQ152Choice','Q152',null,100,E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$',false,true,true,(Select id from aartuser where username='cetesysadmin'),
--	(Select id from aartuser where username='cetesysadmin'),true);
	
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,
	modifieduser,iskeyvaluepairfield)values('questionQ14_1_TEXTChoice','Q14_1_TEXT',null,100,E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$',false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,
	modifieduser,iskeyvaluepairfield)values('questionQ14_2_TEXTChoice','Q14_2_TEXT',null,100,E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$',false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);				

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ14_3_TEXTChoice','Q14_3_TEXT',null,100,E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$',false,true,true,(Select id from aartuser where username='cetesysadmin'),(Select id from aartuser where username='cetesysadmin'),true);
										   
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ15Choice','Q15','{'''',1,2,3,4}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser
	,modifieduser,iskeyvaluepairfield)values('questionQ129Choice','Q129','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ16_1Choice','Q16_1','{'''',1,2,3,4,5,6,7,8,9,10,11,12,13,14}',2,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,
	modifieduser,iskeyvaluepairfield)values('questionQ17Choice','Q17','{'''',1,2,3,4,5,6}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,
	modifieduser,iskeyvaluepairfield)values('questionQ130Choice','Q130','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

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

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ22Choice','Q22','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ133Choice','Q133','{'''',1,2}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

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

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ24Choice','Q24','{'''',1,2}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

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

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ134Choice','Q134','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

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

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ153Choice','Q153','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

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

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ31_1Choice','Q31_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ31_2Choice','Q31_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ31_3Choice','Q31_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);



insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ136Choice','Q136','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ143Choice','Q143','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ147Choice','Q147','{'''',1,2,3,4}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

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

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ137Choice','Q137','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ36Choice','Q36','{'''',1,2}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ37Choice','Q37','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);



insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ39Choice','Q39','{'''',1,2}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ40Choice','Q40','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ41Choice','Q41','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ148Choice','Q148','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ43Choice','Q43','{'''',1,2}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ44Choice','Q44','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_1Choice','Q45_1','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_2Choice','Q45_2','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_3Choice','Q45_3','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_4Choice','Q45_4','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);



insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_5Choice','Q45_5','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_6Choice','Q45_6','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_7Choice','Q45_7','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_8Choice','Q45_8','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_9Choice','Q45_9','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_10Choice','Q45_10','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_11Choice','Q45_11','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ45_12Choice','Q45_12','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ139Choice','Q139','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ47Choice','Q47','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);



insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ49_1Choice','Q49_1','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ49_2Choice','Q49_2','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ49_3Choice','Q49_3','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ49_4Choice','Q49_4','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ49_5Choice','Q49_5','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ49_6Choice','Q49_6','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ140Choice','Q140','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ51_1Choice','Q51_1','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ51_2Choice','Q51_2','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ51_3Choice','Q51_3','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);



insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ51_4Choice','Q51_4','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ51_5Choice','Q51_5','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ51_6Choice','Q51_6','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ51_7Choice','Q51_7','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ51_8Choice','Q51_8','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ52Choice','Q52','{'''',1,2,3,4,5,6}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_1Choice','Q54_1','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_2Choice','Q54_2','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_3Choice','Q54_3','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_4Choice','Q54_4','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);



insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_5Choice','Q54_5','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_6Choice','Q54_6','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_7Choice','Q54_7','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_8Choice','Q54_8','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_9Choice','Q54_9','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_10Choice','Q54_10','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_11Choice','Q54_11','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_12Choice','Q54_12','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ54_13Choice','Q54_13','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ56_1Choice','Q56_1','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);



insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ56_2Choice','Q56_2','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ56_3Choice','Q56_3','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ56_4Choice','Q56_4','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ56_5Choice','Q56_5','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ56_6Choice','Q56_6','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ56_7Choice','Q56_7','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ56_8Choice','Q56_8','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ141Choice','Q141','{'''',1}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ142Choice','Q142','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ146Choice','Q146','{'''',1,2,3}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);



insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ58_1Choice','Q58_1','{'''',1,2,3,4}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ58_2Choice','Q58_2','{'''',1,2,3,4}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ58_3Choice','Q58_3','{'''',1,2,3,4}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ58_4Choice','Q58_4','{'''',1,2,3,4}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ60Choice','Q60','{'''',1,2,3,4,5}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ62Choice','Q62','{'''',9,10}',2,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ62_TEXTChoice','Q62_TEXT',null,2000,E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$',false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ63Choice','Q63',null,2000,E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$',false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ64Choice','Q64','{'''',1,2,3,4}',1,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ65Choice','Q65',null,2000,E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$',false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('questionQ154Choice','Q154',null,2000,E'^[A-z0-9!@#$%^&*()-_''"~`:;\\[\\]{}<>+=\\./\\ ]++$',false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),true);

-- Mahesh. field specs for 19 questions.

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('questionQ152Choice', NULL, NULL, NULL, 100, false, true, '^[A-z0-9!@#$%^&*()-_''"~`:;\[\]{}<>+=\./\ ]++$', 'Q152', true, '2013-04-15 12:08:55.725177-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-15 12:08:55.725177-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('stateStudentIdentifier', NULL, NULL, NULL, 10, true, true, '^[A-z0-9!@#$%^&*()-_''"~`:;\[\]{}<>+=\./\ ]++$', 'Q3', true, '2013-04-09 19:21:28.349906-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:21:28.349906-05',(select id from aartuser where username='cetesysadmin'), false);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('contractingOrganizationAlias', '{1,2,3,4,5,6,7,8,9,10,11,12,13,14}', NULL, NULL, 2, true, true, NULL, 'Q13_1', true, '2013-04-09 19:16:07.245557-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:07.245557-05',(select id from aartuser where username='cetesysadmin'), false);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question149Choice', '{1,''''}', NULL, NULL, 1, false, true, NULL, 'Q149', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question127Choice', '{1,''''}', NULL, NULL, 1, false, true, NULL, 'Q127', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question7_1Choice', '{1,''''}', NULL, NULL, 1, false, true, NULL, 'Q7_1', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question7_2Choice', '{1,''''}', NULL, NULL, 1, false, true, NULL, 'Q7_2', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question7_3Choice', '{1,''''}', NULL, NULL, 1, false, true, NULL, 'Q7_3', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) 
VALUES ('question7_4Choice', '{1,''''}', NULL, NULL, 1, false, true, NULL, 'Q7_4', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) 
VALUES ('question7_6Choice', '{1,''''}', NULL, NULL, 1, false, true, NULL, 'Q7_6', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) VALUES ('question8Choice', '{1,2,''''}', NULL, NULL, 1, false, true, NULL, 'Q8', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question11Choice', '{1,2,3,4,5,6,7,''''}', NULL, NULL, 50, false, true, NULL, 'Q11', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question12_1Choice', '{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30''''}', 0, 30, 2, false, true, NULL, 'Q12_1', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield) 
VALUES ('question9Choice', '{1,2,3,4,5,6,7,8,9,''''}', NULL, NULL, 1, false, true, NULL, 'Q9', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question144Choice', '{1,2,3,4,''''}', NULL, NULL, 1, false, true, NULL, 'Q144', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question6Choice', '{1,2,''''}', NULL, NULL, 1, false, true, NULL, 'Q6', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question128Choice', '{1,2,''''}', NULL, NULL, 1, false, true, NULL, 'Q128', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question10Choice', '{1,2,3,4,5,6,7,8,9,''''}', NULL, NULL, 1, false, true, NULL, 'Q10', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, createddate, createduser, activeflag, modifieddate, modifieduser, iskeyvaluepairfield)
 VALUES ('question7_6_TextChoice', NULL, NULL, NULL, 25, false, true, NULL, 'Q7_6_TEXT', true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true, '2013-04-09 19:16:13.675099-05',(select id from aartuser where username='cetesysadmin'), true);
 
-- fieldspecificationsrecordtypes values From  Q152

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ152Choice' , 'Q152')));
	
insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ14_1_TEXTChoice' , 'Q14_1_TEXT')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ14_2_TEXTChoice' , 'Q14_2_TEXT')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ14_3_TEXTChoice' , 'Q14_3_TEXT')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ15Choice' , 'Q15')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ129Choice' , 'Q129')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ16_1Choice' , 'Q16_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ17Choice' , 'Q17')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ130Choice' , 'Q130')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ19Choice' , 'Q19')));

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

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ151_1Choice' , 'Q151_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ151_2Choice' , 'Q151_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ151_3Choice' , 'Q151_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ24Choice' , 'Q24')));

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
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ134Choice' , 'Q134')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ135Choice' , 'Q135')));

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

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ153Choice' , 'Q153')));

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

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ31_1Choice' , 'Q31_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ31_2Choice' , 'Q31_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ31_3Choice' , 'Q31_3')));



insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ136Choice' , 'Q136')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ143Choice' , 'Q143')));

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

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ34_1Choice' , 'Q34_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ34_2Choice' , 'Q34_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ34_3Choice' , 'Q34_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ137Choice' , 'Q137')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ36Choice' , 'Q36')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ37Choice' , 'Q37')));



insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ39Choice' , 'Q39')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ40Choice' , 'Q40')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ41Choice' , 'Q41')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ148Choice' , 'Q148')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ43Choice' , 'Q43')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ44Choice' , 'Q44')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_1Choice' , 'Q45_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_2Choice' , 'Q45_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_3Choice' , 'Q45_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_4Choice' , 'Q45_4')));



insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_5Choice' , 'Q45_5')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_6Choice' , 'Q45_6')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_7Choice' , 'Q45_7')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_8Choice' , 'Q45_8')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_9Choice' , 'Q45_9')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_10Choice' , 'Q45_10')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_11Choice' , 'Q45_11')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ45_12Choice' , 'Q45_12')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ139Choice' , 'Q139')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ47Choice' , 'Q47')));



insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ49_1Choice' , 'Q49_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ49_2Choice' , 'Q49_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ49_3Choice' , 'Q49_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ49_4Choice' , 'Q49_4')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ49_5Choice' , 'Q49_5')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ49_6Choice' , 'Q49_6')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ140Choice' , 'Q140')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ51_1Choice' , 'Q51_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ51_2Choice' , 'Q51_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ51_3Choice' , 'Q51_3')));



insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ51_4Choice' , 'Q51_4')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ51_5Choice' , 'Q51_5')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ51_6Choice' , 'Q51_6')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ51_7Choice' , 'Q51_7')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ51_8Choice' , 'Q51_8')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ52Choice' , 'Q52')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_1Choice' , 'Q54_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_2Choice' , 'Q54_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_3Choice' , 'Q54_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_4Choice' , 'Q54_4')));



insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_5Choice' , 'Q54_5')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_6Choice' , 'Q54_6')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_7Choice' , 'Q54_7')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_8Choice' , 'Q54_8')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_9Choice' , 'Q54_9')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_10Choice' , 'Q54_10')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_11Choice' , 'Q54_11')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_12Choice' , 'Q54_12')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ54_13Choice' , 'Q54_13')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ56_1Choice' , 'Q56_1')));



insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ56_2Choice' , 'Q56_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ56_3Choice' , 'Q56_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ56_4Choice' , 'Q56_4')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ56_5Choice' , 'Q56_5')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ56_6Choice' , 'Q56_6')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ56_7Choice' , 'Q56_7')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ56_8Choice' , 'Q56_8')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ141Choice' , 'Q141')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ142Choice' , 'Q142')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ146Choice' , 'Q146')));



insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ58_1Choice' , 'Q58_1')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ58_2Choice' , 'Q58_2')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ58_3Choice' , 'Q58_3')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ58_4Choice' , 'Q58_4')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ60Choice' , 'Q60')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ62Choice' , 'Q62')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ62_TEXTChoice' , 'Q62_TEXT')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ63Choice' , 'Q63')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ64Choice' , 'Q64')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ65Choice' , 'Q65')));

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate)
	(Select id as fieldspecificationid, (Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate from fieldspecification where (fieldname,mappedname) in (('questionQ154Choice' , 'Q154')));

--insert all into fspec recordtypes

 insert into fieldspecificationsrecordtypes(fieldspecificationid,recordtypeid,createduser,modifieduser)
(
Select id as fieldspecificationid,
(Select id from category where categorycode='FIRST_CONTACT_RECORD_TYPE') as recordtypeid,
createduser,modifieduser
from fieldspecification fspec
where 
fspec.id not in (Select fieldspecificationid from fieldspecificationsrecordtypes)
and
mappedname like 'Q%'
);	

-- for text responses.
	
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser,responselabel)
                VALUES ((select id from surveylabel where labelnumber='Q7_6_TEXT'), 2,'', now(), (Select id from aartuser where username = 'cetesysadmin'), true,
                now(), (Select id from aartuser where username = 'cetesysadmin'), 'TEXT');
 
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser,responselabel)
                VALUES ((select id from surveylabel where labelnumber='Q14_1_TEXT'), 1,'', now(), (Select id from aartuser where username = 'cetesysadmin'), true,
                now(), (Select id from aartuser where username = 'cetesysadmin'), 'TEXT');
 
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser,responselabel)
                VALUES ((select id from surveylabel where labelnumber='Q14_2_TEXT'), 1,'', now(), (Select id from aartuser where username = 'cetesysadmin'), true,
                now(), (Select id from aartuser where username = 'cetesysadmin'), 'TEXT');
 
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser,responselabel)
                VALUES ((select id from surveylabel where labelnumber='Q14_3_TEXT'), 1,'', now(), (Select id from aartuser where username = 'cetesysadmin'), true,
                now(), (Select id from aartuser where username = 'cetesysadmin'), 'TEXT');
 
INSERT INTO surveyresponse (labelid, responseorder, responsevalue, createddate, createduser, activeflag, modifieddate, modifieduser,responselabel)
                VALUES ((select id from surveylabel where labelnumber='Q62_TEXT'), 2,'', now(), (Select id from aartuser where username = 'cetesysadmin'), true,
                now(), (Select id from aartuser where username = 'cetesysadmin'), 'TEXT');
                
 --CB related metadata for publishing related to task.
 INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Essential Element Linkage', 'ESSENTIAL_ELEMENT_LINKAGE',  'Essential Element Linkage', 'CB', now(),(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES                ('Initial Precursor', 'IP', 'Initial Precursor', (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')),
                ('Distal Precursor', 'DP', 'Distal Precursor', (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')),
                ('Proximal Precursor', 'PP', 'Proximal Precursor', (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')),
                ('Target', 'TA', 'Target', (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')),
                ('Successor', 'S', 'Successor', (select id from categorytype where typecode='ESSENTIAL_ELEMENT_LINKAGE'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Targeted Alternate Pathway', 'TARGETED_ALTERNATE_PATHWAY',  'Targeted Alternate Pathway', 'CB', now(),(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES                ('Yes', 'Y', 'Yes', (select id from categorytype where typecode= 'TARGETED_ALTERNATE_PATHWAY'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')),
                ('No', 'N', 'No', (select id from categorytype where typecode= 'TARGETED_ALTERNATE_PATHWAY'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORYTYPE (typename, typecode, typedescription, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES ('Testlet Access', 'TESTLET_ACCESS',  'Testlet Access', 'CB', now(),(select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));
 
INSERT INTO CATEGORY (categoryname, categorycode, categorydescription, categorytypeid, originationcode, createddate, createduser, modifieddate, modifieduser)
VALUES                ('Deaf', 'Deaf', 'Deaf/hard of hearing', (select id from categorytype where typecode= 'TESTLET_ACCESS'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')),
                ('Blind', 'Blind', 'Blind/low vision', (select id from categorytype where typecode= 'TESTLET_ACCESS'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')),
                ('Mobility', 'Mobility', 'Mobility - Mobility', (select id from categorytype where typecode= 'TESTLET_ACCESS'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin')),
                ('General', 'general', 'General', (select id from categorytype where typecode= 'TESTLET_ACCESS'), 'CB', now(), (select id from aartuser where username='cetesysadmin'), now(), (select id from aartuser where username='cetesysadmin'));

                
                
                
                