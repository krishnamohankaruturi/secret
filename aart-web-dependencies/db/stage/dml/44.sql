--dml/44.sql
--change the file location for existing files to reference the correct folder in AWS S3
update batchupload set filepath=replace(filepath, '/srv/', '') where filepath like '/srv/%'