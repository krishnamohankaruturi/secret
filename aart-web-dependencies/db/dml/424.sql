--424.sql

/************ US16239 *************/
insert into fieldspecification 
	(fieldname,allowablevalues,minimum,maximum,fieldlength,	rejectifempty,rejectifinvalid,formatregex,mappedname,
	showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield,fieldtype,minimumregex,maximumregex)
 values('primaryAssessmentProgram',null,null,null,100,true,true,'^[A-z0-9!@#$%^&*()-_''~`:,\[\]{}<>+=\./\ ]++$',null,true,current_timestamp,
 (select id from aartuser where username='cetesysadmin'),true,current_timestamp,(select id from aartuser where username='cetesysadmin'),false,null,null,null);	
 
/************ US16239 *************/
  insert into fieldspecificationsrecordtypes 
	  select id, (select id as recordtypeid from category where categorycode='USER_RECORD_TYPE'),current_timestamp,(select id from aartuser where username='cetesysadmin'),
	  true,current_timestamp,(select id from aartuser where username='cetesysadmin'),'Primary_Assessment_Program' 
	  from fieldspecification where fieldname = 'primaryAssessmentProgram' ;
	  
/********************US16248************************/
/********Update Query : */
update groups set activeflag ='f' where id IN (
select id from groups where groupname IN ('PD State Admin',  'PD District Admin' , 'PD User'));

/*******************Delete Query:*/
delete from userorganizationsgroups  where groupid IN (select id from groups where groupname IN ('PD State Admin',  'PD District Admin' , 'PD User'));

INSERT INTO category(
            categoryname, categorycode, categorydescription, categorytypeid, 
            externalid, originationcode, createddate, createduser, activeflag, 
            modifieddate, modifieduser)
    VALUES ('PD Training Results Upload', 'PD_TRAINING_RECORD_TYPE', 'PD Training Results Upload', (select id from categorytype where typecode='CSV_RECORD_TYPE'), 
            null, 'AART_ORIG_CODE', current_timestamp , (select id from aartuser where username='cetesysadmin'), true,
            current_timestamp, (select id from aartuser where username='cetesysadmin'));