--For /dml/419.sql
--US16252 & US16246

	/* For User upload field mapping */
	update fieldspecificationsrecordtypes set mappedname = 'Educator_Identifier' 
		where fieldspecificationid = (select id from fieldspecification where fieldname='educatorIdentifier' and fieldlength=254) 
			and recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE');
	update fieldspecificationsrecordtypes set mappedname = 'Legal_Last_Name' 
		where fieldspecificationid = (select id from fieldspecification where fieldname='lastName') 
			and recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE');
	update fieldspecificationsrecordtypes set mappedname = 'Legal_First_Name' 
		where fieldspecificationid = (select id from fieldspecification where fieldname='firstName') 
			and recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE');
	update fieldspecificationsrecordtypes set mappedname = 'Email' 
		where fieldspecificationid = (select id from fieldspecification where fieldname='email') 
			and recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE');
	update fieldspecificationsrecordtypes set mappedname = 'Organization_Level' 
		where fieldspecificationid = (select id from fieldspecification where fieldname='organizationTypeCode') 
			and recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE');
	update fieldspecificationsrecordtypes set mappedname = 'Organization' 
		where fieldspecificationid = (select id from fieldspecification where fieldname='displayIdentifier') 
			and recordtypeid = (select id from category where categorycode='USER_RECORD_TYPE');

	/* To upload Primary and Secondary roles */
	insert into fieldspecification (fieldname,allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,formatregex,mappedname,showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
	values('primaryRole',null,null,null,50,false,false,'^[A-z0-9!@#$%^&*()-_''~`:,\[\]{}<>+=\./\ ]++$',null,true,current_timestamp,
		(select id from aartuser where username='cetesysadmin'),true,current_timestamp,(select id from aartuser where username='cetesysadmin'),false,null,null,null);

	insert into fieldspecification (fieldname,allowablevalues,minimum,maximum,fieldlength,rejectifempty,rejectifinvalid,formatregex,mappedname,showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
	values('secondaryRole',null,null,null,50,false,false,'^[A-z0-9!@#$%^&*()-_''~`:,\[\]{}<>+=\./\ ]++$',null,true,current_timestamp,
	(select id from aartuser where username='cetesysadmin'),true,current_timestamp,(select id from aartuser where username='cetesysadmin'),false,null,null,null);

	insert into fieldspecificationsrecordtypes 
		select id, (select id from category where categorycode='USER_RECORD_TYPE'),current_timestamp,(select id from aartuser where username='cetesysadmin'),
		true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Primary_Role' 
		from fieldspecification where fieldname = 'primaryRole' ;

	insert into fieldspecificationsrecordtypes 
		select id, (select id from category where categorycode='USER_RECORD_TYPE'),current_timestamp,(select id from aartuser where username='cetesysadmin'),
		true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Secondary_Role' 
		from fieldspecification where fieldname = 'secondaryRole' ;