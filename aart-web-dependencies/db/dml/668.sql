--dml/668.sql F429 Kids changes 2018

--TASC : allow only ELA and Math subject codes
update fieldspecification  set allowablevalues = '{01,02,51,52,80,81,82}' where fieldname  = 'tascStateSubjectAreaCode';

--Delete fields related to Financial Literacy Assessment
DELETE FROM fieldspecificationsrecordtypes  WHERE fieldspecificationid in(SELECT id FROM fieldspecification  WHERE fieldname  = 'financialLiteracyAssessment');
DELETE FROM fieldspecification  WHERE fieldname  = 'financialLiteracyAssessment';

DELETE FROM fieldspecificationsrecordtypes  WHERE fieldspecificationid in(SELECT id FROM fieldspecification  WHERE fieldname  = 'groupingInd1FinancialLiteracy');
DELETE FROM fieldspecification  WHERE fieldname  = 'groupingInd1FinancialLiteracy';

DELETE FROM fieldspecificationsrecordtypes  WHERE fieldspecificationid in(SELECT id FROM fieldspecification  WHERE fieldname  = 'groupingInd2FinancialLiteracy');
DELETE FROM fieldspecification  WHERE fieldname  = 'groupingInd2FinancialLiteracy';

--update allowable values for History assessment
update fieldspecification  set allowablevalues = '{0,2,C}' where fieldname  = 'stateHistGovAssessment';

update fieldspecification  set allowablevalues = '{0,1,3,6,C}' where fieldname  = 'generalCTEAssessment';
update fieldspecification  set allowablevalues = '{0,1,2,3,4,5,6,7,C}' where fieldname  = 'comprehensiveAgAssessment';
update fieldspecification  set allowablevalues = '{0,1,3,6,C}' where fieldname  = 'animalSystemsAssessment';
update fieldspecification  set allowablevalues = '{0,1,2,3,4,5,6,7,C}' where fieldname  = 'plantSystemsAssessment';
update fieldspecification  set allowablevalues = '{0,1,C}' where fieldname  = 'manufacturingProdAssessment';
update fieldspecification  set allowablevalues = '{0,1,C}' where fieldname  = 'designPreConstructionAssessment';
update fieldspecification  set allowablevalues = '{0,1,2,5,C}' where fieldname  = 'financeAssessment';
update fieldspecification  set allowablevalues = '{0,1,C}' where fieldname  = 'comprehensiveBusinessAssessment';

--Add new fields for HistoryGovernance proctor information
--HGSS ProctorId
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('historyGovProctorId',null,null,20,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser, now() as createddate,
	modifieduser,now() as modifieddate, 'State_History_Proctor_ID' as mappedname from fieldspecification where fieldname = 'historyGovProctorId');

--HGSS_Proctor_First_Name
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('historyGovProctorFirstName',null,null,120,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate, 'State_History_Proctor_First_Name' as mappedname from fieldspecification where fieldname = 'historyGovProctorFirstName');

--HGSS_Proctor_Last_Name
insert into fieldspecification(fieldname, mappedname,allowablevalues,fieldlength,formatregex,rejectifempty,rejectifinvalid,showerror,createduser,modifieduser,
	iskeyvaluepairfield)values('historyGovProctorLastName',null,null,120,null,false,true,true,(Select id from aartuser where username='cetesysadmin'),
	(Select id from aartuser where username='cetesysadmin'),false);

insert into fieldspecificationsrecordtypes (fieldspecificationid,recordtypeid,createduser,createddate,modifieduser,modifieddate, mappedname)
	(Select id as fieldspecificationid, (Select id from category where categorycode='KID_RECORD_TYPE') recordtypeid,createduser,now() as createddate,
	modifieduser,now() as modifieddate, 'State_History_Proctor_Last_Name' as mappedname from fieldspecification where fieldname = 'historyGovProctorLastName');	
