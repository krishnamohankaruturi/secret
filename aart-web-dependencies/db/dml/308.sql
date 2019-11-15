-- US15408: Roster - Upload enhancements
delete from fieldspecificationsrecordtypes where recordtypeid = (select id from category where categorycode='SCRS_RECORD_TYPE') and fieldspecificationid=(select id from fieldspecification where fieldname='dlmStatus' and mappedname='DLM_Status');
delete from fieldspecificationsrecordtypes where recordtypeid = (select id from category where categorycode='SCRS_RECORD_TYPE') and fieldspecificationid=(select id from fieldspecification where fieldname='residenceDistrictIdentifier' and mappedname is null);
update fieldspecification set fieldName= 'removeFromRoster',  allowablevalues= '{'''',Remove,Yes}', rejectifinvalid = true where id = (select id from fieldspecification where fieldname='enrollmentStatusCode' and mappedname is null);
update fieldspecification set rejectifempty = false where id = (select id from fieldspecification where fieldname='stateCourseCode' and mappedname is null);
update fieldspecification set rejectifempty= true,rejectifinvalid=true where id = (select id from fieldspecification where fieldname='stateSubjectAreaCode' and mappedname is null);
update fieldspecification set rejectifinvalid=true where id =(select id from fieldspecification where fieldname='legalFirstName' and mappedname is null);
update fieldspecification set rejectifempty=true, rejectifinvalid=true where id =(select id from fieldspecification where fieldname='educatorFirstName' and mappedname is null);
update fieldspecification set rejectifempty=true, rejectifinvalid=true  where id =(select id from fieldspecification where fieldname='educatorLastName' and mappedname is null);
--update fieldspecification set fieldLength=10 where id = (select id from fieldspecification where fieldname='schoolIdentifier');
--update fieldspecification set fieldLength= 10 where id = (select id from fieldspecification where fieldname='localStudentIdentifier' and mappedname is null);

update aartuser set uniquecommonidentifier = NULL where uniquecommonidentifier='';