-- 473.sql
update fieldspecification  set rejectifempty = false where fieldname = 'stateStudentIdentifier' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and
  fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'stateStudentIdentifier'  )
);

update fieldspecification  set rejectifempty = false where fieldname = 'attendanceSchoolProgramIdentifier' and id = (select 
fieldspecificationid from fieldspecificationsrecordtypes fs where
 recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' )
 and fieldspecificationid = ( select id from fieldspecification where fs.fieldspecificationid = id and
  fs.recordtypeid = ( select id from category where categorycode='TEC_RECORD_TYPE' ) and fieldname = 'attendanceSchoolProgramIdentifier'  )
);

INSERT INTO authorities(authority, displayname, objecttype, createddate, createduser, 
            activeflag, modifieddate, modifieduser)
    VALUES ('PERM_SCORING_ASSIGNSCORER', 'Assign Scorer', 'Test Management-Scoring',CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'), true, 
            CURRENT_TIMESTAMP,(select id from aartuser where email='cete@ku.edu'));
