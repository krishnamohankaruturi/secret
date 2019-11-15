--DE15601: EP Prod: TEC Files not uploading with valid Exit Reason codes
DO 
$BODY$
DECLARE
 field_spec_id BIGINT;
BEGIN
  INSERT INTO fieldspecification(
        fieldname, allowablevalues, fieldlength, 
            rejectifempty, rejectifinvalid, showerror, 
            createddate, createduser, activeflag, modifieddate, modifieduser, 
            iskeyvaluepairfield)
    VALUES ('exitReason', '{'''',1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,30,98,99}', 2, false, true, true, now(), 12, true, now(), 12, false)
    RETURNING id INTO field_spec_id;
  UPDATE fieldspecificationsrecordtypes SET fieldspecificationid = field_spec_id, modifieddate = now(), modifieduser = 12 where recordtypeid = (select id from category where categorycode = 'TEC_RECORD_TYPE' 
	and categorytypeid = (select id from categorytype where typecode ='CSV_RECORD_TYPE'))
and fieldspecificationid in (select id from fieldspecification where fieldname ='exitReason');


END;
$BODY$; 