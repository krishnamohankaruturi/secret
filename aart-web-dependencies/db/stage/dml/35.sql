--dml/35.SQL 

update batchupload set uploadtype ='UPLOAD_GRF_NY_FILE_TYPE' where uploadtype ='UPLOAD_NY_GRF_FILE_TYPE';
update batchupload set uploadtype ='UPLOAD_GRF_IA_FILE_TYPE' where uploadtype ='UPLOAD_IA_GRF_FILE_TYPE';
update batchupload set uploadtype ='UPLOAD_SC_CODE_KS_FILE_TYPE' where uploadtype ='UPLOAD_KS_SC_CODE_FILE_TYPE';
