--dml/618.sql

INSERT INTO fieldspecification(
            fieldname, allowablevalues, minimum, maximum, fieldlength, 
            rejectifempty, rejectifinvalid, formatregex, mappedname, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ('testId', null, null, null, null, 
            true, true, null, null, true, 
            CURRENT_TIMESTAMP, (select id from aartuser  where email = 'cete@ku.edu'), true, CURRENT_TIMESTAMP, (select id from aartuser  where email = 'cete@ku.edu'), 
            false, 'number', null, null);		
			
update fieldspecificationsrecordtypes set fieldspecificationid = (select id from fieldspecification where fieldname = 'testId' and mappedname is null) 
where fieldspecificationid = (select id from fieldspecification where fieldname = 'testId' and mappedname is not null) 
and recordtypeid = (select id from category where categorycode = 'SCORING_RECORD_TYPE');

