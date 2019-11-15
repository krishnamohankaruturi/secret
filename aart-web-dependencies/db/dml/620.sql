--dml/620.sql

INSERT INTO fieldspecificationsrecordtypes(
            fieldspecificationid, recordtypeid, createddate, createduser, 
            activeflag, modifieddate, modifieduser, mappedname)
    VALUES ((select id from fieldspecification where fieldname = 'legalMiddleName' and rejectifempty is false 
    			and rejectifinvalid is false and activeflag is true and showerror is false), 
    			(select id from category where categorycode = 'SCORING_RECORD_TYPE'), CURRENT_TIMESTAMP, 
    			(Select id from aartuser where username  = 'cetesysadmin'), 
            true, CURRENT_TIMESTAMP, (Select id from aartuser where username  = 'cetesysadmin'), 'Student Middle Name');