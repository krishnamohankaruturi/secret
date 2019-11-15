--dml/598.sql
--Relaxing the validation on TestID1 (not required for social studies) for Raw to scale score upload
update fieldspecification 
set rejectifempty = false
where id = (
select fs.id 
from fieldspecification fs
join fieldspecificationsrecordtypes fsrt ON fs.id = fsrt.fieldspecificationid and fsrt.activeflag is true
where fsrt.recordtypeid = (select id from category where categorycode = 'RAW_TO_SCALE_SCORE' and categorytypeid =(select id from categorytype where typecode = 'REPORT_UPLOAD_FILE_TYPE'))
and fsrt.mappedname = 'TestID_1');


