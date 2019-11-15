-- DML:570
INSERT INTO fieldspecification(
          fieldname,  fieldlength, 
            rejectifempty, rejectifinvalid,mappedname,showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield, fieldtype, minimumregex, maximumregex)
    VALUES ( 'grade', 100, 
            false, false,'Grade',true, 
            current_timestamp, 12, true, current_timestamp, 12, 
            false, '', '', '');

UPDATE fieldspecificationsrecordtypes set fieldspecificationid=(select id from fieldspecification where fieldname like 'grade' and mappedname like 'Grade')
where  recordtypeid=(select id as recordtypeid from category where categorycode='TEC_RECORD_TYPE')and mappedname like 'Grade';

