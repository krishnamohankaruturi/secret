-- 462.sql
update category set categoryname='2016' where categorycode='KANSAS_SCHEDULED_WEB_SERVICE_SCHOOL_YEAR';

update organization 
			set schoolstartdate=(select timestamp '2015-08-01 00:00'), 
					schoolenddate=(select timestamp '2016-07-31 00:00') 
	where id=(select id from organization where organizationtypeid=2 and displayidentifier='KS');
	
INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingInd1Elpa21', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_ELPA21_1', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingInd1Elpa21' and mappedname='Grouping_ELPA21_1'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_ELPA21_1');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingInd2Elpa21', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_ELPA21_2', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingInd2Elpa21' and mappedname='Grouping_ELPA21_2'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_ELPA21_2');	

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingInd1CTE', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_CTE_1', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingInd1CTE' and mappedname='Grouping_CTE_1'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_CTE_1');

INSERT INTO fieldspecification (fieldname, allowablevalues, minimum, maximum, fieldlength, rejectifempty, rejectifinvalid, formatregex, mappedname, showerror,createddate,createduser,activeflag,modifieddate,modifieduser,iskeyvaluepairfield) 
VALUES ('groupingInd2CTE', NULL, NULL, NULL, 60, false, false, NULL, 'Grouping_CTE_2', true,now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),false);
INSERT INTO fieldspecificationsrecordtypes (fieldspecificationid, recordtypeid,createddate,createduser,activeflag,modifieddate,modifieduser,mappedname) 
VALUES ((select id from fieldspecification where fieldname = 'groupingInd2CTE' and mappedname='Grouping_CTE_2'), (select id from category where categorycode = 'KID_RECORD_TYPE'),
now(),(select id from aartuser where username='cetesysadmin'),true,now(),(select id from aartuser where username='cetesysadmin'),'Grouping_CTE_2');	
