--DE9740: Reports upload - UI changes per user feedback
UPDATE category 
SET activeflag = false
WHERE categorytypeid = (select id from categorytype where typecode ='REPORT_UPLOAD_FILE_TYPE') and categorycode='MISCELLANEOUS_REPORT_TEXT';