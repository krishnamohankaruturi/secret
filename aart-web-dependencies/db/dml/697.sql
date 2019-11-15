--697.dml

update fieldspecification set allowablevalues = '{TRUE, FALSE, true, false, True, False}' 
	where id in(select id from fieldspecification where fieldname='completedFlag'
	and rejectifempty is true and rejectifinvalid is true and mappedname ='Completed_Flag' and activeflag is true);

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('stateId', null, null, null, null, 
            'true', 'true', null, '', 'true', 
            now(), (select id  from aartuser  where email ='cete@ku.edu'), 'true', now(), (select id  from aartuser  where email ='cete@ku.edu'), 
            'false', 'String', null, null);

update fieldspecificationsrecordtypes set fieldspecificationid = (select id from fieldspecification where fieldname='stateId'and rejectifempty is true and rejectifinvalid is true and activeflag is true) where fieldspecificationid = (select id from fieldspecification where fieldname='state'and rejectifempty is true and rejectifinvalid is true and mappedname is null and activeflag is true) and recordtypeid = (SELECT id from category where    categorycode ='UPLOAD_STUDENT_SCORE_CALC' and categorytypeid=
(select id from categorytype where typecode ='CSV_RECORD_TYPE'));

select * from DFMInsert(46,'Testing_Readiness_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','DLM');
select * from DFMInsert(46,'Testing_Readiness_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','KAP');
select * from DFMInsert(46,'Testing_Readiness_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','KELPA2');
select * from DFMInsert(46,'Testing_Readiness_Extract_Data_Dictionary.pdf','srv/ep-data-store/datadictionaries',null,null,'cetesysadmin','CPASS');

