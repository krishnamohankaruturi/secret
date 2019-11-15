--dml/745.sql
--change the file location for existing files to reference the correct folder in AWS S3
update datadictionaryfilemapping set filelocation=replace(filelocation, 'srv/', '') where filelocation like 'srv/%';

update statespecificfile set filelocation=replace(filelocation, '/srv/', '') where filelocation like '/srv/%'; 

update modulereport set filename=replace(filename, '/srv/', '') where filename like '/srv/%';

update appconfiguration 
set attrname ='A student with the entered State Student Identifier already exists and is active for this school year. To make changes, please use the upload, edit or transfer functions.' 
where  attrcode='add_student_exist_active';
